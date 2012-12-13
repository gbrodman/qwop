import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Calf extends BodyPart {
  
  private int length;
  
  private double angleFromThigh;
  
  private GPoint start;
  private GPoint end;
  private GLine line;
  private Thigh thigh;
  private static double pi = 3.14159;
  
  public Calf(GraphicsProgram p, boolean isBack, Thigh t) {
    mass = 10;
    length = 50;
    // difference between angle with calf's ground and corresponding 
    // thigh's angle with ground; between 0 and 135 degrees
    if (isBack) {
      angleFromThigh = 20 * pi / 180;
    } else {
      angleFromThigh = 40 * pi / 180;
    }
    angle = t.getAngle() - angleFromThigh;
    angularVelocity = 0;
    thigh = t;
    this.start = thigh.getEnd();
    this.end = new GPoint(start.getX() + length * Math.sin(angle),
                          start.getY() + length * Math.cos(angle));
    line = new GLine(start.getX(), start.getY(), end.getX(), end.getY());
    p.add(line);
  }
  
  
  public void update(boolean plantFoot) {
    angularVelocity *= .8;
    angle += angularVelocity + thigh.angularVelocity;
    if (plantFoot) {
      start = new GPoint(end.getX() - length * Math.sin(angle),
                         end.getY() - length * Math.cos(angle));
      thigh.setKnee(start);
    }
    else {
      start = thigh.getEnd();
      angleFromThigh = thigh.getAngle() - angle;
      if (angleFromThigh > (9*pi/10)) {
        angle = thigh.getAngle() - (9*pi/10);
      }
      if (angleFromThigh < (pi/8)) {
        angle = thigh.getAngle() - pi/8;
      }
      end = new GPoint(start.getX() + length * Math.sin(angle),
                       start.getY() + length * Math.cos(angle));
    }
    line.setStartPoint(start.getX(), start.getY());
    line.setEndPoint(end.getX(), end.getY());  
  }

  public GPoint getCenter() {
    return new GPoint(.5*start.getX() + .5*end.getX(), .5*start.getY() + .5*end.getY());
  }
  
  public GPoint getEnd() {
    return end;
  }

  public void scroll(double amount) {
    end.setLocation(end.getX() + amount, end.getY());
  }

  @Override
  public boolean outOfBounds() {
    // TODO Auto-generated method stub
    return false;
  }
  
}