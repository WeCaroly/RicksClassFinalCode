package javapaint;

import Images.MoreKernels;
import Images.MyImageNew;
import java.awt.*;
import static java.awt.Color.BLACK;
import static java.awt.Color.BLUE;
import static java.awt.Color.GREEN;
import static java.awt.Color.RED;
import static java.awt.Color.WHITE;
import static java.awt.Color.black;
import java.awt.event.*;
import java.awt.image.*;
import javax.swing.*;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import javapaint.Tools.*;
import Images.MyImageNew;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.io.IOException;
import javafx.embed.swing.SwingFXUtils;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFileChooser;


/**
 * Create Controls the paint application
 *
 * @author carol_8wybosj
 */
public class Create extends JFrame implements Runnable {

    static MyImageNew outputImageJfx; // javafx
    static Image inputImageJfx; // javaFx
    
    public Image imagefile;
    public Image imagefileout =  new Image("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/CurvInUse.png");
    
    static java.util.List<MyImageNew> undoStack = new ArrayList<MyImageNew>();
    static java.util.List<MyImageNew> redoStack = new ArrayList<MyImageNew>();

    /**
     * stores a point of a line
     */
    public class PointPair {

        Point left;
        Point right;

        /**
         *
         * @param l left
         * @param r right
         */
        public PointPair(Point l, Point r) {
            left = l;
            right = r;
        }
    }
    private Boolean upload = false;
    private final FrameRate frameRate;
    private BufferStrategy bs;
    private volatile boolean running;
    private Thread gameThread;

    private Canvas canvas;
    private CreateInput mouse;
    private KeyboardInput keyboard;
    private final Point point = new Point(0, 0);

    FreeDraw fDraw;
    Line lDraw;
    RectangleShape rDraw;
    Rectangle rectangle;

    private boolean drawingLine;

    int current = 1;
    int actionCode = 1;
    JButton freeBrush; //1 code
    JButton lineBrush; //2 code
    JButton polyLine; //3 code
    JButton rectangleBrush; //4 code
    Point strRec, endRec, str, end;
    JButton RedB, GreenB, BlueB, BlackB;
    //added
    JButton uploadFile, ReduceColor, Blur, Magic, edgeDetector, Sharpen;
    JPanel pannelTool;
    Box buttonHolder;
    ///////////////
    boolean mouseDown = false;
    private String[] description;
    private final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.BLACK};
    private int colorIndex = 4;
    ///////////////////////////
    private MoreKernels mk;

    /**
     * Set Up of the create object
     */
    public Create() throws FileNotFoundException {

        frameRate = new FrameRate();
        pannelTool = new JPanel();
        buttonHolder = Box.createVerticalBox();
        mk = new MoreKernels();
        
       
        
        //ImageView inputImageJfx; 
        //outputImageJfx = new MyImageNew((int) inputImageJfx.getWidth(), (int) inputImageJfx.getHeight());;
        //outputImageJfx.copyFrom(inputImageJfx);
        freeBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/CurvInUse.png");
        lineBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/LineNotInUse.png");
        polyLine = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/PolyLineNotInUse.png");
        rectangleBrush = setButton("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/JavaPaint/src/javapaint/Images/RecNotInUse.png");
        RedB = setColorButton(1);
        GreenB = setColorButton(2);
        BlueB = setColorButton(3);
        BlackB = setColorButton(4);

        uploadFile = new JButton("Upload");
        uploadFile.setForeground(RED);

        ReduceColor = new JButton("Reduce Color");
        Blur = new JButton("Blur");
        Magic = new JButton("Magic");
        edgeDetector = new JButton("Edge Detector");
        Sharpen = new JButton("Sharpen");

        buttonHolder.add(freeBrush);
//        buttonHolder.add(lineBrush);
//        buttonHolder.add(polyLine);
        buttonHolder.add(rectangleBrush);

//        buttonHolder.add(RedB);
//        buttonHolder.add(GreenB);
//        buttonHolder.add(BlueB);
//        buttonHolder.add(BlackB);
        buttonHolder.add(uploadFile);
//        buttonHolder.add(ReduceColor);
//        buttonHolder.add(Blur);
//        buttonHolder.add(Magic);
//        buttonHolder.add(edgeDetector);
//        buttonHolder.add(Sharpen);

        pannelTool.add(buttonHolder);

        this.add(buttonHolder, BorderLayout.WEST);
        disableCursor();
        this.setVisible(true);

    }

    /**
     * Called so to change the icon on select
     *
     * @param p
     */
    public void changeIcon(int p) {
        switch (p) {
            case 1://freeForm //colorIndex
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));

                break;
            case 2://Lines
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));

                break;
            case 3: //Poly
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecNotInUse.png"));
                break;
            case 4://rec
                freeBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/CurvNotInUse.png"));
                lineBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/LineNotInUse.png"));
                polyLine.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/PolyLineNotInUse.png"));
                rectangleBrush.setIcon(new ImageIcon("C:/Users/carol_8wybosj/OneDrive/Documents/NetBeansProjects/Test/src/test/RecInUse.png"));
        }
    }

    /**
     * Setting of all tool buttons
     */
    private JButton setButton(String png) {
        JButton button = new JButton();
        Icon buttonIcon = new ImageIcon(png);
        button.setIcon(buttonIcon);
        //Add Listener to JButton
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object source = e.getSource();
                System.out.println("Event" + e.getSource());
                if (source == freeBrush) {
                    actionCode = 1;
                    str = null;
                    drawingLine = false;
                    changeIcon(actionCode);
                } else if (source == lineBrush) {
                    actionCode = 2;
                    drawingLine = false;
                    str = null;
                    changeIcon(actionCode);
                } else if (source == polyLine) {
                    actionCode = 3;
                    drawingLine = false;
                    str = null;
                    changeIcon(actionCode);
                } else if (source == rectangleBrush) {
                    actionCode = 4;
                    drawingLine = false;
                    str = null;
                    changeIcon(actionCode);
                } else if (source == uploadFile) {
                    upload = true;
                }

            }

            public void mouseReleased(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    mouseDown = false;
                }
            }
        });

        button.setBackground(WHITE);
        return button;
    }

    /**
     * Setting of all the colorButtons
     */
    private JButton setColorButton(int i) {
        JButton button = new JButton();

        //Add Listener to JButton
        button.addActionListener((ActionEvent e) -> {
            Object source = e.getSource();
            if (RedB == source) {
                colorIndex = 1;
            }
            if (GreenB == source) {
                colorIndex = 2;
                /* From book edited foe more buttons and GUI*/
            }
            if (BlueB == source) {
                colorIndex = 3;
            }
            if (BlackB == source) {
                colorIndex = 4;
            }
        });

        switch (i) {
            case 1:
                button.setBackground(RED);
                button.setSize(30, 20);
                break;
            case 2:
                button.setBackground(GREEN);
                button.setSize(30, 20);
                break;
            case 3:
                button.setBackground(BLUE);
                button.setSize(30, 20);
                break;
            case 4:
                button.setBackground(BLACK);
                button.setSize(30, 20);
                break;
            default:
                button.setBackground(black);
        }
        return button;
    }

    /**
     * From book edited foe more buttons and GUI
     */
    protected void createAndShowGUI() {
        canvas = new Canvas();
        canvas.setSize(1280, 720);
        canvas.setBackground(Color.WHITE);
        canvas.setIgnoreRepaint(true);

        getContentPane().add(canvas);
        setTitle("Java Paint");
        setIgnoreRepaint(true);
        pack();

        // Add key listeners
        keyboard = new KeyboardInput();
        canvas.addKeyListener(keyboard);

        // Add mouse listeners
        // For full screen : mouse = new RelativeMouseInput( this );
        mouse = new CreateInput(canvas);
        canvas.addMouseListener(mouse);
        canvas.addMouseMotionListener(mouse);

        //Mouse wheel listener 
        canvas.addMouseWheelListener(mouse);

        init();
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
    @Override
    public void run() {
        running = true;
        frameRate.initialize();
        while (running) {
            gameLoop();
        }
    }

    /**
     * ads canvas to the tool objects
     */
    public void init() {
        colorIndex = 1;
        fDraw = new FreeDraw(canvas);
        lDraw = new Line(canvas);
        rDraw = new RectangleShape(canvas);
    }

    /**
     * From Book unedited
     */
    private void gameLoop() {
        processInput();
        renderFrame();
        sleep(10L);
    }

    /**
     * From Book unedited
     */
    void renderFrame() {
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
     * From Book edited for other types of tools
     */
    private void processInput() {

        if (upload) {         
                    System.out.println("hit");
                    JFileChooser fileChooser = new JFileChooser();
                    int returnValue = fileChooser.showDialog(null, "Attach");
                    File file = fileChooser.getSelectedFile();
                    if (returnValue == JFileChooser.APPROVE_OPTION) {
                       //imagefile = new BufferedImage(file);
                       imagefileout = new Image(file.getName()); 
                       System.out.println("Attaching file: " + imagefile.toString());
                    }
            
            //imagefile = new BufferedImageLoader("javapaint/Images/what.jpg");
            imagefileout = imagefile;

            upload = false;
        }

        keyboard.poll();
        mouse.poll();
        Graphics g = bs.getDrawGraphics();
        g.setColor(COLORS[colorIndex - 1]);
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

        if (mouse.buttonDownOnce(MouseEvent.BUTTON1)) {
            if (str == null) {
                str = mouse.getPosition();
                if (actionCode == 2 || actionCode == 3) {
                    lDraw.setSTR(str);
                } else if (actionCode == 4) {
                    rDraw.setSTR(str);
                }
                drawingLine = true;
            } else {
                end = mouse.getPosition();

                //For line singel
                if (actionCode == 2 || actionCode == 3) {
                    lDraw.setEND(end);
                    lDraw.save(g);
                    if (actionCode == 3) {//will keep going until right click
                        str = end;
                        lDraw.setSTR(end);
                    }

                    //For Rectangle
                } else if (actionCode == 4) {
                    rDraw.setEND(end);
                    end = point.getLocation();
                    rDraw.save(g);
                }
                if (actionCode != 3) { // since it needs the end point dont null
                    drawingLine = false;
                    str = null;
                }
            }
            if (actionCode == 1) {
                fDraw.setArrayLines(null);
            }
        }

        // if the button is down, add line point only for free draw
        if (mouse.buttonDown(MouseEvent.BUTTON1)) {
            //Called Draw here for other file from book
        } else if (drawingLine) {
            //draw shape
            if (actionCode == 1) {
                fDraw.setArrayLines(null);
            }
        }

        if (mouse.buttonDownOnce(MouseEvent.BUTTON3)) {
            if (str != null && actionCode == 3) {
                end = mouse.getPosition();
                lDraw.setEND(end);
                lDraw.save(g);
                drawingLine = false;
                str = null;
            }

        }
        // if 'C' is down, clear the lines
        if (keyboard.keyDownOnce(KeyEvent.VK_C)) {
            clear();
        }

        Point p = mouse.getPosition();
        if (mouse.isRelative()) {
            point.x += p.x;
            point.y += p.y;
        } else {
            point.x = p.x;
            point.y = p.y;
        }
    }

    /**
     * From Book edited for scroll and rendering
     */
    private void render(Graphics g) {

        int notches = mouse.getNotches();
        if (notches < 0) { //neg up
            //change tool
            if (actionCode != 4) {
                actionCode += 1;
                changeIcon(actionCode);
            } else {
                actionCode = 1;
                changeIcon(actionCode);
            }
        } else if (notches > 0) { // pos down
            //change color
            if (colorIndex != 4) {
                colorIndex += 1;
            } else {
                colorIndex = 1;
            }
        }
        Color colors = COLORS[colorIndex - 1];
        g.setColor(colors);

        //add image
        //TODO: FIX
       // g.drawImage(imagefile, imagefile.getWidth(), imagefile.getHeight(), null);  
       BufferedImage Bimagefileout = null;
        Bimagefileout = SwingFXUtils.fromFXImage(imagefileout, null);
       //def as: SwingFXUtils.fromFXImage(imagefile, Bimagefileout)
       g.drawImage(Bimagefileout, Bimagefileout.getWidth(), Bimagefileout.getHeight(), null);
    
        // ImageView outputImageView = new ImageView(outputImageJfx);      
       
        frameRate.calculate();
        g.drawLine(point.x + 10, point.y, point.x, point.y);
        g.drawLine(point.x, point.y + 10, point.x, point.y);
        g.drawLine(point.x, point.y, point.x - 10, point.y);
        g.drawLine(point.x, point.y, point.x, point.y - 10);

        //Call Draw
        if (drawingLine) {
            Draw(g);
        }
        // processInput();

        //Old ArrayList
        fDraw.AddLines(g);
        lDraw.AddShapes(g);
        rDraw.AddShapes(g);
       
    }

    /**
     * Draws Shape to screen
     */
    private void Draw(Graphics g) {

        switch (actionCode) {
            case 1:
                fDraw.draw(g, mouse);
                break;

            case 2:
            case 3:
                g.drawLine(str.x, str.y, point.x, point.y);
                break;
            case 4:
                int xValue = Math.min(str.x, point.x);
                int yValue = Math.min(str.y, point.y);
                int width = Math.abs(str.x - point.x);
                int height = Math.abs(str.y - point.y);
                Rectangle rectangle = new Rectangle(xValue, yValue, width, height);
                g.drawRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
                break;
            default:
                System.out.print("Draw Error\n");
        }
    }

    /**
     * From Book unedited
     */
    private void disableCursor() {
        Toolkit tk = Toolkit.getDefaultToolkit();
        java.awt.Image image1 = tk.createImage("");
        Point points = new Point(0, 0);
        String name = "Pointer";
        Cursor cursor = tk.createCustomCursor(image1, points, name);
        setCursor(cursor);

    }

    /**
     * From Book unedited
     */
    protected void onWindowClosing() {
        try {
            running = false;
            gameThread.join();
        } catch (InterruptedException e) {
            System.out.println("Error " + e);
        }
        System.exit(0);
    }

    /**
     * Clears all arrays
     */
    protected void clear() {
        rDraw.clear();
        lDraw.clear();
        fDraw.clear();
        str = null;
        drawingLine = false;
    }
}
/**
 * try { Image img =
 * ImageIO.read(getClass().getResource("resources/water.bmp"));
 * button.setIcon(new ImageIcon(img)); } catch (IOException ex) {
 */
