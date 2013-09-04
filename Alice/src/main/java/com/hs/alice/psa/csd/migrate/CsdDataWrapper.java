package com.hs.alice.psa.csd.migrate;

import java.util.List;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.hs.alice.psa.csd.domain.RndRqst;
import com.hs.alice.sr.domain.SrRequest;
import com.ibatis.sqlmap.client.SqlMapClient;

@Repository
public class CsdDataWrapper {

	JdbcTemplate template;
	
	@Resource(name="dataSourceCsd")
	public void setDateSource(DataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	
	public void rqstDate() {
		int count = template.queryForInt("SELECT count(*) FROM rnd_rqst");
		System.out.println(count);
	}

	public int getRndRqstCount() {
		return template.queryForInt("SELECT count(*) FROM rnd_rqst");
	}
	
	public List<RndRqst> wrapSrRequest() {
		int startIndex = 0;
		List<RndRqst> list = template.query(
				"SELECT rqstid, answer, final_answer, procid FROM rnd_rqst WHERE rqstid > ?", 
				new BeanPropertyRowMapper<RndRqst>(RndRqst.class), 
				startIndex);
		
//		List<RndRqst>
		
		
		
		
		
		return list;
	}
}
