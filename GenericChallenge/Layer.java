package GenericChallenge;

import java.util.ArrayList;
import java.util.List;

// Layer 裡的東西 必須是 Mappable , 或是實作 Mappable 的類別
public class Layer <T extends Mappable> {

    private List<T> layerElements;

    public Layer(T[] layerElements) {
        // List.of(...)      // 變 List（但不能改）
        //  ↓
        // new ArrayList<>(...) // 變成可以改的 List
        this.layerElements = new ArrayList<T>(List.of(layerElements));
    }

    public void addElements(T... elements) {
        layerElements.addAll(List.of(elements));
    }

    public void renderLayer() {

        for (T element : layerElements) {
            element.render();
        }
    }
}
