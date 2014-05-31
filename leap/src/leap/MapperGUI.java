package leap;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.*;

import com.leapmotion.leap.Controller;
import com.leapmotion.leap.GestureList;
import com.leapmotion.leap.Leap;

public class MapperGUI extends JFrame {

	public static boolean statusRecording = false;
	public static boolean runningRecording = false;
	public static boolean statusRecognizing = false;
	public static boolean runningRecognizing = false;
	public static JLabel noLeap = new JLabel("No Leap connected!!!!");
	public static Controller controller = new Controller();
	public static SampleListener2 listener = new SampleListener2();

	public MapperGUI() {
		// TODO Auto-generated constructor stub
		JPanel panel = new JPanel();
		controller.addListener(listener);
		JButton startRecButton = new JButton("Start recording");
		JButton stopRecButton = new JButton("Stop recordiing");
		JButton stopRecogButton = new JButton("Stop recorgnizing");
		final JButton deleteButton = new JButton("delete gesture");
		JButton startMapper = new JButton("Assign action to parameters");
		JButton showPlot = new JButton("Show Plot");
		JButton showFPS = new JButton("show FPS");
		final JButton startRecog = new JButton("Start Recognition");

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
		showPlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Points.area=false;
				Points.finger=false;
				if(!Recorderv2.gestureList.isEmpty())
					{Points ps = new Points();
				Points.area=false;
				Points.finger=false;
				ps.setVisible(true);}
				
			}
		});
		showFPS.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

	
				if(!Recorderv2.gestureList.isEmpty())
					{FPS fps = new FPS();
	
				fps.setVisible(true);}
				
			}
		});
		startMapper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				ActionMapper am = new ActionMapper();
				am.setVisible(true);
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
								}
							}
						});
						t.start();
						runningRecognizing = true;
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

			}
		});

		panel.add(startRecButton);
		panel.add(stopRecButton);
		panel.add(showPlot);
		panel.add(startMapper);
		panel.add(startRecog);
		panel.add(deleteButton);
		panel.add(stopRecogButton);
		panel.add(noLeap);
		panel.add(showFPS);
		noLeap.setVisible(false);
		startRecButton.setBounds(100, 100, 120, 30);
		stopRecButton.setBounds(250, 100, 140, 30);
		showPlot.setBounds(100, 200, 120, 30);
		startMapper.setBounds(100, 300, 120, 30);
		startRecog.setBounds(100, 400, 140, 30);
		stopRecogButton.setBounds(300, 400, 140, 30);
		showFPS.setBounds(300, 200, 140, 30);
		deleteButton.setBounds(400, 100, 180, 30);
		noLeap.setBounds(200, 500, 180, 30);
		setResizable(false);

		// panel2.add(new Surface());

		setSize(800, 600);

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stubThread t=new Thread(new Runnable(){

		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				MapperGUI ex = new MapperGUI();
				ex.setVisible(true);

			}
		});

	}

}

class Surface extends JPanel {

	private void doDrawing(Graphics g, boolean finger, boolean area) {

		Graphics2D g2d = (Graphics2D) g;
		Graphics2D a2d = (Graphics2D) g;

		g2d.setColor(Color.blue);
	

		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;

		if (finger == false) {
			for (int i = 1; i < Recorderv2.gestureList
					.get(Recorderv2.gestureCount - 1).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).palmPosition().getX() % w);
				int y = (int) -(Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).palmPosition().getY() % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).palmPosition()
							.getX() % w);
					nextY = (int) -(Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).palmPosition()
							.getY() % h);
					
				}
				if (i % 10 == 1){
					
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)) + y);}
				g2d.drawLine(((size.width) / 2) + x, ((size.height)) + y,
						((size.width) / 2) + nextX, ((size.height)) + nextY);
				if(area==true){
					g2d.setColor(Color.red);
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2),
							(int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2));
					a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2),
							(int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2));
				//	a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2),
				//			(int) (((size.width) / 2) + nextX+Recognizer.deviationX*300/2),(int) (((size.height)) + nextY+Recognizer.deviationZ*300/2));
				//	a2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2),
					//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height)) + nextY-Recognizer.deviationZ*300/2));
				//g2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + y-Recognizer.deviationZ*300/2),
				//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + nextY-Recognizer.deviationZ*300/2));
					g2d.setColor(Color.blue);
					}
			}
		} else if (finger == true) {
			for (int i = 1; i < Recorderv2.gestureList
					.get(Recorderv2.gestureCount - 1).NodeList.size() - 1; i++) {

				int x = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).fingers().frontmost().tipPosition()
						.getX() % w);
				int y = (int) -(Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).fingers().frontmost().tipPosition()
						.getY() % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).fingers()
							.frontmost().tipPosition().getX() % w);
					nextY = -(int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).fingers()
							.frontmost().tipPosition().getY() % h);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height)) + y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height)) + y,
						((size.width) / 2) + nextX, ((size.height)) + nextY);
			
			if(area==true){
				a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y+Recognizer.deviationZ*300/2),
						(int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2));
				a2d.drawLine((int) (((size.width) / 2) + x+Recognizer.deviationX*300/2),(int) (((size.height)) + y-Recognizer.deviationZ*300/2),
						(int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) ) + y+Recognizer.deviationZ*300/2));
			//g2d.drawLine((int) (((size.width) / 2) + x-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + y-Recognizer.deviationZ*300/2),
			//		(int) (((size.width) / 2) + nextX-Recognizer.deviationX*300/2),(int) (((size.height) / 2) + nextY-Recognizer.deviationZ*300/2));
				}
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, Points.finger, Points.area);
		
	}

}

class Points extends JFrame {
	public static boolean finger = false;
	public static boolean area = false;
	JButton showFinger = new JButton("show finger coordinates");
	JButton deleteFirst = new JButton("delete First node");
	JButton deleteLast = new JButton("delete Last node");
	JButton showArea = new JButton("show recognition area (beta");

	public Points() {

		initUI();
	}

	private void initUI() {

		setTitle("Points");
		final Surface surface = new Surface();
		showFinger.setBounds(50, 60, 80, 30);
		showFinger.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				if (finger == false) {
					finger = true;
					showFinger.setText("show palm coordinates");
				} else if (finger == true) {
					finger = false;
					showFinger.setText("show finger coordinates");
				}
				surface.repaint();
			}
		});
		deleteFirst.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.remove(0);

				surface.repaint();
			}
		});
		deleteLast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.remove(Recorderv2.gestureList
								.get(Recorderv2.gestureCount - 1).NodeList
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
		add(surface);
		surface.add(showFinger);
		surface.add(deleteFirst);
		surface.add(deleteLast);
		surface.add(showArea);
		setSize(750, 750);
		setLocationRelativeTo(null);
	}
}

class ActionMapper extends JFrame {
	JPanel panel = new JPanel();
	JButton mapButton = new JButton("Map ");

	public ActionMapper() {
		getContentPane().add(panel);
		initUI();
	}

	private void initUI() {

		setTitle("ActionMapper");
		// final Surface surface = new Surface();
		mapButton.setBounds(50, 60, 80, 30);
		mapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).attribute = "XZ";
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).action = "XZ";
			}
		});

		// add(surface);
		panel.add(mapButton);
		setSize(750, 750);
		setLocationRelativeTo(null);
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

		for(int i=0;i<Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList.size()-1;i++){
			float factor=((float)w/(float)(Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList.size()-1));
			int x = (int) ((float)i*factor);
			System.out.println(factor);
			int y = (int) (Recorderv2.gestureList
					
					.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame.currentFramesPerSecond() % h);


			if (i % 500 == 1)
				g2d.drawString(Integer.toString((int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame.currentFramesPerSecond() % h)), x,
						((size.height) / 2) - y);
	
				g2d.drawLine( x, ((size.height) / 2) - y*4,
					x, ((size.height) / 2) - y*4);
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, Points.finger, Points.area);
	}

}