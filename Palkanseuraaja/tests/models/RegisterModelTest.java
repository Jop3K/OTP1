package models;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;

import application.Main;
import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
@RunWith(JfxRunner.class)
public class RegisterModelTest extends TestCase{
	


	RegisterModel fail, pass;

	@Before
    public void setUp() throws Exception {
    	fail = new RegisterModel("name", "lastname", "username", "password","password");
    	
    }
	@Test
    @TestInJfxThread
    public void testFirstnameAndLastName() {
		//Names needs to be minimum two character long
		fail.setfName("M");
		fail.setlName("M");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimen pitää olla vähintään kaksi merkkiä pitkä!");
		
		//Names doesn't include special characters or numbers
		fail.setfName("Moi*");
		fail.setlName("Moi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("Moi/");
		fail.setlName("Moi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("Moi");
		fail.setlName("M.oi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("M@i");
		fail.setlName("Moi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("Mo½");
		fail.setlName("Moi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("Moi1");
		fail.setlName("Moi");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		fail.setfName("Moi");
		fail.setlName("Moi4");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Etu- ja sukunimi ei saa sisältää erikoismerkkejä tai numeroita!");
		
		
		
	}
    @Test
    @TestInJfxThread
	public void testEmptyRegisterForm() {
        
		fail = new RegisterModel("","","","","");
		Assert.assertFalse(fail.formValidation(fail));
		Assert.assertEquals(fail.getAlert().getHeaderText(), "Täytä pakolliset kentät!");
		
	}
    @Test
    @TestInJfxThread
    public void testMandatoryFieldFilledInRegisterForm() {
    	
    	
    	assertTrue(fail.formValidation(fail));
    }
    @Test
    @TestInJfxThread
    public void testPasswordsDontMatch() {
    	
    	fail.setPw2("passworddontmatch");
    	assertFalse(fail.formValidation(fail));
    	assertEquals(fail.getAlert().getHeaderText(), "Salasanat eivät täsmää!");
    }
    @Test
    @TestInJfxThread
    public void testPasswordTooShort() {
    	//Four characters is minimum
    	fail.setPw1("123");
    	fail.setPw2("123");
    	assertFalse(fail.formValidation(fail));
    	assertEquals(fail.getAlert().getHeaderText(), "Salasanan pitää olla vähintään neljä merkkiä!");
    }
    @Test
    @TestInJfxThread
    public void testUsernameTooShort() {
    	//Three characters is minimum
    	fail.setuName("mm");
    	assertFalse(fail.formValidation(fail));
    	assertEquals(fail.getAlert().getHeaderText(), "Käyttäjänimen pitää olla vähintään kolme merkkiä!");
    }

    
    
    
    


}
