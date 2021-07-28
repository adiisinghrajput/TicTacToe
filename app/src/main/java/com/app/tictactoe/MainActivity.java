package com.app.tictactoe;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.airbnb.lottie.LottieAnimationView;

public class MainActivity extends AppCompatActivity {
    boolean gameActive = true;
    TextView playAgain, exit, crossDigit, zeroDigit, resultText;
    View view;
    CardView cardView;
    Button playAgainButton, exitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        playAgain = findViewById(R.id.playAgain);
        exit = findViewById(R.id.exit);
        crossDigit = findViewById(R.id.crossDigit);
        zeroDigit = findViewById(R.id.zeroDigit);
        cardView = findViewById(R.id.cardView);
        playAgainButton = findViewById(R.id.playAgainCard);
        exitButton = findViewById(R.id.exitCard);
        resultText = findViewById(R.id.resultText);


        exit.setOnClickListener(v -> finish());
        playAgain.setOnClickListener(v -> gameReset(view));

        //Card Control
        exitButton.setOnClickListener(v -> finish());
        playAgainButton.setOnClickListener(v -> {
                gameReset(view);
                cardView.setVisibility(View.INVISIBLE);
        });
    }

    // Player represents
    // 0 - X
    // 1 - O
    int activePlayer = 0;
    int[] gameState = {2, 2, 2, 2, 2, 2, 2, 2, 2};

    // State meanings:
    // 0 - X
    // 1 - O
    // 2 - Null
    // put all win positions in a 2D array
    int[][] winPositions = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};
    public static int counter = 0;

    // this function will be called every time a
    // players tap in an empty box of the grid
    @SuppressLint("SetTextI18n")
    public void boxClick(View view) {
        ImageView img = (ImageView) view;
        TextView crossDigit = findViewById(R.id.crossDigit);
        TextView zeroDigit = findViewById(R.id.zeroDigit);

        int tappedImage = Integer.parseInt(img.getTag().toString());

        // if someone wins or the boxes are full
        if (!gameActive) {
            gameReset(view);
        }

        // if the tapped image is empty
        if (gameState[tappedImage] == 2) {
            counter++;

            // check if its the last box
            if (counter == 9) {
                gameActive = false;
            }

            // mark this position
            gameState[tappedImage] = activePlayer;
            img.setTranslationY(-1000f); //animation

            // change the active player
            if (activePlayer == 0) {
                img.setImageResource(R.drawable.cross);
                activePlayer = 1;
                TextView status = findViewById(R.id.status);
                status.setText("O's Turn - Tap to play");
            } else {
                // set the image of o
                img.setImageResource(R.drawable.oblack);
                activePlayer = 0;
                TextView status = findViewById(R.id.status);

                // change the status
                status.setText("X's Turn - Tap to play");
            }
            img.animate().translationYBy(1000f).setDuration(300);
        }
        int flag = 0;
        // Check if any player has won
        for (int[] winPosition : winPositions) {
            if (gameState[winPosition[0]] == gameState[winPosition[1]] &&
                    gameState[winPosition[1]] == gameState[winPosition[2]] &&
                    gameState[winPosition[0]] != 2) {
                flag = 1;

                // Somebody has won! - Find out who!
                String winnerStr;

                // game reset function be called
                gameActive = false;
                if (gameState[winPosition[0]] == 0) {
                    winnerStr = "X has won";
                    crossDigit.setText("1");
                    cardView.setVisibility(View.VISIBLE);
                    resultText.setText("Hurrah! X has won");
                } else {
                    winnerStr = "O has won";
                    zeroDigit.setText("1");
                    cardView.setVisibility(View.VISIBLE);
                    resultText.setText("Hurrah! 0 has won");

                }
                playAgain.setText("Play Again");
                // Update the status bar for winner announcement
                TextView status = findViewById(R.id.status);
                status.setText(winnerStr);
            }
        }
        // set the status if the match draw
        if (counter == 9 && flag == 0) {
            TextView status = findViewById(R.id.status);
            status.setText("Match Draw");
            playAgain.setText("Play Again");
            crossDigit.setText("0");
            zeroDigit.setText("0");
            cardView.setVisibility(View.VISIBLE);
            resultText.setText("Match Draw, Both of you winner");
        }
    }

    // reset the game
    public void gameReset(View view) {
        gameActive = true;
        activePlayer = 0;
        crossDigit.setText("0");
        zeroDigit.setText("0");
        for (int i = 0; i < gameState.length; i++) {
            gameState[i] = 2;
        }

        // remove all the images from the boxes inside the grid
        ((ImageView) findViewById(R.id.imageView0)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView1)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView2)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView3)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView4)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView5)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView6)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView7)).setImageResource(0);
        ((ImageView) findViewById(R.id.imageView8)).setImageResource(0);

        TextView status = findViewById(R.id.status);
        status.setText("X's Turn - Tap to play");
    }

}

