package com.hs.alice.web.support;

import java.beans.PropertyEditorSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.hs.alice.auth.domain.AuthRole;
import com.hs.alice.auth.domain.AuthRoleUserMap;

public class RoleMapPropertyEditor extends PropertyEditorSupport {

	@SuppressWarnings("unchecked")
	@Override
	public String getAsText() {
		System.out.println("getAsText : " + super.getAsText());

		Object object = super.getValue();

		if(object instanceof Map<?, ?>) {
			Map<AuthRole, AuthRoleUserMap> map = (Map<AuthRole, AuthRoleUserMap>)object; 
			String[] array = new String[map.size()];
			int i = 0;
			for(AuthRole authRole : map.keySet()) {
				array[i] = authRole.getRoleid().toString();
			}
			return Arrays.toString(array);
		} 
		else {
			return super.getAsText();
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		System.out.println("setAsText : " + text);
		System.out.println("setAsText : getValue = " + getValue());
		
		Object object = super.getValue();

		if(object instanceof Map<?, ?>) {
			Map<AuthRole, AuthRoleUserMap> rebuild = new HashMap<AuthRole, AuthRoleUserMap>();
			Map<AuthRole, AuthRoleUserMap> map = (Map<AuthRole, AuthRoleUserMap>)object; 
			String[] roleIds = text.split(",");
			for(String roleid : roleIds) {
				System.out.println("\tid=" + roleid);
				boolean found = false;
				for(AuthRole authRole : map.keySet()) {
					if(authRole.getRoleid().toString().equals(roleid)) {
						rebuild.put(authRole, map.get(authRole));
						found = true;
						break;
					}
				}
				if(!found) {
					AuthRole authRole = new AuthRole();
					authRole.setRoleid(Integer.parseInt(roleid));
					
					AuthRoleUserMap authRoleUserMap = new AuthRoleUserMap();
					authRoleUserMap.setAuthRole(authRole);
					
					rebuild.put(authRole, authRoleUserMap);
				}
			}
			super.setValue(rebuild);
		} 
		else {
			super.setAsText(text);
			return;
		}
		
	}
	
}
