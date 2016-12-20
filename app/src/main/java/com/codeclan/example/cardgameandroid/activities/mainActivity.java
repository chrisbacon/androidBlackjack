package com.codeclan.example.cardgameandroid.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.*;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.codeclan.example.cardgameandroid.R;
import com.codeclan.example.cardgameandroid.cardGame.*;
import com.codeclan.example.cardgameandroid.cardGame.View;

import java.util.ArrayList;

/**
 * Created by user on 16/12/2016.
 */
public class mainActivity extends AppCompatActivity {
    TextView playerHand;
    Button twist;
    Button stick;
    Button reset;
    LinearLayout buttons;

    LinearLayout cards;

    TextView dealerHand;
    TextView result;

    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Log log = new Log();
        final Game game = new Game(log);
        view = new View(log);

        game.addPlayer("Player");
        game.addDealer("Dealer");

        game.setUp();
        game.dealCardToCurrentPlayer();
        game.dealCardToDealer();

        setContentView(R.layout.activity_main);



        playerHand = (TextView)findViewById(R.id.player_hand);
        dealerHand = (TextView)findViewById(R.id.dealer_hand);
        result = (TextView)findViewById(R.id.result);



        twist = (Button)findViewById(R.id.twist);
        stick = (Button)findViewById(R.id.stick);
        reset = (Button)findViewById(R.id.reset);

        buttons = (LinearLayout)findViewById(R.id.buttons);
        cards = (LinearLayout)findViewById(R.id.cards);

        playerHand.setText(view.displayCurrentPlayerHand());
        displayCards();

        twist.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                game.dealCardToCurrentPlayer();
                view.getPlayerMove("twist");
                game.handleMove();
                playerHand.setText(view.displayCurrentPlayerHand());

                displayCards();

                if (log.getBust()) {
                    buttons.setVisibility(android.view.View.INVISIBLE);
                    game.setResult();
                    result.setText(view.displayResult());
                }
            }
        });

        stick.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                view.getPlayerMove("stick");
                game.handleMove();
                playerHand.setText(view.displayCurrentPlayerHand());

                game.nextPlayer();

                while (log.getPlaying() && !log.getBust()) {
                    game.runDealerTurn();
                    dealerHand.setText(view.displayDealerHand());
                }

                buttons.setVisibility(android.view.View.INVISIBLE);
                game.setResult();
                result.setText(view.displayResult());

            }
        });

        reset.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                game.endRound();
                game.setUp();
                game.dealCardToCurrentPlayer();
                game.dealCardToDealer();



                cards.removeAllViews();
                playerHand.setText(view.displayCurrentPlayerHand());
                displayCards();

                dealerHand.setText("");
                result.setText("");

                buttons.setVisibility(android.view.View.VISIBLE);

            }
        });


    }

    private void displayCards() {
        cards.removeAllViews();

        ArrayList<String> imageNames = view.getCurrentPlayerHandCardImages();
        for (String filename : imageNames) {
            ImageView card = new ImageView(getBaseContext());
            card.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT));
            int resID = getResources().getIdentifier(filename , "drawable", getPackageName());
            card.setImageResource(resID);
            cards.addView(card);
        }
    }
}
