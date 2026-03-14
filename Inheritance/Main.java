package Inheritance;

public class Main {
    public static void main(String[] args) {
        Animal animal = new Animal("Generic", "Huge", 100);
        doAnimalStuff(animal, "slow");

        Dog dog = new Dog();
        doAnimalStuff(dog, "fast");

        Dog yorkie = new Dog("Yorkie", 20);
        doAnimalStuff(yorkie, "fast");

        Dog wolf = new Dog("wolf", 40);
        doAnimalStuff(wolf, "slow");

        Fish goldie = new Fish("Goldfish", 0.25, 2, 3);
        doAnimalStuff(goldie, "fast");
    }

    public static void doAnimalStuff(Animal animal, String speed) {
        animal.makeNoise();
        animal.move(speed);
        System.out.println(animal);
    }
}
