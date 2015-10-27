package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class Decodeur extends Transmetteur<Boolean, Boolean> {

	
	/**
	 * Decodage en ligne information logiques reçues 
	 * @throws InformationNonConforme 
	 */
	public void decodage() throws InformationNonConforme
	{
		int i = 0;
		while(i<informationRecue.nbElements())
		{
			if(informationRecue.iemeElement(i) == false){i++;//si premier info vaut 0
				 if(informationRecue.iemeElement(i) == false){i++; //si deuxieme info vaut 0
					 if(informationRecue.iemeElement(i) == false){ informationEmise.add(false);}//si troisieme info vaut 0
					 else { informationEmise.add(true);}//si troisieme info vaut 1
					}
				 else{i++; informationEmise.add(false);}//si deuxieme info vaut 1 et on ne regarde plus le troisieme info car on enverra toujours 0
				}
			else{i++;
					if(informationRecue.iemeElement(i) == false){i++;informationEmise.add(true);}//si deuxieme info vaut 0 et on ne regarde plus le troisieme info car on enverra toujours 1
					else{i++;//si deuxieme info vaut 1
						if(informationRecue.iemeElement(i) == false){informationEmise.add(false);}//si troisieme info vaut 0
						else {informationEmise.add(true);}//si troisieme info vaut 1
					}
				}
			i++;
		}
		emettre();
	}
	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		informationRecue = information;
		informationEmise = new Information<Boolean>();
		decodage();
	}

	@Override
	public void emettre() throws InformationNonConforme {
		 for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
	            destinationConnectee.recevoir(informationEmise);
		 }
	}

}
