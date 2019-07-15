package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {

	/**
	 * 
	 * falta implementar o algoritmo para gerar os números aleatórios
	 * 
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Timer timer;
	
	private Card cards[][] = new Card[4][4];
	
	private int prevI, prevJ;
	private int nextI, nextJ;
	
	private int n = 8;
	
	private int mouseMovementX, mouseMovementY;
	
	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;
	
	private int ids[] = new int[2];
	
	private int mouseClickedX, mouseClickedY;
	
	private int index = 0;
	private int time = 0;
	
	private boolean toRemove = false;
	private boolean toAdjust;
	
	private MainWindow window;
	
	public GamePanel(MainWindow window) {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		
		this.window = window;
		
		setCards();
		
		timer = new Timer(20, this);
		timer.start();
	}
	
	private void setCards() {
		
		
		int[] aux = new int[8];
		
		for(int i = 0; i < 8; i++) {
			aux[i] = 0;
		}
		
		Random r = new Random();
		
		int x;
		
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				
				cards[i][j] = new Card(20 + j * Card.WIDTH + 10*j, i* Card.HEIGHT + 10*i + 20, 7);
				
				x = 1 + r.nextInt(8);
				while((aux[x - 1] >= 2)) {
					x = 1 + r.nextInt(8);
				}
				cards[i][j].setID(x);
				aux[x-1]++;
			}
		}
	}

	private void verifyClick(int x, int y) {
		if(toRemove || toAdjust) return;
		
		for(int i = 0; i < cards.length; i++) {
			for(int j = 0; j < cards[0].length; j++) {
				if(cards[i][j].click(x, y)) {
					ids[index] = cards[i][j].getID();
					index++;
					if(index == 1) {
						prevI = i;
						prevJ = j;
					} else if(index == 2) {
						nextI = i;
						nextJ = j;
						if(ids[0] == ids[1]) {
							toRemove = true;
						} else {
							toAdjust = true;
						}
						index = 0;
					}
				}
			}
		}
	}
	
	private void update() {
		updateCards();
		if(toRemove) {
			time++;
			if(time == 50) {
				toRemove = false;
				time = 0;
				cards[prevI][prevJ].getItDone();
				cards[nextI][nextJ].getItDone();
				n--;
			}
		}
		if(toAdjust) {
			time++;
			if(time == 50) {
				toAdjust = false;
				time = 0;
				cards[prevI][prevJ].setDiscovered(false);
				cards[nextI][nextJ].setDiscovered(false);;
			}
		}
		
		if(n == 0) {
			Object[] options = {"Sair", "Jogar novamente"};
			this.repaint();
			int dialog = JOptionPane.showOptionDialog(null, "O que você deseja fazer?", "Fim de jogo", JOptionPane.DEFAULT_OPTION, 
					JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(dialog == 1) {
				setCards();
				this.repaint();
				n = 8;
			} else {
				System.exit(0);
			}
		}
	}
	
	private void updateCards() {
		for(int i = 0; i < cards.length; i++) {
			for(int j = 0; j < cards[i].length; j++) {	
				if(cards[i][j] == null) return;
				cards[i][j].update(mouseMovementX, mouseMovementY);
			}
		}
	}
	
	public void render(Graphics2D g) {
		// draw background
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		
		// draw cards
		for(int i = 0; i < cards.length; i++) {
			for(int j = 0; j < cards[0].length; j++) {
				cards[i][j].render(g);
			}
		}
		
		// draw number
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g.setColor(Color.BLACK);
		g.setFont(new Font("Arial", Font.BOLD, 42));
		g.drawString(""+n, 480, 150);
		
		g.setFont(new Font("Arial", Font.BOLD, 22));
		g.drawString("Peças faltantes:", 400, 100);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}



	@Override
	public void mouseClicked(MouseEvent e) {
		mouseClickedX = e.getX();
		mouseClickedY = e.getY();
		
		verifyClick(mouseClickedX, mouseClickedY);
	}



	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}



	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}



	@Override
	public void mousePressed(MouseEvent arg0) {
		
	}



	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}



	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}



	@Override
	public void mouseMoved(MouseEvent e) {
		mouseMovementX = e.getX();
		mouseMovementY = e.getY();
	}

}
