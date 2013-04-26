package com.hs.alice.psa.csd;

import java.io.UnsupportedEncodingException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import com.hs.alice.util.crypt.AliceCrypt;

/**
 * 미구현 상태
 * @author kamoru
 *
 */
public class CsdUserService implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(CsdUserService.class);
	
	private SessionFactory sessionFactory;
	@SuppressWarnings("unused")
	private AliceCrypt crypt;
	
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	public void setCrypt(AliceCrypt crypt) {
		this.crypt = crypt;
	}

	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		CsdUser csdUser = null;
		try {
			username = new String(username.getBytes("8859_1"), "UTF-8");
		} catch (UnsupportedEncodingException e1) {
		}
		logger.info(username);
		try {
			csdUser = (CsdUser) sessionFactory.getCurrentSession().get(CsdUser.class, username);
			
//			user.setEnabled(true);
//			csdUser.setPassword(crypt.decrypt(csdUser.getPassword()));
//			csdUser.addAuthority("ROLE_USER");
			
			logger.info("obtained : " + csdUser);
		} catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
		return (UserDetails) csdUser;
	}

}
