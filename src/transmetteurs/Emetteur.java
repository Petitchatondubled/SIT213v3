package transmetteurs;

import java.util.LinkedList;

import destinations.DestinationInterface;
import information.Information;
import information.InformationNonConforme;
/**
 * Classe permettant de transformer un signal logique et le transformer en signal logique pour 
 * le transmettre par la suite.
 * @author bendaoud
 *
 */
public class Emetteur extends Transmetteur<Boolean,Float>{
	
	private int nEchantillon ;
	private float ampMax ;
	private float ampMin ;
	private String forme ;
	private Information<Float> infoGeneree ;
	/**
	 * Constructeur de la classe emetteur
	 * @param form forme du signal analogique
	 * @param nEch nombre d'échantillon pour ce signal
	 * @param aMax Amplitude maximum du signal
	 * @param aMin Amplitude minimum du signal
	 * 
	 */
	public Emetteur(String form,int nEch,float aMax,float aMin){
		
		nEchantillon = nEch ;
		ampMax = aMax ;
		ampMin = aMin ;
		forme = form ;
		infoGeneree = new Information<Float>();

	}

	/**
	 * Permet de transformer un signal logique en signal analogique de type NRZ
	 * @throws InformationNonConforme 
	 */
	public void transformateurNRZ() throws InformationNonConforme{
		
		for(Boolean bool : informationRecue){ // On parcourt la suite des données reçues ( liste de booleens)
			
			if(bool){ //si le booleen est un true (correspond au 1 en logique)
				
				for(int i=0 ; i<nEchantillon;i++){ // on genere nEchantion echantillons valant AmpMax 
					infoGeneree.add(ampMax);
				}
			}else{
				for(int i=0 ; i<nEchantillon;i++){ // on genere nEchantion echantillons valant AmpMin
					infoGeneree.add(ampMin);
				}
			}
		}
		
	}
	
	/**
	 * Permet de transformer un signal logique en signal analogique de type NRZT
	 * @throws InformationNonConforme
	 */

	private void transformateurNRZT() throws InformationNonConforme {
        infoGeneree = new Information<Float>() ; //
        int nb_tiers = nEchantillon/3 ;
        int reste = nEchantillon%3 ;
        
        
        for(Boolean bool : informationRecue){
                
                if(bool){

                        
                        for(int i=0 ; i < nb_tiers;i++){
                                //équation de la droite pour la monté
                                float y =  ((float)((ampMax - ampMin)/nb_tiers))*(float)i+ ampMin ;
                                infoGeneree.add(y);
                        }
                        
                        for(int j=0 ; j < (nb_tiers+reste);j++){
                                //equation pour la droite amplitude Max
                                float y = ampMax ;
                                infoGeneree.add(y);
                        }
                        
                        for(int k=(2*nb_tiers+reste) ; k < nEchantillon ;k++){
                                //équation de la droite pour la descente
                                float y =  (((float)((ampMax - ampMin)/(2*nb_tiers+reste-nEchantillon)))*(float)k)+ ampMin-((ampMax-ampMin)/(2*nb_tiers+reste-nEchantillon))*nEchantillon ;
                                infoGeneree.add(y);
                        }
                }
                else
                {
                        for(int i=0 ; i<nEchantillon;i++)        infoGeneree.add(ampMin);
                }
        }
        
	}

	/**
	 * Permet de transformer un signal logique en signal analogique de type RZ
	 * @throws InformationNonConforme
	 */
	private void transformateurRZ() throws InformationNonConforme {
		
		infoGeneree = new Information<Float>() ;
		int nb_tiers = nEchantillon/3; // nombre d'échantillon pour un tiers du temps bit
		int nb_reste = nEchantillon%3; // le reste de la division (ex 5/3 = 1 et 5%3= 2), afin de respecter le nombre d'échantillons voulus
		
		for(Boolean bool : informationRecue){
			
			if(bool){ //si c'est un 1 qu'on recupere
				
				for(int i=0 ; i<nb_tiers;i++){ // on emet AmpMin sur le premier tiers du temps bit
					infoGeneree.add(ampMin);
				}
				for(int i=0 ; i<nb_tiers+nb_reste;i++){ // on met AmpMax sur le deuxieme tiers du temps bit
					infoGeneree.add(ampMax);
				}
				for(int i=0 ; i<nb_tiers;i++){ //on emet AmpMin sur le dernier tiers du temps bit
					infoGeneree.add(ampMin);
				}
				
			}else{ //si le bit vaut 0
				for(int i=0 ; i<nEchantillon;i++){ // Tous le temps bit vaut ampMin
					infoGeneree.add(ampMin);
				}
			}
		}
		
		
	}

	@Override
	public void recevoir(Information<Boolean> information)
			throws InformationNonConforme {
		informationRecue = information; 
		switch(forme){ // Ce switch permet de savoir lorsque nous recevons une donnée qu'elle transformation nous devons lui faire
		
			case "RZ" : this.transformateurRZ();
						break ;
			case "NRZ" : this.transformateurNRZ();
						break ;
			case "NRZT" : this.transformateurNRZT();
						break ;
			default :this.transformateurRZ(); 
						break ;
		}
		emettre(); // Quand on a fini de transformer les données, nous emettons le signal analogique
	}
	
	

	@Override
	public void emettre() throws InformationNonConforme {
		for (DestinationInterface<Float> destinationConnectee : destinationsConnectees) { //On recupere toutes les destinations auxquelles est connectée notre instance
            destinationConnectee.recevoir(infoGeneree); //puis on appel la méthode recevoir de l'instance distante et on lui transmet les données
            destinationConnectee.getInformationRecue();
         }   
	}

}
