package com.osreboot.ld34;

import java.util.ArrayList;
import java.util.Arrays;

import org.newdawn.slick.Color;

import com.osreboot.ld34.Level.Gate;
import com.osreboot.ld34.Level.Slider;
import com.osreboot.ridhvl.action.HvlAction0;
import com.osreboot.ridhvl.action.HvlAction2;
import com.osreboot.ridhvl.action.HvlAction2r;

public class Levels {

	public static Level getLevel(int index){
		for(Level l : levels) if(l.getIndex() == index) return l;
		return null;
	}
	
	public static float calculateRawMemory(){
		float memory = 0;
		for(int i = 0; i < levels.size(); i++){
			if(Save.completedLevels[i]) memory += levels.get(i).getWorth();
		}
		return memory;
	}
	
	public static String calculateMemory(){
		float memory = 0;
		for(int i = 0; i < levels.size(); i++){
			if(Save.completedLevels[i]) memory += levels.get(i).getWorth();
		}
		return breakMemory(memory);
	}
	
	public static String breakMemory(float mi){
		String symbol = "mb";
		if(mi / 1000 > 1){
			symbol = "gb";
			mi/= 1000;
		}
		if(mi / 1000 > 1){
			symbol = "tb";
			mi/= 1000;
		}
		if(mi / 1000 > 1){
			symbol = "pb";
			mi/= 1000;
		}
		return mi + symbol;
	}
	
	public static ArrayList<Level> levels = new ArrayList<>();
	
	private static final HvlAction2r<Boolean, Integer, Boolean[]> gate_closed = new HvlAction2r<Boolean, Integer, Boolean[]>(){
		@Override
		public Boolean run(Integer i, Boolean[] b){
			return false;
		}
	};
	
	private static final HvlAction2r<Boolean, Integer, Boolean[]> gate_open = new HvlAction2r<Boolean, Integer, Boolean[]>(){
		@Override
		public Boolean run(Integer i, Boolean[] b){
			return b[i];
		}
	};
	
	private static final HvlAction2r<Boolean, Integer, Boolean[]> gate_invert = new HvlAction2r<Boolean, Integer, Boolean[]>(){
		@Override
		public Boolean run(Integer i, Boolean[] b){
			return !b[i];
		}
	};
	
	private static final HvlAction2r<Boolean, Integer, Boolean[]> gate_on = new HvlAction2r<Boolean, Integer, Boolean[]>(){
		@Override
		public Boolean run(Integer i, Boolean[] b){
			return true;
		}
	};
	
	public static Level intro, intro2, practice1, intro3, practice2, intro4, test1, test2, test3;
	
	public static void initialize(){
		intro = new Level(new Boolean[]{true, true, true}, new Boolean[]{false, true, false}, Main.IDX_SERVER_SMALL, 127, 0, 0, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("hey, glad you accepted my \noffer. this server belongs to \na hobbyist out in a suburb \nsomwhere. should be a piece of \ncake.", 0f, false);
				Main.writeToTerminal("[comms terminated 12:20:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				Main.fontLarge.drawWord("<DRAG", Main.LARGE_SCREEN_XSTART + 132, l.getSliders().get(0).getOffsetFine() + Main.LARGE_SCREEN_YCENTER - 64f, new Color(0.906f, 0.906f, 1f, 0.4f));
				Main.fontLarge.drawWord("MATCH^", Main.LARGE_SCREEN_XEND - 172, Main.LARGE_SCREEN_YCENTER + 52f, new Color(0.906f, 0.906f, 1f, 0.4f));
			}
		});
		intro.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro),
				new Level.Slider(-2, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro)
		})));
		
		intro2 = new Level(new Boolean[]{true, false, true}, new Boolean[]{true, false, true}, Main.IDX_SERVER_SMALL, 311, 1, 127, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("all warmed up? good. i don't \nhave the info on this one, \nall i know is it fits our \nsize requirements.", 0f, false);
				Main.writeToTerminal("[comms terminated 12:25:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
			
			}
		});
		intro2.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, intro2),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, intro2),
				new Level.Slider(-2, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro2)
		})));
		
		practice1 = new Level(new Boolean[]{true, true, true, true}, new Boolean[]{false, true, false, false}, Main.IDX_SERVER_MEDIUM, 756, 2, 400, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("you don't mess around, do you? \nthis one belongs to a game \nserver hosting llc. end 'em", 0f, false);
				Main.writeToTerminal("[comms terminated 12:32:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		practice1.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, practice1),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, practice1),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, practice1),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, practice1)
		})));
		
		intro3 = new Level(new Boolean[]{false, false, false}, new Boolean[]{true, true, true}, Main.IDX_SERVER_MEDIUM, 3002, 3, 1000, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("this server contains advanced \nlogic chips that create a \npositive signal without a \nstimulating signal. good luck.", 0f, false);
				Main.writeToTerminal("[comms terminated 12:40:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		intro3.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro3),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, intro3),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, intro3)
		})));
		practice2 = new Level(new Boolean[]{false, false, true}, new Boolean[]{true, true, true}, Main.IDX_SERVER_LARGE, 10403, 4, 4000, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("wow! four gigabytes and growing! \nno need to slow down now. \npeople are going to start to \nnotice our botnet", 0f, false);
				Main.writeToTerminal("[comms terminated 12:52:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		practice2.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(-2, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, practice2),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, practice2),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, practice2),
				new Level.Slider(-3, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, practice2)
		})));
		
		intro4 = new Level(new Boolean[]{false, true, true}, new Boolean[]{true, false, true}, Main.IDX_SERVER_LARGE, 207227, 5, 10000, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("now things start to get tricky. \nthis server bank has inverters \n(they reverse input signals).", 0f, false);
				Main.writeToTerminal("[comms terminated 1:01:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		intro4.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(-3, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, intro4),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, intro4),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, intro4),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, intro4),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, intro4),
		})));
		
		test1 = new Level(new Boolean[]{false, true, true, true}, new Boolean[]{false, false, false, false}, Main.IDX_SERVER_HUGE, 4547483, 6, 200000, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("this is real now. we will \nspend a lot of time behind bars \nif we get caught. this is \nserious business.", 0f, false);
				Main.writeToTerminal("[comms terminated 1:11:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		test1.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
				}, test1),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, test1),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, test1),
				new Level.Slider(-2, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
				}, test1),
		})));
		
		test2 = new Level(new Boolean[]{true, true, true}, new Boolean[]{false, true, false}, Main.IDX_SERVER_HUGE, 14067858, 7, 1000000, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("hands starting to shake? \nyeah, mine too.", 0f, false);
				Main.writeToTerminal("[comms terminated 1:25:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		test2.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, test2),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, test2),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, test2),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
				}, test2),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, test2),
		})));
		
		test3 = new Level(new Boolean[]{true, true, true}, new Boolean[]{true, false, true}, Main.IDX_SERVER_HUGE, 342948932, 8, 14067858, new HvlAction0(){
			@Override
			public void run(){
				Main.writeToTerminal("", 0f, false);
				Main.writeToTerminal("this is the big one, middle \nof a huge datacenter. we can \nsell the access key and make \na fortune. let's do this.", 0f, false);
				Main.writeToTerminal("[comms terminated 5:02:13:12]", 0f, true);
			}
		}, new HvlAction2<Float, Level>(){
			@Override
			public void run(Float delta, Level l){
				
			}
		});
		test3.setSliders(new ArrayList<Slider>(Arrays.asList(new Slider[]{
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, test3),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, test3),
				new Level.Slider(-1, new Gate[]{
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
				}, test3),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
						new Level.Gate(gate_invert, Main.IDX_GATE_INVERT),
				}, test3),
				new Level.Slider(0, new Gate[]{
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_on, Main.IDX_GATE_ON),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, test3),
				new Level.Slider(-3, new Gate[]{
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_open, Main.IDX_GATE_OPEN),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
						new Level.Gate(gate_closed, Main.IDX_GATE_CLOSED),
				}, test3),
		})));
	}
	
}
