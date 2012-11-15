import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Thigh {

	private int mass;
	private int xloc; // 0 is aligned vertically
	private int yloc;
	
	private int width;
	private int height;
	
	private double angleToBody; // -45 to 90 degrees
	
	private GLine shape;
	
	public Thigh(boolean isLeft) {
		mass = 8;
		width = 20;
		height = 50;;
		xloc = 200;
		yloc = 200;
		if (isLeft) {
			angleToBody = 10;
		} else {
			angleToBody = 5;
		}
		this.shape = new GLine(xloc, yloc, xloc, 250);
	}
	
	public void drawShape(GraphicsProgram p) {
		p.add(shape);
	}
	
}
