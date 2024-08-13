import org.opencv.core.*;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.imgproc.Imgproc;
import org.opencv.videoio.VideoCapture;

public class ObjectDetection {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        VideoCapture capture = new VideoCapture(0);
        if (!capture.isOpened()) {
            System.out.println("Error: Cannot open video stream");
            return;
        }

        CascadeClassifier faceDetector = new CascadeClassifier("path_to_haarcascade.xml");
        Mat frame = new Mat();
        MatOfRect faceDetections = new MatOfRect();

        while (capture.read(frame)) {
            faceDetector.detectMultiScale(frame, faceDetections);
            for (Rect rect : faceDetections.toArray()) {
                Imgproc.rectangle(frame, new Point(rect.x, rect.y),
                        new Point(rect.x + rect.width, rect.y + rect.height),
                        new Scalar(0, 255, 0));
            }


        }

        capture.release();
    }
}
