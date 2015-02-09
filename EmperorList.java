/* EmperorList
   An object which contains a list of strings representing all Roman Emperors up to a given year (inclusively).
   Makes use of text files years.txt and emperors.txt to calculate the number of emperors to include and to populate the list, respectively.
*/
import java.io.*;
import java.util.*;

public class EmperorList
{
   private String[]emperors;
   
   //Constructor
   public EmperorList(int finalYr)
   {
      emperors=new String[empCount(finalYr)];
      populateList();
   }
   
   //Updates count of emperors to include from emperors.txt by comparing against years in years.txt.
   private int empCount(int finalYr)
   {
       String token = "";
       
       //The first entry in years.txt is 14, the accession year of Tiberius (the second Emperor).
       int empCount=1;

       try 
       {
          Scanner inFile = new Scanner(new File("years.txt"));
          token=inFile.next();
          
          // While loop for reading, terminates once either the final entry or a year later than the input is reached.
          while ((Integer.parseInt(token)!=-1)&&(Integer.parseInt(token)<=finalYr))
          {
            empCount++;
            token=inFile.next();
          }
          inFile.close();
       }
       catch (FileNotFoundException ex){}

       return empCount;
   }
   
   //Fills array with strings from emperors.txt.
   private void populateList()
   {
       try 
       {
          Scanner inFile1 = new Scanner(new File("emperors.txt"));
          
          for (int i=0;i<emperors.length;i++)
          {
            emperors[i]=inFile1.next();
          }
          inFile1.close();
       }
       catch (FileNotFoundException ex){}
   }
   
   //Getter methods
   public int getLength()
   {
      return emperors.length;
   }
   
   public String getEmperor(int num)
   {
      return emperors[num];
   }
   
   public void setEmperor(int num, String newEmp)
   {
      emperors[num]=newEmp;
   }
      
}