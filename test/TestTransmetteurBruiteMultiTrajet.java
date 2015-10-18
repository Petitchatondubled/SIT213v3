import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import information.Information;
import information.InformationNonConforme;
import transmetteurs.TransmetteurBruite;


/**
 * @author Finaritra
 *
 */
public class TestTransmetteurBruiteMultiTrajet {
	TransmetteurBruite transmetteur = null;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDecalageInfo() throws InformationNonConforme {
		Information<Integer> numTraj = new Information<Integer>();
		numTraj.add(1);
		Information<Integer> dt = new Information<Integer>();
		dt.add(30);
		Information<Float> ar = new Information<Float>();
		ar.add(0.5f);
		transmetteur = new TransmetteurBruite(numTraj,dt,ar);
		Information<Float> infoDecale = new Information<Float>();
		Information<Float> info =  new Information<Float>();
		for(int i=0;i<100;i++){
			info.add(2.0f);
		}
		infoDecale = transmetteur.decalageInfo(info, dt, ar);
		for(int i=0;i<30;i++){
		assertTrue(infoDecale.iemeElement(i) == 0);
		}
		for(int i=31;i<70;i++){
			assertTrue(infoDecale.iemeElement(i) == 1.0f);
			}
	}

	@Test
	public void testMax() {
		Information<Integer> numTraj = new Information<Integer>();
		numTraj.add(1);
		Information<Integer> dt = new Information<Integer>();
		dt.add(30);
		Information<Float> ar = new Information<Float>();
		ar.add(0.5f);
		transmetteur = new TransmetteurBruite(numTraj,dt,ar);
		Information<Integer> info =  new Information<Integer>();
		for(int i=0;i<100;i++){
			if(i==50) info.add(6);
			else info.add(2);
		}
		
		assertTrue(transmetteur.max(info)== 6);
	}

	@Test
	public void testBruitMultiTrajet() throws InformationNonConforme {
		Information<Integer> numTraj = new Information<Integer>();
		numTraj.add(1);
		Information<Integer> dt = new Information<Integer>();
		dt.add(30);
		Information<Float> ar = new Information<Float>();
		ar.add(0.5f);
		transmetteur = new TransmetteurBruite(numTraj,dt,ar);
		Information<Float> info =  new Information<Float>();
		Information<Float> infoOrigine =  new Information<Float>();
		for(int i=0;i<50;i++){
			info.add(2.0f);
		}
		for(int i=0;i<50;i++){
			infoOrigine.add(2.0f);
		}
		Information<Float> infoDecale = new Information<Float>();
		transmetteur.recevoir(info);
		for(float f:transmetteur.getInformationEmise())
		{
			infoDecale.add(f);
		}
		
		for(int i=0;i<30;i++){
			assertTrue(infoDecale.iemeElement(i).equals(infoOrigine.iemeElement(i)));//début des info doit être comme les info d'origine
			}
			for(int i=30;i<50;i++){
				assertTrue(infoDecale.iemeElement(i)==3.0);
				}
			for(int i=50;i<80;i++){
				assertTrue(infoDecale.iemeElement(i)==1.0);
				}
	}

}
