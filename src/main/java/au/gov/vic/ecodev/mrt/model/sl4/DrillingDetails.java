package au.gov.vic.ecodev.mrt.model.sl4;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class DrillingDetails implements au.gov.vic.ecodev.mrt.template.processor.model.Entity, Serializable {

	/**
	 * Generate serial version UID.
	 */
	private static final long serialVersionUID = 6779368660745013588L;

	private long id;
	private String fileName;
	private String drillType;
	private String drillCompany;
	private String drillDescription;
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getDrillType() {
		return drillType;
	}
	public void setDrillType(String drillType) {
		this.drillType = drillType;
	}
	public String getDrillCompany() {
		return drillCompany;
	}
	public void setDrillCompany(String drillCompany) {
		this.drillCompany = drillCompany;
	}
	public String getDrillDescription() {
		return drillDescription;
	}
	public void setDrillDescription(String drillDescription) {
		this.drillDescription = drillDescription;
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
