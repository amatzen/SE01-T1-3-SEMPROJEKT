package dk.sdu.mmmi.swe20.t1.g3.Config;

import java.util.Map;
import java.util.stream.Collectors;

import static dk.sdu.mmmi.swe20.t1.g3.Config.Direction.*;

// TODO: JavaFX

public enum Scenes {
    START(
            "start",
            "Starten",
            "Velkommen til Starten",
            "01_START",
            Map.of(
                    RIGHT, "skov"
            )
    ),

    SKOV(
            "skov",
            "Skoven",
            "Velkommen til skoven",
            "02_SKOV",
            Map.of(
                    RIGHT, "strand",
                    LEFT, "start"
            )
    ),

    STRAND(
            "strand",
            "Stranden",
            "Velkommen til Stranden!",
            "03_STRAND",
            Map.of(
                    LEFT, "skov",
                    RIGHT, "strandv2",
                    DOWN, "hav"
            )
    ),

    STRANDV2(
            "strandv2",
            "Strandenv2",
            "Du befinder dig stadig på Stranden!",
            "",
            Map.of(
                    LEFT, "strand",
                    DOWN, "hav"
            )
    ),

    HAV(
            "hav",
            "Havet",
            "Velkommen til havet!",
            "",
            Map.of(
                    UP, "strand",
                    DOWN,"havbund",
                    RIGHT, "havv2",
                    LEFT, "havv3"
            )
    ),

    HAVV2(
            "havv2",
            "Havetv2",
            "Velkommen til havetv2!",
            "",
            Map.of(
                    UP, "strandv2",
                    LEFT, "hav",
                    DOWN, "koralrev"
            )
    ),

    HAVV3(
            "havv3",
            "Havetv3",
            "Velkommen til havetv3!",
            "",
            Map.of(
                    UP, "strand",
                    RIGHT, "hav",
                    DOWN, "skibsvrag"
            )
    ),

    HAVBUND("havbund",
            "Havbunden",
            "Velkommen til havbunden!",
            "",
            Map.of(
                    UP, "hav",
                    RIGHT, "koralrev",
                    LEFT,"skibsvrag"
            )
    ),

    KORALREV("koralrev",
            "Koralrevet",
            "Velkommen til koralrevet!",
            "",
            Map.of(
                    UP, "hav",
                    LEFT, "havbund"
            )
    ),

    SKIBSVRAG(
            "skibsvrag",
            "Skibsvraget",
            "Velkommen til skibsvraget!",
            "",
            Map.of(
                    UP, "hav",
                    RIGHT, "havbund"
            )
    );



    private String slug, name, description, fxmlScene;
    private Map<String, String> exits;

    Scenes(String slug, String name, String description, String fxmlScene, Map<Direction, String> exits) {
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.fxmlScene = fxmlScene;
        this.exits = exits.entrySet().stream()
            .collect(Collectors.toMap(e->e.getKey().getDirectionString(), Map.Entry::getValue));
    };

    public String getSlug() {
        return slug;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Map<String, String> getExitsString() {
        return exits;
    }

}
