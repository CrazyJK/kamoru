package com.hs.alice.sr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TB_CMPNYINFO", schema = "SA")
public class TbCmpnyinfo {

	private Integer cmpnyid;
	private String cmpnyname;
	
	@Id
	@Column(name = "CMPNY_ID")
	public Integer getCmpnyid() {
		return cmpnyid;
	}
	public void setCmpnyid(Integer cmpnyid) {
		this.cmpnyid = cmpnyid;
	}
	
	@Column(name = "CMPNY_NAME")
	public String getCmpnyname() {
		return cmpnyname;
	}
	public void setCmpnyname(String cmpnyname) {
		this.cmpnyname = cmpnyname;
	}
	
	
}
