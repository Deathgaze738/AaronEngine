package aaron.game.pokemonatb.system;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Arrays;

import aaron.game.pokemonatb.component.ActiveComponent;
import aaron.game.pokemonatb.component.Component;
import aaron.game.pokemonatb.component.InputComponent;
import aaron.game.pokemonatb.component.InputState;
import aaron.game.pokemonatb.component.InteractibleComponent;
import aaron.game.pokemonatb.component.TextComponent;
import aaron.game.pokemonatb.main.ECSEngine;
import aaron.game.pokemonatb.main.GamePanel;
import aaron.game.pokemonatb.manager.ResourceManager;
import aaron.game.pokemonatb.manager.SoundManager;

public class TextRenderSystem extends GameSystemBase{
	BufferedImage textBoxBackground;
	String[] textBuffer = new String[2];
	int animationOffset = 0;
	int animationTick = 0;
	boolean animationFlag = false;

	public TextRenderSystem(ECSEngine engine) {
		super(engine);
		Arrays.fill(textBuffer, "");
		ArrayList<Class<? extends Component>> cList = new ArrayList<Class<? extends Component>>();
		cList.add(TextComponent.class);
		cList.add(ActiveComponent.class);
		cList.add(InputComponent.class);
		addRequirements(SysRequirement.AllOf, cList);
		cList = new ArrayList<Class<? extends Component>>();
		cList.add(InputComponent.class);
		addRequirements(SysRequirement.AllOf, cList);
		SoundManager.getInstance().addClip("Resources\\sounds\\ding.wav", "ding");
		textBoxBackground = ResourceManager.getInstance().getImage("Resources\\Images\\textbox.png");
		
	}

	@Override
	public void update() {
		if(getNumberOfEntities(0) > 0){
			TextComponent text = engine.getComponent(getFirstEntity(0), TextComponent.class);
			InputComponent pIn = engine.getComponent(getFirstEntity(1), InputComponent.class);
			if((pIn.state == InputState.INTERACT_HOLD || pIn.state == InputState.INTERACT_PRESS) && !pIn.used){
				if(text.waiting){
					SoundManager.getInstance().playClip("ding", SoundManager.ONCE);
					text.waiting = false;
					text.currentChar = 0;
					if(text.currentLine % 2 != 0){
						text.currentLine++;
					}
					if(text.currentLine >= text.numLines){
						text.currentTick = 0;
						text.currentChar = 0;
						text.currentLine = 0;
						System.out.println("COMPLETE");
						engine.addComponent(getFirstEntity(0), new InteractibleComponent());
						engine.removeComponent(getFirstEntity(0), ActiveComponent.class);

					}
					
					textBuffer[1] = "";
					textBuffer[0] = "";
				}
				else{
					if(text.currentLine % 2 == 0){
						textBuffer[0] = text.text.get(text.currentLine).substring(0, text.text.get(text.currentLine).length());
						text.currentLine++;
					}
					if(!(text.currentLine >= text.numLines))
						textBuffer[1] = text.text.get(text.currentLine).substring(0, text.text.get(text.currentLine).length());
					else
						textBuffer[1] = "";
					text.waiting = true;
				}
			}
			else if(!text.waiting){
				if(text.currentTick > text.ticksPerChar && text.currentLine < text.numLines){
					text.currentTick = 0;
					if(text.currentChar >= text.text.get(text.currentLine).length()){
						if(text.currentLine % 2 != 0){
							text.waiting = true;
						}
						text.currentChar = 0;
						text.currentLine++;
					}
					else{
						text.currentChar++;
						textBuffer[text.currentLine % 2] = text.text.get(text.currentLine).substring(0, text.currentChar);				}
				}
				else{
					text.currentTick++;
				}
			}
			if(text.currentLine >= text.numLines){
				text.waiting = true;
			}
			animateArrow();
		}
	}
	
	private void animateArrow(){
		if(animationTick == 4 && animationFlag){
			animationTick = 0;
			animationOffset++;
			if(animationOffset > 3){
				animationFlag = !animationFlag;
			}
		}
		else if(animationTick == 4 && !animationFlag){
			animationTick = 0;
			animationOffset--;
			if(animationOffset < 0){
				animationFlag = !animationFlag;
			}
		}
		animationTick++;
	}
	
	@Override
	public void draw(Graphics2D g){
		if(getNumberOfEntities(0) > 0){
			TextComponent text = engine.getComponent(getFirstEntity(0), TextComponent.class);
			g.setColor(Color.BLACK);
			g.drawImage(textBoxBackground, 0,  2*GamePanel.WIDTH/3 + 24, GamePanel.WIDTH, GamePanel.HEIGHT/4, null);
			g.drawString(textBuffer[0], GamePanel.WIDTH/10, 7 * GamePanel.HEIGHT/8-3);
			g.drawString(textBuffer[1], GamePanel.WIDTH/10, 7 * GamePanel.HEIGHT/8 + 12);
			if(text.waiting){
				int[] x = {245, 250, 255};
				int[] y = {255 + animationOffset, 260 + animationOffset, 255 + animationOffset};
				g.fillPolygon(x, y, 3);
			}
		}
	}
}
