package test;
import java.awt.Font;

import org.jfree.chart.ChartFactory;
import sources.*;
import destinations.*;
import transmetteurs.*;
import information.*;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset; 
import org.jfree.data.category.DefaultCategoryDataset; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 

public class BarChart_AWT extends ApplicationFrame
{
   public BarChart_AWT( String applicationTitle , String chartTitle, Information<Float> info )
   {
      super( applicationTitle );        
      JFreeChart barChart = ChartFactory.createBarChart(
         chartTitle,           
         "",            
         "Nb Echantillon",            
         createDataset(info),          
         PlotOrientation.HORIZONTAL,           
         true, true, false);
     
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 1000 , 1500 ) );        
      setContentPane( chartPanel ); 
      CategoryPlot plot = barChart.getCategoryPlot();
      Font font3 = new Font("Dialog", Font.PLAIN, 9); 
      plot.getDomainAxis().setLabelFont(font3);
      plot.getRangeAxis().setLabelFont(font3);
   }
   private CategoryDataset createDataset(Information<Float> infoRecue )
   {
      final String bar = "Bruit généré";
      double[][][] val = new double[1000000][10][2];
      int compteur=0;
      for (int i=0;i<val.length;i++)
      { 
    	  for(int j=0;j<10;j++) for(int h=0;h<2;h++)val[i][j][h]=0;
      }
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
      
      float temp = 0.0f;
      for(Float test:infoRecue)
      {
    	temp = test;
    	if(test >0){
    		if(test.intValue()==test)
    		{
    			val[Math.abs(test.intValue())][0][0]++;
    		}
    		else
    		{
    			temp = (float) (temp - 0.1);
    			while(test.intValue()<=temp)
    				{
    					compteur++;
    					temp = (float) (temp - 0.1);
    				}
    			val[Math.abs(test.intValue())][compteur][0]++;
    			compteur = 0;
    		}
    	}else
    	{
    		if(test.intValue()==test)
    		{
    			val[Math.abs(test.intValue())][0][1]++;
    		}
    		else
    		{
    			temp = (float) (temp + 0.1);
    			while(test.intValue()>=temp)
    				{
    					compteur++;
    					temp = (float) (temp + 0.1);
    				}
    			val[Math.abs(test.intValue())][compteur][1]++;
    			compteur = 0;
    		}
    	}
      }
    	
      for (int i=val.length-1;i>=0;i--)
      {
    	 for(int j=9;j>=0;j--)
    	 {
    		 for(int h=0;h<2;h++){
    		 if(val[i][j][h] != 0)
    		 {
    			 if(h ==1)
    				 dataset.addValue( val[i][j][h], bar,"-"+Integer.toString(i)+"."+Integer.toString(j));
    		 }
    	 }
    	
      }
    	 }
      for (int i =0;i<val.length;i++)
      {
    	 for(int j=0;j<10;j++)
    	 {
    		 for(int h=0;h<2;h++){
    		 if(val[i][j][h] != 0)
    		 {
    			 if(h ==0)
    				 dataset.addValue( val[i][j][h], bar, Integer.toString(i)+"."+Integer.toString(j));
    		 }
    	 }
    	
      }
    	 }
      return dataset; 
   }
   
}