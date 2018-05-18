package au.gov.vic.ecodev.mrt.template.processor.updater.tables.index.finder.sl4;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import au.gov.vic.ecodev.mrt.fixture.TestFixture;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class HoleIdIndexFinderTest {
	
	private HoleIdIndexFinder holeIdIndexFinder;
	
	@Test
	public void shouldReturnCorrectIndex() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		// When
		int index = holeIdIndexFinder.find();
		// Then
		assertThat(index, is(equalTo(0)));
	}

	@Test
	public void shouldReturnInstance() {
		// Given
		givenTestInstance();
		// When
		// Then
		assertThat(holeIdIndexFinder, is(notNullValue()));
	}

	@Test(expected = IllegalArgumentException.class)
	public void shouldRaiseExceptionWhenListIsNull() {
		// Given
		List<String> headers = null;
		// When
		new HoleIdIndexFinder(headers);
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionWhenListIsNotContainsHoleId() throws TemplateProcessorException {
		// Given
		List<String> headers = new ArrayList<>();
		headers.add("abc");
		// When
		new HoleIdIndexFinder(headers).find();
		fail("Program reached unexpected point!");
	}
	
	@Test(expected = TemplateProcessorException.class)
	public void shouldRaiseExceptionHoleIdIndexWhenHeaderIsAbsent() throws TemplateProcessorException {
		// Given
		givenTestInstance();
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.remove(0);
		// When
		new HoleIdIndexFinder(headers).find();
		fail("Program reached unexpected point!");
	}
	
	private void givenTestInstance() {
		List<String> headers = new ArrayList<>();
		headers.add("Hole_id");
		
		holeIdIndexFinder = new HoleIdIndexFinder(headers);
	}
}
