package Kahoot;

import java.util.List;

public class Reponse {

    private int idReponse;
    private String textReponse;
    private int nb ;

    public Reponse(int idReponse, String textReponse,int nb) {
        this.idReponse = idReponse;
        this.textReponse = textReponse;
        this.nb=nb;
    }

    public Reponse(String textReponse) {
        this.textReponse = textReponse;
    }

    public void setIdReponse(int idReponse) {
        this.idReponse = idReponse;
    }

    public int getIdReponse() {
        return idReponse;
    }

    @Override
    public String toString() {
        return "\t"+ nb + ". " + textReponse+"\n";
    }


    public String getTextReponse() {
        return textReponse;
    }
    public static String getTextReponseByid(List<Reponse> reponses, int idReponse) {
        for (Reponse r : reponses){
            if (r.getIdReponse()==idReponse)
                return r.getTextReponse();
        }
        return null;
    }
}
