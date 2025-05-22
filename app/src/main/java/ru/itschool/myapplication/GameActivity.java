package ru.itschool.myapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.annotation.SuppressLint;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import android.view.View;

import android.widget.GridLayout;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import com.google.android.material.snackbar.Snackbar;
import ru.itschool.myapplication.databinding.ActivityGameBinding;
public class GameActivity extends AppCompatActivity {
    private ActivityGameBinding binding;
    MediaPlayer mySong;
    int galleryCount = 0;
    int cameraCount = 0;
    int manageCount = 0;
    int shareCount = 0;
    int callCount = 0;
    int compassCount = 0;

    int number1, number2, number3, number4, number5, number6;
    private String difficulty;
    private String time;
    private int skinIndex;
    private List<ImageView> imageViews;
    private List<Integer> imageResources;
    private boolean isMemoryPhase = true;
    private CountDownTimer memoryTimer;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityGameBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mySong = MediaPlayer.create(GameActivity.this, R.raw.music1);
        mySong.setLooping(true);
        mySong.start();

        time = getIntent().getStringExtra("time");
        skinIndex = getIntent().getIntExtra("selectedSkinIndex", skinIndex);
        difficulty = getIntent().getStringExtra("difficulty");

        imageResources = new ArrayList<>();


        if (skinIndex == -1) {
            imageResources.add(android.R.drawable.ic_menu_gallery);
            imageResources.add(android.R.drawable.ic_menu_camera);
            imageResources.add(android.R.drawable.ic_menu_manage);
            imageResources.add(android.R.drawable.ic_menu_share);
            imageResources.add(android.R.drawable.ic_menu_call);
            imageResources.add(android.R.drawable.ic_menu_compass);
        } else if (skinIndex == 0) {
            imageResources.add(R.drawable.apple);
            imageResources.add(R.drawable.arbuz);
            imageResources.add(R.drawable.banana);
            imageResources.add(R.drawable.avocado);
            imageResources.add(R.drawable.limon);
            imageResources.add(R.drawable.orange);
            binding.countGallery.setImageResource(R.drawable.apple);
            binding.countCamera.setImageResource(R.drawable.arbuz);
            binding.countManage.setImageResource(R.drawable.banana);
            binding.countShare.setImageResource(R.drawable.avocado);
            binding.countCall.setImageResource(R.drawable.limon);
            binding.countCompass.setImageResource(R.drawable.orange);

        } else if (skinIndex == 1) {
            imageResources.add(R.drawable.eren);
            imageResources.add(R.drawable.ervin);
            imageResources.add(R.drawable.levi);
            imageResources.add(R.drawable.mikasa);
            imageResources.add(R.drawable.eni);
            imageResources.add(R.drawable.armin);
            binding.countGallery.setImageResource(R.drawable.eren);
            binding.countCamera.setImageResource(R.drawable.ervin);
            binding.countManage.setImageResource(R.drawable.levi);
            binding.countShare.setImageResource(R.drawable.mikasa);
            binding.countCall.setImageResource(R.drawable.eni);
            binding.countCompass.setImageResource(R.drawable.armin);
        } else if (skinIndex == 2) {
            imageResources.add(R.drawable.mrbist);
            imageResources.add(R.drawable.mark_rober);
            imageResources.add(R.drawable.a4);
            imageResources.add(R.drawable.piudipay);
            imageResources.add(R.drawable.dream);
            imageResources.add(R.drawable.technoblade);
            binding.countGallery.setImageResource(R.drawable.mrbist);
            binding.countCamera.setImageResource(R.drawable.mark_rober);
            binding.countManage.setImageResource(R.drawable.a4);
            binding.countShare.setImageResource(R.drawable.piudipay);
            binding.countCall.setImageResource(R.drawable.dream);
            binding.countCompass.setImageResource(R.drawable.technoblade);
        }

        int rows, cols;


        switch (difficulty) {
            case "Easy":
                rows = 3;
                cols = 3;
                break;
            case "Medium":
                rows = 4;
                cols = 4;
                break;
            case "Hard":
                rows = 5;
                cols = 5;
                break;
            case "Insane":
                rows = 6;
                cols = 6;
                break;
            default:
                rows = 3;
                cols = 3;
        }

        binding.gridLayout.setRowCount(rows);
        binding.gridLayout.setColumnCount(cols);

        imageViews = new ArrayList<>();
        for (int i = 0; i < rows * cols; i++) {
            ImageView imageView = new ImageView(this);
            imageViews.add(imageView);

            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            binding.gridLayout.addView(imageView);
        }

        assignRandomImages();
        startMemoryPhase();


        binding.botoon1.setOnClickListener(v -> {
            String num1 = binding.editText1.getText().toString();
            String num2 = binding.editText2.getText().toString();
            String num3 = binding.editText3.getText().toString();
            String num4 = binding.editText4.getText().toString();
            String num5 = binding.editText5.getText().toString();
            String num6 = binding.editText6.getText().toString();
            if (num1.isEmpty() || num2.isEmpty() || num3.isEmpty() || num4.isEmpty() || num5.isEmpty() || num6.isEmpty()) {
                Snackbar.make(v, getText(R.string.PleaseEnterAllFields), Snackbar.LENGTH_SHORT).show();
                return;
            }

            number1 = Integer.parseInt(num1);
            number2 = Integer.parseInt(num2);
            number3 = Integer.parseInt(num3);
            number4 = Integer.parseInt(num4);
            number5 = Integer.parseInt(num5);
            number6 = Integer.parseInt(num6);

            Intent intent = FinishActivity.getIntent(this, galleryCount, cameraCount, manageCount, shareCount, callCount, compassCount, number1, number2, number3, number4, number5, number6);
            intent.putExtra("difficulty", difficulty);
            intent.putExtra("time", time);
            startActivity(intent);
            finish();
        });


        binding.buttonGo.setOnClickListener(v -> {
            Intent intent = new Intent(GameActivity.this, MainActivity.class);
            mySong.stop();
            startActivity(intent);
            finish();
        });

        binding.buttonGo1.setOnClickListener(v -> {

            if (memoryTimer != null) {
                memoryTimer.cancel();
                isMemoryPhase = false;
                binding.timerTextView.setVisibility(View.GONE);
                mySong.stop();

            }



            hideImages();
            showInputFields();
        });
    }

    private void assignRandomImages() {
        int gridSize = binding.gridLayout.getRowCount() * binding.gridLayout.getColumnCount();
        Random random = new Random();
        for (int i = 0; i < gridSize; i++) {
            int typeImage = random.nextInt(imageResources.size());
            imageViews.get(i).setImageResource(imageResources.get(typeImage));
            switch (typeImage) {
                case 0:
                    galleryCount++;
                    break;
                case 1:
                    cameraCount++;
                    break;
                case 2:
                    manageCount++;
                    break;
                case 3:
                    shareCount++;
                    break;
                case 4:
                    callCount++;
                    break;
                case 5:
                    compassCount++;
                    break;
            }
        }
    }

    private void startMemoryPhase() {
        int time_this = Integer.parseInt(time);
        time_this = time_this * 1000;
        isMemoryPhase = true;
        binding.timerTextView.setVisibility(View.VISIBLE);
        binding.timerTextView.setText("Remember!");

        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.VISIBLE);
        }

        memoryTimer = new CountDownTimer(time_this, 1000) {
            public void onTick(long millisUntilFinished) {
                binding.timerTextView.setText("Запомни! " + millisUntilFinished / 1000 + "s");
            }

            public void onFinish() {
                isMemoryPhase = false;
                binding.timerTextView.setVisibility(View.GONE);
                hideImages();
                showInputFields();
                mySong.stop();
            }
        }.start();
    }

    private void hideImages() {
        for (ImageView imageView : imageViews) {
            imageView.setVisibility(View.INVISIBLE);
        }
    }

    private void showInputFields() {
        binding.countGallery.setVisibility(View.VISIBLE);
        binding.editText1.setVisibility(View.VISIBLE);

        binding.countCamera.setVisibility(View.VISIBLE);
        binding.editText2.setVisibility(View.VISIBLE);

        binding.countCompass.setVisibility(View.VISIBLE);
        binding.editText3.setVisibility(View.VISIBLE);

        binding.countManage.setVisibility(View.VISIBLE);
        binding.editText4.setVisibility(View.VISIBLE);

        binding.countShare.setVisibility(View.VISIBLE);
        binding.editText5.setVisibility(View.VISIBLE);

        binding.countCall.setVisibility(View.VISIBLE);
        binding.editText6.setVisibility(View.VISIBLE);

        binding.botoon1.setVisibility(View.VISIBLE);
    }
}