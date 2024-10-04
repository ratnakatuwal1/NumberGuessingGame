package com.ratna.numberguessinggame;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity {
    Button button;
    RadioButton radio1, radio2, radio3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        radio1 = findViewById(R.id.radio1);
        radio2 = findViewById(R.id.radio2);
        radio3 = findViewById(R.id.radio3);

        button.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            if (!radio1.isChecked() && !radio2.isChecked() && !radio3.isChecked()) {
                Snackbar.make(view, "Please select a number of digits", Snackbar.LENGTH_SHORT).show();
            } else {
                if (radio1.isChecked()){
                    intent.putExtra("two", true);
                }
                if (radio2.isChecked()){
                    intent.putExtra("three", true);
                }
                if (radio3.isChecked()) {
                    intent.putExtra("four", true);
                }
                startActivity(intent);
            }
        });
    }
}