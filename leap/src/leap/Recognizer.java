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
import com.leapmotion.leap.Hand;
import com.leapmotion.leap.Listener;



 class Recognizer {
	//public static Controller controller = new Controller();
	// private static SampleListener listener = new SampleListener();
	public static float deviationX = 30.10F;
	public static float deviationY = 30.10F;
	public static float deviationZ = 30.10F;
	public static float deviationPitch = 0.08F;
	public static float deviationRoll = 0.08F;
	public static float deviationYaw = 0.08F;
	static Frame frame;
	static Node currentNode;
	public static ArrayList<Frame> startFrame = new ArrayList<Frame>();
	private static Hand currentHand;


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

		int[] matched_attributes = new int[100];
		String[] gestureStatus = new String[100];


		while (MapperGUI.statusRecognizing == true) {

		
			Thread.sleep(5);
			frame = controller.frame();
			currentHand = frame.hands().get(0);
			for (int gestureIterator = 0; gestureIterator < gestureList.size(); gestureIterator++) {
				if (frame.hands().isEmpty()) break;
				if(matched[gestureIterator]==0 )
				{
					System.out.println("NEW STARTFRAME");
				//	if((startFrame.get(gestureIterator)))startFrame.remove(gestureIterator);
					startFrame.add(gestureIterator, frame);
					timer[gestureIterator]=System.currentTimeMillis();
				}
				// System.out.println("timer start"+timer);
			//	for (int nodeIterator = matched[gestureIterator] + 1; nodeIterator < gestureList
					//	.get(gestureIterator).NodeList.size();) {
				if (matched[gestureIterator] == gestureList
						.get(gestureIterator).NodeList.size()-1){
					  Robot.execute(gestureIterator);
					
		
					//System.out.println("RECOGNITION SUCCESS for gesture number "+ gestureIterator + " , action goes here");
			
					if (Recorderv2.gestureList.get(Recorderv2.gestureCount-1).cont==true){
						matched[gestureIterator]=matched[gestureIterator]-1;
						timer[gestureIterator] = System.currentTimeMillis();
						matched_attributes[gestureIterator]=0;		
					}
					else{
						matched[gestureIterator]=0;
						timer[gestureIterator] = 0;
						matched_attributes[gestureIterator]=0;
					}
				
					break;
					}
				
					currentNode=Recorderv2.gestureList.get(gestureIterator).NodeList.get(matched[gestureIterator]);
					if (System.currentTimeMillis() < (timer[gestureIterator] + 200)
							) {
						
						if (gestureList.get(gestureIterator).attributes.contains("XY")){
						if ((currentHand.palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) < currentNode.hand0_x_denorm
								+ deviationX
								&& (currentHand.palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) > currentNode.hand0_x_denorm
								- deviationX
								&& (currentHand.palmPosition().getY()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getY()) < currentNode.hand0_y_denorm
								+ deviationY
								&& (currentHand.palmPosition().getY()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getY()) > currentNode.hand0_y_denorm
								- deviationY) {
							// System.out.println(frame.hands().get(0).palmPosition().normalized());
							matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
							}}
						if (gestureList.get(gestureIterator).attributes.contains("XZ")){
							if ((currentHand.palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) < currentNode.hand0_x_denorm
									+ deviationX
									&& (currentHand.palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()) > currentNode.hand0_x_denorm
									- deviationX
									&& (currentHand.palmPosition().getZ()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getZ()) < currentNode.hand0_z_denorm
									+ deviationZ
									&& (currentHand.palmPosition().getZ()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getZ()) > currentNode.hand0_z_denorm
									- deviationZ) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
						
						if (gestureList.get(gestureIterator).attributes.contains("frontmost_finger_XY")){
							if ((currentHand.fingers().frontmost().tipPosition().getX()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getX()) < currentNode.hand0_frontmost_x
									+ deviationX
									&& (currentHand.fingers().frontmost().tipPosition().getX()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getX()) > currentNode.hand0_frontmost_x
									- deviationX
									&& (currentHand.fingers().frontmost().tipPosition().getY()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getY()) < currentNode.hand0_frontmost_y
									+ deviationY
									&& (currentHand.fingers().frontmost().tipPosition().getY()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getY()) > currentNode.hand0_frontmost_y
									- deviationY) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
						if (gestureList.get(gestureIterator).attributes.contains("frontmost_finger_XZ")){
							if ((currentHand.fingers().frontmost().tipPosition().getX()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getX()) < currentNode.hand0_frontmost_x
									+ deviationX
									&& (currentHand.fingers().frontmost().tipPosition().getX()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getX()) > currentNode.hand0_frontmost_x
									- deviationX
									&& (currentHand.fingers().frontmost().tipPosition().getZ()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getZ()) < currentNode.hand0_frontmost_z
									+ deviationZ
									&& (currentHand.fingers().frontmost().tipPosition().getZ()-startFrame.get(gestureIterator).hands().get(0).fingers().frontmost().tipPosition().getZ()) > currentNode.hand0_frontmost_z
									- deviationZ) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
						
						if (gestureList.get(gestureIterator).attributes.contains("roll")){
							if ((currentHand.palmNormal().roll()-startFrame.get(gestureIterator).hands().get(0).palmNormal().roll()) < currentNode.hand0_roll
									+ deviationRoll
									&& (currentHand.palmNormal().roll()-startFrame.get(gestureIterator).hands().get(0).palmNormal().roll()) > currentNode.hand0_roll
									- deviationRoll
									) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
						if (gestureList.get(gestureIterator).attributes.contains("yaw")){
							if ((currentHand.palmNormal().yaw()-startFrame.get(gestureIterator).hands().get(0).palmNormal().yaw()) < currentNode.hand0_yaw
									+ deviationYaw
									&& (currentHand.palmNormal().yaw()-startFrame.get(gestureIterator).hands().get(0).palmNormal().yaw()) > currentNode.hand0_yaw
									- deviationYaw
									) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
				
						if (gestureList.get(gestureIterator).attributes.contains("pitch")){
							if ((currentHand.palmNormal().pitch()-startFrame.get(gestureIterator).hands().get(0).palmNormal().pitch()) < currentNode.hand0_pitch
									+ deviationPitch
									&& (currentHand.palmNormal().pitch()-startFrame.get(gestureIterator).hands().get(0).palmNormal().pitch()) > currentNode.hand0_pitch
									- deviationPitch
									) {
								// System.out.println(frame.hands().get(0).palmPosition().normalized());
								matched_attributes[gestureIterator]=matched_attributes[gestureIterator]+1;
								}}
							if (matched_attributes[gestureIterator]==Recorderv2.gestureList.get(gestureIterator).attributes.size() && matched_attributes[gestureIterator]!=0){
					
								matched[gestureIterator] = matched[gestureIterator]+1;
								timer[gestureIterator] = System.currentTimeMillis()+200;
								matched_attributes[gestureIterator]=0;
								System.out.println("matched node number "+ matched[gestureIterator]+ " of gesture "+ gestureIterator+ " with "+ gestureList.get(gestureIterator).NodeList.get(matched[gestureIterator]).hand0_x_denorm
								+ "with "+ (frame.hands().get(0).palmPosition().getX()-startFrame.get(gestureIterator).hands().get(0).palmPosition().getX()));
								
						} else
							{matched_attributes[gestureIterator]=0;
							
							 
							}
						
						}
					else {
						matched_attributes[gestureIterator]=0;
						matched[gestureIterator] = 0;
						timer[gestureIterator] = 0;
						
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
