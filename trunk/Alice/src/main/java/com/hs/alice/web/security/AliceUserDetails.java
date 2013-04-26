package com.hs.alice.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import com.hs.alice.auth.domain.AuthGroup;
import com.hs.alice.auth.domain.AuthRole;
import com.hs.alice.auth.domain.AuthRoleGroupMap;
import com.hs.alice.auth.domain.AuthRoleUserMap;
import com.hs.alice.auth.domain.AuthUser;
import com.hs.alice.core.AliceCore;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

public class AliceUserDetails implements UserDetails{

	private static final long serialVersionUID = AliceCore.SERIAL_VERSION_UID;

	private AuthUser authUser;
	
	public void setAuthUser(AuthUser authUser) {
		Assert.notNull(authUser, "AuthUser must not be null!");
		this.authUser = authUser;
	}
	
	public AuthUser getAuthUser() {
		return this.authUser;
	}
	
	private List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}

	@Override
	public boolean isAccountNonExpired() {
		return authUser.getAccountexpired() != 'T';
	}

	@Override
	public boolean isAccountNonLocked() {
		return authUser.getAccountlocked() != 'T';
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return authUser.getPasswordexpired() != 'T';
	}

	@Override
	public boolean isEnabled() {
		return this.isAccountNonExpired() && this.isAccountNonLocked();
	}

	public void fillAuthorities() {
		AuthGroup authGroup = authUser.getAuthGroup();
		if(authGroup != null) {
			Set<AuthRoleGroupMap> authRoleGroupMaps = authGroup.getAuthRoleGroupMaps();
			if(authRoleGroupMaps != null) {
				for(AuthRoleGroupMap authRoleGroupMap : authRoleGroupMaps) {
					AuthRole authRole = authRoleGroupMap.getAuthRole();
					this.authorites.add(new SimpleGrantedAuthority(authRole.getRolename()));
				}
			}
		}
		Set<AuthRoleUserMap> authRoleUserMaps = authUser.getAuthRoleUserMaps();
		if(authRoleUserMaps != null) {
			for(AuthRoleUserMap authRoleUserMap : authRoleUserMaps) {
				AuthRole authRole = authRoleUserMap.getAuthRole();
				this.authorites.add(new SimpleGrantedAuthority(authRole.getRolename()));
			}
		}
	}

	@Override
	public String getPassword() {
		return authUser.getPassword();
	}

	@Override
	public String getUsername() {
		return authUser.getUsername();
	}
	
	public Integer getUserid() {
		return authUser.getUserid();
	}

	@Override
	public String toString() {
		return "AliceUserDetails [authUser=" + authUser + ", authorites="
				+ authorites + "]";
	}

	
}
