package fr.eni.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
			.authorizeHttpRequests((authorize) -> authorize
				.requestMatchers("/encheres").hasAnyRole("UTILISATEUR", "ADMIN")
				.requestMatchers("/encheres/detail").hasAnyRole("UTILISATEUR", "ADMIN")
				.requestMatchers("/encheres/creer").hasAnyRole("UTILISATEUR", "ADMIN")
				.requestMatchers("/utilisateur/creer").hasRole("ADMIN")
				.requestMatchers("/", "/css/*", "/images/*").permitAll()
				.anyRequest().denyAll()
			)
				.formLogin(form -> form
						.loginPage("/utilisateur/login") // chemin vue connexion personnalisé
						.failureUrl("/utilisateur/login?error")
						.defaultSuccessUrl("/encheres", true)
						.permitAll()
				)
				.logout(Customizer.withDefaults()); // Optionnel : configurer la déconnexion

		return http.build();
	}


	/**
	 * Gère les utilisateurs en BDD
	 * @param dataSource
	 * @return
	 */
	@Bean
	UserDetailsManager users(DataSource dataSource) {

		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
		users.setUsersByUsernameQuery("SELECT pseudo AS username, password, compteActif AS enabled FROM UTILISATEUR WHERE pseudo = ?");
		users.setAuthoritiesByUsernameQuery("SELECT pseudo AS username, role FROM ROLES WHERE pseudo = ?");
		return users;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}


}
