package Interface;

enum FlightStages implements Trackable {
    GROUNDED, LAUNCH , CRUISE , DATA_COLLECTION;

    @Override
    public void track() {
        if (this != GROUNDED) {
            System.out.println("Monitoring" + this);
        }
    }

    public FlightStages getNextStage() {
        // values() 是 Java 自動幫 enum 產生的方法 [ GROUNDED, LAUNCH, CRUISE, DATA_COLLECTION ]
        FlightStages[] allStages = values();

        // ordinal() 取得目前位置（index）
        // % 長度 = 超過就回到起點
        return allStages[(ordinal() + 1) % allStages.length];
    }
}   

// 一般來說，record（記錄類別）是不會有 class body（類別主體）的。
// 但因為我正在實作 FlightEnabled 介面，所以這個 record 必須實作 FlightEnabled 中的抽象方法
record  DragonFly(String name, String type) implements FlightEnabled {

    @Override
    public void takeOff() {
    }

    @Override
    public void land() {
    }

    @Override
    public void fly() {
    }
}

class Satellite implements OrbitEarth {
    @Override
    public void achieveOrbit() {
        System.out.println("Orbit achieved");
    }

    @Override
    public void takeOff() {
    }

    @Override
    public void land() {
    }

    @Override
    public void fly() {
    };
}

interface OrbitEarth extends FlightEnabled {
    void achieveOrbit();

}

// 把它們全部當成同一種類型來看
// 也就是「會飛的東西」，而忽略各個類別之間的差異
interface FlightEnabled {
    // 介面讓我們可以用「行為」來定義物件的型別，而不是看它實際是哪一種類別
    double MILES_TO_KM = 1.609;
    double KM_TO_MILES = 0.621;

    void takeOff();

    void land();

    void fly();

    // 新需求：所有 FlightEnabled 的物件，都需要一個新方法 => 使用 default method
    default FlightStages transition(FlightStages stage) {
        // this 代表：取得「實際執行這個 method 的物件類別名稱」
        // 也可以寫成 getClass().getName()
        // System.out.println("transition not implemented for" + this.getClass().getName());
        // return null;
        FlightStages nextStage = stage.getNextStage();
        System.out.println("Transitioning from" + stage + "to" + nextStage);
        return nextStage;

    }
}

interface Trackable {
    void track();}

public abstract class Animal {
    public abstract void move();
}
