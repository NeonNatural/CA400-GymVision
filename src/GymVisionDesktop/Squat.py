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
import os
from threading import Thread

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
        frameCount = 1
        kneeCheckCount = 0
        hipcheckCount = 0
        footCheckCount = 0
        kneeCheckFailed = False
        footCheckFailed = False
        hipCheckPassed = False



        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/SquatBad2.mp4')
        cam = cv2.VideoCapture(args.camera)
        ret_val, image = cam.read()
        orange_color = (0, 140, 255)

        while True:
            ret_val, image = cam.read()
            frameCount+=1


            humans = e.inference(image, resize_to_default=(w > 0 and h > 0), upsample_size=args.resize_out_ratio)
            pose = humans
            image = TfPoseEstimator.draw_humans(image, humans, imgcopy=False)

            if len(pose) > 0:
                # distance calculations
                for human in humans:
                    for i in range(len(humans)):

                        try:
                            pos.getPositions(human,image)
                            if footCheckCount < 5:
                                if self.feetSquareCheck(human,image,pos):
                                    footCheckCount+=1


                            if footCheckCount ==5:
                                footCheckFailed = True
                                footCheckCount+=1

                            if footCheckFailed:
                                cv2.putText(image, "Feet were not Square", (5, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)



                            if kneeCheckCount < 5:
                                if self.kneeCheck(human,image,pos):
                                    kneeCheckCount+=1

                            if kneeCheckCount ==5:
                                kneeCheckCount+=1
                                kneeCheckFailed = True
                            if kneeCheckFailed:
                                cv2.putText(image, "Knees went too far forward", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)

                            if hipcheckCount <1:

                                if self.hipCheck(human,image,pos):
                                    hipcheckCount +=1
                                    hipCheckPassed = True
                            if hipCheckPassed:
                                    cv2.putText(image, "Hips went low enough", (5, 70), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, ),2)



                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break
            while False:
                break
        cam.release()

        cv2.destroyAllWindows()

    def kneeCheck(self,human,image,pos):

        dist = (abs(pos.xLeftKnee - pos.xLeftAnkle))
        dist2 = (abs(pos.xRightKnee - pos.xRightAnkle))
        if dist > 40 or dist2 > 40:
            return True
        return False
    def hipCheck(self,human,image,pos): # Return 1 if hipcheck passes
        if pos.yLeftKnee <= pos.yLeftHip:
            return True
        return False

    def feetSquareCheck(self,human,image,pos):
        dist = abs(pos.xLeftAnkle - pos.xRightAnkle)
        if dist > 5:
            return True
        return False












if __name__ == "__main__":
    ex = ex()
    ex.analyze()
