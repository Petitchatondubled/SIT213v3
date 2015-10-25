package transmetteurs;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
import visualisations.SondeAnalogique;

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
	private float atteTrajets[] = new float[5] ;
	private int deltasTrajets[] = new int[5] ;
	private boolean multiTrajets = false ;
	SondeAnalogique sonde = new SondeAnalogique("Signal reconstitué") ;
	/**
	 * Constructeur de la classe Recepteur
	 * @param form forme du signal analogique
	 * @param nEch nombre d'échantillon pour ce signal
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
		
		int i=0;
		nEchantillonPerBit = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
		for(float f:patt){ //mise en tableau attenuations
			atteTrajets[i]=f;
			i++;
		}
		i=0;
		for(int f:pdt){ //mise en tableau attenuations
			deltasTrajets[i]=f;
			i++;
		}
		multiTrajets = true ;
	}
	/**
	 * Permet de trouver le maximum d'une liste de INT
	 * @param info liste de int
	 * @return le maximum trouvé
	 */
	public int max(int[] info){
		
		int max = 0 ;
		for(int a : info){
			
			if(a>=max){
				max = a ;
			}
		}		
		return max;
	}
	/**
	 * Permet de trouver le minimum d'une liste de INT
	 * @param info liste de int
	 * @return le min trouvé
	 */
	public int min(int[] info){
		
		int min = info[0] ;
		for(int a : info){
			
			if(a<=min && a!=0){
				min = a ;
			}
		}		

		return min;
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
		int nEchantillonInformation ;
		int numTrajet = 0;// numero du trajet supplémentaire testé
		float valeur = 0;//valeur calculee en fonction d'un echantillon passé en connaissant le delta
		
		
		if(multiTrajets){
			int minDelta = min(deltasTrajets); //delta le plus faible
		
			nEchantillonInformation = informationRecue.nbElements()-max(deltasTrajets);
			while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
				if(i>=minDelta){ //Pas de calcul avant delta min
					numTrajet = 0; 
					for(int delta : deltasTrajets){
						if(i>=delta){ 
							valeur = informationRecue.iemeElement(i) - atteTrajets[numTrajet]*informationRecue.iemeElement(i-delta);
							informationRecue.setIemeElement(i, valeur);
						}	
						numTrajet++;
					}
				}			
				i++;
			}	
			sonde.recevoir(informationRecue);
		
		}else{
			nEchantillonInformation = informationRecue.nbElements();
		}
		i=0;
		while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
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
		float sommeEchantillon = 0; //Somme des échantillons sur le temps d'un bit.
		float moyenneAmplitude = 0;
		float seuilDecision = (ampMax+ampMin)/2;
		int nEchantillonInformation ;
		int numTrajet = 0;// numero du trajet supplémentaire testé
		float valeur = 0;//valeur calculee en fonction d'un echantillon passé en connaissant le delta
				
		
		if(multiTrajets){
			int minDelta = min(deltasTrajets); //delta le plus faible
			nEchantillonInformation = informationRecue.nbElements()-max(deltasTrajets);
			while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
				if(i>=minDelta){ //Pas de calcul avant delta min
					numTrajet = 0;
					for(int delta : deltasTrajets){
						if(i>=delta){ 
							valeur = informationRecue.iemeElement(i) - atteTrajets[numTrajet]*informationRecue.iemeElement(i-delta);
							informationRecue.setIemeElement(i, valeur);
						}	
						numTrajet++;
					}
				}
				i++;
			}
			sonde.recevoir(informationRecue);
		}else{
			nEchantillonInformation = informationRecue.nbElements();
		}		
		i=0;
		while(nEchantillonInformation>i){ //tant qu'on a pas lu l'ensemble des échantillons du signal recu
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
