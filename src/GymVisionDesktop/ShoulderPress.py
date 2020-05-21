import argparse
import logging
import time
import math
import cv2
import numpy as np
import parserSetup
from tf_pose.estimator import TfPoseEstimator
from tf_pose.networks import get_graph_path, model_wh
from positions import position

def str2bool(v):
    return v.lower() in ("yes", "true", "t", "1")


def model(w, h, args):
    if w > 0 and h > 0:
        e = TfPoseEstimator(get_graph_path(args.model), target_size=(w, h), trt_bool=str2bool(args.tensorrt))
    else:
        e = TfPoseEstimator(get_graph_path(args.model), target_size=(432, 368), trt_bool=str2bool(args.tensorrt))
    return e


class ex():
    def __init__(self):
        return

    def analyze(self):
        args = parserSetup.parserSetup()
        w, h = model_wh(args.resize)
        e = model(w, h, args)
        pos = position()
        gripCheckFailed = 0
        elbowCheckFailed = 0


        #cam = cv2.VideoCapture("C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/OverheadGood.mp4")
        cam = cv2.VideoCapture(args.camera)
        ret_val, image = cam.read()
        orange_color = (0, 140, 255)

        while True:
            ret_val, image = cam.read()

            humans = e.inference(image, resize_to_default=(w > 0 and h > 0), upsample_size=args.resize_out_ratio)
            pose = humans
            image = TfPoseEstimator.draw_humans(image, humans, imgcopy=False)

            if len(pose) > 0:
                # distance calculations
                for human in humans:
                    for i in range(len(humans)):

                        try:


                            pos.getPositions(human,image)



                            if gripCheckFailed < 5:
                                if not self.gripWidthCheck(human,image,pos):
                                    gripCheckFailed +=1

                            if gripCheckFailed == 5:
                                cv2.putText(image, "Grip is too wide/too close", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)
                            else:
                                cv2.putText(image, "Good grip width!", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)



                            if elbowCheckFailed < 5:
                                if not self.elbowCheck(human,image,pos):
                                    elbowCheckFailed +=1
                            if elbowCheckFailed ==5:
                                cv2.putText(image, "Starting position forearms not vertical", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)
                            else:
                                cv2.putText(image, "Elbows and Wrists aligned, Good", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)


                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()

    def gripWidthCheck(self,human,image,pos): # your grip should be a little bit over shoulder width
        leftCheckBoundary = pos.xLeftShoulder + 60
        rightCheckBoundary= pos.xRightShoulder - 60
        check1 = pos.xLeftWrist <= leftCheckBoundary and pos.xLeftWrist >= pos.xLeftShoulder and pos.xRightWrist >= rightCheckBoundary and pos.xRightWrist <= pos.xRightShoulder
        if check1 :
            return True
        else:
            return False





    def elbowCheck(self,human,image,pos): # Elbows should be directly underneath wrists in shoulder press

        check1 = abs(pos.xRightWrist - pos.xRightElbow) < 20
        check2 = abs(pos.xLeftWrist -pos.xLeftElbow) < 20
        if pos.yRightElbow > pos.yRightShoulder + 20 and pos.yLeftElbow > pos.yLeftShoulder + 20:
            if check1 and check2:
                return True
            else:
                return False
        return True




















if __name__ == "__main__":
    ex = ex()
    ex.analyze()
