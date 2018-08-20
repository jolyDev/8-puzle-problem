package Pazzie;

public class DNode {
	
		public String cells_placement = null;

		public DNode web[] = new DNode[5]; // 0 - Father // 1 - UP // 2 - DOWN // 3 - LEFT // 4 - Right

		public String action = null; // UP, DOWN, LEFT, RIGHT
		
		public DNode(String data, String actions, DNode parent) {
			this.cells_placement = new String(data);
			this.action = new String(actions);
			this.web[0] = parent;
		}
}
