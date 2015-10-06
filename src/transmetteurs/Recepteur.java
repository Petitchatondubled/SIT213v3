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
	private Information<Boolean> infoDecodee ;
	/**
	 * Constructeur de la classe Recepteur
	 * @param form forme du signal analogique
	 * @param nEch nombre d'�chantillon pour ce signal
	 * @param aMax Amplitude maximum du signal
	 * @param aMin Amplitude minimum du signal
	 */
	public Recepteur(String form,int nEch,float aMax,float aMin){
		
		nEchantillon = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
		infoDecodee = new Information<Boolean>();
	}

	/**
	 * Permet de transformer un signal analogique de type NRZ en un signal logique
	 * @throws InformationNonConforme
	 */
	public void decodeurNRZ() throws InformationNonConforme{
		int i = 0 ;

		while(informationRecue.nbElements()>i){ //tant qu'on a pas lu l'ensemble des elements nous continuons � regarder chaque �chantillon
			int k = 0 ;
			int h= 0 ;
			
			for(int j=0 ;j<nEchantillon;j++){ // On parcourt l'ensemble des echantillons pendant un temps bit
				if(informationRecue.iemeElement(i+j) == ampMax ){ // si l'�chantillon � comme valeur ampMax
					h=0;
					k++ ; //On compte le nombre de fois � la suite qu'on a cette valeur
					if(k==nEchantillon){ // si cette valeur est trouv�e pendant le temps bit, c'est que cela correspond a 1 logique
						infoDecodee.add(true);
					}
				}else{ // si on se trouve pas dans sur l'amplitude max
					k=0;
					h++ ; //meme fonctionnement que precedement
					if(h==nEchantillon){
						infoDecodee.add(false);
					}
				}
			}
			i+=nEchantillon ; //on incremente du temps bit (nombre d'�chantillon)
		}
		
		
		
		
	}

	/**
	 * Permet de transformer un signal analogique de type NRZT en un signal logique
	 * @throws InformationNonConforme
	 */
	private void decodeurNRZT() throws InformationNonConforme {
        int begin = 0 ; //pointeur pour le debut des echantillons d'un bit de l'information recue    
        
        
        while(informationRecue.nbElements()>begin){ //On parcours l'information recue
		if(informationRecue.iemeElement(begin+(nEchantillon/2)) == ampMax){ // on regarde � chaque fois au milieu du temps bit, si on a l'amplitude max ==> 1
                                infoDecodee.add(true);
                        }else
			{ // sinon c'est 0
                                infoDecodee.add(false);
                        }
                        begin+=nEchantillon ; //on incremente i pour regarder le prochain bit
          }
   

    }

    /**
     * Permet de transformer un signal analogique de type RZ en un signal logique
     * @throws InformationNonConforme
     */
	private void decodeurRZ() throws InformationNonConforme {

		int i = 0 ;
		
		
		while(informationRecue.nbElements()>i){ //tant qu'on a pas lu l'ensemble des �chantillons nous continuons � regarder chaque �chantillon
			
			if(informationRecue.iemeElement(i+(nEchantillon/2)) == ampMax){ // on regarde � chaque fois au milieu du temps bit, si on a l'amplitude max ==> 1
				infoDecodee.add(true);
			}else{ // sinon c'est 0
				infoDecodee.add(false);
			}
			i+=nEchantillon ; //on incremente i pour regarder le prochain bit
		}

	}

	@Override
	public void recevoir(Information<Float> information)
			throws InformationNonConforme {
		informationRecue = information; //on sauvegarde la donn�e recue
		switch(forme){ //Switch pour adapter le bon traitement voulu par l'utilisateur
		
			case "RZ" : this.decodeurRZ();
						break ;
			case "NRZ" : this.decodeurNRZ();
						break ;
			case "NRZT" : this.decodeurNRZT();
						break ;
			default :this.decodeurRZ();
						break ;
		}
		
		emettre(); // ON EMET LA DONNEE
	}
	
	

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Boolean> destinationConnectee : destinationsConnectees) { //On recupere toutes les destinations auxquelles est connect�e notre instance
            destinationConnectee.recevoir(infoDecodee);//puis on appel la m�thode recevoir de l'instance distante et on lui transmet les donn�es
            destinationConnectee.getInformationRecue();
         }   
		informationEmise = infoDecodee ; // on met l'information emise dans cette variable (peut servir pour le TEB)
	}

}
