package au.gov.vic.ecodev.mrt.model.sl4;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.constants.Constants.Numeral;
import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

public class Site implements Entity, Serializable {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = -673653681493365648L;

	private long loaderId;
	private String siteId;
	private long gsvSiteId;
	private String parish;
	private String prospect;
	private BigDecimal amgZone;
	private BigDecimal easting;
	private BigDecimal northing;
	private BigDecimal latitude;
	private BigDecimal Longitude;
	private BigDecimal locnAcc;
	private String locnDatumCd;
	private BigDecimal elevationGl;
	private BigDecimal elevAcc;
	private String elevDatumCd;
	private int numberOfDataRecord = Numeral.NEGATIVE_ONE;
	
	public long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(long loaderId) {
		this.loaderId = loaderId;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public long getGsvSiteId() {
		return gsvSiteId;
	}
	public void setGsvSiteId(long gsvSiteId) {
		this.gsvSiteId = gsvSiteId;
	}
	public String getParish() {
		return parish;
	}
	public void setParish(String parish) {
		this.parish = parish;
	}
	public String getProspect() {
		return prospect;
	}
	public void setProspect(String prospect) {
		this.prospect = prospect;
	}
	public BigDecimal getAmgZone() {
		return amgZone;
	}
	public void setAmgZone(BigDecimal amgZone) {
		this.amgZone = amgZone;
	}
	public BigDecimal getEasting() {
		return easting;
	}
	public void setEasting(BigDecimal easting) {
		this.easting = easting;
	}
	public BigDecimal getNorthing() {
		return northing;
	}
	public void setNorthing(BigDecimal northing) {
		this.northing = northing;
	}
	public BigDecimal getLatitude() {
		return latitude;
	}
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	public BigDecimal getLongitude() {
		return Longitude;
	}
	public void setLongitude(BigDecimal longitude) {
		Longitude = longitude;
	}
	public BigDecimal getLocnAcc() {
		return locnAcc;
	}
	public void setLocnAcc(BigDecimal locnAcc) {
		this.locnAcc = locnAcc;
	}
	public String getLocnDatumCd() {
		return locnDatumCd;
	}
	public void setLocnDatumCd(String locnDatumCd) {
		this.locnDatumCd = locnDatumCd;
	}
	public BigDecimal getElevationGl() {
		return elevationGl;
	}
	public void setElevationGl(BigDecimal elevationGl) {
		this.elevationGl = elevationGl;
	}
	public BigDecimal getElevAcc() {
		return elevAcc;
	}
	public void setElevAcc(BigDecimal elevAcc) {
		this.elevAcc = elevAcc;
	}
	public String getElevDatumCd() {
		return elevDatumCd;
	}
	public void setElevDatumCd(String elevDatumCd) {
		this.elevDatumCd = elevDatumCd;
	}
	public int getNumberOfDataRecord() {
		return numberOfDataRecord;
	}
	public void setNumberOfDataRecord(int numberOfDataRecord) {
		this.numberOfDataRecord = numberOfDataRecord;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}
