import java.util.ArrayList;
import java.util.List;

public class FoodMenu{
    private final List<Food> menu = new ArrayList<>();

    public FoodMenu(){
        menu.add(new Food("Guacamole","from avocado",10));
        menu.add(new Food("Mexican burrito","vege, not spicy",200));
        menu.add(new Food("Chicken","with rice and creatine",12));
        menu.add(new Food("Rice","with creatine and chicken",12));
        menu.add(new Food("Creatine","with chicken and rice",12));
        menu.add(new Food("Ice cream","sugar lactose and taste -free",5));
    }

    @Override
    public String toString(){
        StringBuilder text = new StringBuilder();
        for (int i = 1; i< menu.size()+1; i++){
            text.append(i).append(". ").append(menu.get(i - 1).toString());
        }
        return text.toString();
    }

    public Food getFood(int index){
        if(index < 1 || index > menu.size()) return null;
        else return menu.get(index - 1);
    }

    public Food getLowestCostFood(){
        int lowestPrice = 1000;
        Food cheapestFood = null;
        for (Food item : menu){
            if (item.getPrice() < lowestPrice) cheapestFood = item;
        }
        return cheapestFood;
    }
}