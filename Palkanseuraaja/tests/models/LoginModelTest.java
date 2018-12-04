package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
@RunWith(JfxRunner.class)
public class LoginModelTest {
	LoginModelRefactored fail;
	@Before
	public void setUp() throws Exception {
	}

	@Test
	@TestInJfxThread
	public void testLoginFieldValidation() {
                fail = new LoginModelRefactored("", "password");
		assertFalse(fail.loginFieldValidation());
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
                fail = new LoginModelRefactored("username", "");
		assertFalse(fail.loginFieldValidation());
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
                fail = new LoginModelRefactored("username", "password");
		assertTrue(fail.loginFieldValidation());
		
	}

}
