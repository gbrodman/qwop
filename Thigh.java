import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Thigh {

	int mass;
	int xloc; // 0 is aligned vertically
	int yloc;
	
	int width;
	int height;
	
	double angleToBody; // -45 to 90 degrees
	
	GRect shape;
	
	public Thigh(int width, int yloc) {
		this.mass = 8;
		this.width = 20;
		this.height = 50;
		this.yloc = yloc;
		this.shape = new GRect(width, height);
		xloc = 200;
		yloc = 200;
		angleToBody = 0;
	}
	
	public void drawShape(GraphicsProgram p) {
		
	}
	
}
