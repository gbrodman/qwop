import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;


public class Torso extends BodyPart{
    
    private int length;
    
    private GPoint neck;
    private GPoint hips;
    private GLine line;
    private GPoint shoulders;
    private static double pi = 3.14159;
    
    public Torso(GraphicsProgram p, GPoint h) {
      mass = 15;
      length = 90;
      angle = 9 * pi / 10;
      angularVelocity = 0;
      hips = h;
      neck = new GPoint(hips.getX() + length * Math.sin(angle), hips.getY() + length * Math.cos(angle));
      line = new GLine(neck.getX(), neck.getY(), hips.getX(), hips.getY());
      p.add(line);
      shoulders = new GPoint(.75*neck.getX() + .25*hips.getX(), .75*neck.getY() + .25*hips.getY());
    }
    
    public GPoint getEnd() {
      return hips;
    }
    
    public GPoint getStart() {
      return neck; 
    }
    
    public GPoint getShoulders(){
      shoulders.setLocation(.75*neck.getX() + .25*hips.getX(), .75*neck.getY() + .25*hips.getY());
      return shoulders;
    }

    public GPoint getCenter() {
      return new GPoint(.5*neck.getX() + .5*hips.getX(), .5*neck.getY() + .5*hips.getY());
    }
    
    public void update() {
      angularVelocity *= decayRate;
      angle += angularVelocity;
      neck.setLocation(hips.getX() + length * Math.sin(angle), hips.getY() + length * Math.cos(angle));
      shoulders.setLocation(.75*neck.getX() + .25*hips.getX(), .75*neck.getY() + .25*hips.getY());
      line.setStartPoint(neck.getX(), neck.getY());
      line.setEndPoint(hips.getX(), hips.getY());
    }

    public void multiplyVelocity(double amount) {
      angularVelocity *= amount;
    }

    @Override
    public boolean outOfBounds() {
      return neck.getY() > 300;
    }
  }