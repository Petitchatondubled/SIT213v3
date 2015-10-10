package test;
import org.jfree.chart.ChartFactory;
import sources.*;
import destinations.*;
import transmetteurs.*;
import information.*;
import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
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
         "Puissance de bruit",            
         "Nb Echantillon",            
         createDataset(info),          
         PlotOrientation.VERTICAL,           
         true, true, false);
         
      ChartPanel chartPanel = new ChartPanel( barChart );        
      chartPanel.setPreferredSize(new java.awt.Dimension( 360 , 367 ) );        
      setContentPane( chartPanel ); 
   }
   private CategoryDataset createDataset(Information<Float> infoRecue )
   {
      final String bar = "Puissance de bruit";        
      float ncq = 0.0f;
      float nqt = 0.0f;
      float ntd = 0.0f;
      float ndu = 0.0f;
      float nuz = 0.0f;
      float zu = 0.0f;
      float ud = 0.0f;
      float dt = 0.0f;
      float tq = 0.0f;
      float qc = 0.0f;
     
      final DefaultCategoryDataset dataset = new DefaultCategoryDataset();  
      for(Float test:infoRecue)
      {
    	  if(-5<test&& test<-4)  ncq++;
    	  else if (-4<test&& test<-3)  nqt++;
    	  else if (-3<test&& test<-2)  ntd++;
    	  else if (-2<test&& test<-1)  ndu++;
    	  else if (-1<test&& test<0)  nuz++;
    	  else if (0<test&& test<1)  zu++;
    	  else if (1<test&& test<2)  ud++;
    	  else if (2<test&& test<3)  dt++;
    	  else if (3<test&& test<4)  tq++;
    	  else if (4<test&& test<5)  qc++;
      }
      dataset.addValue( ncq, bar, "-5   -4" );
      dataset.addValue( nqt, bar, "-4   -3" );
      dataset.addValue( ntd, bar, "-3   -2" );
      dataset.addValue( ndu, bar, "-1   0" );
      dataset.addValue( nuz, bar, "0   1" );
      dataset.addValue( zu, bar, "0   1" );
      dataset.addValue( ud, bar, "1   2" );
      dataset.addValue( dt, bar, "2   3" );
      dataset.addValue( tq, bar, "3   4" );
      dataset.addValue( qc, bar, "4   5" );
      
      

      return dataset; 
   }
   
}