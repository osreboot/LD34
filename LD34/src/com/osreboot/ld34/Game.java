package com.osreboot.ld34;

import org.lwjgl.input.Keyboard;

import com.osreboot.ridhvl.menu.HvlMenu;

public class Game {

	private static Level currentLevel;
	private static float currentTime = 0, completeTime = 0;
	private static boolean locked = false;
	
	private static boolean completeButtonPressed = false;

	protected static Level getCurrentLevel(){
		return currentLevel;
	}

	protected static void setCurrentLevel(int indexArg){
		currentTime = 0;
		completeTime = 0;
		locked = false;
		completeButtonPressed = false;
		Levels.levels.clear();
		Levels.initialize();
		currentLevel = Levels.getLevel(indexArg);
		Main.clearTerminal();
		Menus.completeLevel.setVisible(false);
		Menus.completeLevel.setEnabled(false);
		Main.writeToTerminal("connected to server.", 0f, true);
		currentLevel.getStartAction().run();
	}
	
	public static void update(float delta){
		if(Math.sin(currentTime*100f) + (currentTime/1f) > 0f){
			currentLevel.update(delta);
			currentLevel.getUpdateAction().run(delta, currentLevel);
		}
		currentTime += delta;
		if(currentLevel.isComplete() && !completeButtonPressed) completeTime += delta; else completeTime = 0;
		if(currentLevel.isComplete() && !completeButtonPressed && completeTime > 1f){
			Menus.completeLevel.setVisible(true);
			Menus.completeLevel.setEnabled(true);
		}else{
			Menus.completeLevel.setVisible(false);
			Menus.completeLevel.setEnabled(false);
		}
		if(currentLevel.isComplete() && Main.isTerminalFinished() && completeButtonPressed){
			Save.completedLevels[currentLevel.getIndex()] = true;
			HvlMenu.setCurrent(Menus.main);
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) && !completeButtonPressed){
			HvlMenu.setCurrent(Menus.main);
			Main.clearTerminal();
		}
	}
	
	public static boolean isLocked(){
		return locked;
	}

	public static void setLocked(boolean lockedArg){
		locked = lockedArg;
	}

	public static boolean isCompleteButtonPressed(){
		return completeButtonPressed;
	}

	public static void setCompleteButtonPressed(boolean completeButtonPressedArg){
		completeButtonPressed = completeButtonPressedArg;
	}
	
}
