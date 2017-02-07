package objmatrixt;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static com.sun.glass.ui.Cursor.setVisible;
import java.awt.*;
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
    private static final int SCREEN_H = 480;
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
    
    private final Point point = new Point(0, 0);

    Canvas canvas;
    //
    private float tx, ty;
    private float vx, vy;
    private float rot, rotStep;
    private float scale, scaleStep;
    private float sx, sxStep;
    private float sy, syStep;
    private boolean doTranslate;
    private boolean doRotate;
    private boolean clockwise1;
    private boolean clockwise2;

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
              
        shapeSq = new VectorObject(1);
        doTranslate = true; //starts bounce
        
        shapeTri = new VectorObject(2);
        shapeHex = new VectorObject(3);

//       world = new Vector2f[shapeHex.objects.length];
//        world = new Vector2f[shapeSq.objects.length];
//        world = new Vector2f[shapeTri.objects.length];
        reset();
    }

    private void reset() {
        tx = SCREEN_W; // /2;
        ty = SCREEN_H; // /2;
        vx = vy = 2;
        rot = 0.0f;
        rotStep = (float) Math.toRadians(1.0);
        scale = 1.0f;
        scaleStep = 0.1f;
        sx = sy = 0.0f;
        sxStep = syStep = 0.01f;
        doRotate = doTranslate = false;

    }

    private void processInput() {
        keyboard.poll();
        mouse.poll();

        if (canvas != null) {
            if (point.x + 25 < 0) {
                point.x = canvas.getWidth() - 1;
            } else if (point.x > canvas.getWidth() - 1) {
                point.x = -25;
            }
            if (point.y + 25 < 0) {
                point.y = canvas.getHeight() - 1;
            } else if (canvas.getHeight() - 1 > point.y) {
            } else {
                point.y = -25;
            }
        }

        //Tringle 
        shapeTri.updatePoint(point);
        if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
            doRotate = !doRotate; //tri roate clockwise
            clockwise1 = true;
        }
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            //tri roate clockwise
            clockwise1 = true;
        }
        else {
            doRotate = false;
        }
        if (mouse.buttonDownOnce(MouseEvent.BUTTON2)) {
            doRotate = !doRotate; //tri roate counter
            clockwise1 = false;
        }
        if (mouse.buttonDown(MouseEvent.BUTTON2)) {
           //tri roate counter
            clockwise1 = false;
        }else {
            doRotate = false;
        }
        

        ////Square
        //None needed
        //// Hexagon
        if (keyboard.keyDownOnce(KeyEvent.VK_W)) {
            //up hex
            shapeHex.move(1, 0);
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_A)) {
            //left hex
            shapeHex.move(0, -1);
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_S)) {
            //down hex
            shapeHex.move(-1, 0);
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_D)) {
            //right hex
            shapeHex.move(0, 1);
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_Q)) {
            //inq hex rotation
            shapeHex.rotation++;
        }
        if (keyboard.keyDownOnce(KeyEvent.VK_E)) {
            //decrs hex rotation
            shapeHex.rotation--;
        }

        if (keyboard.keyDown(KeyEvent.VK_SPACE)) {
            //revert the hexagon direction
            clockwise2 = !clockwise2;
        }
        if (keyboard.keyDown(KeyEvent.VK_W)) {
            //up hex
            shapeHex.move(1, 0);
        }
        if (keyboard.keyDown(KeyEvent.VK_A)) {
            //left hex
            shapeHex.move(0, -1);
        }
        if (keyboard.keyDown(KeyEvent.VK_S)) {
            //down hex
            shapeHex.move(-1, 0);
        }
        if (keyboard.keyDown(KeyEvent.VK_D)) {
            //right hex
            shapeHex.move(0, 1);
        }
        if (keyboard.keyDown(KeyEvent.VK_Q)) {
            //inq hex rotation
            shapeHex.rotation++;
        }
        if (keyboard.keyDown(KeyEvent.VK_E)) {
            //decrs hex rotation
            shapeHex.rotation--;
        }
        
        
        
    }

    private void processObjects() {
        // copy data...    
//        for (int i = 0; i < polygon.length; ++i) {
//            world[i] = new Vector2f(polygon[i]);
//        }
        if (doRotate) {
            rot += rotStep;
            if (rot < 0.0f || rot > 2 * Math.PI) {
                rotStep = -rotStep;
            }
        }
        if (doTranslate) {
            tx += vx;
            if (tx < 0 || tx > SCREEN_W) {
                vx = -vx;
            }
            ty += vy;
            if (ty < 0 || ty > SCREEN_H) {
                vy = -vy;
            }
        }

//        for (int i = 0; i < world.length; ++i) {
//            world[i].rotate(rot);
//            world[i].translate(tx, ty);
//        }
    }

    private void render(Graphics g) {
        frameRate.calculate();
        
        shapeTri.render(g);
        shapeHex.render(g);
        shapeSq.render(g);
    }

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
