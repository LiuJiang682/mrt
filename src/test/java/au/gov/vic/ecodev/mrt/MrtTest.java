package au.gov.vic.ecodev.mrt;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class MrtTest {

	@Test
	public void contextLoads() throws Exception {
		
	}
	
	@BeforeClass
	public static void setUp() {
		TestFixture.initSetup();
	}
}
