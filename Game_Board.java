package Pazzie;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Game_Board extends JFrame implements Runnable {
	//Server variables
	static private ServerSocket server;
	static private Socket connection;	
	static private ObjectOutputStream output;
	static private ObjectInputStream input;
	final static private int PORT = 4329;
	
	private static Point space = new Point(2,2);
	JButton[] b = new JButton[9];
    
    public Game_Board() {
    	super(":)");
    	setLayout(new GridLayout(0,3));

		setSize(400, 400);
		// Font baseFont = Font.createFont(Font.TRUETYPE_FONT, 32);

		for (int i = 0; i < b.length; i++) {
			if (i != b.length - 1)
				b[i] = new JButton(Integer.toString(i + 1));
			else b[i] = new JButton("");
		    b[i].setFont(new Font("Arial", Font.PLAIN, 40));
		    int y = i;
			b[i].addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (e.getSource() == b[y])
						moveSomewere(y, b);
				}});
	    	add(b[i]);
		}
		setVisible(true);
    }
    
    public static void main(String[] argc) {
    	new Thread(new Game_Board()).start();
    }

	@Override
	public void run() {
		try {
			server = new ServerSocket(PORT, 1);
			for(;;) {
				connection = server.accept();
				output = new ObjectOutputStream(connection.getOutputStream());
				input = new ObjectInputStream(connection.getInputStream());
				String dir = (String) input.readObject();
				System.out.println("->" + dir);
				int pos = 0; 

				if (dir.equals("UP"))
					pos = (space.y - 1) * 3 + space.x;
				if (dir.equals("DOWN"))
					pos = (space.y + 1) * 3 + space.x;
				if (dir.equals("LEFT"))
					pos = space.y * 3 + space.x - 1;
				if (dir.equals("RIGHT"))
					pos = space.y * 3 + space.x + 1;

				if (dir.equals("GetInitState")) {
					String response = "";
					for (JButton jb : b) 
						if (jb.getText().equals(""))
							response = response + '0';
						else
							response = response + jb.getText();
					output.writeObject(response);
				} else {
					moveSomewere(pos, b);
					output.writeObject("Succes");
				}
				connection.close();
				}
		} catch (Exception e) {
			System.out.println("Connection not succide");
			e.printStackTrace();
		}
	}
	
	public void moveSomewere(int i, JButton[] b) {
		if (i > 9 || i < 0)
			return;
		System.out.println("\"" + b[i].getText() + "\"");
		System.out.println("\"" + b[space.x + space.y * 3].getText() + "\"");
		int x = i % 3; int y = i / 3;
		if (space.x == x || space.y == y)
		if (space.x + 1 >= x && space.x - 1 <= x)
			if (space.y + 1 >= y && space.y - 1 <= y) {
				String temp = b[i].getText();
				b[i].setText(b[space.x + space.y * 3].getText());
				b[space.x + space.y * 3].setText(temp);
				space.x = x; space.y = y;
			}
		update (this.getGraphics());
	}
}