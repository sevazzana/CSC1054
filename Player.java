import javafx.scene.paint.*;
import javafx.scene.canvas.*;

public class Player extends DrawableObject
{
	//constructor --> takes in its position
   public Player(float x, float y)
   {
      super(x,y);
   }
   
   //drawMe method --> draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      //player
      gc.setFill(Color.BLACK);
      gc.fillOval(x-14,y-14,27,27);
      gc.setFill(Color.GRAY);
      gc.fillOval(x-13,y-13,25,25);
      
      //player
      gc.setFill(Color.BLACK);
      gc.fillOval(x-6,y-6,10,10);
      gc.setFill(Color.GREEN);
      gc.fillOval(x-5,y-5,8,8);
   }
   
   public void drawScore(float x, float y, GraphicsContext gc)
   {
      //text for score
      gc.setFill(Color.WHITE);
      gc.fillText("Score is: "+score, 5,20);
      gc.fillText("High Score is: "+highscore, 5, 50);
   }

   //instance variables
   private double yForce = 0;
   private double xForce = 0;
   private boolean down = false;
   private boolean up = false;
   private boolean right = false;
   private boolean left = false;
   private int score = 0;
   private int highscore = 0;

   //accessors
   public double getYForce()
   {
      return yForce;   
   }
   
   public double getXForce()
   {
      return xForce;
   }
   
   //mutators
   public void setDown(boolean newDown)
   {
      down = newDown;
   }
   public void setUp(boolean newUp)
   {
      up = newUp;
   }
   public void setRight(boolean newRight)
   {
      right = newRight;
   }
   public void setLeft(boolean newLeft)
   {
      left = newLeft;
   }
   public void setScore(int newScore)
   {
      score = newScore;
   }
   public void setHighScore(int newHighScore)
   {
      highscore = newHighScore;
   }
   public int getHighScore()
   {
      return highscore;
   }
   
   //act method --> calculations and moving
   public void act()
   {
      // force calculations
      if (up == true)
      {
         yForce = yForce + (-0.1);
      }
      if (down == true)
      { 
         yForce = yForce+ (0.1);
      }
      if (right == true)
      {
         xForce = xForce+ (0.1);
      }
      if (left == true)
      {
         xForce = xForce+ (-0.1);
      }
      
      //restrictions on xForce
      if (xForce < -5)
      {
         xForce = -5;
      }
      if (xForce > 5)
      {
         xForce = 5;
      }
      
      //restrictions on yForce
      if (yForce < -5)
      {
         yForce = -5;
      }
      if (yForce > 5)
      {
         yForce = 5;
      }
      
      //reduction calculations
      if (up == false && down == false)
      {
         //restrictions
         if (-0.25 < yForce && yForce < 0.25)
         {
            yForce = 0;
         }
         else if (yForce < 0.25)
         {
            yForce += .025;   
         } 
         else if (yForce > -0.25)
         {
            yForce -= .025; 
         }   
      }
      
      if (left == false && right == false)
      {
         //restrictions
         if (-0.25 < xForce && xForce < 0.25)
         {
            xForce = 0;
         }
         else if (xForce < 0.25)
         {
            xForce += .025;   
         } 
         else if (xForce > -0.25)
         {
            xForce -= .025; 
         }  
      }
      
      //set x and y
      setX(getX()+(float)xForce);
      setY(getY()+(float)yForce);          
   }
}
