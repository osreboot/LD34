package com.osreboot.ld34;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import com.osreboot.ridhvl.HvlCoord;
import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.action.HvlAction2r;
import com.osreboot.ridhvl.painter.HvlCursor;

public class Level {

	public static final float SLIDER_HITBOX_WIDTH = 40f,
			SLIDER_GATE_HEIGHT = 32f,
			SLIDER_WIDTH = 32f;

	public static HvlCoord getSliderPosition(Level l, Slider s){
		return new HvlCoord(HvlMath.lerp(Main.LARGE_SCREEN_XSTART + 100, Main.LARGE_SCREEN_XEND - 100, (float)l.sliders.indexOf(s)/((float)l.sliders.size() - 1)), Main.LARGE_SCREEN_YCENTER);
	}

	private int paths, texture, worth, index, requirement;
	private ArrayList<Slider> sliders;
	private Boolean[] input, outputReq;
	private boolean isComplete = false;
	private HvlAction0 startAction;
	private HvlAction2<Float, Level> updateAction;

	public Level(Boolean[] inputArg, Boolean[] outputReqArg, int textureArg, int worthArg, int indexArg, int requirementArg, HvlAction0 startActionArg, HvlAction2<Float, Level> updateActionArg){
		paths = inputArg.length;
		input = inputArg;
		outputReq = outputReqArg;
		texture = textureArg;
		worth = worthArg;
		index = indexArg;
		requirement = requirementArg;
		startAction = startActionArg;
		updateAction = updateActionArg;
		Levels.levels.add(this);
	}

	public boolean isComplete(){
		return isComplete;
	}

	public int getTexture(){
		return texture;
	}

	public int getWorth(){
		return worth;
	}
	
	public int getRequirement(){
		return requirement;
	}

	public HvlAction0 getStartAction(){
		return startAction;
	}

	public int getIndex(){
		return index;
	}
	
	public HvlAction2<Float, Level> getUpdateAction(){
		return updateAction;
	}

	public void update(float delta){
		for(Slider s : sliders) s.update(delta);
		Boolean[] current = input;
		for(int i = 0; i < paths; i++){
			hvlDrawQuad(Main.LARGE_SCREEN_XSTART + (SLIDER_WIDTH), Main.LARGE_SCREEN_YCENTER - (paths*SLIDER_GATE_HEIGHT/2) + (i*SLIDER_GATE_HEIGHT),
					64 - (SLIDER_WIDTH/2), SLIDER_GATE_HEIGHT, Main.getTexture(current[i] ? Main.IDX_PATH_LIT : Main.IDX_PATH_UNLIT));
			hvlDrawQuad(Main.LARGE_SCREEN_XEND - (SLIDER_WIDTH*2), Main.LARGE_SCREEN_YCENTER - (paths*SLIDER_GATE_HEIGHT/2) + (i*SLIDER_GATE_HEIGHT),
					SLIDER_GATE_HEIGHT, SLIDER_GATE_HEIGHT, Main.getTexture(outputReq[i] ? Main.IDX_REQ_LIT : Main.IDX_REQ_UNLIT));
			hvlDrawQuad(Main.LARGE_SCREEN_XSTART + (SLIDER_WIDTH/2), Main.LARGE_SCREEN_YCENTER - (paths*SLIDER_GATE_HEIGHT/2) + (i*SLIDER_GATE_HEIGHT),
					SLIDER_GATE_HEIGHT, SLIDER_GATE_HEIGHT, Main.getTexture(current[i] ? Main.IDX_INP_LIT : Main.IDX_INP_UNLIT));
		}
		for(int i = 0; i < sliders.size(); i++){
			current = sliders.get(i).transform(current);
			for(int i2 = 0; i2 < paths; i2++){
				hvlDrawQuad(getSliderPosition(this, sliders.get(i)).x + (SLIDER_WIDTH/2) + 4, Main.LARGE_SCREEN_YCENTER - (paths*SLIDER_GATE_HEIGHT/2) + (i2*SLIDER_GATE_HEIGHT),
						(i == sliders.size() - 1 ? 56 : (getSliderPosition(this, sliders.get(i + 1)).x) - getSliderPosition(this, sliders.get(i)).x) - SLIDER_WIDTH - 8, SLIDER_GATE_HEIGHT, Main.getTexture(current[i2] ? Main.IDX_PATH_LIT : Main.IDX_PATH_UNLIT));
			}
		}
		isComplete = true;
		for(int i = 0; i < current.length; i++) if(current[i] != outputReq[i]) isComplete = false;
	}

	public void setSliders(ArrayList<Slider> slidersArg){
		sliders = slidersArg;
	}
	
	public ArrayList<Slider> getSliders(){
		return sliders;
	}

	public static class Slider{

		private Level level;
		private int offset, previousOffset;
		private Gate[] gates;
		private float offsetFine, mouseOffset;
		private boolean isDragging = false, thisDragging = false;

		public Slider(int offsetArg, Gate[] gateArgs, Level levelArg){
			offset = offsetArg;
			previousOffset = offsetArg;
			offsetFine = offsetArg;
			gates = gateArgs;
			level = levelArg;
		}

		public Boolean[] transform(Boolean[] input){
			Boolean[] output = new Boolean[input.length];
			for(int i = 0; i < input.length; i++){
				output[i] = gates[i - offset].getOutput()
						.run((Integer)(i), input);
			}
			return output;
		}
		
		public float getOffsetFine(){
			return offsetFine;
		}

		public void setOffset(float offsetFineArg){
			offsetFine = HvlMath.limit(offsetFineArg, -(gates.length - level.paths) * SLIDER_GATE_HEIGHT, 0 * SLIDER_GATE_HEIGHT);
			offset = Math.round((offsetFine)/SLIDER_GATE_HEIGHT);
			if(previousOffset != offset){
				if(!Save.muted) Main.getSound(2).playAsSoundEffect(4.5f, 0.2f, false);
				previousOffset = offset;
			}
		}
		
		public int getGateLength(){
			return gates.length;
		}

		public void update(float delta){
			if(Mouse.isButtonDown(0) && !Game.isLocked()){
				if(!isDragging){
					isDragging = true;
					if(Math.abs(getSliderPosition(level, this).x - HvlCursor.getCursorX()) < SLIDER_HITBOX_WIDTH/2 && 
							HvlCursor.getCursorY() > Main.LARGE_SCREEN_YCENTER - 192 && HvlCursor.getCursorY() < Main.LARGE_SCREEN_YCENTER + 192){
						thisDragging = true;
						mouseOffset = HvlCursor.getCursorY() - offsetFine;
					}
				}
				if(thisDragging){
					offsetFine = HvlMath.limit(HvlCursor.getCursorY() - mouseOffset, -(gates.length - level.paths) * SLIDER_GATE_HEIGHT, 0 * SLIDER_GATE_HEIGHT);
					offset = Math.round((offsetFine)/SLIDER_GATE_HEIGHT);
					if(previousOffset != offset){
						if(!Save.muted) Main.getSound(2).playAsSoundEffect(4.5f, 0.2f, false);
						previousOffset = offset;
					}
				}
			}else{
				isDragging = false;
				thisDragging = false;
				mouseOffset = 0;
				offsetFine = HvlMath.stepTowards(offsetFine, delta*200, offset * SLIDER_GATE_HEIGHT);
			}
			for(int i = 0; i < gates.length; i++){
				hvlDrawQuadc(getSliderPosition(level, this).x, Main.LARGE_SCREEN_YCENTER + offsetFine + (level.paths % 2 == 1 ? 0 : (SLIDER_GATE_HEIGHT/2)) + ((i + (level.paths > 4 ? (3 - level.paths) : (2 - level.paths)))*SLIDER_GATE_HEIGHT), SLIDER_WIDTH, SLIDER_GATE_HEIGHT, Main.getTexture(gates[i].getTexture()));
			}
		}
		
	}

	public static class Gate{

		private HvlAction2r<Boolean, Integer, Boolean[]> output;
		private int texture;

		public Gate(HvlAction2r<Boolean, Integer, Boolean[]> outputArg, int textureArg){
			output = outputArg;
			texture = textureArg;
		}

		public HvlAction2r<Boolean, Integer, Boolean[]> getOutput(){
			return output;
		}

		public int getTexture(){
			return texture;
		}

	}

}
