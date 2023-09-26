import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        System.out.println("Enter your name and money");
        Scanner input = new Scanner(System.in);
        String customerName = input.next();
        int money = input.nextInt();

        Customer customer = new Customer(customerName, money);
        TakeOutSimulator tOS = new TakeOutSimulator(customer, input);
        tOS.startTakeOutSimulator();
    }
}

