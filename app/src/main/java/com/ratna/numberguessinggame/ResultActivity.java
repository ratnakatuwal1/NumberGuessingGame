package com.ratna.numberguessinggame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Random;

public class ResultActivity extends AppCompatActivity {
    TextView textLastGuess, textRemainingLife, textView4;
    EditText editTextText;
    Button buttonConfirm;
    boolean twoDigit, threeDigit, fourDigit;
    Random r = new Random();
    int random;
    int remainingLife = 10;
    ArrayList<Integer> guessesList = new ArrayList<>();
    int userAttempts = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        textLastGuess = findViewById(R.id.textLastGuess);
        textRemainingLife = findViewById(R.id.textRemainingLife);
        textView4 = findViewById(R.id.textView4);
        editTextText = findViewById(R.id.editTextText);
        buttonConfirm = findViewById(R.id.buttonConfirm);

        twoDigit = getIntent().getBooleanExtra("two", false);
        threeDigit = getIntent().getBooleanExtra("three", false);
        fourDigit = getIntent().getBooleanExtra("four", false);

        if (twoDigit) {
            random = r.nextInt(90) + 10;
        }
        if (threeDigit) {
            random = r.nextInt(900) + 100;
        }
        if (fourDigit) {
            random = r.nextInt(9000) + 1000;
        }

        buttonConfirm.setOnClickListener(view -> {
            String guess = editTextText.getText().toString();
            if (guess.equals("")) {
                Toast.makeText(this, "Please enter a guess", Toast.LENGTH_SHORT).show();
            } else {
                textLastGuess.setVisibility(View.VISIBLE);
                textRemainingLife.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.VISIBLE);
                userAttempts++;

                remainingLife--;
                int userGuess = Integer.parseInt(guess);
                guessesList.add(userGuess);

                textLastGuess.setText("Your Last Guess is: " + guess);
                textRemainingLife.setText("Your Remaining life is: " + remainingLife);

                if (random == userGuess) {
                    // User guessed the correct number
                    AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                    builder.setTitle("Number Guessing Game");
                    builder.setCancelable(false);
                    builder.setMessage("Congratulations! You guessed the correct number: " + random + "!\n\nIt took you " + userAttempts + " attempts to find my number. Great job!\n\nWould you like to play again?");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            moveTaskToBack(true);
                            android.os.Process.killProcess(android.os.Process.myPid());
                            System.exit(1);
                        }
                    });
                    builder.create().show();
                } else {
                    // User guessed incorrectly
                    if (random < userGuess) {
                        textView4.setText("Decrease your guess");
                    } else {
                        textView4.setText("Increase your guess");
                    }

                    // If user is out of attempts
                    if (remainingLife == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                        builder.setTitle("Number Guessing Game");
                        builder.setCancelable(false);
                        builder.setMessage("Sorry, that was not the correct number. The correct number was: " + random + ".\n\nIt took you " + userAttempts + " attempts. Better luck next time!\n\nWould you like to play again?");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(ResultActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        });
                        builder.create().show();
                    }
                }
                // Clear the input field after each guess
                editTextText.setText("");
            }
        });
    }
}
