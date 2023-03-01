package com.rvcabs.microservices.security;

public interface JwtKeyConstants {

	static final String ISSUER = "RvCabs";
	
	static final String SIGN_IN_KEY = "CpoxyPASS!@#";
	
	static final int TOKEN_EXPIRY_TIME = 30;
}
