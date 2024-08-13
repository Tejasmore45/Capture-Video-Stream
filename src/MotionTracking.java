import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

public class MotionTracking {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error: Cannot open video stream");
            return;
        }

        BackgroundSubtractorMOG2 subtractor = Video.createBackgroundSubtractorMOG2();
        Mat frame = new Mat();
        Mat fgMask = new Mat();

        while (capture.read(frame)) {
            subtractor.apply(frame, fgMask);


        }

        capture.release();
    }
}
