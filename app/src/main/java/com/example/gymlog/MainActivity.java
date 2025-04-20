package com.example.gymlog;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private GymLogRepository repository;

    public static final String TAG = "DAC_GYMLOG";
    String mExercise ="";
    double mWeight = 0;

    int loggedInUserId = -1;

    //TODO add login informqation

    int mReps = 0;
    //ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = ActivityMainBinding.inflate(getLayoutInflater());

        binding.logDisplayTextView.setMovementMethod(new ScrollingMovementMethod());

        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());
        updateDisplay();
        binding.logButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v){
               // Toast.makeText(MainActivity.this, "Button Clicked", Toast.LENGTH_SHORT).show();
                getInformationFromDisplay();
                insertGymLogRecord();
                updateDisplay();
            }
        });
        binding.exerciseInputEditText.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                updateDisplay();
            }
        });

    }


    private void insertGymLogRecord(){
        if(mExercise.isEmpty()){
            return;
        }

        GymLog log = new GymLog(mExercise, mWeight, mReps, loggedInUserId);
        repository.insertGymLog(log);
    }
    private void updateDisplay(){
        ArrayList<GymLog> allLogs = repository.getAllLogs();
        if(allLogs.isEmpty()){
            binding.logDisplayTextView.setText(R.string.nothing_to_show_time_to_hit_the_gym);
        }
        StringBuilder sb = new StringBuilder();
        for(GymLog log : allLogs){
            sb.append(log);
        }

//        String currentInfo = binding.logDisplayTextView.getText().toString();
//        Log.d(TAG, "Current Info: " + currentInfo);
//        String newDisplay = String.format(Locale.US,"Exercise:%s%nWeight:%.2f%nReps:%d%n+-+-+%n%s", mExercise, mWeight, mReps, currentInfo);
//        newDisplay += currentInfo;
        binding.logDisplayTextView.setText(sb.toString());
//        Log.i(TAG,repository.getAllLogs().toString());
    }

    private void getInformationFromDisplay(){
        mExercise = binding.exerciseInputEditText.getText().toString();
        try {
            mWeight = Double.parseDouble(binding.weightInputEditText.getText().toString());
        } catch (NumberFormatException e){
            Log.d(TAG, "Error Reading Value from Weight edit text.");
            //mWeight = 0;
        }

        try{
            mReps = Integer.parseInt(binding.repInputEditText.getText().toString());

        }
        catch (NumberFormatException e){
            Log.d(TAG, "Error Reading Value from Reps edit text.");
            //mReps = 0;
        }


    }

}