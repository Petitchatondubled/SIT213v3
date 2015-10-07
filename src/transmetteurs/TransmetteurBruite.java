package transmetteurs;
import java.util.*;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private float snr;
	private float puissanceMoySignalRecu = 0;
	private float puissanceMoyBruit = 0;
	
	/**
	 * Constructeur de la classe Transmetteur bruite
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 */
	public TransmetteurBruite (float snr)
	{
		this.snr = snr;
	}
	
	/**
	    * Calcul de la puissance moyenne du signal d'entrée a partir d'informationRecue
	    * @return puissanceMoyenne puissance moyenne calculee du signal recu
	    */
	public void calculPuissanceMoySignal (){
		float sommeEchantillon = 0; //Contiendra la somme des échantillon au carré
		for (Iterator<Float> echantillon = informationRecue.iterator(); echantillon.hasNext();){
			//On parcours l'ensemble des échantillons dl'information recue (avec la methode iterator voir classe information)
			sommeEchantillon = sommeEchantillon + (echantillon.next()*echantillon.next()); 
		}
		puissanceMoySignalRecu =  sommeEchantillon/informationRecue.nbElements();
		System.out.println("Puissance moyenne du signal recu :"+ puissanceMoySignalRecu);
	}
	
	public void bruitBlancGaussien(){
		float a1 = 0; //Première variable aléatoire 
		float a2 = 0; //Seconde variable aléatoire 
		float echantillonBruite = 0; //echantillon calcule (Signal d'entre + bruit)
		
		// Calcul de Pb en fonction du SNR et de Ps
		puissanceMoyBruit = (float) (10*Math.log10(puissanceMoySignalRecu)-snr);
		puissanceMoyBruit = (float) Math.pow(puissanceMoyBruit, 10);	
		
		
		// Boucle : Ajout du bruit calcule  chaque echantillon (voir formule)
		for (Iterator<Float> echantillon = informationRecue.iterator(); echantillon.hasNext();){
			a1 = (float) Math.random();
			a2 = (float) Math.random();
		    //Calcul d'un echantillon de bruit
			echantillonBruite = (float) (Math.sqrt(puissanceMoyBruit)*Math.sqrt(-2*Math.log1p(1-a1))*Math.cos(2*Math.PI*a2));
			System.out.println("Echantillon bruite calcule :"+ echantillonBruite);
			//informationEmise.add(echantillonBruite+echantillon);
		}
		
		// Fonction Histogramme pour vÃ©rifier que notre bruit est bien blanc gaussien et centrÃ©.
	}
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		informationRecue = information; //Réception de l'information 
		calculPuissanceMoySignal ();
		bruitBlancGaussien();
		informationEmise = informationRecue;
		// Appel methode Calcul de la puissance du signal recu PS
		// Appel methode Calcul du Bruit en fonction de PS et du SNR
		emettre(); //Emission de l'information bruitée vers le récepteur		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
