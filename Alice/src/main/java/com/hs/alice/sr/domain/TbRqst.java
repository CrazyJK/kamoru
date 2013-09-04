package com.hs.alice.sr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TB_RQST", schema = "SA")
public class TbRqst {

	/*csd.rqst_id, 
	 * csd.boundary, 
	 * csd.version, 
	 * csd.component, 
	 * csd.rqster_name, 
	 * csd.rqst_type_cd, 
	 * csd.rqst_grade_cd
		(SELECT c.cmpny_name FROM tb_cmpnyinfo c WHERE c.cmpny_id = csd.cmpny_id) cmpnyname
	 * */
	
	private Integer rqstid;
	private String subject;
	private String content;
	private String boundary;
	private String version;
	private String component;
	private String rqstername;
	private Integer rqsttypecd;
	private Integer rqstgradecd;
	private RndRqst rndRqst;
	private TbCmpnyinfo tbCmpnyinfo;
	
	@Id
	@Column(name = "RQST_ID")
	public Integer getRqstid() {
		return rqstid;
	}
	public void setRqstid(Integer rqstid) {
		this.rqstid = rqstid;
	}

	@Column(name="SUBJECT")
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}

	@Column(name="CONTENT")
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

	@Column(name="BOUNDARY")
	public String getBoundary() {
		return boundary;
	}
	public void setBoundary(String boundary) {
		this.boundary = boundary;
	}
	
	@Column(name="VERSION")
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	
	@Column(name="COMPONENT")
	public String getComponent() {
		return component;
	}
	public void setComponent(String component) {
		this.component = component;
	}
	
	@Column(name="RQSTER_NAME")
	public String getRqstername() {
		return rqstername;
	}
	public void setRqstername(String rqstername) {
		this.rqstername = rqstername;
	}
	
	@Column(name="RQST_TYPE_CD")
	public Integer getRqsttypecd() {
		return rqsttypecd;
	}
	public void setRqsttypecd(Integer rqsttypecd) {
		this.rqsttypecd = rqsttypecd;
	}
	
	@Column(name="RQST_GRADE_CD")
	public Integer getRqstgradecd() {
		return rqstgradecd;
	}
	public void setRqstgradecd(Integer rqstgradecd) {
		this.rqstgradecd = rqstgradecd;
	}

	@OneToOne(mappedBy="tbRqst")
	public RndRqst getRndRqst() {
		return rndRqst;
	}
	public void setRndRqst(RndRqst rndRqst) {
		this.rndRqst = rndRqst;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "CMPNY_ID")
	public TbCmpnyinfo getTbCmpnyinfo() {
		return tbCmpnyinfo;
	}
	public void setTbCmpnyinfo(TbCmpnyinfo tbCmpnyinfo) {
		this.tbCmpnyinfo = tbCmpnyinfo;
	}
	
	
}
