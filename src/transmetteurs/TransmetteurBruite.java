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
		float sommeEchantillon = 0; //Contiendra la somme des Èchantillon au carrÈ
		float puissanceMoyenne = 0;
		for (Iterator<Float> echantillon = informationRecue.iterator(); echantillon.hasNext();){
			//On parcours l'ensemble des Èchantillons dl'information recue (avec la methode iterator voir classe information)
			sommeEchantillon = sommeEchantillon + (echantillon.next()*echantillon.next()); 
		}
		puissanceMoyenne =  sommeEchantillon/informationRecue.nbElements();
		return puissanceMoyenne;
	}
	
	public void bruitBlancGaussien(){
		// Calcul de la puissance moyenne (Ps) du signal d'entr√©e 
		// Calcul de Pb en fonction du SNR et de Ps
		// Boucle : Ajout du bruit calcul√© √† chaque √©chantillon (voir formule)
		// Fonction Histogramme pour v√©rifier que notre bruit est bien blanc gaussien et centr√©.
	}
	
	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		informationRecue = information; //RÈception de l'information 
		// Appel methode Calcul de la puissance du signal recu.
		// Appel methode Calcul du Bruit
		emettre(); //Emission de l'information bruitÈe vers le rÈcepteur		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         } 
		
	}

}
