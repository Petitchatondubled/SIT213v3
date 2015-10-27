package test;

import org.jfree.ui.RefineryUtilities;

import information.Information;

public class histogramm {

	   
    /**
    * contructeur par défaut de l'histogramme
    */
      public histogramm() {
              }

     		
   	/**
   	 *Classe exécuté pour le calcule de l'histogramme du bruit gaussien 
   	 */

     public static void main(final String[] args) {
    	  	  Information<Float> bruitATraiter = new Information<Float>();
    	  	float variance = 1;
    	  	int nbE = 100;
    	  	int j =0;

		for(j=0;j<args.length;j++){
    	  	if(args[j].matches("-var")){j++; variance =  new Float(args[j]);} 
    	  	else if(args[j].matches("-nbEch")){j++; nbE =  new Integer(args[j]);}}

    	  	float echantillonBruit = 0;
    	  	float a1 = 0.0f;
			float a2 = 0.0f;
			
			for(int i=0; i<nbE;i++){
				 a1 = (float) Math.random(); //a1 et a2 les des variables aléatoires du calcul
				 a2 = (float) Math.random(); 
		   			 echantillonBruit= (float) (Math.sqrt(variance)*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2));//calcule de l'échantillon de bruit
		   			 bruitATraiter.add(echantillonBruit);
		   			 System.out.println(echantillonBruit);
			}

	          BarChart_AWT chart = new BarChart_AWT("Vérification du bruit", "Histogramme",bruitATraiter);//création de la fenêtre
		      chart.pack( );        
		      RefineryUtilities.centerFrameOnScreen( chart );        
		      chart.setVisible( true ); //Affichage
	     		
      }
	
}
