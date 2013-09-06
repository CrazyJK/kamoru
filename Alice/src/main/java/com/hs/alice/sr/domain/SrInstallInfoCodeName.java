package com.hs.alice.sr.domain;

// Generated 2013. 4. 29 오후 2:46:51 by Hibernate Tools 3.4.0.CR1

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

/**
 * SrInstallInfoCodeName generated by hbm2java
 */
@Entity
@Table(name = "SR_INSTALL_INFO_CODE_NAME", schema = "ALICE")
public class SrInstallInfoCodeName implements java.io.Serializable {

	private int id;
	private String value;
	private Set<SrInstallInfo> srInstallInfos = new HashSet<SrInstallInfo>(0);
	private Set<SrInstallInfoCodeValue> srInstallInfoCodeValues = new HashSet<SrInstallInfoCodeValue>(
			0);

	public SrInstallInfoCodeName() {
	}

	public SrInstallInfoCodeName(int id) {
		this.id = id;
	}

	public SrInstallInfoCodeName(int id, String value,
			Set<SrInstallInfo> srInstallInfos,
			Set<SrInstallInfoCodeValue> srInstallInfoCodeValues) {
		this.id = id;
		this.value = value;
		this.srInstallInfos = srInstallInfos;
		this.srInstallInfoCodeValues = srInstallInfoCodeValues;
	}

	@Id
	@TableGenerator(name="TABLE_GEN", table="ALICE_ID", pkColumnName="ID_NAME", 
		valueColumnName="ID_COUNT", pkColumnValue="SR_INSTALL_INFO_CODE_NAME", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "VALUE", length = 200)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srInstallInfoCodeName")
	public Set<SrInstallInfo> getSrInstallInfos() {
		return this.srInstallInfos;
	}

	public void setSrInstallInfos(Set<SrInstallInfo> srInstallInfos) {
		this.srInstallInfos = srInstallInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srInstallInfoCodeName")
	public Set<SrInstallInfoCodeValue> getSrInstallInfoCodeValues() {
		return this.srInstallInfoCodeValues;
	}

	public void setSrInstallInfoCodeValues(
			Set<SrInstallInfoCodeValue> srInstallInfoCodeValues) {
		this.srInstallInfoCodeValues = srInstallInfoCodeValues;
	}

}