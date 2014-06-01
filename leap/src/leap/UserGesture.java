package leap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Map;

import com.leapmotion.leap.InteractionBox;
import com.leapmotion.leap.Vector;

public class UserGesture implements Serializable {

	//public Vector vector = new Vector(); 
	public ArrayList<String> attributes = new ArrayList<String>();
	public ArrayList<String> actions = new ArrayList<String>();
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
