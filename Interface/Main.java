package Interface;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        Bird bird = new Bird();
        Animal animal = bird;
        FlightEnabled flier = bird;
        Trackable tracked = bird;

        animal.move();
        // flier.move(); ==> 沒有move method
        // tracked.move()

        // flier.takeOff();
        // flier.fly();
        // tracked.track();
        // flier.land();
        inFlight(flier);
        inFlight(new Jet());
        Trackable truck = new Truck();
        truck.track();

        double kmsTraveled = 100;
        double milesTraveled = kmsTraveled;
        System.out.printf("The truck traveled %.2f km or %.2f miles%n",
                kmsTraveled, milesTraveled);

        LinkedList<FlightEnabled> fliers = new LinkedList<>();
        fliers.add(bird);

        //  這是比上面好的寫法
        // 因為：
        // List 是 介面 Interface 而 ArrayList 是 類別 Class
        // 如果你寫 ArrayList , 未來想換 LinkedList 要改很多 code
        /*
        可替換性（flexibility）
        List<FlightEnabled> f = new ArrayList<>();
        
        可以改成：
        List<FlightEnabled> f = new LinkedList<>();
        */
        List<FlightEnabled> betterFliers = new LinkedList<>();
        fliers.add(bird);

        triggerFliers(fliers);
        flyFliers(fliers);
        landFliers(fliers);

        triggerFliers(betterFliers);
        flyFliers(betterFliers);
        landFliers(betterFliers);
    }

    private static void inFlight(FlightEnabled flier) {
        flier.takeOff();
        flier.fly();
        // 為什麼已經是 FlightEnabled，還要再判斷 Trackable?
        // 編譯器只保證 flier 有 FlightEnabled 的能力
        // 若不是所有 FlightEnabled 都是 Trackable 會爆炸
        if (flier instanceof Trackable tracked) {
            tracked.track();
        }
        flier.land();
    }

    private static void triggerFliers(List<FlightEnabled> fliers) {
        for (var flier : fliers) {
            flier.takeOff();
        }
    }

    private static void flyFliers(List<FlightEnabled> fliers) {
        for (var flier : fliers) {
            flier.fly();
        }
    }

    private static void landFliers(List<FlightEnabled> fliers) {
        for (var flier : fliers) {
            flier.land();
        }
    }
}

