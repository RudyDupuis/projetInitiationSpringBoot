package fr.eni.tp.filmotheque.configuration.security;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class FilmothequeSecurityConfig {

	@Bean
	UserDetailsManager userDetailManager(DataSource dataSource) {
		JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);
		jdbcUserDetailsManager.setUsersByUsernameQuery("SELECT email, password, 1 FROM membre WHERE email = ?");
		jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
				"SELECT m.email, r.role FROM membre m INNER JOIN roles r ON r.is_admin = m.admin WHERE m.email = ?");
		return jdbcUserDetailsManager;
	}

	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests(auth -> {
			auth.requestMatchers("/").permitAll()
				.requestMatchers("/css/*").permitAll()
				.requestMatchers("/img/*").permitAll()
				.requestMatchers("/films").permitAll()
				.requestMatchers("/films/detail").permitAll()
				.anyRequest().authenticated();
		});

		http.formLogin(form -> form.loginPage("/login").permitAll().defaultSuccessUrl("/session").permitAll());

		http.logout(logout -> logout.invalidateHttpSession(true)
									.clearAuthentication(true)
									.deleteCookies("JSESSIONID")
									.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
									.logoutSuccessUrl("/")
									.permitAll());

		return http.build();
	}

}
