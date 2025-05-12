package ru.itschool.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import ru.itschool.myapplication.databinding.ActivityGameBinding;
import ru.itschool.myapplication.databinding.ActivityProfileBinding;

public class Profile extends AppCompatActivity {//надо пофиксить таблицу а так норм)))

    private EditText timeEditText;

    private Button getResultButton;
    private ActivityProfileBinding binding;
    private TextView resultTextView;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        Spinner difficultySpinner = findViewById(R.id.difficultySpinner);


        String[] difficulties = {"Easy", "Medium", "Hard", "Insane"};


        ArrayAdapter<String> adapter = new ArrayAdapter<>(Profile.this, android.R.layout.simple_spinner_item, difficulties);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        difficultySpinner.setAdapter(adapter);




        timeEditText = findViewById(R.id.timeEditText);

        getResultButton = findViewById(R.id.getResultButton);
        resultTextView = findViewById(R.id.resultTextView);
        int rows, cols;
        rows = 10;
        cols = 10;
        binding.gridLayout.setRowCount(rows);
        binding.gridLayout.setColumnCount(cols);

        databaseHelper = new DatabaseHelper(this);
        checkAchievements();

        getResultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String time = timeEditText.getText().toString();



                String selectedDifficulty = difficultySpinner.getSelectedItem().toString();

                int result = (int) databaseHelper.getResult(time, selectedDifficulty);
                if (result != -1) {
                    resultTextView.setText(String.valueOf(result) + "%");
                } else {
                    Toast.makeText(Profile.this, "Результат не найден", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void checkAchievements() {
        SharedPreferences prefs = getSharedPreferences("achievements", MODE_PRIVATE);

        int achievement1 = prefs.getInt("achievement1", 0);
        int achievement2 = prefs.getInt("achievement2", 0);
        int achievement3 = prefs.getInt("achievement3", 0);

        GridLayout gridLayout = findViewById(R.id.gridLayout);

        if (achievement1 == 52) {
            int imageResource = R.drawable.achievement1;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResource);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gridLayout.addView(imageView);
        }

        if (achievement2 == 52) {
            int imageResource = R.drawable.achievement2;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResource);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gridLayout.addView(imageView);
        }

        if (achievement3 == 52) {
            int imageResource = R.drawable.achievement3;
            ImageView imageView = new ImageView(this);
            imageView.setImageResource(imageResource);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.height = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.rowSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            imageView.setLayoutParams(params);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            gridLayout.addView(imageView);
        }
    }
}