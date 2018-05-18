package au.gov.vic.ecodev.mrt.template.processor.updater.tables.index.finder.sl4;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.fields.SL4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class HoleIdIndexFinder {
	
	private final List<String> headers;
	
	public HoleIdIndexFinder(final List<String> headers) {
		if (CollectionUtils.isEmpty(headers)) {
			throw new IllegalArgumentException("Parameter headers cannot be null!");
		}
		this.headers = headers.stream()
				.map(String::toUpperCase)
				.collect(Collectors.toList());
	}

	public int find() throws TemplateProcessorException {
		int index = headers.indexOf(SL4ColumnHeaders.HOLE_ID.getCode().toUpperCase());
		if (Numeral.NOT_FOUND == index) {
			throw new TemplateProcessorException("No Hole_id index in the header");
		}
		return index;
	}
}
