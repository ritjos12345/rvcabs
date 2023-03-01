package com.rvcabs.microservices.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.rvcabs.microservices.security.HttpCachingRequestWrapperFilter;
import com.rvcabs.microservices.security.JwtAuthenticationEntryPoint;
import com.rvcabs.microservices.security.JwtRequestFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
@Configuration
public class JwtSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService myUserDetailsService;

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(myUserDetailsService);
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Autowired
	private JwtAuthenticationEntryPoint entryPoint;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		System.out.println("configure() called:==>>");
		// TODO enable csrf by uncommenting the below lines and commenting the disabling
		// lines
		// http.csrf().csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).ignoringAntMatchers("/**/**/signIn/**").and()
		// disabling csrf
		http.csrf().disable().cors().and()
				// authorize all of my requests
				.authorizeRequests()
				// if something with rest,those will be authenticated
				.antMatchers("/public/**").permitAll()
				// .antMatchers("/**/**").authenticated().and()

				.anyRequest().authenticated().and()
				// if url is not authenticated,it has to go to entry point
				.exceptionHandling().authenticationEntryPoint(entryPoint).and()
				//
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		// it is going to tell use my filter before going to validate username and
		// password instead of spring default filter
		http.addFilterAfter(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		// http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(),
		// CsrfFilter.class);
		// http.addFilterAfter(decryptionRequestFilter(),
		// UsernamePasswordAuthenticationFilter.class);
		http.headers().cacheControl();
	}
}