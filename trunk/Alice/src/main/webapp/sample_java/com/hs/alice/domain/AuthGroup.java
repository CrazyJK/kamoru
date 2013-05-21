package com.hs.alice.domain;

// Generated 2013. 4. 10 오전 10:19:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hs.alice.core.AliceCore;

/**
 * AuthGroup generated by hbm2java
 */
public class AuthGroup implements java.io.Serializable {

	private static final long serialVersionUID = AliceCore.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(AuthGroup.class);

	private BigDecimal groupid;
	private String groupname;
	private BigDecimal parentid;
	private Character enabled;
	private Set<AuthRoleGroupMap> authRoleGroupMaps = new HashSet<AuthRoleGroupMap>(0);
	private Set<AuthUser> authUsers = new HashSet<AuthUser>(0);

	private String[] roles;

	public AuthGroup() {
	}

	public AuthGroup(BigDecimal groupid, String groupname) {
		this.groupid = groupid;
		this.groupname = groupname;
	}

	public AuthGroup(BigDecimal groupid, String groupname, BigDecimal parentid,
			Character enabled, Set<AuthRoleGroupMap> authRoleGroupMaps,
			Set<AuthUser> authUsers) {
		this.groupid = groupid;
		this.groupname = groupname;
		this.parentid = parentid;
		this.enabled = enabled;
		this.authRoleGroupMaps = authRoleGroupMaps;
		this.authUsers = authUsers;
	}

	public void initialize() {
		for(AuthRoleGroupMap authRoleGroupMap : authRoleGroupMaps)
			authRoleGroupMap.getAuthRole();
		for(AuthUser authUser : authUsers)
			authUser.fillAuthorities();
	}
	
	public BigDecimal getGroupid() {
		return this.groupid;
	}

	public void setGroupid(BigDecimal groupid) {
		this.groupid = groupid;
	}

	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public BigDecimal getParentid() {
		return this.parentid;
	}

	public void setParentid(BigDecimal parentid) {
		this.parentid = parentid;
	}

	public Character getEnabled() {
		return this.enabled;
	}

	public void setEnabled(Character enabled) {
		this.enabled = enabled;
	}

	public Set<AuthRoleGroupMap> getAuthRoleGroupMaps() {
		return this.authRoleGroupMaps;
	}

	public void setAuthRoleGroupMaps(Set<AuthRoleGroupMap> authRoleGroupMaps) {
		this.authRoleGroupMaps = authRoleGroupMaps;
	}

	public Set<AuthUser> getAuthUsers() {
		return this.authUsers;
	}

	public void setAuthUsers(Set<AuthUser> authUsers) {
		this.authUsers = authUsers;
	}
	
	public String[] getRoles() {
		if(roles == null) {
			roles = new String[this.authRoleGroupMaps.size()];
			int i = 0;
			for(AuthRoleGroupMap authRoleGroupMap : authRoleGroupMaps) {
				roles[i++] = authRoleGroupMap.getAuthRole().getRoleid().toString();
			}
		}
		return roles;
	}

	public void setRoles(String[] roles) {
		Set<AuthRoleGroupMap> rebuildAuthRoleGroupMaps = new HashSet<AuthRoleGroupMap>();

		for(String roleid : roles) {
			boolean notfound = true;
			// 기존에 가지고 있는지 찾아, 다시 넣어준다.
			for(AuthRoleGroupMap authRoleGroupMap : this.getAuthRoleGroupMaps()) {
				BigDecimal rid = authRoleGroupMap.getAuthRole().getRoleid();
				logger.info("id: " + rid + " ? " + roleid);
				if(rid.toString().equals(roleid)) {
					rebuildAuthRoleGroupMaps.add(authRoleGroupMap);
					notfound = false;
					logger.info("\tFound : " + authRoleGroupMap);
					break;
				}
			}
			// 기존에 없는 것이면, 새로 만들어 넣어준다.
			if(notfound) {
				AuthRole authRole = new AuthRole();
				authRole.setRoleid(new BigDecimal(roleid));
				AuthRoleGroupMap authRoleGroupMap = new AuthRoleGroupMap();
				authRoleGroupMap.setAuthGroup(this);
				authRoleGroupMap.setAuthRole(authRole);
				
				rebuildAuthRoleGroupMaps.add(authRoleGroupMap);
				logger.info("\tnotFound. new " + authRoleGroupMap);
			}
		}
		this.setAuthRoleGroupMaps(rebuildAuthRoleGroupMaps);
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "AuthGroup [groupid=" + groupid + ", groupname=" + groupname
				+ ", parentid=" + parentid + ", enabled=" + enabled
				+ ", authRoleGroupMaps=" + authRoleGroupMaps + ", authUsers="
				+ authUsers + ", roles=" + Arrays.toString(roles) + "]";
	}
	
}