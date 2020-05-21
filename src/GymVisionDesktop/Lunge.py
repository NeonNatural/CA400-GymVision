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
        leftKneeLowCheckPassed = 0
        kneeCheckFailed = 0
        kneeHipCheckPassed = 0
        forwardLungeCheckFailed = 0


        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/LungeGood.mp4')
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

                            self.leftKneeLow(human,image,pos)

                            self.kneeCheck(human,image,pos)

                            self.kneeHip(human,image,pos)

                            self.forwardLunge(human,image,pos)


                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()
    def kneeCheck(self,human,image,pos): # effected by model sometimes

        if pos.xRightKnee < pos.xRightKnee -50:
            print("Keep your left knee behind your toe")
            return False
        return True

    def leftKneeLow(self,human,image,pos): #
        heightBoundary = pos.yLeftAnkle - 20
        if pos.yLeftKnee > heightBoundary:
            return True
        return False

    def kneeHip(self,human,image,pos):
        kneeDist = abs(pos.yRightKnee - pos.yRightHip)
        if(kneeDist < 30):
            return True
        return False

    def forwardLunge(self,human,image,pos):
        if(pos.xNose > pos.yRightKnee):
            return False
        return True




#Checks work but model sucks







if __name__ == "__main__":
    ex = ex()
    ex.analyze()
