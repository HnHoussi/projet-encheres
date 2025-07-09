package fr.eni.encheres.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/encheres", "/encheres/detail", "/encheres/creer").hasAnyRole("UTILISATEUR", "ADMIN")
                        .requestMatchers("/utilisateur/creer").hasRole("ADMIN")
                        .requestMatchers("/", "/css/**", "/images/**", "/js/**", "/favicon.ico").permitAll()
                        .requestMatchers("/utilisateur/login", "/utilisateur/after-login", "/utilisateur/inscription",
                                "/utilisateur/profil-creer", "/error").permitAll()
                        .anyRequest().denyAll()
                )
                .formLogin(form -> form
                        .loginPage("/utilisateur/login")
                        .loginProcessingUrl("/login")
                        .failureUrl("/utilisateur/login?error")
                        .defaultSuccessUrl("/utilisateur/after-login", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/deconnexion")
                        .logoutSuccessUrl("/encheres")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        JdbcUserDetailsManager users = new JdbcUserDetailsManager(dataSource);

        users.setUsersByUsernameQuery("""
            SELECT pseudo AS username, motdepasse AS password, 1 AS enabled
            FROM UTILISATEURS
            WHERE pseudo = ?
        """);

        users.setAuthoritiesByUsernameQuery("""
            SELECT u.pseudo AS username, r.role
            FROM ROLES r
            JOIN UTILISATEURS u ON u.idutilisateur = r.idutilisateur
            WHERE u.pseudo = ?
        """);

        return users;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
