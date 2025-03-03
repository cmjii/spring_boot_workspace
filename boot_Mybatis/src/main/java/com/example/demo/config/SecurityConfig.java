package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.security.CustomUserService;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

	//passwordEncoder => springSecurity5(이후부터 변경사항)
	//createDekkegatingPasswordEncoder => 암호화는 같음{Bcript}추가
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();
	}
	
	//SecurityFilterchain 객체로 설정
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		return http.csrf(csrf-> csrf.disable())
				.authorizeHttpRequests(authorize -> authorize
						.requestMatchers("/index","/","/js/**","/dist/**","/board/list","/member/login",
								"/member/register","/comment/**","/upload/**").permitAll()
						.requestMatchers("/member/list").hasRole("ADMIN").anyRequest().authenticated())
				.formLogin(login -> login
						.usernameParameter("email")
						.passwordParameter("pwd")
						.loginPage("/member/login")
						.defaultSuccessUrl("/").permitAll())
				.logout(logout -> logout
						.logoutUrl("/member/logout")
						.invalidateHttpSession(true)
						.deleteCookies("JSESSIONID")
						.logoutSuccessUrl("/")).build();
	}
	
	
	//AuthrntivationMavaget // 샛페로 설정
	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
	
	
	//UserDetailService : 기존 spring과 같은 클래스
	//리턴값으로 시큐리티에 클래스 만들었음
	@Bean
	UserDetailsService userDetailsService() {
		return new CustomUserService();
	}
}
