package Pazzie;

import java.util.LinkedList;
import java.util.Queue;

public class Train {
	
	static final int puzleSize = 9;
	
	static String[] action = {"", "UP", "DOWN", "LEFT", "RIGHT"};
	
	public static String InitialState = "152703846";
	
	public static String[] proceed() { // +
		System.out.print(InitialState);
		DNode root = new DNode(InitialState, "START", null);
		Queue<DNode> q = new LinkedList<DNode>();
		q.add(root);
		
		for(;!q.isEmpty();) {
			
			DNode node = q.remove();
			
			if (node == null)
				continue;
			
			if (isGoalState(node.cells_placement))
				return printPath(node);
			
			for (int i = 1; i < 5; i++) {
				if (!isIncompatibleMoves(node.prevAction, action[i])) {

					System.out.println(node.cells_placement + " " + action[i] + " " + Move(node.cells_placement, action[i]));
					String move = Move(node.cells_placement, action[i]);

					if (!move.equals("can`t")) {
						node.web[i] = new DNode(move, action[i], node, move);
						q.add(node.web[i]);
					}
				}
			}
		}
		return null;
	}
	
	static boolean isIncompatibleMoves(String com, String com2) {
		// 1 - UP // 2 - DOWN // 3 - LEFT // 4 - Right
		if (com.equals("UP") && com2.equals("DOWN"))
			return true;
		if (com2.equals("UP") && com.equals("DOWN"))
			return true;
		if (com.equals("LEFT") && com2.equals("RIGHT"))
			return true;
		if (com2.equals("RIGHT") && com.equals("LEFT"))
			return true;
		return false;
	}
	
	private static String[] printPath(DNode node) {
		String[] st = new String[25];
		int i = 0;
		for (; node.web[0] != null; i++) {
			st[i] = node.action;
			System.out.println(st[i]);
			node = node.web[0];
		}
		String[] reverse = new String[i + 1];
		for (int k = 0; k <= i; k++)
			reverse[k] = st[i - k];
		return reverse;
	}
	
	public static String Move(String state, String direction) {
		char[] Ch = state.toCharArray();
		int i = 0; char temp = '\0';
		for (;i < puzleSize && Ch[i] != '0'; i++)
			;
		int zeroX = i % 3; int ZeroY = i / 3; 
		int x = i % 3; int y = i / 3;
		
		if (direction.endsWith("UP"))
			if (y != 0) {
				temp = Ch[i];
				Ch[i] = Ch[i - 3];
				Ch[i - 3] = temp;
			} else return "can`t";
		
		if (direction.endsWith("DOWN"))
			if(y != 2) {
				temp = Ch[i];
				Ch[i] = Ch[i + 3];
				Ch[i + 3] = temp;
		} else return "can`t";
		
		if (direction.endsWith("LEFT")) 
			if (x != 0) {
				temp = Ch[i];
				Ch[i] = Ch[i - 1];
				Ch[i - 1] = temp;
			} else return "can`t";
		
		if (direction.endsWith("RIGHT"))
			if (x != 2) {
				temp = Ch[i];
				Ch[i] = Ch[i + 1];
				Ch[i + 1] = temp;
			} else return "can`t";
		System.out.println(String.valueOf(Ch));
		return String.valueOf(Ch);
	}
	
	public static boolean isGoalState(String gameField) {
		return gameField.equals("123456780");
	}
}
