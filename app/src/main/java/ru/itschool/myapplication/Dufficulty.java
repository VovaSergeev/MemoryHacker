package ru.itschool.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import ru.itschool.myapplication.databinding.ActivityDifficultyBinding;

public class Dufficulty extends AppCompatActivity {
    private ActivityDifficultyBinding binding;
    private String difficulty = "Easy";
    private  int skinIndex;

    private String time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDifficultyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        time = getIntent().getStringExtra("time");
        skinIndex= getIntent().getIntExtra("selectedSkinIndex", skinIndex);
        System.out.println(time+skinIndex);


        binding.easyImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Easy";
                Intent intent = new Intent(Dufficulty.this, GameActivity.class);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("time",time);
                intent.putExtra("selectedSkinIndex", skinIndex);
                startActivity(intent);
            }
        });

        binding.mediumImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                difficulty = "Medium";
                Intent intent = new Intent(Dufficulty.this, GameActivity.class);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("time",time);
                intent.putExtra("selectedSkinIndex", skinIndex);
                startActivity(intent);
            }
        });

        binding.hardImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Hard";
                Intent intent = new Intent(Dufficulty.this, GameActivity.class);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("time",time);
                intent.putExtra("selectedSkinIndex", skinIndex);
                startActivity(intent);
            }
        });

        binding.insaneImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                difficulty = "Insane";
                Intent intent = new Intent(Dufficulty.this, GameActivity.class);
                intent.putExtra("difficulty", difficulty);
                intent.putExtra("time",time);
                intent.putExtra("selectedSkinIndex", skinIndex);
                startActivity(intent);
            }
        });


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dufficulty.this, MainActivity.class);
                startActivity(intent);

            }
        });
    }
}