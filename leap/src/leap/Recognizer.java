package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;

class SampleListener3 extends Listener {

	public void onInit(Controller controller) {
		System.out.println("Initialized");
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		Date now = new Date();
		System.out.println(now);
		/*
		 * controller.enableGesture(UserGesture.Type.TYPE_SWIPE);
		 * controller.enableGesture(UserGesture.Type.TYPE_CIRCLE);
		 * controller.enableGesture(UserGesture.Type.TYPE_SCREEN_TAP);
		 * controller.enableGesture(UserGesture.Type.TYPE_KEY_TAP);
		 */
	}

	public void onDisconnect(Controller controller) {
		// Note: not dispatched when running in a debugger.
		System.out.println("Disconnected");
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information

	}

}

 class Recognizer {
	public static Controller controller = new Controller();
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

		while (MapperGUI.statusRecognizing == true) {
			frame = controller.frame();
			Thread.sleep(5);
			for (int gestureIterator = 0; gestureIterator < gestureList.size(); gestureIterator++) {
				// System.out.println("timer start"+timer);
				for (int nodeIterator = matched[gestureIterator] + 1; nodeIterator < gestureList
						.get(gestureIterator).NodeList.size();) {
					if (System.currentTimeMillis() < (timer[gestureIterator] + 2000)
							|| timer[gestureIterator] == 0) {
						// System.out.println("timer inside"+timer);
						if (frame.hands().get(0).palmPosition().normalized()
								.getX() < gestureList.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0)
								.palmPosition().normalized().getX()
								+ deviationX
								&& frame.hands().get(0).palmPosition()
								.normalized().getX() > gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0)
								.palmPosition().normalized().getX()
								- deviationX
								&& frame.hands().get(0).palmPosition()
								.normalized().getZ() < gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0)
								.palmPosition().normalized().getZ()
								+ deviationZ
								&& frame.hands().get(0).palmPosition()
								.normalized().getZ() > gestureList
								.get(gestureIterator).NodeList
								.get(nodeIterator).frame.hands().get(0)
								.palmPosition().normalized().getZ()
								- deviationZ) {
							// System.out.println(frame.hands().get(0).palmPosition().normalized());
							matched[gestureIterator] = nodeIterator;
							timer[gestureIterator] = System.currentTimeMillis();
							System.out.println("matched node number "
									+ nodeIterator
									+ " of gesture "
									+ gestureIterator
									+ " with "
									+ gestureList.get(gestureIterator).NodeList
									.get(nodeIterator).frame.hands()
									.get(0).palmPosition().normalized()
									+ "with "
									+ frame.hands().get(0).palmPosition()
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
						.get(gestureIterator).NodeList.size())
					System.out
					.println("RECOGNITION SUCCESS for gesture number "
							+ gestureIterator + " , action goes here");

			}
		}

		MapperGUI.runningRecognizing = false;

		return;

	}
}
