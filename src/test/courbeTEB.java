package test;


import information.Information;
import visualisations.SondeAnalogique;

public class courbeTEB {
	
		

	
	    public static void main(final String[] args) throws Exception {//main pour lancer la simulation
	    	SondeAnalogique s1 = new SondeAnalogique("BER/SNR");  //sonde pour l'affichage
	    	Simulateur  simu = null;  //simulateur pour la simu
	    	double nbTest = 100;  //nombre de réalisation (par défaut 100)
	    	float snr = -20;  //snr de départ (par defaut -20)
		float freqsnr = 0.1f; //Pas du snr (par dedfaut 0.1)
	    	float ber = 0.0f; 
		int nbit = 100;  //nombre de bit envoyé
		int seed = 5;  //germe utilisé pour avoir le même msg aleatoire à chaque fois
		String form = "RZ"; //form du signal (par defaut RZ)
	    	Information<Float> info = new Information<Float>(); //information contenant les valeur de BER
	    	
	    	for (int j=0;j<args.length;j++){ //gestion des arguments
	    	if(args[j].matches("-n")){j++; nbTest =  new Double(args[j]);} 
    	  	else if(args[j].matches("-snr")){j++; snr =  new Integer(args[j]);}
		else if(args[j].matches("-snrfreq")){j++; freqsnr =  new Float(args[j]);}
		else if(args[j].matches("-nb")){j++; nbit =  new Integer(args[j]);}
		else if(args[j].matches("-seed")){j++; seed =  new Integer(args[j]);}
		else if(args[j].matches("-form")){j++; form = args[j];}
		else throw new ArgumentsException("Valeur du parametre invalide : " + args[j]);
	}
	    	
	    	
	    	//lancement du simulateur en fonction du nbTest voulu en augmentant le snr à chaque fois avec un pas de freqsnr
	    	for (int i=0; i<nbTest;i++){
	    	String arg1[]={"-snr",String.valueOf(snr),"-mess",String.valueOf(nbit),"-seed",String.valueOf(seed),"-form",String.valueOf(form)};
			simu = new Simulateur(arg1);
			simu.execute(); //execution du simulateur
			ber = simu.calculTauxErreurBinaire();//recuperation du BER
			info.add(ber); 
			System.out.println(ber);
			snr= (float) (snr+freqsnr);
	    	}
	    	s1.recevoir(info);
	    }
	    
	}


