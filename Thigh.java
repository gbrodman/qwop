import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Thigh {

	private int mass;
	private int xloc; 
	private int yloc;
	
	private int width;
	private int length;
	
	private double angle; // angle away from vertical axis
	
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
			angle = 10 * pi / 180;
		} else {
			angle = 5 * pi / 180;
		}
		this.start = new GPoint(xloc, yloc);
		this.end = new GPoint(xloc + length * Math.sin(angle), yloc + length * Math.cos(angle));
	}
	
	public void drawShape(GraphicsProgram p) {
		p.add(new GLine(start.getX(), start.getY(), end.getX(), end.getY()));
	}
	
	public void moveUnit() {
		// move the thigh in some direction by increasing the angle and resetting the endpoint
	}
	
}
