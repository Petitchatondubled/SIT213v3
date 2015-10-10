import static org.junit.Assert.*;

import java.util.Random;

import information.Information;

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
	 * Test method for {@link transmetteurs.TransmetteurBruite#calculPuissanceMoySignal()}.
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
	 * Test method for {@link transmetteurs.TransmetteurBruite#bruitBlancGaussien()}.
	 */
	@Test
	public void testBruitBlancGaussien() {
		
	}

}
