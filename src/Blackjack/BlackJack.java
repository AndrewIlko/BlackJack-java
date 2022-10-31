package Blackjack;
import Dealer.Dealer;
import Player.Player;

import java.util.Objects;
import java.util.Random;
import java.util.Scanner;
public class BlackJack {

    Dealer dealer = new Dealer();
    Player[] players = new Player[0];
    Player[] activePlayers = new Player[0];

    Random rand = new Random();
    Scanner option = new Scanner(System.in);
    private int[] cardsInBlackjack = {2,3,4,5,6,7,8,9,10,11};

    public Player[] pushPlayer(Player[] array, Player player){
        Player[] arrayCopy = new Player[array.length+1];
        for(int i=0;i<array.length;i++){
            arrayCopy[i] = array[i];
        }
        arrayCopy[arrayCopy.length-1] = player;
        return arrayCopy;
    }

    private void error(){
        System.out.println("Error! Try again.");
    }
    public void enterPlayers(){
        boolean flag = false;
        int countOfPlayers = 0;
        while(!flag) {
            String strCount;
            System.out.print("Enter count of players: ");
            strCount = option.nextLine();
            try{
                countOfPlayers=Integer.parseInt(strCount);
            }
            catch (Exception e){
                error();
            }
            finally {
                if(countOfPlayers>0){
                    flag = true;
                }
            }
        }
        players = new Player[countOfPlayers];
        createPlayersObj();
    }
    private void createPlayersObj(){
        System.out.println("Creating players...");
        for(int i=0;i<players.length;i++){
            Player player = new Player();
            player.setName("Player #"+(i+1));
            players[i] = player;
        }
        enterBets();
    }
    public void enterBets(){

        for(int i=0;i<players.length;i++){
            Player player = players[i];
            System.out.println(player.getName());
            System.out.println("Available balance: "+ player.getBalance() +"$");
            boolean flag = false;
            int bet = 0;
            while(!flag){
                System.out.print("Enter your bet: ");
                String betStr = option.nextLine();
                try{
                    bet = Integer.parseInt(betStr);
                }
                catch (Exception e){
                    error();
                }
                finally {
                    if(bet==0){
                        break;
                    }
                    if(bet>=0 && player.getBalance()>=bet){
                        flag=true;
                        player.setBet(bet);
                        boolean checkPlayer = false;
                        for(int j=0;j<activePlayers.length;j++){
                            if(activePlayers[j]==player){
                                checkPlayer = true;
                                System.out.println("Fuck u");
                            }
                        }
                        if(checkPlayer==false){
                            activePlayers = pushPlayer(activePlayers,player);
                        }


                    }
                    else if((bet>=0 && player.getBalance()<bet)){
                        System.out.println("You don't have enough money.");
                    }
                }
            }
        }

    }

    private void printCards(){
        dealer.printInfo();
        for(int i=0;i<activePlayers.length;i++){
            activePlayers[i].printInfo();
        }
    }
    public void checkCards(String type){
        String Black = "Blackjack";
        for(int i=0;i<activePlayers.length;i++){
            Player player = activePlayers[i];
            if(Objects.equals(type,"Blackjack")){
                if(player.getSplitFlag()){
                    String res1 = "In process...",res2="In process...";

                    int sum1 = player.getCardSum("main");
                    int sum2 = player.getCardSum("split");
                    if(sum1==21){
                        res1 = Black;
                    }
                    if(sum2==21){
                        res2 = Black;
                    }
                    player.setWinStatus(res1 + " "+"/"+" " +res2);
                }
                else{
                    int sum = player.getCardSum("main");
                    if(sum==21){
                        player.setWinStatus(Black);
                    }
                }
            }
            else if(Objects.equals(type,"Final")){
                dealer.setFinalCheck(true);
                if(player.getSplitFlag()){
                    int gainMoney = 0;
                    String res1 = "In process...";
                    String res2="In process...";
                    int sum1 = player.getCardSum("main");
                    int sum2 = player.getCardSum("split");

                    res1 = setGameStatus(sum1);
                    res2 = setGameStatus(sum2);

                    gainMoney+=moneyOutcome(res1,player.getBet());
                    gainMoney+=moneyOutcome(res2,player.getBet());
                    player.setBalance(gainMoney);
                    String result = res1 + " " + "/" + " " + res2 + " ";
                    result+=(gainMoney>0)? "+"+gainMoney : gainMoney;
                    player.setWinStatus(result + "$");
                }
                else {
                    int gainMoney = 0;
                    int sum = player.getCardSum("main");
                    String res = setGameStatus(sum);
                    gainMoney+=moneyOutcome(res,player.getBet());
                    player.setBalance(gainMoney);
                    String result = "";
                    result+=(gainMoney>0)? "+"+gainMoney : gainMoney;
                    player.setWinStatus(setGameStatus(sum) + " "+ result + "$");
                }
            }
        }
    }

    public String setGameStatus(int sum){
        int dealerSum = dealer.getCardSum("main");
        String result = "";

        if(sum>21){
            return "Defeat";
        }
        if(dealerSum!=21 && sum==21){
            return "Blackjack";
        }
        if(dealerSum>21 || dealerSum<sum){
            return "Win";
        }
        if(dealerSum==21 && sum==21){
            return "Push";
        }
        if(dealerSum>sum){
            return "Defeat";
        }
        if(dealerSum==sum){
            return "Draw";
        }
        return result;
    }
    public int moneyOutcome(String status,int bet){
        if(Objects.equals(status, "Win")){
            return bet;
        }
        else if(Objects.equals(status, "Blackjack")){
            return (bet*3)/2;
        }
        else if(Objects.equals(status, "Defeat")){
            return -bet;
        }
        return 0;
    }
    public void checkDealerCards(String type){
        String Black = "Blackjack";
        if(Objects.equals(type,"Blackjack")) {
            if(dealer.getCardSum("main")==21){
                dealer.setWinStatus(Black);
                setAllDefeat();
            }
        }
    }
    public void setAllDefeat(){
        for(int i=0;i<activePlayers.length;i++){
            Player player = activePlayers[i];
            if(!Objects.equals(player.getWinStatus(), "Blackjack")){
                player.setWinStatus("Defeat");
            }
        }
    }
    public void dealPlayers(){
        for(int i=0;i<activePlayers.length;i++){
            Player player = activePlayers[i];
            int sum = player.getCardSum("main");
            int card = cardsInBlackjack[rand.nextInt(10)];
            if(card==11 && card+sum>21){
                player.addCard(1,"main");
            }
            else{
                player.addCard(card,"main");
            }
        }
    }
    public void dealDealer(Dealer dealer){
        int sum = dealer.getCardSum("main");
        int number = cardsInBlackjack[rand.nextInt(10)];
        if(number==11 && sum+number>21){
            dealer.addCard(1);
        }
        else {
            dealer.addCard(number);
        }
    }
    public void takeStopSplit(){
        for(int i=0;i<activePlayers.length;i++) {
            Player player = activePlayers[i];
            if(player.getCardSum("main")!=21)player.menu();
        }
    }

    public void clearAll(){
        dealer.clearDealerInfo();
        for(int i=0;i<activePlayers.length;i++){
            activePlayers[i].clearAllInfo();
        }
    }
    public void start(){
        while (true){
            System.out.println("Choose option: ");
            System.out.println("Enter new players - 1");
            System.out.println("Play with existing players- 2");
            System.out.println("Exit       - 3");
            String str = option.nextLine();
            if(Objects.equals(str, "1")){
                enterPlayers();
                dealPlayers();
                dealPlayers();
                checkCards("Blackjack");
                printCards();
                dealDealer(dealer);
                dealDealer(dealer);
                checkDealerCards("Blackjack");
                printCards();
                if(dealer.getCardSum("main")!=21){
                    takeStopSplit();
                    while(dealer.getCardSum("main")<=16){
                        int card = cardsInBlackjack[rand.nextInt(10)];
                        if(card==11 && card + dealer.getCardSum("main")>21){
                            card=1;
                        }
                        dealer.addCard(card,"main");

                    }
                }
                else {
                    dealer.setFinalCheck(true);
                }
                checkCards("Final");
                printCards();
                clearAll();

            }
            else if(Objects.equals(str, "2")){
                enterBets();
                dealPlayers();
                dealPlayers();
                checkCards("Blackjack");
                printCards();
                dealDealer(dealer);
                dealDealer(dealer);
                checkDealerCards("Blackjack");
                printCards();
                if(dealer.getCardSum("main")!=21){
                    takeStopSplit();
                    while(dealer.getCardSum("main")<=16){
                        int card = cardsInBlackjack[rand.nextInt(10)];
                        if(card==11 && card + dealer.getCardSum("main")>21){
                            card=1;
                        }
                        dealer.addCard(card,"main");
                    }
                }
                else {
                    dealer.setFinalCheck(true);
                }
                checkCards("Final");
                printCards();
                clearAll();
            }
            else {
                break;
            }
        }

    }

}
