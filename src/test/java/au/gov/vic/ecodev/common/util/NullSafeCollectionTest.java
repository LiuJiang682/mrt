package au.gov.vic.ecodev.common.util;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.hamcrest.core.IsNull.nullValue;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class NullSafeCollectionTest {

	@Test
	public void shouldReturnObjectWhenIndexIsCorrect() {
		//Given
		List<Object> list = new ArrayList<>();
		list.add(new String("0"));
		list.add(new String("1"));
		list.add(new String("2"));
		list.add(new String("3"));
		list.add(new String("4"));
		list.add(new String("5"));
		NullSafeCollections nullSafeCollection = new NullSafeCollections(list);
		//When
		Object object = nullSafeCollection.get(2);
		//Then
		assertThat(object, is(notNullValue()));
		assertThat(object, is(equalTo(new String("2"))));
	}
	
	@Test
	public void shouldReturnInstance() {
		//Given
		List<Object> list = new ArrayList<>();
		//When
		NullSafeCollections nullSafeCollection = new NullSafeCollections(list);
		//Then
		assertThat(nullSafeCollection, is(notNullValue()));
	}
	
	@Test
	public void shouldReturnNullWhenListIsNull() {
		//Given
		List<Object> list = null;
		NullSafeCollections nullSafeCollection = new NullSafeCollections(list);
		//When
		Object object = nullSafeCollection.get(0);
		//Then
		assertThat(object, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullWhenListIsEmpty() {
		//Given
		List<Object> list = new ArrayList<>();
		NullSafeCollections nullSafeCollection = new NullSafeCollections(list);
		//When
		Object object = nullSafeCollection.get(0);
		//Then
		assertThat(object, is(nullValue()));
	}
	
	@Test
	public void shouldReturnNullWhenListIsLessThenIndex() {
		//Given
		List<Object> list = new ArrayList<>();
		list.add(new Object());
		NullSafeCollections nullSafeCollection = new NullSafeCollections(list);
		//When
		Object object = nullSafeCollection.get(3);
		//Then
		assertThat(object, is(nullValue()));
	}
}
