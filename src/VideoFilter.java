import in.ac.abes.care.utils.FrameDisplay;
import in.ac.abes.care.utils.Utils;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;

public class VideoFilter {
	static {
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

	}

	public static void main(String[] args) {
		/*
		 * FrameDisplay imageDisplay=new FrameDisplay("Ïmage"); //FrameDisplay
		 * imageDisplay1=new FrameDisplay("Ïmage"); Mat
		 * image=Highgui.imread("contours.png"); Mat contoursMat=new Mat(); //
		 * Imgproc.cvtColor(image, image, Imgproc.COLOR_BGR2HSV);
		 * contoursMat=image.clone(); Imgproc.cvtColor(image, image,
		 * Imgproc.COLOR_BGR2GRAY); Imgproc.threshold(image, image, 122, 255,
		 * Imgproc.THRESH_BINARY); ArrayList<MatOfPoint> contours=new
		 * ArrayList<MatOfPoint>(); Imgproc.findContours(image, contours, new
		 * Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);
		 * Imgproc.drawContours(contoursMat, contours, 8 ,new Scalar(0,0,255),
		 * 3);
		 * 
		 * // imageDisplay1.updateFrame(Utils.fromMatToBufferedImage(image));
		 * imageDisplay.updateFrame(Utils.fromMatToBufferedImage(contoursMat));
		 * 
		 * 
		 * // System.out.println(image.channels());
		 */

		Mat frame = new Mat();
		Mat filteredMAt = new Mat();
		// Mat filteredMat1=new Mat();
		Mat erodedMat = new Mat();
		Mat originalFrame = new Mat();

		Scalar lowerb = new Scalar(25, 70, 160);
		Scalar upperb = new Scalar(108, 102, 208
				);
	//	Scalar lowera = new Scalar(65, 10, 160);
	//	Scalar uppera = new Scalar(95, 100, 250);

		Mat kernel = Imgproc.getStructuringElement(Imgproc.MORPH_RECT, new Size(5, 5));

		//Mat contoursMat = new Mat();
		ArrayList<MatOfPoint> contours = new ArrayList<MatOfPoint>();

		FrameDisplay frameDisplay = new FrameDisplay("Frame Display");
		//FrameDisplay filteredDisplay = new FrameDisplay("Filtered Display");
		//FrameDisplay filteredDisplay1 = new FrameDisplay("Eroded Display");

		VideoCapture capture = new VideoCapture(0);

		if (!capture.isOpened()) {
			return;
		}

		for (int i = 0; i < 5000; i++) {
			capture.read(frame);
			if (frame.empty()) {
				continue;
			}

			originalFrame = frame.clone();
			// Core.putText(frame, "Anuj", new Point(200,200),
			// Core.FONT_HERSHEY_SCRIPT_SIMPLEX, 1.0, new Scalar(0,255,0));
			Core.rectangle(frame, new Point(50, 50), new Point(150, 100), new Scalar(014, 255, 25));
			// Core.circle(frame, new Point(80,80),100 , new Scalar(0,0,255),
			// 5);

			Imgproc.cvtColor(frame, frame, Imgproc.COLOR_BGR2HSV);
			Core.inRange(frame, lowerb, upperb, filteredMAt);
			// Core.inRange(frame, lowera, uppera, filteredMat1);
			Imgproc.erode(filteredMAt, erodedMat, kernel);

			contours.clear();

			Imgproc.findContours(erodedMat, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_SIMPLE);

			for (int j = 0; j < contours.size(); j++) {

				MatOfPoint contour = contours.get(j);
				double area = Imgproc.contourArea(contour);

				if (area > 1000) 
				{
					Imgproc.drawContours(originalFrame, contours, j, new Scalar(0, 0, 255), 1);
					Rect rect=Imgproc.boundingRect(contours.get(j));
					Point cg=new Point(rect.x+rect.width/2,rect.y+rect.height/2);
					Core.circle(originalFrame, cg, 8, new Scalar(0,0,255));
					

				}

			}

			frameDisplay.updateFrame(Utils.fromMatToBufferedImage(originalFrame));

			//filteredDisplay.updateFrame(Utils.fromMatToBufferedImage(filteredMAt));
			// filteredDisplay1.updateFrame(Utils.fromMatToBufferedImage(filteredMat1));
			//filteredDisplay1.updateFrame(Utils.fromMatToBufferedImage(erodedMat));
			
			
			
		}

	}

}
