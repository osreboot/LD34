package com.osreboot.ld34;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.input.Keyboard;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.input.collection.HvlCPG_Gamepad;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.HvlMenuInteractor;

public class Game {

	private static Level currentLevel;
	private static float currentTime = 0, completeTime = 0;
	private static boolean locked = false;
	private static Level.Slider selected = null;
	private static HvlInput sliderSwitchJoy;
	
	private static boolean completeButtonPressed = false;

	protected static Level getCurrentLevel(){
		return currentLevel;
	}
	
	public static void initialize(){
		HvlMenuInteractor.addEnableEvent(new HvlAction0(){
			@Override
			public void run(){
				if(currentLevel != null) selected = currentLevel.getSliders().get(0);
			}
		});
		HvlMenuInteractor.addDisableEvent(new HvlAction0(){
			@Override
			public void run(){
				selected = null;
			}
		});
		sliderSwitchJoy = new HvlInput(new HvlInput.InputFilter(){
			@Override
			public float getCurrentOutput(){
				return Main.profile.getValue(HvlCPG_Gamepad.JOY1X) > 0.8f || Main.profile.getValue(HvlCPG_Gamepad.JOY1X) < -0.8f ? 1 : 0;
			}
		});
		sliderSwitchJoy.setPressedAction(new HvlAction1<HvlInput>(){
			@Override
			public void run(HvlInput aArg){
				if(HvlMenuInteractor.isEnabled() && HvlMenu.getCurrent() == Menus.game && !isLocked()) selected = getCurrentLevel().getSliders().get((int)HvlMath.limit(getCurrentLevel().getSliders().indexOf(selected) + Math.round(Main.profile.getValue(HvlCPG_Gamepad.JOY1X)), 0, getCurrentLevel().getSliders().size() - 1));
			}
		});
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
		if(HvlMenuInteractor.isEnabled()) selected = currentLevel.getSliders().get(0);
	}
	
	public static void update(float delta){
		if(selected != null && !isLocked()){
			if(HvlMenuInteractor.isEnabled()){
				selected.setOffset(selected.getOffsetFine() + (-Main.profile.getValue(HvlCPG_Gamepad.JOY2Y) * delta * 500f));
				hvlDrawQuadc(Level.getSliderPosition(currentLevel, selected).x, Main.LARGE_SCREEN_YCENTER + selected.getOffsetFine() + ((selected.getGateLength() - 1) * Level.SLIDER_GATE_HEIGHT) + 4, 16, 16, Main.getTexture(Main.IDX_POINT));
			}
		}
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
		if((Keyboard.isKeyDown(Keyboard.KEY_ESCAPE) || Main.profile.getValue(HvlCPG_Gamepad.BUTTON_START) == 1) && !completeButtonPressed){
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
