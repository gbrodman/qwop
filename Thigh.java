import acm.graphics.GLine;
import acm.graphics.GPoint;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;


public class Thigh extends BodyPart{

	private int length;
	
	private GPoint hips;
	private GPoint hipsCopy;
	private GPoint end;
	private GPoint endCopy;
	private GLine line;

    private double verticalVelocity;
    private double horizontalVelocity;
  
	private static double pi = 3.14159;
	
	public Thigh(GraphicsProgram p, boolean isBack, GPoint h) {
		mass = 8;
		length = 60;
//		if (isBack) {
//		  angle = 30 * pi / 180;
//		} else {
//		  angle = 0;
//		}
		if (isBack) {
			  angle = Math.random() * pi / 4;
			} else {
			  angle = Math.random() * pi / 4 - pi / 8;
		}
		hips = h;
		hipsCopy = new GPoint(h);
		angularVelocity = 0;
		verticalVelocity = 0;
    end = new GPoint(hips.getX() + length * Math.sin(angle), hips.getY() + length * Math.cos(angle));
    endCopy = new GPoint(end);
    line = new GLine(hips.getX(), hips.getY(), end.getX(), end.getY());
    p.add(line);
	}
	
	public void update(boolean fixKnee) {
	  angularVelocity *= decayRate;
	  horizontalVelocity *= decayRate;
	  angle += angularVelocity;
	  if (fixKnee) {
	    end.setLocation(end.getX() + horizontalVelocity, end.getY() + verticalVelocity);
	    hips.setLocation(end.getX() - length * Math.sin(angle), end.getY() - length * Math.cos(angle));
	  }
	  else {
	    hips.setLocation(hips.getX(), hips.getY() + verticalVelocity);
	    end.setLocation(hips.getX() + length * Math.sin(angle), hips.getY() + length * Math.cos(angle));
	  }
    line.setStartPoint(hips.getX(), hips.getY());
    line.setEndPoint(end.getX(), end.getY());
	}

	public GPoint getEnd() {
	  return end;
	}
	
	public GPoint getStart() {
	  return hips; 
	}
	
  public GPoint getCenter() {
    return new GPoint(.5*hips.getX() + .5*end.getX(), .5*hips.getY() + .5*end.getY());
  }
  
  public void setKnee(GPoint knee) {
    end = knee;
    line.setEndPoint(end.getX(), end.getY());
  }
  
  public void increaseVertVelocity(double amount) {
    verticalVelocity += amount;
  }
  
  public void increaseHorVelocity(double amount) {
    horizontalVelocity += amount;
  }
  
  public double getVertVelocity() {
    return verticalVelocity;
  }
  
  public void setVertVelocity(double amount) {
    verticalVelocity = amount;
  }
  

  public void copyAllValues() {
	hipsCopy.setLocation(hips);
	endCopy.setLocation(end);
	angleCopy = angle;
	angularVelocityCopy = angularVelocity;
  }

  public void restoreAllValues() {
	 hips.setLocation(hipsCopy);
	 end.setLocation(endCopy);
	 angle = angleCopy;
	 angularVelocity = angularVelocityCopy; 
  }
  @Override
  public boolean outOfBounds() {
    return false;
  }
  
}
