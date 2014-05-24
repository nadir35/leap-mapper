package leap;

import com.leapmotion.leap.Frame;

public class Node {

	public float x;
	public  float y;
	public  float z;
	public long timestamp;
	public Frame frame;
	
	 public String toString() {
		 String s = "( "+x+", "+y+", "+z+", "+timestamp+" )";
         return s;
     }
}
