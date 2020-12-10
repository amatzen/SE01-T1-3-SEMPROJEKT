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
                    RIGHT, "strandindgang"
            )
    ),

    STRANDINDGANG(
            "strandindgang",
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
                    LEFT, "strandindgang",
                    RIGHT, "hav",
                    DOWN, "strandv2"
            )
    ),

    STRANDV2(
            "strandv2",
            "Strandenv2",
            "Du befinder dig stadig på Stranden!",
            "Views/Scenes/08_STRAND_HAV.png",
            Map.of(
                    RIGHT, "hav",
                    UP, "strand",
                    LEFT, "strandindgang"

            )
    ),

    HAV(
            "hav",
            "Havet",
            "Velkommen til havet!",
            "Views/Scenes/09_HAVOVERFLADE_MED_BRO.png",
            Map.of(
                    DOWN,"koralrev",
                    RIGHT, "havv2",
                    LEFT, "strand"
            )
    ),
    HAVV2(
            "havv2",
            "Havetv2",
            "Velkommen til Havetv2",
            "Views/Scenes/10_HAVOVERFLADE.png",
            Map.of(
                    LEFT, "hav",
                    RIGHT, "klippetop",
                    DOWN, "skibsvrag"
            )
    ),

    KLIPPETOP(
            "klippetop",
            "Klippetoppen",
            "Velkommen til havetv3!",
            "Views/Scenes/11_HAVOVERFLADE_MED_KANT.png",
            Map.of(
                    LEFT, "havv2",
                    DOWN, "klippekant"
            )
    ),

    SKIBSVRAG("skibsvrag",
            "Skibsvraget",
            "Velkommen til skibsvraget!",
            "Views/Scenes/16_HAVBUND_MED_SKIBSSKROG.png",
            Map.of(
                    UP, "havv2",
                    LEFT, "koralrev",
                    RIGHT,"klippekant"
            )
    ),

    KORALREV("koralrev",
            "Koralrevet",
            "Velkommen til koralrevet!",
            "Views/Scenes/14_KORALREV_HAVBUND.png",
            Map.of(
                    UP, "hav",
                    RIGHT, "skibsvrag"
            )
    ),

    KLIPPEKANT(
            "klippekant",
            "Klippekant",
            "Velkommen til undervandklippen!",
            "Views/Scenes/17_HAVBUND_MED_KANT.png",
            Map.of(
                    UP, "klippetop",
                    LEFT, "skibsvrag"
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
