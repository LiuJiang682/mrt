package au.gov.vic.ecodev.mrt.constants;

import java.util.regex.Pattern;

import au.gov.vic.ecodev.mrt.api.constants.LogSeverity;


public interface Constants {

	interface Strings {
		static final String EMPTY = "";
		static final String SPACE = " ";
		static final String NUMBER_OF_DATA_RECORDS_ADDED = "Number_of_records_added";
		static final String COMMA = ",";
		static final String COLUMN_HEADERS = "Column_headers";
		static final String NUMBER_OF_DATA_RECORDS_TITLE = "Number_of_data_records";
		static final String ZIP_FILE_EXTENSION = ".zip";
		static final String KEY_H1000 = "H1000";
		static final String KEY_H0532 = "H0532";
		static final String KEY_H0533 = "H0533";
		static final String COLON = ":";
		static final String AZIMUTH_MAG_PRECISION = "Azimuth_Mag_Precision";
		static final String DIP_PRECISION = "DIP_Precision";
		static final String TAB = "\t";
		static final String CURRENT_LINE = "currentLine";
		static final String EOF = "EOF";
		static final String LOG_INFO_HEADER = LogSeverity.INFO.name() + COLON + SPACE;
		static final String LOG_WARNING_HEADER = LogSeverity.WARNING.name() + COLON + SPACE;
		static final String LOG_ERROR_HEADER = LogSeverity.ERROR.name() + COLON + SPACE;
		static final String AZIMUTH_TRUE = "Azimuth_TRUE";
		static final String DATA_RECORD_PREFIX = "D";
		static final String DATE_FORMAT_DD_MMM_YY = "dd-MMM-yy";
		static final String KEY_H0200 = "H0200";
		static final String TEMPLATE_NAME_DS4 = "DS4";
		static final String TITLE_PREFIX = "Title_";
		static final String TEMPLATE_NAME_DG4 = "DG4";
		static final String TEMPLATE_NAME_SL4 = "SL4";
		static final String TEMPLATE_NAME_SG4 = "SG4";
		static final String JOB_NO_MULTIPLY = "Multiply";
		static final String KEY_PREFIX_DUPLICATED = "Duplicated_";
		static final String TEMPLATE = "Template";
		static final String TEMPLATE_PROP_DG4_H1001_MANDATORY_FIELDS_HEADER = "DG4:H1001.MANDATORY.FIELDS.HEADER";
		static final String TEMPLATE_PROP_DG4_H1002_MANDATORY_FIELDS_HEADER = "DG4:H1002.MANDATORY.FIELDS.HEADER";
		static final String TEMPLATE_PROP_DG4_H1003_MANDATORY_FIELDS_HEADER = "DG4:H1003.MANDATORY.FIELDS.HEADER";
		static final String TEMPLATE_PROP_SG4_H1001_MANDATORY_FIELDS_HEADER = "SG4:H1001.MANDATORY.FIELDS.HEADER";
		static final String TEMPLATE_PROP_SG4_H1002_MANDATORY_FIELDS_HEADER = "SG4:H1002.MANDATORY.FIELDS.HEADER";
		static final String TEMPLATE_PROP_SG4_H1003_MANDATORY_FIELDS_HEADER = "SG4:H1003.MANDATORY.FIELDS.HEADER";
		static final String KEY_H0100 = "H0100";
		static final String JSON_SQL_TAG = "-SQL:";
		static final String TEMPLATE_NAME_DL4 = "DL4";
		static final String UNDER_LINE = "_";
		static final String UNDER_LINE_DATA_KEY = "_D";
		static final String PARTIAL_FILE_NAME_KEY = "isPartialFileName";
	}
	
	interface Numeral {
		static final int ZERO = 0;
		static final int ONE = 1;
		static final int TWO = 2;
		static final int THREE = 3;
		static final int FOUR = 4;
		static final int FIVE = 5;
		static final int SIX = 6;
		static final int SEVEN = 7;
		
		static final int NOT_FOUND = au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE;
		static final int INVALID_LINE_NUMBER = au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE;
		static final int INVALID_COLUMN_COUNT = au.gov.vic.ecodev.mrt.api.constants.Constants.Numeral.NEGATIVE_ONE;
	}
	
	interface RegexPattern {

		public static final Pattern PERIODIC_TABLE_ELEMENT_NAME_PATTERN = Pattern.compile("^\\p{Alpha}{1,2}[\\d| |_|-]");
		
	}
}
