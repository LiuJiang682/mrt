package au.gov.vic.ecodev.mrt.rest.service.template;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TemplateDataServicesImplTest {

	@Autowired
	private TemplateDataServicesImpl testInstance;
	
//	@Ignore
	@Test
	public void shouldReturnAllTemplateData() throws Exception {
		//Given
		String batchId = "100";
		//When
		Map<String, List<Map<String, Object>>> results = testInstance.getAllTemplateData(batchId);
		//Then
		assertThat(results, is(notNullValue()));
		assertThat(results.isEmpty(), is(false));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
}
