import javafx.scene.image.Image;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import org.opencv.core.Mat;
import org.opencv.core.CvType;

public class Utils {
    public static Image mat2Image(Mat frame) {
        WritableImage writableImage = new WritableImage(frame.width(), frame.height());
        PixelWriter pw = writableImage.getPixelWriter();

        for (int y = 0; y < frame.height(); y++) {
            for (int x = 0; x < frame.width(); x++) {
                double[] pixel = frame.get(y, x);
                pw.setColor(x, y, Color.rgb((int) pixel[0], (int) pixel[1], (int) pixel[2]));
            }
        }
        return writableImage;
    }
}

