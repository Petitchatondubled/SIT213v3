package sources;

import information.Information;

import java.util.Random;

public class SourceAleatoire extends Source<Boolean>{
	
	/**
	 * Classe permettant de generer une suite de bits al�atoire avec le germe.
	 * le germe permet d'avoir la m�me suite binaire � chaque r�alisation
	 * @param seed  germe utilis� pour generer une suite de bit al�atoire
	 * @param nb  taille de la donn�e � emettre
	 */
	public SourceAleatoire(int seed,int nb){

		   Random generator = new Random(seed);
		   Boolean[] information = new Boolean[nb] ;
		   int i = 0 ;
		   
		   for(i=0;i<nb;i++){
			   
				   information[i] = generator.nextBoolean();
			   
		   }
		   
		   informationGeneree = new Information<Boolean>(information);
		   
		  
	}
	/**
	 * Classe permettant de generer une suite de bits al�atoire sans le germe.
	 * @param nb  taille de la donn�e � emettre
	 */
	public SourceAleatoire(int nb){

		 
		   Random generator = new Random();
		   Boolean[] information = new Boolean[nb] ;
		   int i = 0 ;
		   for(i=0;i<nb;i++){			   
			   information[i] = generator.nextBoolean();	   
		   }
		   
		   informationGeneree = new Information<Boolean>(information);
		   
	}
	
	 

}
