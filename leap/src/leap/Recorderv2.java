package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Gesture;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

class SampleListener2 extends Listener {

	public void onInit(Controller controller) {
		System.out.println("Initialized");
		
	}

	public void onConnect(Controller controller) {
		System.out.println("Connected");
		MapperGUI.statusLabel.setVisible(false);
		//Date now = new Date();
		//System.out.println(now);
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
		MapperGUI.statusLabel.setVisible(true);
		MapperGUI.statusRecording = false;
		MapperGUI.statusRecognizing = false;
	}

	public void onExit(Controller controller) {
		System.out.println("Exited");
	}

	public void onFrame(Controller controller) {
		// Get the most recent frame and report some basic information
	//	Frame frame = controller.frame();
	//	Date now = new Date();

	}

}

class Recorderv2 {

	//public static Controller controller = new Controller();
	//public static SampleListener2 listener = new SampleListener2();
	static Vector startPos = new Vector();
	static Vector currentPos = new Vector();
	static long startTime;
	static int gestureCount;
	static Frame frame;
	static int nodeCount;
	BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
			System.in));
	public static ArrayList<UserGesture> gestureList = new ArrayList<UserGesture>();
	static UserGesture newGesture = new UserGesture();
	private static Hand startHand;
	private static Hand currentHand;

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Create a sample listener and controller

		// Have the sample listener receive events from the controller
	//	controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Type exit to quit...");
		System.out.println("Press R to record new gesture \n");

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));

		String input = null;
		int gesturecount = -1;
		while (true) {
			input = bufferRead.readLine().toString();

			if (input.equals("exit")) {
				break;
			} else if (input.equals("r")) {
				gesturecount = gesturecount + 1;
			//	record(gesturecount, controller);
				System.out.println("\n \n \n \n \n");
				System.out.println("Type exit to quit...");
				System.out.println("Press R to record new gesture \n");
			}

			// Remove the sample listener when done
		//	 controller.removeListener(listener);

		}

		// TODO Auto-generated constructor stub
	}

	public static void record(int i, Controller controller) throws IOException,
			InterruptedException {

		gestureCount = i;
		newGesture = new UserGesture();
		nodeCount = 0;
		boolean sta = MapperGUI.statusRecording;
		Node startnode = new Node();
		/*
		 * System.out.println(
		 * "Recording a new gesture.\n Press X to start and Y to stop recording"
		 * ); BufferedReader bufferRead = new BufferedReader(new
		 * InputStreamReader( System.in)); String input = null; input =
		 * bufferRead.readLine().toString();
		 */

		while (MapperGUI.statusRecording == true) {
			
			// System.out.println("	click to node the current position to node["+(newGesture.NodeList.size())+"]");
			frame = controller.frame();
			Node newnode = new Node();
			Thread.sleep(10);
			while (frame.hands().isEmpty() && MapperGUI.statusRecording == true) {
				//System.out.println("no hands detected");
				frame = controller.frame();
				Thread.sleep(100);
			}
			if (MapperGUI.statusRecording == false)
				break;
			if (newGesture.NodeList.isEmpty()) {
				Frame frame = controller.frame();
				System.out
						.println("\n \n \n \n \n \n //////////////////////////////////////////////////////////////////// \n");
				System.out.println("Starting recording gesture number "
						+ gestureCount);
				// System.out.println("hand palmvelocity "
				// + frame.hands().get(0).palmVelocity());
				startHand = frame.hands().get(0);
				startPos = frame.hands().get(0).palmPosition();
				startTime = frame.timestamp();


				startnode.hand0_x = 	startPos.normalized().getX();
				startnode.hand0_y = 	startPos.normalized().getY();
				startnode.hand0_z = 	startPos.normalized().getZ();
				startnode.hand0_x_denorm = 	startPos.getX();
				startnode.hand0_y_denorm = 	startPos.getY();
				startnode.hand0_z_denorm = 	startPos.getZ();
				startnode.hand0_frontmost_x = startHand.fingers().frontmost().tipPosition().getX();
				startnode.hand0_frontmost_y = startHand.fingers().frontmost().tipPosition().getY();
				startnode.hand0_frontmost_z = startHand.fingers().frontmost().tipPosition().getZ();
				startnode.hand0_pitch = startHand.direction().pitch();
				startnode.hand0_yaw =  startHand.direction().yaw();
				startnode.hand0_roll =  startHand.palmNormal().roll();
				
				
				
				
				startnode.timestamp = System.currentTimeMillis();
				if (newGesture.NodeList.isEmpty()) {
					// System.out.println("list 0, adding startnode");
					newGesture.NodeList.add(nodeCount, startnode);
				}

				System.out
						.println("Started recording. starting pos is " + startPos);
				System.out.println(nodeCount + "     " + startnode.toString());
				nodeCount = nodeCount + 1;
			}
			currentHand =frame.hands().get(0);
			currentPos = frame.hands().get(0).palmPosition();
			// System.out.println("currentpos is " + currentPos);
			newnode.hand0_x = currentPos.normalized().getX()-startnode.hand0_x;
					//- newGesture.NodeList.get(newGesture.NodeList.size() - 1).x;
			newnode.hand0_y =currentPos.normalized().getY()-startnode.hand0_y;
			// newnode.y=currentPos.getY()-newGesture.NodeList.get(nodeCount-1).y;
			newnode.hand0_z =  currentPos.normalized().getZ()-startnode.hand0_z;
			newnode.hand0_x_denorm = 	currentPos.getX()-startnode.hand0_x_denorm;
			newnode.hand0_y_denorm = 	currentPos.getY()-startnode.hand0_y_denorm;
			newnode.hand0_z_denorm = 	currentPos.getZ()-startnode.hand0_z_denorm;
			newnode.hand0_frontmost_x = currentHand.fingers().frontmost().tipPosition().getX()-newGesture.NodeList.get(0).hand0_frontmost_x;
			newnode.hand0_frontmost_y = currentHand.fingers().frontmost().tipPosition().getY()-newGesture.NodeList.get(0).hand0_frontmost_y;
			newnode.hand0_frontmost_z = currentHand.fingers().frontmost().tipPosition().getZ()-newGesture.NodeList.get(0).hand0_frontmost_z;
			newnode.hand0_pitch = currentHand.direction().pitch()-startHand.direction().pitch();
			newnode.hand0_yaw = currentHand.direction().yaw()-startHand.direction().yaw();
			newnode.hand0_roll = currentHand.palmNormal().roll()-startHand.palmNormal().roll();
			// newnode.z = currentPos.getZ()
			// - newGesture.NodeList
			// .get(newGesture.NodeList.size() - 1).z;
			newnode.timestamp = System.currentTimeMillis()
					- newGesture.NodeList.get(0).timestamp;
			newGesture.NodeList.add(nodeCount, newnode);
			System.out.println(nodeCount + "     " + newnode.toString());
			nodeCount = nodeCount + 1;
	

		}
		System.out.println("stopped recording. node count is " + nodeCount);
		//System.out.println("which button should the gesture trigger?");
		// newGesture.keycode = bufferRead.readLine().toString();
if (newGesture.NodeList.isEmpty()==false){
		newGesture.NodeList.get(0).timestamp = 0;
		newGesture.NodeList.get(0).hand0_x = 0;
		newGesture.NodeList.get(0).hand0_y = 0;
		newGesture.NodeList.get(0).hand0_z = 0;
		newGesture.NodeList.get(0).hand0_x_denorm = 0;
		newGesture.NodeList.get(0).hand0_y_denorm = 0;
		newGesture.NodeList.get(0).hand0_z_denorm = 0;
		newGesture.NodeList.get(0).hand0_frontmost_x = 0;
		newGesture.NodeList.get(0).hand0_frontmost_y = 0;
		newGesture.NodeList.get(0).hand0_frontmost_z = 0;
		newGesture.NodeList.get(0).hand0_pitch = 0;
		newGesture.NodeList.get(0).hand0_yaw = 0;
		newGesture.NodeList.get(0).hand0_roll = 0;}
else{System.out.println(" no node was written, returning");
		return;
}
		gestureList.add(gestureCount, newGesture);
		gestureCount = gestureCount + 1;

		System.out.println("List of Gestures");
		for (int i1 = 0; i1 < gestureList.size(); i1++) {
			System.out.println("gesture[" + i1 + "]_nodeList = "
					+ gestureList.get(i1).toString());
			//System.out.println("gesture[" + i1 + "]_key = "
			//		+ gestureList.get(i1).keycode.toString());

		}
		return;

	}
}
