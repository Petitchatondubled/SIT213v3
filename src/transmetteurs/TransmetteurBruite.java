package transmetteurs;
import java.util.*;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private float snr;
	public float puissanceMoyBruit = 0;
	private int seed = -1; //germe du random si -1 ça veut dire qu'il n'y a pas de germe
	private float ar;//attenuation
	private int dt;//retard
	private int numTrajet;//numéro du trajet
	private LinkedList<Information<Float>> trajet= new LinkedList<Information<Float>>();//liste des trajectoires
	boolean signalBruiteTrajetMult = false ;
	
	/**
	 * Constructeur de la classe Transmetteur bruite
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 */
	public TransmetteurBruite (float snr)
	{
		this.snr = snr;
	}
	
	public TransmetteurBruite ( float snr, float ar, int dt, int numTrajet)
	{
		this.signalBruiteTrajetMult = true ;
		this.snr=snr;
		this.ar=ar;
		this.dt=dt;
		this.numTrajet=numTrajet;
		System.out.println("Entré dans le constructeur TBruite");
		
	}
	/**
	 * Constructeur de la classe Transmetteur bruiteavec seed
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 * @param seed germe de random
	 */
	public TransmetteurBruite (float snr,int sd)
	{
		this.snr = snr;
		this.seed = sd;
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
		System.out.println("Puissance moyenne du signal recu :"+ puissanceMoySignalRecu);
		return puissanceMoySignalRecu;
	}
	
	/**
	    * Calcul du bruit blanc gaussien centrÃ© en fonction du snr
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
		
		informationEmise = informationRecue; //Copie du signal recu dans informationEmise
		
		// Calcul de Pb en fonction du SNR et de Ps
		puissanceMoyBruit = (float) (10*Math.log10(puissanceMoySignalRecu)-snr);
		puissanceMoyBruit = (float) Math.pow(10, (puissanceMoyBruit/10));	
		System.out.println("Puissance moyenne du bruit en fonction de Ps :"+ puissanceMoyBruit);
		
		// Boucle : Ajout du bruit calcule a chaque echantillon (voir formule)
		for (Float echantillon : informationEmise){
			a1 =  g1.nextFloat(); //a1 et a2 les des variables alÃ©atoires du calcul
			a2 =  g2.nextFloat();
			
		    //Calcul d'un echantillon de bruit			
			echantillonBruite = (float) (Math.sqrt(puissanceMoyBruit)*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2));
			informationEmise.setIemeElement(i, informationEmise.iemeElement(i)+ echantillonBruite);
			i++; 
		}
		return echantillonBruite;
	}
	
	public void bruitTrajetsMultiples(float ar, int dt, int numTrajet){
		
		int j =0;
		int k=0;
		informationEmise = informationRecue; 
		
		//Déclaration de la trajectoire et ajout de l'informatino reçue
		Information<Float> trajectoire = new Information<Float>();	
		for(Float f : informationRecue) trajectoire.add(f);
		
		//Calcul de la puissance moyenne du signal pour le bruit blanc Gaussien
		float puissanceMoySignalRecu=calculPuissanceMoySignalRecu (informationRecue);
		
	//Calcul de la trajectoire du signal indirect
	if (numTrajet>0 && numTrajet<6){
	
		//Décalage du signal de dt échantillons
		for(int i=0; i<dt; i++){
			trajectoire.setIemeElement(i, 0.0f );
		}
		//Ajout du signal d'origine après le décalage dt
		while(j<informationRecue.nbElements()-dt){
			trajectoire.setIemeElement(j+dt, ar*informationRecue.iemeElement(j) );
			j++;
		}
		//Ajout de la trajectorie dans la liste des trajets
		trajet.add(numTrajet-1,trajectoire);
	
	//Ajout de la trajectoire et du bruit au signal à émettre
	for (Float echantillon : informationEmise){
		informationEmise.setIemeElement(k, informationRecue.iemeElement(k)+ trajet.get(numTrajet-1).iemeElement(k)+bruitBlancGaussien(snr,puissanceMoySignalRecu,seed));
		k++;
	}
	}
	else{
		System.out.println("Mauvais numéro de trajectoire");
	}
}
	/**
	    * Recoit l'information de l'emetteur
	    */
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		float puissanceMoySignalRecu = 0; 
		
		informationRecue = information; //Reception de l'information 
		puissanceMoySignalRecu = calculPuissanceMoySignalRecu (informationRecue); //Calcul la puissance moyenne d'informationRecue
		if (signalBruiteTrajetMult){
			bruitTrajetsMultiples(ar, dt, numTrajet);
			emettre();
		}else{
		bruitBlancGaussien(snr,puissanceMoySignalRecu,seed); //Calcul des Ã©chantillons de bruit blanc et l'ajoute au signal Ã  Ã©mettre
		emettre(); //Emission de l'information bruitee vers le recepteur
		}
	}
	/**
	    * Emet l'information bruitee
	    */
	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
