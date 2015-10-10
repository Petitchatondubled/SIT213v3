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
	    * Calcul de la puissance moyenne du signal d'entrï¿½e a partir d'informationRecue
	    * @return puissanceMoyenne puissance moyenne calculee du signal recu
	    */
	public void calculPuissanceMoySignal (){
		float sommeEchantillon = 0; //Contiendra la somme des ï¿½chantillon au carrï¿½
		for (Float echantillon : informationRecue){
			//On parcours l'ensemble des ï¿½chantillons dl'information recue (avec la methode iterator voir classe information)
			sommeEchantillon = sommeEchantillon + (echantillon*echantillon); 
		}
		puissanceMoySignalRecu =  sommeEchantillon/informationRecue.nbElements();
		System.out.println("Puissance moyenne du signal recu :"+ puissanceMoySignalRecu);
	}
	
	/**
	    * Calcul du bruit blanc gaussien centré en fonction du snr
	    */
	public void bruitBlancGaussien(){
		float a1 = 0; //Premiï¿½re variable alï¿½atoire 
		float a2 = 0; //Seconde variable alï¿½atoire 
		float echantillonBruite = 0; //echantillon calcule (Signal d'entre + bruit)
		float cacheCalcul = 0;
		int i =0; 
		
		informationEmise = informationRecue; //Copie du signal recu dans informationEmise
		
		// Calcul de Pb en fonction du SNR et de Ps
		puissanceMoyBruit = (float) (10*Math.log10(puissanceMoySignalRecu)-snr);
		puissanceMoyBruit = (float) Math.pow(10, (puissanceMoyBruit/10));	
		System.out.println("Puissance moyenne du bruit en fonction de Ps :"+ puissanceMoyBruit);
		
		// Boucle : Ajout du bruit calculeï¿½ chaque echantillon (voir formule)
		for (Float echantillon : informationEmise){
			a1 = (float) Math.random(); //a1 et a2 les des variables aléatoires du calcul
			a2 = (float) Math.random();
			
		    //Calcul d'un echantillon de bruit
			/*    Garder pour l'instant le calcul détaillé ici tant qu'on a pas vérifié que c'était gaussien
			echantillonBruite = (float) (Math.sqrt(puissanceMoyBruit));
			cacheCalcul = (float) (-2*Math.log(1-a1));
			echantillonBruite = (float) Math.sqrt(cacheCalcul);
			echantillonBruite = (float) (echantillonBruite*Math.cos(2*Math.PI*a2)); */
			echantillonBruite = (float) (Math.sqrt(puissanceMoyBruit)*Math.sqrt(-2*Math.log(1-a1))*Math.cos(2*Math.PI*a2));
			informationEmise.setIemeElement(i, informationEmise.iemeElement(i)+ echantillonBruite);
			echantillon = echantillon + echantillonBruite; //Somme des échantillons du signal recue avec le bruit
			i++; 
		}
		
	}
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		informationRecue = information; //Reception de l'information 
		System.out.println("---- Information recue => "+ informationRecue.toString());
		calculPuissanceMoySignal (); //Calcul la puissance moyenne d'informationRecue
		bruitBlancGaussien(); //Calcul des échantillons de bruit blanc et l'ajoute au signal à émettre
		emettre(); //Emission de l'information bruitee vers le recepteur		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
