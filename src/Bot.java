import in.ac.abes.care.utils.FrameDisplay;
import in.ac.abes.care.utils.Utils;

import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.VideoCapture;
import org.opencv.imgproc.Imgproc;


public class Bot {
	static
	{
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String args[])
	{
		Mat Filteredmat = new Mat();
		Mat Frame = new Mat();
		
		FrameDisplay framedisplay = new FrameDisplay("Video Feed");
		
		Scalar lowera = new Scalar(0,0,255);
		Scalar uppera = new Scalar (0,0,255);
		
		Mat kernal = Imgproc.getStructuringElement(Imgproc.MORPH_OPEN,new Size(10,10));
		
		ArrayList<MatOfPoint> contours1 = new ArrayList<MatOfPoint>();
		ArrayList<MatOfPoint> contours2 = new ArrayList<MatOfPoint>();
		ArrayList<MatOfPoint> contours3 = new ArrayList<MatOfPoint>();
		
		VideoCapture video = new VideoCapture(1);
		if(!video.isOpened())
		{
			return;
		}
		for(int i=0 ; i<1000; i++)
		{
			video.read(Frame);
			if(Frame.empty())
			{
				continue;
			}
		}
		Filteredmat = Frame.clone();

		Core.inRange(Frame, lowera, uppera, Filteredmat);
		framedisplay.updateFrame(Utils.fromMatToBufferedImage(Filteredmat));

		
	}

}
