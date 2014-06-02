package leap;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.sound.midi.Receiver;
import javax.swing.*;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Leap;

public class MapperGUI extends JFrame {

	public static boolean statusRecording = false;
	public static boolean runningRecording = false;
	public static boolean statusRecognizing = false;
	public static boolean runningRecognizing = false;
	public static JLabel statusLabel = new JLabel("Status: ");
	public static Controller controller = new Controller();
	public static SampleListener2 listener = new SampleListener2();

	public MapperGUI() {
		// TODO Auto-generated constructor stub
		JPanel panel = new JPanel();
		controller.addListener(listener);
		controller.setPolicyFlags(Controller.PolicyFlag.POLICY_BACKGROUND_FRAMES);
		JButton startRecButton = new JButton("Start recording");
		JButton stopRecButton = new JButton("Stop recordiing");
		JButton stopRecogButton = new JButton("Stop recorgnizing");
		final JButton deleteButton = new JButton("delete gesture");
		JButton startMapper = new JButton("Assign action to parameters");
		JButton showPlot = new JButton("Show Plot");
		JButton showFPS = new JButton("show FPS");
		final JButton startRecog = new JButton("Start Recognition");
		JButton loadGestures = new JButton("load gestures");
		JButton saveGestures = new JButton("save gestures");
		getContentPane().add(panel);

		setTitle("LeapRecorder");
		setLocation(430, 330);
		// setLocationRelativeTo(null);
		panel.setLayout(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);

		startRecButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				statusRecording = true;
				if (runningRecording == false) {
					if (controller.isConnected()) {
						final Thread t = new Thread(new Runnable() {
							public void run() {
								// this shall get executed, after start() has
								// been
								// called, outside the EDT
								try {

									Recorderv2.record(Recorderv2.gestureCount,
											controller);
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						
						t.start();
						runningRecording = true;
						statusLabel.setText("Status: Recording");
						statusLabel.setVisible(true);
					} else {
						System.out.println("no leap connected");
					}
				}
			}
		});

		stopRecButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusRecording = false;
				runningRecording = false;
				statusLabel.setText("Status: ");
				deleteButton.setText("delete gesture nr. "
						+ Recorderv2.gestureCount);

			}
		});

		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (Recorderv2.gestureCount != 0) {
					Recorderv2.gestureList.remove(Recorderv2.gestureList.size() - 1);
					Recorderv2.gestureCount = Recorderv2.gestureCount - 1;
					System.out.println("gesture nr " + Recorderv2.gestureCount
							+ " deleted");
					deleteButton.setText("delete gesture nr. "
							+ Recorderv2.gestureCount);
				}
				if (Recorderv2.gestureCount == 0)
					deleteButton.setText("no gestures to delete");
			}
		});
		saveGestures.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				  try
			      {
				
			         FileOutputStream fileOut =
			         new FileOutputStream("c:\\gestures.ser");
			         ObjectOutputStream out = new ObjectOutputStream(fileOut);
			         out.writeObject(Recorderv2.gestureList);
			         out.close();
			         fileOut.close();
			         System.out.println("Serialized data is saved in c:\\gestures.ser");
			      }catch(IOException i)
			      {
			          i.printStackTrace();
			      }
			}
		});
		loadGestures.addActionListener(new ActionListener() {
			@SuppressWarnings("unchecked")
			@Override
			public void actionPerformed(ActionEvent event) {
				try
			      {
					  ArrayList<UserGesture> gl=new  ArrayList<UserGesture>();
			         FileInputStream fileIn = new FileInputStream("c:\\gestures.ser");
			         ObjectInputStream in = new ObjectInputStream(fileIn);
			         gl = (ArrayList<UserGesture>) in.readObject();
			         in.close();
			         fileIn.close();
			         Recorderv2.gestureList = gl;
			         Recorderv2.gestureCount= gl.size();
			         System.out.println("Serialized data was loaded from c:\\gestures.ser");
			         //System.out.println(Recorderv2.gestureList);
			         deleteButton.setText("delete gesture nr. "
								+ (Recorderv2.gestureCount-1));
			      }catch(IOException i)
			      {
			         i.printStackTrace();
			         return;
			      } catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		showPlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Points.area=false;
				Points.property=0;
				if(!Recorderv2.gestureList.isEmpty())
					{Points ps = new Points();
				Points.area=false;
				ps.setVisible(true);}
				
			}
		});
		showFPS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

	
			/*	if(!Recorderv2.gestureList.isEmpty())
					{FPS fps = new FPS();
	
				fps.setVisible(true);}*/
				
			}
		});
		startMapper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				ActionMapper am = new ActionMapper();
				am.setVisible(true);
				if(Recorderv2.gestureCount!=0){
					//am.map_propListModel.clear();
					//Recorderv2.gestureList.get(Recorderv2.gestureCount-1).actions.clear();
					//am.map_actionListModel.clear();
				//	Recorderv2.gestureList.get(Recorderv2.gestureCount-1).attributes.clear();
				}
			}
		});
		startRecog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusRecognizing = true;
				if (runningRecognizing == false) {
					if (controller.isConnected()) {
						final Thread t = new Thread(new Runnable() {
							public void run() {
								// this shall get executed, after start() has
								// been
								// called, outside the EDT
								try {

									Recognizer.recog(Recorderv2.gestureList,
											controller);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (AWTException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});
						t.start();
						runningRecognizing = true;
						statusLabel.setText("Status: Recognizing");
					} else {
						System.out.println("no leap connected");
					}
				}

			}
		});
		stopRecogButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusRecognizing = false;
				runningRecognizing = false;
				statusLabel.setText("Status: ");
			}
		});

		panel.add(startRecButton);
		panel.add(stopRecButton);
		panel.add(showPlot);
		panel.add(startMapper);
		panel.add(startRecog);
		panel.add(deleteButton);
		panel.add(stopRecogButton);
		panel.add(statusLabel);
		panel.add(showFPS);
		panel.add(saveGestures);
		panel.add(loadGestures);
		statusLabel.setVisible(false);
		startRecButton.setBounds(100, 100, 120, 30);
		stopRecButton.setBounds(250, 100, 140, 30);
		showPlot.setBounds(100, 200, 120, 30);
		startMapper.setBounds(100, 300, 120, 30);
		startRecog.setBounds(100, 400, 140, 30);
		stopRecogButton.setBounds(300, 400, 140, 30);
		showFPS.setBounds(300, 200, 140, 30);
		deleteButton.setBounds(400, 100, 180, 30);
		saveGestures.setBounds(500, 300, 100, 30);
		loadGestures.setBounds(600, 300, 100, 30);
		statusLabel.setBounds(200, 500, 180, 30);
		setResizable(false);

		// panel2.add(new Surface());

		setSize(800, 600);
		setFocusable(false);


	}

	public static void main(String[] args) {
		// TODO Auto-generated method stubThread 
		Thread t = new Thread(new Runnable(){

	//	SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MapperGUI ex = new MapperGUI();
				ex.setVisible(true);

			}
		
		});
		t.start();
	}

}

class Surface extends JPanel {

	private void doDrawing(Graphics g, int property, boolean area, int showingGesture) {

		Graphics2D g2d = (Graphics2D) g;
		Graphics2D a2d = (Graphics2D) g;

		g2d.setColor(Color.blue);
	

		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;

		if (property == 0) { // draw palm XY
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_x_denorm % w);
				int y = (int) -(Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_y_denorm % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_x_denorm % w);
					nextY = (int) -(Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_y_denorm % h);
					
				}
				if (i % 10 == 1){
					
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)/2) + y);
					}
				g2d.drawLine(((size.width) / 2) + x, ((size.height)/2) + y,
						((size.width) / 2) + nextX, ((size.height)/2) + nextY);
				if(area==true){
					g2d.setColor(Color.red);
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y+Recognizer.deviationY),
							(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationY));
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationY),
							(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2) + y+Recognizer.deviationY));
				//	a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2),
				//			(int) (((size.width) / 2) + nextX+Recognizer.deviationX*300/2),(int) (((size.height)) + nextY+Recognizer.deviationZ*300/2));
				//	a2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2),
					//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height)) + nextY-Recognizer.deviationZ*300/2));
				//g2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + y-Recognizer.deviationZ*300/2),
				//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + nextY-Recognizer.deviationZ*300/2));
					g2d.setColor(Color.blue);
					}
			}
			g2d.drawString("y",1,(size.height)-18);
			g2d.drawString("^",18,(size.height)-27);
			g2d.drawString("|",19,(size.height)-25);
			
			g2d.drawString("->",19,(size.height)-15);
			g2d.drawString("x",15,(size.height)-6);
		} else if (property ==1) { //draw finger XY
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_frontmost_x % w);
				int y = (int) -(Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_frontmost_y % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_frontmost_x % w);
					nextY = -(int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_frontmost_y % h);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)/2) + y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height)/2) + y,
						((size.width) / 2) + nextX, ((size.height)/2) + nextY);
			
			if(area==true){
				g2d.setColor(Color.red);
				a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y+Recognizer.deviationY),
						(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationY));
				a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationY),
						(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2 ) + y+Recognizer.deviationY));
			//g2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + y-Recognizer.deviationZ*300/2),
			//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + nextY-Recognizer.deviationZ*300/2));
				g2d.setColor(Color.blue);
				}
			}
			g2d.drawString("y",1,(size.height)-18);
			g2d.drawString("^",18,(size.height)-27);
			g2d.drawString("|",19,(size.height)-25);
			
			g2d.drawString("->",19,(size.height)-15);
			g2d.drawString("x",15,(size.height)-6);
		}
		else if (property ==2) { //show pitch
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = i/2;
				int y = (int) (Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_pitch*200);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextX = (i+1)/2;
					nextY = (int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_pitch*200);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), 100 + x,
							((size.height)/2) + y);
				g2d.drawLine(100 + x, ((size.height)/2) + y,
						100 + nextX, ((size.height)/2) + nextY);

			}
			g2d.drawString("value",1,(size.height)-18);
			g2d.drawString("^",38,(size.height)-27);
			g2d.drawString("|",39,(size.height)-25);
			
			g2d.drawString("->",39,(size.height)-15);
			g2d.drawString("time",35,(size.height)-6);
		}
		else if (property ==3) { //show yaw
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_yaw*200);
				int y = i/2;
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextY = (i+1)/2;
					nextX = (int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_yaw*200);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)-100) - y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height)-100) - y,
						((size.width) / 2) + nextX, ((size.height)-100) - nextY);

			}
			g2d.drawString("time",1,(size.height)-18);
			g2d.drawString("^",38,(size.height)-27);
			g2d.drawString("|",39,(size.height)-25);
			
			g2d.drawString("->",39,(size.height)-15);
			g2d.drawString("value",35,(size.height)-6);
		}
		else if (property ==4) { //show roll
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = (int) -(Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_roll*200);
				int y = i/2;
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextY = (i+1)/2;
					nextX = (int) -(Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_roll*200);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)-100) - y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height)-100) - y,
						((size.width) / 2) + nextX, ((size.height)-100) - nextY);

			}
			g2d.drawString("time",1,(size.height)-18);
			g2d.drawString("^",38,(size.height)-27);
			g2d.drawString("|",39,(size.height)-25);
			
			g2d.drawString("->",39,(size.height)-15);
			g2d.drawString("value",35,(size.height)-6);
		}
		else if (property == 5) { // draw palm XZ
			for (int i = 1; i < Recorderv2.gestureList
					.get(showingGesture).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_x_denorm % w);
				int y = (int) -(Recorderv2.gestureList
						.get(showingGesture).NodeList.get(i).hand0_z_denorm % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(showingGesture).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_x_denorm % w);
					nextY = (int) -(Recorderv2.gestureList
							.get(showingGesture).NodeList
							.get(i + 1).hand0_z_denorm % h);
					
				}
				if (i % 10 == 1){
					
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)/2) + y);
					}
				g2d.drawLine(((size.width) / 2) + x, ((size.height)/2) + y,
						((size.width) / 2) + nextX, ((size.height)/2) + nextY);
				if(area==true){
					g2d.setColor(Color.red);
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y+Recognizer.deviationZ),
							(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationZ));
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX),(int) (((size.height)/2) + y-Recognizer.deviationZ),
							(int) (((size.width) / 2) + x-Recognizer.deviationX),(int) (((size.height)/2) + y+Recognizer.deviationZ));
				//	a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2),
				//			(int) (((size.width) / 2) + nextX+Recognizer.deviationX*300/2),(int) (((size.height)) + nextY+Recognizer.deviationZ*300/2));
				//	a2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2),
					//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height)) + nextY-Recognizer.deviationZ*300/2));
				//g2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + y-Recognizer.deviationZ*300/2),
				//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + nextY-Recognizer.deviationZ*300/2));
					g2d.setColor(Color.blue);
					}
			}
			g2d.drawString("z",1,(size.height)-18);
			g2d.drawString("^",18,(size.height)-27);
			g2d.drawString("|",19,(size.height)-25);
			
			g2d.drawString("->",19,(size.height)-15);
			g2d.drawString("x",15,(size.height)-6);
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, Points.property, Points.area, Points.showingGesture);
		
	}

}

class Points extends JFrame {
	public static int property = 0;
	public static boolean area = false;
	public static int showingGesture = Recorderv2.gestureList.size()-1;
	JButton showProperty = new JButton("show finger xy coordinates");
	JButton deleteFirst = new JButton("delete First node");
	JButton deleteLast = new JButton("delete Last node");
	JButton showArea = new JButton("show recognition area (beta");
	JButton nextGesture = new JButton("next Gesture");
	JButton prevGesture = new JButton("prev Gesture");
	JLabel curGesture = new JLabel("current: "+Integer.toString(showingGesture));

	public Points() {
		showingGesture = Recorderv2.gestureList.size()-1;
		curGesture.setText("current: "+Integer.toString(showingGesture));
		initUI();
	}
	public void recalcNodes() {

		UserGesture ug = new UserGesture();
		ug =Recorderv2.gestureList.get(showingGesture);
		for(int i=1; i<ug.NodeList.size()-1;i++){
			ug.NodeList.get(i).hand0_x_denorm=ug.NodeList.get(i+1).hand0_x_denorm-ug.NodeList.get(0).hand0_x_denorm;
			ug.NodeList.get(i).hand0_y_denorm=ug.NodeList.get(i+1).hand0_y_denorm-ug.NodeList.get(0).hand0_y_denorm;
			ug.NodeList.get(i).hand0_z_denorm=ug.NodeList.get(i+1).hand0_z_denorm-ug.NodeList.get(0).hand0_z_denorm;

			ug.NodeList.get(i).hand0_frontmost_x=ug.NodeList.get(i+1).hand0_frontmost_x-ug.NodeList.get(0).hand0_frontmost_x;
			ug.NodeList.get(i).hand0_frontmost_y=ug.NodeList.get(i+1).hand0_frontmost_y-ug.NodeList.get(0).hand0_frontmost_y;
			ug.NodeList.get(i).hand0_frontmost_z=ug.NodeList.get(i+1).hand0_frontmost_z-ug.NodeList.get(0).hand0_frontmost_z;
			ug.NodeList.get(i).hand0_pitch=ug.NodeList.get(i+1).hand0_pitch-ug.NodeList.get(0).hand0_pitch;
			ug.NodeList.get(i).hand0_roll=ug.NodeList.get(i+1).hand0_roll-ug.NodeList.get(0).hand0_roll;
			ug.NodeList.get(i).hand0_yaw=ug.NodeList.get(i+1).hand0_yaw-ug.NodeList.get(0).hand0_yaw;
			
		}
		ug.NodeList.get(0).hand0_x_denorm=0;
		ug.NodeList.get(0).hand0_y_denorm=0;
		ug.NodeList.get(0).hand0_z_denorm=0;
		
		ug.NodeList.get(0).hand0_frontmost_x=0;
		ug.NodeList.get(0).hand0_frontmost_y=0;
		ug.NodeList.get(0).hand0_frontmost_z=0;
		ug.NodeList.get(0).hand0_pitch=0;
		ug.NodeList.get(0).hand0_roll=0;
		ug.NodeList.get(0).hand0_yaw=0;
		
	}

	private void initUI() {

		setTitle("Palm XY");
		final Surface surface = new Surface();
		showProperty.setBounds(50, 60, 180, 30);
		showProperty.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (property ==0) {
					property=property+1;
					showProperty.setText("show pitch");
					setTitle("finger xy");
				} else if (property==1) {
					property=property+1;
					setTitle("pitch");
					showProperty.setText("show yaw");
				}
				 else if (property==2) {
						property=property+1;
						setTitle("yaw");
						showProperty.setText("show roll");
					}
				 else if (property==3) {
						property=property+1;
						setTitle("roll");
						showProperty.setText("show palm XZ");
					}
				 else if (property==4) {
						property=property+1;
						setTitle("Palm XZ");
						showProperty.setText("show palm XY");
					}
				 else if (property==5) {
						property=0;
						setTitle("Palm XY");
						showProperty.setText("show finger  XY coordinates");
					}
				
				
				
				
				surface.repaint();
			}
		});
		deleteFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(showingGesture).NodeList
						.remove(0);
				recalcNodes();


				surface.repaint();
			}

		});
		deleteLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(showingGesture).NodeList
						.remove(Recorderv2.gestureList
								.get(showingGesture).NodeList
								.size() - 1);
				surface.repaint();
			}
		});
		showArea.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (area == false) {
					area = true;
					showArea.setText("hide area");
				} else if (area == true) {
					area= false;
					showArea.setText("show area");
				}	

				surface.repaint();
			}
		});
		nextGesture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(showingGesture<Recorderv2.gestureList.size()-1)
					showingGesture=showingGesture+1;
				curGesture.setText("current: "+Integer.toString(showingGesture));
				surface.repaint();
			}
		});
		prevGesture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(showingGesture>0)
					showingGesture=showingGesture-1;
				curGesture.setText("current: "+Integer.toString(showingGesture));
				surface.repaint();
			}
		});
		
		add(surface);
		surface.add(showProperty);
		surface.add(deleteFirst);
		surface.add(deleteLast);
		surface.add(showArea);
		surface.add(prevGesture);
		surface.add(nextGesture);
		surface.add(curGesture);
		setSize(750, 750);
		setLocationRelativeTo(null);
	}
}

class ActionMapper extends JFrame {
	JPanel panel = new JPanel();
	JButton mapButton = new JButton("Map ");
	JButton propAddButton = new JButton("add property ");
	JButton actionAddButton = new JButton("add action");
	JButton resetButton = new JButton("reset");
	JButton recordActionButton = new JButton("record key");
	JCheckBox contBox = new JCheckBox("continuous");
	public String[] available_props = new String[]{"A", "B"};
	public String[] available_actions = new String[]{"C", "D"};
	public JList<String> propList;
	public JList<String> map_propList;
	public JList<String> actionList;
	public JList<String> map_actionList;
	public JScrollPane propListScroller;
	public JScrollPane map_propListScroller;
	public JScrollPane actionListScroller;
	public JScrollPane map_actionListScroller;
	public DefaultListModel<String> propListModel = new DefaultListModel<String>();
	public DefaultListModel<String> actionListModel = new DefaultListModel<String>();
	public DefaultListModel<String> map_propListModel = new DefaultListModel<String>();
	public DefaultListModel<String> map_actionListModel = new DefaultListModel<String>();
	public static int settingGesture=0;
	JButton nextGesture = new JButton("next Gesture");
	JButton prevGesture = new JButton("prev Gesture");
	JLabel curGesture = new JLabel();

	public ActionMapper()  {
		getContentPane().add(panel);
		initUI();
		refreshData();
	}

	private void initUI()  {

		setTitle("ActionMapper");
		// final Surface surface = new Surface();
	
		panel.setLayout(null);


	   
	
	    propList = new JList<String>(propListModel);    
		propList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		propList.setLayoutOrientation(JList.VERTICAL);
		propList.setVisibleRowCount(3);
		propListScroller = new JScrollPane(propList);
		actionList = new JList<String>(actionListModel);    
		actionList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		actionList.setLayoutOrientation(JList.VERTICAL);
		actionList.setVisibleRowCount(3);
		actionListScroller = new JScrollPane(actionList);
		map_propList = new JList<String>(map_propListModel);    
		map_propList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		map_propList.setLayoutOrientation(JList.VERTICAL);
		map_propList.setVisibleRowCount(3);
		map_propListScroller = new JScrollPane(map_propList);
		map_actionList = new JList<String>(map_actionListModel);    
		map_actionList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		map_actionList.setLayoutOrientation(JList.VERTICAL);
		map_actionList.setVisibleRowCount(3);
		map_actionListScroller = new JScrollPane(map_actionList);
	    

		curGesture.setBounds(350, 50, 50, 30);
		prevGesture.setBounds(150, 50, 100, 30);
		nextGesture.setBounds(500, 50, 100, 30);
		contBox.setBounds(350, 500, 100, 30);
		recordActionButton.setBounds(550, 450, 100, 30);
		mapButton.setBounds(300, 600, 100, 30);
		propAddButton.setBounds(100, 500, 100, 30);
		actionAddButton.setBounds(550, 500, 100, 30);
		resetButton.setBounds(400, 600, 100, 30);
		mapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				// assign action and properties to gesture
				if(Recorderv2.gestureCount!=0){
				for(int i=0;i<map_propListModel.size();i++){
					if((Recorderv2.gestureList.get(settingGesture).attributes.size()==0) || (Recorderv2.gestureList.get(settingGesture).attributes.size()!=map_propListModel.size() ))	
						Recorderv2.gestureList.get(settingGesture).attributes.add(i, map_propListModel.get(i).toString());}
				for(int i=0;i<map_actionListModel.size();i++){
					if((Recorderv2.gestureList.get(settingGesture).actions.size()==0) || (Recorderv2.gestureList.get(settingGesture).actions.size()!=map_actionListModel.size() ))	
						Recorderv2.gestureList.get(settingGesture).actions.add(i, map_actionListModel.get(i).toString());}
				System.out.print("gesture nr. "+settingGesture+"  "+Recorderv2.gestureList.get(settingGesture).attributes.toString());
				System.out.println("    "+Recorderv2.gestureList.get(settingGesture).actions.toString()+"    continuous:"+contBox.isSelected());
				if (contBox.isSelected()==true)Recorderv2.gestureList.get(settingGesture).cont=true;
				if (contBox.isSelected()==false)Recorderv2.gestureList.get(settingGesture).cont=false;
				}}
		});
		propAddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				 map_propListModel.addElement(propList.getSelectedValue().toString());
				
			}
		});
		actionAddButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				 map_actionListModel.addElement(actionList.getSelectedValue().toString());
				
			}
		});
		resetButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(Recorderv2.gestureCount!=0){
				map_propListModel.clear();
				Recorderv2.gestureList.get(settingGesture).actions.clear();
				map_actionListModel.clear();
				Recorderv2.gestureList.get(settingGesture).attributes.clear();
			}}
		});
		recordActionButton.addActionListener(new ActionListener() {
			private String key;

			@SuppressWarnings({ "null", "static-access" })
			@Override
			public void actionPerformed(ActionEvent event) {
				// TODO Auto-generated method stub
				 JOptionPane jo = new  JOptionPane();
						 jo.showMessageDialog(null, " press a key after closign this dialog");
					
						 KeyListener listener = new KeyListener() {
							 private int k;

							@Override
							 public void keyTyped(KeyEvent e) {
							
							 }

							@Override
							public void keyPressed(KeyEvent e) {
								// TODO Auto-generated method stub
							
								
							}

							@Override
							public void keyReleased(KeyEvent e) {
								// TODO Auto-generated method stub
								k=e.getKeyCode();
								key= e.getKeyText(k);
								 actionListModel.addElement(Integer.toString(k)+"   = "+key);
								 recordActionButton.removeKeyListener(this);
								
							}
						 
						 };
						 
						 recordActionButton.addKeyListener(listener);

					 
					
					}
			
		});
		nextGesture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(settingGesture<Recorderv2.gestureList.size()-1)
					{settingGesture=settingGesture+1;
					refreshData();
				}
				
			}
		});
		prevGesture.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if(settingGesture>0){
					settingGesture=settingGesture-1;
					getContentPane().remove(panel);
					getContentPane().add(panel);
					refreshData();
				}
				
			}
		});


		panel.add(mapButton);
		panel.add(propAddButton);
		panel.add(actionAddButton);
		panel.add(resetButton);
		panel.add(recordActionButton);
		panel.add(contBox);
		panel.add(prevGesture);
		panel.add(nextGesture);
		panel.add(curGesture);
		propListScroller.setBounds(50, 100, 100,300);
		map_propListScroller.setBounds(270, 100, 100,300);
		actionListScroller.setBounds(600, 100, 100,300);
		map_actionListScroller.setBounds(380, 100, 100,300);
		
		//panel.add(list);
		panel.add(propListScroller);
		panel.add(map_propListScroller);
		panel.add(actionListScroller);
		panel.add(map_actionListScroller);
		
		setSize(750, 750);
		setLocationRelativeTo(null);
	}

private void refreshData(){
	propListModel.clear();
	map_propListModel.clear();
	map_actionListModel.clear();
	propListModel.addElement("XY");
    propListModel.addElement("XZ");
    propListModel.addElement("frontmost_finger_XY");
    propListModel.addElement("frontmost_finger_XZ");
	propListModel.addElement("roll");
    propListModel.addElement("yaw");
	propListModel.addElement("pitch");
	 curGesture.setText(Integer.toString(settingGesture));
		for(int i=0;i<Recorderv2.gestureList.get(settingGesture).attributes.size();i++)
			map_propListModel.addElement(Recorderv2.gestureList.get(settingGesture).attributes.get(i));
		for(int i=0;i<Recorderv2.gestureList.get(settingGesture).actions.size();i++)
			map_actionListModel.addElement(Recorderv2.gestureList.get(settingGesture).actions.get(i));
		if (Recorderv2.gestureList.get(settingGesture).cont==true)
			contBox.setSelected(true);
		
		
	 
}
}
class FPS extends JFrame {


	public FPS() {

		initUI();
	}

	private void initUI() {

		setTitle("FPS");
		final FPSGraph surface = new FPSGraph();
		
		add(surface);
		setSize(750, 750);
		setLocationRelativeTo(null);
	}
}
class FPSGraph extends JPanel {

	private void doDrawing(Graphics g, boolean finger, boolean area) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);

		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;



	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		//doDrawing(g, Points.property, Points.area);
	}




}