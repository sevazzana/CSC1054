import javafx.scene.paint.*;
import javafx.scene.canvas.*;
import java.io.*;
import java.util.*;

//this is an example object
public class Mine extends DrawableObject
{
	//instance variables
   float colorValue;
   int way;
   Random randValue;
   
   //takes in its position
   public Mine(float x, float y)
   {
      super(x,y);
      randValue = new Random();
      
      //set up interpolating variables
      colorValue = randValue.nextFloat();
      way = randValue.nextInt(2);
      if (way == 0)
      {
         way = 1;
      }
   }
   
   //draws itself at the passed in x and y.
   public void drawMe(float x, float y, GraphicsContext gc)
   {
      //determine colorValue
      colorValue += 0.05*way;
     
      if(colorValue > 1)
      {
         colorValue = 1; 
         way = -1;   
      }
      if(colorValue < 0)
      {
         colorValue = 0; 
         way = 1;   
      }
      
      //interpolate colors
      gc.setFill(Color.RED.interpolate(Color.WHITE, colorValue)); 
      gc.fillOval(x-6,y-6,11,11);
   }
}
