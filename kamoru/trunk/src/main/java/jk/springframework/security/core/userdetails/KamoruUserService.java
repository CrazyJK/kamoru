package jk.springframework.security.core.userdetails;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**로그인 요청한 사용자를 찾아주는 service<br>
 * 현재는 유일 사용자만 반환
 * @author kamoru
 *
 */
public class KamoruUserService implements UserDetailsService {

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		KamoruUser user = new KamoruUser(username);
		return user;
	}

	
}
