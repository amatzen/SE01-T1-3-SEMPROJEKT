package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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

    public void promptOk(String title, String text, Runnable action) {
        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
        a.setHeaderText(title);
        a.setContentText(text);

        a.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK)
                action.run();
        });
    }

    public void emitNotify(String title, String text, int seconds) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .hideAfter(Duration.seconds(seconds))
                .position(Pos.BOTTOM_RIGHT);
        notificationBuilder.showInformation();
    }

    public double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    public double calculateDistanceBetweenPointAndLine(
        Point2D p,
        double a,
        double b,
        double c) {

        return Math.abs(((a * p.getX() + b * p.getY() + c)) /
                (Math.sqrt(a * a + b * b)));
    }

}
