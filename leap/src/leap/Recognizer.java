package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;



 class Recognizer {
	//public static Controller controller = new Controller();
	// private static SampleListener listener = new SampleListener();
	public static float deviationX = 0.10F;
	public static float deviationZ = 0.10F;
	static Frame frame;

	/*
	 * public static void main(String[] args) throws IOException,
	 * InterruptedException { // Create a sample listener and controller
	 * 
	 * // Have the sample listener receive events from the controller
	 * controller.addListener(listener2);
	 * 
	 * // Remove the sample listener when done //
	 * controller.removeListener(listener);
	 * 
	 * // TODO Auto-generated constructor stub }
	 */

	public static void recog(ArrayList<UserGesture> gestureList,
			Controller controller) throws IOException, InterruptedException {
		System.out.println("started recog");

		long[] timer = new long[gestureList.size()];
		int[] matched = new int[gestureList.size()];
		int dot=0;

		while (MapperGUI.statusRecognizing == true) {
			//if(dot<3)	{System.out.print(".");	dot=dot+1;} 				// FOR CONSOLE JAR
			//else {dot=0;System.out.print("\b \b \b");}
			frame = controller.frame();
			Thread.sleep(5);
			for (int gestureIterator = 0; gestureIterator < gestureList.size(); gestureIterator++) {
				// System.out.println("timer start"+timer);
				for (int nodeIterator = matched[gestureIterator] + 1; nodeIterator < gestureList
						.get(gestureIterator).NodeList.size();) {
					if (System.currentTimeMillis() < (timer[gestureIterator] + 100)
							|| timer[gestureIterator] == 0) {
						// System.out.println("timer inside"+timer);
						if (frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getX() < gestureList.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getX()
								+ deviationX
								&& frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getX() > gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getX()
								- deviationX
								&& frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getY() < gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getY()
								+ deviationZ
								&& frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getY() > gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0).fingers().frontmost().tipPosition()
								.normalized().getY()
								- deviationZ) {
							// System.out.println(frame.hands().get(0).palmPosition().normalized());
							matched[gestureIterator] = nodeIterator;
							timer[gestureIterator] = System.currentTimeMillis();
							System.out.println("matched node number "+ nodeIterator+ " of gesture "+ gestureIterator+ " with "+ gestureList.get(gestureIterator).NodeList.get(nodeIterator).frame.hands().get(0).fingers().frontmost().tipPosition()
									.normalized()+ "with "+ frame.hands().get(0).fingers().frontmost().tipPosition()
									.normalized());
							break;
						} else
							break;
					} else {
						matched[gestureIterator] = 0;
						timer[gestureIterator] = 0;
						break;
					}
				}
				if (matched[gestureIterator] == gestureList
						.get(gestureIterator).NodeList.size()-1){
					
					if (gestureIterator==0){
						System.out.println("A");}
					else if(gestureIterator==1){
						System.out.println("B");}
					else if(gestureIterator==2){
						System.out.println("C");}
				
					//System.out.println("RECOGNITION SUCCESS for gesture number "+ gestureIterator + " , action goes here");
					matched[gestureIterator]=0;
					timer[gestureIterator] = 0;
					break;
					}

			}
			if (MapperGUI.statusRecognizing == false)
				break;
		}
	
		System.out.println("stopped recog");
		//MapperGUI.runningRecognizing = false;

		return;

	}
}
