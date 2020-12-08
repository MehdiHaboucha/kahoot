	package Kahoot;

import java.io.Serializable;

public class Joueur implements Serializable {
    private int idJoueur;
    private String login ;



    public int getIdJoueur() {
		return idJoueur;
	}

	public void setIdJoueur(int idJoueur) {
		this.idJoueur = idJoueur;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public Joueur(int idJoueur, String login) {
        this.idJoueur = idJoueur;
        this.login = login;
    }

    public Joueur(String login) {
        this.login = login;
    }


    public String getLogin() {
        return login;
    }

    public void setIdJoueurs(int idJoueurs) {
        this.idJoueur = idJoueurs;
    }



    @Override
    public String toString() {
        return "\t idJoueurs= " + idJoueur + ", login= " + login  +  '\n' ;
    }
}
