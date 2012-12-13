import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.program.GraphicsProgram;


public class Arm extends BodyPart{
  
  private int length;
  
  private GPoint shoulders;
  private GPoint end;
  private GLine line;
  private static double pi = 3.14159;
  
  public Arm(GraphicsProgram p, boolean isLeft, GPoint s) {
    mass = 5;
    length = 65;
    if (isLeft) {
      angle = pi/4;
    }
    else {
      angle = -pi/6;
    }
    shoulders = s;
    end = new GPoint(shoulders.getX() + length * Math.sin(angle), shoulders.getY() + length * Math.cos(angle));
    line = new GLine(shoulders.getX(), shoulders.getY(), end.getX(), end.getY());
    p.add(line);
  }
  
  public GPoint getEnd() {
    return end;
  }
  public GPoint getStart() {
    return shoulders; 
  }
  public double getAngle(){
    return angle;
  }

  public GPoint getCenter() {
    return new GPoint(.5*shoulders.getX() + .5*end.getX(), .5*shoulders.getY() + .5*end.getY());
  }
  
  public void update() {
    end.setLocation(shoulders.getX() + length * Math.sin(angle), shoulders.getY() + length * Math.cos(angle));
    line.setStartPoint(shoulders.getX(), shoulders.getY());
    line.setEndPoint(end.getX(), end.getY());
  }
  
  public void copyAllValues() {}
  public void restoreAllValues() {}
  
  @Override
  public boolean outOfBounds() {
    // TODO Auto-generated method stub
    return false;
  }
}
