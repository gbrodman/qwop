import acm.graphics.GLine;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Thigh {

	private int mass;
	private int xloc; // 0 is aligned vertically
	private int yloc;
	
	private int width;
	private int length;
	
	private double angleToBody; // -45 to 90 degrees
	
	private GPoint start;
	
	
	private static double pi = 3.14159;
	
	public Thigh(boolean isLeft) {
		mass = 8;
		width = 20;
		length = 50;;
		xloc = 200;
		yloc = 200;
		if (isLeft) {
			angleToBody = 10 * pi / 180;
		} else {
			angleToBody = 5 * pi / 180;
		}
		this.shape = new GLine(xloc, yloc, xloc + length * Math.sin(angleToBody), yloc + length * Math.cos(angleToBody));
	}
	
	public void drawShape(GraphicsProgram p) {
		p.add(shape);
	}
	
}
