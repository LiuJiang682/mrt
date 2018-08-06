package au.gov.vic.ecodev.mrt.model.dg4;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

public class GeoChemistry implements Entity, Serializable {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 5471305443748582728L;

	private long id;
	private long loaderId;
	private String holeId;
	private String sampleId;
	private BigDecimal from;
	private BigDecimal to;
	private String drillCode;
	private String fileName;
	
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
	public String getSampleId() {
		return sampleId;
	}
	public void setSampleId(String sampleId) {
		this.sampleId = sampleId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public BigDecimal getFrom() {
		return from;
	}
	public void setFrom(BigDecimal from) {
		this.from = from;
	}
	public BigDecimal getTo() {
		return to;
	}
	public void setTo(BigDecimal to) {
		this.to = to;
	}
	public String getDrillCode() {
		return drillCode;
	}
	public void setDrillCode(String drillCode) {
		this.drillCode = drillCode;
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
