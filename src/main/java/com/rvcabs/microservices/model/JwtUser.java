package com.rvcabs.microservices.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class JwtUser {
    private String userName;
    private String accountId;
	/*
	 * private String role; private List<GrantedAuthority> grantedAuthorities;
	 */
	public JwtUser(String userName, String accountId) {
		super();
		this.userName = userName;
		this.accountId = accountId;
	}

	
}
