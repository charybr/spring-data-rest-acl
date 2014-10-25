package sample.sdr.auth.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import sample.sdr.auth.security.SecurityUtil;

@Controller
public class LoginController {
	protected static Logger logger = LoggerFactory
			.getLogger(LoginController.class);

	@RequestMapping(method = RequestMethod.GET, value = "/login_success")
	public @ResponseBody
	String handleLoginSuccess(Principal principal) {
		return SecurityUtil.getUserRoles().toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/login_failure")
	public @ResponseBody
	ResponseEntity<String> handleLoginFailure(Principal principal) {
		return new ResponseEntity<>("Login failed", HttpStatus.UNAUTHORIZED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/logout")
	public @ResponseBody
	String handleLogout() {
		return "Logout successful";
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all/invalid_session")
	public @ResponseBody
	ResponseEntity<String> handleInvalidSession(HttpServletRequest request,
			HttpServletResponse response) {
		return new ResponseEntity<>("Logged in from a different device",
				HttpStatus.REQUEST_TIMEOUT);
	}

	/**
	 * api to set session timeout for current HttpSession. timeoutInSeconds is
	 * optional parameter. If not set, will be defaulted to 24 hours (86400s)
	 * 
	 * @param timeoutInSeconds
	 * @param httpSession
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/loginsession/timeout")
	public @ResponseBody
	String setSessionTimeout(
			@RequestParam(value = "timeoutInSeconds", defaultValue = "86400") int timeoutInSeconds,
			HttpSession httpSession) {
		httpSession.setMaxInactiveInterval(timeoutInSeconds);
		return "httpSession timeout set to:"
				+ httpSession.getMaxInactiveInterval();
	}
}
