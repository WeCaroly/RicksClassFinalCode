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

public class CannonExample extends JFrame implements Runnable {

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
    private float tx, ty;
    private float vx, vy;
    private CityBlockManager city;

    private ArrayList<VectorObject> meteoroids;

    boolean startGame;

    public CannonExample() {
    }

    public void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(SCREEN_W, SCREEN_H);
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
        meteoroids = new ArrayList<VectorObject>();
        VectorObject vec = new VectorObject(2, BLACK);
        rand = new Random();
        int random = 30;
        //no overlap on same point
        for (int x = 0; x < meteoroids.size(); x++) {
            random = rand.nextInt(SCREEN_W);
            while (random <= meteoroids.get(x).centerLocation.x + 15 && random >= meteoroids.get(x).centerLocation.x - 15) {
                random = rand.nextInt(SCREEN_W);
            }
        }

        //when box is clicked add 10 to score
        vec.setVectorLocation(random, -25);
        meteoroids.add(vec);
        tx = 0;
        ty = 0;
        vx = vy = 2;

        startGame = false;
    }

    private void gameLoop(double delta) {
        processInput(delta);
        if (startGame) {
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
                    }
                }
            }
            //point 
         }
        

    }

    private void updateObjects(double delta) {
        Matrix3x3f mat = Matrix3x3f.identity();
        windSpeed = score / 100;

        city.updateWorld();
        //block multiplier
        blockMulti = city.countCity();
        //update boxes.
        rand = new Random(15);

        //add position  
        //do same as score but remember that this number must be small or it will fly out of control
        //6 should be like max speed this ever gets
        tx += 0; // add wind

        //CANT HAPPEN EVERY FRAME, only update if game is on
        //This goees from 0 speed to 30 speed in 3 seconds
        //only increase speed when a meteor is destoryed or if the score is set to something
        //make it like speed 1 at start then after killing 3 its 2 and then after 8 its 3 or something
        for (int i = 0; i < meteoroids.size(); i++) {
            if (meteoroids.get(i) != null) {
                //double random;
                //random = ((double)rand.nextDouble()%.01);
                ty += .001F; //TODO: add score
                meteoroids.get(i).setVectorLocation(meteoroids.get(i).centerLocation.x, meteoroids.get(i).centerLocation.y + ty);
                //Rendering in cycle or death   
                if (meteoroids.get(i).centerLocation.y > SCREEN_H + 25) {
                    ty = 0;
                }
                meteoroids.get(i).updateWorld();
            } else {
                //if at death create new
                ty = 0;
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

        //add randpm if it is made? 
        if ((meteoroids.size() < 5 && score % 30 == 0 && score != 0)) {

            rand = new Random();
            int random = rand.nextInt(SCREEN_W);
            int delay = -25;
            for (int x = 0; x < meteoroids.size(); x++) {
                while (random <= meteoroids.get(x).centerLocation.x + 30 && random >= meteoroids.get(x).centerLocation.x - 30) {
                    random += 30;
                    x--;
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
        g.setColor(Color.BLACK);
        String windSpeedBoard = String.format("Wind Speed : %.02f", windSpeed);
        String scoreBoard = String.format("Score: %.02f", score);
        String blockMultiBoard = String.format("Block Multi : %.0f", blockMulti);

        g.setColor(Color.GREEN);
        if (!startGame) {
            g.drawString("Press Space to start", 20, 70);
        }
        g.drawString(windSpeedBoard, 20, 30);
        g.drawString(blockMultiBoard, 20, 45);
        g.drawString(scoreBoard, 20, 10);

    }

    private void render(Graphics g) {
        renderBoard(g);

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
}
