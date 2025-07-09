package fr.eni.encheres;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EniEncheresApplication {

    public static void main(String[] args) {
        SpringApplication.run(EniEncheresApplication.class, args);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode("testpseudo");
        System.out.println("Hash : " + hash);
        System.out.println("Vérification : " + encoder.matches("testpseudo", hash)); // ➜ true

    }

}
