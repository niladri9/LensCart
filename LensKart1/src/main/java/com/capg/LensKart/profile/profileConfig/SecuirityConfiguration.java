package com.capg.LensKart.profile.profileConfig;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.capg.LensKart.profile.jwt.Filter.JwtFilter;
import com.capg.LensKart.profile.profileservice.UserService;

@Configuration
@EnableWebSecurity
public class SecuirityConfiguration extends WebSecurityConfigurerAdapter{

	@Autowired
	private UserService userservice;
	@Autowired
	private JwtFilter jwtFilter;
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userservice);
	}
	
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		 http.csrf()
         .disable()
         .authorizeRequests()
         .antMatchers("/Signup/**").permitAll()
         .antMatchers("/v3/api-docs/**").permitAll()
         .antMatchers("/swagger.json").permitAll()
         .antMatchers("/swagger-ui/**").permitAll()
         .antMatchers("/swagger-resources/**").permitAll()
         .antMatchers("/webjars/**").permitAll()
         .antMatchers("/TestEmailValidity/**").permitAll()
         .antMatchers("/ResetPassword/**").permitAll()
         .antMatchers("/GetAllAdminEmail/**").permitAll()
         .antMatchers("/allowed/**").permitAll()
         .antMatchers("/authenticate")
         .permitAll()
         .anyRequest()
         .authenticated()
         //.and().formLogin().permitAll()
         .and()
         .sessionManagement()
         .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		 http.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
}
