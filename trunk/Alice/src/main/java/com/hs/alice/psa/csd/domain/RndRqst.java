package com.hs.alice.psa.csd.domain;

import java.io.Serializable;

public class RndRqst implements Serializable {

	private int rqstid;
	private String answer;
	private String final_answer;
	private String procid;
	
	public int getRqstid() {
		return rqstid;
	}
	public void setRqstid(int rqstid) {
		this.rqstid = rqstid;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getFinal_answer() {
		return final_answer;
	}
	public void setFinal_answer(String final_answer) {
		this.final_answer = final_answer;
	}
	public String getProcid() {
		return procid;
	}
	public void setProcid(String procid) {
		this.procid = procid;
	}
	@Override
	public String toString() {
		return String.format(
				"RndRqst [rqstid=%s, answer=%s, final_answer=%s, procid=%s]",
				rqstid, answer, final_answer, procid);
	}
	
	
}
