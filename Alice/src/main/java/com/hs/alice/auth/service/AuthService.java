package com.hs.alice.auth.service;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.hs.alice.auth.dao.AuthGroupDao;
import com.hs.alice.auth.dao.AuthRoleDao;
import com.hs.alice.auth.dao.AuthUserDao;
import com.hs.alice.auth.domain.AuthGroup;
import com.hs.alice.auth.domain.AuthRole;
import com.hs.alice.auth.domain.AuthUser;

@Service
@Transactional(readOnly=true)
public class AuthService {
	
	private static final Log logger = LogFactory.getLog(AuthService.class);
	
	@Autowired private AuthUserDao authUserDao;
	@Autowired private AuthGroupDao authGroupDao;
	@Autowired private AuthRoleDao authRoleDao;
	
	// about AuthUser =============================================================================
	
	public AuthUser getUserById(int userid) {
		AuthUser authUser = authUserDao.findById(userid);
//		logger.debug(authUser);
		return authUser;
	}

	public AuthUser getUserByUsername(String username) {
		return authUserDao.findByName(username);
	}

	public AuthUser fetchUserById(int userid) {
		AuthUser authUser = authUserDao.fetchById(userid);
		authUser.getRoles();
		return authUser;
	}

	public Object getUserForForm(Integer userid) {
		AuthUser authUser = authUserDao.findById(userid);
		authUser.getAuthGroup().getGroupid();
		return authUser;
	}

	public List<AuthUser> getUserList() {
		List<AuthUser> authUserList = authUserDao.findAll();
//		logger.debug(authUserList);
		return authUserList;
	}
	
	public void unLockUser(Integer userid) {
		AuthUser authUser = authUserDao.findById(userid);
		authUser.initializeFlag();
		authUserDao.merge(authUser);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer saveUser(AuthUser authUser) {
		authUser.initializeFlag();
//		logger.debug(authUser);
		authUserDao.persist(authUser);
		return authUser.getUserid();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateUser(AuthUser authUser) {
//		logger.debug(authUser);
		authUserDao.merge(authUser);		
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteAllRoles(int userid) {
		AuthUser user = authUserDao.findById(userid);
		user.getAuthRoleUserMaps().clear();
		authUserDao.merge(user);
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteUser(int userid) {
		AuthUser user = authUserDao.findById(userid);
		authUserDao.remove(user);
	}
	
	// about AuthGroup ============================================================================
	
	public AuthGroup getGroupById(int groupid) {
		AuthGroup authGroup = authGroupDao.findById(groupid);
		logger.debug(authGroup);
		return authGroup;
	}
	
	public AuthGroup getGroupDetailById(int groupid) {
		AuthGroup authGroup = authGroupDao.fetchById(groupid);
		authGroup.getRoles();
		logger.debug(authGroup);
		return authGroup;
	}
	
	public List<AuthGroup> getGroupList() {
		return authGroupDao.findAll();
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void deleteGroup(int groupid) {
		authGroupDao.remove(authGroupDao.findById(groupid));
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public void updateGroup(AuthGroup authGroup) {
		authGroupDao.merge(authGroup);
	}

	@Transactional(propagation=Propagation.REQUIRED)
	public int saveGroup(AuthGroup authGroup) {
		authGroupDao.persist(authGroup);
		return authGroup.getGroupid();
	}

	// about AuthRole =============================================================================

	public List<AuthRole> getRoleList() {
		List<AuthRole> authRoleList = authRoleDao.findAll();
		logger.debug(authRoleList);
		return authRoleList;
	}
	
	public AuthRole getRoleById(int roleid) {
		return authRoleDao.findById(roleid);
	}

	public Object getRoleDetailById(int roleid) {
		return authRoleDao.fetchById(roleid);
	}

	
}
