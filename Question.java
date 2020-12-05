package Kahoot;

public class Question{

    private int idQuestion;
    private int idbonneReponse;
    private int idCategorie;
    private String textQuestion;

    public Question(int idQuestion, int idbonneReponse, int idCategorie, String textQuestion) {
        this.idQuestion = idQuestion;
        this.idbonneReponse = idbonneReponse;
        this.idCategorie = idCategorie;
        this.textQuestion = textQuestion;
    }

    public Question(int idbonneReponse, int idCategorie, String textQuestion) {
        this.idbonneReponse = idbonneReponse;
        this.idCategorie = idCategorie;
        this.textQuestion = textQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public int getidbonneReponse() {
        return idbonneReponse;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public String getTextQuestion() {
        return textQuestion;
    }

    @Override
    public String toString() {
        return "Question{" +
                "idQuestion=" + idQuestion +
                ", idbonneReponse=" + idbonneReponse +
                ", idCategorie=" + idCategorie +
                ", textQuestion='" + textQuestion + '\'' +
                '}';
    }
}