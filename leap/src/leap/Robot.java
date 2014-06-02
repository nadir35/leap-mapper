package leap;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class Robot{

	public Robot() {
		// TODO Auto-generated constructor stub
	}

	public static void execute(int gestureCount) throws AWTException{
		java.awt.Robot robby = new java.awt.Robot();
		String s = new String();
		int keycode;
		ArrayList<String> actions = Recorderv2.gestureList.get(gestureCount).actions;
		
		for (int i=0; i<actions.size();i++){
			
			if(actions.get(i).contains(" = "))
				s=actions.get(i).substring(0, 4);
				s=s.trim();
				keycode=  Integer.parseInt(s);
				System.out.println(KeyEvent.getKeyText(keycode));
				robby.keyPress(keycode);
				robby.delay(1);
				robby.keyRelease(keycode);

				 
				
				
			
		}
	}

}
