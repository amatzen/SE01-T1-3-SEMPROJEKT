package dk.sdu.mmmi.swe20.t1.g3.Config;

import dk.sdu.mmmi.swe20.t1.g3.Objects.Item;

import java.util.ArrayList;
import java.util.Map;

public enum Scenes {
    START(
            "start",
            "Starten",
            "Velkommen til Starten",
            Map.of(
                    "højre", "skov"
            )
    ),

    SKOV(
            "skov",
            "Skoven",
            "Velkommen til skoven",
            Map.of(
                    "højre", "strand",
                    "venstre", "start"
            )
    ),

    STRAND(
            "strand",
            "Stranden",
            "Velkommen til Stranden!",
            Map.of(
                    "venstre", "skov",
                    "højre", "strandv2",
                    "ned", "hav"
            )
    ),

    STRANDV2(
            "strandv2",
            "Strandenv2",
            "Du befinder dig stadig på Stranden!",
            Map.of(
                    "venstre", "strand",
                    "ned", "hav"
            )
    ),

    HAV(
            "hav",
            "Havet",
            "Velkommen til havet!",
            Map.of(
                    "op", "strand",
                    "ned","havbund",
                    "højre", "havv2",
                    "ventre", "havv3"
            )
    ),

    HAVV2(
            "havv2",
            "Havetv2",
            "Velkommen til havetv2!",
            Map.of(
                    "op", "strandv2",
                    "venstre", "hav",
                    "ned", "koralrev"
            )
    ),

    HAVV3(
            "havv3",
            "Havetv3",
            "Velkommen til havetv3!",
            Map.of(
                    "op", "strand",
                    "højre", "hav",
                    "ned", "skibsvrag"
            )
    ),

    HAVBUND("havbund",
            "Havbunden",
            "Velkommen til havbunden!",
            Map.of(
                    "op", "hav",
                    "højre", "koralrev",
                    "venstre","skibsvrag"
            )
    ),

    KORALREV("koralrev",
            "Koralrevet",
            "Velkommen til koralrevet!",
            Map.of(
                    "op", "hav",
                    "venstre", "havbund"
            ))
    ,

    SKIBSVRAG(
            "skibsvrag",
            "Skibsvraget",
            "Velkommen til skibsvraget!",
            Map.of(
                    "op", "hav",
                    "højre", "havbund"
            )
    );



    private String slug, name, description;
    private Map<String, String> exits;
    private ArrayList<Item> items;

    Scenes(String slug, String name, String description, Map<String, String> exits) {
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.exits = exits;
    };
    Scenes(String slug, String name, String description, Map<String, String> exits, ArrayList<Item> items) {
        this.slug = slug;
        this.name = name;
        this.description = description;
        this.exits = exits;
        this.items = items;
    };

}
