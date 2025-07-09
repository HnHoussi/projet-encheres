package fr.eni.encheres.bll;

import fr.eni.encheres.bo.Utilisateur;
import fr.eni.encheres.dal.UtilisateurDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {
    private UtilisateurDAO utilisateurDAO;

    public UtilisateurServiceImpl(UtilisateurDAO utilisateurDAO) {
        this.utilisateurDAO = utilisateurDAO;
    }


    @Override
    public void creerCompte(Utilisateur utilisateur) {
        utilisateurDAO.create(utilisateur);
    }

    @Override
    public void modifierProfil(Utilisateur utilisateur) {
        utilisateurDAO.update(utilisateur);
    }


    @Override
    public Utilisateur consulterUtilisateur(long idUtilisateur) {
        return utilisateurDAO.findById(idUtilisateur);
    }

    @Override
    public void desactiverCompte(long idUtilisateur) {

        Utilisateur utilisateur = utilisateurDAO.findById(idUtilisateur);
        utilisateur.setCompteActif(false);
        utilisateurDAO.update(utilisateur);
    }

    @Override
    public void activerCompte(long idUtilisateur) {
        Utilisateur utilisateur = utilisateurDAO.findById(idUtilisateur);
        utilisateur.setCompteActif(true);
        utilisateurDAO.update(utilisateur);
    }

    @Override
    public void supprimerCompte(long idUtilisateur) {
        utilisateurDAO.delete(idUtilisateur);
    }

    @Override
    public List<Utilisateur> consulterListeUtilisateurs() {
        return utilisateurDAO.findAll();
    }

    public Utilisateur connexion(String email, String motDePasse, String pseudo) {

        return null;
    }
    public Utilisateur charger(String email){

    return null;

  }

}
