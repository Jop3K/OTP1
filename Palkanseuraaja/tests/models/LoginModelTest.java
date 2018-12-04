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
	}

	@Test
	@TestInJfxThread
	public void testLoginFieldValidation() {
                fail = new LoginModel("", "password");
		assertFalse(fail.loginFieldValidation());
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
                fail = new LoginModel("username", "");
		assertFalse(fail.loginFieldValidation());
		assertEquals(fail.getAlert().getHeaderText(), "Syötä käyttäjätunnus ja salasana");
		
                fail = new LoginModel("username", "password");
		assertTrue(fail.loginFieldValidation());
		
	}

}
