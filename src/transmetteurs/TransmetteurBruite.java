package transmetteurs;
import java.util.*;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private float snr;
	private Information<Float> infoBruitee;
	
	/**
	 * Constructeur de la classe Transmetteur bruite
	 * @param snr rapport signal sur bruit voulu pour ce transmetteur
	 */
	public TransmetteurBruite (float snr)
	{
		this.snr = snr;
	}
	
	public float calculPuissanceMoySignal (){
		float sommeEchantillon = 0; //Contiendra la somme des �chantillon au carr�
		float puissanceMoyenne = 0;
		for (Iterator<Float> echantillon = informationRecue.iterator(); echantillon.hasNext();){
			//On parcours l'ensemble des �chantillons dl'information recue (avec la methode iterator voir classe information)
			sommeEchantillon = sommeEchantillon + (echantillon.next()*echantillon.next()); 
		}
		puissanceMoyenne =  sommeEchantillon/informationRecue.nbElements();
		return puissanceMoyenne;
	}
	
	public void bruitBlancGaussien(){
		// Calcul de la puissance moyenne (Ps) du signal d'entrée 
		// Calcul de Pb en fonction du SNR et de Ps
		// Boucle : Ajout du bruit calculé à chaque échantillon (voir formule)
		// Fonction Histogramme pour vérifier que notre bruit est bien blanc gaussien et centré.
	}
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		informationRecue = information; //R�ception de l'information 
		// Appel methode Calcul de la puissance du signal recu.
		// Appel methode Calcul du Bruit
		emettre(); //Emission de l'information bruit�e vers le r�cepteur		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
