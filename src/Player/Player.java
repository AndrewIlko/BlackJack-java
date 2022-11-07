package Player;
import java.util.Random;
import java.util.Scanner;
import java.util.Objects;
import java.util.Random;


/** Клас гравця **/
public class Player {
    public int[] cardsMain = new int[0];
    public int[] cardsSplit = new int[0];

    private boolean splitFlag = false;

    public int bet;
    private String name;
    private int balance = 1000;
    private String winStatus = "In process...";

    Scanner scan = new Scanner(System.in);
    private int[] cardsInBlackjack = {2,3,4,5,6,7,8,9,10,11};
    Random rand = new Random();

    /**
     *
     * @return Повертає флаг, який показує чи "сплітанув" гравець
     */
    public boolean getSplitFlag(){
        return splitFlag;
    }

    /**
     *
     * @param res
     * Встановлує результат гравця
     */
    public void setWinStatus(String res){
        winStatus = res;
    }

    /**
     *
     * @return Повертає результат гравця
     */
    public String getWinStatus(){
        return winStatus;
    }

    /**
     *
     * @param name
     * Встановлює імя гравця
     *
     */
    public void setName(String name){
        this.name = name;
    }
    /**
     *
     * @return Повертає імя гравця
     */
    public String getName(){
        return this.name;
    }
    /**
     *
     * @return Повертає баланс гравця
     */
    public int getBalance(){
        return this.balance;
    }

    /**
     *
     * @param num
     * Встановлує баланс гравця
     */
    public void setBalance(int num){
        balance+=num;
    }
    /**
     *
     * @param num
     * Встановлує ставку гравця
     */
    public void setBet(int num){
        bet = num;
    }
    /**
     *
     * @return Повертає ставку гравця
     */
    public int getBet(){
        return bet;
    }

    /**
     *
     * @param card - карта, яку потрібно додати
     * @param arrName - назва колоду в яку потрібно додати карту
     * arrName = "main" - додає до головної колоди
     * arrName = "split" - додає до другорядної колоди
     */
    public void addCard(int card,String arrName){
        if(Objects.equals(arrName, "main")){
            int[] cardsCopy = new int[cardsMain.length+1];
            for(int i=0;i<cardsMain.length;i++){
                cardsCopy[i] = cardsMain[i];
            }
            cardsCopy[cardsMain.length] = card;
            cardsMain = cardsCopy;
        }
        else if(Objects.equals(arrName, "split")){
            int[] cardsCopy = new int[cardsSplit.length+1];
            for(int i=0;i<cardsSplit.length;i++){
                cardsCopy[i] = cardsSplit[i];
            }
            cardsCopy[cardsSplit.length] = card;
            cardsSplit = cardsCopy;
        }
    }

    /**
     *
     * @param arrName - массив, суму якого треба порахувати
     * @return Повертає суму массива arrName
     */
    public int getCardSum(String arrName){
        if(Objects.equals(arrName, "main")){
            int sum = 0;
            for(int i=0;i<cardsMain.length;i++){
                sum+=cardsMain[i];
            }
            return sum;
        }
        else {
            int sum = 0;
            for(int i=0;i<cardsSplit.length;i++){
                sum+=cardsSplit[i];
            }
            return sum;
        }
    }

    /**
     * Ігрове меню гравця під час гри
     */
    public void menu(){
        printInfo();
        System.out.println("Choose option: ");
        System.out.println("Take  - 1");
        System.out.println("Stop  - 2");
        System.out.println("Split - 3");
        String option = scan.nextLine();
        System.out.println(option);

        if(Objects.equals(option, "3")){
            splitFlag = true;
            int firstCard = cardsMain[0];
            int secondCard = cardsMain[1];
            cardsMain = new int[0];
            addCard(firstCard,"main");
            addCard(secondCard,"split");
            setBet(getBet()*2);
            printInfo();
            while(true){
                System.out.println("Cards 1");
                System.out.println("Choose option: ");
                System.out.println("Take  - 1");
                System.out.println("Stop  - 2");
                option = scan.nextLine();
                if(Objects.equals(option,"1")){
                    int card = cardsInBlackjack[rand.nextInt(10)];
                    if(card==11 && card+getCardSum("main")>21){
                        card = 1;
                    }
                    this.addCard(card,"main");
                    printInfo();
                    if(getCardSum("main")>=21) break;
                }
                else break;
            }
            while(true){
                System.out.println("Cards 2");
                System.out.println("Choose option: ");
                System.out.println("Take  - 1");
                System.out.println("Stop  - 2");
                option = scan.nextLine();
                if(Objects.equals(option,"1")){
                    int card = cardsInBlackjack[rand.nextInt(10)];
                    if(card==11 && card+getCardSum("split")>21){
                        card = 1;
                    }
                    this.addCard(card,"split");
                    printInfo();
                    if(getCardSum("split")>=21) break;
                }
                else break;
            }
        }
        else if(Objects.equals(option, "1")){

            int card = cardsInBlackjack[rand.nextInt(10)];
            this.addCard(card,"main");
            printInfo();
            if(this.getCardSum("main")>=21)return;
            while(true){
                System.out.println("Choose option: ");
                System.out.println("Take  - 1");
                System.out.println("Stop  - 2");
                option = scan.nextLine();

                if(Objects.equals(option, "1")){
                    card = cardsInBlackjack[rand.nextInt(10)];
                    if(card==11 && card+getCardSum("main")>21){
                        card = 1;
                    }
                    this.addCard(card,"main");
                    printInfo();
                    if(getCardSum("main")>=21) break;
                }
                else if(Objects.equals(option, "2")){
                    break;
                }
            }
        }
    }
    //cardsInBlackjack[rand.nextInt(10)];

    /**
     * Друкує повну інформацію про гравця (Імя, ставку, карти колод та результат)
     *
     */
    public void printInfo(){
        System.out.println(name);
        System.out.println("Bet: "+bet);
        if(splitFlag){
            String cardsStr1 = "Cards 1: ";
            String cardsStr2 = "Cards 2: ";

            for(int i=0;i<cardsMain.length;i++){
                cardsStr1+=cardsMain[i]+" ";
            }
            cardsStr1+="Sum: " + getCardSum("main");

            for(int i=0;i<cardsSplit.length;i++){
                cardsStr2+=cardsSplit[i]+" ";
            }
            cardsStr2+="Sum: " + getCardSum("split");

            System.out.println(cardsStr1);
            System.out.println(cardsStr2);
            System.out.println("Game status: " + getWinStatus());
        }

        else{
            String cardsStr = "Cards: ";
            if(cardsMain.length==0){
                cardsStr+=("No cards");
            }
            else{
                for(int i=0;i<cardsMain.length;i++){
                    cardsStr+=cardsMain[i]+" ";
                }
            }
            cardsStr+="Sum: " + getCardSum("main");
            System.out.println(cardsStr);
            System.out.println("Game status: " + getWinStatus());
        }
        System.out.println("--------");
    }

    /**
     * Видаляє інформацію гри, яка закінчилась
     *
     */
    public void clearAllInfo(){
        this.cardsMain = new int[0];
        this.cardsSplit = new int[0];
        this.splitFlag = false;
        this.winStatus = "In process...";
    }
}
