import org.junit.Assert;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;


public class EmperorListTest
{

   @Test
   public void Constructor_When_YearIsGreaterThanOrEqualToAccessionYear_Expect_EmperorIsAdded()
   {
      EmperorList emps=new EmperorList(12);
      emps=new EmperorList(1448);
      assertEquals(164,emps.getLength());
      emps=new EmperorList(1449);
      assertEquals(165,emps.getLength());
      emps=new EmperorList(1453);
      assertEquals(165,emps.getLength());
   }
   
   @Test
   public void Constructor_When_ListIsBuilt_Expect_CorrectStringsFromFileAdded()
   {
      EmperorList emps=new EmperorList(37);
      assertEquals("Augustus",emps.getEmperor(0));
      assertEquals("Tiberius",emps.getEmperor(1));
      assertEquals("Caligula",emps.getEmperor(2));
   }

}
