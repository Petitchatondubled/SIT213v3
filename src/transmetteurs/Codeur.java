package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

public class Codeur extends Transmetteur<Boolean, Boolean> {
	
	/**
	 * Codage des booleens reçu en analogique
	 * @throws InformationNonConforme 
	 */
	public void Codage () throws InformationNonConforme
	{
		for(Boolean b:informationRecue)
		{
			informationEmise.add(b);
			if (b == true) //si on a un 1 on ajoute 01
			{
				informationEmise.add(false);
				informationEmise.add(true);
			}
			else //si on a un 0 on ajoute 10
			{
				informationEmise.add(true);
				informationEmise.add(false);
			}
		}
		emettre();
	}

	@Override
	public void emettre() throws InformationNonConforme {
		 for (DestinationInterface <Boolean> destinationConnectee : destinationsConnectees) {
	            destinationConnectee.recevoir(informationEmise);
		 }
	}

	@Override
	public void recevoir(Information<Boolean> information) throws InformationNonConforme {
		// TODO Auto-generated method stub
		informationRecue = information;
		informationEmise = new Information<Boolean>();
		Codage();
	}

}
