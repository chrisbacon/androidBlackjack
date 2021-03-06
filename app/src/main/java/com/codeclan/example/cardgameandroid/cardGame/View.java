package com.codeclan.example.cardgameandroid.cardGame;

import java.util.ArrayList;

public class View {
    private Log log;

    public View(Log log) {
        this.log = log;
    }

    public String displayCurrentPlayerHand() {
        Player player = this.log.getCurrentPlayer();
        return player.getName() + ": " + player.getHandString();
    }

    public ArrayList<String> getCurrentPlayerHandCardImages() {
        ArrayList<String> cardImages = new ArrayList<>();
        Player player = log.getCurrentPlayer();
        for (Card card : player.getHand()) {
            cardImages.add(card.toString());
        }
        return cardImages;
    }

    public String getLastCardImageFileName() {
        Player player = log.getCurrentPlayer();

        return player.getHand().getLastCard().toString();
    }

    public String displayDealerHand() {
        Dealer dealer = this.log.getDealer();
        return dealer.getName() + ": " + dealer.getHandString();
    }

    public String displayResult() {
        Player winner = this.log.getWinner();
        Player loser = this.log.getLoser();
        String output;

        if (!this.log.getDraw()) {
            output = winner.getName() + " wins with " + winner.getHandValue() + " against " + loser.getHandValue() + "!";
        } else {
            output = "Draw! Both the player and the dealer have " + winner.getHandValue();
        }

        if (this.log.getBust()) {
            output = this.log.getCurrentPlayer().getName() + " went bust with " + this.log.getCurrentPlayer().getHandValue();
        }

        return output;

    }

    public void getPlayerMove(String move) {
        this.log.setMove(move);
    }
}