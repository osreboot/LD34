package com.osreboot.ld34;

import com.osreboot.ridhvl.menu.HvlMenu;

public class Sequencer {

	private static boolean initialSequence = false, introDialogueSequenceStart = false, introDialogueSequence = false;
	private static float introDialogueSequenceTimer = 0f;
	
	public static void initialize(){
		introDialogueSequence = Save.completedIntro;
	}

	public static void update(float delta){
		if(Main.getNewestInstance().getTimer().getTotalTime() > 11.5f && !initialSequence){
			initialSequence = true;
			if(Save.completedIntro) HvlMenu.setCurrent(Menus.main);
			else HvlMenu.setCurrent(Menus.greetings);
		}
		if(introDialogueSequenceStart){
			introDialogueSequenceTimer += delta;
			if(introDialogueSequenceTimer > 46f && !introDialogueSequence){
				introDialogueSequence = true;
				HvlMenu.setCurrent(Menus.main);
				Save.completedIntro = true;
			}
		}
	}
	
	public static void doDialogueIntro(){
		introDialogueSequenceStart = true;
		Main.writeToTerminal("commsread", 0, false);
		Main.writeToTerminal("hello. long time since we last \ncommunicated.", 1f, false);
		Main.writeToTerminal("don't know if you still do your \n\"job\" or not, but i have come \nacross an opportunity.", 3f, false);
		Main.writeToTerminal("i recently pinned down some \nmachines with software \nvulnerabilities. using a decent \ncache of memory we could snap \ntheir protective algorithms in \nhalf.", 3f, false);
		Main.writeToTerminal("alone these servers are \nimpenetrable. however, by \nharnessing the power of infected \nmachines you can easily overcome \nthem one after the other.", 3f, false);
		Main.writeToTerminal("yeah, i'm talking about a \nsnowball effect. let me know if \nyou want to work.", 3f, false);
		Main.writeToTerminal("[comms terminated 8:01:12:12]", 0f, true);
	}

}