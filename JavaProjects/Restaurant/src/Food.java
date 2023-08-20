// Create Food class here
public class Food implements PricedItem<Integer>{
    private final String name;
    private final String description;
    private int price;

    public Food(String name, String desc, int price){
        this.name = name;
        this.description = desc;
        this.price = price;
    }

    @Override
    public void setPrice(Integer price){
        this.price = price;
    }

    @Override
    public Integer getPrice(){
        return this.price;
    }

    @Override
    public String toString(){
        return this.name + ". Description: " + this.description + ". Cost: " + this.price + "\n";
    }
}