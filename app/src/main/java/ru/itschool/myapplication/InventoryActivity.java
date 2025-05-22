package ru.itschool.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.itschool.myapplication.databinding.ActivityInventoryBinding;


public class InventoryActivity extends AppCompatActivity {

    private List<Skin> skins;

    private int selectedSkinIndex = -1;
    private SharedPreferences sharedPreferences;
    private ActivityInventoryBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityInventoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        Set<String> purchasedSkins = sharedPreferences.getStringSet("purchasedSkins", new HashSet<>());

        createSkins();
        updateUI();

        binding.useSkinButton.setOnClickListener(v -> {
            if (selectedSkinIndex >= 0) {
                useSkin(selectedSkinIndex);
            }

        });
        binding.back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(InventoryActivity.this, MainActivity.class);

                startActivity(intent);


            }

        });


        for (int i = 0; i < skins.size(); i++) {
            Skin skin = skins.get(i);
            if (purchasedSkins.contains(String.valueOf(skin.getDrawableId())) || skin.getName().equals("Default")) {
                int skinIndex = i;
                ImageView skinPreview = new ImageView(this);
                skinPreview.setImageResource(skins.get(i).getDrawableId());
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(200, 200);
                params.setMargins(20, 20, 20, 20);
                skinPreview.setLayoutParams(params);
                skinPreview.setOnClickListener(v -> selectSkin(skinIndex));
                binding.skinListLayout.addView(skinPreview);
            }
        }
    }

    private void createSkins() {
        skins = new ArrayList<>();
        skins.add(new Skin(R.drawable.fruiet, "Skin 1"));
        skins.add(new Skin(R.drawable.attake_on_titan, "Skin 2"));
        skins.add(new Skin(R.drawable.blogers, "Skin 3"));
    }

    private void selectSkin(int index) {
        selectedSkinIndex = index;
        updateUI();
    }

    private void useSkin(int skinIndex) {
        Skin skin = skins.get(skinIndex);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("selectedSkin", skin.getDrawableId());
        editor.apply();

        Intent intent = new Intent(InventoryActivity.this, MainActivity.class);
        intent.putExtra("selectedSkinIndex", skinIndex);
        startActivity(intent);

        Toast.makeText(this, "Skin " + skin.getName() + " selected!", Toast.LENGTH_SHORT).show();
        updateUI();
        finish();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        if (selectedSkinIndex >= 0) {
            binding.useSkinButton.setEnabled(true);
            binding.useSkinButton.setText("Use " + skins.get(selectedSkinIndex).getName());
        } else {
            binding.useSkinButton.setEnabled(false);
            binding.useSkinButton.setText("Select Skin to Use");
        }
    }
}