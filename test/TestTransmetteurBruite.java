import static org.junit.Assert.*;

import java.util.Random;

import information.Information;
import information.InformationNonConforme;

import org.junit.Before;
import org.junit.Test;

import destinations.Destination;
import destinations.DestinationFinale;
import sources.Source;
import sources.SourceFixe;
import transmetteurs.*;

/**
 * 
 */

/**
 * @author bendaoud
 *
 */
public class TestTransmetteurBruite {

	Source source ;
	Emetteur emetteur ;
	Recepteur recepteur ;
	Destination  destination ;
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
		
		
	}

	/**
	 * Test method pour la méthode qui calcule la puissance moyenne du signal
	 */
	@Test
	public void testCalculPuissanceMoySignal() {
		//test Basique
		TransmetteurBruite transmetteur = new TransmetteurBruite(1.0f);
		Information<Float> info =  new Information<Float>();
		info.add(2.0f);
		info.add(3.0f);
		info.add(4.0f);
		info.add(-1.0f);
		info.add(0.0f);
		assertEquals(6.0f,transmetteur.calculPuissanceMoySignalRecu(info),0.0f);
		
		//Si le signal reçu est nul
		info =  new Information<Float>();
		info.add(0.0f);
		info.add(0.0f);
		info.add(0.0f);
		info.add(0.0f);
		info.add(0.0f);
		assertEquals(0.0f,transmetteur.calculPuissanceMoySignalRecu(info),0.0f);
		
		//Si le signal reçu est trés grand
		info =  new Information<Float>();
		int i=0;
		for(i=0;i<10000;i++){
			info.add(2.0f);
		}
		assertEquals(4.0f,transmetteur.calculPuissanceMoySignalRecu(info),0.0f);
		
	}

	/**
	 * Test method pour la méthode qui génére le bruit gaussien
	 * @throws InformationNonConforme information non conforme
	 */
	@Test
	public void testBruitBlancGaussien() throws InformationNonConforme {
		
		//Si le bruit est équivalent au signal
		TransmetteurBruite transmetteur = new TransmetteurBruite(0.0f);
		Information<Float> info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(3.9f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <4.1f);
		
		//Si la Pm du signal est deux fois plus importante que le bruit
		transmetteur = new TransmetteurBruite(3.0f);
		info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(1.9f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <2.1f);
		
		//Si la Pm du signal est dix fois plus importante que le bruit
		transmetteur = new TransmetteurBruite(10.0f);
		info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(0.39f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <0.41f);
		
		//Si la Pm du signal est 1MILLIONS DE fois plus importante que le bruit
		transmetteur = new TransmetteurBruite(60.0f);
		info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(0.0000039f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <0.0000041f);
		
		//Si la Pm du signal est deux fois plus petit que le bruit
		transmetteur = new TransmetteurBruite(-3.0f);
		info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(7.9f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <8.1f);

		//Si la Pm du signal est cent fois plus petit que le bruit
		transmetteur = new TransmetteurBruite(-20.0f);
		info =  new Information<Float>();
		for(int i=0;i<400;i++){
			info.add(2.0f);
		}
		transmetteur.recevoir(info);
		assertTrue(399.9f<transmetteur.puissanceMoyBruit && transmetteur.puissanceMoyBruit <400.1f);

	}

}
