package leap;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class MapperGUI extends JFrame {

	public static boolean statusRecording = false;
	public static boolean runningRecording = false;
	public static boolean statusRecognizing = false;
	public static boolean runningRecognizing = false;

	public MapperGUI() {
		// TODO Auto-generated constructor stub
		JPanel panel = new JPanel();

		JButton startRecButton = new JButton("Start recording");
		JButton stopRecButton = new JButton("Stop recordiing");
		JButton startMapper = new JButton("Assign action to parameters");
		JButton showPlot = new JButton("Show Plot");
		JButton startRecog = new JButton("Start Recognition");


		getContentPane().add(panel);

		setTitle("LeapRecorder");

		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		startRecButton.setBounds(50, 60, 80, 30);
		startRecButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				statusRecording = true;
				if (runningRecording == false) {
					final Thread t = new Thread(new Runnable() {
						public void run() {
							// this shall get executed, after start() has been
							// called, outside the EDT
							try {

								Recorderv2.record(Recorderv2.gestureCount,
										Recorderv2.controller);
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
				}
			}
		});
		stopRecButton.setBounds(50, 60, 80, 30);
		stopRecButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusRecording = false;
				runningRecording = false;

			}
		});
		stopRecButton.setBounds(50, 60, 80, 30);
		showPlot.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

				Points ps = new Points();
				ps.setVisible(true);
			}
		});
		startMapper.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {

			
				ActionMapper ps = new ActionMapper();
				ps.setVisible(true);
			}
		});
		startRecog.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				statusRecognizing = true;
				if (runningRecognizing == false) {
					final Thread t = new Thread(new Runnable() {
						public void run() {
							// this shall get executed, after start() has been
							// called, outside the EDT
							try {

								Recognizer.recog(Recorderv2.gestureList,
										Recognizer.controller);
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
					runningRecognizing = true;
				}
			
			
			}
		});
		panel.add(startRecButton);
		panel.add(stopRecButton);
		panel.add(showPlot);
		panel.add(startMapper);
		panel.add(startRecog);
		// panel2.add(new Surface());

		setSize(600, 400);

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

	private void doDrawing(Graphics g, boolean finger) {

		Graphics2D g2d = (Graphics2D) g;

		g2d.setColor(Color.blue);

		Dimension size = getSize();
		Insets insets = getInsets();

		int w = size.width - insets.left - insets.right;
		int h = size.height - insets.top - insets.bottom;

		if (finger == false) {
			for (int i = 1; i < Recorderv2.gestureList
					.get(Recorderv2.gestureCount - 1).NodeList.size(); i++) {

				int x = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).palmPosition().getX() % w);
				int y = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).palmPosition().getZ() % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).palmPosition()
							.getX() % w);
					nextY = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).palmPosition()
							.getZ() % h);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height) / 2) + y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height) / 2) + y,
						((size.width) / 2) + nextX, ((size.height) / 2) + nextY);
			}
		} else if (finger == true) {
			for (int i = 1; i < Recorderv2.gestureList
					.get(Recorderv2.gestureCount - 1).NodeList.size(); i++) {

				int x = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).fingers().frontmost().tipPosition()
						.getX() % w);
				int y = (int) (Recorderv2.gestureList
						.get(Recorderv2.gestureCount - 1).NodeList.get(i).frame
						.hands().get(0).fingers().frontmost().tipPosition()
						.getZ() % h);
				int nextX = 0;
				int nextY = 0;
				if (Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).NodeList
						.size() != (i + 1)) {
					nextX = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).fingers()
							.frontmost().tipPosition().getX() % w);
					nextY = (int) (Recorderv2.gestureList
							.get(Recorderv2.gestureCount - 1).NodeList
							.get(i + 1).frame.hands().get(0).fingers()
							.frontmost().tipPosition().getZ() % h);
				}
				if (i % 10 == 1)
					g2d.drawString(Integer.toString(i), ((size.width) / 2) + x,
							((size.height) / 2) + y);
				g2d.drawLine(((size.width) / 2) + x, ((size.height) / 2) + y,
						((size.width) / 2) + nextX, ((size.height) / 2) + nextY);
			}
		}

	}

	@Override
	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		doDrawing(g, Points.finger);
	}

}

class Points extends JFrame {
	public static boolean finger = false;
	JButton showFinger = new JButton("show finger coordinates");

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
				if(finger==false){finger = true; showFinger.setText("show palm coordinates");}
				else if(finger==true){finger=false; showFinger.setText("show finger coordinates");}
				surface.repaint();
			}
		});
	
		add(surface);
		surface.add(showFinger);
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
		//final Surface surface = new Surface();
		mapButton.setBounds(50, 60, 80, 30);
		mapButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).attribute="XZ";
				Recorderv2.gestureList.get(Recorderv2.gestureCount - 1).action="XZ";
			}
		});
	
		//add(surface);
		panel.add(mapButton);
		setSize(750, 750);
		setLocationRelativeTo(null);
	}
}

