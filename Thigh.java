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
	
	public Thigh() {
		mass = 8;
		width = 20;
		height = 50;;
		xloc = 200;
		yloc = 200;
		this.shape = new GRect(xloc, yloc, width, height);
		angleToBody = 0;
	}
	
	public void drawShape(GraphicsProgram p) {
		
	}
	
}
