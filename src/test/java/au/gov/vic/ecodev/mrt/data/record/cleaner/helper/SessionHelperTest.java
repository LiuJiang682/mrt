package au.gov.vic.ecodev.mrt.data.record.cleaner.helper;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.data.record.cleaner.persistent.model.Session;

public class SessionHelperTest {

	private SessionHelper testInstance;
	private List<Map<String, Object>> result;
	
	@Test
	public void shouldReturn2UniqueTemplate() {
		//Given
		result = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("ID", 100l);
		map.put("TEMPLATE", "MRT,mrt,vgpHydro");
		result.add(map);
		givenTestInstance();
		//When
		List<Session> sessionsToClean = testInstance.getSession();
		//Then
		assertThat(sessionsToClean, is(notNullValue()));
		assertThat(sessionsToClean.size(), is(equalTo(1)));
		Session session = sessionsToClean.get(0);
		assertThat(session.getSessionId(), is(equalTo(100l)));
		List<String> templates = session.getTemplateList();
		assertThat(templates, is(notNullValue()));
		assertThat(templates.size(), is(equalTo(2)));
		assertThat(templates.get(0), is(equalTo("MRT")));
		assertThat(templates.get(1), is(equalTo("VGPHYDRO")));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		result = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		map.put("ID", 100l);
		map.put("TEMPLATE", "MRT");
		result.add(map);
		givenTestInstance();
		//When
		//Then
		assertThat(testInstance, is(notNullValue()));
	}
	
	public void shouldRaiseExceptionWhenResultIsNull() {
		//Given
		List<Map<String, Object>> result = null;
		//When
		new SessionHelper(result);
		fail("Program reached unexpected point!");
	}

	private void givenTestInstance() {
		testInstance = new SessionHelper(result);
	}
}
