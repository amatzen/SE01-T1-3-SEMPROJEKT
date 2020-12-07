package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import javafx.geometry.Pos;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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

    public void emitNotify(String title, String text, int seconds) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(seconds))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showInformation();
    }
}
