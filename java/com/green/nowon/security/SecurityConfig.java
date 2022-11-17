package com.green.nowon.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@EnableWebSecurity
public class SecurityConfig{
	
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		
		http
			.authorizeRequests(authorize -> authorize
					.antMatchers("/css/**","/js/**").permitAll()
					//.antMatchers(HttpMethod.PUT ,"/boards/**").hasRole("USER")
					.antMatchers(HttpMethod.DELETE ,"/boards/**").hasRole("USER")
					.antMatchers(HttpMethod.POST,"/boards/**").hasRole("USER")
					.antMatchers(HttpMethod.GET,"/boards/write").hasRole("USER")
					.antMatchers("/","/boards/**").permitAll()
					.anyRequest().authenticated()
			)
			//.csrf().disable()
			//.csrf(csrf -> csrf.disable())
			.formLogin(login->login
					.loginPage("/signin")
					.usernameParameter("email")// username->email
					.passwordParameter("pass")// password->pass
					.loginProcessingUrl("/signin/proc")//form action
					.failureUrl("/signin?myerror")
					//.defaultSuccessUrl("/",true)//인증성공 후 무조건 / 페이지로 이동
					.successHandler(mySuccessHandler())//인증성공이후 이동할 페이지처리
					.permitAll())
			;
			
			
		return http.build();
	}

	@Bean
	public AuthenticationSuccessHandler mySuccessHandler() {
		return new MySuccessHandler();
	}
	
}