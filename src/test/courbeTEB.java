package test;


import org.jfree.*;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RefineryUtilities;

import information.Information;
import visualisations.SondeAnalogique;

public class courbeTEB {
	
		

	
	    public static void main(final String[] args) throws Exception {//main pour lancer la simulation
	    	
	    	
	    	Simulateur  simu = null;  //simulateur pour la simu
	    	double nbTest = 40;  //nombre de réalisation (par défaut 100)
	    	float snr = -20;  //snr de départ (par defaut -20)
	    	float freqsnr = 1f; //Pas du snr (par dedfaut 0.1)
	    	float ber = 0.0f; 
	    	int nbit = 1000;  //nombre de bit envoyé
	    	int seed = 5;  //germe utilisé pour avoir le même msg aleatoire à chaque fois
	    	
	    	Information<Float> berInfo = new Information<Float>(); //information contenant les valeur de BER
	    	Information<Float> snrInfo = new Information<Float>();
	    	Information<Float> berInfoNRZ = new Information<Float>(); //information contenant les valeur de BER
	    	Information<Float> snrInfoNRZ = new Information<Float>();
	    	Information<Float> berInfoNRZT = new Information<Float>(); //information contenant les valeur de BER
	    	Information<Float> snrInfoNRZT = new Information<Float>();

	    	String form  ="30" ;
	    	String form2 = "300";
	    	String form3 = "3000";
	    	//lancement du simulateur en fonction du nbTest voulu en augmentant le snr à chaque fois avec un pas de freqsnr
	    	for (int i=0; i<nbTest;i++){
	    		
		    	String arg1[]={"-snr",String.valueOf(snr),"-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-nbEch",String.valueOf(form)};
		    	String arg2[]={"-snr",String.valueOf(snr),"-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-nbEch",String.valueOf(form2)};
		    	String arg3[]={"-snr",String.valueOf(snr),"-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-nbEch",String.valueOf(form3)};
				simu = new Simulateur(arg1);
				
				//pour le signal RZ
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfo.add(ber);
				snrInfo.add(snr);
				
				//Pour le signal NRZ
				simu = new Simulateur(arg2);
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfoNRZ.add(ber);
				snrInfoNRZ.add(snr);
				
				//Pour le signal NRZT
				simu = new Simulateur(arg3);
				simu.execute(); //execution du simulateur
				ber = simu.calculTauxErreurBinaire();//recuperation du BER
				berInfoNRZT.add(ber);
				snrInfoNRZT.add(snr);
				
				snr= (float) (snr+freqsnr);
	    	}
	    	
	    	
	    	
	    	Graphe graphe = new Graphe("TEB/SNR pour "+nbit+" bits");
			graphe.createDataset("30",snrInfo, berInfo);
			graphe.createDataset("300",snrInfoNRZ, berInfoNRZ);
			graphe.createDataset("3000",snrInfoNRZT, berInfoNRZT);
	        JFreeChart chart = graphe.createChart(graphe.dataset,"SNR (dB)","TEB");
	        ChartPanel chartPanel = new ChartPanel(chart);
	        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
	        graphe.setContentPane(chartPanel);
	        graphe.pack();
	        RefineryUtilities.centerFrameOnScreen(graphe);
	        graphe.setVisible(true);
	    }
	    
	}
