import java.util.HashMap;
import java.util.Map;

public class ShoppingBag<T extends PricedItem<Integer>> {
    private final Map<T, Integer> shoppingBag;

    public ShoppingBag(){
        shoppingBag = new HashMap<>();
    }

    public void addItem(T item){
        shoppingBag.merge(item, 1, Integer::sum);
    }

    public int getTotalPrice(){
        int price = 0;
        for (T item : shoppingBag.keySet()){
            price += item.getPrice() * shoppingBag.get(item);
        }
        return price;
    }

}