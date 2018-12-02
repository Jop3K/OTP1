package models;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.saxsys.javafx.test.JfxRunner;
import de.saxsys.javafx.test.TestInJfxThread;
@RunWith(JfxRunner.class)
public class LoginModelTest {
	LoginModel fail;
	@Before
	public void setUp() throws Exception {
		fail = new LoginModel();
	}

	@Test
	@TestInJfxThread
	public void testLoginFieldValidation() {
		assertFalse(fail.loginFieldValidation("", "password"));
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
		assertFalse(fail.loginFieldValidation("username", ""));
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
		assertTrue(fail.loginFieldValidation("username", "password"));
		
	}

}
