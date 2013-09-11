package jk.springframework.security.core.userdetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class KamoruUser implements UserDetails {

	private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;
	
	private String username;
	private List<GrantedAuthority> authorites = new ArrayList<GrantedAuthority>();

	public KamoruUser(String username) {
		this.username = username;
		this.authorites.add(new SimpleGrantedAuthority("ROLE_USER"));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorites;
	}

	@Override
	public String getPassword() {
		return "crazyjk";
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return isAccountNonExpired() && isAccountNonLocked();
	}

}
