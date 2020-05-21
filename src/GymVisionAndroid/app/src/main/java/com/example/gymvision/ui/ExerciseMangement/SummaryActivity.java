package com.example.gymvision.ui.ExerciseMangement;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewManager;

import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.gymvision.R;
import com.example.gymvision.ui.HomeActivity;
import com.example.gymvision.utils.ExerciseUtils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.transferwise.sequencelayout.SequenceStep;

import static com.example.gymvision.camera.CameraActivity.EXERCISE_KEY;


public class SummaryActivity extends AppCompatActivity {

    DatabaseReference reference;
    History history;

    String exerciseName;
    SequenceStep check1,check2, check3,check4,  exercisename;
    LottieAnimationView success, fail;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_summary);
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        check1 = findViewById(R.id.check1);
        check2 = findViewById(R.id.check2);
        check3 = findViewById(R.id.check3);
        check4 = findViewById(R.id.check4);
        exercisename  = findViewById(R.id.exercisename);
        history = new History();
        reference = FirebaseDatabase.getInstance().getReference().child("History");
        success = findViewById(R.id.success1);
        fail = findViewById(R.id.failed1);


        success.setOnClickListener( v ->{
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    startActivity(homeIntent);
                }
                );
        fail.setOnClickListener( v ->{
                    Intent homeIntent = new Intent(this, HomeActivity.class);
                    startActivity(homeIntent);
                }
        );

        if (extras != null) {
            exerciseName = extras.getString(EXERCISE_KEY, "");
            if (!exerciseName.isEmpty()) {
                this.exerciseName = exerciseName;
                exercisename.setTitle(exerciseName);}

            switch (exerciseName) {
                case "Squat": {
                    ((ViewManager)check4.getParent()).removeView(check4);
                    exercisename.setSubtitle(R.string.squat_subtitle);

                    if (extras.getInt(ExerciseUtils.SQUAT_KNEE_BELOW_HIP) == 1 && extras.getInt(ExerciseUtils.SQUAT_FEETSQUARE) == 1 && extras.getInt(ExerciseUtils.SQUAT_KNEES_TOO_FAR_FORWARD ) ==1) {
                        success.setVisibility(View.VISIBLE);
                        check3.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.SQUAT_KNEE_BELOW_HIP) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle("Your hip went below your knees, which is correct and safe form");
                    } else {
                        check1.setTitle(R.string.not_passed_check);
                        check1.setSubtitle("Your hip did not go below your knees, try getting a little lower in your squat");
                    }

                    if (extras.getInt(ExerciseUtils.SQUAT_FEETSQUARE) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("Your feet were side-by-side during the squat, this is correct form");
                    } else {
                        check2.setTitle(R.string.not_passed_check);
                        check2.setSubtitle("Your feet were not side-by-side during the squat, make sure that you keep your feet aligned throughout the movement ");
                    }

                    if (extras.getInt(ExerciseUtils.SQUAT_KNEES_TOO_FAR_FORWARD) == 1) {
                        check3.setTitle(R.string.passed_check);
                        check3.setSubtitle("Your knees we're behind your toes the entire squat");
                    } else {
                        check3.setTitle(R.string.not_passed_check);
                        check3.setSubtitle("Your knees were too far forward during the squat, try and make sure your knees don't go past your toes for a more correct and safer stance");
                    }
                    break;
                }


                case "Shoulder Press": {
                    ((ViewManager)check3.getParent()).removeView(check3);
                    ((ViewManager)check4.getParent()).removeView(check4);

                    exercisename.setSubtitle(R.string.shoulder_press_subtitle);
                    if (extras.getInt(ExerciseUtils.SHOULDERPRESS_ELBOW_WRIST_CHECK) == 1 && extras.getInt(ExerciseUtils.SHOULDERPRESS_GRIP_WIDTH_CHECK) == 1 ) {
                        success.setVisibility(View.VISIBLE);
                        check2.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.SHOULDERPRESS_ELBOW_WRIST_CHECK) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle(R.string.shoulderpress_wristcheck);

                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle(R.string.shoulderpress_failedwristcheck);

                    }

                    if (extras.getInt(ExerciseUtils.SHOULDERPRESS_GRIP_WIDTH_CHECK) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("Grip width for shoulder press correct");

                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle("Shoulder press grip width incorrect, your grip should be slightly wider than your shoulder width.");

                    }
                    break;
                }



                case "Dead Lift": {
                    exercisename.setSubtitle(R.string.deadlift_subtitle);
                    ((ViewManager)check4.getParent()).removeView(check4);
                    Log.d("TooClose1", ""+extras.getInt(ExerciseUtils.GRIP_TOO_CLOSE));

                    if (extras.getInt(ExerciseUtils.GRIP_TOO_CLOSE) == 1 && extras.getInt(ExerciseUtils.GRIP_TOO_WIDE) == 1 && extras.getInt(ExerciseUtils.DEAD_LIFT_STANCE_CHECK)==1) {
                        success.setVisibility(View.VISIBLE);
                        check3.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.GRIP_TOO_WIDE) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle(R.string.deadliftwide);

                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle(R.string.deadlifttoowide);
                    }

                    if (extras.getInt(ExerciseUtils.GRIP_TOO_CLOSE) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle(R.string.deadliftclose);

                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle(R.string.deadliftooclose);

                    }

                   /* if (extras.getInt(ExerciseUtils.DEADLIFT_HIP_CHECK) == 1) {
                        check3.setTitle(R.string.passed_check);
                        check3.setSubtitle("Deadlift hip check passed");

                    } else {
                        check3.setTitle(R.string.check_failed);
                        check3.setSubtitle("Deadlift hip check not passed");

                    }*/

                    if (extras.getInt(ExerciseUtils.DEAD_LIFT_STANCE_CHECK) == 1) {
                        check3.setTitle(R.string.passed_check);
                        check3.setSubtitle("Deadlift stance is correct ");

                    } else {
                        check3.setTitle(R.string.check_failed);
                        check3.setSubtitle("Deadlift stance is incorrect, your feet should be around hip-width");

                    }
                    break;

                }

                case "Bench Press": {
                    ((ViewManager)check3.getParent()).removeView(check3);
                    ((ViewManager)check4.getParent()).removeView(check4);
                    exercisename.setSubtitle(R.string.benchpress_subtitle);
                    if (extras.getInt(ExerciseUtils.BENCHPRESS_BAR_CHECK) == 1 && extras.getInt(ExerciseUtils.BENCHPRESS_ELBOW_CHECK) == 1) {
                        success.setVisibility(View.VISIBLE);
                        check2.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.BENCHPRESS_BAR_CHECK) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle("You brought the bar to your chest, which is safe and good form.");
                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle("The bar was not brought low enough in the movement, you should bring the bar down to your chest");
                    }

                    if (extras.getInt(ExerciseUtils.BENCHPRESS_ELBOW_CHECK) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("Your elbows were low and close to your side, this is the safest form");
                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle("Elbows were too high for this movement, this causes strain on the shoulder, try lowering your elbow");

                    }

                    break;

                }

                case "Bent Over Row":{
                    ((ViewManager)check3.getParent()).removeView(check3);
                    ((ViewManager)check4.getParent()).removeView(check4);
                    exercisename.setSubtitle(R.string.bentOverRow_subtitle);
                    if (extras.getInt(ExerciseUtils.BENT_OVER_ROW_STANCE_CHECK) == 1 && extras.getInt(ExerciseUtils.BENT_OVER_ROW_GRIP_CHECK) == 1) {
                        success.setVisibility(View.VISIBLE);
                        check2.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }


                    if (extras.getInt(ExerciseUtils.BENT_OVER_ROW_STANCE_CHECK) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle("Bent over row stance was correct");
                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle("Bent over row stance was incorrect, you should keep your stance at hip width");
                    }

                    if (extras.getInt(ExerciseUtils.BENT_OVER_ROW_GRIP_CHECK) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("Bent over row grip was correct");
                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle("Bent over row grip was incorrect, your grip should be just over shoulder width apart");

                    }

                    break;

                }

                case "Lunge": {

                    exercisename.setSubtitle(R.string.lunge_subtitle);
                    if (extras.getInt(ExerciseUtils.LUNGE_RIGHT_KNEE_CHECK) == 1 && extras.getInt(ExerciseUtils.LUNGE_LEANING_FORWARD_CHECK) == 1 && extras.getInt(ExerciseUtils.LUNGE_KNEE_ALGINED_CHECK) == 1 && extras.getInt(ExerciseUtils.LUNGE_KNEE_FORWARD_CHECK) == 1) {
                        success.setVisibility(View.VISIBLE);
                        check4.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.LUNGE_RIGHT_KNEE_CHECK) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle("Lunge right knee went low enough for the lunge");
                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle("Lunge right should go low enough so that it is almost touching the floor.");
                    }

                    if (extras.getInt(ExerciseUtils.LUNGE_LEANING_FORWARD_CHECK) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("You back was straight and your shoulders were in line with your hips");
                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle("You back was not  straight and your shoulders were not  in line with your hips, try not leaning too far forward.");

                    }

                    if (extras.getInt(ExerciseUtils.LUNGE_KNEE_ALGINED_CHECK) == 1) {
                        check3.setTitle(R.string.passed_check);
                        check3.setSubtitle("Your hip went down to your knee height, this is correct depth.");
                    } else {
                        check3.setTitle(R.string.check_failed);
                        check3.setSubtitle("You didn't go lowe enough in the movement, try bringing your hip to knee height.");

                    }


                    if (extras.getInt(ExerciseUtils.LUNGE_KNEE_FORWARD_CHECK) == 1) {
                        check4.setTitle(R.string.passed_check);
                        check4.setSubtitle("Your left knee was in the correct position");
                    } else {
                        check4.setTitle(R.string.check_failed);
                        check4.setSubtitle("Your left knee should be behind the toes on your left foot.");
                    }

                    break;
                }
                case "Skull Crusher": {
                    ((ViewManager)check3.getParent()).removeView(check3);
                    ((ViewManager)check4.getParent()).removeView(check4);
                    exercisename.setSubtitle(R.string.skullCrushers_subtitle);
                    if (extras.getInt(ExerciseUtils.SKULLCRUSHER_ARM_DISTANCE_CHECK) == 1 && extras.getInt(ExerciseUtils.SKULLCRUSHER_ARM_PERPENDICULAR_CHECK) == 1) {
                        success.setVisibility(View.VISIBLE);
                        check2.setActive(true);
                    } else {
                        fail.setVisibility(View.VISIBLE);
                        exercisename.setActive(true);
                    }

                    if (extras.getInt(ExerciseUtils.SKULLCRUSHER_ARM_DISTANCE_CHECK) == 1) {
                        check1.setTitle(R.string.passed_check);
                        check1.setSubtitle("You brought the weights to the correct position.");
                    } else {
                        check1.setTitle(R.string.check_failed);
                        check1.setSubtitle("Make sure that you are bringing the weights to your forehead when performing the skull crusher");
                    }

                    if (extras.getInt(ExerciseUtils.SKULLCRUSHER_ARM_PERPENDICULAR_CHECK) == 1) {
                        check2.setTitle(R.string.passed_check);
                        check2.setSubtitle("Your forearms were perpendicular to the floor for the movement");
                    } else {
                        check2.setTitle(R.string.check_failed);
                        check2.setSubtitle("Make sure that your arms are perpendicular to the floor when performing the skull crusher exercise");

                    }



                    break;
                }


 /*
                case "Skull Crusher": {

                    break;

                }*/

                 }}
  }



}















