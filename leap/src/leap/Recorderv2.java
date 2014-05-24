package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

class SampleListener2 extends Listener {


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
		Frame frame = controller.frame();
		Date now = new Date();
	

	}

}

class Recorderv2 {

	public static Controller controller = new Controller();
	public static SampleListener2 listener = new SampleListener2();
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

	public static void main(String[] args) throws IOException,
			InterruptedException {
		// Create a sample listener and controller

		// Have the sample listener receive events from the controller
		controller.addListener(listener);

		// Keep this process running until Enter is pressed
		System.out.println("Type exit to quit...");
		System.out.println("Press R to record new gesture \n");

		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		Recorder rec = new Recorder();
		String input = null;
		int gesturecount = -1;
		while (true) {
			input = bufferRead.readLine().toString();

			if (input.equals("exit")) {
				break;
			} else if (input.equals("r")) {
				gesturecount = gesturecount + 1;
				record(gesturecount,controller);
				System.out.println("\n \n \n \n \n");
				System.out.println("Type exit to quit...");
				System.out.println("Press R to record new gesture \n");
			}

			// Remove the sample listener when done
			//controller.removeListener(listener);

		}

		// TODO Auto-generated constructor stub
	}

	public static void record(int i,Controller controller) throws IOException, InterruptedException {
		
		gestureCount = i;
		newGesture = new UserGesture();
		nodeCount = 0;
		int status=0;
		Node startnode = new Node();
		System.out.println("Recording a new gesture.\n Press X to start and Y to stop recording");
		BufferedReader bufferRead = new BufferedReader(new InputStreamReader(
				System.in));
		String input = null;
		input = bufferRead.readLine().toString();
		if (status==0 && input.equals("x")) {
			Frame frame=controller.frame();
			System.out.println("hand palmvelocity "
					+ frame.hands().get(0).palmVelocity());
			startPos = frame.hands().get(0).palmPosition();
			startTime = frame.timestamp();

			startnode.x = startPos.getX();
			startnode.y = startPos.getY();
			startnode.z = startPos.getZ();
			startnode.timestamp = frame.timestamp();
			if (newGesture.NodeList.isEmpty()) {
				System.out.println("list 0, adding startnode");
				newGesture.NodeList.add(nodeCount, startnode);
			}

			System.out
					.println("started recording. starting pos is " + startPos);
			System.out.println(nodeCount + "     " + startnode.toString());
			nodeCount = nodeCount + 1;
			status= status+1;
			while(status==1){
				System.out.println("	click to node the current position to node["+(newGesture.NodeList.size())+"]");
				input = bufferRead.readLine().toString();
				if (input.equals("y")){ status=2;break;}
				frame=controller.frame();
				Node newnode = new Node();
				while(frame.hands().isEmpty()){
						System.out.println("no hands detected, press again");
						input = bufferRead.readLine().toString();
						frame=controller.frame();}

				currentPos = frame.hands().get(0).palmPosition();
				//System.out.println("currentpos is " + currentPos);
				newnode.x = currentPos.getX()
						- newGesture.NodeList
								.get(newGesture.NodeList.size() - 1).x;
				newnode.y = 0;
				// newnode.y=currentPos.getY()-newGesture.NodeList.get(nodeCount-1).y;
				newnode.z = currentPos.getZ()
						- newGesture.NodeList
								.get(newGesture.NodeList.size() - 1).z;
				newnode.timestamp = System.currentTimeMillis()
						- newGesture.NodeList
								.get(newGesture.NodeList.size() - 1).timestamp;
				newGesture.NodeList.add(nodeCount, newnode);
				System.out.println(nodeCount + "     " + newnode.toString());
				nodeCount = nodeCount + 1;
				//Thread.sleep(100);
			
				
			}
			System.out.println("stopped recording. node count is "+nodeCount);
			 System.out.println("which button should the gesture trigger?");
			 newGesture.keycode = bufferRead.readLine().toString();
			 
			 newGesture.NodeList.get(0).timestamp=0;	
			 newGesture.NodeList.get(0).x=0;
			 newGesture.NodeList.get(0).y=0;
			 newGesture.NodeList.get(0).z=0;
			 gestureList.add(gestureCount, newGesture);
			 
			 System.out.println("List of Gestures");
				for (int i1=0;i1<gestureList.size();i1++)
				{
					 System.out.println("gesture["+i1+"]_nodeList = " +gestureList.get(i1).toString());
					 System.out.println("gesture["+i1+"]_key = " +gestureList.get(i1).keycode.toString());
				
				}
				return;
		}

	}
}
