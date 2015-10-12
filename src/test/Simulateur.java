package test ;
   import sources.*;
import destinations.*;
import transmetteurs.*;
import information.*;
import visualisations.*;

 import java.util.regex.*;
import java.util.*;
import java.lang.Math;

	
 import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;


/** La classe Simulateur permet de construire et simuler une cha�ne de transmission compos�e d'une Source, d'un nombre variable de Transmetteur(s) et d'une Destination.  
 * @author cousin
 * @author prou
 *
 */
   public class Simulateur {
      	
   /** indique si le Simulateur utilise des sondes d'affichage */
      private          boolean affichage = false;
   /** indique si le Simulateur utilise un message g�n�r� de mani�re al�atoire */
      private          boolean messageAleatoire = true;
   /** indique si le Simulateur utilise un germe pour initialiser les g�n�rateurs al�atoires */
      private          boolean aleatoireAvecGerme = false;
   /** la valeur de la semence utilis�e pour les g�n�rateurs al�atoires */
      private          Integer seed = null;
   /** la longueur du message al�atoire � transmettre si un message n'est pas impose */
      private          int nbBitsMess = 100; 
   /** la cha�ne de caract�res correspondant � m dans l'argument -mess m */
      private          String messageString = "100";
   /** la chaine de caractere correspond au type de forme du signal analogique */   
      private String forme = "RZ" ;
   /** Le nombre d'échantillon par bit, c'est le temps bit indirectement */
      private int nbEch = 30 ;
   /** L'amplitude maximun du signal analogique, par défaut c'est 1.0f*/
      private float amplMax = 1.0f ;
   /** L'amplitude minimum du signal analogique, par défaut c'est 0.0f*/
      private float amplMin = 0.0f ;
   /** Indique si le simulateur utilise un signal analogique, par défaut c'est false*/  
      private boolean messageAnalogique = false ;
   /** Rapport signal sur bruit desire dans le transmetteur, valeur par defaut : 0.0f*/
   private boolean signalBruite = false ;
   /** Rapport signal sur bruit desire dans le transmetteur, valeur par defaut : 0.0f*/
   private float snr = 0.0f ;
   
   
   	
   /** le  composant Source de la chaine de transmission */
      private			  Source <Boolean>  source = null;
   /** le  composant Transmetteur parfait logique de la chaine de transmission */
      private			  Transmetteur <Boolean, Boolean>  transmetteurLogique = null;
   /** le  composant Transmetteur analogique de la chaine de transmission */
      private			  Transmetteur <Float, Float>  transmetteurAnalogique = null;
   /** le  composant Destination de la chaine de transmission */
      private			  Destination <Boolean>  destination = null;
   	
   
   /** Le constructeur de Simulateur construit une chaine de transmission composee d'une Source Boolean, d'une Destination Boolean et de Transmetteur(s) [voir la m�thode analyseArguments]...  
   * <br> Les diff�rents composants de la cha�ne de transmission (Source, Transmetteur(s), Destination, Sonde(s) de visualisation) sont crees et connect�s.
   * @param args le tableau des diff�rents arguments.
   *
   * @throws ArgumentsException si un des arguments est incorrect
   *
   */   
      public  Simulateur(String [] args) throws ArgumentsException {
    	  
      		analyseArguments(args);
      	
      }
   
   
   
   /** La mÃ©thode analyseArguments extrait d'un tableau de chaï¿œnes de caractÃšres les diffï¿œrentes options de la simulation. 
   * Elle met Ã  jour les attributs du Simulateur.
   *
   * <br>  Les arguments autorisÃ©s sont : 
   * <dl>
   * <dt> -mess m  </dt><dd> m (String) constituï¿œ de 7 ou plus digits Ã  0 | 1, le message Ã  transmettre</dd>
   * <dt> -mess m  </dt><dd> m (int) constituï¿œ de 1 Ã  6 digits, le nombre de bits du message "alÃ©atoire" Ã  transmettre</dd> 
   * <dt> -s </dt><dd> utilisation des sondes d'affichage</dd>
   * <dt> -seed v </dt><dd> v (int) d'initialisation pour les gÃ©nÃ©rateurs alÃ©atoires</dd> 
   * <dt> -form f </dt><dd>  codage (String) RZ, NRZR, NRZT, la forme d'onde du signal Ã  transmettre (RZ par dï¿œfaut)</dd>
   * <dt> -nbEch ne </dt><dd> ne (int) le nombre d'Ã©chantillons par bit (ne supÃ©rieur ou Ã©gale 6 pour du RZ, ne supÃ©rieur ou Ã©gale 9 pour du NRZT, ne supÃ©rieur ou Ã©gale 18 pour du RZ,  30 par dÃ©faut))</dd>
   * <dt> -ampl min max </dt><dd>  min (float) et max (float), les amplitudes min et max du signal analogique Ã  transmettre ( min infÃ©rieur Ã  max, 0.0 et 1.0 par dÃ©faut))</dd> 
   * 
   * <dt> -snr s </dt><dd> s (float) le rapport signal/bruit en dB</dd>
   * 
   * <dt> -ti i dt ar </dt><dd> i (int) numero du trajet indirect (de 1 Ã  5), dt (int) valeur du decalage temporel du iÃ©me trajet indirect 
   * en nombre d'Ã©chantillons par bit, ar (float) amplitude relative au signal initial du signal ayant effectuÃš le iÃ©me trajet indirect</dd>
   * 
   * <dt> -transducteur </dt><dd> utilisation de transducteur</dd>
   * 
   * <dt> -aveugle </dt><dd> les rï¿œcepteurs ne connaissent ni l'amplitude min et max du signal, ni les diffï¿œrents trajets indirects (s'il y en a).</dd>
   * 
   * </dl>
   * <b>Contraintes</b> :
   * Il y a des interdÃ©pendances sur les paramÃ©tres effectifs. 
   * @param args le tableau des diffÃ©rents arguments.
   * @throws ArgumentsException si un des arguments est incorrect.
   *
   */   
      public  void analyseArguments(String[] args)  throws  ArgumentsException {
      		
         for (int i=0;i<args.length;i++){ // pour chaque argument, nous faisons un traitement
         
              
            if (args[i].matches("-s")){ // indique si on souhaite l'affichage des sondes
               affichage = true;
            }
            else if (args[i].matches("-seed")) { //permet de savoir si on souhaite genereer des données via un germe
               aleatoireAvecGerme = true;
               i++; 
            	
               try { 
                  seed =new Integer(args[i]);
                  
               }
                  catch (Exception e) {
                     throw new ArgumentsException("Valeur du parametre -seed  invalide :" + args[i]);
                  }           		
            }
            
            else if (args[i].matches("-mess")){
               i++; 
            	// traiter la valeur associee
               messageString = args[i];
               if (args[i].matches("[0,1]{7,}")) {
                  messageAleatoire = false;
                  nbBitsMess = args[i].length();
                  
               } 
               else if (args[i].matches("[0-9]{1,6}")) {
                  messageAleatoire = true;
                  nbBitsMess = new Integer(args[i]);
                  if (nbBitsMess < 1) 
                     throw new ArgumentsException ("Valeur du parametre -mess invalide : " + nbBitsMess);
               }
               else 
                  throw new ArgumentsException("Valeur du parametre -mess invalide : " + args[i]);
            }
            
            else if (args[i].matches("-form")){ // Permet de definir la forme voulu par l'utilisateur
            	i++; //on incremente i pour recuperer le parametre
            	messageAnalogique = true ; // On indique au simulateur qu'on souhaite transmettre un signal analogique
            	switch(args[i]){ // On compare la forme voulu par l'utilisateur et celles disponibles
	            	
	            	case "RZ" : forme = "RZ" ;break ;
	            	case "NRZ" : forme = "NRZ";break ;
	            	case "NRZT" : forme = "NRZT";break ;
	            	default : throw new ArgumentsException("Valeur du parametre -form invalide : " + args[i]); // si aucune ne match, on affiche une erreur
	            	
            	}
            	
            }
            else if (args[i].matches("-ampl")){ //Définissons des amplitudes
            	i++; //on incremente i pour recuperer le parametre
            	messageAnalogique = true ;// On indique au simulateur qu'on souhaite transmettre un signal analogique
            	amplMin = new Float(args[i]) ;
            	i++ ;
            	amplMax = new Float(args[i]) ;
            	
            	if(amplMin>=amplMax){ // on verifie que l'amplitude max est bien supérieur à la minimum, sinon exception
            		throw new ArgumentsException("Valeur du parametre -ampl invalide : min doit être inférieur à max" );
            	}
            	
            	
            }
            else if (args[i].matches("-nbEch")){ // Définissions du nombre d'échantillons par bit
            	i++; 
            	messageAnalogique = true ;
            	nbEch = new Integer(args[i]) ;
            	if(nbEch<=0){ // on verifie que l'utilisateur veut bien au moins un échantillon du signal
            		throw new ArgumentsException("Valeur du parametre -nbEch invalide : le nombre d'échantillon doit être positif" );
            	}else if(nbEch<3 && (forme.equals("RZ") || forme.equals("NRZT"))){ // On oblige l'utilisateur à avoir au moins trois échantillons pour ces signaux (faisabilité)
            		throw new ArgumentsException("Valeur du parametre -nbEch invalide : pour le signal "+forme+" nous devons avoir au moins 3 échantillons" );
            	}
            	
            	
            }
            else if (args[i].matches("-snr")){ //Definission du SNR
            	i++; //on incremente i pour recuperer le parametre
            	messageAnalogique = true ;// On indique au simulateur qu'on souhaite transmettre un signal analogique
            	signalBruite = true;
            	snr = new Float(args[i]) ;
            	         	
            }
             
            else throw new ArgumentsException("Option invalide :"+ args[i]); // Si aucun argument ne correspond à ceux définis
          
         }
      
      }
     
    
   	
   /** La m�thode execute effectue un envoi de message par la source de la cha�ne de transmission du Simulateur. 
   *
   * @throws Exception si un probl�me survient lors de l'ex�cution
   *
   */ 
      public void execute() throws Exception {
    	  
    	 
    	 if(messageAnalogique){ //si c'est un signal analogique qu'on souhaite simuler
    		 if(messageAleatoire){ //si c'est une donne aleatoire qu'on souhaite avoir
    			 if(aleatoireAvecGerme){ //si on veut une donnee aleatoire avec germe 
            		 source = new SourceAleatoire(seed,nbBitsMess); // appel du bon constructeur 
            	 }else{
            		 source = new SourceAleatoire(nbBitsMess); 
            	 }
    		 }else{
    			 source = new SourceFixe(messageString);  
    		 }
    		 
    		 //Creation de 4 sondes pour bien suivre et observer l'integrite des donnees
         	 SondeLogique sondeLogique1 = new SondeLogique("sonde_Logique_entree",50);
         	 SondeLogique sondeLogique2 = new SondeLogique("Sonde_Logique_sortie", 50);
         	 SondeAnalogique sondeAnalogique1 = new SondeAnalogique("Sonde_analogique_entree") ;
         	 SondeAnalogique sondeAnalogique2 = new SondeAnalogique("Sonde_analogique_sortie") ;
         	 
         	 //creation de l'emetteur, indication des parametres de transformation
    		 Emetteur emetteur = new Emetteur(forme, nbEch, amplMax, amplMin) ;
    		 destination = new DestinationFinale() ;
    		 
    		 //creation d'un transmetteur
    		 if(signalBruite){
    			 if(aleatoireAvecGerme)	 transmetteurAnalogique = new TransmetteurBruite(snr,seed);
    			 else {transmetteurAnalogique = new TransmetteurBruite(snr);}
    		 }else{
    			 transmetteurAnalogique = new TransmetteurParfaitAnalogique() ;
    		 }
    		 
    		 //Decodage
    		 Recepteur recepteur = new Recepteur(forme, nbEch, amplMax, amplMin);
    		 
    		 if(affichage){
    			 source.connecter(sondeLogique1);
    			 emetteur.connecter(sondeAnalogique1);
    			 transmetteurAnalogique.connecter(sondeAnalogique2);
    			 recepteur.connecter(sondeLogique2);
    		 }
    		 //execution des elements de la chaine de transmission
    		 source.connecter(emetteur);
    		 emetteur.connecter(transmetteurAnalogique);
    		 transmetteurAnalogique.connecter(recepteur);
    		 recepteur.connecter(destination);
    		 source.emettre();
    		 
    	 }else if(!messageAleatoire){ //Si c'est pas analogique 
        	source = new SourceFixe(messageString);
        	
        	SondeLogique sondeLogique1 = new SondeLogique("sondeDataEmis",50);
        	SondeLogique sondeLogique2 = new SondeLogique("sondeDataRecus",50);
        		
        	transmetteurLogique = new TransmetteurParfait() ;
        	destination = new DestinationFinale() ;
        	
        	
        	source.connecter(transmetteurLogique); //Connexion de la source et du transmetteur
        	if(affichage) source.connecter(sondeLogique1); // affichage des sondes si souhaité par l'utilisateur
        	
        	transmetteurLogique.connecter(destination); //Connexion du transmetteur et de la destination finale
        	if(affichage) transmetteurLogique.connecter(sondeLogique2); // affichage des sondes si souhaité par l'utilisateur
      
        	source.emettre(); // on emet le signal
        	
         }else{
        	 if(aleatoireAvecGerme){
        		 source = new SourceAleatoire(seed,nbBitsMess); 
        	 }else{
        		 source = new SourceAleatoire(nbBitsMess); 
        	 }
        	 
        	 
        	SondeLogique sondeLogique1 = new SondeLogique("sondeDataEmis",50);
         	SondeLogique sondeLogique2 = new SondeLogique("sondeDataRecus",50);
         		
         	transmetteurLogique = new TransmetteurParfait() ;
         	destination = new DestinationFinale() ;
         	
         	
         	source.connecter(transmetteurLogique); //Connexion de la source et du transmetteur
         	if(affichage) source.connecter(sondeLogique1); // affichage des sondes si souhaité par l'utilisateur
         	
         	transmetteurLogique.connecter(destination); //Connexion du transmetteur et de la destination finale
         	if(affichage) transmetteurLogique.connecter(sondeLogique2); // affichage des sondes si souhaité par l'utilisateur
       
         	source.emettre(); // on emet le signal
         }
        		 
      	     	      
      }
   
   	   	
   	
   /** La m�thode qui calcule le taux d'erreur binaire en comparant les bits du message �mis avec ceux du message re�u.
   *
   * @return  La valeur du Taux dErreur Binaire.
   */   	   
      public float  calculTauxErreurBinaire() {
      
      	int nbElementsEmis = source.getInformationEmise().nbElements() ; // on recupere le nombre d'elements émis
        int nbBitsFaux = 0 ;
      	int i ;
      	for(i=0;i<nbElementsEmis;i++){
      		if(source.getInformationEmise().iemeElement(i) != destination.getInformationRecue().iemeElement(i)){ // on verifie la correspondance entre ce qu'on a émis et ce qu'on a recu
      			nbBitsFaux++ ;
      		}	
      	}
      	if(nbElementsEmis != 0)	// on n'oublie pas la division par zero n'existe pas
         return (float)nbBitsFaux/ (float)nbElementsEmis;
      	else
      		return 0.0f ;
      }
   
   
   
   
   /** La fonction main instancie un Simulateur � l'aide des arguments param�tres et affiche le r�sultat de l'ex�cution d'une transmission.
   *  @param args les diff�rents arguments qui serviront � l'instanciation du Simulateur.
   */
      public static void main(String [] args) { 
      
         Simulateur simulateur = null;
      	
         try {
            simulateur = new Simulateur(args);
         }
            catch (Exception e) {
               System.out.println(e); 
               System.exit(-1);
            } 
      		
         try {
            simulateur.execute();
            float tauxErreurBinaire = simulateur.calculTauxErreurBinaire();
            String s = "java  Simulateur  ";
            for (int i = 0; i < args.length; i++) {
         		s += args[i] + "  ";
         	}
            
			System.out.println(s + "  =>   TEB : " + tauxErreurBinaire );
         }
            catch (Exception e) {
               System.out.println(e);
               e.printStackTrace();
               System.exit(-2);
            }              	
      }
   	
  

}
