package com.example.gymvision.utils;

import android.graphics.PointF;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.Map;

import ai.fritz.vision.poseestimation.Keypoint;

public class ExerciseUtils {
    private static Integer counter = 0;


    public static final String SQUAT_KNEE_BELOW_HIP = "squat_knee_below_hip";
    public static final String SQUAT_FEETSQUARE = "feet_square";
    public static final String SQUAT_KNEES_TOO_FAR_FORWARD = "knees too far forward";

    public static final String SHOULDERPRESS_ELBOW_WRIST_CHECK = "wrist_elbow";
    public static final String SHOULDERPRESS_GRIP_WIDTH_CHECK = "grip width check";

    public static final String GRIP_TOO_WIDE = "grip_too_wide";
    public static final String GRIP_TOO_CLOSE = "grip_too_close";
    public static final String DEADLIFT_HIP_CHECK = "dead lift hip check";
    public static final String DEAD_LIFT_STANCE_CHECK = "deadlift stance check";


    public static final String BENCHPRESS_ELBOW_CHECK = "benchpress elbow check";
    public static final String BENCHPRESS_BAR_CHECK = "bench press bar check";
    public static final String BENT_OVER_ROW_GRIP_CHECK = "bent over row grip check";
    public static final String BENT_OVER_ROW_STANCE_CHECK = "bent over row stance check";

    public static final String LUNGE_KNEE_FORWARD_CHECK = "lunge knee forward check";
    public static final String LUNGE_RIGHT_KNEE_CHECK = "lunge right knee check";
    public static final String LUNGE_KNEE_ALGINED_CHECK = "lunge knee aligned check";
    public static final String LUNGE_LEANING_FORWARD_CHECK = "lunge leaning forward check";

    public static final String SKULLCRUSHER_ARM_PERPENDICULAR_CHECK = "skull crusher arm perpendicular check";
    public static final String SKULLCRUSHER_ARM_DISTANCE_CHECK = "skull crusher arm distance check";

    /*
    Squat Checks
     */
    @NonNull //TChecks that you are going low enough in your squat.
    public static Integer evaluateHipBelowKnee(@Nullable Keypoint leftKnee, @Nullable Keypoint leftHip) { //TODO ADD BOTH SIDE CHECKS
        if (leftKnee == null || leftHip == null) {
            if (leftKnee == null) {
                Log.d("Keypoint null", "Left Knee");
            }
            if (leftHip == null) {
                Log.d("Keypoint null", "Left Hip");
            }
            return 0;
        }
        PointF leftKneePos = leftKnee.getPosition();
        PointF hipPos = leftHip.getPosition();

        float kneeY = leftKneePos.y;
        float hipY = hipPos.y;

        return kneeY < hipY+10 ? 1 : 0;
    }


    @NonNull //Checks that your feet are aligned
    public static Integer evaluateFeetSquare(@Nullable Keypoint leftAnkle, @Nullable Keypoint rightAnkle) {

        PointF rightAnklepos = rightAnkle.getPosition();
        PointF leftAnklepos = leftAnkle.getPosition();

        float lanklex = leftAnklepos.x;
        float ranklex = rightAnklepos.x;

        float dist = Math.abs(lanklex - ranklex);

        if (dist < 10) {
            return 1;
        } else {

        return 0;}
    }






    @NonNull //Checks that your knees are not going past your toes in the squat
    public static Integer kneesTooFarForward(@Nullable Keypoint leftAnkle, @Nullable Keypoint rightAnkle,@Nullable Keypoint leftKnee, @Nullable Keypoint rightKnee) {
        PointF rightAnklepos = rightAnkle.getPosition();
        PointF leftAnklepos = leftAnkle.getPosition();
        PointF leftKneepos = leftKnee.getPosition();
        PointF rightKneepos = rightKnee.getPosition();

        float lanklex = leftAnklepos.x;
        float ranklex = rightAnklepos.x;
        float lKneex = leftKneepos.x;
        float rKneex = rightKneepos.x;

        float dist = Math.abs(lKneex - lanklex);
        float dist2 = Math.abs(rKneex - ranklex);

        if (dist < 20 || dist2 < 20) {
            return 1;
        }
        return 0;
    }

    /*
    Shoulderpress checks
     */

    @NonNull   // Checks that your elbows are in line with your wrists so that your forearm is vertical.
    public static Integer elbowWidthCheckShoulderPress(@Nullable Keypoint leftElbow, @Nullable Keypoint rightElbow, @Nullable Keypoint leftWrist, @Nullable Keypoint rightWrist,
                                                       @Nullable Keypoint leftShoulder, @Nullable Keypoint rightShoulder) {
        if (leftWrist == null || leftElbow == null || rightWrist == null || rightElbow == null) {
            if (leftWrist == null) {
            }
            if (leftElbow == null) {
            }
            if (rightElbow == null) {
            }
            if (rightWrist == null) {

            }
            return 0;
        }

        PointF leftElbowPos = leftElbow.getPosition();
        PointF rightElbowPos = rightElbow.getPosition();
        PointF rightWristPos = rightWrist.getPosition();
        PointF leftWristPos = leftWrist.getPosition();
        PointF rightShoulderPos = rightShoulder.getPosition();
        PointF leftShoulderPos = leftShoulder.getPosition();

        float leftElbowX = leftElbowPos.x;
        float rightElbowX = rightElbowPos.x;
        float rightElbowY = rightElbowPos.y;
        float leftElbowY = leftElbowPos.y;


        float rightWristX = rightWristPos.x;
        float leftWristX = leftWristPos.x;

        float rightShoulderY = rightShoulderPos.y;
        float leftShoulderY = leftShoulderPos.y;
        float boundary = rightWristX - 15;

        boolean check1 = Math.abs(leftElbowX - leftWristPos.x ) <15 ;
        boolean check2 = Math.abs(rightElbowX - rightWristPos.x) < 15;




        if (leftElbowY > leftShoulderY + 20 && rightElbowY > rightShoulderY + 20) {

            if(check1 && check2) {//TODO ADD BOUNDARY


                return 1;//good value
            } else {
                return 0;//bad value
            }
        }
        return 1;
    }

    @NonNull //Checks that your shoulder press grip is correct
    public static Integer shoulderPressGripWidthCheck( @Nullable Keypoint leftWrist, @Nullable Keypoint rightWrist,
                                                       @Nullable Keypoint leftShoulder, @Nullable Keypoint rightShoulder,@Nullable Keypoint leftElbow, @Nullable Keypoint rightElbow) { //TODO ATT STARTING POSITION CHECK

        PointF rightWristPos = rightWrist.getPosition();
        PointF leftWristPos = leftWrist.getPosition();
        PointF rightShoulderPos = rightShoulder.getPosition();
        PointF leftShoulderPos = leftShoulder.getPosition();
        PointF leftElbowPos = leftElbow.getPosition();
        PointF rightElbowPos = rightElbow.getPosition();


        float rightWristX = rightWristPos.x;
        float leftWristX = leftWristPos.x;

        float rightShoulderX = rightShoulderPos.x;
        float leftShoulderX = leftShoulderPos.x;


        float leftCheckBoundary = leftShoulderPos.x + 45;
        float rightCheckBoundary= rightShoulderPos.x - 45;
        Log.d("lelleftElbow", ""+leftElbowPos.y);
        Log.d("lelrightElbow", ""+rightElbowPos.y);
        Log.d("lelleftShoulder", ""+leftShoulderPos.y);
        Log.d("lerightShoulder", ""+rightShoulderPos.y);

        if (leftElbowPos.y > leftShoulderPos.y + 20 && rightElbowPos.y > rightShoulderPos.y + 20) {
            if(leftWristPos.x <= leftCheckBoundary && leftWristPos.x >= leftShoulderPos.x + 5 && rightWristPos.x >= rightCheckBoundary && rightWristPos.x <= rightShoulderPos.x -5){
                return 1;
            }else{return 0;}
        }
       return 1;
    }

    /*
    Deadlift checks
     */
    @NonNull  // Checks that your deadlift grip is close enough
    public static Integer deadliftGripTooWide(@Nullable Keypoint rightWrist, @Nullable Keypoint leftWrist,@Nullable Keypoint leftShoulder,@Nullable Keypoint rightShoulder) {
        PointF rightWristPosition = rightWrist.getPosition();
        PointF leftWristPosition = leftWrist.getPosition();
        PointF leftShoulderPosition = leftShoulder.getPosition();
        PointF rightShoulderPosition = rightShoulder.getPosition();

        if(leftWristPosition.x < leftShoulderPosition.x + 30 && rightWristPosition.x > rightShoulderPosition.x -30) {

            return 1;
        }
        return 0;
    }



    @NonNull //Checks that your Deadlift grip is wide enough
    public static Integer deadliftGripTooClose(@Nullable Keypoint rightWrist, @Nullable Keypoint leftWrist,@Nullable Keypoint leftShoulder,@Nullable Keypoint rightShoulder) {
        PointF rightWristPosition = rightWrist.getPosition();
        PointF leftWristPosition = leftWrist.getPosition();
        PointF leftShoulderPosition = leftShoulder.getPosition();
        PointF rightShoulderPosition = rightShoulder.getPosition();

        if(leftWristPosition.x > leftShoulderPosition.x - 10 && rightWristPosition.x < rightShoulderPosition.x + 10) {
            return 1;
        }
        return 0;
    }
  /*  //Side view JUST THIS
    @NonNull
    public static Integer deadliftHipCheck(@Nullable Keypoint leftHip, @Nullable Keypoint leftShoulder,@Nullable Keypoint leftKnee) {
        PointF leftHipPos = leftHip.getPosition();
        PointF leftShoulderPos = leftShoulder.getPosition();
        PointF leftKneePos = leftKnee.getPosition();

        float leftKneeY = leftKneePos.y;
        float leftShoulderY = leftShoulderPos.y;
        float leftHipY = leftHipPos.y;

        if(leftHipY < leftKneeY && leftHipY > leftShoulderY) {          //
            return 0;
        }
        return 0;
    }*/

    @NonNull //
    public static Integer deadliftStanceCheck(@Nullable Keypoint rightAnkle, @Nullable Keypoint leftAnkle,@Nullable Keypoint leftShoulder,@Nullable Keypoint rightShoulder
    ) {
        PointF leftShoulderPosition = leftShoulder.getPosition();
        PointF rightShoulderPosition = rightShoulder.getPosition();
        PointF rightAnklePosition= rightAnkle.getPosition();
        PointF leftAnklePosition = leftAnkle.getPosition();

        boolean tooWideCheck = leftAnklePosition.x > leftShoulderPosition.x+ 5 && rightAnklePosition.x < rightShoulderPosition.x-5;
        boolean tooCloseCheck = leftAnklePosition.x < leftShoulderPosition.x+ -5 && rightAnklePosition.x > rightShoulderPosition.x+5;
        Log.d("litShoulderLeft", ""+ leftShoulderPosition.x);
        Log.d("litShoulderRight", ""+ rightShoulderPosition.x);
        Log.d("litAnkleLeft", ""+ leftAnklePosition.x);
        Log.d("litAnkleRight", ""+ rightAnklePosition.x);
        if(!tooCloseCheck && !tooWideCheck){
            return 1;
        }
        return 0;

    }

   /*
    Bench checks
     */



   @NonNull // this checks that the elbows are lower than the shoulders to ensure that you are not putting strain on your shoulders
   public static Integer benchPressElbowLowCheck(@Nullable Keypoint rightElbow, @Nullable Keypoint leftElbow, @Nullable Keypoint leftShoulder, @Nullable Keypoint rightShoulder) {
       PointF rightElbowPosition = rightElbow.getPosition();
       PointF leftElbowPosition = leftElbow.getPosition();
       PointF leftShoulderPosition = leftShoulder.getPosition();
       PointF rightShoulderPosition = rightShoulder.getPosition();

       if (leftElbowPosition.y > leftShoulderPosition.y + 10 || rightElbowPosition.y > rightShoulderPosition.y + 10) {
           return 1;
       }
       return 0;

   }

    @NonNull // This checks that the bar is being brought close enough to the chest in the movement.
    public static Integer benchPressBringBarLow(@Nullable Keypoint rightWrist, @Nullable Keypoint leftWrist, @Nullable Keypoint leftShoulder, @Nullable Keypoint rightShoulder) {
        PointF rightWristPosition = rightWrist.getPosition();
        PointF leftWristPosition = leftWrist.getPosition();
        PointF leftShoulderPosition = leftShoulder.getPosition();
        PointF rightShoulderPosition = rightShoulder.getPosition();



        if(leftWristPosition.x >= leftShoulderPosition.x - 20){
            return 1;
        }
        return 0;
    }


     /*
    Bent-over Row checks
     */

     //
     @NonNull // This checks you are using the right grip for a barbell row (Medium grip)
     public static Integer rowGripCheck(@Nullable Keypoint rightWrist, @Nullable Keypoint leftWrist,@Nullable Keypoint leftKnee,@Nullable Keypoint rightKnee) {
         PointF rightWristPosition = rightWrist.getPosition();
         PointF leftWristPosition = leftWrist.getPosition();
         PointF leftKneePosition = leftKnee.getPosition();
         PointF rightKneePosition = rightKnee.getPosition();

         boolean check1 = leftWristPosition.x > leftKneePosition.x && leftWristPosition.x < leftKneePosition.x + 50;
         boolean check2 = rightWristPosition.x < rightKneePosition.x && rightWristPosition.x > rightKneePosition.x -50;
         if(check1 && check2){
             return 1;
         }

         return 0;

     }

    //
    @NonNull // Checks that your feet are in the correct stance for the bent over row.
    public static Integer rowStanceCheck(@Nullable Keypoint rightAnkle, @Nullable Keypoint leftAnkle,@Nullable Keypoint leftShoulder,@Nullable Keypoint rightShoulder,
                                         @Nullable Keypoint leftHip,@Nullable Keypoint rightHip) {
        PointF leftShoulderPosition = leftShoulder.getPosition();
        PointF rightShoulderPosition = rightShoulder.getPosition();
        PointF rightAnklePosition= rightAnkle.getPosition();
        PointF leftAnklePosition = leftAnkle.getPosition();
        PointF leftHipPos = leftHip.getPosition();
        PointF rightHipPos = rightHip.getPosition();


        boolean check1 = leftAnklePosition.x > leftHipPos.x  && rightAnklePosition.x < rightHipPos.x  ;
        /// boolean check2 = leftAnklePosition.x < leftShoulderPosition.x && rightAnklePosition.x  >rightShoulderPosition.x;
        if(check1 ){
            return 1;
        }

        return 0;

    }
     /*
     Lunges checks (this exercise should be done from the side with your left side facing the camera)
     */


     @NonNull // checks that the knees don't go past the toes should go with left knee forward

     public static Integer lungeKneeForwardCheck(@Nullable Keypoint leftKnee, @Nullable Keypoint leftAnkle
                                         ){
         PointF leftAnklePosition = leftAnkle.getPosition();
         PointF leftKneePosition = leftKnee.getPosition();
         float dist = Math.abs(leftKneePosition.x - leftAnklePosition.x);
         if(dist < 35){
             return 1;
         }
         return 0;
     }

    @NonNull // Checks that your right knee is going close enough to the ground

    public static Integer lungeRightKneeCheck(@Nullable Keypoint rightKnee, @Nullable Keypoint rightAnkle
    ){
        PointF rightAnklePosition = rightAnkle.getPosition();
        PointF rightKneePosition = rightKnee.getPosition();

        if(rightKneePosition.y > rightAnklePosition.y -25){
            return 1;
        }
        return 0;
    }

    @NonNull // Checks that the Left knee is at a similar height to hip when in the lunge position

    public static Integer kneeHip(@Nullable Keypoint leftKnee, @Nullable Keypoint leftHip
    ){
        PointF leftHipPosition = leftHip.getPosition();
        PointF leftKneePosition = leftKnee.getPosition();
        float dist = Math.abs(leftKneePosition.y - leftHipPosition.y);
        if(dist < 15){
            return 1;
        }
        return 0;
    }

    @NonNull
    // This checks that the user is not leaning too far forward in the lunge exercise.
    public static Integer lungeLeaningForwardCheck(@Nullable Keypoint leftKnee, @Nullable Keypoint nose
    ){
        PointF nosePosition = nose.getPosition();
        PointF leftKneePosition = leftKnee.getPosition();


        if(nosePosition.x  > leftKneePosition.x -  30){
            return 1;
        }
        return 0;
    }

    // skullcrusher checks

    @NonNull
    // TODO NUMBER OF TIEMS FAILED CHECK
    public static Integer armePerpendicularCheck(@Nullable Keypoint leftShoulder, @Nullable Keypoint leftElbow
    ){
        PointF shoulderPos = leftElbow.getPosition();
        PointF elbowPos = leftShoulder.getPosition();


        if(elbowPos.y  >= shoulderPos.y -  5 && elbowPos.y <= shoulderPos.y +5){
            return 1;
        }
        return 0;
    }

    public static Integer armDistanceCheck(@Nullable Keypoint leftWrist, @Nullable Keypoint nose
    ){
        PointF nosePos = nose.getPosition();
        PointF wristPos = leftWrist.getPosition();


        if(wristPos.x >= nosePos.x +5){
            return 1;
        }
        return 0;
    }


    public static Bundle buildScoresheetBundle(Map<String, Integer> exerciseScores) {

         //Squat results
        Integer squatKneeHip = exerciseScores.get(SQUAT_KNEE_BELOW_HIP);
        Integer squatFeetSquare = exerciseScores.get(SQUAT_FEETSQUARE);
        Integer squatKneesTooFarForward = exerciseScores.get(SQUAT_KNEES_TOO_FAR_FORWARD);


        //Shoulderpress results
        Integer shoulderPressElbowWrist = exerciseScores.get(SHOULDERPRESS_ELBOW_WRIST_CHECK);
        Integer shoulderPressGripWidth = exerciseScores.get(SHOULDERPRESS_GRIP_WIDTH_CHECK);


        //Deadlift results
        Integer deadLiftWide = exerciseScores.get(GRIP_TOO_WIDE);
        Integer deadLiftClose = exerciseScores.get(GRIP_TOO_CLOSE);
        Integer deadLiftHipCheck = exerciseScores.get(DEADLIFT_HIP_CHECK);
        Integer deadLiftStanceCheck = exerciseScores.get(DEAD_LIFT_STANCE_CHECK);



        //Bench press results
        Integer benchPressElbowCheck = exerciseScores.get(BENCHPRESS_ELBOW_CHECK);
        Integer benchPressBarCheck = exerciseScores.get(BENCHPRESS_BAR_CHECK);


        //Bent over row results
        Integer bentOverRowGripCheck = exerciseScores.get(BENT_OVER_ROW_GRIP_CHECK);
        Integer bentOverRowStanceCheck = exerciseScores.get(BENT_OVER_ROW_STANCE_CHECK);

        //Lunge results
        Integer lungeKneeForward = exerciseScores.get(LUNGE_KNEE_FORWARD_CHECK);
        Integer lungeLeaning = exerciseScores.get(LUNGE_LEANING_FORWARD_CHECK);
        Integer lungeRightKnee = exerciseScores.get(LUNGE_RIGHT_KNEE_CHECK);
        Integer lungeKneeAligned = exerciseScores.get(LUNGE_KNEE_ALGINED_CHECK);


        Bundle bundle = new Bundle();


        if (squatKneeHip != null) {
            bundle.putInt(SQUAT_KNEE_BELOW_HIP, squatKneeHip);
            bundle.putInt(SQUAT_FEETSQUARE, squatFeetSquare);
            bundle.putInt(SQUAT_KNEES_TOO_FAR_FORWARD, squatKneesTooFarForward);

            return bundle;
        }

        if(shoulderPressElbowWrist != null){
            bundle.putInt(SHOULDERPRESS_ELBOW_WRIST_CHECK, shoulderPressElbowWrist);
            bundle.putInt(SHOULDERPRESS_GRIP_WIDTH_CHECK, shoulderPressGripWidth );

            return bundle;
        }

        if(deadLiftClose != null ){

            bundle.putInt(GRIP_TOO_WIDE, deadLiftWide);
            bundle.putInt(GRIP_TOO_CLOSE, deadLiftClose);
            bundle.putInt(DEAD_LIFT_STANCE_CHECK, deadLiftStanceCheck);

            return bundle;
        }

        if(benchPressBarCheck != null){
            bundle.putInt(BENCHPRESS_BAR_CHECK, benchPressBarCheck);
            bundle.putInt(BENCHPRESS_ELBOW_CHECK, benchPressElbowCheck);

            return bundle;
        }

        if(bentOverRowGripCheck != null){
            bundle.putInt(BENT_OVER_ROW_GRIP_CHECK, bentOverRowGripCheck);
            bundle.putInt(BENT_OVER_ROW_STANCE_CHECK, bentOverRowStanceCheck);

            return bundle;
        }

        if(lungeKneeAligned != null){
            bundle.putInt(LUNGE_KNEE_FORWARD_CHECK, lungeKneeForward);
            bundle.putInt(LUNGE_LEANING_FORWARD_CHECK, lungeLeaning);
            bundle.putInt(LUNGE_RIGHT_KNEE_CHECK, lungeRightKnee);
            bundle.putInt(LUNGE_KNEE_ALGINED_CHECK, lungeKneeAligned);

            return bundle;
        }


        return null;
    }



}

