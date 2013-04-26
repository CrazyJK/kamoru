package com.hs.alice.sr.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "RND_RQST", schema = "SA")
public class RndRqst {

	private Integer rqstid;
	private String answer;
	private String finalAnswer;
	private Integer procid;
	private TbRqst tbRqst;
	
	@Id
	@Column(name = "RQSTID")
	public Integer getRqstid() {
		return this.rqstid;
	}
	
	public void setRqstid(Integer rqstid) {
		this.rqstid = rqstid;
	}
	
	@Column(name="ANSWER")
	public String getAnswer() {
		return this.answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	@Column(name="FINAL_ANSWER")
	public String getFinalAnswer() {
		return this.finalAnswer;
	}
	public void setFinalAnswer(String finalAnswer) {
		this.finalAnswer = finalAnswer;
	}
	
	@Column(name="PROCID")
	public Integer getProcid() {
		return this.procid;
	}
	public void setProcid(Integer procid) {
		this.procid = procid;
	}

	@OneToOne
	@JoinColumn(name="RQSTID", unique=true)
	public TbRqst getTbRqst() {
		return tbRqst;
	}

	public void setTbRqst(TbRqst tbRqst) {
		this.tbRqst = tbRqst;
	}

	

	
	
}
