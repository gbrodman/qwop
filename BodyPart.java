import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class BodyPart {

	int mass;
	int xloc; // 0 is aligned vertically
	int yloc;
	
	int width;
	int height;
	
	GRect shape;
	
	public BodyPart(int mass, int width, int height, int yloc) {
		this.mass = mass;
		this.width = width;
		this.height = height;
		this.yloc = yloc;
		this.shape = new GRect(width, height);
		xloc = 0;
	}
	
	public void drawShape(GraphicsProgram p) {
		
	}
	
}
