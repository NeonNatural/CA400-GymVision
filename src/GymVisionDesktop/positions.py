class position():
    def __init__(self):
        return
    def getPositions(self,human,image):
        try:

            leftAnkle = human.body_parts[13]
            self.yLeftAnkle = leftAnkle.y * image.shape[0]
            self.xLeftAnkle = leftAnkle.x * image.shape[1]
        except:
            pass
        try:
            rightAnkle = human.body_parts[10]
            self.yRightAnkle = rightAnkle.y * image.shape[0]
            self.xRightAnkle = rightAnkle.x * image.shape[1]
        except:
            pass
        try:
            leftKnee = human.body_parts[12]
            self.xLeftKnee = leftKnee.x * image.shape[1]
            self.yLeftKnee = leftKnee.y * image.shape[0]
        except:
            pass
        try:
            rightKnee = human.body_parts[9]
            self.xRightKnee = rightKnee.x * image.shape[1]
            self.yRightKnee = rightKnee.y * image.shape[0]
        except:
            pass
        try:
            leftHip = human.body_parts[11]
            self.xLeftHip = leftHip.x * image.shape[1]
            self.yLeftHip = leftHip.y * image.shape[0]
        except:
            pass
        try:
            rightHip = human.body_parts[8]
            self.xRightHip = rightHip.x * image.shape[1]
            self.yRightHip = rightHip.y * image.shape[0]
        except:
            pass
        try:
            leftShoulder = human.body_parts[5]
            self.xLeftShoulder = leftShoulder.x * image.shape[1]
            self.yLeftShoulder = leftShoulder.y * image.shape[0]
        except:
            pass
        try:
            rightShoulder = human.body_parts[2]
            self.xRightShoulder = rightShoulder.x * image.shape[1]
            self.yRightShoulder = rightShoulder.y * image.shape[0]
        except:
            pass
        try:
            leftWrist = human.body_parts[7]
            self.xLeftWrist = leftWrist.x * image.shape[1]
            self.yLeftWrist = leftWrist.y * image.shape[0]
        except:
            pass
        try:
            rightWrist = human.body_parts[4]
            self.xRightWrist = rightWrist.x * image.shape[1]
            self.yRightWrist = rightWrist.y * image.shape[0]
        except:
            pass
        try:
            Neck = human.body_parts[1]
            self.xNeck = Neck.x * image.shape[1]
            self.yNeck = Neck.y * image.shape[0]
        except:
            pass
        try:
            leftElbow = human.body_parts[6]
            self.xLeftElbow = leftElbow.x * image.shape[1]
            self.yLeftElbow = leftElbow.y * image.shape[0]

        except:
            pass
        try:
            rightElbow = human.body_parts[3]
            self.xRightElbow = rightElbow.x * image.shape[1]
            self.yRightElbow = rightElbow.y * image.shape[0]
        except:
            pass

        try:
            Nose = human.body_parts[0]
            self.xNose = Nose.x * image.shape[1]
            self.yNose = Nose.y * image.shape[0]
        except:
            pass
