package test;


import information.Information;
import visualisations.SondeAnalogique;

public class courbeTEB {
	
		

	
	    public static void main(final String[] args) throws Exception {
	    	SondeAnalogique s1 = new SondeAnalogique("BER/SNR"); 
	    	Simulateur  simu = null;
	    	double nbTest = 1000;
	    	float snr = -3;
	    	int j = 0;
	    	float ber = 0.0f;
	    	Information<Float> info = new Information<Float>();
	    	
	    	for (int i=0;i<args.length;i++){ 
	    	if(args[j].matches("-n")){j++; nbTest =  new Double(args[j]);} 
    	  	else if(args[j].matches("-snr")){j++; snr =  new Integer(args[j]);}}
	    	
	    	
	    	for (int i=0; i<nbTest;i++){
	    	String arg1[]={"-snr",String.valueOf(snr)};
			simu = new Simulateur(arg1);
			simu.execute();
			ber = simu.calculTauxErreurBinaire();
			info.add(ber);
			System.out.println(ber);
			snr= (float) (snr+0.01);
	    	}
	    	s1.recevoir(info);
	    }
	    
	}



