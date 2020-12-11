package dk.sdu.mmmi.swe20.t1.g3.Views.Objects;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

public class G_Menu extends Application {

    private static final Font FONT = Font.font("", FontWeight.THIN, 18);

    private VBox menuBox;
    private int currentItem = 0;


    private ScheduledExecutorService bgThread = Executors.newSingleThreadScheduledExecutor();

    private Parent createContent() {
        Pane root = new Pane();
        root.setPrefSize(900, 600);

        ImageView imageView = new ImageView(new Image(getClass()
                .getResource("trueVilley.png").toExternalForm()));

        imageView.setFitWidth(900);
        imageView.setFitHeight(600);




        ContentFrame frame3 = new ContentFrame(createRightContent());

        HBox hbox = new HBox(15, frame3);
        hbox.setTranslateX(25);
        hbox.setTranslateY(470);
        hbox.setOpacity(0.5);

        frame3.setAlignment(Pos.BASELINE_CENTER);

        MenuItem itemExit = new MenuItem("EXIT");
        itemExit.setOnActivate(() -> System.exit(0));

        menuBox = new VBox(10,
                new MenuItem("Vend tilbage til spillet"),

                itemExit);
        menuBox.setAlignment(Pos.TOP_CENTER);
        menuBox.setTranslateX(360);
        menuBox.setTranslateY(300);


        getMenuItem(0).setActive(true);

        root.getChildren().addAll(imageView, hbox, menuBox);
        return root;
    }



    private Node createRightContent() {
        String title = "Velkommen til World Of Fish!!";
        HBox letters = new HBox(0);
        letters.setAlignment(Pos.CENTER);
        for (int i = 0; i < title.length(); i++) {
            Text letter = new Text(title.charAt(i) + "");
            letter.setFont(FONT);
            letter.setFill(Color.WHITE);
            letter.setOpacity(0);
            letters.getChildren().add(letter);

            FadeTransition ft = new FadeTransition(Duration.seconds(2), letter);
            ft.setDelay(Duration.millis(i * 100));
            ft.setToValue(1);
            ft.setAutoReverse(true);
            ft.setCycleCount(TranslateTransition.INDEFINITE);
            ft.play();
        }

        return letters;
    }

    private MenuItem getMenuItem(int index) {
        return (MenuItem)menuBox.getChildren().get(index);
    }

    private static class ContentFrame extends StackPane {
        public ContentFrame(Node content) {
            setAlignment(Pos.TOP_CENTER);

            Rectangle frame = new Rectangle(275, 100);
            frame.setArcWidth(25);
            frame.setArcHeight(25);
            frame.setStroke(Color.BLACK);

            getChildren().addAll(frame, content);
        }
    }

    private static class MenuItem extends HBox {


        private Text text;
        private Runnable script;

        public MenuItem(String name) {
            super(15);
            setAlignment(Pos.CENTER);

            text = new Text(name);
            text.setFont(FONT);
            text.setEffect(new GaussianBlur(2));

            getChildren().addAll(text);
            setActive(false);
            setOnActivate(() -> System.out.println(name + "activated"));
        }

        public void setActive(boolean b) {

            text.setFill(b ? Color.WHITE : Color.BLACK);
        }

        public void setOnActivate(Runnable r) {
            script = r;
        }

        public void activate() {
            if (script != null)
                script.run();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.UP) {
                if (currentItem > 0) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(--currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.DOWN) {
                if (currentItem < menuBox.getChildren().size() - 1) {
                    getMenuItem(currentItem).setActive(false);
                    getMenuItem(++currentItem).setActive(true);
                }
            }

            if (event.getCode() == KeyCode.ENTER) {
                getMenuItem(currentItem).activate();
            }
        });

        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> {
            bgThread.shutdownNow();
        });
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
