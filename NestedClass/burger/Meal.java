// 因為 nested（巢狀）關係，兩個 class 可以互相存取彼此的所有成員（包含 private）
package NestedClass.burger;

import java.util.ArrayList;
import java.util.List;

public class Meal {
    private double price = 5.0;
    private Burger burger;
    private Item drink;
    private Item side;

    private double conversionRate;

    public Meal() {
        this(1);
    }

    public Meal(double conversionRate) {
        this.conversionRate = conversionRate;

        burger = new Burger("regular");
        drink = new Item("coke", "drink", 1.5);

        // 👉 outer class 直接用 inner 的 private
        System.out.println(drink.name);

        side = new Item("fries", "side", 2.0);
    }

    public double getTotal() {
        double total = burger.getPrice() + drink.price + side.price;
        return Item.getPrice(total, conversionRate);
    }

    @Override
    public String toString() {
        return "%s%n%s%n%s%n%26s$%.2f".formatted(
                burger,
                drink,
                side,
                "Total Due: ",
                getTotal());
    }

    // 對外提供簡單 API（Meal）
    public void addToppings(String... selectedToppings) {
        burger.addToppings(selectedToppings);
    }

    // =========================
    // Inner Class
    // =========================
    private class Item {

        private String name;
        private String type;
        private double price;

        // constructor 1
        public Item(String name, String type) {
            this(
                    name,
                    type,
                    // 明確指定「外層 Meal 物件的 price」
                    // 👉 inner class 直接用 outer 的 private
                    type.equals("burger") ? Meal.this.price : 0);
        }

        // constructor 2
        public Item(String name, String type, double price) {
            this.name = name;
            this.type = type;
            this.price = price;
        }

        @Override
        public String toString() {
            return "%10s%15s $%.2f".formatted(
                    type,
                    name,
                    getPrice(price, conversionRate));
        }

        private static double getPrice(double price, double rate) {
            return price * rate;
        }
    }

    // Burger 是 Meal 的內部細節（封裝）
    private class Burger extends Item {

        private enum Extra {
            AVOCADO, BACON, CHEESE, KETCHUP, MAYO, MUSTARD, PICKLES;

            private double getPrice() {
                return switch (this) {
                    case AVOCADO -> 1.0;
                    case BACON, CHEESE -> 1.5;
                    default -> 0;
                };
            }

        }

        private List<Item> toppings = new ArrayList<>();

        // 預設 package-private
        // 只有「同一個 package」裡的 class 才能存取
        Burger(String name) {
            super(name, "burger", 5.0);
        }

        public double getPrice() {

            // 我要拿的是父類 Item 的 price
            double total = super.price;
            for (Item topping : toppings) {
                total += topping.price;
            }
            return total;
        }

        // 對內處理細節（Burger）
        private void addToppings(String... selectedToppings) {

            for (String selectedTopping : selectedToppings) {
                try {
                    // Extra.valueOf("BACON") → Extra.BACON
                    Extra topping = Extra.valueOf(selectedTopping.toUpperCase());
                    toppings.add(new Item(topping.name(), "TOPPING",
                            topping.getPrice()));
                } catch (IllegalArgumentException ie) {
                    System.out.println("No topping found for " + selectedTopping);
                }
            }
        }

        @Override
        public String toString() {

            StringBuilder itemized = new StringBuilder(super.toString());
            for (Item topping : toppings) {
                itemized.append("\n");
                itemized.append(topping);
            }

            return itemized.toString();
        }

    }
}