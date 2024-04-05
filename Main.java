import java.net.*;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.text.*;
import javafx.stage.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.*;
import javafx.scene.paint.*;
import javafx.geometry.*;
import javafx.scene.image.*;

import java.io.*;

import java.util.*;
import java.text.*;
import java.io.*;
import java.lang.*;
import javafx.application.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.canvas.*;
import javafx.scene.paint.*;
import javafx.scene.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.animation.*;
import javafx.scene.control.*;
import javafx.scene.image.*;
import java.net.*;
import javafx.geometry.*;
import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

public class Main extends Application
{
   //create FlowPane
   FlowPane fp;
   
   //create Canvas
   Canvas theCanvas = new Canvas(600,600);
   
   //create Player
   Player thePlayer;
   
   //boolean for player existing
   boolean playerExist = false;
   
   //score variables
   Player secPlayer = new Player(300,300);
   int score;
   int highscore;
   
   //file variables
   Scanner scan;
   FileOutputStream fos;
   PrintWriter pw;
   
   //create AnimationHandler
   AnimationHandler ta = new AnimationHandler();

   public void start(Stage stage)
   {  
      //create player if it exists
      if(playerExist == false)
      {
         //create player
         thePlayer = new Player(300,300);
         
         //set score
         score = (int)thePlayer.distance(secPlayer);    
      }
      else if (playerExist == true)
      {
        //dont create player
        System.out.println("player doesn't exist");
      }
      
      //create file once game has been played once
      try 
      {
         //scanner to read in from file
         scan = new Scanner(new File("HighScoreFile.txt"));
         highscore = scan.nextInt();         
      }
      catch(FileNotFoundException fnfe)
      {
         System.out.println("File was not found");
         highscore = 0;
      }
      
      //initialize flowpane 
      fp = new FlowPane();
      
      //set graphics
      gc = theCanvas.getGraphicsContext2D();
      drawBackground(300,300,gc);
      
      //add to flowPane
      fp.getChildren().add(theCanvas);
      
      //setOnAction
      fp.setOnKeyPressed(new KeyListenerDown());
      fp.setOnKeyReleased(new KeyListenerUp());
      
      //start animationHandler
      ta.start();
      
      //set scene
      Scene scene = new Scene(fp, 600, 600);
      stage.setScene(scene);
      stage.setTitle("Project :)");
      stage.show();
      
      //request Focus
      fp.requestFocus();
   }
   
   //background variables
   GraphicsContext gc;
   Image background = new Image("stars.png");
   Image overlay = new Image("starsoverlay.png");
   Random backgroundRand = new Random();
   
   //drawing background
   public void drawBackground(float playerx, float playery, GraphicsContext gc)
   {
	  //re-scale player position to make the background move slower. 
      playerx*=.1;
      playery*=.1;
   
	   //figuring out the tile's position.
      float x = (playerx) / 400;
      float y = (playery) / 400;
      
      int xi = (int) x;
      int yi = (int) y;
      
	   //draw a certain amount of the tiled images
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(background,-playerx+i*400,-playery+j*400);
         }
      }
      
	  //below repeats with an overlay image
      playerx*=2f;
      playery*=2f;
   
      x = (playerx) / 400;
      y = (playery) / 400;
      
      xi = (int) x;
      yi = (int) y;
      
      for(int i=xi-3;i<xi+3;i++)
      {
         for(int j=yi-3;j<yi+3;j++)
         {
            gc.drawImage(overlay,-playerx+i*400,-playery+j*400);
         }
      }
   }
   
   //create KeyListenerDown (pressed)
   public class KeyListenerDown implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         
         if (event.getCode() == KeyCode.A) 
         {
             //set true
             thePlayer.setLeft(true);
         } 
         if (event.getCode() == KeyCode.W)
         {
             //set true
             thePlayer.setUp(true);
         }
         if (event.getCode() == KeyCode.D)
         {
             //set true
             thePlayer.setRight(true);
         }
         if (event.getCode() == KeyCode.S)
         {
             //set true
             thePlayer.setDown(true);  
         }
      }
   }
   
   //create KeyListenerUp (released)
   public class KeyListenerUp implements EventHandler<KeyEvent>  
   {
      public void handle(KeyEvent event) 
      { 
         if (event.getCode() == KeyCode.A) 
         {
             //set false
             thePlayer.setLeft(false);
         } 
         if (event.getCode() == KeyCode.W)
         {
             //set false
             thePlayer.setUp(false);
         }
         if (event.getCode() == KeyCode.D)
         {
             //set false
             thePlayer.setRight(false);
         }
         if (event.getCode() == KeyCode.S)
         {
             //set false
             thePlayer.setDown(false);  
         }
      }
   }
   
   //mine variables
   int n;
   int cgridx = 3;
   int cgridy = 3;
   int lastCgridx = 3;
   int lastCgridy = 3;
   Random randNum = new Random();
   ArrayList<Mine> mineList = new ArrayList<Mine>();
   
   //createMines method
   public void createMines(int startCgridx, int startCgridy)
   {
      //read in arguments
      int newCgridx = startCgridx;
      int newCgridy = startCgridy;
      
      //compute N
      double distance = Math.sqrt(((newCgridx*100)-300)*((newCgridx*100)-300)+((newCgridy*100)-300)*((newCgridy*100)-300));
      n = (int)distance/1000;

      //create mines based on N
      for (int i = 0; i < n; i++)
      {
         //30% chance for creating mines
         float rand = randNum.nextFloat(2);
         if (rand < .3) 
         {
            //create a mine object at a random position in square
            float randX = randNum.nextFloat(100);
            float randY = randNum.nextFloat(100);
            Mine newM = new Mine((newCgridx*100)+randX,(newCgridy*100)+randY);
            
            //add mine to arraylist
            mineList.add(newM);  
         }   
      }
   }
   
   //create animationHandler
   public class AnimationHandler extends AnimationTimer
   {
      public void handle(long currentTimeInNanoSeconds) 
      {
         gc.clearRect(0,0,600,600);
         
         //USE THIS CALL ONCE YOU HAVE A PLAYER
         drawBackground(thePlayer.getX(),thePlayer.getY(),gc);
         
         //if player exists then draw
         if (playerExist == false)
         {   
            //implement act method
            thePlayer.act();
            
   	      //example calls of draw - this should be the player's call for draw
            thePlayer.draw(300,300,gc,true); //all other objects will use false in the parameter.
         }   
               
         //draw score & highscore 
         thePlayer.drawScore(300, 300, gc);
         
         //update score
         Player secPlayer = new Player(300,300);
         score = (int)thePlayer.distance(secPlayer);
         thePlayer.setScore(score);
         
         //update highscore
         if (score > highscore)
         {
            highscore = score;
         }
         
         //try writing to highscore file
         try
         {
            //write to file highscore
            fos = new FileOutputStream("HighScoreFile.txt", false);
            pw = new PrintWriter(fos);
            pw.println(highscore);
            pw.close();
         }
         catch(FileNotFoundException fnfe)
         {
            System.out.println("File not found");
         }
         
         //set highscore
         thePlayer.setHighScore(highscore);
         
         //create grid
         cgridx = ((int)thePlayer.getX())/100;
         cgridy = ((int)thePlayer.getY())/100;
         
         //place mines:
         if ((lastCgridy != cgridy) || (lastCgridx != cgridx))
         {
            //top row of grid
            int topCornerX = cgridx-5;
            int topCornerY = cgridy-5;
         
            for (int i = 0; i < 9; i++)
            {
               //increment x
               int newTopCornerX = topCornerX+i;
                           
               //create mines
               createMines(newTopCornerX, topCornerY);
            }
            
            //bottom row of grid
            int bottomCornerX = cgridx-5;
            int bottomCornerY = cgridy+4;
   
            for (int i = 0; i < 9; i++)
            {
               //increment x
               int newBottomCornerX = bottomCornerX+i;
               
               //create mines
               createMines(newBottomCornerX, bottomCornerY); 
            }
            
            //left column of grid
            int leftCornerX = cgridx-5;
            int leftCornerY = cgridy-5;
            
            for (int i = 1; i < 9; i++)
            {
               //increment y
               int newLeftCornerY = leftCornerY+i;
               
               //create mines
               createMines(topCornerX, newLeftCornerY);
            }
            
            //right column of grid
            int rightTopCornerX = cgridx+4;
            int rightTopCornerY = cgridy-5;
            
            for (int i = 1; i < 9; i++)
            {
               //increment y
               int newRightTopCornerY = rightTopCornerY+i;
               
               //create mines
               createMines(rightTopCornerX, newRightTopCornerY);
            }
           
            //update last positions
            lastCgridy = ((int)thePlayer.getY())/100;
            lastCgridx = ((int)thePlayer.getX())/100; 
         } 
         
         //check for collision
         for (int i = 0; i < mineList.size(); i++)
         {
            Mine currentMine = mineList.get(i);
            
            //get distance between mine and player
            double newDistance = thePlayer.distance(currentMine);
            
            //if equal to or less than 20 there is a collision 
            if (newDistance <= 20)
            {
               playerExist = true;
               
               //remove offending mine
               mineList.remove(currentMine);
            }
         }

         //run through arrayList and draw mines
         for (int j = 0; j < mineList.size(); j++)
         {
            mineList.get(j).draw(thePlayer.getX(),thePlayer.getY(),gc,false);   
         }
         
         //run through arrayList and remove mines based on distance 
         for (int i = 0; i < mineList.size(); i++)
         {
            Mine currentMine = mineList.get(i);
            
            //get distance between mine and player
            double newDistance = thePlayer.distance(currentMine);
            
            //if >= 800 then remove mines
            if (newDistance >= 800)
            {
               mineList.remove(currentMine);
            }
         }
         
      }
   }
   
   //launch main
   public static void main(String[] args)
   {
      launch(args);
   }
}

