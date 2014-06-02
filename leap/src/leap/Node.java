package leap;

import java.io.Serializable;

public class Node implements Serializable{

	public long timestamp;
	public float hand0_x;
	public float hand0_y;
	public float hand0_z;
	public float hand0_x_denorm;
	public float hand0_y_denorm;
	public float hand0_z_denorm;
	public float hand0_frontmost_x;
	public float hand0_frontmost_y;
	public float hand0_frontmost_z;
	public float hand0_yaw;
	public float hand0_roll;
	public float hand0_pitch;
	
	
	 public String toString() {
		 String s = "( "+hand0_x+", "+hand0_y+", "+hand0_z+", "+timestamp+", "+hand0_roll+", "+hand0_pitch+", "+hand0_yaw+" )";
         return s;
     }
}
