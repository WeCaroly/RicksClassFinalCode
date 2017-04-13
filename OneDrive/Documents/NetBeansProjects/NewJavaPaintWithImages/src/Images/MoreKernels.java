package Images;

import java.awt.Color;
import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

public class MoreKernels extends Application {
    
    static Image inputImageJfx;
    static MyImageNew outputImageJfx;
    
    static List<MyImageNew> undoStack = new ArrayList<MyImageNew>();
    static List<MyImageNew> redoStack = new ArrayList<MyImageNew>();
     ImageView outputImageView = new ImageView(outputImageJfx);
      

    public static void main(String[] args) throws Exception{
        
         System.out.println("Working Directory = " +
              System.getProperty("user.dir"));
        
        inputImageJfx = new Image(new FileInputStream(System.getProperty("user.home") + "/Desktop/what.jpg"));
        
        outputImageJfx = new MyImageNew((int)inputImageJfx.getWidth(), (int)inputImageJfx.getHeight());;
        
        outputImageJfx.copyFrom(inputImageJfx);
        
        
        
        launch(args);
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        //ImageView inputImageView = new ImageView(inputImageJfx);
       
        VBox menuPane = new VBox();
        
        Button undoButton = new Button("Undo");
        undoButton.setOnAction(
                    (a)->{
                      
                if(undoStack.size() > 0)
                {
                    MyImageNew previousImage = undoStack.get(undoStack.size() - 1);
                    undoStack.remove(undoStack.size() - 1);
                    
                    redoStack.add(outputImageJfx);
                    
                    outputImageJfx = previousImage;
                            
                            
                    outputImageView.setImage(outputImageJfx);
                    
                }
        });
        menuPane.getChildren().add(undoButton);
        
        Button redoButton = new Button("Redo");
        redoButton.setOnAction(
                    (a)->{
                if(redoStack.size() > 0)
                {
                    MyImageNew previousImage = redoStack.get(redoStack.size() - 1);
                    redoStack.remove(redoStack.size() - 1);
                    
                    undoStack.add(outputImageJfx);
                    
                    outputImageJfx = previousImage;
                            
                            
                    outputImageView.setImage(outputImageJfx);
                    
                    
                }
      
        });
        menuPane.getChildren().add(redoButton);
        
        menuPane.getChildren().add(new Separator());
        
        
        for(Method method : outputImageJfx.getClass().getDeclaredMethods())
        {
            if (!Modifier.isPublic(method.getModifiers())) {
                continue;
            }
            
            
            Button button = new Button(method.getName());
            button.setOnAction(
                    (ActionEvent a)->{
                                                   
                           DoWithUndo(()->{
                               try {
                                    method.invoke(outputImageJfx, new Object[]{1});
                               } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ex) {
                                   Logger.getLogger(MoreKernels.class.getName()).log(Level.SEVERE, null, ex);
                               }
                           });
                    });
                        
            menuPane.getChildren().add(button);
        }
        
        menuPane.getChildren().add(new Separator());
        
        Button reduceButton = new Button("Reduce Colors");
        reduceButton.setOnAction(a->DoWithUndo(()->outputImageJfx.reduceColors(8))); 
        
        reduceButton.setOnAction(a->DoWithUndo(()->outputImageJfx.reduceColors(16)));
        menuPane.getChildren().add(reduceButton);
        
        
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setStyle("-fx-background-color: DAE6F3;");
        root.setCenter(outputImageView);
        
        
        root.setLeft(menuPane);
        
        Canvas canvas = new Canvas(100, 256);
        menuPane.getChildren().add(canvas);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        drawHistogram(gc);
        
        Scene scene = new Scene(root, inputImageJfx.getWidth() + 400, inputImageJfx.getHeight() + 20 );
        
        primaryStage.setTitle("Duplicates");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
    
    private void DoWithUndo(Runnable lamda){
    
         MyImageNew newImage = new MyImageNew((int)inputImageJfx.getWidth(), (int)inputImageJfx.getHeight());

                newImage.copyFrom(outputImageJfx);

                undoStack.add(outputImageJfx);

                redoStack.clear();

                outputImageJfx = newImage;

                outputImageJfx.reduceColors(8);

                lamda.run();
                outputImageView.setImage(outputImageJfx);
    }

    private void drawHistogram(GraphicsContext gc) {
        //Jfx uses like javascript canvas
        
        
       // gc.setFill(Color.WHITE);
        gc.fillRect(0,0,100,256);
        
        
        int[] buckets = new int [256]; //bin 
        
        for(int x=0;x<outputImageJfx.getHeight(); x++){
            
            for(int y=0;y<outputImageJfx.getWidth(); y++){
             javafx.scene.paint.Color myColor = outputImageJfx.getPixelReader().getColor(x,y);
             double grayscale = myColor.grayscale().getBlue();
       //      buckets[(int)(grayscale*255)];
           
            }
        }
       // gc.setStroke(Color.Black);
        int max=0;
        for(int i =0; i<buckets.length;i++){
            if(buckets[i]>max)
               max=buckets[i];
        }
        for(int x=0;x<256;x++){
        gc.strokeLine(0, x, buckets[x]/(float)max*100, x);
        }
    }
}