import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;


public class Torso extends BodyPart{
    
    private int length;
    
    private GPoint neck;
    private GPoint neckCopy;
    private GPoint hips;
    private GPoint hipsCopy;  
    private GLine line;
    private GPoint shoulders;
    private GPoint shouldersCopy;
    private static double pi = 3.14159;
    
    public Torso(GraphicsProgram p, GPoint h) {
      mass = 15;
      length = 90;
      angle = 9 * pi / 10;
      angularVelocity = 0;
      hips = h;
      hipsCopy = new GPoint(hips);
      neck = new GPoint(hips.getX() + length * Math.sin(angle), hips.getY() + length * Math.cos(angle));
      neckCopy = new GPoint(neck);
      line = new GLine(neck.getX(), neck.getY(), hips.getX(), hips.getY());
      p.add(line);
      shoulders = new GPoint(.75*neck.getX() + .25*hips.getX(), .75*neck.getY() + .25*hips.getY());
      shouldersCopy = new GPoint(shoulders);
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
    
    public void copyAllValues() {
      neckCopy.setLocation(neck);
      hipsCopy.setLocation(hips);
      shouldersCopy.setLocation(shoulders);
      angleCopy = angle;
      angularVelocityCopy = angularVelocity;
    }
    
    public void restoreAllValues() {
      neck.setLocation(neckCopy);
      hips.setLocation(hipsCopy);
      shoulders.setLocation(shouldersCopy);
      angularVelocity = angularVelocityCopy;
      angle = angleCopy;
    }

    @Override
    public boolean outOfBounds() {
      return neck.getY() > 300;
    }
  }