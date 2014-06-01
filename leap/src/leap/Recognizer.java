package leap;

import java.awt.AWTException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.w3c.dom.traversal.NodeIterator;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Listener;



 class Recognizer {
	//public static Controller controller = new Controller();
	// private static SampleListener listener = new SampleListener();
	public static float deviationX = 30.10F;
	public static float deviationY = 30.10F;
	static Frame frame;
	static Node currentNode;
	public static ArrayList<Frame> startFrame = new ArrayList<Frame>();

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
			Controller controller) throws IOException, InterruptedException, AWTException {
		System.out.println("started recog");

		long[] timer = new long[gestureList.size()];
		int[] matched = new int[gestureList.size()];
		int dot=0;
		int[] matched_attributes = new int[100];
		while (MapperGUI.statusRecognizing == true) {
			//if(dot<3)	{System.out.print(".");	dot=dot+1;} 				// FOR CONSOLE JAR
			//else {dot=0;System.out.print("\b \b \b");}
		
			Thread.sleep(1);
			frame = controller.frame();
			for (int gestureIterator = 0; gestureIterator < gestureList.size(); gestureIterator++) {
				if (frame.hands().isEmpty()) break;
				if(matched[gestureIterator]==0)
				{
					System.out.println("NEW STARTFRAME");
					if(!startFrame.isEmpty())startFrame.remove(gestureIterator);
					startFrame.add(gestureIterator, frame);
					timer[gestureIterator]=System.currentTimeMillis();
				}
				// System.out.println("timer start"+timer);
			//	for (int nodeIterator = matched[gestureIterator] + 1; nodeIterator < gestureList
					//	.get(gestureIterator).NodeList.size();) {
				if (matched[gestureIterator] == gestureList
						.get(gestureIterator).NodeList.size()-1){
					 Robot.execute(gestureIterator);
					if (gestureIterator==0){
						System.out.println("A");}
					else if(gestureIterator==1){
						System.out.println("B");}
					else if(gestureIterator==2){
						System.out.println("C");}
				
					//System.out.println("RECOGNITION SUCCESS for gesture number "+ gestureIterator + " , action goes here");
					matched[gestureIterator]=0;
					timer[gestureIterator] = 0;
					matched_attributes[gestureIterator]=0;
					break;
					}
				
					currentNode=Recorderv2.gestureList.get(Recorderv2.gestureCount-1).NodeList.get(matched[gestureIterator]);
					if (System.currentTimeMillis() < (timer[gestureIterator] + 100)
							) {
						
						if (gestureList.get(gestureIterator).attributes.contains("XY")){
						if ((frame.hands().get(0).palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) < currentNode.hand0_x_denorm
								+ deviationX
								&& (frame.hands().get(0).palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) > currentNode.hand0_x_denorm
								- deviationX
								&& (frame.hands().get(0).palmPosition().getY()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getY()) < currentNode.hand0_y_denorm
								+ deviationY
								&& (frame.hands().get(0).palmPosition().getY()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getY()) > currentNode.hand0_y_denorm
								- deviationY) {
							// System.out.println(frame.hands().get(0).palmPosition().normalized());
							matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
							}}
						
						
							if (matched_attributes[gestureIterator]==Recorderv2.gestureList.get(gestureIterator).attributes.size() && matched_attributes[gestureIterator]!=0){
								matched[gestureIterator] = matched[gestureIterator]+1;
								timer[gestureIterator] = System.currentTimeMillis()+200;
								matched_attributes[gestureIterator]=0;
								System.out.println("matched node number "+ matched[gestureIterator]+ " of gesture "+ gestureIterator+ " with "+ gestureList.get(gestureIterator).NodeList.get(matched[gestureIterator]).hand0_x_denorm
								+ "with "+ (frame.hands().get(0).palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()));
								break;
						} else
							{matched_attributes[gestureIterator]=0;
							
							break; 
							}
						
						}
					else {
						matched_attributes[gestureIterator]=0;
						matched[gestureIterator] = 0;
						timer[gestureIterator] = 0;
						break;
					}
	
			//	}



			}
			
			if (MapperGUI.statusRecognizing == false)
				break;
		}
	
		System.out.println("stopped recog");
		//MapperGUI.runningRecognizing = false;

		return;

	}
}
