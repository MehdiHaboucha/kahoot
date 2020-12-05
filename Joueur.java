package Kahoot;

public class Joueur {
    private int idJoueurs;
    private String login ;



    public Joueur(int idJoueurs, String login) {
        this.idJoueurs = idJoueurs;
        this.login = login;
    }

    public Joueur(String login) {
        this.login = login;
    }


    public String getLogin() {
        return login;
    }

    public void setIdJoueurs(int idJoueurs) {
        this.idJoueurs = idJoueurs;
    }



    @Override
    public String toString() {
        return "\t idJoueurs= " + idJoueurs + ", login= " + login  +  '\n' ;
    }
}
