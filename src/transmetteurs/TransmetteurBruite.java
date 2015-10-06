package transmetteurs;

import information.Information;
import information.InformationNonConforme;

public class TransmetteurBruite extends Transmetteur<Float, Float> {

	private float snr;
	private Information<Float> infoBruitee;
	
	public TransmetteurBruite (float psnr)
	{
		snr = psnr;
		infoBruitee = new Information<>();
	}
	
	public void bruitBlancGaussien(){
		// Calcul de la puissance moyenne (Ps) du signal d'entrée 
		// Calcul de Pb en fonction du SNR et de Ps
		// Boucle : Ajout du bruit calculé à chaque échantillon (voir formule)
		// Fonction Histogramme pour vérifier que notre bruit est bien blanc gaussien et centré.
	}
	
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		// Appel methode Calcul du Bruit
		// emettre()
		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		// TODO Auto-generated method stub
		
	}

}
