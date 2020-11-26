package dk.sdu.mmmi.swe20.t1.g3;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import static com.almasb.fxgl.dsl.FXGL.entityBuilder;

public class GameFactory implements EntityFactory {
    @Spawns("player")
    public Entity newPlayer(SpawnData spawnData) {
        return entityBuilder()
                .from(spawnData)
                .type(Main.EntityType.PLAYER)
                .view(FXGL.getAssetLoader().loadTexture("player.png"))
                .build();
    }

    @Spawns("item")
    public Entity newItem(SpawnData spawnData) {
        return entityBuilder()
                .from(spawnData)
                .type(Main.EntityType.ITEM)
                .view(new Rectangle(40, 40, Color.BLUE))
                .build();
    }


}
