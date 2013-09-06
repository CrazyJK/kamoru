package com.hs.alice.sr.domain;

// Generated 2013. 4. 29 오후 2:46:51 by Hibernate Tools 3.4.0.CR1

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * SrInstall generated by hbm2java
 */
@Entity
@Table(name = "SR_INSTALL", schema = "ALICE")
public class SrInstall implements java.io.Serializable {

	private int id;
	private Integer version;
	private SrInstallCodeProduct srInstallCodeProduct;
	private SrUser srUserByInstaller;
	private SrInstallCodeVersion srInstallCodeVersion;
	private SrUser srUserByManager;
	private Date installed;
	private Set<SrInstallInfo> srInstallInfos = new HashSet<SrInstallInfo>(0);
	private Set<SrRequest> srRequests = new HashSet<SrRequest>(0);
	private Set<SrCompany> srCompanies = new HashSet<SrCompany>(0);

	public SrInstall() {
	}

	public SrInstall(int id) {
		this.id = id;
	}

	public SrInstall(int id, SrInstallCodeProduct srInstallCodeProduct,
			SrUser srUserByInstaller,
			SrInstallCodeVersion srInstallCodeVersion, SrUser srUserByManager,
			Date installed, Set<SrInstallInfo> srInstallInfos,
			Set<SrRequest> srRequests, Set<SrCompany> srCompanies) {
		this.id = id;
		this.srInstallCodeProduct = srInstallCodeProduct;
		this.srUserByInstaller = srUserByInstaller;
		this.srInstallCodeVersion = srInstallCodeVersion;
		this.srUserByManager = srUserByManager;
		this.installed = installed;
		this.srInstallInfos = srInstallInfos;
		this.srRequests = srRequests;
		this.srCompanies = srCompanies;
	}

	@Id
	@TableGenerator(name="TABLE_GEN", table="ALICE_ID", pkColumnName="ID_NAME", 
		valueColumnName="ID_COUNT", pkColumnValue="SR_INSTALL", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN")
	@Column(name = "ID", unique = true, nullable = false, precision = 8, scale = 0)
	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Version
	@Column(name = "VERSION", precision = 8, scale = 0)
	public Integer getVersion() {
		return this.version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "PRODUCT")
	public SrInstallCodeProduct getSrInstallCodeProduct() {
		return this.srInstallCodeProduct;
	}

	public void setSrInstallCodeProduct(
			SrInstallCodeProduct srInstallCodeProduct) {
		this.srInstallCodeProduct = srInstallCodeProduct;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "INSTALLER")
	public SrUser getSrUserByInstaller() {
		return this.srUserByInstaller;
	}

	public void setSrUserByInstaller(SrUser srUserByInstaller) {
		this.srUserByInstaller = srUserByInstaller;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "VERSION", insertable = false, updatable = false)
	public SrInstallCodeVersion getSrInstallCodeVersion() {
		return this.srInstallCodeVersion;
	}

	public void setSrInstallCodeVersion(
			SrInstallCodeVersion srInstallCodeVersion) {
		this.srInstallCodeVersion = srInstallCodeVersion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MANAGER")
	public SrUser getSrUserByManager() {
		return this.srUserByManager;
	}

	public void setSrUserByManager(SrUser srUserByManager) {
		this.srUserByManager = srUserByManager;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "INSTALLED", length = 7)
	public Date getInstalled() {
		return this.installed;
	}

	public void setInstalled(Date installed) {
		this.installed = installed;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srInstall")
	public Set<SrInstallInfo> getSrInstallInfos() {
		return this.srInstallInfos;
	}

	public void setSrInstallInfos(Set<SrInstallInfo> srInstallInfos) {
		this.srInstallInfos = srInstallInfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srInstall")
	public Set<SrRequest> getSrRequests() {
		return this.srRequests;
	}

	public void setSrRequests(Set<SrRequest> srRequests) {
		this.srRequests = srRequests;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "srInstall")
	public Set<SrCompany> getSrCompanies() {
		return this.srCompanies;
	}

	public void setSrCompanies(Set<SrCompany> srCompanies) {
		this.srCompanies = srCompanies;
	}

}