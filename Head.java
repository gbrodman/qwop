import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GOval;
import acm.program.GraphicsProgram;


public class Head extends BodyPart {
    
    private double radius;
 
    private GPoint neck; 
    private GLine line;
    private GOval circle;
    private static double pi = 3.14159;
    
    public Head(GraphicsProgram p, GPoint n) {
      mass = 10;
      neck = n;
      radius = 20;
      circle = new GOval(neck.getX() - radius, neck.getY() - 2*radius, 2*radius, 2*radius);
      p.add(circle);
    }
    
    public GPoint getCenter() {
      return new GPoint(neck.getX(), neck.getY() - radius);
    }
    public void update() {
      circle.setLocation(neck.getX() - radius, neck.getY() - 2*radius);
    }

    @Override
    public boolean outOfBounds() {
      // TODO Auto-generated method stub
      return false;
    }
  }