package com.hs.alice.web;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.support.SessionStatus;

import com.hs.alice.auth.service.AuthService;
import com.hs.alice.auth.domain.AuthGroup;
import com.hs.alice.auth.domain.AuthRole;
import com.hs.alice.auth.domain.AuthUser;
import com.hs.alice.web.security.AliceUserDetails;

@Controller
@SessionAttributes({"authUser", "authGroup", "authRole"})
@RequestMapping("/auth")
public class AuthController {

    private static final Log logger = LogFactory.getLog(AuthController.class);
	
	@Autowired private AuthService authService;

	/* register CustomEditor. in this case, it's not necessary.*/	
	//@Inject Provider<AuthRoleUserMapPropertyEditor> authRoleUserMapPropertyEditorProvider;
	
	@InitBinder
	public void initBinder(WebDataBinder dataBinder) {
//		dataBinder.registerCustomEditor(AuthRoleUserMap.class, authRoleUserMapPropertyEditorProvider.get());
//		dataBinder.registerCustomEditor(Map.class, "authRoleUserMaps", new RoleMapPropertyEditor());
	}
	

	@ModelAttribute
	public List<AuthGroup> groups() {
		return this.authService.getGroupList(); 
	}
	
	@ModelAttribute
	public List<AuthRole> roles() {
		List<AuthRole> list = this.authService.getRoleList();
		return  list;
	}

	@ModelAttribute("currentUser")
	public AliceUserDetails currentUser() {
		return (AliceUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
	
	// about AuthUser =============================================================================
	@RequestMapping()
	public String authHome() {
		return "auth/home";
	}

	
	@RequestMapping("/user")
	public String userList(Model model) {
		model.addAttribute("userList", authService.getUserList());
		return "auth/userList";
	}
	
	@RequestMapping("/user/{userid}")
	public String userDetail(Model model, @PathVariable Integer userid) {
		model.addAttribute("authUser", authService.fetchUserById(userid));
		return "auth/userDetail";
	}
	
	@RequestMapping(value="/user/{userid}/edit", method=RequestMethod.GET)
	public String showEditForm(Model model, @PathVariable Integer userid) {
		model.addAttribute("authUser", authService.fetchUserById(userid));
		return "auth/userForm";
	}
	
	@RequestMapping(value="/user/{userid}/edit", method=RequestMethod.POST)
	public String editUser(@ModelAttribute AuthUser authUser, SessionStatus session, @PathVariable Integer userid) {
		logger.debug(authUser);
		authService.updateUser(authUser);
		session.setComplete();
		return "redirect:/auth/user/{userid}";
	}

	@RequestMapping(value="/user/{userid}/delete", method=RequestMethod.GET)
	public String deleteUserRoles(@PathVariable Integer userid) {
		authService.deleteUser(userid);
		return "redirect:/auth/user";
	}
	
	@RequestMapping(value="/user/new", method=RequestMethod.GET)
	public String showForm(Model model) {
		AuthUser authUser = new AuthUser();
		authUser.setUsername("이름");
		model.addAttribute("authUser", authUser);
		return "auth/userForm";
	}

	@RequestMapping(value="/user/new", method=RequestMethod.POST)
	public String saveUser(@ModelAttribute AuthUser authUser, SessionStatus session, Model model) {
		logger.debug(authUser);
		int newUserid = this.authService.saveUser(authUser);
		session.setComplete();
		return "redirect:/auth/user/" + newUserid;
	}

	// about AuthGroup ============================================================================

	@RequestMapping("/group")
	public String groupList(Model model) {
//		model.addAttribute("groupList", authService.getGroupList());
		return "auth/groupList";
	}

	@RequestMapping("/group/{groupid}")
	public String groupDetail(Model model, @PathVariable int groupid) {
		model.addAttribute("authGroup", authService.getGroupDetailById(groupid));
		return "auth/groupDetail";
	}

	@RequestMapping(value="/group/{groupid}/edit", method=RequestMethod.GET)
	public String showGroupForm(Model model, @PathVariable int groupid) {
		model.addAttribute("authGroup", authService.getGroupDetailById(groupid));
		return "auth/groupForm";
	}
	
	@RequestMapping(value="/group/{groupid}/edit", method=RequestMethod.POST)
	public String editGroup(@ModelAttribute AuthGroup authGroup, SessionStatus session, @PathVariable int groupid) {
		logger.debug(authGroup);
		authService.updateGroup(authGroup);
		session.setComplete();
		return "redirect:/auth/group/{groupid}";
	}

	@RequestMapping(value="/group/{groupid}/delete", method=RequestMethod.GET)
	public String deleteGroup(@PathVariable int groupid) {
		authService.deleteGroup(groupid);
		return "redirect:/auth/group";
	}
	
	@RequestMapping(value="/group/new", method=RequestMethod.GET)
	public String showGroupForm(Model model) {
		AuthGroup authGroup = new AuthGroup();
		model.addAttribute("authGroup", authGroup);
		return "auth/groupForm";
	}

	@RequestMapping(value="/group/new", method=RequestMethod.POST)
	public String saveGroup(@ModelAttribute AuthGroup authGroup, SessionStatus session) {
		logger.debug(authGroup);
		int groupid = this.authService.saveGroup(authGroup);
		session.setComplete();
		return "redirect:/auth/group/" + groupid;
	}

	// about AuthRole =============================================================================

	@RequestMapping("/role")
	public String roleList(Model model) {
		model.addAttribute(authService.getRoleList());
		return "auth/roleList";
	}
	
	@RequestMapping("/role/{roleid}")
	public String roleDetail(Model model, @PathVariable int roleid) {
		model.addAttribute("role", authService.getRoleDetailById(roleid));
		return "auth/roleDetail";
	}

}
