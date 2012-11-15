import acm.graphics.GLine;
import acm.graphics.GPoint;
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
	private GPoint end;
	
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
		this.start = new GPoint(xloc, yloc);
		this.end = new GPoint(xloc + length * Math.sin(angleToBody), yloc + length * Math.cos(angleToBody));
	}
	
	public void drawShape(GraphicsProgram p) {
		p.add(new GLine(start.getX(), start.getY(), end.getX(), end.getY()));
	}
	
}
