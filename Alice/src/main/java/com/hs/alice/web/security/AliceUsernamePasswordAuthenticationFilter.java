package com.hs.alice.web.security;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.hs.alice.util.crypt.AliceCrypt;

public class AliceUsernamePasswordAuthenticationFilter extends
		UsernamePasswordAuthenticationFilter {

	private static final Logger logger = LoggerFactory.getLogger(AliceUsernamePasswordAuthenticationFilter.class);

	private String enc;
	private AliceCrypt crypt;
	
	public void setEnc(String enc) {
		this.enc = enc;
	}

	public void setCrypt(AliceCrypt crypt) {
		this.crypt = crypt;
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		String encryptStr = crypt.encrypt(super.obtainPassword(request));
		logger.info("encrypt str : " + encryptStr);
		return encryptStr;
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		try {
			request.setCharacterEncoding(enc);
		} catch (UnsupportedEncodingException e) {
		}
		return super.obtainUsername(request);
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		logger.info("== successful login ==");
		super.successfulAuthentication(request, response, chain, authResult);
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		logger.info("== failed login ==");
		super.unsuccessfulAuthentication(request, response, failed);
	}

	
}
