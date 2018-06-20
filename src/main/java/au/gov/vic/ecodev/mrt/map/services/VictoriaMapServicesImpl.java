package au.gov.vic.ecodev.mrt.map.services;

import java.io.File;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.locationtech.jts.operation.union.CascadedPolygonUnion;
import org.locationtech.jtstest.testbuilder.io.shapefile.Shapefile;
import org.springframework.core.io.ClassPathResource;

import au.gov.vic.ecodev.mrt.config.MrtConfigProperties;
import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.constants.Constants.Strings;
import au.gov.vic.ecodev.mrt.map.services.helper.ExceptionEmailer;

public class VictoriaMapServicesImpl implements VictoriaMapServices {
	
	private static final Logger LOGGER = Logger.getLogger(VictoriaMapServicesImpl.class);
	
	private static final String SHAPE_FILE_EXTENSION = ".shp";
	private static final String FILE_NAME_SEPERATOR = ".";
	private static final String AGD_55_NORTHING_EASTING = "AGD_55_NORTHING_EASTING";
	private static final String AGD_54_NORTHING_EASTING = "AGD_54_NORTHING_EASTING";
	private static final String MGA_55_LATITUDE_LONGITUDE = "MGA_55_LATITUDE_LONGITUDE";
	private static final String MGA_55_NORTHING_EASTING = "MGA_55_NORTHING_EASTING";
	private static final String MGA_54_NORTHING_EASTING = "MGA_54_NORTHING_EASTING";
	private static final String MGA_54_LATITUDE_LONGITUDE = "MGA_54_LATITUDE_LONGITUDE";
	private final Map<String, Geometry> maps;
	private final MrtConfigProperties mrtConfigProperties;
	private final int SRID_GDA94;
	private final int SRID_AGD84;
	
	public VictoriaMapServicesImpl(MrtConfigProperties mrtConfigProperties) {
		if (null == mrtConfigProperties) {
			throw new IllegalArgumentException("VictoriaMapServicesImpl:mrtConfigProperties cannot be null!");
		}
		this.mrtConfigProperties = mrtConfigProperties;
		maps = new HashMap<>();
		SRID_GDA94 = mrtConfigProperties.getSridGda94();
		SRID_AGD84 = mrtConfigProperties.getSridAgd84();
		
		init();
	}
	
	@Override
	public boolean isWithinMga54NorthEast(BigDecimal easting, BigDecimal northing) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_GDA94);
		final Coordinate coord = new Coordinate(easting.doubleValue(), northing.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(MGA_54_NORTHING_EASTING));
	}
	
	@Override
	public boolean isWithinMga54LatitudeLongitude(BigDecimal latitude, BigDecimal longitude) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_GDA94);
		final Coordinate coord = new Coordinate(latitude.doubleValue(), 
				longitude.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(MGA_54_LATITUDE_LONGITUDE));
	}
	
	@Override
	public boolean isWithinMga55NorthEast(BigDecimal easting, BigDecimal northing) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_GDA94);
		final Coordinate coord = new Coordinate(easting.doubleValue(), northing.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(MGA_55_NORTHING_EASTING));
	}
	
	@Override
	public boolean isWithinMga55LatitudeLongitude(BigDecimal latitude, BigDecimal longitude) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_GDA94);
		final Coordinate coord = new Coordinate(latitude.doubleValue(), 
				longitude.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(MGA_55_LATITUDE_LONGITUDE));
	}
	
	@Override
	public boolean isWithinAgd54NorthEast(BigDecimal easting, BigDecimal northing) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_AGD84);
		final Coordinate coord = new Coordinate(easting.doubleValue(), northing.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(AGD_54_NORTHING_EASTING));
	}

	@Override
	public boolean isWithinAgd55NorthEast(BigDecimal easting, BigDecimal northing) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), SRID_AGD84);
		final Coordinate coord = new Coordinate(easting.doubleValue(), northing.doubleValue());
		final Point point = gf.createPoint(coord);
		return point.within(maps.get(AGD_55_NORTHING_EASTING));
	}
	
	protected void init() {
		//final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), 4283);
		final String MGA_54_NE_FILE_NAME = mrtConfigProperties.getMga54NeFileName();
		Geometry mga54Ne = loadMap(SRID_GDA94, MGA_54_NE_FILE_NAME);
		maps.put(MGA_54_NORTHING_EASTING, mga54Ne);
		
		final String MGA_55_NE_FILE_NAME = mrtConfigProperties.getMga55NeFileName();
		Geometry mga55Ne = loadMap(SRID_GDA94, MGA_55_NE_FILE_NAME);
		maps.put(MGA_55_NORTHING_EASTING, mga55Ne);
		
		final String MGA_54_LAT_FILE_NAME = mrtConfigProperties.getMga54LatFileName();
		Geometry mga54Lat = loadMap(SRID_GDA94, MGA_54_LAT_FILE_NAME);
		maps.put(MGA_54_LATITUDE_LONGITUDE, mga54Lat);
		
		final String MGA_55_LAT_FILE_NAME = mrtConfigProperties.getMga55LatFileName();
		Geometry mga55Lat = loadMap(SRID_GDA94, MGA_55_LAT_FILE_NAME);
		maps.put(MGA_55_LATITUDE_LONGITUDE, mga55Lat);
		
		final String AGD_54_NE_FILE_NAME = mrtConfigProperties.getAgd8454NeFileName();
		Geometry agd8454ne = loadMap(SRID_AGD84, AGD_54_NE_FILE_NAME);
		maps.put(AGD_54_NORTHING_EASTING, agd8454ne);
		
		final String AGD_55_NE_FILE_NAME = mrtConfigProperties.getAgd8455NeFileName();
		Geometry agd8455ne = loadMap(SRID_AGD84, AGD_55_NE_FILE_NAME);
		maps.put(AGD_55_NORTHING_EASTING, agd8455ne);
	}
	
	protected final Geometry loadMap(final int srid, final String fileName) {
		final GeometryFactory gf = new GeometryFactory(new PrecisionModel(), srid);
		
		Geometry victoriaCollection = null;
		Geometry victoria = null;
		
		try {
			victoriaCollection = readFile(fileName, gf);
		} catch (Exception e) {
			new ExceptionEmailer(mrtConfigProperties).sendEmail(e);
			LOGGER.error(e.getMessage(), e);
			shutDown();
		}
		
		if (null != victoriaCollection) {
			int len = victoriaCollection.getNumGeometries();
			List<Geometry> victoriaList = new ArrayList<>(len);
			for (int i = 0; i < len; i++) {
				victoriaList.add(victoriaCollection.getGeometryN(i));
			}
			victoria = CascadedPolygonUnion.union(victoriaList);	
		}
		return victoria;
	}

	private Geometry readFile(String fileName, GeometryFactory gf) throws Exception {
		String fileExtension = getFileExtension(fileName);
		if (SHAPE_FILE_EXTENSION.equalsIgnoreCase(fileExtension)) {
			return readShapeFile(fileName, gf);
		}
		throw new RuntimeException("Unsupported file type: " + fileExtension);
	}

	private Geometry readShapeFile(String fileName, GeometryFactory gf) throws Exception {
		ClassPathResource classPathResource = new ClassPathResource(fileName);
		InputStream inputStream = classPathResource.getInputStream();
		Shapefile shpfile = new Shapefile(inputStream);
	    shpfile.readStream(gf);
	    List<Geometry> geomList = new ArrayList<>();
	    do {
	      Geometry geom = shpfile.next();
	      if (geom == null)
	        break;
	      geomList.add(geom);
	    } while (true);
	    
	    return gf.createGeometryCollection(GeometryFactory.toGeometryArray(geomList));
	}

	private String getFileExtension(String fileName) {
		File file = new File(fileName);
		String filesName = file.getName();
		int extIndex = filesName.lastIndexOf(FILE_NAME_SEPERATOR);
		if (extIndex < Numeral.ZERO)
			return Strings.EMPTY;
		return filesName.substring(extIndex, filesName.length());
	}

	private void shutDown() {
		System.exit(Numeral.NEGATIVE_ONE);
	}

	@Override
	public boolean isWithinTenementMga54LatitudeLongitude(String tenmentNo, BigDecimal latitude, BigDecimal longitude) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isWithinTenementMga55LatitudeLongitude(String tenmentNo, BigDecimal latitude, BigDecimal longitude) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWithinTenementMga54NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public boolean isWithinTenementMga55NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWithinTenementAgd54NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean isWithinTenementAgd55NorthEast(String tenmentNo, BigDecimal easting, BigDecimal northing) {
		throw new UnsupportedOperationException();
	}
}
