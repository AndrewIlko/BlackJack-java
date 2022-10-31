package Dealer;
import Player.Player;

import java.util.Objects;

public class Dealer extends Player {

    private boolean finalCheck = false;
    public boolean getFinalCheck(){
        return finalCheck;
    }
    public void setFinalCheck(boolean status){
        finalCheck = status;
    }
    public void addCard(int card){
        int[] cardsCopy = new int[cardsMain.length+1];
        for(int i=0;i<cardsMain.length;i++){
            cardsCopy[i] = cardsMain[i];
        }
        cardsCopy[cardsMain.length] = card;
        cardsMain = cardsCopy;
    }
    public void printInfo(){
        System.out.println("Dealer");
        String cardsStr = "Cards: ";
        if(cardsMain.length==0){
            cardsStr+=("No cards");
        }
        else{
            if(cardsMain.length==2 && !finalCheck){
                for(int i=0;i<cardsMain.length;i++) {
                    cardsStr+= cardsMain[i] + " " + "[?]" + " ";
                    break;
                }
                cardsStr+="Sum: " + cardsMain[0];
            }
            else {
                for (int i = 0; i < cardsMain.length; i++) {
                    cardsStr += cardsMain[i] + " ";
                }
                cardsStr += "Sum: " + getCardSum("main");
            }
        }
        System.out.println(cardsStr);
        System.out.println("--------");
    }
    public void clearDealerInfo(){
        finalCheck = false;
        cardsMain = new int[0];
    }
}
