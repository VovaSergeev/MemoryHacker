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
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.itschool.myapplication.databinding.ActivityShopBinding;

public class Shop extends AppCompatActivity {

    private int currency;

    private List<Skin> skins;
    private int selectedSkinIndex = -1;
    private SharedPreferences sharedPreferences;
    private ActivityShopBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityShopBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences("GamePrefs", Context.MODE_PRIVATE);
        currency = sharedPreferences.getInt("currency", 0);
        currency += 100;

        createSkins();
        updateUI();

        binding.purchaseButton.setOnClickListener(v -> {
            if (selectedSkinIndex >= 0) {
                purchaseSkin(selectedSkinIndex);
            }
        });

        binding.skin1Button.setOnClickListener(v -> selectSkin(0));
        binding.skin2Button.setOnClickListener(v -> selectSkin(1));
        binding.skin3Button.setOnClickListener(v -> selectSkin(2));

        binding.back1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Shop.this, MainActivity.class);

                startActivity(intent);


            }

        });

    }

    private void selectSkin(int index) {
        selectedSkinIndex = index;
        updateUI();
    }

    private void createSkins() {
        skins = new ArrayList<>();
        skins.add(new Skin(R.drawable.fruiet, "Скин 1", 100));
        skins.add(new Skin(R.drawable.attake_on_titan, "Скин 2", 100));
        skins.add(new Skin(R.drawable.blogers, "Скин 3", 100));
    }

    private void purchaseSkin(int skinIndex) {
        Skin skin = skins.get(skinIndex);
        if (currency >= skin.getPrice()) {
            currency -= skin.getPrice();

            savePurchasedSkin(skin.getDrawableId());
            Toast.makeText(this, skin.getName() + " куплен!", Toast.LENGTH_SHORT).show();
            updateUI();
        } else {
            Toast.makeText(this, "Недостаточно токенов", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("MutatingSharedPrefs")
    private void savePurchasedSkin(int drawableId) {
        Set<String> purchasedSkins = sharedPreferences.getStringSet("purchasedSkins", new HashSet<>());
        purchasedSkins.add(String.valueOf(drawableId));
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putStringSet("purchasedSkins", purchasedSkins);
        editor.apply();
    }

    @SuppressLint("SetTextI18n")
    private void updateUI() {
        binding.currencyTextView.setText("Токены: " + currency);
        binding.skin1Preview.setImageResource(skins.get(0).getDrawableId());
        binding.skin2Preview.setImageResource(skins.get(1).getDrawableId());
        binding.skin3Preview.setImageResource(skins.get(2).getDrawableId());

        if (selectedSkinIndex >= 0) {
            Skin selectedSkin = skins.get(selectedSkinIndex);
            Set<String> purchasedSkins = sharedPreferences.getStringSet("purchasedSkins", new HashSet<>());

            if (purchasedSkins.contains(String.valueOf(selectedSkin.getDrawableId()))) {
                binding.purchaseButton.setEnabled(false);
                binding.purchaseButton.setText( selectedSkin.getName()+"уже куплен");
            } else {
                binding.purchaseButton.setEnabled(true);
                binding.purchaseButton.setText("Купить: " + selectedSkin.getName());
            }
        } else {
            binding.purchaseButton.setEnabled(false);
            binding.purchaseButton.setText("Выберите скин для покупки");
        }
    }

}