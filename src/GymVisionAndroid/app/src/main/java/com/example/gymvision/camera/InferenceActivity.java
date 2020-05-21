package com.example.gymvision.camera;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.util.Size;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.RequiresApi;
import com.example.gymvision.R;
import com.example.gymvision.ui.ExerciseMangement.SummaryActivity;
import com.example.gymvision.utils.ExerciseUtils;
import com.example.gymvision.utils.KeypointUtils;
import com.example.gymvision.utils.SkeletonUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


//fritz libraries
import ai.fritz.core.Fritz;
import ai.fritz.vision.FritzVision;
import ai.fritz.vision.FritzVisionImage;
import ai.fritz.vision.FritzVisionModels;
import ai.fritz.vision.FritzVisionOrientation;
import ai.fritz.vision.ImageRotation;
import ai.fritz.vision.ModelVariant;
import ai.fritz.vision.filter.OneEuroFilterMethod;
import ai.fritz.vision.poseestimation.FritzVisionPosePredictor;
import ai.fritz.vision.poseestimation.FritzVisionPosePredictorOptions;
import ai.fritz.vision.poseestimation.FritzVisionPoseResult;
import ai.fritz.vision.poseestimation.Keypoint;
import ai.fritz.vision.poseestimation.Pose;
import ai.fritz.vision.poseestimation.PoseOnDeviceModel;


public class InferenceActivity extends LiveActivity implements TextToSpeech.OnInitListener {

    FritzVisionPosePredictor predictor;
    FritzVisionImage image;
    FritzVisionPoseResult result;


    //TextToSpeech Que
    private String queue;
    private TextToSpeech textToSpeech;
    private boolean init;


    Integer count = 0;


    Integer counter_one = 0;
    Integer counter_two = 0;
    Integer counter_three = 0;
    Integer counter_four = 0;


    TextView checker, checker2,checker3, checker4, timerSet, startStop;


    private static final String API_KEY = "beecdfa66ecc4caeaaab03f0dbd64299";

    private Map<String, Integer> exerciseScores = new HashMap<>();

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checker = findViewById(R.id.exer_check_1);
        checker2 = findViewById(R.id.exer_check_2);
        checker3 = findViewById(R.id.exer_check_3);
        checker4 = findViewById(R.id.exer_check_4);
        timerSet = findViewById(R.id.timerSet);
        startStop = findViewById(R.id.startStop);

        timer = findViewById(R.id.timer);

        title = findViewById(R.id.title);
        //btnStart = findViewById(R.id.timerButton);


        //text to speech

        textToSpeech = new TextToSpeech(this, this /*listener*/);
        textToSpeech.setOnUtteranceProgressListener(progressListener);
        textToSpeech.setLanguage(Locale.UK);




        //timer
        timer.setOnClickListener(v -> {
            if (!running) {
                startStop.setVisibility(View.INVISIBLE);
                //btnStart.setText("Stop!");
                new CountDownTimer(30000, 1000) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                       // timer.setText("" + millisUntilFinished / 1000);


                        timer.setCurrentValues(millisUntilFinished / 1000);
                        timerSet.setText(""+millisUntilFinished / 1000);

                    }

                    @Override
                    public void onFinish() {
                        if (running) {
                            Intent intent = new Intent(getApplicationContext(), SummaryActivity.class);
                            Bundle bundle = ExerciseUtils.buildScoresheetBundle(exerciseScores);

                            if (bundle == null) {
                                Bundle newBundle = new Bundle();
                                newBundle.putString(EXERCISE_KEY, exerciseName);

                                intent.putExtras(newBundle);
                            } else {
                                bundle.putString(EXERCISE_KEY, exerciseName);

                                intent.putExtras(bundle);
                            }
                            startActivity(intent);
                            }
                        }

                }.start();
            } else {

                Log.d("Frames", ""+count);
                Intent intent = new Intent(this, SummaryActivity.class);
                Bundle bundle = ExerciseUtils.buildScoresheetBundle(exerciseScores);

                if (bundle == null) {
                    Bundle newBundle = new Bundle();
                    newBundle.putString(EXERCISE_KEY, exerciseName);

                    intent.putExtras(newBundle);
                } else {
                    bundle.putString(EXERCISE_KEY, exerciseName);

                    intent.putExtras(bundle);
                }
                startActivity(intent);
            }

            running = !running;
        });
        timer.setCurrentValues(0);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            String exerciseName = extras.getString(EXERCISE_KEY, "");


            if (!exerciseName.isEmpty()) {
                this.exerciseName = exerciseName;
                title.setText(exerciseName);


                //Toast.makeText(getApplicationContext(), exerciseName, Toast.LENGTH_SHORT).show();
            }
        }
    }


    public void tts(String text) {


        if (!init) {
            queue = text;
            return;
        }

        queue = null;

        setTtsListener();
        HashMap<String, String> map = new HashMap<>();
        map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "utteranceId");
        textToSpeech.speak(text, TextToSpeech.QUEUE_ADD, map);
    }


    private void setTtsListener() {

    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            init = true;
            textToSpeech.setLanguage(Locale.UK);

            if (queue != null) {
                tts(queue);
            }
        }


    }


    private abstract class runnable implements Runnable { //Queues text to speech so it wont overlap
    }


    private UtteranceProgressListener progressListener = new UtteranceProgressListener() {
        @Override
        public void onStart(String utteranceId) {
        }

        @Override
        public void onError(String utteranceId) {
        }
        @Override
        public void onDone(String utteranceId) {

            new Thread() {
                public void run() {
                    InferenceActivity.this.runOnUiThread(new runnable() {
                        public void run() {
                        }
                    });
                }
            }.start();

        }
    };


    @Override
    protected void initializeFritz() {
        Fritz.configure(this, API_KEY);
    }

    @Override
    protected void setupPredictor() {
        PoseOnDeviceModel poseEstimationOnDeviceModel = FritzVisionModels.getHumanPoseEstimationOnDeviceModel(ModelVariant.ACCURATE);
        predictor = FritzVision.PoseEstimation.getPredictor(poseEstimationOnDeviceModel);

        FritzVisionPosePredictorOptions options = new FritzVisionPosePredictorOptions();
        options.minPoseThreshold = 0.2f;
        options.minPartThreshold = .5f;

        predictor = FritzVision.PoseEstimation.getPredictor(poseEstimationOnDeviceModel, options);

        FritzVisionPosePredictorOptions posePredictorOptions = new FritzVisionPosePredictorOptions();
        posePredictorOptions.smoothingOptions = new OneEuroFilterMethod(0.2, 0.01, 0.6);
        /*
        Mincutoff - Minimum frequency cutoff. Lower values will decrease jitter but increase lag.
        Beta - Higher values of beta will help reduce lag, but may increase jitter.
        derivateCutoff - Max derivative value allowed. Increasing will allow more sudden movements.
        */
    }

    @Override
    protected void setupImageForPrediction(Image image) {
        ImageRotation imageRotation = FritzVisionOrientation.getImageRotationFromCamera(this, cameraid);
        this.image = FritzVisionImage.fromMediaImage(image, imageRotation);
    }

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void runInference() {

        result = predictor.predict(image);
        List<Pose> poses = result.getPoses(); // Pose per person in image

        for (Pose pose : poses) {
            Map<String, Keypoint> keypointMap = KeypointUtils.keypointsToMap(pose.getKeypoints());

            switch (exerciseName) {
                case "Squat": {

                    Integer hibBelowKnee = ExerciseUtils.evaluateHipBelowKnee(
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.LEFT_HIP)
                    );


                    Integer kneesTooFarForwardResult = ExerciseUtils.kneesTooFarForward(
                            keypointMap.get(SkeletonUtils.LEFT_ANKLE),
                            keypointMap.get(SkeletonUtils.RIGHT_ANKLE),
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.RIGHT_KNEE)
                    );

                    Integer feetSquareResult = ExerciseUtils.evaluateFeetSquare(keypointMap.get(SkeletonUtils.LEFT_ANKLE), keypointMap.get(SkeletonUtils.RIGHT_ANKLE));

                    Integer squat = exerciseScores.get(ExerciseUtils.SQUAT_KNEE_BELOW_HIP);
                    Integer squat2 = exerciseScores.get(ExerciseUtils.SQUAT_FEETSQUARE);
                    Integer squat3 = exerciseScores.get(ExerciseUtils.SQUAT_KNEES_TOO_FAR_FORWARD);

                    if (squat == null || squat < 1) { //Only need to check this happens once
                        exerciseScores.put(ExerciseUtils.SQUAT_KNEE_BELOW_HIP, hibBelowKnee);

                        if (hibBelowKnee == 1) {
                            this.runOnUiThread(() -> {
                                checker.setText("Hip low enough"); // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Hip is low enough");


                                //textToSpeech.speak("check passed", TextToSpeech.QUEUE_FLUSH, null);
                            });

                        }
                    }



                    if (squat2 == null ||  counter_one < 5) {

                        exerciseScores.put(ExerciseUtils.SQUAT_FEETSQUARE, feetSquareResult);

                        if(feetSquareResult == 0){
                            counter_one++; } // count all the occurences of feet too wide

                        if (counter_one == 5) {

                            this.runOnUiThread(() -> {
                                checker2.setText("Feet not aligned");  // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your feet need to be side by side.");

                            });
                        }
                    }

                    if (squat3 == null || counter_two < 3) {
                        exerciseScores.put(ExerciseUtils.SQUAT_KNEES_TOO_FAR_FORWARD, kneesTooFarForwardResult);

                        if(kneesTooFarForwardResult == 0){
                            counter_two ++; //count all occurences of knees going too far forward

                        }


                        if (counter_two == 3) {
                            this.runOnUiThread(() -> {
                                checker3.setText("Knees too forward"); // Sets the visual feedback on the screen
                                checker3.setVisibility(View.VISIBLE);

                                tts("Bring your knees back, they should be behind your toes");

                            });
                        }
                    }
                    break;
                }

                case "Shoulder Press": {
                    Integer elbowWidthCheck = ExerciseUtils.elbowWidthCheckShoulderPress(keypointMap.get(SkeletonUtils.LEFT_ELBOW), keypointMap.get(SkeletonUtils.RIGHT_ELBOW),
                            keypointMap.get(SkeletonUtils.LEFT_WRIST), keypointMap.get(SkeletonUtils.RIGHT_WRIST), keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER));

                    Integer gripWidthCheck = ExerciseUtils.shoulderPressGripWidthCheck(
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.RIGHT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER),
                            keypointMap.get(SkeletonUtils.LEFT_ELBOW),
                            keypointMap.get(SkeletonUtils.RIGHT_ELBOW));

                    Integer shoulderPress1 = exerciseScores.get(ExerciseUtils.SHOULDERPRESS_ELBOW_WRIST_CHECK);
                    Integer gripWidth = exerciseScores.get(ExerciseUtils.SHOULDERPRESS_GRIP_WIDTH_CHECK);


                    if (shoulderPress1 == null || counter_one < 5) {
                        exerciseScores.put(ExerciseUtils.SHOULDERPRESS_ELBOW_WRIST_CHECK, elbowWidthCheck);

                        if(elbowWidthCheck == 0 ){ // count all bad occurences
                            counter_one ++;

                        }

                        if (counter_one == 5) {
                            this.runOnUiThread(() -> {
                                checker.setText("Forearm not vertical"); // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);
                               tts("Make sure your forearm is vertical");

                            });
                        }
                    }

                    if (gripWidth == null || counter_two < 5) {
                        exerciseScores.put(ExerciseUtils.SHOULDERPRESS_GRIP_WIDTH_CHECK, gripWidthCheck);

                        if(gripWidthCheck == 0){
                            counter_two ++ ;
                        }
                        if (counter_two == 5) {
                            this.runOnUiThread(() -> {
                                checker2.setText("Grip too wide"); // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);
                                tts("Your grip should be just outside shoulder width apart");

                            });
                        }
                    }


                    break;
                }

                case "Dead Lift": {

                    Integer gripTooWide = ExerciseUtils.deadliftGripTooWide(keypointMap.get(SkeletonUtils.RIGHT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER)
                    );

                    Integer gripTooClose = ExerciseUtils.deadliftGripTooClose(keypointMap.get(SkeletonUtils.RIGHT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER)
                    );

                    /*Integer deadLiftHipCheck = ExerciseUtils.deadliftHipCheck(
                            keypointMap.get(SkeletonUtils.LEFT_HIP),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.LEFT_KNEE)
                    );*/

                    Integer deadliftStanceCheck = ExerciseUtils.deadliftStanceCheck(
                            keypointMap.get(SkeletonUtils.RIGHT_ANKLE),
                            keypointMap.get(SkeletonUtils.LEFT_ANKLE),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER)
                    );

                    Integer gripTooWideResult = exerciseScores.get(ExerciseUtils.GRIP_TOO_WIDE);
                    Integer gripTooCloseResult = exerciseScores.get(ExerciseUtils.GRIP_TOO_CLOSE);
                    Integer hipCheckResult = exerciseScores.get(ExerciseUtils.DEADLIFT_HIP_CHECK);
                    Integer stanceCheckResult = exerciseScores.get(ExerciseUtils.DEAD_LIFT_STANCE_CHECK);



                    if (gripTooWideResult == null || counter_one < 5) {
                        exerciseScores.put(ExerciseUtils.GRIP_TOO_WIDE, gripTooWide);

                        if(gripTooWide == 0){
                            counter_one++;
                        }


                        if (counter_one == 5) {
                            this.runOnUiThread(() -> {
                                checker.setText("Grip too wide");  // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Bring your grip are closer"); // Plays audio message of the feed back
                            });
                        }
                }


                    if (gripTooCloseResult == null || counter_two < 5) {
                        exerciseScores.put(ExerciseUtils.GRIP_TOO_CLOSE, gripTooClose);
                        Log.d("TooClose", ""+gripTooClose);

                        if(gripTooClose == 0){
                            counter_two ++;
                        }

                        if (counter_two == 5) {
                            this.runOnUiThread(() -> {
                                checker2.setText("Grip too narrow");  // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your grip needs to be a little wider");  // Plays audio message of the feed back
                            });
                        }
                    }

                  /*  if (hipCheckResult == null || hipCheckResult < 1) {
                        exerciseScores.put(ExerciseUtils.DEADLIFT_HIP_CHECK, deadLiftHipCheck);

                        if (deadLiftHipCheck == 1) {
                            this.runOnUiThread(() -> {
                                checker3.setText("Check passed");
                                checker3.setVisibility(View.VISIBLE);

                                tts("Hip in the correct position for Dead Lift");
                            });
                        }
                    }*/

                    if (stanceCheckResult == null || counter_three < 5) {
                        exerciseScores.put(ExerciseUtils.DEAD_LIFT_STANCE_CHECK, deadliftStanceCheck);
                        if(deadliftStanceCheck == 0){
                            counter_three ++;
                        }
                        if (counter_three == 7) {
                            this.runOnUiThread(() -> {
                                checker3.setText("Incorrect stance");  // Sets the visual feedback on the screen
                                checker3.setVisibility(View.VISIBLE);

                                tts("Make sure to use a hip-width wide stance"); // Plays audio message of the feed back
                            });
                        }
                    }
                    break;
                }

                case "Bench Press": {

                    Integer elbowCheck = ExerciseUtils.benchPressElbowLowCheck(
                            keypointMap.get(SkeletonUtils.RIGHT_ELBOW),
                            keypointMap.get(SkeletonUtils.LEFT_ELBOW),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER)
                    );

                    Integer barCheck = ExerciseUtils.benchPressBringBarLow(
                            keypointMap.get(SkeletonUtils.RIGHT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER)
                    );

                    Integer elbowCheckResult = exerciseScores.get(ExerciseUtils.BENCHPRESS_ELBOW_CHECK);
                    Integer barCheckResult = exerciseScores.get(ExerciseUtils.BENCHPRESS_BAR_CHECK);

                    if (elbowCheckResult == null || counter_one <2 /*|| counter_two < 5*/) {
                        exerciseScores.put(ExerciseUtils.BENCHPRESS_ELBOW_CHECK, elbowCheck);
                        if(elbowCheck == 0 ){
                            counter_one ++;
                        }
                        /*if(elbowCheck == 1 ){
                            counter_two ++;
                        }*/
                        if (counter_one == 2) {//negative check
                            this.runOnUiThread(() -> {
                                checker.setText("Elbows too high"); // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Try lowering your elbows"); // Plays audio message of the feed back
                            });
                        }
                        if (counter_two == 5) {//positive check
                            this.runOnUiThread(() -> {
                                checker.setText("Elbows correct"); // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Your elbows are in the correct position, continue to hold this form"); // Plays audio message of the feed back
                            });
                        }


                    }

                    if (barCheckResult == null || counter_three < 1) {
                        exerciseScores.put(ExerciseUtils.BENCHPRESS_BAR_CHECK, barCheck);
                        if(barCheck == 1) {
                            counter_three++;
                        }

                        if (counter_three == 1) {
                            this.runOnUiThread(() -> {
                                checker2.setText("Bar brought low"); // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("You brought the bar low enough!"); // Plays audio message of the feed back
                            });
                        }
                    }

                    break;
                }

                case "Bent Over Row": {

                    Integer rowGrip = ExerciseUtils.rowGripCheck(
                            keypointMap.get(SkeletonUtils.RIGHT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.RIGHT_KNEE)
                    );

                    Integer rowStance = ExerciseUtils.rowStanceCheck(
                            keypointMap.get(SkeletonUtils.RIGHT_ANKLE),
                            keypointMap.get(SkeletonUtils.LEFT_ANKLE),
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.RIGHT_SHOULDER),
                            keypointMap.get(SkeletonUtils.LEFT_HIP),
                            keypointMap.get(SkeletonUtils.RIGHT_HIP)
                    );


                    Integer rowGripResult = exerciseScores.get(ExerciseUtils.BENT_OVER_ROW_GRIP_CHECK);
                    Integer rowStanceResult = exerciseScores.get(ExerciseUtils.BENT_OVER_ROW_STANCE_CHECK);


                    if (rowGripResult == null || counter_one < 5 || counter_two < 5) {
                        exerciseScores.put(ExerciseUtils.BENT_OVER_ROW_GRIP_CHECK, rowGrip);
                        if(rowGrip == 0){
                            counter_two ++;
                        } else if(rowGrip == 1){
                            counter_one ++;
                        }
                        if (counter_one == 5) {
                            this.runOnUiThread(() -> {
                                counter_one ++;
                                checker.setText("Grip is correct");  // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Your grip for the barbell is correct, continue to hold this form"); // Plays audio message of the feed back
                            });
                        }

                        if (counter_two == 5) {
                            counter_two ++;
                            this.runOnUiThread(() -> {
                                checker.setText("Grip not correct");  // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Your grip for the barbell row is incorrect, you need to hold at a medium grip"); // Plays audio message of the feed back
                            });
                        }
                    }

                    if (rowStanceResult == null || counter_three < 5 || counter_four < 5)  {
                        exerciseScores.put(ExerciseUtils.BENT_OVER_ROW_STANCE_CHECK, rowStance);

                        if(rowStance == 0){
                            counter_four ++;
                        } else if(rowStance == 1){
                            counter_three ++;



                        }
                        if (counter_three == 5) {
                            counter_three ++;
                            this.runOnUiThread(() -> {
                                checker2.setText("Stance is correct");  // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your row stance is correct, continue to hold this form"); // Plays audio message of the feed back
                            });
                        }

                        if (counter_four == 5) {
                            counter_four ++;
                            this.runOnUiThread(() -> {
                                checker2.setText("Stance is incorrect");   // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your stance should be shoulder width apart"); // Plays audio message of the feed back
                            });
                        }
                    }

                    break;
                }

                case "Lunge": {

                    Integer lungeKneeForwrdCheck = ExerciseUtils.lungeKneeForwardCheck(
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.LEFT_ANKLE)
                    );

                    Integer lungeRightKneeCheck = ExerciseUtils.lungeRightKneeCheck(
                            keypointMap.get(SkeletonUtils.RIGHT_KNEE),
                            keypointMap.get(SkeletonUtils.RIGHT_ANKLE)

                    );

                    Integer lungeKneeAlignedWithHipCheck = ExerciseUtils.kneeHip(
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.LEFT_HIP)

                    );

                    Integer lungeLeaningForwardCheck = ExerciseUtils.lungeLeaningForwardCheck(
                            keypointMap.get(SkeletonUtils.LEFT_KNEE),
                            keypointMap.get(SkeletonUtils.NOSE)
                    );


                    Integer kneeForwardResult = exerciseScores.get(ExerciseUtils.LUNGE_KNEE_FORWARD_CHECK);
                    Integer rightKneeResult = exerciseScores.get(ExerciseUtils.LUNGE_RIGHT_KNEE_CHECK);
                    Integer kneeAlignedResult = exerciseScores.get(ExerciseUtils.LUNGE_KNEE_ALGINED_CHECK);
                    Integer leaningForwardResult = exerciseScores.get(ExerciseUtils.LUNGE_LEANING_FORWARD_CHECK);


                    if (kneeForwardResult == null || counter_one < 5) {
                        exerciseScores.put(ExerciseUtils.LUNGE_KNEE_FORWARD_CHECK, lungeKneeForwrdCheck);

                        if(lungeKneeForwrdCheck == 0){
                            counter_one ++;
                        }

                        if (counter_one ==5) {
                            this.runOnUiThread(() -> {
                                checker.setText("Knee far forward");  // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Bring your front knee behind your toes"); // Plays audio message of the feed back
                            });
                        }
                    }

                    if (rightKneeResult == null || counter_two < 2) {
                        exerciseScores.put(ExerciseUtils.LUNGE_RIGHT_KNEE_CHECK, lungeRightKneeCheck);
                        if(lungeRightKneeCheck == 1){
                            counter_two ++;
                        }
                        if (counter_two == 2) {
                            this.runOnUiThread(() -> {
                                checker2.setText("Knee low enough");  // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your right knee is in the correct position"); // Plays audio message of the feed back
                            });
                        }
                    }

                    if (kneeAlignedResult == null || kneeAlignedResult < 1) {
                        exerciseScores.put(ExerciseUtils.LUNGE_KNEE_ALGINED_CHECK, lungeKneeAlignedWithHipCheck);

                        if (lungeKneeAlignedWithHipCheck == 1) {
                            this.runOnUiThread(() -> {
                                checker3.setText("Lunge low enough");  // Sets the visual feedback on the screen
                                checker3.setVisibility(View.VISIBLE);

                                tts("Lunge depth low enough"); // Plays audio message of the feed back
                            });
                        }
                    }

                    if (leaningForwardResult == null || counter_four < 5) {
                        exerciseScores.put(ExerciseUtils.LUNGE_LEANING_FORWARD_CHECK, lungeLeaningForwardCheck);

                        if(lungeLeaningForwardCheck == 0){
                            counter_four ++;
                        }

                        if (counter_four == 5) {
                            this.runOnUiThread(() -> {
                                checker4.setText("Leaning far forward"); // Sets the visual feedback on the screen
                                checker4.setVisibility(View.VISIBLE);

                                tts("You are leaning too far forward you need to lean back"); // Plays audio message of the feed back
                            });
                        }
                    }



                    break;
                }
                case "Skull Crusher": {

                    Integer armPerpendicularCheck = ExerciseUtils.armePerpendicularCheck(
                            keypointMap.get(SkeletonUtils.LEFT_SHOULDER),
                            keypointMap.get(SkeletonUtils.LEFT_ELBOW)


                    );

                    Integer armDistanceCheck = ExerciseUtils.armDistanceCheck(
                            keypointMap.get(SkeletonUtils.LEFT_WRIST),
                            keypointMap.get(SkeletonUtils.NOSE)


                    );

                    Integer armPerpendicularResult = exerciseScores.get(ExerciseUtils.SKULLCRUSHER_ARM_PERPENDICULAR_CHECK);
                    Integer armDistanceResult = exerciseScores.get(ExerciseUtils.SKULLCRUSHER_ARM_DISTANCE_CHECK);

                    if (armPerpendicularResult == null || armPerpendicularResult <1) {
                        exerciseScores.put(ExerciseUtils.SKULLCRUSHER_ARM_PERPENDICULAR_CHECK, armPerpendicularCheck);


                        if (armPerpendicularCheck == 1) {
                            this.runOnUiThread(() -> {
                                checker.setText("Arm floor perpendicular"); // Sets the visual feedback on the screen
                                checker.setVisibility(View.VISIBLE);

                                tts("Your upper arm is perpendicular to the floor"); // Plays audio message of the feed back
                            });
                        }

                    }

                    if (armDistanceResult == null || armDistanceResult < 1) {
                        exerciseScores.put(ExerciseUtils.SKULLCRUSHER_ARM_DISTANCE_CHECK, armDistanceCheck);


                        if (armDistanceCheck == 1) {
                            this.runOnUiThread(() -> {
                                checker2.setText("Arm distance correct"); // Sets the visual feedback on the screen
                                checker2.setVisibility(View.VISIBLE);

                                tts("Your arms are extending past to your forehead"); // Plays audio message of the feed back
                            });
                        }
                    }

                    break;
                }

                default: {
                    Toast.makeText(this, "NO EXERCISE NAMED", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    protected void onDestroy() {

        //resetting the counters here
        counter_one = 0;
        counter_two = 0;
        counter_three = 0;
        counter_four = 0;

        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }


    @Override
    protected void showResult(Canvas canvas, Size cameraSize) {

        if (result != null) {
            List<Pose> poses = result.getPoses();

            for (Pose pose : poses) {
                pose.draw(canvas);
            }
        }
    }
}


