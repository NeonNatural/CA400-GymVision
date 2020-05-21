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
        stanceCheckFailed = 0
        gripCheckFailed = 0


        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/Row.mp4')
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
                            if stanceCheckFailed <5:
                                if(self.stance(human,image,pos)):
                                    stanceCheckFailed +=1
                            if stanceCheckFailed ==5:
                                cv2.putText(image, "Stance is not correct", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)
                            else:
                                cv2.putText(image, "Stance Good", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)


                            if gripCheckFailed <5:
                                if(self.grip(human,image,pos)):
                                    gripCheckFailed +=1
                            if gripCheckFailed ==5:
                                if stanceCheckFailed <5:
                                    if(self.stance(human,image,pos)):
                                        stanceCheckFailed +=1
                                if stanceCheckFailed ==5:
                                    cv2.putText(image, "Grip is not correct", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)



                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()

    def stance(self,human,image,pos): # feet should be narrower than shoulder width appart but wider  than hip
        check1 = pos.xLeftAnkle > pos.xLeftHip and pos.xRightAnkle < pos.xRightHip
        check2 = pos.xLeftAnkle < pos.xLeftShoulder and pos.xRightAnkle > pos.xRightShoulder
        if check1 and check2:
            return True
        return False

    def grip(self,human,image,pos): # grip should be outside shoulder with apart but still narrower than bench press
        # print(pos.xLeftWrist,pos.xLeftKnee,pos.xLeftKnee +40)
        # print(pos.xRightWrist,pos.xRightKnee,pos.xRightKnee -40)
        check1 = pos.xLeftWrist > pos.xLeftKnee and pos.xLeftWrist < pos.xLeftKnee + 50
        check2 = pos.xRightWrist < pos.xRightKnee and pos.xRightWrist > pos.xRightKnee -50
        if check1 and check2:
            return True
        return False














if __name__ == "__main__":
    ex = ex()
    ex.analyze()
