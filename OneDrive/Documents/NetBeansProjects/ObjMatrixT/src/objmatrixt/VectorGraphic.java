package objmatrixt;

import java.awt.*;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import java.awt.event.*;
import java.awt.image.*;
import javagames.util.*;
import javax.swing.*;

/**
 *
 * @author Carolyn Wichers
 */
public class VectorGraphic extends JFrame implements Runnable {

    private static final int SCREEN_W = 1280;
    private static final int SCREEN_H = 720;
    private static final int SCREEN_W_HALF = 640;
    private static final int SCREEN_H_HALF = 360;
    private FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;
    private RelativeMouseInput mouse;
    private KeyboardInput keyboard;
    ///////

    private VectorObject shapeTri;
    private VectorObject shapeSq;
    private VectorObject shapeHex;
    
    private boolean flipped = false;

    private Point point = new Point(0, 0);
    Canvas canvas;
    private float rotato = .01f;
    private float tx, ty;
    private float vx, vy;
    private float scale = .1f;
    private boolean doTranslate;
    private boolean doRotateTri;
    float rotation = 0;
    float rotationTri = 0;

    /**
     * the construction of the class disables cursor 
     */
    public VectorGraphic() {
        disableCursor();
    }

    /**
     * From Book unedited
     */
    protected void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(SCREEN_W, SCREEN_H);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);
        getContentPane().add(canvas);
        setTitle("Matrix Transformation");
        setIgnoreRepaint(true);
        pack();
        // Add key listeners
        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);
        // Add mouse listeners
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
    
    /**
     * From Book unedited
     */
    public void run() {
        running = true;
        initialize();
        while (running) {
            gameLoop();
        }
    }
    
    /**
     * From Book unedited
     */
    private void gameLoop() {
        processInput();
        processObjects();
        renderFrame();
        sleep(10L);
    }
    
    /**
     * From Book unedited
     */
    private void renderFrame() {
        do {
            do {
                Graphics g = null;
                try {
                    g = bs.getDrawGraphics(); //check this 
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
    
    /**
     * From Book unedited
     */
    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Initialize gives values to the global variables and creates the VectorObjects
     * 
     */
    private void initialize() {
        frameRate = new FrameRate();
        frameRate.initialize();

        shapeSq = new VectorObject(3, RED);
        doTranslate = true; //starts bounce
        shapeTri = new VectorObject(1, GREEN);
        shapeHex = new VectorObject(2, BLUE);
        shapeHex.setRotation(.2f);
        
        shapeTri.setScale(.2f);
        shapeHex.setScale(.2f);

        shapeSq.setVectorLocation(SCREEN_W_HALF, SCREEN_H_HALF);
        shapeHex.setVectorLocation(SCREEN_W_HALF, SCREEN_H_HALF);
        shapeTri.setVectorLocation(SCREEN_W_HALF, SCREEN_H_HALF);
        reset();
        shapeSq.setScale(scale);
        shapeHex.setScale(scale);
        shapeTri.setScale(scale);

    }

    /**
     * Rets the  values of the screen
     */
    private void reset() {
        tx = SCREEN_W / 2;
        ty = SCREEN_H / 2;
        vx = vy = 2;
        scale = 1.3f;
        doRotateTri = false;
    }

    /**
    *  Processes th Input of all movements and button presses 
    */
    private void processInput() {
        keyboard.poll();
        mouse.poll();

        ////////////////Tringle////////////////////// 
      
      if(!(mouse.getPosition().x < 13 || mouse.getPosition().y < 20 || mouse.getPosition().y > SCREEN_H-12 || mouse.getPosition().x > SCREEN_W-12 )){
        shapeTri.setVectorLocation((float)mouse.getPosition().x, (float)mouse.getPosition().y);
       }
        if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
            doRotateTri = true; //tri roate clockwise
            rotationTri = .02f;
        } else if (mouse.buttonDown(MouseEvent.BUTTON1)) {
                rotationTri = .02f;
           
        }
        else if (mouse.buttonDownOnce(MouseEvent.BUTTON3)) {
            doRotateTri = true; //tri roate counter clockwise
            rotationTri = -.02f;
        }else if (mouse.buttonDown(MouseEvent.BUTTON3)) {
                rotationTri = -.02f;
        }else{
            doRotateTri = false;
           
        }
       

      ////////////// Hexagon//////////////
       if (keyboard.keyDownOnce(KeyEvent.VK_W) || keyboard.keyDown(KeyEvent.VK_W)) {
            //up hex
            if (shapeHex.centerLocation.y > 20) {
                   shapeHex.setVectorLocation(shapeHex.centerLocation.x, shapeHex.centerLocation.y - 1);
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_A)|| keyboard.keyDown(KeyEvent.VK_A)) {
            //left hex
            if (shapeHex.centerLocation.x > 18) {
                   shapeHex.setVectorLocation(shapeHex.centerLocation.x - 1, shapeHex.centerLocation.y);
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_S)|| keyboard.keyDown(KeyEvent.VK_S)) {
            //down hex
            if (shapeHex.centerLocation.y < SCREEN_H - 20) {
                shapeHex.setVectorLocation(shapeHex.centerLocation.x, shapeHex.centerLocation.y + 1);
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_D)|| keyboard.keyDown(KeyEvent.VK_D)) {
            //right hex
            if (shapeHex.centerLocation.x < SCREEN_W - 17) {
                
                shapeHex.setVectorLocation(shapeHex.centerLocation.x + 1, shapeHex.centerLocation.y);
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_Q) || (keyboard.keyDown(KeyEvent.VK_Q))) {
            //inq hex rotation
            if (!flipped)
            {
                if (rotato < 3f)
                    rotato += .001f;
            }
            else
            {
                if (rotato > -3f)
                    rotato -= .001f;
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_E) ||(keyboard.keyDown(KeyEvent.VK_E))) {
            //decrs hex rotation
            if (!flipped)
                rotato -= .001f;
            else
                rotato += .001f;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_SPACE)) {
            flipped = !flipped;
            rotato = -rotato;
            
            
        }
    }

    /**
     * Processes the input and does the rotation and bounce of the objects
     */
    private void processObjects() {
        //Triangle
        if (doRotateTri) {
                shapeTri.setRotation(shapeTri.rotation + rotationTri);
            System.out.println("ROTATION " + shapeTri.rotation + "\n\n");
        }      
     
        
            shapeHex.setRotation(shapeHex.rotation + rotato);
        
        //Square
        if (doTranslate) {
            tx += vx;
            if (tx < 10 || tx > SCREEN_W - 10) {
                vx = -vx;
            }
            ty += vy;
            if (ty < 15 || ty > SCREEN_H - 10) {
                vy = -vy;
            }
        }
        shapeSq.setVectorLocation(tx, ty);

        shapeTri.updateWorld();
        shapeHex.updateWorld();
        shapeSq.updateWorld();
    
    }
    /**
     * Calls renders from objectVector files
     * @param Graphic g
     */
    private void render(Graphics g) {
        frameRate.calculate();

        shapeTri.render(g);
        shapeHex.render(g);
        shapeSq.render(g);
    }

    /**
     * From Book unedited
     */
    protected void onWindowClosing() {
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
