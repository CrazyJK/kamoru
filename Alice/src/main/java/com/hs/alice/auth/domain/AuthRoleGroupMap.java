package com.hs.alice.auth.domain;

// Generated 2013. 4. 16 오후 4:38:05 by Hibernate Tools 3.4.0.CR1

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import com.hs.alice.core.AliceCore;

/**
 * AuthRoleGroupMap generated by hbm2java
 */
@Entity
@Table(name = "AUTH_ROLE_GROUP_MAP", schema = "ALICE", uniqueConstraints = @UniqueConstraint(columnNames = {
		"ROLEID", "GROUPID" }))
public class AuthRoleGroupMap implements java.io.Serializable {

	private static final long serialVersionUID = AliceCore.SERIAL_VERSION_UID;

	private Integer mapid;
	private AuthGroup authGroup;
	private AuthRole authRole;

	public AuthRoleGroupMap() {
	}

	public AuthRoleGroupMap(Integer mapid, AuthGroup authGroup,
			AuthRole authRole) {
		this.mapid = mapid;
		this.authGroup = authGroup;
		this.authRole = authRole;
	}

	@Id
	@TableGenerator(name="TABLE_GEN", table="ALICE_ID", pkColumnName="ID_NAME", 
		valueColumnName="ID_COUNT", pkColumnValue="AUTH_ROLE_GROUP_MAP", allocationSize=1)
	@GeneratedValue(strategy=GenerationType.TABLE, generator="TABLE_GEN")
	@Column(name = "MAPID", unique = true, nullable = false, precision = 22, scale = 0)
	public Integer getMapid() {
		return this.mapid;
	}

	public void setMapid(Integer mapid) {
		this.mapid = mapid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "GROUPID", nullable = false)
	public AuthGroup getAuthGroup() {
		return this.authGroup;
	}

	public void setAuthGroup(AuthGroup authGroup) {
		this.authGroup = authGroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ROLEID", nullable = false)
	public AuthRole getAuthRole() {
		return this.authRole;
	}

	public void setAuthRole(AuthRole authRole) {
		this.authRole = authRole;
	}

	@Override
	public String toString() {
		return "AuthRoleGroupMap [mapid=" + mapid + ", groupid=" + authGroup.getGroupid()
				+ ", roleid=" + authRole.getRoleid() + "]";
	}

	
}
