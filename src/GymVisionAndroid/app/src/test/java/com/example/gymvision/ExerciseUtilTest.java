package com.example.gymvision;

import android.graphics.PointF;

import com.example.gymvision.mocks.FakePointF;
import com.example.gymvision.utils.ExerciseUtils;

import org.junit.Assert;
import org.junit.Test;

import ai.fritz.vision.poseestimation.Keypoint;

public class ExerciseUtilTest {

    @Test
    public void bringBarLow_isSuccess() {
        PointF leftWristPosition = new FakePointF(160.0F, 0.0F);
        PointF leftShoulderPosition = new FakePointF(140.0F, 0.0F);
        PointF rightWristPosition = new FakePointF(100.0F, 0.0F);
        PointF rightShoulderPosition = new FakePointF(140.0F, 0.0F);

        Keypoint leftWrist = new Keypoint(1, "left_wrist", leftWristPosition, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "left_shoulder", leftShoulderPosition, 100.00F);
        Keypoint rightWrist = new Keypoint(1, "right_wrist", rightWristPosition, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "right_shoulder", rightShoulderPosition, 100.00F);

        int score = ExerciseUtils.benchPressBringBarLow(rightWrist, leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(1, score);
    }

    @Test
    public void bringBarLow_isFailed() {
        PointF leftWristPosition = new FakePointF(30.0F, 0.0F);
        PointF leftShoulderPosition = new FakePointF(60.0F, 0.0F);
        PointF rightWristPosition = new FakePointF(120.0F, 0.0F);
        PointF rightShoulderPosition = new FakePointF(60.0F, 0.0F);

        Keypoint leftWrist = new Keypoint(1, "left_wrist", leftWristPosition, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "left_shoulder", leftShoulderPosition, 100.00F);
        Keypoint rightWrist = new Keypoint(3, "right_wrist", rightWristPosition, 100.00F);
        Keypoint rightShoulder = new Keypoint(4, "right_shoulder", rightShoulderPosition, 100.00F);

        int score = ExerciseUtils.benchPressBringBarLow(rightWrist, leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(0, score);
    }

    @Test
    public void evaluateHipBelowKnee_isSuccess(){
        PointF leftKneePosition = new FakePointF(0.0F, 100.0F);
        PointF leftHipPosition = new FakePointF(0.0F, 120.0F);


        Keypoint leftKnee = new Keypoint(1, "left_knee", leftKneePosition, 100.00F);
        Keypoint leftHip = new Keypoint(2, "left_hip", leftHipPosition, 100.00F);


        int score = ExerciseUtils.evaluateHipBelowKnee(leftKnee, leftHip);

        Assert.assertEquals(1, score);
    }

    @Test
    public void evaluateHipBelowKnee_isFailed(){
        PointF leftKneePosition = new FakePointF(0.0F, 150.0F);
        PointF leftHipPosition = new FakePointF(0.0F, 120.0F);


        Keypoint leftKnee = new Keypoint(1, "left_knee", leftKneePosition, 100.00F);
        Keypoint leftHip = new Keypoint(2, "left_hip", leftHipPosition, 100.00F);


        int score = ExerciseUtils.evaluateHipBelowKnee(leftKnee, leftHip);

        Assert.assertEquals(0, score);
    }

    @Test
    public void evaluateFeetSquare_isSuccess(){
        PointF leftAnklePosition = new FakePointF(105.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(100.0F, 0.0F);


        Keypoint leftAnkle = new Keypoint(1, "left_ankle", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "right_ankle", rightAnklePosition, 100.00F);


        int score = ExerciseUtils.evaluateFeetSquare(leftAnkle, rightAnkle);

        Assert.assertEquals(1, score);
    }

    @Test
    public void evaluateFeetSquare_isFailed(){
        PointF leftAnklePosition = new FakePointF(180.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(100.0F, 0.0F);


        Keypoint leftAnkle = new Keypoint(1, "left_ankle", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "right_ankle", rightAnklePosition, 100.00F);


        int score = ExerciseUtils.evaluateFeetSquare(leftAnkle, rightAnkle);

        Assert.assertEquals(0, score);
    }


    @Test
    public void evaluateKneesTooFarForward_isSuccess(){
        PointF leftAnklePosition = new FakePointF(180.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(100.0F, 0.0F);
        PointF leftKneePosition = new FakePointF(100.0F, 0.0F);
        PointF rightKneePosition = new FakePointF(100.0F, 0.0F);


        Keypoint leftAnkle = new Keypoint(1, "left_ankle", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "right_ankle", rightAnklePosition, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "left_knee", leftKneePosition, 100.00F);
        Keypoint rightKnee = new Keypoint(2, "right_knee", rightKneePosition, 100.00F);


        int score = ExerciseUtils.kneesTooFarForward(leftAnkle, rightAnkle, leftKnee, rightKnee);

        Assert.assertEquals(1, score);
    }

   @Test
    public void evaluateKneesTooFarForward_isFailed(){
        PointF leftAnklePosition = new FakePointF(10.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(20.0F, 0.0F);
        PointF leftKneePosition = new FakePointF(170.0F, 0.0F);
        PointF rightKneePosition = new FakePointF(150.0F, 0.0F);


        Keypoint leftAnkle = new Keypoint(1, "left_ankle", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "right_ankle", rightAnklePosition, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "left_knee", leftKneePosition, 100.00F);
        Keypoint rightKnee = new Keypoint(2, "right_knee", rightKneePosition, 100.00F);


        int score = ExerciseUtils.kneesTooFarForward(leftAnkle, rightAnkle, leftKnee, rightKnee);

        Assert.assertEquals(0, score);
    }

    @Test
    public void elbowWidthCheck_isSuccess(){
        PointF leftElbowPos = new FakePointF(0.0F, 21.0F);
        PointF rightElboowPos = new FakePointF(0.0F, 21.0F);
        PointF leftWristPos = new FakePointF(0.0F, 0.0F);
        PointF rightWristPos = new FakePointF(0.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(0.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(0.0F, 0.0F);


        Keypoint leftElbow = new Keypoint(1, "", leftElbowPos, 100.00F);
        Keypoint rightElbow = new Keypoint(2, "", rightElboowPos, 100.00F);
        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.elbowWidthCheckShoulderPress(leftElbow, rightElbow, leftWrist, rightWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(1, score);
    }

    @Test
    public void elbowWidthCheck_isFailed(){
        PointF leftElbowPos = new FakePointF(10.0F, 21.0F);
        PointF rightElboowPos = new FakePointF(20.0F, 21.0F);
        PointF leftWristPos = new FakePointF(170.0F, 0.0F);
        PointF rightWristPos = new FakePointF(150.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(150.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(150.0F, 0.0F);


        Keypoint leftElbow = new Keypoint(1, "", leftElbowPos, 100.00F);
        Keypoint rightElbow = new Keypoint(2, "", rightElboowPos, 100.00F);
        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.elbowWidthCheckShoulderPress(leftElbow, rightElbow, leftWrist, rightWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(0, score);
    }


    @Test
    public void deadLiftGripTooWide_isSuccess(){
        PointF rightWristPos = new FakePointF(0.0F, 0.0F);
        PointF leftWristPos = new FakePointF(0.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(0.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(0.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.deadliftGripTooWide(rightWrist,leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(1, score);
    }

    @Test
    public void deadLiftGripTooWide_isFailed(){
        PointF rightWristPos = new FakePointF(10.0F, 0.0F);
        PointF leftWristPos = new FakePointF(20.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(150.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(150.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.deadliftGripTooWide(rightWrist,leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(0, score);
    }


    @Test
    public void deadLiftGripTooClose_isSuccess(){
        PointF rightWristPos = new FakePointF(0.0F, 0.0F);
        PointF leftWristPos = new FakePointF(0.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(0.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(0.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.deadliftGripTooWide(rightWrist,leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(1, score);
    }

    @Test
    public void deadLiftGripTooClose_isFailed(){
        PointF rightWristPos = new FakePointF(10.0F, 0.0F);
        PointF leftWristPos = new FakePointF(20.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(150.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(150.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);


        int score = ExerciseUtils.deadliftGripTooWide(rightWrist,leftWrist, leftShoulder, rightShoulder);

        Assert.assertEquals(0, score);
    }
/*    //ToDO come back to these
    @Test
    public void deadLifHipCheck_isSuccess(){
        PointF leftShoulderPos = new FakePointF(10.0F, 0.0F);
        PointF leftHipPos = new FakePointF(20.0F, 20.0F);
        PointF leftKneePos = new FakePointF(150.0F, 50.0F);



        Keypoint leftHip = new Keypoint(2, "", leftHipPos, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "", leftKneePos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);



        int score = ExerciseUtils.deadliftHipCheck(leftHip,leftShoulder, leftKnee);

        Assert.assertEquals(0, score);
    }*/

/*    @Test
    public void deadLifHipCheck_isFailed(){
        PointF leftShoulderPos = new FakePointF(10.0F, 0.0F);
        PointF leftHipPos = new FakePointF(20.0F, 0.0F);
        PointF leftKneePos = new FakePointF(150.0F, 0.0F);



        Keypoint leftHip = new Keypoint(2, "", leftHipPos, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "", leftKneePos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);



        int score = ExerciseUtils.deadliftHipCheck(leftHip,leftShoulder, leftKnee);

        Assert.assertEquals(0, score);
    }*/


    @Test
    public void benchPressElbowCheck_isSuccess(){
        PointF leftElbowPos = new FakePointF(10.0F, 50.0F);
        PointF rightElboowPos = new FakePointF(20.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(150.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(150.0F, 0.0F);



        Keypoint leftElbow = new Keypoint(1, "", leftElbowPos, 100.00F);
        Keypoint rightElbow = new Keypoint(2, "", rightElboowPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);



        int score = ExerciseUtils.benchPressElbowLowCheck(rightElbow,leftElbow, leftShoulder, rightShoulder);

        Assert.assertEquals(1, score);
    }

    @Test
    public void benchPressElbowCheck_isFailed(){
        PointF leftElbowPos = new FakePointF(10.0F, 0.0F);
        PointF rightElboowPos = new FakePointF(20.0F, 0.0F);
        PointF leftShoulderPos = new FakePointF(150.0F, 0.0F);
        PointF rightShoulderPos = new FakePointF(150.0F, 0.0F);



        Keypoint leftElbow = new Keypoint(1, "", leftElbowPos, 100.00F);
        Keypoint rightElbow = new Keypoint(2, "", rightElboowPos, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPos, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPos, 100.00F);



        int score = ExerciseUtils.benchPressElbowLowCheck(rightElbow,leftElbow, leftShoulder, rightShoulder);

        Assert.assertEquals(0, score);
    }



    @Test
    public void rowGripCheck_isSuccess(){
        PointF rightWristPos = new FakePointF(100.0F, 0.0F);
        PointF leftWristPos = new FakePointF(101.0F, 0.0F);
        PointF leftKneePos = new FakePointF(100.0F, 0.0F);
        PointF rightKneePos = new FakePointF(101.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "", leftKneePos, 100.00F);
        Keypoint rightKnee = new Keypoint(2, "", rightKneePos, 100.00F);


        int score = ExerciseUtils.rowGripCheck(rightWrist,leftWrist, leftKnee, rightKnee);

        Assert.assertEquals(1, score);
    }

    @Test
    public void rowGripCheck_isFailed(){
        PointF rightWristPos = new FakePointF(10.0F, 0.0F);
        PointF leftWristPos = new FakePointF(20.0F, 0.0F);
        PointF leftKneePos = new FakePointF(150.0F, 0.0F);
        PointF rightKneePos = new FakePointF(150.0F, 0.0F);


        Keypoint leftWrist = new Keypoint(2, "", leftWristPos, 100.00F);
        Keypoint rightWrist = new Keypoint(2, "", rightWristPos, 100.00F);
        Keypoint leftKnee = new Keypoint(2, "", leftKneePos, 100.00F);
        Keypoint rightKnee = new Keypoint(2, "", rightKneePos, 100.00F);


        int score = ExerciseUtils.rowGripCheck(rightWrist,leftWrist, leftKnee, rightKnee);

        Assert.assertEquals(0, score);
    }

    @Test
    public void rowStanceCheck_isSuccess(){
        PointF leftAnklePosition = new FakePointF(101.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(103.0F, 0.0F);
        PointF leftShoulderPosition = new FakePointF(102.0F, 0.0F);
        PointF rightShoulderPosition = new FakePointF(102.0F, 0.0F);
        PointF leftHipPosition = new FakePointF(100.0F, 120.0F);
        PointF rightHipPosition = new FakePointF(105.0F, 120.0F);

        Keypoint leftAnkle = new Keypoint(2, "", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "", rightAnklePosition, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPosition, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPosition, 100.00F);
        Keypoint leftHip = new Keypoint(2, "", leftHipPosition, 100.00F);
        Keypoint rightHip = new Keypoint(2, "", rightHipPosition, 100.00F);


        int score = ExerciseUtils.rowStanceCheck(rightAnkle,leftAnkle, leftShoulder, rightShoulder, leftHip, rightHip);

        Assert.assertEquals(1, score);
    }

    @Test
    public void rowStanceCheck_isFailed(){
        PointF leftAnklePosition = new FakePointF(180.0F, 0.0F);
        PointF rightAnklePosition = new FakePointF(100.0F, 0.0F);
        PointF leftShoulderPosition = new FakePointF(140.0F, 0.0F);
        PointF rightShoulderPosition = new FakePointF(140.0F, 0.0F);
        PointF leftHipPosition = new FakePointF(0.0F, 120.0F);
        PointF rightHipPosition = new FakePointF(0.0F, 120.0F);

        Keypoint leftAnkle = new Keypoint(2, "", leftAnklePosition, 100.00F);
        Keypoint rightAnkle = new Keypoint(2, "", rightAnklePosition, 100.00F);
        Keypoint leftShoulder = new Keypoint(2, "", leftShoulderPosition, 100.00F);
        Keypoint rightShoulder = new Keypoint(2, "", rightShoulderPosition, 100.00F);
        Keypoint leftHip = new Keypoint(2, "", leftHipPosition, 100.00F);
        Keypoint rightHip = new Keypoint(2, "", rightHipPosition, 100.00F);


        int score = ExerciseUtils.rowStanceCheck(rightAnkle,leftAnkle, leftShoulder, rightShoulder, leftHip, rightHip);

        Assert.assertEquals(0, score);
    }





























}
