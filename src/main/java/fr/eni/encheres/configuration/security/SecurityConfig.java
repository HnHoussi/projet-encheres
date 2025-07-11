//package fr.eni.encheres.configuration.security;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.provisioning.JdbcUserDetailsManager;
//import org.springframework.security.provisioning.UserDetailsManager;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//	/**
//	 * 🔐 Définition de la chaîne de filtres de sécurité.
//	 * - Gère les autorisations par URL
//	 * - Configure la page de login personnalisée
//	 * - Gère proprement la déconnexion
//	 */
//	@Bean
//	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		http
//				.authorizeHttpRequests(auth -> auth
//						.requestMatchers("/", "/encheres", "/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
//						.requestMatchers("/encheres/detail", "/encheres/creer").hasAnyRole("UTILISATEUR", "ADMIN")
//						.requestMatchers("/utilisateur/creer").hasRole("ADMIN")
//						.requestMatchers("/utilisateur/login", "/utilisateur/after-login", "/utilisateur/inscription",
//								"/utilisateur/profil-creer", "/error").permitAll()
//						.anyRequest().denyAll()
//				)
//
//
//				// 🔑 Configuration du formulaire de connexion personnalisé
//				.formLogin(form -> form
//						.loginPage("/utilisateur/login")                     // Page personnalisée de login
//						.loginProcessingUrl("/login")                        // Traitement du formulaire POST
//						.failureUrl("/utilisateur/login?error")              // Redirection en cas d’échec
//						.defaultSuccessUrl("/utilisateur/after-login", true) // Redirection après succès
//						.permitAll()
//				)
//				// 🚪 Configuration explicite de la déconnexion
//				.logout(logout -> logout
//						.logoutUrl("/deconnexion")                 // URL de déconnexion
//						.logoutSuccessUrl("/encheres")             // Page après déconnexion
//						.invalidateHttpSession(true)               // Invalide la session HTTP
//						.deleteCookies("JSESSIONID")               // Supprime le cookie de session
//						.permitAll()
//				);
//
//		return http.build();
//	}
//
//	/**
//	 * 🧩 Définition du gestionnaire d’utilisateurs basé sur JDBC.
//	 * Permet de récupérer les infos utilisateurs et leurs rôles via SQL.
//	 */
//	@Bean
//	public UserDetailsManager users(DataSource dataSource) {
//		JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);
//
//		// 🔍 Requête SQL personnalisée pour charger l'utilisateur
//		users.setUsersByUsernameQuery("""
//            SELECT pseudo AS username, motdepasse AS password, 1 AS enabled
//            FROM UTILISATEURS
//            WHERE pseudo = ?
//        """);
//
//		// 👥 Requête SQL pour récupérer les rôles associés à l’utilisateur
//		users.setAuthoritiesByUsernameQuery("""
//            SELECT u.pseudo AS username, r.role
//            FROM ROLES r
//            JOIN UTILISATEURS u ON u.idutilisateur = r.idutilisateur
//            WHERE u.pseudo = ?
//        """);
//
//		return users;
//	}
//
//	/**
//	 * 🔐 Fournisseur de mot de passe utilisant BCrypt.
//	 * Utilisé pour encoder et vérifier les mots de passe hachés.
//	 */
//	@Bean
//	public PasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}
//}
