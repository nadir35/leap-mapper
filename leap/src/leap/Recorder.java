package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Frame;
import com.leapmotion.leap.Leap;
import com.leapmotion.leap.Listener;
import com.leapmotion.leap.Vector;

public class Recorder extends Listener {

	Vector startPos = new Vector();
	Vector currentPos = new Vector();
	long startTime;
	 int gestureCount;
	 Frame frame;
	 int nodeCount ;
	 BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	 public static ArrayList<UserGesture> gestureList= new ArrayList<UserGesture>();
	 UserGesture newGesture = new UserGesture();
	public Recorder() {
		// TODO Auto-generated constructor stub


	}
	public void init(int i) throws IOException, InterruptedException {
		gestureCount=i;
		newGesture = new UserGesture();
		nodeCount =0;
		 LeapMapper.controller.addListener(this);
		
		// TODO Auto-generated constructor stub

		System.out.println("Recording a new gesture.\n Press X to start and Y to stop recording");
	if(bufferRead.readLine().equals("x")){
							this.start();
							
				}return;
	}
	public void start() throws IOException, InterruptedException {
		// TODO Auto-generated constructor stub
		
		  Node startnode = new Node();
		if(Math.abs(frame.hands().get(0).palmVelocity().getX())<=30 ||
				Math.abs(frame.hands().get(0).palmVelocity().getZ())<=30 ){
		        System.out.println("hand palmvelocity "+frame.hands().get(0).palmVelocity());
		}
		 System.out.println("hand palmvelocity "+frame.hands().get(0).palmVelocity());
		startPos = frame.hands().get(0).palmPosition();
		startTime =frame.timestamp();
		
		 startnode.x=startPos.getX();
		  startnode.y=startPos.getY();
		  startnode.z=startPos.getZ();
		  startnode.timestamp=frame.timestamp();
		 if(newGesture.NodeList.isEmpty()){  
			 System.out.println("list 0, adding startnode");
			 newGesture.NodeList.add(nodeCount, startnode);}

      System.out.println("started recording. starting pos is "+startPos);	
	  System.out.println(nodeCount+"     "+startnode.toString());
	  nodeCount=nodeCount+1;
			this.nodefactory();
			return;

	}
	public void nodefactory() throws IOException, InterruptedException {
		// TODO Auto-generated constructor stub
		   System.out.println("palmvelocity "+frame.hands().get(0).palmVelocity().getX());
		 if(Math.abs(frame.hands().get(0).palmVelocity().getX())>=50 ||
					Math.abs(frame.hands().get(0).palmVelocity().getZ())>=50 )
			{   Node newnode = new Node();

			 currentPos = frame.hands().get(0).palmPosition();
		      System.out.println("currentpos is "+currentPos);	
			  newnode.x=currentPos.getX()-newGesture.NodeList.get(newGesture.NodeList.size()-1).x;
			  newnode.y=0;
			 // newnode.y=currentPos.getY()-newGesture.NodeList.get(nodeCount-1).y;
			  newnode.z=currentPos.getZ()-newGesture.NodeList.get(newGesture.NodeList.size()-1).z;
			  newnode.timestamp=System.currentTimeMillis()-newGesture.NodeList.get(newGesture.NodeList.size()-1).timestamp;
			  newGesture.NodeList.add(nodeCount, newnode);		    
			  System.out.println(nodeCount+"     "+newnode.toString());
			  nodeCount=nodeCount+1;
			  
			  }
		 else {	LeapMapper.controller.removeListener(this);
			 System.out.println("stopped recording. node count is "+nodeCount);
					 this.assignAction();
					 return;
					 }


	   

	}
	public void assignAction() throws IOException {
		// TODO Auto-generated constructor stub
		return;
		 /*System.out.println("which button should the gesture trigger?");
		 newGesture.keycode = bufferRead.readLine().toString();
		 
		 newGesture.NodeList.get(0).timestamp=0;	
		 newGesture.NodeList.get(0).x=0;
		 newGesture.NodeList.get(0).y=0;
		 newGesture.NodeList.get(0).z=0;
		 gestureList.add(gestureCount, newGesture);
		 
 this.printGestures();
 return;*/
	}
	public void printGestures() throws IOException {
		// TODO Auto-generated constructor stub	
		System.out.println("List of Gestures");
		for (int i=0;i<gestureList.size();i++)
		{
			 System.out.println("gesture["+i+"]_nodeList = " +gestureList.get(i).toString());
		//	 System.out.println("gesture["+i+"]_key = " +gestureList.get(i).keycode.toString());
		
		}
		return;


	}
	
	@Override
	public void onFrame(Controller controller){
		   frame =controller.frame();

		   try {
				Thread.sleep(1000);
				
			} catch (InterruptedException e) {
				
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		 

}
}
