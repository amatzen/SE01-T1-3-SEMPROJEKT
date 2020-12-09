package dk.sdu.mmmi.swe20.t1.g3.Config;

import java.util.Map;
import java.util.stream.Collectors;

import static dk.sdu.mmmi.swe20.t1.g3.Config.Direction.*;

public enum Scenes {
    START(
            "start",
            "Starten",
            "Velkommen til Starten",
            "Views/Scenes/01_START.png",
            Map.of(
                    RIGHT, "skov"
            )
    ),

    SKOV(
            "skov",
            "Strandindgangen",
            "Velkommen til Strandindgangen",
            "Views/Scenes/02_LAND_STRAND.png",
            Map.of(
                    RIGHT, "strand",
                    LEFT, "start"
            )
    ),

    STRAND(
            "strand",
            "Stranden",
            "Velkommen til Stranden!",
            "Views/Scenes/07_STRAND_HAV.png",
            Map.of(
                    LEFT, "skov",
                    RIGHT, "hav",
                    DOWN, "strandv2"
            )
    ),

    STRANDV2(
            "strandv2",
            "Strandenv2",
            "Du befinder dig stadig på Stranden!",
            "Views/Scenes/04, strand_vand.png",
            Map.of(
                    RIGHT, "hav",
                    UP, "strand"
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
                    RIGHT, "havv3",
                    LEFT, "havv2"
            )
    ),

    HAVV2(
            "havv2",
            "Havetv2",
            "Velkommen til havetv2!",
            "",
            Map.of(
                    UP, "strandv2",
                    RIGHT, "hav",
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
                    LEFT, "hav",
                    DOWN, "skibsvrag"
            )
    ),

    HAVBUND("havbund",
            "Havbunden",
            "Velkommen til havbunden!",
            "",
            Map.of(
                    UP, "hav",
                    LEFT, "koralrev",
                    RIGHT,"skibsvrag"
            )
    ),

    KORALREV("koralrev",
            "Koralrevet",
            "Velkommen til koralrevet!",
            "",
            Map.of(
                    UP, "havv2",
                    RIGHT, "havbund"
            )
    ),

    SKIBSVRAG(
            "skibsvrag",
            "Skibsvraget",
            "Velkommen til skibsvraget!",
            "",
            Map.of(
                    UP, "hav",
                    LEFT, "havbund"
            )
    );



    private String slug, name, description, fxmlScene;
    private Map<String, String> exits;
    private String sceneURL;

    Scenes(String slug, String name, String description, String sceneURL, Map<Direction, String> exits) {
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.sceneURL = sceneURL;
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

    public String getSceneURL() { return sceneURL; }
}
