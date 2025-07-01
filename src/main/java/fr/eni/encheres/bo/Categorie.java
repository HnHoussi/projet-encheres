package fr.eni.encheres.bo;

import java.util.Objects;

public class Categorie {
    private int idCategorie;
    private String libelle;

    private Categorie categorie;

    public Categorie() {
    }

    public Categorie(int idCategorie, String libelle, Categorie categorie) {
        this.idCategorie = idCategorie;
        this.libelle = libelle;
        this.categorie = categorie;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Categorie{");
        sb.append("idCategorie=").append(idCategorie);
        sb.append(", libelle='").append(libelle).append('\'');
        sb.append(", categorie=").append(categorie);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Categorie categorie = (Categorie) o;
        return idCategorie == categorie.idCategorie;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(idCategorie);
    }
}
