package modele.test;

import modele.Date;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

public class DateTest {
	
	private Date date1, date2, date3, date4;

	@Before
	public void setUp() {
		date1 = new Date(27,6,1248);
		date2 = new Date(3,64);
		date3 = new Date(-800);
		date4 = new Date();
	}
	
	@Test
	public void testerConstructeur1() {
		assertTrue(date1.getJour() == 27);
		assertTrue(date1.getMois() == 6);
		assertTrue(date1.getAnnee() == 1248);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseConstructeur11() {
		new Date(40,6,10);
	}

	@Test(expected=AssertionError.class)
	public void testerRobustesseConstructeur12() {
		new Date(25,-6,27);
	}

	@Test
	public void testerConstructeur2() {
		assertTrue(date2.getJour() == 0);
		assertTrue(date2.getMois() == 3);
		assertTrue(date2.getAnnee() == 64);
	}

	@Test
	public void testerConstructeur3() {
		assertTrue(date3.getJour() == 0);
		assertTrue(date3.getMois() == 0);
		assertTrue(date3.getAnnee() == -800);
	}

	@Test
	public void testerConstructeur4() {
		assertTrue(date4.getJour() == 0);
		assertTrue(date4.getMois() == 0);
		assertTrue(date4.getAnnee() == 0);
	}

	@Test
	public void testerSetJour() {
		date4.setJour(27);
		assertTrue(date4.getJour() == 27);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseSetJour1() {
		date1.setJour(-1);
	}

	@Test(expected=AssertionError.class)
	public void testerRobustesseSetJour2() {
		date2.setJour(32);
	}

	@Test
	public void testerSetMois() {
		date1.setMois(12);
		assertTrue(date1.getMois() == 12);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseMois1() {
		date3.setMois(-1);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseMois2() {
		date3.setMois(13);
	}
	
	@Test
	public void testerSetAnnee() {
		date2.setAnnee(2017);
		assertTrue(date2.getAnnee() == 2017);
	}
	
	@Test
	public void testerSetDate() {
		date4.setDate(5,6,2018);
		assertTrue(date4.getJour() == 5);
		assertTrue(date4.getMois() == 6);
		assertTrue(date4.getAnnee() == 2018);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseSetDate1() {
		date4.setDate(-8,8,1347);
	}
	
	@Test(expected=AssertionError.class)
	public void testerRobustesseSetDate2() {
		date4.setDate(9,-42,1347);
	}
	
	@Test
	public void testerEquals() {
		int i = 0;
		assertFalse(date1.equals(i));
		assertFalse(date1.equals(date2));
		date2.setJour(date1.getJour());
		assertFalse(date1.equals(date2));
		date2.setMois(date1.getMois());
		assertFalse(date1.equals(date2));
		date2.setAnnee(date1.getAnnee());
		assertTrue(date1.equals(date2));
	}
	
	@Test
	public void testerClone() {
		Date date5 = date1.clone();
		assertTrue(date5.equals(date1));
	}

	@Test
	public void testerToInteger() {
		assertTrue(date4.toInteger() == 0);
		assertTrue(date3.toInteger() == - 8000000);
		
	}
	
	@Test
	public void testerCompareTo() {
		assertTrue(date1.compareTo(date4) > 0);
		assertFalse(date2.compareTo(date1) > 0);
	}
	
	@Test
	public void testerToString() {
		assertEquals(date1.toString(), "27/6/1248");
		assertEquals(date2.toString(), "3/64");
		assertEquals(date3.toString(), "-800");
		assertEquals(date4.toString(), "0");
	}
	
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(DateTest.class.getName());
	}
}
