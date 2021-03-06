package transmetteurs;
import java.util.*;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private float snr;
	public float puissanceMoyBruit = 0;
	private int seed = -1; //germe du random si -1 �a veut dire qu'il n'y a pas de germe
	private Information<Integer> dt = new Information<Integer>();//liste des retards
	private Information<Float> ar = new Information<Float>() ;//liste des att�nuations
	private Information<Integer> numTrajet = new Information<Integer>() ;//liste des trajectoires
	boolean signalBruiteTrajetMult = false ;
	boolean signalBruite = false ;

	/**
	 * Constructeur de la classe Transmetteur bruite
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 */
	public TransmetteurBruite (float snr)
	{
		this.snr = snr;
		signalBruite = true ;
	}

	/**
	 * Constructeur de la classe Transmetteur bruite avec seed
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 * @param seed germe de random
	 */
	
	public TransmetteurBruite (float snr,int sd)
	{
		this.snr = snr;
		this.seed = sd;
		signalBruite = true ;
	}
	
	/**
	 * Constructeur de la classe Transmetteur bruite avec trajet multiple et seed
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 * @param seed germe de random
	 * @param numTraj numero du trajet multiple
	 * @param dt decalage du trajet multiple
	 * @param ar attenuation du trajet multiple 
	 */
	
	public TransmetteurBruite(float snr, Integer seed, Information<Integer> numTraj, Information<Integer> dt,Information<Float> ar) {
		
		this.signalBruiteTrajetMult = true ;
		this.snr=snr;
		this.ar=ar;
		this.dt=dt;
		this.numTrajet=numTraj;
		this.seed = seed;
		signalBruite = true ;
	}
	/**
	 * Constructeur de la classe Transmetteur bruite avec trajet multiple 
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 * @param numTraj numero du trajet multiple
	 * @param dt decalage du trajet multiple
	 * @param ar attenuation du trajet multiple 
	 */
	public TransmetteurBruite(float snr,Information<Integer> numTraj, Information<Integer> dt,Information<Float> ar) {
		
		this.signalBruiteTrajetMult = true ;
		this.snr=snr;
		this.ar=ar;
		this.dt=dt;
		this.numTrajet=numTraj;
		signalBruite = true ;
		
	}
	/**
	 * Constructeur de la classe Transmetteur avec trajet multiple 
	 * @param numTraj numero du trajet multiple
	 * @param dt decalage du trajet multiple
	 * @param ar attenuation du trajet multiple 
	 */
	public TransmetteurBruite(Information<Integer> numTraj, Information<Integer> dt,Information<Float> ar) {
		this.signalBruiteTrajetMult = true ;
		this.ar=ar;
		this.dt=dt;
		this.numTrajet=numTraj;
		signalBruite = false ;
		
	}
	/**
	    * Calcul de la puissance moyenne du signal d'entree a partir d'informationRecue
		* @param informationRecue information recue pour la generation du bruit
	    * @return puissanceMoyenne puissance moyenne calculee du signal recu
	    */
	public float calculPuissanceMoySignalRecu (Information<Float> informationRecue){
		float puissanceMoySignalRecu =0;
		float sommeEchantillon = 0; //Contiendra la somme des echantillon au carre
		for (Float echantillon : informationRecue){
			//On parcours l'ensemble des echantillons dl'information recue (avec la methode iterator voir classe information)
			sommeEchantillon = sommeEchantillon + (echantillon*echantillon); 
		}
		puissanceMoySignalRecu =  sommeEchantillon/informationRecue.nbElements();
		return puissanceMoySignalRecu;
	}
	
	/**
	    * Calcul du bruit blanc gaussien centré en fonction du snr
	    * @param snr rapport signal sur bruit
	    * @param puissanceMoySignalRecu Puissance moyenne du signal recu calculee
	    */
	public float bruitBlancGaussien(float snr,float puissanceMoySignalRecu, int sd){
		float a1 = 0.0f;
		float a2 = 0.0f;
		Random g1 = null;
		Random g2 = null;
		if(seed == -1) //cas sans germe 
		{
		g1 = new Random(); //Premiere variable aleatoire 
		g2 = new Random(); //Seconde variable aleatoire 
		}
		else //cas avec germe
		{
		g1 = new Random(seed); 
		g2 = new Random(seed);  
		}
		float echantillonBruite = 0; //echantillon calcule (Signal d'entre + bruit)
		int i =0; 
		
		 
		// Calcul de Pb en fonction du SNR et de Ps
		puissanceMoyBruit = (float) (10*Math.log10(puissanceMoySignalRecu)-snr);
		puissanceMoyBruit = (float) Math.pow(10, (puissanceMoyBruit/10));	
		//System.out.println("Puissance moyenne du bruit en fonction de Ps :"+ puissanceMoyBruit);
		
		// Boucle : Ajout du bruit calcule a chaque echantillon (voir formule)
		for (Float echantillon : informationEmise){
			a1 =  g1.nextFloat(); //a1 et a2 les des variables aléatoires du calcul
			a2 =  g2.nextFloat();
			
		    //Calcul d'un echantillon de bruit			
			echantillonBruite = (float) (Math.sqrt(puissanceMoyBruit)*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2));
			informationEmise.setIemeElement(i, informationEmise.iemeElement(i)+ echantillonBruite);
			i++; 
		}
		return echantillonBruite;
	}
	
	/**
	 * Permet de recuperer un signal et des parametres pour la r�alisation d'un signal en sortie contenant ce meme signal decall�
	 * @param info signal auquel rajouter les d�callages
	 * @param dt : une liste de decallage
	 * @param ar : une liste d'att�nuation
	 * @return le signal avec contenant les decallages
	 */
	public Information<Float> decalageInfo(Information<Float> info,Information<Integer> dt,Information<Float> ar){
		
		Information<Float> infoCreated = new Information<Float>();
	
		for(int j=0;j<max(dt)+info.nbElements();j++){
			infoCreated.add(0.0f);
		}
		
		int i = 0 ;
		while(dt.nbElements()>i){
			for(int k=dt.iemeElement(i);k<dt.iemeElement(i)+info.nbElements();k++){
				
				infoCreated.setIemeElement(k, infoCreated.iemeElement(k)+info.iemeElement(k-dt.iemeElement(i))*ar.iemeElement(i));
			}
			i++;
		}
		
		return infoCreated ;
	}
	/**
	 * Retourne le maximum d'une liste d'entier
	 * @param info Liste d'entier
	 * @return l'entier maximum
	 */
	public int max(Information<Integer> info){
		
		int max = 0 ;
		for(int a : info){
			
			if(a>=max){
				max = a ;
			}
		}
		
		return max;
	}
	
	/**
	 * Methode permettant de cr�er les trajets multiples
	 */
	public void bruitMultiTrajet(){
		
		Information<Float> infoGeneree = decalageInfo(informationRecue, dt,ar);

		for(int i=0;i<max(dt);i++){
			informationRecue.add(0.0f);
		}
		//On initialise la cha�ne � �mettre
		informationEmise = new Information<Float>();
		
		int i = 0 ;
		for(float val : informationRecue){
			informationEmise.add((float)(infoGeneree.iemeElement(i)+val));
			i++;
		}

	}

	/**
	    * Recoit l'information de l'emetteur
	    */
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		

		informationRecue = information ;
		
		
		if(signalBruiteTrajetMult && signalBruite){
			float puissanceMoySignalRecu = calculPuissanceMoySignalRecu (informationRecue);
			bruitMultiTrajet();
			bruitBlancGaussien(snr,puissanceMoySignalRecu,seed); //Calcul des échantillons de bruit blanc et l'ajoute au signal �  émettre
			emettre(); //Emission de l'information bruitee vers le recepteur
		}else if(!signalBruite){
			bruitMultiTrajet();
			emettre(); //Emission de l'information bruitee vers le recepteur
			
		}else{
			float puissanceMoySignalRecu = calculPuissanceMoySignalRecu (informationRecue);
			informationEmise = informationRecue;
			bruitBlancGaussien(snr,puissanceMoySignalRecu,seed); //Calcul des échantillons de bruit blanc et l'ajoute au signal �  émettre
			emettre(); //Emission de l'information bruitee vers le recepteur
		}
		
	}
	/**
	    * Emet l'information bruitee
	    * @throws InformationNonConforme information non conforme
	    */
	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
