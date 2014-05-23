package leap;


import java.awt.Event;
import java.awt.List;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

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
	public static ArrayList<UserGesture> gestures= new ArrayList<UserGesture>();
    public static void main(String[] args) {
        // Create a sample listener and controller
        SampleListener listener = new SampleListener();
        Controller controller = new Controller();

        // Have the sample listener receive events from the controller
        controller.addListener(listener);

        // Keep this process running until Enter is pressed
        System.out.println("Press Enter to quit...");
        System.out.println("Press R to record new gesture");
 
        BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
       


        try {
			if(bufferRead.readLine().equals("r")){
				System.out.println("Recording a new gesture.\n Press X to start and Y to stop recording");
				Recorder(controller);
			    
   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        Roboter();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Remove the sample listener when done
        controller.removeListener(listener);

    }

	private static void Roboter() {
		// TODO Auto-generated method stub
		
	}

	private static void Recorder(Controller controller) {
		 BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	       
	    	Vector startPos = new Vector();
	    	Vector endPos = new Vector();
		 int i=0;
		 try {
				if(bufferRead.readLine().equals("x")){
				
					startPos = controller.frame().hands().get(0).palmPosition();

				    System.out.println("started recording. starting pos is "+startPos);		    
	   }
				
		 }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 }
		 try {
				if(bufferRead.readLine().equals("y")){
				
					endPos = controller.frame().hands().get(0).palmPosition();
				    System.out.println("stopped recording. end pos is "+endPos);		    
	   }
				
		 }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 }
	
		 
		

		
		 UserGesture e = new UserGesture();
		 gestures.add(i, e);
		 gestures.set(i, e).vector.setX(endPos.getX()-startPos.getX());
		 gestures.set(i, e).vector.setZ(endPos.getZ()-startPos.getZ());
		 gestures.set(i, e).vector.setY(0);
		 System.out.println("which button should the gesture trigger?");
		 try {
			 gestures.set(i, e).keycode = bufferRead.readLine().toString();
		} catch (IOException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		 System.out.println("Recorded data ");
		 System.out.println("gesture_vector= " +gestures.get(i).vector.toString());
		 System.out.println("gesture_key= " +gestures.get(i).keycode.toString());
	
}

}
 class UserGesture{ 
	
	public Vector vector = new Vector();
	public String keycode = "";
			

	
}

