package dk.sdu.mmmi.swe20.t1.g3.Utilities;

import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * The type Fx utils.
 */
public class FXUtils {
    /**
     * Prompt Dialog.
     *
     * @param title the title
     * @param text  the text
     * @return the string
     */
    public String prompt(String title, String text) {
        TextInputDialog dialog = new TextInputDialog();

        dialog.setTitle(title);
        dialog.setHeaderText(title);
        dialog.setContentText(text);

        Optional<String> res = dialog.showAndWait();

        return res.orElse("");
    }

    /**
     * Calculate distance between points.
     *
     * @param x1 the x 1
     * @param y1 the y 1
     * @param x2 the x 2
     * @param y2 the y 2
     * @return the double
     */
    public double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }

    /**
     * Calculate distance between point and line.
     *
     * @param p the p
     * @param a the a
     * @param b the b
     * @param c the c
     * @return the double
     */
    public double calculateDistanceBetweenPointAndLine(
            Point2D p,
            double a,
            double b,
            double c) {

        return Math.abs(((a * p.getX() + b * p.getY() + c)) /
                (Math.sqrt(a * a + b * b)));
    }

}
