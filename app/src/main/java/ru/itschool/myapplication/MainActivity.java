package ru.itschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import ru.itschool.myapplication.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private   int skinIndex = -1;



    MediaPlayer mySong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());




        setContentView(binding.getRoot());


        mySong = MediaPlayer.create(MainActivity.this,R.raw.music);
        mySong.setLooping(true);
        mySong.start();

        skinIndex= getIntent().getIntExtra("selectedSkinIndex", skinIndex);
        System.out.println(skinIndex);


        binding.shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, Shop.class);

                startActivity(intent);
                mySong.stop();
            }

        });


        binding.inventory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, InventoryActivity.class);

                startActivity(intent);
                mySong.stop();
            }

        });
        binding.record.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, Profile.class);
            startActivity(intent);
            mySong.stop();
        });



        binding.playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mySong.stop();


                String time = binding.time.getText().toString();



                Intent intent = new Intent(MainActivity.this, Dufficulty.class);





                if (time.isEmpty()) {
                    Snackbar.make(v, "Введите время", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                intent.putExtra("time",time);

                intent.putExtra("selectedSkinIndex", skinIndex);


                startActivity(intent);
            }

        });
        binding.leaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


    }

    }

