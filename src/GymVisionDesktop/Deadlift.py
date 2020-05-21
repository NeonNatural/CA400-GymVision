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
        checklist = []
        wideFeedback = "Stance is too wide"
        stanceWideCheckFailed = 0
        stanceCloseCheckFailed = 0
        gripTooWideCheckFailed = 0
        gripTooCloseCheckFailed = 0


        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/OverheadGood.mp4')
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
                            if stanceWideCheckFailed <5:
                                if self.stanceTooWide(human,image,pos) == True:
                                    stanceWideCheckFailed +=1
                            if stanceWideCheckFailed ==5:
                                cv2.putText(image, "Stance too wide", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)






                            print(pos.xLeftAnkle,pos.xLeftShoulder -25,pos.xRightAnkle,pos.xRightShoulder +25)
                            if stanceCloseCheckFailed <5:
                                if self.stanceTooClose(human,image,pos) == True:
                                    stanceCloseCheckFailed+=1
                            if stanceCloseCheckFailed ==5:
                                cv2.putText(image, "Stance too close", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)
                            if stanceCloseCheckFailed !=5 and stanceWideCheckFailed !=5:
                                cv2.putText(image, "Stance good!", (5, 90), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)



                            if gripTooWideCheckFailed <5:
                                if self.armsGripTooWide(human,image,pos) == True:
                                    gripTooWideCheckFailed +=1
                            if gripTooWideCheckFailed ==5:
                                cv2.putText(image, "Grip too wide", (5, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)



                            if gripTooCloseCheckFailed <5:
                                if self.armGripsTooClose(human,image,pos) == True:
                                    gripTooCloseCheckFailed +=1
                            if gripTooCloseCheckFailed ==5:
                                cv2.putText(image, "Grip too close", (5, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 0, 255),2)

                            if gripTooWideCheckFailed !=5 and gripTooCloseCheckFailed !=5:
                                cv2.putText(image, "Grip good!", (5, 50), cv2.FONT_HERSHEY_SIMPLEX, 0.5,(0, 255, 0),2)












                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()
    def stanceTooWide(self,human,image,pos):
        leftBoundary1 = pos.xLeftShoulder + 5
        rightBounadry1 = pos.xRightShoulder - 5
        if pos.xLeftAnkle > leftBoundary1 and pos.xRightAnkle < rightBounadry1:
            return True
        return False
    def stanceTooClose(self,human,image,pos):
        self.leftBoundary1 = pos.xLeftShoulder - 5
        self.rightBounadry1 = pos.xRightShoulder + 5
        if pos.xLeftAnkle < self.leftBoundary1 and pos.xRightAnkle > self.rightBounadry1:
            return True
        return False
    def armsGripTooWide(self,human,image,pos):
        self.leftBoundary1 = pos.xLeftShoulder + 20
        self.rightBounadry1 = pos.xRightShoulder -20
        if pos.xLeftWrist > self.leftBoundary1 and pos.xRightWrist < self.rightBounadry1:
            return True
        return False

    def armGripsTooClose(self,human,image,pos):
        self.leftBoundary1 = pos.xLeftShoulder - 20
        self.rightBounadry1 = pos.xRightShoulder +20
        if pos.xLeftWrist < self.leftBoundary1 and pos.xRightWrist > self.rightBounadry1:
            return True
        return False


# #Side view Checks
#     def hipShoulderHeightCheck(self,human,image,pos): # When bending over you should not lower your back so far that it becomes lower than your hip
#         correct = True
#         try:
#             check1 = pos.yRightHip < pos.yRightShoulder
#             check2 = pos.yRightHip < pos.yLeftShoulder
#         except:
#             pass
#         check3 = pos.yLeftHip < pos.yLeftShoulder
#         check4 = pos.yLeftHip < pos.yRightShoulder
#         if check1 or check2 or check3 or check4:
#             correct = False
#
#         return correct
#     def hipKneeCheck(self,human,image,pos): #Your hips should not drop as low as a squat in DeadLift.
#         check1 = pos.yLeftKnee <= pos.yLeftHip
#         check2  = pos.yLeftKnee <= pos.yRightHip
#         check3 = pos.yRightKnee <= pos.yLeftHip
#         check4 = pos.yRightKnee <= pos.yRightHip
#         if check1 or check2 or check3 or check4:
#             return False
#         return True











if __name__ == "__main__":
    ex = ex()
    ex.analyze()
