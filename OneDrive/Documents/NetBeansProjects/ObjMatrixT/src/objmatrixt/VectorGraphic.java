package objmatrixt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author carol_8wybosj
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
    //

    private VectorObject shapeTri;
    private VectorObject shapeSq;
    private VectorObject shapeHex;

    private Point point = new Point(0, 0);

    Canvas canvas;
    //

    private float tx, ty;
    private float vx, vy;
    private float scale = .1f;
    private boolean doTranslate;
    private boolean doRotateTri;
    float rotation = 0;
    float rotationTri = 0;

    public VectorGraphic() {
        disableCursor();
    }

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
        // For full screen : mouse = new RelativeMouseInput( this );
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
        while (running) {
            gameLoop();
        }
    }

    private void gameLoop() {
        processInput();
        processObjects();
        renderFrame();
        sleep(10L);
    }

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

    private void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

    private void initialize() {
        frameRate = new FrameRate();
        frameRate.initialize();

        shapeSq = new VectorObject(3, RED);
        doTranslate = true; //starts bounce
        shapeTri = new VectorObject(1, GREEN);
        shapeHex = new VectorObject(2, BLUE);
        shapeHex.setRotation(.2f);

        shapeSq.setLocation(new Point(SCREEN_W_HALF, SCREEN_H_HALF));
        shapeHex.setLocation(new Point(SCREEN_W_HALF, SCREEN_H_HALF));
        shapeTri.setLocation(new Point(SCREEN_W_HALF, SCREEN_H_HALF));
        reset();
        shapeSq.setScale(scale);
        shapeHex.setScale(scale);
        shapeTri.setScale(scale);

    }

    private void reset() {
        tx = SCREEN_W / 2;
        ty = SCREEN_H / 2;
        vx = vy = 2;
        scale = 1.3f;
        doRotateTri = false;
    }

    private void processInput() {
        keyboard.poll();
        mouse.poll();

        //Tringle 
        shapeTri.setLocation(mouse.getPosition());
        if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
            doRotateTri = true; //tri roate clockwise
            rotationTri = .02f;
        } else if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            if(shapeTri.rotation < 5f){
                rotationTri = .02f;
            }else{
                rotationTri = 0;
            }
        }
        else if (mouse.buttonDownOnce(MouseEvent.BUTTON3)) {
            doRotateTri = true; //tri roate counter clockwise
            rotationTri = -.02f;
        }else if (mouse.buttonDown(MouseEvent.BUTTON3)) {
            if(shapeTri.rotation > -5f){
                rotationTri = -.02f;
            }else{
                rotationTri = 0;
            }
        }else{
            doRotateTri = false;
            shapeTri.rotation = 0;
            rotationTri = 0;
        }
       

      ////////////// Hexagon//////////////
       if (keyboard.keyDownOnce(KeyEvent.VK_W) || keyboard.keyDown(KeyEvent.VK_W)) {
            //up hex
            if (shapeHex.location.y > 20) {
                shapeHex.setLocation(new Point(shapeHex.location.x, shapeHex.location.y - 1));
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_A)|| keyboard.keyDown(KeyEvent.VK_A)) {
            //left hex
            if (shapeHex.location.x > 5) {
                shapeHex.setLocation(new Point(shapeHex.location.x - 1, shapeHex.location.y));
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_S)|| keyboard.keyDown(KeyEvent.VK_S)) {
            //down hex
            if (shapeHex.location.y < SCREEN_H - 20) {
                shapeHex.setLocation(new Point(shapeHex.location.x, shapeHex.location.y + 1));
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_D)|| keyboard.keyDown(KeyEvent.VK_D)) {
            //right hex
            if (shapeHex.location.x < SCREEN_W - 17) {
                shapeHex.setLocation(new Point(shapeHex.location.x + 1, shapeHex.location.y));
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_Q) || (keyboard.keyDown(KeyEvent.VK_Q))) {
            //inq hex rotation
            if (shapeHex.rotation < 5f) {
                shapeHex.setRotation(shapeHex.rotation+.25f);
                System.out.println("ROTATION Q:" + shapeHex.rotation + "\n\n");
            }
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_E) ||(keyboard.keyDown(KeyEvent.VK_E))) {
            //decrs hex rotation
            if (shapeHex.rotation > -5f) {
                shapeHex.setRotation(shapeHex.rotation+-.25f);
               System.out.println("ROTATION E:" + shapeHex.rotation + "\n\n");
            }
        }
        if (keyboard.keyDown(KeyEvent.VK_SPACE)) {
            shapeHex.setRotation(-shapeHex.rotation);
            System.out.println("ROTATION Space:" + shapeHex.rotation + "\n\n");
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
        shapeSq.setLocation(new Point((int) tx, (int) ty));

        //world
        shapeTri.updateWorld();
        shapeHex.updateWorld();
        shapeSq.updateWorld();
    
    }
    /**
     * From Book unedited
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
