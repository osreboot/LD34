package com.osreboot.ld34;

public class Save {

	public static boolean completedIntro, muted;
	public static boolean[] completedLevels;
	
	static{
		completedIntro = false;
		muted = false;
		completedLevels = new boolean[]{false, false, false, false, false, false, false, false, false};
	}
	
}
