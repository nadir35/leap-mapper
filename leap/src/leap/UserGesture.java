package leap;

import java.util.ArrayList;

import com.leapmotion.leap.Vector;

public class UserGesture {

	public Vector vector = new Vector(); 
	public String keycode = "";
	public ArrayList<Node> NodeList = new ArrayList<Node>();
			

	public UserGesture() {
		// TODO Auto-generated constructor stub
	}
	  public String toString() {
		  String s= "";
		  for (int i=0; i<NodeList.size();i++)
		s= s+NodeList.get(i).toString()+" \n";
          return s;
      }
}
