package sample.sdr.auth.security;

import java.util.ArrayList;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SecurityUtil {
	private static Logger logger = LoggerFactory.getLogger(SecurityUtil.class);

	public static String getUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth.getPrincipal() instanceof UserDetails) {
            return ((UserDetails) auth.getPrincipal()).getUsername();
        } else {
            return auth.getPrincipal().toString();
        }
    }

	public static ArrayList<String> getUserRoles() {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		Collection<? extends GrantedAuthority> authorities = auth
				.getAuthorities();
		ArrayList<String> currentUserRoles = new ArrayList<String>();
		for (GrantedAuthority authority : authorities) {
			currentUserRoles.add(authority.getAuthority());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("currentUserRoles:" + currentUserRoles);
		}
		return currentUserRoles;
	}
    
    public static boolean doesUserHasRole(String role) {
    	ArrayList<String> currentUserRoles = getUserRoles();
    	for(String s : currentUserRoles) {
    		if(s.equals(role)) {
    			return true;
    		}
    	}
    	return false;
    }    
}
