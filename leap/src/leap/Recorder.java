package leap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.Leap;
import com.leapmotion.leap.Vector;

public class Recorder {

	Vector startPos = new Vector();
	Vector currentPos = new Vector();
	long startTime;
	 int gestureCount;
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
		// TODO Auto-generated constructor stub

		System.out.println("Recording a new gesture.\n Press X to start and Y to stop recording");
	if(bufferRead.readLine().equals("x")){
							this.start();
				}
	}
	public void start() throws IOException, InterruptedException {
		// TODO Auto-generated constructor stub
		
		  Node startnode = new Node();
		while (Math.abs(LeapMapper.controller.frame().hands().get(0).palmVelocity().getX())<=20 ||
				Math.abs(LeapMapper.controller.frame().hands().get(0).palmVelocity().getZ())<=20 ){
		        System.out.println("hand palmvelocity "+LeapMapper.controller.frame().hands().get(0).palmVelocity());
		}
		 System.out.println("hand palmvelocity "+LeapMapper.controller.frame().hands().get(0).palmVelocity());
		startPos = LeapMapper.controller.frame().hands().get(0).palmPosition();
		startTime =System.currentTimeMillis();
		
		 startnode.x=startPos.getX();
		  startnode.y=startPos.getY();
		  startnode.z=startPos.getZ();
		  startnode.timestamp=System.currentTimeMillis();
		 if(newGesture.NodeList.isEmpty()){  
			 System.out.println("list 0, adding startnode");
			 newGesture.NodeList.add(nodeCount, startnode);}

      System.out.println("started recording. starting pos is "+startPos);	
	  System.out.println(nodeCount+"     "+startnode.toString());
	  nodeCount=nodeCount+1;
			nodefactory();

	}
	public void nodefactory() throws IOException, InterruptedException {
		// TODO Auto-generated constructor stub
		Node newnode = new Node();
		while (Math.abs(LeapMapper.controller.frame().hands().get(0).palmVelocity().getX())>=10 ||
				Math.abs(LeapMapper.controller.frame().hands().get(0).palmVelocity().getZ())>=10 )
		{
		
	        Thread.sleep(100);
	  	  System.out.println("neeuer loop von nodefactory");
		         newnode = new Node();

		 currentPos = LeapMapper.controller.frame().hands().get(0).palmPosition();
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
		 
		    System.out.println("stopped recording. node count is "+nodeCount);
	    this.assignAction();

	}
	public void assignAction() throws IOException {
		// TODO Auto-generated constructor stub
		 System.out.println("which button should the gesture trigger?");
		 newGesture.keycode = bufferRead.readLine().toString();
		 
		 newGesture.NodeList.get(0).timestamp=0;	
		 newGesture.NodeList.get(0).x=0;
		 newGesture.NodeList.get(0).y=0;
		 newGesture.NodeList.get(0).z=0;
		 gestureList.add(gestureCount, newGesture);
		 
 this.printGestures();
	}
	public void printGestures() throws IOException {
		// TODO Auto-generated constructor stub	
		System.out.println("List of Gestures");
		for (int i=0;i<gestureList.size();i++)
		{
			 System.out.println("gesture["+i+"]_nodeList = " +gestureList.get(i).toString());
			 System.out.println("gesture["+i+"]_key = " +gestureList.get(i).keycode.toString());
			 
			 

		
		}
		return;
	


	}
	
	/*private static void Recorder(Controller controller) {
		 BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
	       
	    	Vector startPos = new Vector();
	    	Vector endPos = new Vector();
		 int i=0;
		 try {
				if(bufferRead.readLine().equals("x")){
				
					   
	   }
				
		 }catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		 }
		 try {
				if(bufferRead.readLine().equals("y")){
				
						    
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
		
		 try {
			 
		} catch (IOException ee) {
			// TODO Auto-generated catch block
			ee.printStackTrace();
		}
		
		 System.out.println("Recorded data ");

	
}*/
}
