package modele.test;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import modele.Relation;

public class RelationTest {

	private Relation relation1, relation2;
	
	@Before 
	public void setUp() {
		relation1 = new Relation();
		relation2 = new Relation("Michel","père");
	}
	
	@Test
	public void testerConstructeur1() {
		assertEquals(relation1.getNomPersoImplique(),"");
		assertEquals(relation1.getType(),"");
	}
	
	@Test
	public void testerConstructeur2() {
		assertEquals(relation2.getNomPersoImplique(),"Michel");
		assertEquals(relation2.getType(),"père");
	}
	
	@Test
	public void testerSetNomPersoImplique() {
		relation1.setNomPersoImplique("Louis");
		assertEquals(relation1.getNomPersoImplique(),"Louis");
		relation2.setNomPersoImplique("Micheline");
		assertEquals(relation2.getNomPersoImplique(),"Micheline");
	}
	
	@Test
	public void testerSetType() {
		relation1.setType("frère");
		assertEquals(relation1.getType(),"frère");
		relation2.setType("soeur");
		assertEquals(relation2.getType(),"soeur");
	}

	@Test
	public void testerClone() {
		Relation relation3 = relation1.clone();
		assertEquals(relation3.getNomPersoImplique(),relation1.getNomPersoImplique());
		assertEquals(relation3.getType(),relation1.getType());
	}
	
	public static void main(String[] args) {
		org.junit.runner.JUnitCore.main(RelationTest.class.getName());
	}
	
}
