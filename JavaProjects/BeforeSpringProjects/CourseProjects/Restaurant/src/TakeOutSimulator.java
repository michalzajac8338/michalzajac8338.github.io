import java.util.Scanner;

public class TakeOutSimulator{
    private final Customer customer;
    private final FoodMenu menu;
    private final Scanner input;

    public TakeOutSimulator(Customer customer, Scanner input){
        this.customer = customer;
        this.menu = new FoodMenu();
        this.input = input;
    }

    private <T> T getOutputOnIntInput(String userInputPrompt, IntUserInputRetriever<T> intUserInputRetriever){
        while(true) {
            System.out.println(userInputPrompt);
            if (input.hasNextInt()) {
                int a = input.nextInt();
                input.nextLine();
                try {
                    return intUserInputRetriever.produceOutputOnIntUserInput(a);
                } catch (IllegalArgumentException e) {
                    System.out.println(a + " is an invalid index.");
                }
            } else {
                System.out.println("Wrong input type: must be an int.");
                input.nextLine();
            }
        }
    }

    public boolean shouldSimulate(){
        String userPrompt = "1 - proceed\n0 - exit";
        IntUserInputRetriever<Boolean> intUIR = selection -> {
            if (selection == 1 && customer.getMoney() >= menu.getLowestCostFood().getPrice()) return true;
            else if (selection==0) {
                System.out.println("Thank you");
                return false;
            } else throw new IllegalArgumentException();
        };
        return getOutputOnIntInput(userPrompt, intUIR);
    }

    public Food getMenuSelection() {

        System.out.println("Options:");
        System.out.println(menu);

        String userPrompt = "Choose option for Your order:";

        IntUserInputRetriever<Food> intUIR = selection -> {
            if (menu.getFood(selection) != null) {
                return menu.getFood(selection);
            } else throw new IllegalArgumentException();
        };
        return this.getOutputOnIntInput(userPrompt, intUIR);
    }

    public boolean isStillOrderingFood(){
        String userPrompt = "1 - continue order\n0 - checkout";
        IntUserInputRetriever<Boolean> intUIR = selection -> {
            if (selection == 1) return true;
            else if (selection == 0) return false;
            else throw new IllegalArgumentException();
        };
        return this.getOutputOnIntInput(userPrompt, intUIR);
    }

    public void checkoutCustomer(ShoppingBag<Food> shoppingBag){
        System.out.println("Processing payment");
        System.out.println("Money before payment: $" + customer.getMoney());
        customer.setMoney(customer.getMoney() - shoppingBag.getTotalPrice());
        System.out.println("Your remaining money: $"+ customer.getMoney());
    }

    public void takeOutPrompt() {
        ShoppingBag<Food> sB = new ShoppingBag<>();
        int customerMoneyLeft = customer.getMoney();

        boolean stillOrdering = true;
        while (stillOrdering) {
            System.out.println("Your remaining money: $" + customerMoneyLeft);
            Food food = this.getMenuSelection();
            if (customerMoneyLeft >= food.getPrice()) {
                customerMoneyLeft -= food.getPrice();
                sB.addItem(food);
            } else System.out.println("Not enough money.");
            stillOrdering = this.isStillOrderingFood();
            if (!stillOrdering) checkoutCustomer(sB);
        }
    }

    public void startTakeOutSimulator(){
        System.out.println("Hello " + customer.getName() + ",\nWelcome to my restaurant!");
        boolean continueSimulation = true;
        while(continueSimulation) {
            this.takeOutPrompt();
            continueSimulation = this.shouldSimulate();
        }
    }

}

