package game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.plaf.DimensionUIResource;

@SuppressWarnings("serial")
public class GamePanel extends JPanel implements Runnable, KeyListener {

	public static final int width = 400;
	public static final int height = 400;
	//Render
	private Graphics2D g2d;
	private BufferedImage image;
	
	
	//Game Loop
	private Thread thread;
	private boolean running;
	private long targetTime;
	
	//Game Stuff
	private final int SIZE = 10;
	private int score;
	private int level;
	private boolean gameOver;
	private Entity head;
	private Entity apple;
	private ArrayList<Entity> snake;
	
	//Movement
	private int dx;
	private int dy;
	
	//Key input
	private boolean up;
	private boolean down;
	private boolean left;
	private boolean right;
	private boolean start;
	
	public GamePanel() {
		setPreferredSize(new DimensionUIResource(width, height));
		setFocusable(true);
		requestFocus();
		addKeyListener(this);
	}
	
	@Override
	public void addNotify() {
		super.addNotify();
		thread = new Thread(this);
		thread.start();
	}
	private void setFps(int fps){
		targetTime = 1000 / fps;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_UP){
			up = true;
		}
		if(k == KeyEvent.VK_DOWN){
			down = true;
		}
		if(k == KeyEvent.VK_LEFT){
			left = true;
		}
		if(k == KeyEvent.VK_RIGHT){
			right = true;
		}
		if(k == KeyEvent.VK_ENTER){
			start = true;
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int k = e.getKeyCode();
		if(k == KeyEvent.VK_UP){
			up = false;
		}
		if(k == KeyEvent.VK_DOWN){
			down = false;
		}
		if(k == KeyEvent.VK_LEFT){
			left = false;
		}
		if(k == KeyEvent.VK_RIGHT){
			right = false;
		}
		if(k == KeyEvent.VK_ENTER){
			start = false;
		}

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		

	}

	@Override
	public void run() {
		if (running) return;
		init();
		long startTime;
		long elapsed;
		long wait;
		while (running){
			startTime = System.nanoTime();
			
			update();
			requestRender();
			
			elapsed = System.nanoTime() - startTime;
			wait = targetTime - elapsed / 1000000;
			if (wait > 0){
				try{
					Thread.sleep(wait);
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
			
		}
	}

	private void requestRender() {
		render(g2d);
		Graphics g = getGraphics();
		g.drawImage(image, 0, 0, null);
		g.dispose();
		
		
	}

	private void update() {
		if(gameOver){
			if(start){
				setUpLevel();
			}
			return;
		}
		if(up && dy == 0){
			dy = -SIZE;
			dx = 0;
		}
		if(down && dy == 0){
			dy = SIZE;
			dx = 0;
		}
		if(left && dx == 0){
			dy = 0;
			dx = -SIZE;
		}
		if(right && dx == 0 && dy != 0){
			dy = 0;
			dx = SIZE;
		}
		if(dx != 0 || dy != 0 ){
		for(int i = snake.size() - 1; i > 0; i--){
			snake.get(i).setPosition(snake.get(i - 1).getX(), snake.get(i - 1).getY());
		}
		head.move(dx, dy);
		}
		for(Entity e : snake){
			if(e.isCollision(head)){
				gameOver = true;
				break;
			}
		}
		//Snake grows in size per apple eaten
		if(apple.isCollision(head)){
			score++;
			setApple();
			Entity e = new Entity(SIZE); 	
			e.setPosition(-100, -100);
			snake.add(e);
			if(score % 10 == 0){
				level++;
				if(level > 10){ 
					level = 10;
				}
				setFps(level * 10);
				
			}
		}
		if(head.getX() < 0){
			head.setX(width);
		}
		if(head.getY() < 0){
			head.setY(height);
		}
		if(head.getX() > width){
			head.setX(0);
		}
		if(head.getY() > width){
			head.setY(0);
		}
		
	}
	
	//Renders various game objects
	public void render(Graphics2D g2d){
		g2d.clearRect(0, 0, width, height);
		g2d.setColor(Color.GREEN);
		for(Entity e : snake){
			e.render(g2d);
		}
		g2d.setColor(Color.RED);
		apple.render(g2d);
		if(gameOver){
			g2d.drawString("Game Over!", 150, 200);
		}
			
		g2d.setColor(Color.WHITE);
		g2d.drawString("Score : " + score + " Level : " + level, 10, 10);
		if(dx == 0 && dy == 0){
			g2d.drawString("Ready!", 150, 200);
		}
	}

	private void init() {
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		g2d = image.createGraphics();
		running = true;
		setUpLevel();
		gameOver = false;
		level = 1;
		setFps(level * 10);
		
	}
	private void setUpLevel(){
		snake = new ArrayList<Entity>();
		head = new Entity(SIZE);
		head.setPosition(width / 2, height / 2);
		snake.add(head);
		
		//starting size of snake
		for(int i = 1; i < 3; i++){
			Entity e = new Entity(SIZE); 	
			e.setPosition(head.getX() + (i * SIZE), head.getY());
			snake.add(e);
		}
		apple = new Entity(SIZE);
		setApple();
		score = 0;
		gameOver = false;
	}
	public void setApple(){
		int appleLocationX = (int)(Math.random() * (width - SIZE));
		int appleLocationY = (int)(Math.random() * (height - SIZE));
		
		//Prevent apple from randomly appearing over top snake at start
		appleLocationY = appleLocationY - (appleLocationY % SIZE);
		appleLocationX = appleLocationX - (appleLocationX % SIZE);
		
		apple.setPosition(appleLocationX, appleLocationX);
		
	}

}
