import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private VideoStreamFX videoStreamFX;
    private ImageView imageView;

    @Override
    public void start(Stage primaryStage) {
        imageView = new ImageView();


        String faceCascadePath = "C:\\Users\\tejas\\opencv\\data\\haarcascades\\haarcascade_frontalface_default.xml";
        videoStreamFX = new VideoStreamFX(imageView, faceCascadePath);

        Button startButton = new Button("Start Video Stream");
        Button stopButton = new Button("Stop Video Stream");

        startButton.setOnAction(event -> videoStreamFX.startStream());
        stopButton.setOnAction(event -> videoStreamFX.stopStream());

        VBox root = new VBox(10, imageView, startButton, stopButton);
        Scene scene = new Scene(root, 800, 600);

        primaryStage.setTitle("Video Stream Analysis");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
