import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

public class VideoStream {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture capture = new VideoCapture(0); // 0 for default camera
        if (!capture.isOpened()) {
            System.out.println("Error: Cannot open video stream");
            return;
        }

        Mat frame = new Mat();
        while (capture.read(frame)) {
            // Process the frame
            System.out.println("Frame captured");
        }

        capture.release();
    }
}

