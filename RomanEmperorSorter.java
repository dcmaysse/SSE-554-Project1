/* RomanEmperorSorter
   This program can be broken down into four main functions:
      1. Prompts user for a year and constructs an EmperorList object based on it
      2. Randomizes the EmperorList
      3. Performs a Bottom-up (Iterative) MergeSort, with user input serving as comparator
      4. Prints the final ordered list
   Code for selectAndSort, copy, and merge adapted from http://algs4.cs.princeton.edu/lectures/22Mergesort.pdf.
*/
import java.util.*;

public class RomanEmperorSorter
{
   private static EmperorList emperors;
   private static Scanner s=new Scanner(System.in);
   
   public static void main(String[]args)
   {
      System.out.println("Welcome to the Roman Emperor Sorter!");
      System.out.println("Include emperors to the end of what year (CE)?\n(Note: Year must be at least 14, when Tiberius, the second emperor, ascended to the throne.)");
      finalYearPrompt();
      randomize();
      selectAndSort();
      printRank();
   }
   
   //Promopts the user for int to create EmperorList object and handles erroneous inputs.
   private static void finalYearPrompt()
   {
      //Handles cases where input is not an integer.
      while (!s.hasNextInt())
      {
         System.out.println("Not a valid year, try again.");
         s.nextLine();
      }
      int finalYr=s.nextInt();
      
      //Negative numbers are invalid and there would only be 1 emperor for comparison between 0 and 14 CE.
      if (finalYr>=0&&finalYr<14)
      {
         System.out.println("Too early! Please enter a year after 14 CE.");
         finalYearPrompt();
      }
      
      //Handles negative number input.
      else if (finalYr<0)
      {
         System.out.println("Not a valid year, try again.");
         finalYearPrompt();
      }
      
      //Initializes EmperorList if input is valid.
      else
         emperors= new EmperorList(finalYr);
   }
   
   //Randomizes the EmperorList by generating two random indexes of the EmperorList and swapping their contents.
   private static void randomize()
   {
      String temp;
      Random r=new Random();
      for (int i=0;i<500;i++)
      {
         int switch1=r.nextInt(emperors.getLength());
         int switch2=r.nextInt(emperors.getLength());
         
         //Ensures that an item is not switched with itself.
         while (switch1==switch2)
            switch1=r.nextInt(emperors.getLength());
            
         temp=emperors.getEmperor(switch1);
         emperors.setEmperor(switch1,emperors.getEmperor(switch2));
         emperors.setEmperor(switch2,temp);
      }
   }
   
	// Bottom-up merge sort.
	private static void selectAndSort()
   {
      int N = emperors.getLength();
      String[] aux = new String[N];
      
      //Nested loop which divides the array into chunks of size sz for merging.
      for (int sz = 1; sz < N; sz = sz+sz)
         for (int lo = 0; lo < N-sz; lo += sz+sz)
            copy(aux, lo, lo+sz-1, Math.min(lo+sz+sz-1, N-1));
   }
   
   //Copies elements from the subarrays into the helper array.   
   private static void copy(String[] aux, int lo, int mid, int hi)
   {
      for (int k = lo; k <= hi; k++)
         aux[k] = emperors.getEmperor(k);
      int i = lo, j = mid+1, k=lo;
      sort(aux,lo,mid,hi,i,j,k);
   }
   
   //Performs the actual sorting of the subarrays. In most implementations it is combined with the previous
      //method. However, for simplicity and because of a recursive call, it is its own method.
   private static void sort(String[]aux, int lo, int mid, int hi, int i, int j, int k)
   {
      while (k <= hi)
      {
         //If the left half is sorted, the rest of the right half will be copied over.
         if (i > mid)
         {
            emperors.setEmperor(k,aux[j++]);
            k++;
         }
         
         //If the right half is sorted, the rest of the left half will be copied over.
         else if (j > hi)
         {
            emperors.setEmperor(k,aux[i++]);
            k++;
         }
         
         //Handles comparison cases.
         else
         {
            System.out.println("Who wore the purple better, A) "+aux[i]+" or B) "+aux[j]+"?");
            String answer=s.next();
            if (answer.equals("A"))
            {
               emperors.setEmperor(k,aux[i++]);
               k++;
            }
            else if (answer.equals("B"))
            {
               emperors.setEmperor(k,aux[i++]);
               k++;
            }
            
            //The recursive call which caused the split from copy.
            else
            {
               System.out.println("Invalid input, try again.");
               sort(aux,lo,mid,hi,i,j,k);
            }
         } 
      }
   }
   
   //Prints the EmperorList in ranked form.   
   private static void printRank()
   {
      System.out.println("Final Ranking:");
      for (int i=0; i<emperors.getLength(); i++)
      {
         System.out.println((i+1)+") "+emperors.getEmperor(i));
      }
    }   
}