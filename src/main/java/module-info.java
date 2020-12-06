module SE01T13SEMPROJEKT {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires io.reactivex.rxjava3;
    requires com.google.gson;
    requires java.desktop;

    opens dk.sdu.mmmi.swe20.t1.g3.Views;
    opens dk.sdu.mmmi.swe20.t1.g3.Views.Partials;
    opens dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

    exports dk.sdu.mmmi.swe20.t1.g3.Views;
    exports dk.sdu.mmmi.swe20.t1.g3.Views.Objects;
    exports dk.sdu.mmmi.swe20.t1.g3.Views.Partials;
    exports dk.sdu.mmmi.swe20.t1.g3;
    exports worldofzuul;
}