package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class TransmetteurParfaitAnalogique extends Transmetteur<Float,Float>{


	@Override
	public void recevoir(Information<Float> information) throws InformationNonConforme {
		informationRecue = information;
		informationEmise = informationRecue; //On se contente de copier l'info recue pour l'emettre
        emettre();		
	}

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) {
            destinationConnectee.recevoir(informationEmise);
            destinationConnectee.getInformationRecue();
         }   
	}

}
