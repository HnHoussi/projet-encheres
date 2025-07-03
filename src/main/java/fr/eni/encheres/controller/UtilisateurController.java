package fr.eni.encheres.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UtilisateurController {
	
//DEPENDANCE
	
	//attribut de dependance
	//private UtilisateurService utilisateurService;
	
	//constructeur defaut
	public UtilisateurController() {
		
	}
	//constructeur : injection de dependance
	//public UtilisateurController(UtilisateurService utilisateurService) {
	//	this.utilisateurService = utilisateurService;
	//}
	
	
	//gestion de validation du formulaire creation profil
	@PostMapping("/encheres/profil-creer")
	public String creerProfil() {
		System.out.println("Appel de la methode creerprofil");
		return "profil-creer";
	}


}
