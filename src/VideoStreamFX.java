import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.videoio.VideoCapture;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javafx.embed.swing.SwingFXUtils;
import javafx.application.Platform;

public class VideoStreamFX {
    static {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
    }

    private CascadeClassifier faceCascade;
    private VideoCapture videoCapture;
    private boolean isRunning;
    private ImageView imageView;

    public VideoStreamFX(ImageView imageView, String faceCascadePath) {
        faceCascade = new CascadeClassifier(faceCascadePath);
        videoCapture = new VideoCapture();
        isRunning = false;
        this.imageView = imageView;
    }

    public void startStream() {
        if (!isRunning) {
            videoCapture.open(0);
            isRunning = true;
            new Thread(() -> {
                Mat frame = new Mat();
                while (isRunning) {
                    if (videoCapture.read(frame) && !frame.empty()) {
                        detectObjects(frame);
                        Image image = matToImage(frame);
                        updateImageView(image);
                    }
                }
                videoCapture.release();
            }).start();
        }
    }

    private void detectObjects(Mat frame) {
        Mat gray = new Mat();
        Imgproc.cvtColor(frame, gray, Imgproc.COLOR_BGR2GRAY);
        MatOfRect faces = new MatOfRect();
        faceCascade.detectMultiScale(
                gray,
                faces,
                1.1,
                3,
                0,
                new Size(30, 30),
                new Size()
        );

        Rect[] faceArray = faces.toArray();
        if (faceArray.length == 0) {
            detectAndLabelObjects(frame, gray);
        } else {
            for (Rect face : faceArray) {
                Imgproc.rectangle(frame, face.tl(), face.br(), new Scalar(0, 255, 0), 3);
                String label = "Face Detected";
                Imgproc.putText(frame, label, new Point(face.x, face.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0, 255, 0), 2);
            }
        }
    }

    private void detectAndLabelObjects(Mat frame, Mat gray) {
        Mat colorFrame = new Mat();
        Imgproc.cvtColor(gray, colorFrame, Imgproc.COLOR_GRAY2BGR);
        MatOfRect objects = new MatOfRect();
        faceCascade.detectMultiScale(
                gray,
                objects,
                1.1,
                3,
                0,
                new Size(30, 30),
                new Size()
        );

        Rect[] objectArray = objects.toArray();
        for (Rect object : objectArray) {
            Imgproc.rectangle(frame, object.tl(), object.br(), new Scalar(0, 0, 255), 3);
            String label = "Object Detected";
            Imgproc.putText(frame, label, new Point(object.x, object.y - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.9, new Scalar(0, 0, 255), 2);
        }
    }

    private Image matToImage(Mat mat) {
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (mat.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        BufferedImage bufferedImage = new BufferedImage(mat.cols(), mat.rows(), type);
        byte[] data = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
        mat.get(0, 0, data);
        return SwingFXUtils.toFXImage(bufferedImage, null);
    }

    private void updateImageView(Image image) {
        Platform.runLater(() -> imageView.setImage(image));
    }

    public void stopStream() {
        isRunning = false;
    }
}
