package leap;


import java.awt.Event;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.KeyStroke;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;


class SampleListener extends Listener {
    public void onInit(Controller controller) {
        System.out.println("Initialized");
    }

    public void onConnect(Controller controller) {
        System.out.println("Connected");
        Date now = new Date();
        System.out.println(now);
       /* controller.enableGesture(UserGesture.Type.TYPE_SWIPE);
        controller.enableGesture(UserGesture.Type.TYPE_CIRCLE);
        controller.enableGesture(UserGesture.Type.TYPE_SCREEN_TAP);
        controller.enableGesture(UserGesture.Type.TYPE_KEY_TAP);*/
    }

    public void onDisconnect(Controller controller) {
        //Note: not dispatched when running in a debugger.
        System.out.println("Disconnected");
    }

    public void onExit(Controller controller) {
        System.out.println("Exited");
    }

    public void onFrame(Controller controller) {
        // Get the most recent frame and report some basic information
       Frame frame = controller.frame();
        Date now = new Date();
      /*  System.out.println("Frame id: " + frame.id()
                         + ", timestamp: " + frame.timestamp()
                         + ", hands: " + frame.hands().count()
                         + ", fingers: " + frame.fingers().count()
                         + ", tools: " + frame.tools().count()
                         + ", gestures " + frame.gestures().count()
                         + ", time " + now);*/  
        try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      //  System.out.println("hand palmposition "+frame.hands().get(0).palmPosition());
       // System.out.println("hand palmvelocity "+frame.hands().get(0).palmVelocity());
       
    }

}

class LeapMapper {
	
	public static Controller controller = new Controller();
	public static SampleListener listener = new SampleListener();
    public static void main(String[] args) throws IOException, InterruptedException {
        // Create a sample listener and controller
       
       

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Type exit to quit...");
        System.out.println("Press R to record new gesture \n");
 
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		Recorder rec = new Recorder();
        String input = null;
        int gesturecount= -1;
while(true){
     input=bufferRead.readLine().toString();
		
			if(input.equals("exit")){
				break;
			    }
			else if(input.equals("r")){
				gesturecount=gesturecount+1;
				rec.init(gesturecount);
		        System.out.println("\n \n \n \n \n");
		        System.out.println("Type exit to quit...");
		        System.out.println("Press R to record new gesture \n");
			    }
        

    }

           

        // Remove the sample listener when done
        controller.removeListener(listener);

    }





}


