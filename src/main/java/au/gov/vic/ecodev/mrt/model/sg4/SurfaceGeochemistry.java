package au.gov.vic.ecodev.mrt.model.sg4;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

public class SurfaceGeochemistry implements Entity, Serializable {

	/**
	 * Generated serial version UID
	 */
	private static final long serialVersionUID = 1211633015660789468L;

	private long id;
	private long loaderId;
	private String sampleId;
	private BigDecimal easting;
	private BigDecimal northing;
	private String sampleType;
	
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
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
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
	public String getSampleType() {
		return sampleType;
	}
	public void setSampleType(String sampleType) {
		this.sampleType = sampleType;
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
