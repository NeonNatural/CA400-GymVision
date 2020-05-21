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
        barLowCheckPassed = 0
        elbowCheckFailed = 0



        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/BenchG.mp4')
        cam = cv2.VideoCapture(args.camera)
        ret_val, image = cam.read()
        orange_color = (0, 140, 255)

        while True:
            ret_val, image = cam.read()
            image = cv2.rotate(image, cv2.ROTATE_90_COUNTERCLOCKWISE)

            humans = e.inference(image, resize_to_default=(w > 0 and h > 0), upsample_size=args.resize_out_ratio)
            pose = humans
            #image = TfPoseEstimator.draw_humans(image, humans, imgcopy=False)

            if len(pose) > 0:
                # distance calculations
                for human in humans:
                    for i in range(len(humans)):
                        pos.getPositions(human,image)

                        try:
                            pos.getPositions(human,image)
                            if elbowCheckFailed < 5:
                                if not self.elbowCheck(human,image,pos):
                                    elbowCheckFailed +=1
                            if elbowCheckFailed ==5:
                                cv2.putText(image, "Elbows are too high", (5, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)
                            else:
                                cv2.putText(image, "Elbows low enough", (5, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)



                            if barLowCheckPassed <1 :
                                if self.bringBarLow(human,image,pos):
                                    barLowCheckPassed +=1
                            if barLowCheckPassed ==1:
                                cv2.putText(image, "Full range of motion, Good!", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)
                            else:
                                cv2.putText(image, "Bring bar low to chest", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)





                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()

    def elbowCheck(self,human,image,pos):

        if  pos.yLeftElbow < pos.yLeftShoulder :
            return False

        return True

    def bringBarLow(self,human,image,pos):
        if pos.xLeftWrist >= pos.xLeftShoulder - 20:
            return True
        else:
            return False

    #def headCorrectPosition()











if __name__ == "__main__":
    ex = ex()
    ex.analyze()
