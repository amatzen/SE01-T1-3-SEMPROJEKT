package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import javafx.scene.control.TextInputDialog;

import java.util.Optional;

public class FXUtils {
    public String prompt(String title, String text) {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText(text);

        Optional<String> res = dialog.showAndWait();

        return res.orElse("");
    }
}
