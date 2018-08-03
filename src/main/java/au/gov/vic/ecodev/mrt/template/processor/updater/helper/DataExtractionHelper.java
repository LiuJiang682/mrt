package au.gov.vic.ecodev.mrt.template.processor.updater.helper;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import org.apache.log4j.Logger;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import au.gov.vic.ecodev.common.util.NullSafeCollections;
import au.gov.vic.ecodev.common.util.StringToDateConventor;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;

public class DataExtractionHelper {

	private static final Logger LOGGER = Logger.getLogger(DataExtractionHelper.class);
	
	private final List<String> data;
	
	public DataExtractionHelper(final List<String> data) {
		this.data = data;
	}

	public Date extractDate(int index, final String dateFormat) {
		Date date = null;
		String dateString = (String) new NullSafeCollections(data).get(index);
		
		try {
			StringToDateConventor stringToDateConventor = new StringToDateConventor(dateFormat);
			date = stringToDateConventor.parse(dateString);
		} catch(ParseException e) {
			LOGGER.error(e.getMessage(), e);
		}
		return date;
	}

	public int extractMandatoryFieldIndex(String code) throws TemplateProcessorException {
		if (CollectionUtils.isEmpty(data)) {
			throw new TemplateProcessorException("The header cannot be empty!");
		}
		int index = data.indexOf(code);
		if (Numeral.NOT_FOUND == index) {
			if (Stream.of(Strings.SPACE, Strings.UNDER_LINE, Strings.HYPHEN).anyMatch(code::contains)) {
				index = new VariationHelper(data, code).findIndex();
				if (Numeral.NOT_FOUND == index) {
					throw new TemplateProcessorException("No " + code + " index in the header!");
				}
			} else {
				throw new TemplateProcessorException("No " + code + " index in the header!");
			}
		}
		return index;
	}
	
	public BigDecimal extractBigDecimal(int index) {
		String numberString = (String) new NullSafeCollections(data).get(index);
		return convertStringToBigDecimal(numberString);
	}
	
	protected final BigDecimal convertStringToBigDecimal(final String numberString) {
		BigDecimal number = null;
		if (!StringUtils.isEmpty(numberString)) {
			try {
				number = new BigDecimal(numberString);
			} catch (NumberFormatException e) {
				//Ignore
			}
		}
		return number;
	}
	
}
