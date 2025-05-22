package ru.itschool.myapplication;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import ru.itschool.myapplication.databinding.ActivityFinishBinding;

public class FinishActivity extends AppCompatActivity {
    MediaPlayer mySong;
    private  static  final  String GALLERY_COUNT="galleryCount";

    private float sum = 0;
    private float  Totalsum = 6;
    private int achievements1;
    private int achievements2;
    private int achievements3;
    private int achievements4;
    private int achievements5;
    private int achievements6;

    private int result = 0;

    private  static  final  String NUMBER1 = "number1";
    private  static  final  String NUMBER2 = "number2";
    private  static  final  String NUMBER3 = "number3";
    private  static  final  String NUMBER4 = "number4";
    private  static  final  String NUMBER5 = "number5";
    private  static  final  String NUMBER6 = "number6";
    private String difficulty;
    private String time;

    private  static  final  String CAMERA_COUNT="cameraCount";
    private  static  final  String MANAGE_COUNT="manageCount";
    private  static  final  String SHARE_COUNT="shareCount";
    private  static  final  String CALL_COUNT="callCount";
    private  static  final  String COMPASSCOUNT="compassCount";
    private ActivityFinishBinding binding;
    private TextView sumTextView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);


        sumTextView = findViewById(R.id.sumTextView);




        binding = ActivityFinishBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());
        mySong = MediaPlayer.create(FinishActivity.this,R.raw.music3);

        mySong.start();
        time = getIntent().getStringExtra("time");
        difficulty = getIntent().getStringExtra("difficulty");
        System.out.println(time+"  "+difficulty);

        int galleryCount = getIntent().getIntExtra(GALLERY_COUNT, 0);
        int  manageCount = getIntent().getIntExtra(MANAGE_COUNT, 0);
        int shareCount  = getIntent().getIntExtra(SHARE_COUNT, 0);
        int callCount = getIntent().getIntExtra(CALL_COUNT, 0);
        int compassCount = getIntent().getIntExtra(COMPASSCOUNT, 0);
        int cameraCount = getIntent().getIntExtra(CAMERA_COUNT, 0);



        int number1 = getIntent().getIntExtra(NUMBER1, 0);
        int number2 = getIntent().getIntExtra(NUMBER2, 0);
        int number3 = getIntent().getIntExtra(NUMBER3, 0);
        int number4 = getIntent().getIntExtra(NUMBER4, 0);
        int number5 = getIntent().getIntExtra(NUMBER5, 0);
        int number6 = getIntent().getIntExtra(NUMBER6, 0);
        calculateSum(galleryCount, cameraCount, manageCount, shareCount, callCount, compassCount, number1, number2, number3, number4, number5, number6);

        Total_Sum(galleryCount, cameraCount, manageCount, shareCount, callCount, compassCount);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        databaseHelper.addResult(time,difficulty,result);
        databaseHelper.updateResult(time, difficulty, result);
        float record = databaseHelper.getRecord(time, difficulty);

        binding.sumTextView.setText(Float.toString(result) + "%");
        binding.sumTextViewMax.setText(Float.toString(record) + "%");
        achievements(time, difficulty, result);



        binding.toMainActivity.setOnClickListener(v -> {
            Intent intent = new Intent(FinishActivity.this, MainActivity.class);

            startActivity(intent);

            startActivity(intent);

            finish();
        });
    }
    public  static  Intent getIntent(Context context, int galleryCount, int cameraCount, int manageCount, int shareCount, int callCount, int compassCount, int number1, int number2, int number3, int number4, int number5, int number6){
        Intent intent = new Intent(context,FinishActivity.class);
        intent.putExtra(GALLERY_COUNT,galleryCount);
        intent.putExtra(MANAGE_COUNT,manageCount);
        intent.putExtra(COMPASSCOUNT,compassCount);
        intent.putExtra(CALL_COUNT,callCount);
        intent.putExtra(CAMERA_COUNT,cameraCount);
        intent.putExtra(SHARE_COUNT,shareCount);
        intent.putExtra(NUMBER1,number1);
        intent.putExtra(NUMBER2,number2);
        intent.putExtra(NUMBER3,number3);
        intent.putExtra(NUMBER4,number4);
        intent.putExtra(NUMBER5,number5);
        intent.putExtra(NUMBER6,number6);
        return  intent;
    }
    private void calculateSum(int galleryCount,int cameraCount,int  manageCount, int shareCount,int callCount, int compassCount,int number1, int number2, int number3, int number4, int number5, int number6) {
        if (galleryCount == number1) {
            sum++;
        }
        if (cameraCount == number2) {
            sum++;
        }
        if (manageCount == number3) {
            sum++;
        }
        if (shareCount == number4) {
            sum++;
        }
        if (callCount == number5) {
            sum++;
        }
        if (compassCount == number6) {
            sum++;
        }
        System.out.println("galleryCount "+galleryCount);
        System.out.println("cameraCount "+cameraCount);
        System.out.println("manageCount "+manageCount);
        System.out.println("shareCount "+shareCount);
        System.out.println("callCount "+callCount);
        System.out.println("compassCount "+compassCount);

        System.out.println(sum);


    }
    private  void Total_Sum(int galleryCount,int cameraCount,int  manageCount, int shareCount,int callCount, int compassCount){


        result = Math.round((sum / Totalsum) * 100);// в бд

        System.out.println("Total Sum "+ Totalsum);
        System.out.println("result "+ result+"%");
    }
    private void achievements(String time, String difficulty, int result) {


        int time1;
        try {
            time1 = Integer.parseInt(time);
        } catch (NumberFormatException e) {

            return;
        }

        SharedPreferences prefs = getSharedPreferences("achievements", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();


        boolean isAchievement2Obtained = prefs.getInt("achievement2", 0) == 52;
        boolean isAchievement3Obtained = prefs.getInt("achievement3", 0) == 52;
        boolean isAchievement1Obtained = prefs.getInt("achievement1", 0) == 52;
        boolean isAchievement4Obtained = prefs.getInt("achievement4", 0) == 52;
        boolean isAchievement5Obtained = prefs.getInt("achievement5", 0) == 52;
        boolean isAchievement6Obtained = prefs.getInt("achievement6", 0) == 52;

        if (time1 <= 60 && "Insane".equals(difficulty) && result == 100 && !isAchievement2Obtained) {
            achievements2 = 52;
            editor.putInt("achievement2", achievements2);
            Toast.makeText(this, "Очивка: Да ты читак, получена", Toast.LENGTH_SHORT).show();
        }
        if (time1 < 10 && result == 100 && !isAchievement3Obtained) {
            achievements3 = 52;
            editor.putInt("achievement3", achievements3);
            Toast.makeText(this, "Очивка: Быстрее света, получена", Toast.LENGTH_SHORT).show();
        }
        if (time1 > 60 && result != 100 && "Easy".equals(difficulty) && !isAchievement1Obtained) {
            achievements1 = 52;
            editor.putInt("achievement1", achievements1);
            Toast.makeText(this, "Очивка: Тугодум, получена", Toast.LENGTH_SHORT).show();
        }
        if (time1 >10 &&"Easy".equals(difficulty)&& result == 0 && !isAchievement4Obtained) {
            achievements4 = 52;
            editor.putInt("achievement4", achievements4);
            Toast.makeText(this, "Очивка: Да ты снайпер", Toast.LENGTH_SHORT).show();
        }
        if (time1 > 60 && result == 100 && !isAchievement5Obtained) {
            achievements5 = 52;
            editor.putInt("achievement5", achievements5);
            Toast.makeText(this, "Очивка: 7 раз отмерь и 1 раз отрежь", Toast.LENGTH_SHORT).show();
        }
        if (time1 > 100000 && !isAchievement6Obtained) {
            achievements6 = 52;
            editor.putInt("achievement6", achievements6);
            Toast.makeText(this, "Очивка: Долгожитель", Toast.LENGTH_SHORT).show();
        }

        editor.apply();
    }




}

