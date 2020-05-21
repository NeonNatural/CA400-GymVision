package com.example.gymvision.ui.ExerciseMangement;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.gymvision.R;
import com.example.gymvision.camera.InferenceActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import static com.example.gymvision.camera.CameraActivity.EXERCISE_KEY;
import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;


public class exerFragment extends Fragment
{

    RecyclerView recyclerView;

    List<Integer> imageList = new ArrayList<>();
    List<String> titleList = new ArrayList<>();

    List<HashMap<String,Object>> exerciseList = new ArrayList<>();
    ExerciseViewAdapter exerciseViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View view = inflater.inflate(R.layout.fragment_exercises, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);

        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        imageList.add(R.mipmap.squat);
        imageList.add(R.mipmap.shoulderpress);//shoulder press
        imageList.add(R.mipmap.deadlift);//deadlift
        imageList.add(R.mipmap.test);//benchpress
        imageList.add(R.mipmap.row);//bentover row
        imageList.add(R.mipmap.lunge);//lunge
        imageList.add(R.mipmap.skullcrush);//skull crushers


        titleList.add("Squat");
        titleList.add("Shoulder Press");
        titleList.add("Dead Lift");
        titleList.add("Bench Press");
        titleList.add("Bent Over Row");
        titleList.add("Lunge");
        titleList.add("Skull Crusher");

        for(int i=0;i<imageList.size();i++){

            HashMap<String,Object> map = new HashMap<>();
            map.put("Image",imageList.get(i));
            map.put("Title",titleList.get(i));
            exerciseList.add(map);
        }

        exerciseViewAdapter = new ExerciseViewAdapter(getContext(), exerciseList);
        recyclerView.setAdapter(exerciseViewAdapter);

        return view;
    }
    public class ExerciseViewAdapter extends RecyclerView.Adapter<ExerciseViewAdapter.ViewHolder>
    {
        List<HashMap<String,Object>> exerciseList;

        private Context context;

        public ExerciseViewAdapter(Context context, List<HashMap<String, Object>> fList) {
            this.exerciseList = fList;
            this.context = context;

        }

        public class ViewHolder extends RecyclerView.ViewHolder
        {
            ImageView exerciseImage;
            TextView exerciseName;

            public ViewHolder(View itemView)
            {
                super(itemView);
                this.exerciseImage = itemView.findViewById(R.id.image_exercise);
                this.exerciseName = itemView.findViewById(R.id.text_exercisename);

            }
        }

        @NonNull
        @Override
        public ExerciseViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
        {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(ExerciseViewAdapter.ViewHolder holder, final int position)
        {
            HashMap<String,Object> map = exerciseList.get(position);

            holder.exerciseImage.setImageResource((Integer) map.get("Image"));
            holder.exerciseName.setText(String.valueOf(map.get("Title")));

            holder.itemView.setOnClickListener(v -> {
                        Bundle bundle = new Bundle();

                        switch(String.valueOf(map.get("Title"))){

                            case "Squat": {

                                bundle.putString(EXERCISE_KEY, "Squat");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }
                            case "Shoulder Press": {
                                bundle.putString(EXERCISE_KEY, "Shoulder Press");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }

                            case "Dead Lift": {
                                bundle.putString(EXERCISE_KEY, "Dead Lift");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }
                            case "Bench Press": {
                                bundle.putString(EXERCISE_KEY, "Bench Press");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }

                            case "Bent Over Row": {
                                bundle.putString(EXERCISE_KEY, "Bent Over Row");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }

                            case "Lunge": {
                                bundle.putString(EXERCISE_KEY, "Lunge");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }

                            case "Skull Crusher": {
                                bundle.putString(EXERCISE_KEY, "Skull Crusher");
                                Intent intent = new Intent(context, InferenceActivity.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;

                            }

                        }
                    }
            );
        }
        @Override
        public int getItemCount()
        {
            return exerciseList.size();
        }
    }
}




