
package Base;

public class OverloadChallenge {
    public static void main(String[] args) {
        System.out.println((getDurationString(-3945)));

        System.out.println((getDurationString(-65, 45)));

        System.out.println((getDurationString(65, 145)));
    }

    public static String getDurationString(int seconds) {
        if (seconds < 0) {
            return "Invalid data for seconds" + seconds + "must be a positive integer value";
        }

        int minutes = seconds / 60;
        return getDurationString(minutes / 60, seconds % 60);
    }
    
    public static String getDurationString(int minutes, int seconds) {
        if (minutes < 0) {
            return "Invalid data for minutes" + minutes + "must be a positive integer value";
        }

        if (seconds < 0 || seconds > 59) {
           return "Invalid data for seconds";
        }
        int hours = minutes / 60;
        System.out.println(hours);

        int remainingMinutes = minutes % 60;
        System.out.println(remainingMinutes);

        return hours + "h" + remainingMinutes + "m" + seconds + "s";
    }
}
