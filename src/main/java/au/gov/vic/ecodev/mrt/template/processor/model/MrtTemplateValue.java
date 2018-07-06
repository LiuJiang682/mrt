package au.gov.vic.ecodev.mrt.template.processor.model;

import java.io.Serializable;
import java.util.List;

public class MrtTemplateValue implements TemplateValue, Serializable {

	/**
	 * Generate serial version UID.
	 */
	private static final long serialVersionUID = -7173368829467143303L;
	
	private final List<String> datas;
	private final int issueColumnIndex;

	public MrtTemplateValue(final List<String> datas, final int issueColumnIndex) {
		this.datas = datas;
		this.issueColumnIndex = issueColumnIndex;
	}
	
	public List<String> getDatas() {
		return datas;
	}

	public int getIssueColumnIndex() {
		return issueColumnIndex;
	}
}
