package javagames.timeandspace;

import java.awt.*;
import static java.awt.Color.BLACK;
import java.awt.event.*;
import java.awt.image.*;
import java.util.ArrayList;
import java.util.Random;
import javagame.VectorObject;
import javagames.util.*;
import javax.swing.*;

public class CannonControl extends JFrame implements Runnable {

    private static final int SCREEN_W = 1280;
    private static final int SCREEN_H = 720;
    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    private Canvas canvas;
    private Point point = new Point(0, 0);
    private Random rand;
    private float windSpeed = 0, score = 0, blockMulti = 0;
    private float vx, vy;
    private CityBlockManager city;
    private boolean FirstStart = false,GameOver = false;
    private int totalMeteor;
 ////
    	protected Color appBackground = Color.BLACK;
	protected Color appBorder = Color.LIGHT_GRAY;
	protected Color appFPSColor = Color.GREEN;
	protected Font appFont = new Font("Courier New", Font.PLAIN, 14);
	protected String appTitle = "TBD-Title";
	protected float appBorderScale = 0.8f;
	protected int appWidth = 1270;
	protected int appHeight = 720;
	protected float appWorldWidth = 2.0f;
	protected float appWorldHeight = 2.0f;
	protected long appSleep = 10L;
	protected boolean appMaintainRatio = false;
    ////

    private ArrayList<VectorObject> meteoroids;

    boolean startGame;

    public CannonControl() {
    }

    public void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(SCREEN_W, SCREEN_H);
        canvas.setBackground(appBackground);
		
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Cannon Example");
        setIgnoreRepaint(true);
        pack();
        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);
        mouse = new RelativeMouseInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);
        canvas.addMouseWheelListener(mouse);
        setVisible(true);
        canvas.createBufferStrategy(2);
        bs = canvas.getBufferStrategy();
        canvas.requestFocus();
        gameThread = new Thread(this);
        gameThread.start();
        
        if (appMaintainRatio) {
			getContentPane().setBackground(appBorder);
			setSize(appWidth, appHeight);
			setLayout(null);
			getContentPane().addComponentListener(new ComponentAdapter() {
				public void componentResized(ComponentEvent e) {
					onComponentResized(e);
				}
			});
		} else {
			canvas.setSize(appWidth, appHeight);
			pack();
		}
        
    }

    public void run() {
        running = true;
        initialize();
        long curTime = System.nanoTime();
        long lastTime = curTime;
        double nsPerFrame;

        while (running) {
            curTime = System.nanoTime();
            nsPerFrame = curTime - lastTime;
            gameLoop(nsPerFrame / 1.0E9);
            lastTime = curTime;
        }
    }

    private void initialize() {
        disableCursor();
        city = new CityBlockManager();
        city.cityMaker();
        frameRate = new FrameRate();
        frameRate.initialize();
        score = 0;
        windSpeed = 0;
        blockMulti = 0;
        totalMeteor = 1;
        meteoroids = new ArrayList<VectorObject>();
        VectorObject vec = new VectorObject(2, BLACK);
        rand = new Random();
        int random = 30;
        //no overlap on same point
            random = rand.nextInt(SCREEN_W);       
        //when box is clicked add 10 to score
        vec.setVectorLocation(random, -25);
        meteoroids.add(vec);        
        vx = vy = 2;

        startGame = false;
    }

    private void gameLoop(double delta) {
        processInput(delta);
        if (startGame) {
            FirstStart = true;
            updateObjects(delta);
        }
        renderFrame();
        sleep(10L);
    }

    private void renderFrame() {
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics();
                    g.clearRect(0, 0, getWidth(), getHeight());
                        render(g);
                } finally {
                    if (g != null) {
                        g.dispose();
                    }
                }
            } while (bs.contentsRestored());
            bs.show();
        } while (bs.contentsLost());
    }

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

    private void processInput(double delta) {
        keyboard.poll();
        mouse.poll();

        point = mouse.getPosition();

        if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
            startGame = true;
        }
        
         if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
             //call check meteor
            for (int i = 0; i < meteoroids.size(); i++)
            {
                if (meteoroids.get(i) != null)
                {
                    if (point.x >= meteoroids.get(i).centerLocation.x - 10 
                            && point.x <= meteoroids.get(i).centerLocation.x + 10
                            && point.y >= meteoroids.get(i).centerLocation.y - 10
                            && point.y <= meteoroids.get(i).centerLocation.y + 10)
                    {
                        meteoroids.remove(i);
                        score+=10;
                        if (score % 30 == 0)
                        {
                            totalMeteor++;
                        }
                    }
                }
            }//end of for 
         }//end of if
        
    }

    private void updateObjects(double delta) {
        Matrix3x3f mat = Matrix3x3f.identity();
        windSpeed = score / 1000;
        if(windSpeed > 1){
            windSpeed = 1;
        }

        city.updateWorld();
        //block multiplier
        blockMulti = city.countCity();
        //max windSpeed set
        if(blockMulti == 0){
            GameOver = true;
        }
        rand = new Random(15);

         for (int i = 0; i < meteoroids.size(); i++) {
             meteoroids.get(i).ty += (score / 10000);
         }
     
         for (int i = 0; i < meteoroids.size(); i++) {
            if (meteoroids.get(i) != null) {
                 meteoroids.get(i).ty += .001F; 
                meteoroids.get(i).setVectorLocation(meteoroids.get(i).centerLocation.x + windSpeed, meteoroids.get(i).centerLocation.y + meteoroids.get(i).ty);
                //Rendering in cycle or death   
                if (meteoroids.get(i).centerLocation.y > SCREEN_H + 25) {
                     meteoroids.get(i).ty = .0001F;
                }
                meteoroids.get(i).updateWorld();
            } else {
                //if at death create new
                 meteoroids.get(i).ty = 0;
                VectorObject vec = new VectorObject(2, BLACK);
                rand = new Random();
                int random = rand.nextInt(SCREEN_W);
                for (int x = 0; x < meteoroids.size(); x++) {
                    while (random <= meteoroids.get(x).centerLocation.x + 30 && random >= meteoroids.get(x).centerLocation.x - 30) {
                        random = rand.nextInt(SCREEN_W);
                    }
                }
                vec.setVectorLocation(random, -25);
                meteoroids.add(vec);
            }
        }

        //add random if it is made.
        if (meteoroids.size() < totalMeteor){ //&& score % 30 == 0 && score != 0) {

            rand = new Random();
            int random = rand.nextInt(SCREEN_W);
            int delay = -25;
            for (int x = 0; x < meteoroids.size(); x++) {
                
                while (random <= meteoroids.get(x).centerLocation.x + 30 && random >= meteoroids.get(x).centerLocation.x - 30) {
                    random += 30;
                    if(x!=0){
                         x--;
                    }
                }
            }

            VectorObject vec = new VectorObject(2, BLACK);
            //when box is clicked add 10 to score
            vec.setVectorLocation(random, delay);
            meteoroids.add(vec);
        }

        city.hitBuilding(meteoroids);
    }

    /**
     * Renders the Info to the Screen
     *
     * @params Graphics g
     */
    private void renderBoard(Graphics g) {
       
        String windSpeedBoard = String.format("Wind Speed : %.02f", windSpeed);
        String scoreBoard = String.format("Score: %.02f", score);
        String blockMultiBoard = String.format("Block Multi : %.0f", blockMulti);

        
        if (!startGame) {
            g.setColor(Color.GREEN);
            g.drawString("Press Space to start", 20, 70);
        }
         g.setColor(Color.BLACK);
        g.drawString(windSpeedBoard, 20, 30);
        g.drawString(blockMultiBoard, 20, 45);
        g.drawString(scoreBoard, 20, 10);

    }

    private void render(Graphics g) {
        renderBoard(g);
         g.setColor(Color.RED);
        g.drawLine(point.x + 10, point.y, point.x, point.y);
        g.drawLine(point.x, point.y + 10, point.x, point.y);
        g.drawLine(point.x, point.y, point.x - 10, point.y);
        g.drawLine(point.x, point.y, point.x, point.y - 10);

        for (int i = 0; i < meteoroids.size(); i++) {
            if (meteoroids.get(i) != null) {
                meteoroids.get(i).render(g);
            }
        }
        city.render(g);
         if(blockMulti == 0 && FirstStart){
            GameOver(g);
            //reset
        }
    }

    public void onWindowClosing() {
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.exit(0);
    }
    
    /**
     * From Book unedited
     */
    private void disableCursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        Image image = tk.createImage("");
        Point points = new Point(0, 0);
        String name = "Pointer";
        Cursor cursor = tk.createCustomCursor(image, points, name);
        setCursor(cursor);

    }

    private void GameOver(Graphics g) {
        startGame = false;
        g.setColor(Color.RED);
        g.drawString("Game Over.",200,300);
        initialize();
        score = 0;
        windSpeed = 0;
    }
    
	protected void onComponentResized(ComponentEvent e) {
		Dimension size = getContentPane().getSize();
		int vw = (int) (size.width * appBorderScale);
		int vh = (int) (size.height * appBorderScale);
		int vx = (size.width - vw) / 2;
		int vy = (size.height - vh) / 2;
		int newW = vw;
		int newH = (int) (vw * appWorldHeight / appWorldWidth);
		if (newH > vh) {
			newW = (int) (vh * appWorldWidth / appWorldHeight);
			newH = vh;
		}
		// center
		vx += (vw - newW) / 2;
		vy += (vh - newH) / 2;
		canvas.setLocation(vx, vy);
		canvas.setSize(newW, newH);
	}

	protected Matrix3x3f getViewportTransform() {
		return Utility.createViewport(appWorldWidth, appWorldHeight,
				canvas.getWidth(), canvas.getHeight());
	}

	protected Matrix3x3f getReverseViewportTransform() {
		return Utility.createReverseViewport(appWorldWidth, appWorldHeight,
				canvas.getWidth(), canvas.getHeight());
	}
}