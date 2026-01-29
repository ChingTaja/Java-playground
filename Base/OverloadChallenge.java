
package Base;

public class OverloadChallenge {
    public static void main(String[] args) {
        System.out.println((getDurationString(3945)));

        System.out.println((getDurationString(65, 45)));
    }

    public static String getDurationString(int seconds) {
        int minutes = seconds / 60;
        int hours = minutes / 60;
        System.out.println(hours);

        int remainingMinutes = minutes % 60;
        System.out.println(remainingMinutes);

        int remainingSeconds = seconds % 60;

        return hours + "h" + remainingMinutes + "m" + remainingSeconds + "s";
    }
    
    public static String getDurationString(int minutes, int seconds) {
        return "";
    }
}
