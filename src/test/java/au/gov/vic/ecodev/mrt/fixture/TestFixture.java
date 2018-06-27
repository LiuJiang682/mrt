package au.gov.vic.ecodev.mrt.fixture;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.template.context.properties.DefaultStringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.context.properties.StringTemplateProperties;
import au.gov.vic.ecodev.mrt.template.fields.Ds4ColumnHeaders;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.DefaultMessage;
import au.gov.vic.ecodev.mrt.template.loader.fsm.model.Message;
import au.gov.vic.ecodev.mrt.template.processor.context.TemplateProcessorContext;
import au.gov.vic.ecodev.mrt.template.processor.exception.TemplateProcessorException;
import au.gov.vic.ecodev.mrt.template.properties.TemplateProperties;

public class TestFixture {

	public static List<File> getListOfFiles(final String dir) throws IOException {
		return Files.walk(Paths.get(dir)).filter(Files::isRegularFile).map(Path::toFile).collect(Collectors.toList());
	}

	public static Message getMessageWithListOfFileNames() {
		Message message = new DefaultMessage();
		message.setFileNames(Arrays.asList("mrt"));
		return message;
	}

	public static File getFile(String string) throws IOException {
		Path path = Paths.get(string);
		path = Files.write(path, string.getBytes());
		return path.toFile();
	}

	public static File getZipFile(final String string) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Test String");

		File f = new File(string);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("mytext.txt");
		out.putNextEntry(e);

		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();

		out.close();
		return f;
	}

	public static File getDirectoryZipFile(final String string) throws IOException {
		StringBuilder sb = new StringBuilder();
		sb.append("Test String");

		File f = new File(string);
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
		ZipEntry e = new ZipEntry("abc/mytext.txt");
		out.putNextEntry(e);

		byte[] data = sb.toString().getBytes();
		out.write(data, 0, data.length);
		out.closeEntry();

		out.close();
		return f;
	}

	public static List<String> getSl4RequiredFields() {
		String[] fields = { "H0002", "H0005", "H0202", "H0203", "H0400", "H0401", "H0402", "H0501", "H0502", "H0503",
				"H0530", "H0531", "H0532", "H0533", "H1000", "D" };
		List<String> requiredFields = Arrays.asList(fields);
		return requiredFields;
	}

	public static String[] getMandatoryColumns() {
		String[] mandatoryColumns = { "Hole_id", "Easting_MGA", "Northing_MGA", "Elevation", "Total Hole Depth",
				"Drill Code", "Dip", "Azimuth_MAG" };
		return mandatoryColumns;
	}

	public static String[] getMandatoryAmgColumns() {
		String[] mandatoryColumns = { "Hole_id", "Easting_AMG", "Northing_AMG", "Elevation", "Total Hole Depth",
				"Drill Code", "Dip", "Latitude", "Longitude" };
		return mandatoryColumns;
	}

	public static List<String> getColumnHeaderList() {
		return Arrays.asList(TestFixture.getMandatoryColumns());
	}

	public static List<String> getAMGColumnHeaderList() {
		return Arrays.asList(TestFixture.getMandatoryAmgColumns());
	}

	public static List<String> getDsMandatoryColumnList() {
		return Arrays.asList(getDs4MandatoryColumns());
	}

	public static String[] getDs4MandatoryColumns() {
		String[] mandatoryColumns = { "Hole_id", "Surveyed_Depth", "Dip", "Azimuth_MAG" };
		return mandatoryColumns;
	}

	public static String[] getDs4AzimuthTrueColumns() {
		String[] mandatoryColumns = { "Hole_id", "Surveyed_Depth", "Dip", "Azimuth_TRUE" };
		return mandatoryColumns;
	}

	public static List<String> getDs4AzimuthTrueColumnsList() {
		return Arrays.asList(getDs4AzimuthTrueColumns());
	}

	public static List<String> getDList() {
		List<String> d1List = new ArrayList<>();
		d1List.add("STD001");
		d1List.add("630500");
		d1List.add("5845150");
		d1List.add("250");
		d1List.add("335.9");
		d1List.add("DDT");
		d1List.add("-60");
		d1List.add("55");
		return d1List;
	}

	public static List<String> getProjectNameList() {
		List<String> projectNameList = new ArrayList<>();
		projectNameList.add("Project_name");
		projectNameList.add("Kryptonite");
		return projectNameList;
	}

	public static List<String> getProjectZoneList() {
		List<String> projectZoneList = new ArrayList<>();
		projectZoneList.add("Prjection_zone");
		projectZoneList.add("54");
		return projectZoneList;
	}

	public static List<String> getDateList() {
		List<String> data = new ArrayList<>();
		data.add("Start_date_of_data_acquisition");
		data.add("12-Nov-12");
		return data;
	}

	public static TemplateProperties getSl4TemplateProperties() {
		DefaultStringTemplateProperties mockRequiredFields = new DefaultStringTemplateProperties(MANDATORY_FIELD_SL4);
		return mockRequiredFields;
	}

	public static List<String> getMandatoryFieldSl4() {
		return Arrays.asList(MANDATORY_FIELD_SL4.split(","));
	}

	public static final String MANDATORY_FIELD_SL4 = "H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0532,H0533,H1000,D";

	public static TemplateProperties getDs4TemplateProperties() {
		DefaultStringTemplateProperties mockRequiredFields = new DefaultStringTemplateProperties(MANDATORY_FIELD_DS4);
		return mockRequiredFields;
	}

	public static List<String> getMandatoryFieldDs4() {
		return Arrays.asList(MANDATORY_FIELD_DS4.split(","));
	}

	public static final String MANDATORY_FIELD_DS4 = "H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0503,H0530,H0531,H0534,H0535,H1000,D";

	public static List<File> getListOfFiles() throws IOException {
		return Files.walk(Paths.get("src/test/resources/testData")).filter(Files::isRegularFile).map(Path::toFile)
				.collect(Collectors.toList());
	}

	public static List<File> getDummyFileList(final String fileName) throws Exception {
		File file = new File(fileName);
		if (!file.exists()) {
			file.createNewFile();
		}
		return Arrays.asList(file);
	}

	public static void deleteFilesInDirectory(String directory) throws Exception {
		Files.walk(Paths.get(directory)).filter(Files::isRegularFile).map(Path::toFile).forEach(File::delete);
	}

	public static List<String> getDrillingCodeList() {
		List<String> drillingCodeList = getOneDrillingCodeList();

		drillingCodeList.add("DD");
		drillingCodeList.add("RC");
		return drillingCodeList;
	}

	public static List<String> getOneDrillingCodeList() {
		List<String> drillingCodeList = new ArrayList<>();
		drillingCodeList.add("Drill_code");

		return drillingCodeList;
	}

	public static List<String> getDrillingCompanyList() {
		List<String> drillingCompanyList = getOneDrillingCompanyList();

		drillingCompanyList.add("Drill Faster Pty Ltd");
		drillingCompanyList.add("Drill Well Pty Ltd");
		return drillingCompanyList;
	}

	public static List<String> getOneDrillingCompanyList() {
		List<String> drillingCompanyList = new ArrayList<>();
		drillingCompanyList.add("Drill_contractor");

		return drillingCompanyList;
	}

	public static List<String> getHeadlessDrillingCode() {
		return Arrays.asList("DD", "RC");
	}

	public static List<String> getHeadlessDrillingCompanyList() {
		return Arrays.asList("Drill Faster Pty Ltd", "Drill Well Pty Ltd");
	}

	public static StringTemplateProperties getH0002ValidatorClassName() {
		return new DefaultStringTemplateProperties(VALIDATOR_CLASS_NAME_H0002);
	}

	private static final String VALIDATOR_CLASS_NAME_H0002 = "au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0002Validator";

	public static TemplateProperties getH0005ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0005Validator");
	}

	public static TemplateProperties getDs4H0202ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0202Validator");
	}

	public static TemplateProperties getSl4H0202ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0202Validator");
	}

	public static TemplateProperties getH0203ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0203Validator");
	}

	public static TemplateProperties getH0400ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0400Validator");
	}

	public static TemplateProperties getH0401ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0401Validator");
	}

	public static TemplateProperties getH0402ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0402Validator");
	}

	public static TemplateProperties getH0501ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0501Validator");
	}

	public static TemplateProperties getH0502ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0502Validator");
	}

	public static TemplateProperties getH0503ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0503Validator");
	}

	public static TemplateProperties getH0530ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0530Validator");
	}

	public static TemplateProperties getH0531ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0531Validator");
	}

	public static TemplateProperties getDs4H0534ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0534Validator");
	}

	public static TemplateProperties getSl4H0532ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0532Validator");
	}

	public static TemplateProperties getH0535ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H0535Validator");
	}

	public static TemplateProperties getH0533ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H0533Validator");
	}

	public static TemplateProperties getDs4H1000ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.H1000Validator");
	}

	public static TemplateProperties getSl4H1000ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.H1000Validator");
	}

	public static TemplateProperties getDs4DataValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.ds4.DValidator");
	}

	public static TemplateProperties getSl4DataValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sl4.DValidator");
	}

	public static void givenDs4TemplateProperties(TemplateProcessorContext mockContext)
			throws TemplateProcessorException {
		when(mockContext.getTemplateContextProperty(eq("DS4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(getDs4TemplateProperties());
		when(mockContext.getTemplateContextProperty(eq("DS4:AZIMUTHMAG.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
		when(mockContext.getTemplateContextProperty(eq("DS4:DIP.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
		when(mockContext.getTemplateContextProperty(eq("DS4:H0002"))).thenReturn(getH0002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0005"))).thenReturn(getH0005ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0202"))).thenReturn(getDs4H0202ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0203"))).thenReturn(getH0203ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0400"))).thenReturn(getH0400ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0401"))).thenReturn(getH0401ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0402"))).thenReturn(getH0402ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0501"))).thenReturn(getH0501ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0502"))).thenReturn(getH0502ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0503"))).thenReturn(getH0503ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0530"))).thenReturn(getH0530ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0531"))).thenReturn(getH0531ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0534"))).thenReturn(getDs4H0534ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H0535"))).thenReturn(getH0535ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:H1000"))).thenReturn(getDs4H1000ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DS4:D"))).thenReturn(getDs4DataValidatorClassName());
	}

	public static void givenSl4TemplateProperties(TemplateProcessorContext mockContext)
			throws TemplateProcessorException {
		when(mockContext.getTemplateContextProperty(eq("SL4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(getSl4TemplateProperties());
		when(mockContext.getTemplateContextProperty(eq("SL4:AZIMUTHMAG.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
		when(mockContext.getTemplateContextProperty(eq("SL4:DIP.PRECISION")))
				.thenReturn(new DefaultStringTemplateProperties("6"));
		when(mockContext.getTemplateContextProperty(eq("SL4:H0002"))).thenReturn(getH0002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0005"))).thenReturn(getH0005ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0202"))).thenReturn(getSl4H0202ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0203"))).thenReturn(getH0203ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0400"))).thenReturn(getH0400ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0401"))).thenReturn(getH0401ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0402"))).thenReturn(getH0402ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0501"))).thenReturn(getH0501ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0502"))).thenReturn(getH0502ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0503"))).thenReturn(getH0503ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0530"))).thenReturn(getH0530ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0531"))).thenReturn(getH0531ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0532"))).thenReturn(getSl4H0532ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H0533"))).thenReturn(getH0533ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:H1000"))).thenReturn(getSl4H1000ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SL4:D"))).thenReturn(getSl4DataValidatorClassName());
	}

	public static List<String> givenAzimuthMagList() {
		List<String> azimuthMagList = new ArrayList<>();
		azimuthMagList.add(Ds4ColumnHeaders.AZIMUTH_MAG.getCode());
		return azimuthMagList;
	}

	public static List<String> getNumList() {
		List<String> numList = new ArrayList<>();
		numList.add("Number_of_data_records");
		numList.add("3");
		return numList;
	}

	public static void givenDl4TemplateProperties(TemplateProcessorContext mockContext)
			throws TemplateProcessorException {
		when(mockContext.getTemplateContextProperty(eq("DL4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(getDl4TemplateProperties());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0002"))).thenReturn(getH0002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0005"))).thenReturn(getH0005ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0202"))).thenReturn(getDl4H0202ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0203"))).thenReturn(getH0203ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0400"))).thenReturn(getH0400ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0401"))).thenReturn(getH0401ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0402"))).thenReturn(getH0402ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0501"))).thenReturn(getH0501ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0502"))).thenReturn(getH0502ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0530"))).thenReturn(getH0530ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H0531"))).thenReturn(getH0531ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:H1000"))).thenReturn(getDl4H1000ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DL4:D"))).thenReturn(getDl4DataValidatorClassName());
	}

	public static TemplateProperties getDl4DataValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.DValidator");
	}

	public static TemplateProperties getDl4H1000ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.H1000Validator");
	}

	public static TemplateProperties getDl4H0202ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dl4.H0202Validator");
	}

	public static TemplateProperties getDl4TemplateProperties() {
		DefaultStringTemplateProperties mockRequiredFields = new DefaultStringTemplateProperties(MANDATORY_FIELD_DL4);
		return mockRequiredFields;
	}

	public static final String MANDATORY_FIELD_DL4 = "H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0530,H0531,H1000,D";
	public static final String DG4_H1001_MANDATORY_FIELDS = "H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og";
	private static final String DG4_H1002_MANDATORY_FIELDS = "H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og";
	private static final String DG4_H1003_MANDATORY_FIELDS = "H,He,Li,Be,B,C,N,O,F,Ne,Na,Mg,Al,Si,P,S,Cl,Ar,K,Ca,Sc,Ti,V,Cr,Mn,Fe,Co,Ni,Cu,Zn,Ga,Ge,As,Se,Br,Kr,Rb,Sr,Y,Zr,Nb,Mo,Tc,Ru,Rh,Pd,Ag,Cd,In,Sn,Sb,Te,I,Xe,Cs,Ba,La,Ce,Pr,Nd,Pm,Sm,Eu,Gd,Tb,Dy,Ho,Er,Tm,Yb,Lu,Hf,Ta,W,Re,Os,Ir,Pt,Au,Hg,Tl,Pb,Bi,Po,At,Rn,Fr,Ra,Ac,Th,Pa,U,Np,Pu,Am,Cm,Bk,Cf,Es,Fm,Md,No,Lr,Rf,Db,Sg,Bh,Hs,Mt,Ds,Rg,Cn,Nh,Fl,Mc,Lv,Ts,Og";

	public static String[] getDl4MandatoryColumns() {
		String[] mandatoryColumns = { "Hole_id", "Depth_from", "Depth_to" };
		return mandatoryColumns;
	}

	public static List<String> getDl4ColumnHeaderList() {
		return Arrays.asList(getDl4MandatoryColumns());
	}

	public static void givenDg4TemplateProperties(TemplateProcessorContext mockContext)
			throws TemplateProcessorException {
		when(mockContext.getTemplateContextProperty(eq("DG4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(getDg4TemplateProperties());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0002"))).thenReturn(getH0002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0005"))).thenReturn(getH0005ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0202")))
			.thenReturn(getDg4H0202ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0203")))
			.thenReturn(getH0203ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0400"))).thenReturn(getH0400ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0401"))).thenReturn(getH0401ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0402"))).thenReturn(getH0402ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0501")))
			.thenReturn(getH0501ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0502")))
			.thenReturn(getH0502ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0530")))
			.thenReturn(getH0530ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0531")))
			.thenReturn(getH0531ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0600")))
			.thenReturn(getH0600ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0601")))
			.thenReturn(getH0601ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0602")))
			.thenReturn(getH0602ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0700")))
			.thenReturn(getH0700ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0701")))
			.thenReturn(getH0701ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0702")))
			.thenReturn(getH0702ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0800")))
			.thenReturn(getH0800ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0801")))
			.thenReturn(getH0801ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H0802")))
			.thenReturn(getH0802ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1000")))
			.thenReturn(getDg4H1000ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1001")))
			.thenReturn(getDg4H1001ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1002")))
			.thenReturn(getDg4H1002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1003")))
			.thenReturn(getDg4H1003ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1006")))
			.thenReturn(getDg4H1006ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:D")))
			.thenReturn(getDg4DataValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1001.MANDATORY.FIELDS.HEADER")))
			.thenReturn(getDg4H1001MadatoryFieldHeaders());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1002.MANDATORY.FIELDS.HEADER")))
			.thenReturn(getDg4H1002MadatoryFieldHeaders());
		when(mockContext.getTemplateContextProperty(eq("DG4:H1003.MANDATORY.FIELDS.HEADER")))
			.thenReturn(getDg4H1003MadatoryFieldHeaders());
	}

	public static TemplateProperties getDg4H1003MadatoryFieldHeaders() {
		DefaultStringTemplateProperties requiredFields = new DefaultStringTemplateProperties(DG4_H1003_MANDATORY_FIELDS);
		return requiredFields;
	}

	public static TemplateProperties getDg4H1002MadatoryFieldHeaders() {
		DefaultStringTemplateProperties requiredFields = new DefaultStringTemplateProperties(DG4_H1002_MANDATORY_FIELDS);
		return requiredFields;
	}

	public static TemplateProperties getDg4H1001MadatoryFieldHeaders() {
		DefaultStringTemplateProperties requiredFields = new DefaultStringTemplateProperties(DG4_H1001_MANDATORY_FIELDS);
		return requiredFields;
	}

	public static TemplateProperties getDg4H1006ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1006Validator");
	}

	public static TemplateProperties getDg4H1003ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1003Validator");
	}

	public static TemplateProperties getDg4H1002ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1002Validator");
	}

	public static TemplateProperties getDg4H1001ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1001Validator");
	}

	public static TemplateProperties getH0802ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0802Validator");
	}

	public static TemplateProperties getH0801ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0801Validator");
	}

	public static TemplateProperties getH0800ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0800Validator");
	}

	public static TemplateProperties getH0702ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0702Validator");
	}

	public static TemplateProperties getH0701ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0701Validator");
	}

	public static TemplateProperties getH0700ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0700Validator");
	}

	public static TemplateProperties getH0602ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0602Validator");
	}

	public static TemplateProperties getH0601ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0601Validator");
	}

	public static TemplateProperties getH0600ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.common.H0600Validator");
	}

	public static TemplateProperties getDg4DataValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.DValidator");
	}

	public static TemplateProperties getDg4H1000ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H1000Validator");
	}

	public static TemplateProperties getDg4H0202ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.dg4.H0202Validator");
	}

	public static TemplateProperties getDg4TemplateProperties() {
		DefaultStringTemplateProperties requiredFields = new DefaultStringTemplateProperties(MANDATORY_FIELD_DG4);
		return requiredFields;
	}

	public static final String MANDATORY_FIELD_DG4 = "H0002,H0005,H0202,H0203,H0400,H0401,H0402,H0501,H0502,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D";

	public static String[] getDg4MandatoryColumns() {
		String[] mandatoryColumns = { "Hole_id", "Sample ID", "Depth From", "Depth To" };
		return mandatoryColumns;
	}

	public static List<String> getDg4ColumnHeaderList() {
		return Arrays.asList(getDg4MandatoryColumns());
	}

	public static void givenSg4TemplateProperties(TemplateProcessorContext mockContext)
			throws TemplateProcessorException {
		when(mockContext.getTemplateContextProperty(eq("SG4:MANDATORY.VALIDATE.FIELDS")))
				.thenReturn(getSg4TemplateProperties());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0002")))
			.thenReturn(getH0002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0005")))
			.thenReturn(getH0005ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0202")))
			.thenReturn(getSg4H0202ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0203")))
			.thenReturn(getH0203ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0501")))
			.thenReturn(getH0501ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0502")))
			.thenReturn(getH0502ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0503")))
			.thenReturn(getH0503ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0530")))
			.thenReturn(getH0530ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0531")))
			.thenReturn(getH0531ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0600")))
			.thenReturn(getH0600ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0601")))
			.thenReturn(getH0601ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0602")))
			.thenReturn(getH0602ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0700")))
			.thenReturn(getH0700ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0701")))
			.thenReturn(getH0701ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0702")))
			.thenReturn(getH0702ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0800")))
			.thenReturn(getH0800ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0801")))
			.thenReturn(getH0801ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H0802")))
			.thenReturn(getH0802ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H1000")))
			.thenReturn(getSg4H1000ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H1001")))
			.thenReturn(getSg4H1001ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H1002")))
			.thenReturn(getSg4H1002ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H1003")))
			.thenReturn(getSg4H1003ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:H1006")))
			.thenReturn(getSg4H1006ValidatorClassName());
		when(mockContext.getTemplateContextProperty(eq("SG4:D")))
			.thenReturn(getSg4DataValidatorClassName());
	}

	public static TemplateProperties getSg4H1006ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1006Validator");
	}

	public static TemplateProperties getSg4H1003ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1003Validator");
	}

	public static TemplateProperties getSg4H1002ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1002Validator");
	}

	public static TemplateProperties getSg4H1001ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1001Validator");
	}

	public static TemplateProperties getSg4DataValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.DValidator");
	}

	public static TemplateProperties getSg4H1000ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H1000Validator");
	}

	public static TemplateProperties getSg4TemplateProperties() {
		DefaultStringTemplateProperties requiredFields = new DefaultStringTemplateProperties(MANDATORY_FIELD_SG4);
		return requiredFields;
	}

	public static TemplateProperties getSg4H0202ValidatorClassName() {
		return new DefaultStringTemplateProperties(
				"au.gov.vic.ecodev.mrt.template.processor.file.validator.sg4.H0202Validator");
	}

	public static final String MANDATORY_FIELD_SG4 = "H0002,H0005,H0202,H0203,H0501,H0502,H0503,H0530,H0531,H0600,H0601,H0602,H0700,H0701,H0702,H0800,H0801,H0802,H1000,H1001,H1002,H1003,H1006,D";

	public static List<String> getSg4MandatoryFieldsList() {
		return Arrays.asList(MANDATORY_FIELD_SG4.split(Strings.COMMA));
	}

	public static String[] getSg4MandatoryColumns() {
		String[] mandatoryColumns = { "Sample ID", "Easting_MGA", "Northing_MGA",  "Sample_type"};
		return mandatoryColumns;
	}

	public static void givenCurrentLineNumber(Map<String, List<String>> params) {
		String[] lineNumbers = { "6" };
		params.put(Strings.CURRENT_LINE, Arrays.asList(lineNumbers));
	}

	public static String[] getSg4MandatoryAmgColumns() {
		String[] mandatoryColumns = { "Sample ID", "Easting_AMG", "Northing_AMG",  "Sample_type"};
		return mandatoryColumns;
	}
	
	public static List<String> getSg4MandatoryColumnsList() {
		return Arrays.asList(getSg4MandatoryColumns());
	}
	
	public static List<String> getSg4MandatoryAmgColumnsList() {
		return Arrays.asList(TestFixture.getSg4MandatoryAmgColumns());
	}

	public static List<String> getDListWithOptionalFields() {
		List<String> fields = getDList();
		fields.add(3, "0.01");
		fields.add("DDU");
		return fields;
	}

	public static List<String> getColumnHeaderListWithOptionalFields() {
		List<String> headers = new ArrayList<>(TestFixture.getColumnHeaderList());
		headers.add("Au");
		headers.add(3, "Zn");
		return headers;
	}

	public static List<String> getDg4DataList() {
		String[] datas = {"KPDD001", "KP32001", "0", "1", "DDC"};
		return Arrays.asList(datas);
	}

	public static List<String> getSg4DataList() {
		String[] datas = {"KP32001", "392200", "6589600", "SOI"};
		return Arrays.asList(datas);
	}

	public static List<String> getDg4H1001MadatoryFieldHeadersList() {
		String[] datas = DG4_H1001_MANDATORY_FIELDS.split(Strings.COMMA);
		return Arrays.asList(datas);
	}

	public static List<String> getDg4FullColumnHeaderList() {
		List<String> headers = new ArrayList<>(getDg4ColumnHeaderList());
		headers.add("Sample_type");
		headers.add("Au");
		headers.add("Au");
		headers.add("As");
		headers.add("Cu");
		headers.add("Pb");
		headers.add("Zn");
		return headers;
	}
	
	public static String[] getDg4H1001Data() {
		String[] strs = { "H1001", null, null, null, null, null,  "ppm", "ppm", "ppm", "ppm", "ppm" };
		return strs;
	}

	public static String[] getDg4H1003Data() {
		String[] strs = { "H1003", null, null, null, null, null, "1", "0.01", "5", "0.1", "0.1" };
		return strs;
	}

	public static List<String> getSl4H1001List() {
		String[] strs = {null, "metres", "metres", "metres", "metres", "N/A", "degrees", "degrees"};
		return Arrays.asList(strs);
	}

	public static List<String> getSl4H1004List() {
		String[] strs = {null, "1", "1", "1", "1", "0", "1", "1"};
		return Arrays.asList(strs);
	}

	public static List<String> getDs4H1001List() {
		String[] strs = {null, "metres", "degrees", "degrees"};
		return Arrays.asList(strs);
	}

	public static List<String> getDs4H1004List() {
		String[] strs = {null, "1", "0", "0"};
		return Arrays.asList(strs);
	}

	public static List<String> getDl4H1001List() {
		String[] strs = {null, "metres", "metres"};
		return Arrays.asList(strs);
	}

	public static List<String> getDl4H1004List() {
		String[] strs = {null, "1", "1"};
		return Arrays.asList(strs);
	}

}
