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
		Frame frame = controller.frame();
		Date now = new Date();

	}

}
public class Recognizer {
	public static Controller controller = new Controller();
	public static SampleListener3 listener2 = new SampleListener3();
	public static float deviationX =0.10F;
	public static float deviationZ =0.10F;
	static Frame frame;

	public static void main(String[] args) throws IOException,
	InterruptedException {
// Create a sample listener and controller

// Have the sample listener receive events from the controller
controller.addListener(listener2);




	// Remove the sample listener when done
	// controller.removeListener(listener);



// TODO Auto-generated constructor stub
}

	
	public static void recog(ArrayList<UserGesture> gestureList, Controller controller) throws IOException,
	InterruptedException {
		System.out.println("started recog");

while(MapperGUI.statusRecognizing==true){
	frame= controller.frame();
	for (int i =0; i<gestureList.size();i++){
		for (int j=1;j<gestureList.get(i).NodeList.size();j++){
			while(frame.hands().get(0).palmPosition().normalized().getX()>gestureList.get(i).NodeList.get(j).frame.hands().get(0).palmPosition().normalized().getX()+deviationX ||
					frame.hands().get(0).palmPosition().normalized().getX()<gestureList.get(i).NodeList.get(j).frame.hands().get(0).palmPosition().normalized().getX()-deviationX ||
					frame.hands().get(0).palmPosition().normalized().getZ()>gestureList.get(i).NodeList.get(j).frame.hands().get(0).palmPosition().normalized().getZ()+deviationZ ||
					frame.hands().get(0).palmPosition().normalized().getZ()<gestureList.get(i).NodeList.get(j).frame.hands().get(0).palmPosition().normalized().getZ()-deviationZ
					){
				frame= controller.frame();
				System.out.println(frame.hands().get(0).palmPosition().normalized());
		
			}
			System.out.println("matched node number"+gestureList.get(i).NodeList.get(j).frame.hands().get(0).palmPosition().normalized()+"with "+frame.hands().get(0).palmPosition().normalized());
			break;
			
		}
	
		
		
		
	}
	//MapperGUI.statusRecognizing=false;
	
	
	
	
}

return;

}
}
