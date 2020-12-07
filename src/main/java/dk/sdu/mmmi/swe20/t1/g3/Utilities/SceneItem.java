package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;

public class SceneItem {
    private Scene scene;
    private Item item;

    public SceneItem(Scene scene, Item item) {
        this.scene = scene;
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public Scene getScene() {
        return scene;
    }
}
