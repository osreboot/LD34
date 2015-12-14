package com.osreboot.ld34;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import java.util.ArrayList;
import java.util.Arrays;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.HvlTimer;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.config.HvlConfigUtil;
import com.osreboot.ridhvl.display.collection.HvlDisplayModeDefault;
import com.osreboot.ridhvl.input.HvlInput;
import com.osreboot.ridhvl.painter.painter2d.HvlFontPainter2D;
import com.osreboot.ridhvl.template.HvlTemplateInteg2D;

public class Main extends HvlTemplateInteg2D{

	public static void main(String args[]) {
		new Main();
	}

	public Main(){
		super(120, 1280, 720, "net_forget - by Os_Reboot", "Icon", new HvlDisplayModeDefault());
	}

	public static final int IDX_UI = 0,
			IDX_FONTLARGE = 1,
			IDX_FONTSMALL = 2,
			IDX_EMBLEM = 3,
			IDX_GATE_OPEN = 4,
			IDX_GATE_UP = 5,
			IDX_GATE_DOWN = 6,
			IDX_GATE_INVERT = 7,
			IDX_GATE_CLOSED = 8,
			IDX_PATH_LIT = 9,
			IDX_PATH_UNLIT = 10,
			IDX_REQ_LIT = 11,
			IDX_REQ_UNLIT = 12,
			IDX_INP_LIT = 13,
			IDX_INP_UNLIT = 14,
			IDX_BORDER = 15,
			IDX_SERVER_SMALL = 16,
			IDX_SERVER_MEDIUM = 17,
			IDX_SERVER_LARGE = 18,
			IDX_SERVER_HUGE = 19,
			IDX_GATE_ON = 20,
			IDX_UIMASK = 21;

	public static final float LARGE_SCREEN_YCENTER = 720/64*23,
			LARGE_SCREEN_XSTART = 200,
			LARGE_SCREEN_XEND = 1080;

	public static final Color CLR_LARGE_SCREEN_TEXT = new Color(0.906f, 0.906f, 1f),
			CLR_SHADE = new Color(0f, 0f, 0f, 0.9f);

	public static HvlFontPainter2D fontLarge, fontSmall;

	public static HvlInput mute = new HvlInput(new HvlInput.HvlInputFilter(){
		@Override
		public float getCurrentOutput() {
			return Keyboard.isKeyDown(Keyboard.KEY_M) ? 1 : 0;
		}
	});

	@Override
	public void initialize(){
		getTimer().setMaxDelta(HvlTimer.MD_TENTH);

		HvlConfigUtil.loadStaticConfig(Save.class, "res/Save.txt");
		HvlConfigUtil.saveStaticConfig(Save.class, "res/Save.txt");

		getTextureLoader().loadResource("UI");
		getTextureLoader().loadResource("FontLarge");
		getTextureLoader().loadResource("FontSmall");
		getTextureLoader().loadResource("Emblem");
		getTextureLoader().loadResource("GateOpen");
		getTextureLoader().loadResource("GateUp");
		getTextureLoader().loadResource("GateDown");
		getTextureLoader().loadResource("GateInvert");
		getTextureLoader().loadResource("GateClosed");
		getTextureLoader().loadResource("PathLit");
		getTextureLoader().loadResource("PathUnlit");
		getTextureLoader().loadResource("RequirementLit");
		getTextureLoader().loadResource("RequirementUnlit");
		getTextureLoader().loadResource("InputLit");
		getTextureLoader().loadResource("InputUnlit");
		getTextureLoader().loadResource("Border");
		getTextureLoader().loadResource("ServerSmall");
		getTextureLoader().loadResource("ServerMedium");
		getTextureLoader().loadResource("ServerLarge");
		getTextureLoader().loadResource("ServerHuge");
		getTextureLoader().loadResource("GateOn");
		getTextureLoader().loadResource("UIMask");

		getSoundLoader().loadResource("Ambient1");
		getSoundLoader().loadResource("Ambient2");
		getSoundLoader().loadResource("Click1");
		getSoundLoader().loadResource("Click2");

		fontLarge = new HvlFontPainter2D(getTexture(IDX_FONTLARGE), "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789/\\'\")(][?!.,_@*^-+=|;:><abcdefghijklmnopqrstuvwxyz".toCharArray(), 6f, 11f, 0f, 4f);
		fontSmall = new HvlFontPainter2D(getTexture(IDX_FONTSMALL), "abcdefghijklmnopqrstuvwxyz0123456789/\\'\")(][?!.,_@*^-+=|:;><".toCharArray(), 4f, 7f, 0f, 4f);

		Levels.initialize();
		Menus.initialize();
		Sequencer.initialize();

		writeToTerminal("initialize", 0f, false);
		writeToTerminal("initializing... 1", 3f, false);
		writeToTerminal("initializing... 2", 0f, false);
		writeToTerminal("initializing... 3", 0f, false);
		writeToTerminal("initialized.", 0f, true);
		writeToTerminal("synccontracts", 0f, false);
		writeToTerminal("waiting...", 0f, false);
		writeToTerminal("done " + (Save.completedIntro ? "(no new contract(s))" : "(new contract(s))"), 2f, true);

		mute.setPressedAction(new HvlAction1<HvlInput>(){
			@Override
			public void run(HvlInput i){
				Save.muted = !Save.muted;
			}
		});
	}

	private static float timeSinceSound = 0f;

	public static void playButtonSound(){
		if(!Save.muted) getSound(2).playAsSoundEffect(3, 0.4f, false);
	}
	
	@Override
	public void update(float delta){
		timeSinceSound = HvlMath.stepTowards(timeSinceSound, delta, 0);
		if(timeSinceSound == 0 && !Save.muted){
			getSound(0).playAsSoundEffect(Math.random() > 0.5 ? 1 : 2, 0.1f, false);
			timeSinceSound = 20f + ((float)Math.random() * 20f);
		}
		Sequencer.update(delta);
		hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, Display.getWidth(), Display.getHeight(), getTexture(IDX_UI));
		if(Math.sin(getTimer().getTotalTime()*200f) + (getTimer().getTotalTime()/1f) < 8f) hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/2, Display.getWidth(), Display.getHeight(), getTexture(IDX_UIMASK), new Color(1f, 1f, 1f, 0.6f));
		drawTerminal(delta);
		Menus.update(delta);
	}

	private static ArrayList<String> terminalBuffer = new ArrayList<>();
	private static ArrayList<Float> terminalBufferTimes = new ArrayList<>();

	private static String terminalString = "> ";
	private static float terminalGoalTime = 0;

	public static void writeToTerminal(String arg, float waitTime, boolean newCommand){
		terminalBuffer.add(arg + "\n" + (newCommand ? "> " : ""));
		terminalBufferTimes.add(waitTime);
	}

	public static boolean isTerminalFinished(){
		return terminalBuffer.size() == 0;
	}

	public static void clearTerminal(){
		terminalString = "> ";
		terminalBuffer.clear();
	}

	private static boolean playedSound = false;

	private static void drawTerminal(float delta){
		playedSound = false;
		if(terminalBufferTimes.size() > 0 && terminalBufferTimes.get(0) == 0 &&
				terminalBuffer.size() > 0 && terminalBuffer.get(0).length() == 0){
			terminalBuffer.remove(0);
			terminalBufferTimes.remove(0);
		}
		if(terminalBuffer.size() > 0){
			if(terminalBufferTimes.get(0) > 0) terminalBufferTimes.set(0, HvlMath.stepTowards(terminalBufferTimes.get(0), delta, 0));
			else{
				terminalGoalTime += delta*16;
				while(terminalGoalTime > 1){
					if(!playedSound && !Save.muted){
						playedSound = true;
						if(Math.random() > 0.5f) getSound(3).playAsSoundEffect(3, 1f, false);
					}
					terminalGoalTime -= 1;
					terminalString += terminalBuffer.get(0).charAt(0);
					StringBuilder sb = new StringBuilder(terminalBuffer.get(0));
					sb.deleteCharAt(0);
					terminalBuffer.set(0, sb.toString());
				}
			}
		}else terminalGoalTime = 0;
		String temp = terminalString;
		while(temp.split("\n").length > 5){
			ArrayList<String> tempArray = new ArrayList<>(Arrays.asList(temp.split("\n")));
			tempArray.remove(0);
			temp = "";
			for(String s : tempArray) temp += s + (tempArray.indexOf(s) == tempArray.size() - 1 ? "" : "\n");
		}
		fontSmall.drawWord(temp + (Math.sin(getNewestInstance().getTimer().getTotalTime() * 16) > 0f ? "_" : ""), 536, 476, CLR_LARGE_SCREEN_TEXT);
	}

}
