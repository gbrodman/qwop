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

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 300;
	
	public void run() {
		Thigh leftThigh = new Thigh();
		leftThigh.drawShape(this);
	}

}
