import Blackjack.BlackJack;

import java.util.Objects;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        BlackJack user = new BlackJack();
        Scanner option = new Scanner(System.in);
        while(true){
            System.out.println("Choose option: ");
            System.out.println("Start game - 1");
            System.out.println("Exit       - 2");
            String str = option.nextLine();
            if(Objects.equals(str, "1")){
                user.start();
            }
            else{
                System.out.println("Problem closed.");
                break;
            }
        }
    }
}