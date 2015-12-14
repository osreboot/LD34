package com.osreboot.ld34;

import static com.osreboot.ridhvl.painter.painter2d.HvlPainter2D.*;

import org.lwjgl.opengl.Display;
import org.newdawn.slick.Color;

import com.osreboot.ridhvl.HvlMath;
import com.osreboot.ridhvl.action.HvlAction1;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.config.HvlConfigUtil;
import com.osreboot.ridhvl.menu.HvlComponentDefault;
import com.osreboot.ridhvl.menu.HvlMenu;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox;
import com.osreboot.ridhvl.menu.component.HvlArrangerBox.ArrangementStyle;
import com.osreboot.ridhvl.menu.component.HvlButton;
import com.osreboot.ridhvl.menu.component.HvlComponentDrawable;
import com.osreboot.ridhvl.menu.component.HvlLabel;
import com.osreboot.ridhvl.menu.component.HvlSpacer;
import com.osreboot.ridhvl.menu.component.collection.HvlLabeledButton;
import com.osreboot.ridhvl.painter.painter2d.HvlTiledRect;

public class Menus {

	public static HvlMenu intro, greetings, main, game;
	public static HvlLabeledButton completeLevel;
	public static HvlTiledRect buttonRect;
	private static int previewTexture = -1; 

	public static void initialize(){
		HvlComponentDefault.setDefault(new HvlLabel(Main.fontLarge, "textHEREDUMMY", Main.CLR_LARGE_SCREEN_TEXT));

		HvlArrangerBox defaultArrangerBox = new HvlArrangerBox.Builder().setStyle(ArrangementStyle.VERTICAL).setX(640).setY(Main.LARGE_SCREEN_YCENTER).build();
		defaultArrangerBox.setBorderU(16f);
		defaultArrangerBox.setBorderD(16f);
		defaultArrangerBox.setxAlign(0.5f);
		defaultArrangerBox.setyAlign(0.5f);
		HvlComponentDefault.setDefault(defaultArrangerBox);

		buttonRect = new HvlTiledRect(Main.getTexture(Main.IDX_BORDER), 0.125f, 0.875f, 0.125f, 0.875f, 0, 0, 32, 32, 4, 4);

		HvlComponentDefault.setDefault(new HvlLabeledButton(0, 0, 0, Main.fontLarge.getFontHeight(), new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				buttonRect.setTotalWidth(widthArg + 8);
				buttonRect.setTotalHeight(heightArg + 8);
				buttonRect.setX(xArg - 8);
				buttonRect.setY(yArg - 4);
				buttonRect.draw();
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
				buttonRect.setTotalWidth(widthArg + 32);
				buttonRect.setTotalHeight(heightArg + 8);
				buttonRect.setX(xArg - 20);
				buttonRect.setY(yArg - 4);
				buttonRect.draw();
			}
		}, new HvlComponentDrawable(){
			@Override
			public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){

			}
		}, Main.fontLarge, "textHEREDUMMY", Main.CLR_LARGE_SCREEN_TEXT));

		intro = new HvlMenu();
		greetings = new HvlMenu();
		greetings.add(new HvlArrangerBox.Builder().build());
		greetings.getFirstArrangerBox().add(new HvlLabel.Builder().setText("NEW CONTRACT").build());
		greetings.getFirstArrangerBox().add(new HvlLabeledButton.Builder().setText("Print").setWidth(Main.fontLarge.getLineWidth("Print")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton b){
				Main.playButtonSound();
				b.setEnabled(false);
				b.setVisible(false);
				Sequencer.doDialogueIntro();
			}
		}).build());
		main = new HvlMenu();
		main.add(new HvlArrangerBox.Builder().build());
		main.getFirstArrangerBox().add(new HvlLabel.Builder().setText("Target Select").build());
		main.getFirstArrangerBox().add(new HvlSpacer(0, Display.getHeight()/3));
		for(int i = 0; i < Levels.levels.size(); i++){
			final int i2 = i;
			main.add(new HvlLabeledButton.Builder().setText("s" + i).setX(Main.LARGE_SCREEN_XSTART + 64 + (i%3*100)).setY(164 + (i/3*100)).setWidth(Main.fontLarge.getLineWidth("s" + i)).setClickedCommand(new HvlAction1<HvlButton>(){
				@Override
				public void run(HvlButton b){
					Main.playButtonSound();
					if(Levels.calculateRawMemory() >= Levels.levels.get(i2).getRequirement()){
						Game.setCurrentLevel(i2);
						HvlMenu.setCurrent(game);
					}else if(Main.isTerminalFinished()){
						Main.writeToTerminal("", 0f, false);
						Main.writeToTerminal("error: more memory required", 0f, true);
					}
				}
			}).setHoverDrawable(new HvlComponentDrawable(){
				@Override
				public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
					if(Levels.calculateRawMemory() < Levels.levels.get(i2).getRequirement()) buttonRect.setColor(new Color(0.2f, 0.2f, 0.4f));
					else if(Save.completedLevels[i2]) buttonRect.setColor(new Color(0.2f, 0.2f, 0.4f, 0.2f));
					buttonRect.setTotalWidth(widthArg + 32);
					buttonRect.setTotalHeight(heightArg + 8);
					buttonRect.setX(xArg - 20);
					buttonRect.setY(yArg - 4);
					buttonRect.draw();
					previewTexture = Levels.levels.get(i2).getTexture();
					buttonRect.setColor(new Color(1f, 1f, 1f, 1f));
				}
			}).setOffDrawable(new HvlComponentDrawable(){
				@Override
				public void draw(float deltaArg, float xArg, float yArg, float widthArg, float heightArg){
					if(Levels.calculateRawMemory() < Levels.levels.get(i2).getRequirement()) buttonRect.setColor(new Color(0.2f, 0.2f, 0.4f));
					else if(Save.completedLevels[i2]) buttonRect.setColor(new Color(0.2f, 0.2f, 0.4f, 0.2f));
					buttonRect.setTotalWidth(widthArg + 8);
					buttonRect.setTotalHeight(heightArg + 8);
					buttonRect.setX(xArg - 8);
					buttonRect.setY(yArg - 4);
					buttonRect.draw();
					buttonRect.setColor(new Color(1f, 1f, 1f, 1f));
				}
			}).build());
		}
		game = new HvlMenu(){
			@Override
			public void draw(float delta){
				Game.update(delta);
				super.draw(delta);
			}
		};
		completeLevel = new HvlLabeledButton.Builder().setText("Bypass").setWidth(Main.fontLarge.getLineWidth("Bypass")).setClickedCommand(new HvlAction1<HvlButton>(){
			@Override
			public void run(HvlButton b){
				Main.playButtonSound();
				Game.setCompleteButtonPressed(true);
				Game.setLocked(true);
				b.setEnabled(false);
				b.setVisible(false);
				String identifier = "(" + HvlMath.randomInt(96) + ":" + HvlMath.randomInt(96) + ":" + HvlMath.randomInt(96) + ":" + HvlMath.randomInt(96) + ")";
				Main.writeToTerminal("\"net_bypass\" -" + identifier, 0f, false);
				Main.writeToTerminal("waiting for " + identifier + "...", 0f, false);
				Main.writeToTerminal("done.", 1f + HvlMath.randomFloatBetween(0.1f, 1.5f), true);
				Main.writeToTerminal("\"net_infect\" -" + identifier, 0f, false);
				Main.writeToTerminal("infecting " + identifier + "...", 0f, false);
				Main.writeToTerminal("done.", HvlMath.randomFloatBetween(0.1f, 1.5f), true);
				Main.writeToTerminal("\"net_forget\" -" + identifier, 0f, false);
				Main.writeToTerminal("done.", 0f, false);
				Main.writeToTerminal("botnet acquired +" + Levels.breakMemory(Game.getCurrentLevel().getWorth()), 0f, true);
			}
		}).build();
		game.add(new HvlArrangerBox.Builder().build());
		game.getFirstArrangerBox().add(completeLevel);
		game.getFirstArrangerBox().add(new HvlSpacer(0, Display.getHeight()/3));

		HvlMenu.setCurrent(intro);
		
		HvlMenu.setMenuChanged(new HvlAction2<HvlMenu, HvlMenu>(){
			@Override
			public void run(HvlMenu from, HvlMenu to){
				HvlConfigUtil.saveStaticConfig(Save.class, "res/Save.txt");
				if(to == main){
					boolean completed = true;
					for(int i = 0; i < Levels.levels.size(); i++) if(!Save.completedLevels[i]) completed = false;
					if(completed) Main.writeToTerminal("good work! you completed the \nld version of net_forget \nby os_reboot. follow him on \ntwitter @os_reboot for updates \non newer versions!", 0f, true);
				}
			}
		});
	}

	public static void update(float delta){
		if(HvlMenu.getCurrent() == greetings){
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/64*23, 280, 280, Main.getTexture(Main.IDX_EMBLEM), new Color(1f, 1f, 1f, 0.6f));
		}
		if(HvlMenu.getCurrent() == main){
			hvlDrawQuadc(Display.getWidth()/2, Display.getHeight()/64*23, 280, 280, Main.getTexture(Main.IDX_EMBLEM), new Color(1f, 1f, 1f, 0.05f));
			if(previewTexture != -1) hvlDrawQuadc(Display.getWidth()/3*2, Display.getHeight()/64*24, 256, 256, Main.getTexture(previewTexture));
			String memory = Levels.calculateMemory();
			Main.fontLarge.drawWord(memory, Main.LARGE_SCREEN_XEND - 8 - Main.fontLarge.getLineWidth(memory), Display.getHeight()/64*35, Color.white);
		}else previewTexture = -1;
		HvlMenu.updateMenus(delta);
	}

}
