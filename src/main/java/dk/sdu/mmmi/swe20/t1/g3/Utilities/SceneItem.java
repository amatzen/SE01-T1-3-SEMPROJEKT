package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Scene;

/**
 * The type Scene item.
 */
public class SceneItem {
    private final Scene scene;
    private final Item item;

    /**
     * Instantiates a new Scene item.
     *
     * @param scene the scene
     * @param item  the item
     */
    public SceneItem(Scene scene, Item item) {
        this.scene = scene;
        this.item = item;
    }

    /**
     * Gets item.
     *
     * @return the item
     */
    public Item getItem() {
        return item;
    }

    /**
     * Gets scene.
     *
     * @return the scene
     */
    public Scene getScene() {
        return scene;
    }
}
