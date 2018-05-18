package au.gov.vic.ecodev.mrt.model.sl4;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import au.gov.vic.ecodev.mrt.template.processor.model.Entity;

public class BoreHole implements Entity, Serializable {

	/**
	 * Generated serial version UID.
	 */
	private static final long serialVersionUID = 1195070576121861878L;
	
	private static final String DEPTH_UOM_DEFAULT_MTR = "MTR";
	private static final String BH_CONFIDENTIAL_FLAG_DEFAULT_Y = "Y";

	private long loaderId;
	private String holeId;
	private String bhAuthorityCd;
	private String bhRegulationCd;
	private long dillingDetailsId;
	private Date drillingStartDate;
	private Date drillingCompletionDate;
	private BigDecimal mdDepth;
	private BigDecimal elevationKb;
	private BigDecimal azimuthMag;
	private String bhConfidentialFlag = BH_CONFIDENTIAL_FLAG_DEFAULT_Y;
	private String depthUom = DEPTH_UOM_DEFAULT_MTR;
	
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
	public String getBhAuthorityCd() {
		return bhAuthorityCd;
	}
	public void setBhAuthorityCd(String bhAuthorityCd) {
		this.bhAuthorityCd = bhAuthorityCd;
	}
	public String getBhRegulationCd() {
		return bhRegulationCd;
	}
	public void setBhRegulationCd(String bhRegulationCd) {
		this.bhRegulationCd = bhRegulationCd;
	}
	public long getDillingDetailsId() {
		return dillingDetailsId;
	}
	public void setDillingDetailsId(long dillingDetailsId) {
		this.dillingDetailsId = dillingDetailsId;
	}
	public Date getDrillingStartDate() {
		return drillingStartDate;
	}
	public void setDrillingStartDate(Date drillingStartDate) {
		this.drillingStartDate = drillingStartDate;
	}
	public Date getDrillingCompletionDate() {
		return drillingCompletionDate;
	}
	public void setDrillingCompletionDate(Date drillingCompletionDate) {
		this.drillingCompletionDate = drillingCompletionDate;
	}
	public BigDecimal getMdDepth() {
		return mdDepth;
	}
	public void setMdDepth(BigDecimal mdDepth) {
		this.mdDepth = mdDepth;
	}
	public BigDecimal getElevationKb() {
		return elevationKb;
	}
	public void setElevationKb(BigDecimal elevationKb) {
		this.elevationKb = elevationKb;
	}
	public BigDecimal getAzimuthMag() {
		return azimuthMag;
	}
	public void setAzimuthMag(BigDecimal azimuthMag) {
		this.azimuthMag = azimuthMag;
	}
	public String getBhConfidentialFlag() {
		return bhConfidentialFlag;
	}
	public void setBhConfidentialFlag(String bhConfidentialFlag) {
		this.bhConfidentialFlag = bhConfidentialFlag;
	}
	
	public String getDepthUom() {
		return depthUom;
	}
	public void setDepthUom(String depthUom) {
		this.depthUom = depthUom;
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
