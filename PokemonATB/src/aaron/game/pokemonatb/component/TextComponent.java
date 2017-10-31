package aaron.game.pokemonatb.component;

import java.util.ArrayList;
import java.util.List;

public class TextComponent extends Component{
	public List<String> text = new ArrayList<String>();
	public int numLines;
	public int currentLine;
	public int currentChar;
	public int ticksPerChar;
	public int currentTick;
	public boolean waiting;
	
	public TextComponent(List<String> text, int ticksPerChar) {
		super();
		this.text = text;
		this.currentLine = 0;
		this.currentChar = 0;
		this.ticksPerChar = ticksPerChar;
		this.currentTick = 0;
		this.numLines = text.size();
		this.waiting = false;
	}
}
