package test;


import org.jfree.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import information.Information;
import visualisations.SondeAnalogique;

public class CourbeTEB_MultiTrajets {
	
		

	
	    public static void main(final String[] args) throws Exception {//main pour lancer la simulation
	    	
	    	
	    	Simulateur  simu = null;  //simulateur pour la simu
	    	double nbTest = 150;  //nombre de réalisation (par défaut 100)
	    	int dt = 0 ;  //snr de départ (par defaut -20)
	    	int freqdt = 1; //Pas du snr (par dedfaut 0.1)
	    	float ber = 0.0f; 
	    	int nbit = 10000;  //nombre de bit envoyé
	    	int seed = 5;  //germe utilisé pour avoir le même msg aleatoire à chaque fois
	    	
	    	Information<Float> berInfoNRZT = new Information<Float>(); //information contenant les valeur de BER
	    	Information<Float> dtInfo = new Information<Float>();
	    	Information<Float> berInfoNRZ = new Information<Float>(); //information contenant les valeur de BER
	    	Information<Float> berInfoRZ = new Information<Float>();
	
	    	//lancement du simulateur en fonction du nbTest voulu en augmentant le snr à chaque fois avec un pas de freqsnr
	    	for (int i=0; i<nbTest;i++){
	    		
		    	String arg1[]={"-ti","1",String.valueOf(dt),"0.7","-ti","2","30","0.7","-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-form","NRZ"};
				simu = new Simulateur(arg1);
				
				//pour le signal NRZT
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfoNRZT.add(ber);
				
				

		    	String arg2[]={"-ti","1",String.valueOf(dt),"1","-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-form","NRZ"};
				simu = new Simulateur(arg2);
				
				//pour le signal NRZ
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfoNRZ.add(ber);
				
				

		    	String arg3[]={"-ti","1",String.valueOf(dt),"0.7","-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-form","RZ"};
				simu = new Simulateur(arg3);
				
				//pour le signal RZ
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfoRZ.add(ber);
				dtInfo.add(new Float(dt));
				
				dt += freqdt ;
				System.out.println("Calcul | TEB/DeltaT multi-trajet :"+i+"/"+nbTest);
				
	    	}
	    	
	    	
	    	
	    	Graphe graphe = new Graphe("TEB selon le decallage pour "+nbit+" bits");
			graphe.createDataset("NRZT",dtInfo, berInfoNRZT);
			graphe.createDataset("NRZ",dtInfo, berInfoNRZ);
			graphe.createDataset("RZ",dtInfo, berInfoRZ);
	        JFreeChart chart = graphe.createChart(graphe.dataset,"Decallage (DT","TEB");
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        graphe.setContentPane(chartPanel);
	        graphe.pack();
	        RefineryUtilities.centerFrameOnScreen(graphe);
	        graphe.setVisible(true);
	    }
	    
	}
