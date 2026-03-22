package Restaurant;

public class Main {
    public static void main(String[] args) {
        // Item coke = new Item("drink", "coke", 1.50);
        // coke.printItem();
        // coke.setSize("LARGE");
        // coke.printItem();

        // MealOrder regularMeal = new MealOrder();
        // regularMeal.addBurgerToppings(
        //         "BACON", "CHEESE", "MAYO");
        // regularMeal.setDrinkSize("LARGE");
        // regularMeal.printItemizedList();

        MealOrder deluxeMeal = new MealOrder("deluxe", "7-up", "cjhili");

        deluxeMeal.addBurgerToppings("AVOCADO", "BACON", "LETTUCE", "CHEESE", "MAYO");
        deluxeMeal.setDrinkSize("SMALL");
        deluxeMeal.printItemizedList();
    }
}
