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
	
	private int nEchantillonPerBit ;
	private float ampMax ;
	private float ampMin ;
	private String forme ;
	private Information<Float> atteTrajets ;
	private Information<Integer> deltasTrajets ;
	private boolean multiTrajets = false ;
	/**
	 * Constructeur de la classe Recepteur
	 * @param form forme du signal analogique
	 * @param nEch nombre d'�chantillon pour ce signal
	 * @param aMax Amplitude maximum du signal
	 * @param aMin Amplitude minimum du signal
	 */
	public Recepteur(String form,int nEch,float aMax,float aMin){
		
		nEchantillonPerBit = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
	}

	public Recepteur(String form,int nEch,float aMax,float aMin,Information<Integer> pdt,Information<Float> patt){
		
		nEchantillonPerBit = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
		deltasTrajets = pdt ;
		atteTrajets = patt;
		multiTrajets = true ;
	}
	/**
	 * Permet de trouver le maximum d'une liste de INT
	 * @param info liste de int
	 * @return le maximum trouv�
	 */
	public int max(Information<Integer> info){
		
		int max = 0 ;
		for(int a : info){
			
			if(a>=max){
				max = a ;
			}
		}		
		return max;
	}
	/**
	 * Permet de transformer un signal analogique de type NRZ ou NRZT en un signal logique
	 * @throws InformationNonConforme Information non conforme
	 */
	public void decodeurNRZ_NRZT() throws InformationNonConforme{
		
		int i = 0 ;
		int j = 0 ;
		float sommeEchantillon = 0; //Somme des �chantillons sur le temps d'un bit.
		float moyenneAmplitude = 0;
		float seuilDecision = (ampMax+ampMin)/2;
		int nEchantillonInformation ;
		int numBit = 0; //Numero du bit en cours de lecture (en fonction du nombre d'�chantillons par bit)
		int numTrajet = 1;// numero du trajet suppl�mentaire test�
		int numConflit1 = 0; //prochain echantillon ou il y aura conflit (trajet1)
		
		
		if(multiTrajets){			
			nEchantillonInformation = informationRecue.nbElements()-max(deltasTrajets);
			for (Integer delta : deltasTrajets){ //Pour chaque delta de trajet
				if(numBit==0){ //Premier Bit lu
					for(i=0;i<nEchantillonPerBit;i++){ //moyenne amplitude 1er bit
						sommeEchantillon = sommeEchantillon + informationRecue.iemeElement(i);
					}
					if (moyenneAmplitude >= seuilDecision) {  //Au dessus du seuil -> 1
						//snumConflit1 = 
					}else if (moyenneAmplitude < seuilDecision){  //En dessous du seuil -> 0
						
					}
				}
				else{
					
				}
			}
			
		}else{
			nEchantillonInformation = informationRecue.nbElements();
		}
		
		
		
		while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des �chantillons du signal recu
			//Calcul de la moyenne d'amplitude sur le temps d'un bit
			for(j=i;j<i+nEchantillonPerBit;j++){  
				sommeEchantillon = sommeEchantillon + informationRecue.iemeElement(j);
			}
			moyenneAmplitude = sommeEchantillon/nEchantillonPerBit;  
			
			if (moyenneAmplitude >= seuilDecision) {  //Au dessus du seuil -> 1
				informationEmise.add(true); 
			}else if (moyenneAmplitude < seuilDecision){  //En dessous du seuil -> 0
				informationEmise.add(false);
			}
			i+=nEchantillonPerBit ; //on incremente i pour regarder le prochain bit
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
		float sommeEchantillon = 0; //Somme des �chantillons sur le temps d'un bit.
		float moyenneAmplitude = 0;
		float seuilDecision = (ampMax+ampMin)/2;
		int nEchantillonInformation ;
		if(multiTrajets){
			nEchantillonInformation = informationRecue.nbElements()-max(deltasTrajets);
		}else{
			nEchantillonInformation = informationRecue.nbElements();
		}
		 
		while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des �chantillons du signal recu
			//Calcul de la moyenne d'amplitude sur 1/3 du temps d'un bit (au milieu car RZ)
			for(j=i+(nEchantillonPerBit/3);j<i+(2*(nEchantillonPerBit/3));j++){  
				sommeEchantillon = sommeEchantillon + informationRecue.iemeElement(j);
			}
			moyenneAmplitude = sommeEchantillon/(nEchantillonPerBit/3);  
						
			if (moyenneAmplitude >= seuilDecision) {  //Au dessus du seuil -> 1
				informationEmise.add(true); 
			}else if (moyenneAmplitude < seuilDecision){ //En dessous du seuil -> 0
				informationEmise.add(false);
			}
			i+=nEchantillonPerBit ; //on incremente i pour regarder le prochain bit
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
		informationRecue = information; //on sauvegarde la donn�e recue
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
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) { //On recupere toutes les destinations auxquelles est connect�e notre instance
            destinationConnectee.recevoir(informationEmise);//puis on appel la m�thode recevoir de l'instance distante et on lui transmet les donn�es
            destinationConnectee.getInformationRecue();
         }   
	}

}
