package fr.eni.encheres.bo;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class Utilisateur implements Serializable {
    private long idUtilisateur;
    private String pseudo;
    private String nom;
    private String prenom;
    private String email;
    private String telephone;
    private String rue;
    private String codePostal;
    private String ville;
    private String motDePasse;
    private int credit = 100;
    private boolean administrateur;
    private boolean compteActif = true;

    private List<Article> articles;
    private List<Enchere> encheres;

    public Utilisateur() {
    }

    public Utilisateur(long idUtilisateur, String pseudo, String nom, String prenom, String email, String telephone, String rue, String codePostal, String ville, String motDePasse, int credit, boolean administrateur, boolean compteActif, List<Article> articles, List<Enchere> encheres) {
        this.idUtilisateur = idUtilisateur;
        this.pseudo = pseudo;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.telephone = telephone;
        this.rue = rue;
        this.codePostal = codePostal;
        this.ville = ville;
        this.motDePasse = motDePasse;
        this.credit = credit;
        this.administrateur = administrateur;
        this.compteActif = compteActif;
        this.articles = articles;
        this.encheres = encheres;
    }

    public long getIdUtilisateur() {
        return idUtilisateur;
    }

    public void setIdUtilisateur(long idUtilisateur) {
        this.idUtilisateur = idUtilisateur;
    }

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    public String getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(String codePostal) {
        this.codePostal = codePostal;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getMotDePasse() {
        return motDePasse;
    }

    public void setMotDePasse(String motDePasse) {
        this.motDePasse = motDePasse;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public boolean isAdministrateur() {
        return administrateur;
    }

    public void setAdministrateur(boolean administrateur) {
        this.administrateur = administrateur;
    }

    public boolean isCompteActif() {
        return compteActif;
    }

    public void setCompteActif(boolean compteActif) {
        this.compteActif = compteActif;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public List<Enchere> getEncheres() {
        return encheres;
    }

    public void setEncheres(List<Enchere> encheres) {
        this.encheres = encheres;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Utilisateur{");
        sb.append("idUtilisateur=").append(idUtilisateur);
        sb.append(", pseudo='").append(pseudo).append('\'');
        sb.append(", nom='").append(nom).append('\'');
        sb.append(", prenom='").append(prenom).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", telephone='").append(telephone).append('\'');
        sb.append(", rue='").append(rue).append('\'');
        sb.append(", codePostal='").append(codePostal).append('\'');
        sb.append(", ville='").append(ville).append('\'');
        sb.append(", motDePasse='").append(motDePasse).append('\'');
        sb.append(", credit=").append(credit);
        sb.append(", administrateur=").append(administrateur);
        sb.append(", articles=").append(articles);
        sb.append(", encheres=").append(encheres);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Utilisateur that = (Utilisateur) o;
        return idUtilisateur == that.idUtilisateur;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idUtilisateur);
    }
}
