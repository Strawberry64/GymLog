package com.example.gymlog;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowInsetsController;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.LiveData;

import com.example.gymlog.database.GymLogRepository;
import com.example.gymlog.database.entities.GymLog;
import com.example.gymlog.database.entities.User;
import com.example.gymlog.databinding.ActivityLogInBinding;

public class LogInActivity extends AppCompatActivity {


    private ActivityLogInBinding binding;

    private GymLogRepository repository;

    //private User user = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLogInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = GymLogRepository.getRepository(getApplication());



        binding.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyUser();
//                if(!verifyUser()){
//                    Toast.makeText(LogInActivity.this, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//                } else{
//                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
//                    startActivity(intent);
//                }

            }
        });
    }

    private void verifyUser() {
        String username = binding.usernameLoginEditText.getText().toString();
        if(username.isEmpty()){
            toastMaker("Username may not be blank");
            return;
        }
        LiveData<User> userObserver = repository.getUserByUsername(username);
        //= repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if(user != null){
                String password = binding.passwordLoginEditText.getText().toString();
                if(password.equals(user.getPassword())){
//                    SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(MainActivity.SHARED_PREFERENCE_USERID_KEY, Context.MODE_PRIVATE);
//                    SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
//                    sharedPrefEditor.putInt(MainActivity.SHARED_PREFERENCE_USERID_VALUE, user.getId());
//                    sharedPrefEditor.apply();
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId()));

//                    Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getId());
//                    startActivity(intent);
                }
                else{
                    toastMaker("Invalid Password");
                    binding.passwordLoginEditText.setSelection(0);
                }
            }
            else {
                toastMaker(String.format("%s not a vaild username", username));
                binding.usernameLoginEditText.setSelection(0);
                //return;
            }

        });
//        if(user != null){
//            String password = binding.passwordLoginEditText.getText().toString();
//            if(password.equals(user.getPassword())){
//                return;
//            }
//            else{
//                toastMaker("Invalid Password");
//                return;
//            }
//        }
//        toastMaker(String.format("No %s found", username));
        //return false;
    }

    private void toastMaker(String message) {
        Toast.makeText(LogInActivity.this, message, Toast.LENGTH_SHORT).show();
    }

    static Intent loginIntentFactory(Context context) {
        return new Intent(context, LogInActivity.class);
    }
}