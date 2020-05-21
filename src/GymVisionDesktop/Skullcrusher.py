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


        #cam = cv2.VideoCapture('C:/Users/Eamonn/Programming/2020-ca400-template-repo/src/GymVisionDesktop/Videos/SkullCrushers.mp4')
        cam = cv2.VideoCapture(args.camera)
        ret_val, image = cam.read()
        orange_color = (0, 140, 255)

        while True:
            ret_val, image = cam.read()
            image = cv2.rotate(image, cv2.ROTATE_90_CLOCKWISE)

            humans = e.inference(image, resize_to_default=(w > 0 and h > 0), upsample_size=args.resize_out_ratio)
            pose = humans
            image = TfPoseEstimator.draw_humans(image, humans, imgcopy=False)

            if len(pose) > 0:
                # distance calculations
                for human in humans:
                    for i in range(len(humans)):

                        try:
                            pos.getPositions(human,image)
                            self.upperArmPerpendicular(human,image,pos)
                            self.highEnough(human,image,pos)



                        except Exception as exs:
                            print(exs)
                            pass

            cv2.imshow('tf-pose-estimation result', image)

            if cv2.waitKey(1) == 27:
                break

        cv2.destroyAllWindows()

    def upperArmPerpendicular(self,human,image,pos):
        self.widthBoundaryLeft1 = pos.yRightShoulder + 5
        self.widthBoundaryLeft2 = pos.yRightShoulder - 5
        if (pos.yRightElbow >= self.widthBoundaryLeft2 and pos.yRightElbow <= self.widthBoundaryLeft1):
            print("upper arms perpendicular to floor")
        else:
            print("no")

    def highEnough(self,human,image,pos):
        if pos.yRightWrist  <= pos.yNose:
            print("Good")








if __name__ == "__main__":
    ex = ex()
    ex.analyze()
