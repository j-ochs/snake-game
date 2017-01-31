import java.util.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// ------>  press 'w' to reset the game.  press 'q' to quit the game  <--------
/**
 * This class plays the classic video game sometimes called Worms or Snake
 * @author Jacob Ochs
 * @version 032715.1
 */
public class WormGame extends Frame{

	protected int width = 1000, height = 1000;
	protected int scale = 40;		//at 1000x1000 and 40, scale is 1/25 the size of the board's x and y pixel size, creating a 25x25 grid
	protected int scale2 = 25;
	protected int roundRectScale = 10;
	protected int lastKey = 0;
	Random r = new Random();
	protected static int count = 0;
	protected int highScore = 0;
	protected int averageScore;
	protected int currentScore;
	protected int numAttempts = 1;
	protected int runningScore;
	
	protected int headX = r.nextInt(scale2)*scale;  //x coordinate for head of worm
	protected int headY = r.nextInt(scale2)*scale;  //y coordinate for head of worm
	protected int foodX = r.nextInt(scale2)*scale;  //x coordinate for food
	protected int foodY = r.nextInt(scale2)*scale;  //y coordinate for food
	
	protected Point foodXY = new Point(foodX,foodY);
	protected Point headXY = new Point(headX, headY);
	protected Queue<Point> myWorm = new LinkedList<Point>();
	
	/**
	 * This constructor creates an instance of our WormGame class
	 */
	public WormGame(){
		MyKey mk = new MyKey();
		addKeyListener(mk);
		setSize(width, height);
		setUndecorated(true);
		setResizable(false);
		setVisible(true);
		setBackground(Color.BLACK);
		myWorm.add(new Point(headX, headY));
	}

	/**
	 * This class exercises the functionality of the KeyAdapter class
	 */
	class MyKey extends KeyAdapter{

		public void keyPressed(KeyEvent e){
			lastKey = e.getKeyCode();
					if(lastKey == KeyEvent.VK_Q){
						System.exit(0);
					}
					if(lastKey == KeyEvent.VK_W){
						resetGame();
						numAttempts++;
					}
				}
		public void keyReleased(KeyEvent e) {}
		public void keyTyped(KeyEvent e) {}
	}	//end of inner class MyKey ----------------------------------------
	
	/**
	 * This class creates our worm, which is a queue of x and y coordinate points
	 */
	class Worm {
		public Worm(){
			myWorm.add(new Point(headX, headY));
		}
		//public void addSeg(int x, int y){
		//	myWorm.add(new Point(x, y));
		//}
	}  //end of inner class Worm --------------------------------------------
		
	/**
	 * This method paints the initial food spawn at the start of the game
	 * @param gc is a graphics context
	 */
	public void drawFood(Graphics gc){
		gc.setColor(Color.GREEN);
		gc.fillOval(foodX, foodY, scale, scale);
	}
	/**
	 * This method paints the initial worm head segment at the start of the game
	 * @param gc is a graphics context
	 */
	public void paintHead(Graphics gc){
		gc.setColor(Color.ORANGE);
		gc.fillRoundRect(headX, headY, scale, scale, roundRectScale, roundRectScale);	
	}
	/**
	 * This method resets the game at the beginning again
	 */
	public void resetGame(){
		foodX = r.nextInt(scale2)*scale;
		foodY = r.nextInt(scale2)*scale;
		headX = r.nextInt(scale2)*scale;
		headY = r.nextInt(scale2)*scale;
		myWorm.clear();
		count--;
		move();
		//repaint();
	}
	
	public boolean hitSelf(){
		Queue<Point> temp = new LinkedList<Point>();
		for (Point p: myWorm) {
			temp.add(p);
			if (temp.peek() == (new Point(headX,headY))){
				count++;
				repaint();
			}
		}
		return false;
	}

	/**
	 * this method exercises all of the other previous methods, causing motion on our frame
	 */
	public void move(){
		switch(lastKey){
		case KeyEvent.VK_LEFT: headX = headX - scale; break;
		case KeyEvent.VK_RIGHT: headX = headX + scale; break;
		case KeyEvent.VK_UP: headY = headY - scale; break;
		case KeyEvent.VK_DOWN: headY = headY + scale; break;
		}
			if (headX == foodX && headY == foodY){  //spawn a new food at random on the frame
				myWorm.add(new Point(foodX, foodY));
				foodX = r.nextInt(scale2)*scale;
				foodY = r.nextInt(scale2)*scale;
			}		
			if (headX != foodX || headY != foodY){
				myWorm.add(new Point(headX, headY));
				myWorm.remove();
			}
			if (headX == 0-scale || headX == width || headY == 0-scale || headY == height){  //stop the worm if you run into a wall
				count++;
				//repaint();
			}
			Queue<Point> temp = new LinkedList<Point>();
			for (Point p: myWorm) {
				temp.add(p);
			}
			
			Iterator it = temp.iterator();
			while (it.hasNext()){
				Point w = (Point) it.next();
			
			for (int s=0; s<temp.size(); s++){
				//if (myWorm.peek() != temp.peek()){}
				//else 
				//	count++;
					//repaint();
				}
			}
			
		//	Point temp = myWorm.peek();
		//	myWorm.remove(temp);
		//	if (myWorm.contains(temp)) {
		//		count++; repaint();
			}// else {
				//head = temp;
	//			myWorm.add(new Point (headX, headY));
		//	}
			
	//}
	
	/**
	 * This method paints our graphics onto the frame
	 * @param gc is a graphics context
	 */
	public void paint(Graphics gc){
		paintHead(gc);
		drawFood(gc);
		Font f = new Font("SERIF", Font.PLAIN, 30);
		gc.setFont(f);
		gc.setColor(Color.PINK);
		gc.drawString("Press an arrow key to get started, Q to exit, or W to reset", width/9, height/2);
		gc.drawString("Current Score : " + currentScore, 30, height-150);
		gc.drawString("High Score : " + highScore, 30, height-100);
		gc.drawString("Attempts : " + numAttempts, 30, height-50);
		//gc.drawString("Average Score : " + runningScore, 30, height-75);
		gc.setColor(Color.ORANGE);
		for (Point p: myWorm) gc.fillRoundRect(p.x, p.y, scale, scale, roundRectScale, roundRectScale);
		currentScore = myWorm.size();
		if(currentScore > highScore) highScore = currentScore;
		if(numAttempts < 1) 
			runningScore = currentScore;
		if(numAttempts >= 1){
			runningScore = (runningScore + currentScore)/numAttempts;
		}
	}

	/**
	 * This main method exercises all of the methods previously written in our class
	 * @param args is a command line string argument
	 */
	public static void main(String[] args){
		WormGame wg = new WormGame();
		while(true){
			wg.repaint();
			try{Thread.sleep(109);}
			catch (Exception e){}
			if (count <= 0){
				wg.move();
			}
		}
	}
}
