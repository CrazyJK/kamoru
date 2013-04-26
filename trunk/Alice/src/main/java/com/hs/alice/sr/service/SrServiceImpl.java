package com.hs.alice.sr.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hs.alice.sr.dao.RqstDao;
import com.hs.alice.sr.domain.RndRqst;

@Service
@Transactional(readOnly=true)
public class SrServiceImpl implements SrService {

	private static final Log logger = LogFactory.getLog(SrServiceImpl.class);

	@Autowired RqstDao rqstDao;

	@Override
	public RndRqst getRqstById(int id) {
		logger.info(id);
		return this.rqstDao.findById(id);
	}

	@Override
	public List<RndRqst> getRqstList() {
		logger.info("");
		return this.rqstDao.findAll();
	}

}
