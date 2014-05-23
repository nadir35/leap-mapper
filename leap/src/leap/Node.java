package leap;

public class Node {

	public float x;
	public  float y;
	public  float z;
	public long timestamp;
	
	 public String toString() {
		 String s = "( "+x+", "+y+", "+z+", "+timestamp+" )";
         return s;
     }
}
