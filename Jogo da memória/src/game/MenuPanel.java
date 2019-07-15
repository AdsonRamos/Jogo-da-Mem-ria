package game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class MenuPanel extends JPanel implements ActionListener, KeyListener, MouseListener, MouseMotionListener{
	public static final int WIDTH = 600;
	public static final int HEIGHT = 500;

	private BufferedImage background, start, exit, ico;
	
	private int menuChoice = 0;
	
	private Timer timer;
	
	private MainWindow window;
	
	public MenuPanel(MainWindow window) {
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
		setFocusable(true);
		
		this.window = window;
		
		timer = new Timer(20, this);
		timer.start();
		
		try {
			background = ImageIO.read(new File("res/background.png"));
			start = ImageIO.read(new File("res/start.png"));
			exit = ImageIO.read(new File("res/exit.png"));
			ico = ImageIO.read(new File("res/ico.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		update();
		repaint();
	}
	
	private void update() {
		
	}
	
	private void startGame() {
		window.remove(window.menu);
		
		GamePanel game = new GamePanel(window);
		
		window.pack();
		window.add(game);
		window.addMouseListener(game);
		window.addMouseMotionListener(game);
		window.setSize(WIDTH, HEIGHT);
		window.setResizable(false);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setLocationRelativeTo(null);
		window.requestFocus();
		window.setVisible(true);
	}
	
	private void render(Graphics2D g) {
		g.drawImage(background, 0, 0, null);
		g.drawImage(start, 196, 185, null);
		g.drawImage(exit, 231,237, null);
		
		if(menuChoice == 0) {
			g.drawImage(ico, 155, 180, null);
		} else if (menuChoice == 1){
			g.drawImage(ico, 190, 230, null);
		}
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		render((Graphics2D) g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
			menuChoice++;
			if(menuChoice > 1) {
				menuChoice = 0;
			}
		} else if(e.getKeyCode() == KeyEvent.VK_UP) {
			menuChoice--;
			if(menuChoice < 0) {
				menuChoice = 1;
			}
		}
		
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(menuChoice == 0) {
				startGame();
			} else if(menuChoice == 1) {
				System.exit(JFrame.EXIT_ON_CLOSE);
			}
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(e.getY() > 230) {
			menuChoice = 1;
		} else {
			menuChoice = 0;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(menuChoice == 0) {
			startGame();
		} else {
			System.exit(JFrame.EXIT_ON_CLOSE);
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		
	}
}
