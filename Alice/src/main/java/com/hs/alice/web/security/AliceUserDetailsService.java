package com.hs.alice.web.security;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hs.alice.auth.dao.AuthUserDao;
import com.hs.alice.auth.domain.AuthUser;
import com.hs.alice.util.web.WebUtils;

@Service
public class AliceUserDetailsService implements UserDetailsService {

	private static final Log logger = LogFactory.getLog(AliceUserDetailsService.class);

	@Autowired 
	private AuthUserDao authUserDao; 
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		username = WebUtils.getUTF8EncodingString(username);
		AliceUserDetails user = new AliceUserDetails();
		try {
			logger.info("attempt to login by username : " + username);
			logger.info("using dao : " + authUserDao.getClass().getName());

			AuthUser foundAuthUser = authUserDao.findByName(username);
			if(foundAuthUser != null) {
				user.setAuthUser(foundAuthUser);
				user.fillAuthorities();
			} 
			else {
				throw new UsernameNotFoundException("there is no matching username."); 
			}
			
			logger.info("obtained user : " + user);
			
		} catch(HibernateException e) {
			logger.error(e);
			throw e;
		} catch(Exception e) {
			logger.error(e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		return user;
	}

}
