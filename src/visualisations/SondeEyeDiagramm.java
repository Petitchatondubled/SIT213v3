   package visualisations;
	
	
   import information.Information;


/** 
 * Classe r�alisant l'affichage du diagramme de l'oeil pour le signal re�u par le recepteur
 * @author prou
 */
   public class SondeEyeDiagramm extends Sonde <Float> {
          
   int nEch = 0;
	/**
	   * pour construire une sonde diagramme de l'oeil
	   * @param nom  le nom de la fen�tre d'affichage
	   * @param nEch 
	   */
     public SondeEyeDiagramm(String nom,int nEch) {
        super(nom);
        this.nEch = nEch;
     }
   
   
   	 
      public void recevoir (Information <Float> information) { 
         informationRecue = information;
         int nbElements = information.nbElements();
         float [] table = new float[nbElements];
         int i = 0;
         for (float f : information) {
            table[i] = f;
            i++;
         }
         new VueCourbeEyeDiagramm (table,nom,nEch); 
      }   	
   
   
   }