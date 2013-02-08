package com.springinaction.spitter.persistence;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcDaoSupport;
import org.springframework.jdbc.core.simple.SimpleJdbcTemplate;

import com.springinaction.spitter.domain.Spitter;
import com.springinaction.spitter.domain.Spittle;

@SuppressWarnings("deprecation")
public class JdbcSpitterDao extends SimpleJdbcDaoSupport implements SpitterDao {

	private static final String SQL_INSERT_SPITTER = "INSERT INTO spitter (username, password,fullname) VALUES (:username, :password, :fullname)";
	@SuppressWarnings("unused")
	private static final String SQL_UPDATE_SPITTER = "UPDATE spitter SET username = ?, password = ?, fullname = ?";
	private static final String SQL_SELECT_SPITTER = "SELECT id, username, fullname FROM spitter WHERE id = ?";

	public void addSpitter(Spitter spitter) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("username", spitter.getUsername());
		params.put("password", spitter.getPassword());
		params.put("fullname", spitter.getFullName());
		getSimpleJdbcTemplate().update(SQL_INSERT_SPITTER, params);
//		jdbcTemplate.update(SQL_INSERT_SPITTER, spitter.getUsername(),
//				spitter.getPassword(), spitter.getFullName(),
//				spitter.getEmail(), spitter.isUpdateByEmail());
		spitter.setId(queryForIdentity());
	}

	public Spitter getSpitterById(long id) {
		return getSimpleJdbcTemplate().queryForObject(SQL_SELECT_SPITTER,
				new ParameterizedRowMapper<Spitter>() {
					@Override
					public Spitter mapRow(ResultSet rs, int paramInt)
							throws SQLException {
						Spitter spitter = new Spitter();
						spitter.setId(rs.getLong(1));
						spitter.setUsername(rs.getString(2));
						spitter.setPassword(rs.getString(3));
						spitter.setFullName(rs.getString(4));
						return spitter;
					}
				}, id);
	}

	private int queryForIdentity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void saveSpitter(Spitter spitter) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Spittle> getRecentSpittles(int spittlesPerPage) {
		// TODO Auto-generated method stub
		List<Spittle> list = new ArrayList<Spittle>();
		list.add(new Spittle());
		return list;
	}
}
