package au.gov.vic.ecodev.mrt.model.ds4;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

public class DownHole implements Entity, Serializable {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 3437640614048988049L;
	
	private long id;
	private long loaderId;
	private String holeId;
	private String fileName;
	private BigDecimal surveyedDepth;
	private BigDecimal azimuthMag;
	private BigDecimal dip;
	private BigDecimal azimuthTrue;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getLoaderId() {
		return loaderId;
	}
	public void setLoaderId(long loaderId) {
		this.loaderId = loaderId;
	}
	public String getHoleId() {
		return holeId;
	}
	public void setHoleId(String holeId) {
		this.holeId = holeId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BigDecimal getSurveyedDepth() {
		return surveyedDepth;
	}
	public void setSurveyedDepth(BigDecimal surveyedDepth) {
		this.surveyedDepth = surveyedDepth;
	}
	public BigDecimal getAzimuthMag() {
		return azimuthMag;
	}
	public void setAzimuthMag(BigDecimal azimuthMag) {
		this.azimuthMag = azimuthMag;
	}
	public BigDecimal getDip() {
		return dip;
	}
	public void setDip(BigDecimal dip) {
		this.dip = dip;
	}
	public BigDecimal getAzimuthTrue() {
		return azimuthTrue;
	}
	public void setAzimuthTrue(BigDecimal azimuthTrue) {
		this.azimuthTrue = azimuthTrue;
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
