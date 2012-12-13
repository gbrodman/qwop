/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;
import acmx.export.javax.swing.JFrame;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class QWOP extends GraphicsProgram implements KeyListener {

	public static final int NSTATES = 700;
	public static final int NACTIONS = 16;

	public static final int APPLICATION_WIDTH = 600;
	public static final int APPLICATION_HEIGHT = 320;

	private static double PI = 3.14159;
	private static double GRAVITY = .12;
	// controls speed of angular acceleration
	private static double ANGLE_ACCEL = .01;

	// Constraints on angles between body parts
	public static final double MAX_KNEE_ANGLE = 7 * PI / 8;
	public static final double MIN_KNEE_ANGLE = 20 * PI / 180;
	public static final double MAX_THIGH_ANGLE = 150 * PI / 180;
	public static final double MIN_THIGH_TORSO_ANGLE = 90 * PI / 180;
	public static final double MAX_THIGH_TORSO_ANGLE = 240 * PI / 180;
	public static final double DEATH_PENALTY = 100.0;

	Set<Integer> qActions;
	Set<Integer> wActions;
	Set<Integer> oActions;
	Set<Integer> pActions;

	double frontKneeAngle;
	double backKneeAngle;
	double thighsAngle;
	double score;
	double scrollVelocity;
	double distToCenter;

	int timeStep = 0;

	// coordinates of center of gravity
	double centerX = 300;
	double centerY = 0;

	private Thigh backThigh;
	private Thigh frontThigh;
	private Calf backCalf;
	private Calf frontCalf;
	private Arm backArm;
	private Arm frontArm;
	private Torso torso;
	private Head head;
	private GPoint hips;
	private GPoint shoulders;
	private GPoint neck;
	private ArrayList<BodyPart> bodyParts;
	private GOval center;
	private GLine floor;
	private GLabel scoreLabel;

	double distance = 0.0;

	private enum Case {
		FRONT, BACK, BOTH, NEITHER
	};

	private Case last;
	private int countSame = 0;

	private boolean dead;

	public void init() {

		qActions = new HashSet<Integer>(Arrays.asList(1, 5, 8, 9, 11, 12, 13,
				15));
		wActions = new HashSet<Integer>(Arrays.asList(2, 5, 6, 10, 11, 12, 14,
				15));
		oActions = new HashSet<Integer>(Arrays.asList(3, 6, 7, 8, 11, 13, 14,
				15));
		pActions = new HashSet<Integer>(Arrays.asList(4, 8, 9, 10, 12, 13, 14,
				15));

		score = 0;
		//scoreLabel = new GLabel("Distance: 0", 20, 20);
		//add(scoreLabel);
		//addKeyListeners();
		dead = false;
		double xloc = 300;
		double yloc = 190;
		hips = new GPoint(xloc, yloc);
		backThigh = new Thigh(this, true, hips);
		frontThigh = new Thigh(this, false, hips);
		backCalf = new Calf(this, true, backThigh);
		frontCalf = new Calf(this, false, frontThigh);
		torso = new Torso(this, hips);
		shoulders = torso.getShoulders();
		neck = torso.getStart();
		backArm = new Arm(this, true, shoulders);
		frontArm = new Arm(this, false, shoulders);
		head = new Head(this, neck);

		bodyParts = new ArrayList<BodyPart>();
		bodyParts.add(backThigh);
		bodyParts.add(frontThigh);
		bodyParts.add(backCalf);
		bodyParts.add(frontCalf);
		bodyParts.add(backArm);
		bodyParts.add(frontArm);
		bodyParts.add(torso);
		bodyParts.add(head);

		center = new GOval(5, 5);
		floor = new GLine(0, 300, 600, 300);
		add(center);
		add(floor);

		//while(!dead) {
		//fall();
		//}

	}

	public double getReward() {
		if (dead) return -DEATH_PENALTY + score / timeStep;
		else return Math.sqrt(score);
	}

	public boolean isDead() {
		return dead;
	}
	
	public void revert() {
	
	  for (BodyPart b : bodyParts) {
		b.restoreAllValues();
	  }
	}

	/*
	 * 0 = none 1 = Q 2 = w 3 = o 4 = p 5 = qw 6 = wo 7 = op 8 = qo 9 = qp 10 =
	 * wp 11 = qwo 12 = qwp 13 = qop 14 = wop 15 = qwop
	 */

	public void performAction(int action) {
		if (qActions.contains(action)) {
			moveLeftThigh();
		}
		if (wActions.contains(action)) {
			moveRightThigh();
		}
		if (oActions.contains(action)) {
			moveLeftCalf();
		}
		if (pActions.contains(action)) {
			moveRightCalf();
		}
	}

	public double[] getState() {
		double[] state = {backKneeAngle, frontKneeAngle, thighsAngle, distToCenter, last.ordinal()};
		return state;
	}

	private void moveLeftThigh() {
		if (thighsAngle < MAX_THIGH_ANGLE) {
			backThigh.increaseAngVelocity(-3*ANGLE_ACCEL);
			frontThigh.increaseAngVelocity(1.5*ANGLE_ACCEL);
		}
	}
	private void moveRightThigh() {
		if (thighsAngle > -MAX_THIGH_ANGLE) {
			frontThigh.increaseAngVelocity(-3*ANGLE_ACCEL);
			backThigh.increaseAngVelocity(1.5*ANGLE_ACCEL); 
		}
	}
	private void moveLeftCalf() {
		if (backKneeAngle > MIN_KNEE_ANGLE) {
			backCalf.increaseAngVelocity(-10*ANGLE_ACCEL);
		}
		if (frontKneeAngle < MAX_KNEE_ANGLE) 
			frontCalf.increaseAngVelocity(2*ANGLE_ACCEL);
	}
	private void moveRightCalf() {
		if (frontKneeAngle > MIN_KNEE_ANGLE) {
			frontCalf.increaseAngVelocity(-10*ANGLE_ACCEL);
		}
		if (backKneeAngle < MAX_KNEE_ANGLE) 
			backCalf.increaseAngVelocity(2*ANGLE_ACCEL);
	}
	public void fall() {
		timeStep++;
		// coordinates of center of gravity
		double newCenterX = 0;
		double newCenterY = 0;
		double mass = 0;

		for (BodyPart b : bodyParts) {
			if (b.outOfBounds()){
				dead = true;
			}
			b.copyAllValues();
		}

		for (BodyPart b : bodyParts) {
			newCenterX += b.getMass()*b.getCenter().getX(); 
			newCenterY += b.getMass()*b.getCenter().getY(); 
			mass += b.getMass();
		}

		newCenterX /= mass;
		newCenterY /= mass;

		score += newCenterX - centerX;

		centerX = newCenterX;
		centerY = newCenterY;

		if (Math.abs(centerX - 300) > 75) 
			scrollVelocity = .1*Math.signum(300 - centerX)*(Math.abs(300 - centerX) - 75);
		else scrollVelocity = 0;
		hips.setLocation(hips.getX() + scrollVelocity, hips.getY());
		backCalf.scroll(scrollVelocity);
		frontCalf.scroll(scrollVelocity);

		score -= scrollVelocity;

		center.setLocation(new GPoint(centerX, centerY));

		thighsAngle = frontThigh.getAngle() - backThigh.getAngle();
		backKneeAngle = PI - (backThigh.getAngle() - backCalf.getAngle());
		frontKneeAngle = PI - (frontThigh.getAngle() - frontCalf.getAngle());
		double backThighTorsoAngle = torso.getAngle() - backThigh.getAngle();
		double frontThighTorsoAngle = torso.getAngle() - frontThigh.getAngle();


		if (backThighTorsoAngle > MAX_THIGH_TORSO_ANGLE) {
			torso.increaseAngVelocity(-.5*ANGLE_ACCEL);
			backThigh.increaseAngVelocity(ANGLE_ACCEL);
		}

		if (frontThighTorsoAngle > MAX_THIGH_TORSO_ANGLE) {
			torso.increaseAngVelocity(-.5*ANGLE_ACCEL);
			frontThigh.increaseAngVelocity(ANGLE_ACCEL);
		}

		if (frontThighTorsoAngle < MIN_THIGH_TORSO_ANGLE) {
			torso.increaseAngVelocity(ANGLE_ACCEL);
			frontThigh.increaseAngVelocity(-2*ANGLE_ACCEL);
		}

		if (backThighTorsoAngle < MIN_THIGH_TORSO_ANGLE) {
			torso.increaseAngVelocity(ANGLE_ACCEL);
			backThigh.increaseAngVelocity(-2*ANGLE_ACCEL);
		}

		if (thighsAngle > MAX_THIGH_ANGLE) {
			frontThigh.increaseAngVelocity(-.5*ANGLE_ACCEL);
			backThigh.increaseAngVelocity(.5*ANGLE_ACCEL);
		}

		if (thighsAngle < -MAX_THIGH_ANGLE) {
			frontThigh.increaseAngVelocity(.5*ANGLE_ACCEL);
			backThigh.increaseAngVelocity(-.5*ANGLE_ACCEL);
		}

		if (frontKneeAngle < MIN_KNEE_ANGLE) {
			frontThigh.increaseAngVelocity(-.5*ANGLE_ACCEL);
			frontCalf.increaseAngVelocity(1.5*ANGLE_ACCEL);
		}

		if (frontKneeAngle > MAX_KNEE_ANGLE) {
			//frontThigh.increaseAngVelocity(.02);
			frontCalf.increaseAngVelocity(-1.5*ANGLE_ACCEL);
		}

		if (backKneeAngle < MIN_KNEE_ANGLE) {
			backThigh.increaseAngVelocity(-.2*ANGLE_ACCEL);
			backCalf.increaseAngVelocity(1.5*ANGLE_ACCEL);
		}

		if (backKneeAngle > MAX_KNEE_ANGLE) {
			//backThigh.increaseAngVelocity(.02);
			backCalf.increaseAngVelocity(-1.5*ANGLE_ACCEL);
		}

		double angularAccel = 0;

		if(frontCalf.getEnd().getY() >= floor.getY() ||
				backCalf.getEnd().getY() >= floor.getY()) {
			backThigh.setVertVelocity(0);
			double amount = 0;
			if (frontCalf.getEnd().getY() > floor.getY()) {
				amount = frontCalf.getEnd().getY() - floor.getY();
			}
			if (backCalf.getEnd().getY() > floor.getY()) {
				double diff = backCalf.getEnd().getY() - floor.getY();
				amount = (amount > diff) ? amount : diff;
			} 
			hips.setLocation(hips.getX(), hips.getY() - amount);
			backThigh.update(false);
			frontThigh.update(false);

			//both feet on ground
			if(frontCalf.getEnd().getY() >= floor.getY() && 
					backCalf.getEnd().getY() >= floor.getY()) {
				// X coord of midpoint between feet
				double mid = .5*(backCalf.getEnd().getX() + frontCalf.getEnd().getX());
				angularAccel = -.005*ANGLE_ACCEL*(center.getX() - mid);
				frontThigh.increaseAngVelocity(angularAccel);
				backThigh.increaseAngVelocity(angularAccel);
				backCalf.update(false);
				backThigh.update(true);
				frontThigh.update(false);
				frontCalf.update(false);
				last = Case.BOTH;
			}
			//front foot on ground
			else if (frontCalf.getEnd().getY() >= floor.getY()) {
				angularAccel = -.02*ANGLE_ACCEL*(center.getX() - frontCalf.getEnd().getX());
				if (backCalf.getEnd().getY() + 5 >= floor.getY()) angularAccel *= .2;
				frontCalf.update(true);
				frontThigh.increaseAngVelocity(angularAccel);
				frontThigh.update(true);
				backThigh.increaseAngVelocity(angularAccel);
				backThigh.update(false);
				backCalf.increaseAngVelocity(.01*(backThigh.getAngle() - 1.3*backCalf.getAngle()));
				backCalf.update(false);
				last = Case.FRONT;
			}
			//back foot on ground
			else {
				angularAccel = -.02*ANGLE_ACCEL*(center.getX() - backCalf.getEnd().getX());
				backCalf.update(true);
				backThigh.increaseAngVelocity(angularAccel);
				backThigh.update(true);
				frontThigh.increaseAngVelocity(angularAccel);
				frontThigh.update(false);
				frontCalf.increaseAngVelocity(.01*(frontThigh.getAngle() - 1.3*frontCalf.getAngle()));
				frontCalf.update(false);
				last = Case.BACK;
			}
		}

		else {
			// fall vertically
			backThigh.increaseVertVelocity(GRAVITY);
			//frontThigh.increaseVertVelocity(GRAVITY);
			double mid = .5*(backCalf.getEnd().getX() + frontCalf.getEnd().getX());
			angularAccel = -.005*ANGLE_ACCEL*(center.getX() - mid);
			backThigh.update(true);
			backCalf.update(false);
			frontThigh.update(false);
			frontCalf.update(false);
			last = Case.NEITHER;
		}
		torso.increaseAngVelocity(.7*angularAccel);
		torso.update();
		backArm.update();
		frontArm.update();
		head.update();
		
	  }
	}
	//	  public int getState() {
	//		  if (score < -5000) return NSTATES-1;
	//	    int frontKnee = (int)(frontKneeAngle/PI*4);
	//	    int backKnee = (int)Math.pow(2, 2)*(int)(4*frontKneeAngle/PI);
	//	    int thighs = (int)Math.pow(2, 4)*(int)(4*(thighsAngle + MAX_THIGH_ANGLE)/(2*PI));
	//	    int feet = 0;
	//	    int dist = (int)Math.pow(2, 6)*(int)((distToCenter + 10) / 20 * 4);
	//	    switch (last) {
	//	      case BACK: feet = 0; break;
	//	      case FRONT: feet = 1; break;
	//	      case BOTH: feet = 2; break;
	//	      case NEITHER: feet = 3; break;
	//	    }
	//	    feet *= (int)Math.pow(2, 8);
	//	    return (frontKnee + backKnee + thighs + feet + dist) % NSTATES;
	//	  }

