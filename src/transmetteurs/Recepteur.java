package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;

/**
 * Classe permettant de transformer un signal analogique en un signal logique
 * @author bendaoud
 *
 */
public class Recepteur extends Transmetteur<Float,Boolean>{
	
	private int nEchantillon ;
	private float ampMax ;
	private float ampMin ;
	private String forme ;
	private float att ;
	private int dt ;
	private boolean multiTrajets = false ;
	/**
	 * Constructeur de la classe Recepteur
	 * @param form forme du signal analogique
	 * @param nEch nombre d'échantillon pour ce signal
	 * @param aMax Amplitude maximum du signal
	 * @param aMin Amplitude minimum du signal
	 */
	public Recepteur(String form,int nEch,float aMax,float aMin){
		
		nEchantillon = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
	}

	public Recepteur(String form,int nEch,float aMax,float aMin,int pdt,float patt){
		
		nEchantillon = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
		dt =pdt ;
		att=patt;
		multiTrajets = true ;
	}
	/**
	 * Permet de transformer un signal analogique de type NRZ ou NRZT en un signal logique
	 * @throws InformationNonConforme Information non conforme
	 */
	public void decodeurNRZ_NRZT() throws InformationNonConforme{
		
		int i = 0 ;
		int j = 0 ;
		float sommeEchantillon = 0; //Somme des échantillons sur le temps d'un bit.
		float moyenneAmplitude = 0;
		float seuilDecision = (ampMax+ampMin)/2;
		int nombreEchantillons ;
		
		if(multiTrajets){
			nombreEchantillons = informationRecue.nbElements()-dt;
		}else{
			nombreEchantillons = informationRecue.nbElements();
		}
		
		
		
		while(nombreEchantillons>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
			//Calcul de la moyenne d'amplitude sur le temps d'un bit
			for(j=i;j<i+nEchantillon;j++){  
				sommeEchantillon = sommeEchantillon + informationRecue.iemeElement(j);
			}
			moyenneAmplitude = sommeEchantillon/nEchantillon;  
			
			if (moyenneAmplitude > seuilDecision) {  //Au dessus du seuil -> 1
				informationEmise.add(true); 
			}else if (moyenneAmplitude < seuilDecision){  //En dessous du seuil -> 0
				informationEmise.add(false);
			}
			i+=nEchantillon ; //on incremente i pour regarder le prochain bit
			moyenneAmplitude = 0;
			sommeEchantillon = 0;
		}		
		
	}
	

    /**
     * Permet de transformer un signal analogique de type RZ en un signal logique
     * @throws InformationNonConforme
     */
	private void decodeurRZ() throws InformationNonConforme {

		int i = 0 ;
		int j = 0 ;
		float sommeEchantillon = 0; //Somme des échantillons sur le temps d'un bit.
		float moyenneAmplitude = 0;
		float seuilDecision = (ampMax+ampMin)/2;
		int nombreEchantillons ;
		if(multiTrajets){
			nombreEchantillons = informationRecue.nbElements()-dt;
		}else{
			nombreEchantillons = informationRecue.nbElements();
		}
		while(nombreEchantillons>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
			//Calcul de la moyenne d'amplitude sur 1/3 du temps d'un bit (au milieu car RZ)
			for(j=i+(nEchantillon/3);j<i+(2*(nEchantillon/3));j++){  
				sommeEchantillon = sommeEchantillon + informationRecue.iemeElement(j);
			}
			moyenneAmplitude = sommeEchantillon/(nEchantillon/3);  
						
			if (moyenneAmplitude > seuilDecision) {  //Au dessus du seuil -> 1
				informationEmise.add(true); 
			}else if (moyenneAmplitude < seuilDecision){ //En dessous du seuil -> 0
				informationEmise.add(false);
			}
			i+=nEchantillon ; //on incremente i pour regarder le prochain bit
			moyenneAmplitude = 0;
			sommeEchantillon = 0;
		}

	}

	/**
	    * Recoit l'information du transmetteur
	    */
	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information; //on sauvegarde la donnée recue
		informationEmise = new Information<Boolean>();
		switch(forme){ //Switch pour adapter le bon traitement voulu par l'utilisateur
		
			case "RZ" : this.decodeurRZ();
						break ;
			case "NRZ" : this.decodeurNRZ_NRZT();
						break ;
			case "NRZT" : this.decodeurNRZ_NRZT();
						break ;
			default :this.decodeurRZ();
						break ;
		}
		emettre(); // On emet le signal booleen retrouve a la destination
	}
	
	public void decodeurMultiTrajet(){
		
	}
	/**
	    * Emet l'information vers la ou les destination(s)
	    */
	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) { //On recupere toutes les destinations auxquelles est connectée notre instance
            destinationConnectee.recevoir(informationEmise);//puis on appel la méthode recevoir de l'instance distante et on lui transmet les données
            destinationConnectee.getInformationRecue();
         }   
	}

}
