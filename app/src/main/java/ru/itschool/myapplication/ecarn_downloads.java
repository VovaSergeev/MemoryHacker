package ru.itschool.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import androidx.appcompat.app.AppCompatActivity;

import ru.itschool.myapplication.databinding.ActivityEcarnDowloadsBinding;

public class ecarn_downloads extends AppCompatActivity {

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();
    private ActivityEcarnDowloadsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEcarnDowloadsBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());


        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    progressStatus += 1;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            binding.progressBar.setProgress(progressStatus);
                        }
                    });
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                Intent intent = new Intent(ecarn_downloads.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }).start();
    }
}
