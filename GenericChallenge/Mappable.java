package GenericChallenge;

import java.util.Arrays;

public interface Mappable {
    void render();

    // 在 Java 8 之後：  介面可以有 static 方法
    static double[] stringToLatLon(String location) {
        var split = location.split(",");
        double lat = Double.valueOf(split[0]);
        double lng = Double.valueOf(split[1]);

        return new double[] { lat, lng };
    }
}

abstract class Point implements Mappable {
    private double[] location = new double[2];

    public Point(String location) {
        this.location = Mappable.stringToLatLon(location);
    }

    @Override
    public void render() {
        System.out.println("Render" + this + "as POINT (" + location() + ")");
    }

    // Encapsulation
    private String location() {
        return Arrays.toString(location);
    }
}

abstract class Line implements Mappable {

    @Override
    public void render() {
        System.out.println("Render" + this + "as Lines (" + locations() + ")");
    }

    private double[][] locations;

    public Line(String... locations) {
        this.locations = new double[locations.length][];
        int index = 0;
        for (var l : locations) {
            this.locations[index++] = Mappable.stringToLatLon(l);
        }
    }

    private String locations() {
        // 把「多維陣列（尤其是巢狀陣列）」轉成好讀的字串
        return Arrays.deepToString(locations);
    }
}