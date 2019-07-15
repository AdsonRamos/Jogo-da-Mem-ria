package game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Card {
	private int x, y;
	public static final int WIDTH = 80, HEIGHT = 100;
	
	private boolean discovered = false, done = false;
	private int id;
	
	private BufferedImage img;
	private BufferedImage question;
	private BufferedImage question_darker;
	private boolean darker;
	
	public Card(int x, int y, int id) {
		this.x = x;
		this.y = y;
		
		darker = false;
		
		try {
			img = ImageIO.read(new File("res/0"+id+".png"));
			question = ImageIO.read(new File("res/question.png"));
			question_darker = ImageIO.read(new File("res/question_darker.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean click(int mouseX, int mouseY) {
		if(new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(mouseX, mouseY)) {
			discovered = true;
			return true;
		} else {
			return false;
		}
	}
	
	public void setID(int id) {
		this.id = id;
		try {
			img = ImageIO.read(new File("res/0"+id+".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void render(Graphics2D g) {
		
		if(!done) {
			if(!discovered) {
				if(!darker) {
					g.drawImage(question, x, y, null);
				} else {
					g.drawImage(question_darker, x, y, null);
				}
			} else {
				g.drawImage(img, x, y, null);
			}
		} else {
			
		}
		
	}
	
	public void setDiscovered(boolean discovered) {
		this.discovered = discovered;
	}
	
	public void getItDone() {
		done = true;
	}
	
	public void update(int x, int y) {
		if(new Rectangle(this.x, this.y, WIDTH, HEIGHT).contains(x, y)) {
			darker = true;
		} else {
			darker = false;
		}
	}
	
	public boolean isDiscovered() {
		return discovered;
	}
	
	public int getID() {
		return id;
	}
}
