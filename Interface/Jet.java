package Interface;

public class Jet implements FlightEnabled , Trackable {
    @Override
    public void track() {
        System.out.println(getClass().getSimpleName() + "'s coordinates recorded");
    }

    @Override
    public void takeOff() {
        System.out.println(getClass().getSimpleName() + "is taking off");
    }

    @Override
    public void land() {
        System.out.println(getClass().getSimpleName() + "is landing");
    }

    @Override
    public void fly() {
        System.out.println(getClass().getSimpleName() + "is flying");
    }

    @Override
    public FlightStages transition(FlightStages stage) {
        System.out.println(getClass().getSimpleName() + " transitioning");
        // interface 不在繼承鏈裡 , Jet 沒有父類別有 transition()
        //  所以不能寫 super.transition(stage);

        // 呼叫 interface default method 時，必須「指定是哪個 interface」
        // 一個 class 可以 implement 多個 interface
        // 所以你一定要寫 A.super.method() or  B.super.method()
        return FlightEnabled.super.transition(stage);
    }
}
