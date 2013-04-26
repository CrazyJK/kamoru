package com.hs.alice.sr.service;

import java.util.List;

import com.hs.alice.sr.domain.RndRqst;

/**
 * 미구현 상태
 * @author kamoru
 *
 */
public interface SrService {

/*	void registNewSR(ServiceRequest sr);
	void completeSR(ServiceRequest sr);
	void updateSR(ServiceRequest sr);
	void deleteSR(ServiceRequest sr);
	
	ServiceRequest getSR(int idx);
	List<ServiceRequest> getSRList();
	List<ServiceRequest> getSRList(int page, int size);
*///	List<ServiceRequest> getSRList(User user, int page, int size);
	
	RndRqst getRqstById(int id);

	List<RndRqst> getRqstList();
}
