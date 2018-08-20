package Pazzie;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class client extends JFrame implements Runnable {
	
	static private Socket connection;	
	static private ObjectOutputStream output;
	static private ObjectInputStream input;
	final static private int PORT = 4321;
	
	public static int[] b = new int[9];
	

	final JTextField t1 = new JTextField(10);
	final JButton b1 = new JButton("Solve");
	
	public static void main(String[] args) {
		new Thread(new client("Test")).start();
		new Thread(new Game_Board()).start();
	}

	public client (String name) {
		super(name);
		setLayout(new GridLayout(2,1));
		setSize(400, 450);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLocationRelativeTo(null);
		
		//b[i].setFont(new Font("Arial", Font.PLAIN, 40));
		b1.setFont(new Font("Arial", Font.PLAIN, 140));
		t1.setFont(new Font("Arial", Font.PLAIN, 140));
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == b1)
					solveGame();
			}});
		
		add(t1); add(b1);

		setSize(720, 450); // if remove this line, strange bug shows up (it`s not my fault ;) )
	}
	
	protected void solveGame() {

		t1.setText("Computing...");
		// sendData(Integer.valueOf(t1.getText()));
		Train.InitialState = sendData("GetInitState");
		System.out.println("Got here");
		String[] actions = Train.proceed();
		System.out.println("Got here");
		for (String st: actions) {
			if (st == null)
				continue;
			sendData(st);
			t1.setText(st);
			update (this.getGraphics());
			try {
				Thread.sleep(1500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		t1.setText("Solved");
	}

	@Override
	public void run() {}
	
	private static String sendData(Object obj) {
		String response = null;
		try {
			connection = new Socket(InetAddress.getByName("127.0.0.1"), PORT);
			output = new ObjectOutputStream(connection.getOutputStream());
			output.flush();
			output.writeObject(obj);
			input = new ObjectInputStream(connection.getInputStream());
			response = (String) input.readObject();
			connection.close();
		} catch (Exception e) {
			System.out.println("Something going wrong");
			e.printStackTrace();
		}
		return response;
	}
}
