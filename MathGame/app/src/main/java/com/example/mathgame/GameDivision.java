package com.example.mathgame;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;
import java.util.Random;

public class GameDivision extends AppCompatActivity {
//    TextView score;
    TextView getScore;
//    TextView life;
    TextView getLife;
//    TextView time;
    TextView getTime;

    TextView question;
    EditText answer;

    Button ok;
    Button next;
    Random random = new Random();
    int number1;
    int number2;
    int userAnsewr;
    int realAnsewr;
    int userScore = 0;
    int userLife = 3;

    CountDownTimer timer;
    private static final long START_TIMER_IN_MILIS = 60000;
    Boolean timer_running;
    long time_left_in_millis = START_TIMER_IN_MILIS;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_division);

//        score = findViewById(R.id.textViewScore);
        getScore = findViewById(R.id.textViewScore1);
//        life = findViewById(R.id.textViewLife);
        getLife = findViewById(R.id.textViewLife1);
//        time = findViewById(R.id.textViewTime);
        getTime = findViewById(R.id.textViewTimw1);
        question = findViewById(R.id.textViewQuestion);
        answer = findViewById(R.id.editTextAnswer);
        ok = findViewById(R.id.buttonOk);
        next = findViewById(R.id.buttonNext);

        gameContinue();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userAnsewr = Integer.valueOf(answer.getText().toString());

                pauseTimer();

                if(userAnsewr == realAnsewr)
                {
                    userScore = userScore + 10;
                    getScore.setText(""+userScore);
                    question.setText("Congratulations! Your answer is true!");
                }
                else
                {
                    userLife = userLife - 1;
                    getLife.setText(""+userLife);
                    question.setText("Sorry, your answer is wrong!");
                }

            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                answer.setText("");
                gameContinue();
                resetTimer();

                if(userLife <= 0)
                {
                    Toast.makeText(getApplicationContext(),"Game Over", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(GameDivision.this,Result.class);
                    intent.putExtra("score", userScore);
                    startActivity(intent);
                    finish();
                }
                else{
                    gameContinue();
                }
            }
        });
    }

    public void gameContinue(){
        number1 = random.nextInt(10);
        number2 = random.nextInt(10);

        if(number1 >= number2) {
            realAnsewr = number1 / number2;

            question.setText(number1 + "/" + number2);

        } else {
            realAnsewr = number2 / number1;

            question.setText(number2 + "/" + number1);

        }
        startTimer();
    }

    public void startTimer(){
        timer = new CountDownTimer(time_left_in_millis,1000) {
            @Override
            public void onTick(long millisUntilFinished) {

                time_left_in_millis = millisUntilFinished;
                updateText();

            }

            @Override
            public void onFinish() {
                timer_running = false;
                pauseTimer();
                resetTimer();
                updateText();
                userLife = userLife-1;
                getLife.setText(""+userLife);
                question.setText("Sorry! Time is up!");
            }
        }.start();

        timer_running = true;
    }

    public void updateText(){
        int second = (int)(time_left_in_millis/1000)%60;
        String time_left = String.format(Locale.getDefault(),"%02d",second);
        getTime.setText(time_left);
    }

    public void pauseTimer(){
        timer.cancel();
        timer_running = false;
    }

    public void resetTimer(){
        time_left_in_millis = START_TIMER_IN_MILIS;
        updateText();

    }
}