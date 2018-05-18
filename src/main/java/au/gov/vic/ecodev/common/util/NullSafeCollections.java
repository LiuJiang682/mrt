package au.gov.vic.ecodev.common.util;

import java.util.Collection;

import org.springframework.util.CollectionUtils;

public class NullSafeCollections {

	private final Collection<?> collection;
	
	public NullSafeCollections(Collection<?> collection) {
		this.collection = collection;
	}

	public Object get(final int index) {
		if (!CollectionUtils.isEmpty(collection) 
				&& (index < collection.size())) {
			return collection.toArray()[index];
		}
		return null;
	}
	
}
