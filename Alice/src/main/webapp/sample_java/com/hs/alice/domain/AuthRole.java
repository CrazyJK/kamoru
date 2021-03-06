package com.hs.alice.domain;

// Generated 2013. 4. 10 오전 10:19:22 by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.hs.alice.core.AliceCore;

/**
 * AuthRole generated by hbm2java
 */
public class AuthRole implements java.io.Serializable {

	private static final long serialVersionUID = AliceCore.SERIAL_VERSION_UID;

    private static final Log logger = LogFactory.getLog(AuthRole.class);

	private BigDecimal roleid;
	private String rolename;
	private char enabled;
	private Set<AuthRoleUserMap> authRoleUserMaps = new HashSet<AuthRoleUserMap>(0);
	private Set<AuthRoleGroupMap> authRoleGroupMaps = new HashSet<AuthRoleGroupMap>(0);

	public AuthRole() {
	}

	public AuthRole(BigDecimal roleid, String rolename, char enabled) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.enabled = enabled;
	}

	public AuthRole(BigDecimal roleid, String rolename, char enabled,
			Set<AuthRoleUserMap> authRoleUserMaps,
			Set<AuthRoleGroupMap> authRoleGroupMaps) {
		this.roleid = roleid;
		this.rolename = rolename;
		this.enabled = enabled;
		this.authRoleUserMaps = authRoleUserMaps;
		this.authRoleGroupMaps = authRoleGroupMaps;
	}

	public BigDecimal getRoleid() {
		return this.roleid;
	}

	public void setRoleid(BigDecimal roleid) {
		this.roleid = roleid;
	}

	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	public char getEnabled() {
		return this.enabled;
	}

	public void setEnabled(char enabled) {
		this.enabled = enabled;
	}

	public Set<AuthRoleUserMap> getAuthRoleUserMaps() {
		return this.authRoleUserMaps;
	}

	public void setAuthRoleUserMaps(Set<AuthRoleUserMap> authRoleUserMaps) {
		this.authRoleUserMaps = authRoleUserMaps;
	}

	public Set<AuthRoleGroupMap> getAuthRoleGroupMaps() {
		return this.authRoleGroupMaps;
	}

	public void setAuthRoleGroupMaps(Set<AuthRoleGroupMap> authRoleGroupMaps) {
		this.authRoleGroupMaps = authRoleGroupMaps;
	}

	@Override
	public String toString() {
		return "AuthRole [roleid=" + roleid + ", rolename=" + rolename
				+ ", enabled=" + enabled + "]";
	}

	
}
