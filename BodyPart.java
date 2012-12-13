import acm.graphics.GPoint;

public abstract class BodyPart {
  protected int mass;
  protected double angle; // angle away from vertical axis
  protected double angularVelocity;
  protected GPoint center;
  protected static final double decayRate = .92;
  protected static final double maxAngVelocity = .05;
  
  public double getMass() {
    return mass;
  }
  public abstract GPoint getCenter();
  
  public abstract boolean outOfBounds();
  
  public void increaseAngVelocity(double amount) {
    if (Math.abs(angularVelocity) < maxAngVelocity)
      angularVelocity += amount;  
  }

  public void setAngVelocity(double amount) {
    angularVelocity = amount;
  }
  
  public double getAngle(){
    return angle;
  }

  
}
