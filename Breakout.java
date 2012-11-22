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
import java.util.StringTokenizer;

public class Breakout extends GraphicsProgram {

	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 300;
	
	public void run() {
		String test = "1 2 3";
		StringTokenizer tokenizer = new StringTokenizer(test, " ");
		while (tokenizer.hasMoreTokens()) {
			String token = tokenizer.nextToken();
			System.out.println(token);
		}
		Thigh leftThigh = new Thigh(false);
		leftThigh.drawShape(this);
		Thigh rightThigh = new Thigh(true);
		rightThigh.drawShape(this);
	}

}
