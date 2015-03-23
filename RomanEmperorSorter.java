import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class RomanEmperorSorter implements ActionListener
{
   JFrame frame=new JFrame("Roman Emperor Sorter");
   JPanel container=new JPanel();
   //CardLayout cl=new CardLayout();
   
   GridLayout gl=new GridLayout(1,2);   
   JPanel selectScreen=new JPanel();
   JLabel introText=new JLabel("<html><center>Welcome to the Roman Emperor Sorter!<br>Please choose how you want to select the emperors to be included.</center></html>");
   JTabbedPane methodPane=new JTabbedPane();
   JPanel yearPanel=new JPanel();
   JLabel yearText=new JLabel("<html><center>Include emperors up to the end of what year?<br>(Note: Year must be at least 14 CE.)</center></html>");
   JTextField yearField=new JTextField();
   JPanel yearSubmitPanel=new JPanel();
   JButton submitButton=new JButton("Submit");
   JLabel errorText=new JLabel("Not a valid year, please try again.");
   JPanel eraPanel=new JPanel();
   JLabel eraText=new JLabel("Include emperors up to the end of what era?");
   String[]eras={"Julio-Claudian Dynasty (68)","Year of the Four Emperors (69)","Flavian Dynasty (96)","Nerva-Antonine Dynasty (192)","Year of the Five Emperors (193)",
      "Severan Dynasty (235)","Crisis of the 3rd Century (285)","Tetrarchy (324)","Constantinian Dynasty (363)","Valentinian Dynasty (392)","Theodosian Dynasty (457)",
      "Fall of the West (476)","Leonid Dynasty (518)","Justinian Dynasty (602)","Heraclian Dynasty (695)","20 Years' Anarchy (717)","Isaurian Dynasty (802)","Nikephorian Dynasty (813)",
      "Amorian Dynasty (867)","Macedonian Dynasty (1056)","Doukid Dynasty (1081)","Komnenid Dynasty (1185)","Angelid Dynasty (1204)","Laskarid Dynasty (1261)","Fall of Constantinople (1453)"};
   JComboBox eraBox=new JComboBox(eras);
   JButton submitButton2=new JButton("Submit");
   
   JPanel askScreen=new JPanel();
   JLabel question=new JLabel("Who wore the purple better?");
   JPanel choices=new JPanel();
   JButton choiceA=new JButton();
   JButton choiceB=new JButton();
   
   JPanel resultScreen=new JPanel();
   JLabel resultText=new JLabel("Here is your final ranking.");
   JScrollPane scroller=new JScrollPane();
   JButton returnButton=new JButton("Return");
   
   private static EmperorList emperors;
   private int N,sz,lo,mid,hi,i,j,k;
   private int boxYear=68;
   private String[] aux;
   private boolean continueFlag=true;
   private int[]eraYears={68,69,96,192,193,235,285,324,363,392,457,476,518,602,695,717,802,813,867,1056,1081,1185,1204,1261,1453};
   
   public RomanEmperorSorter()
   {      
      //container.setLayout(cl);
      gl.setHgap(40);
      selectScreen.setLayout(new BoxLayout(selectScreen,BoxLayout.PAGE_AXIS));
      askScreen.setLayout(new BoxLayout(askScreen,BoxLayout.PAGE_AXIS));
      choices.setLayout(gl);
      resultScreen.setLayout(new BoxLayout(resultScreen,BoxLayout.PAGE_AXIS));
      yearPanel.setLayout(new BorderLayout());
      yearSubmitPanel.setLayout(new BorderLayout());
      eraPanel.setLayout(new BorderLayout());
      
      selectScreen.add(introText);
      introText.setAlignmentX(Component.CENTER_ALIGNMENT);
      selectScreen.add(Box.createRigidArea(new Dimension(0,15)));
      selectScreen.add(methodPane);
      methodPane.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      methodPane.addTab("By Year",yearPanel);
      yearPanel.add(yearText,BorderLayout.NORTH);
      yearText.setHorizontalAlignment(JLabel.CENTER);
      //yearPanel.add(Box.createRigidArea(new Dimension(0,15)));
      yearPanel.add(yearField,BorderLayout.CENTER);
      //yearField.setMaximumSize(new Dimension(Integer.MAX_VALUE,yearField.getPreferredSize().height));
      //yearPanel.add(Box.createRigidArea(new Dimension(0,15)));
      yearPanel.add(yearSubmitPanel,BorderLayout.SOUTH);
      yearSubmitPanel.add(submitButton,BorderLayout.CENTER);
      submitButton.addActionListener(this);
      submitButton.setAlignmentX(SwingConstants.CENTER);
      yearSubmitPanel.add(errorText,BorderLayout.SOUTH);
      errorText.setHorizontalAlignment(JLabel.CENTER);
      errorText.setVisible(false);
      
      methodPane.addTab("By Era",eraPanel);
      eraPanel.add(eraText,BorderLayout.NORTH);
      eraText.setHorizontalAlignment(JLabel.CENTER);
      //eraPanel.add(Box.createRigidArea(new Dimension(0,15)));
      eraPanel.add(eraBox,BorderLayout.CENTER);
      eraBox.setAlignmentX(SwingConstants.CENTER);
      eraBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String eraChoice=(String)eraBox.getSelectedItem();
                for (int i=0;i<eras.length;i++)
                {
                  if (eraChoice.equals(eras[i]))
                  {
                     boxYear=eraYears[i];
                     break;
                  }
                }
            }});
      //eraPanel.add(Box.createRigidArea(new Dimension(0,15)));
      eraPanel.add(submitButton2,BorderLayout.SOUTH);
      submitButton2.setAlignmentX(SwingConstants.CENTER);
      submitButton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
               emperors=new EmperorList(boxYear);
               randomize();
               
               N=emperors.getLength();
               sz=1;
               lo=0;
               mid=lo+sz-1;
               hi=Math.min(lo+sz+sz-1, N-1);
               i = lo;
               j = mid+1;
               k=lo;
               
               aux=new String[N];
               for (int k = lo; k <= hi; k++)
                  aux[k] = emperors.getEmperor(k);
                  
               choiceA.setText(emperors.getEmperor(0));
               choiceB.setText(emperors.getEmperor(1));
               
               container.add(askScreen);
               container.remove(selectScreen);
               frame.pack();
            }});
      
      askScreen.add(question);
      question.setAlignmentX(Component.CENTER_ALIGNMENT);
      askScreen.add(Box.createRigidArea(new Dimension(0,15)));
      askScreen.add(choices);
      askScreen.setPreferredSize(selectScreen.getPreferredSize());
      
      //choices.add(Box.createHorizontalGlue());
      choices.add(choiceA);
      choiceA.addActionListener(this);
      //choices.add(Box.createRigidArea(new Dimension(25,0)));
      choices.add(choiceB);
      choiceB.addActionListener(this);
      //choices.add(Box.createHorizontalGlue());
      
      resultScreen.setPreferredSize(new Dimension(250,600));
      resultScreen.add(resultText);
      resultText.setAlignmentX(Component.CENTER_ALIGNMENT);
      resultScreen.add(Box.createRigidArea(new Dimension(0,15)));
      resultScreen.add(scroller);
      scroller.setAlignmentX(Component.CENTER_ALIGNMENT);
      resultScreen.add(Box.createRigidArea(new Dimension(0,15)));
      resultScreen.add(returnButton);
      returnButton.addActionListener(this);
      returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);
      
      container.add(selectScreen);
      /*container.add(askScreen,"2");
      container.add(resultScreen,"3");*/
      //cl.show(container,"1");
      
      frame.add(container);
      frame.setResizable(false);
      frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.pack();
		frame.setVisible(true); 
            
   }
   
   public void actionPerformed(ActionEvent e)
   {
      if (e.getSource()==submitButton)
      {
   		String year=yearField.getText();
         if (isValidYear(year))
         {
            emperors=new EmperorList(Integer.parseInt(year));
            randomize();
            
            N=emperors.getLength();
            sz=1;
            lo=0;
            mid=lo+sz-1;
            hi=Math.min(lo+sz+sz-1, N-1);
            i = lo;
            j = mid+1;
            k=lo;
            
            aux=new String[N];
            for (int k = lo; k <= hi; k++)
               aux[k] = emperors.getEmperor(k);
               
            choiceA.setText(emperors.getEmperor(0));
            choiceB.setText(emperors.getEmperor(1));
            
            container.add(askScreen);
            container.remove(selectScreen);
            frame.pack();
            //cl.show(container,"2");
            errorText.setVisible(false);
            yearField.setText("");
         }
         else
         {
   		   errorText.setVisible(true);
            frame.pack();
         }
      }
      
      else if (e.getSource()==choiceA)
      {
         if (k>hi)
            loopResolve();
         else
         {
            emperors.setEmperor(k,aux[i++]);
            k++;
            blockResolve();
         }
         
         if (k>hi)
            loopResolve();
         if (continueFlag)
         {
            choiceA.setText(aux[i]);
            choiceB.setText(aux[j]);
         }
            
   	}
      
      else if (e.getSource()==choiceB)
      {
         if (k>hi)
            loopResolve();
         else
         {
            choiceA.setText(aux[i]);
            choiceB.setText(aux[j]);
            emperors.setEmperor(k,aux[j++]);
            k++;
            blockResolve();
         }
         
         if (k>hi)
            loopResolve();
         if (continueFlag)
         {
            choiceA.setText(aux[i]);
            choiceB.setText(aux[j]);
         }
      }
      
      else if (e.getSource()==returnButton)
      {
         continueFlag=true;
         container.add(selectScreen);
         container.remove(resultScreen);
         frame.pack();
         //cl.show(container,"1");
         yearField.requestFocusInWindow();
      }
   }
   
   protected static boolean isValidYear(String year)
   {
      Scanner s=new Scanner(year);
      boolean isValidYear=true;
      if (!s.hasNext())
         return false;
      while (s.hasNext()&&isValidYear==true)
      {
         isValidYear=s.hasNextInt();
         s.next();
      }
      if (isValidYear==true&&Integer.parseInt(year)<14)
      {
         isValidYear=false;
      }
      return isValidYear;
   }
   
   //Randomizes the EmperorList by generating two random indexes of the EmperorList and swapping their contents.
   private void randomize()
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
   
   private void loopResolve()
   {
      lo+=sz+sz;
      if (lo>=N-sz)
      {
         sz=sz+sz;
         if (sz>=N)
         {
            continueFlag=false;
            createTables();
            container.add(resultScreen);
            container.remove(askScreen);
            frame.pack();
            //cl.show(container,"3");
         }
         else 
         {
            lo=0;
            mid=lo+sz-1;
            hi=Math.min(lo+sz+sz-1, N-1);
            for (int k = lo; k <= hi; k++)
               aux[k] = emperors.getEmperor(k);
            i = lo;
            j = mid+1;
            k=lo;
            blockResolve();
            
         }
      }
      
      else
      {
         mid=lo+sz-1;
         hi=Math.min(lo+sz+sz-1, N-1);
         for (int k = lo; k <= hi; k++)
            aux[k] = emperors.getEmperor(k);
         i = lo;
         j = mid+1;
         k=lo;
         blockResolve();
         
      }
   }
   
   private void blockResolve()
   {
      while (k <= hi&&(i>mid||j>hi))
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
      }
   }
   
   public void createTables()
   {
      String[]columnNames={"Rank","Emperor"};
      Object[][]data=new Object[emperors.getLength()][2];
      for (int i=0;i<emperors.getLength();i++)
      {
         data[i][0]=i+1;
         data[i][1]=emperors.getEmperor(i);
      }
      JTable rankTable=new JTable(data,columnNames);
      scroller.setViewportView(rankTable);
      frame.pack();
   }
      
   public static void main(String[] args)
   {
		SwingUtilities.invokeLater(new Runnable()
      {
			@Override
			public void run()
         {
				new RomanEmperorSorter();
			}
		});
   }
}