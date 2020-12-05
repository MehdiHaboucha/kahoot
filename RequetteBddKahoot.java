package Kahoot;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.sql.*;


public class RequetteBddKahoot {
    private static Connection connect;
    private static String url="jdbc:mysql://localhost:3306/kahootbinks";
    private static String user="admin";
    private static String mdp="mysql";

    /**
     * RequetteBddKahoot()
     * permet la connection avc la bdd
     * @throws SQLException
     */
    public RequetteBddKahoot() throws SQLException {
        connect = DriverManager.getConnection(url,user,mdp);
    }


    /**
     *addJoueur
     * @param j (joueur)
     * @return True/False
     * @throws SQLException
     */
    public int addJoueur(Joueur j) throws SQLException{
        try{
            String requete = "INSERT INTO joueur () VALUES (NULL,?);";
            PreparedStatement pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(j.getLogin()));
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int id=0;
            if (res.next()) {
                id = res.getInt(1);
            }
            res.close();
            pstmt.close();
            j.setIdJoueurs(id);
            return 1;
        }
        catch(SQLException sqle){
            if (sqle.getSQLState().equals("23000")){
                return  -2;
            }
            System.out.println("Erreur SQl: "+sqle.getMessage());
            return 0;
        }

    }

    /**
     * addCategorie
     * @param c
     * @return l'objet si reussi null si echec
     * @throws SQLException
     */
    public Categorie addCategorie(Categorie c) throws SQLException{
        try{
            String requete = "INSERT INTO categorie VALUES (NULL,?);";
            PreparedStatement pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(c.getNomCategorie()));
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int c1=0;
            while (res.next()){
                c1 =res.getInt(1);

            }
            res.close();
            pstmt.close();
            c.setId(c1);
            return c;
        }
        catch(SQLException sqle){
            System.out.println("Erreur SQl: "+sqle.getMessage());
            return null;
        }

    }

    /**
     * addReponse
     * @param r
     * @return l'objet si reussi null si echec
     * @throws SQLException
     */
    public Reponse addReponse(Reponse r) throws SQLException{
        try{
            String requete = "INSERT INTO reponse VALUES (NULL,?);";
            PreparedStatement pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, String.valueOf(r.getTextReponse()));
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int r1=-1;
            while (res.next()){
                r1 =res.getInt(1);
            }
            res.close();
            pstmt.close();
            r.setIdReponse(r1);
            return r;
        }
        catch(SQLException sqle){
            System.out.println("Erreur SQl: "+sqle.getMessage());
            return null;
        }

    }

    /**
     * addQuestion
     * @param q
     * @return True/False
     * @throws SQLException
     */
    public Boolean addQuestion(Question q)throws SQLException{
        try{
            String requete = "INSERT INTO question VALUES (NULL,?,?,?);";
            PreparedStatement pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(3  , Integer.valueOf(q.getidbonneReponse()));
            pstmt.setInt(2, Integer.valueOf(q.getIdCategorie()));
            pstmt.setString(1, String.valueOf(q.getTextQuestion()));
            pstmt.executeUpdate();
            ResultSet res = pstmt.getGeneratedKeys();
            int q1=-1;
            while (res.next()){
                q1 =res.getInt(1);
            }
            res.close();
            q.setIdQuestion(q1);
            return true;
        }
        catch(SQLException sqle){
            System.out.println("Erreur SQl: "+sqle.getMessage());
            return false;
        }
    }

    /**
     * addProposition
     * @param q
     * @param r
     * @return
     */
    public boolean addProposition(Question q, Reponse r)
    {
        try {
            String requete = "INSERT INTO proposition VALUES(?,?)";
            PreparedStatement pstmt = null;
            pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            String v=String.valueOf(q.getIdQuestion());
            String k=String.valueOf(r.getIdReponse());

            pstmt.setString(2, String.valueOf(q.getIdQuestion())); // Identifiant de la question qui vient d'être créée
            pstmt.setString(1, String.valueOf(r.getIdReponse())); // Identifiant de la réponse de cette proposition
            pstmt.executeUpdate();
            return true;
        }
        catch(SQLException sqle){
            System.out.println("Erreur SQl: "+sqle.getMessage());
            return false;
        }
    }

    /**
     * ImportJson
     * @param fichier
     * @return
     */
    public boolean ImportJson(String fichier){
        JSONParser jsonP2 = new JSONParser();
        //JSONArray test = (JSONArray) ((JSONObject) ((JSONObject) toutJson.get("quizz")).get("fr")).get("débutant");
        // Lecture des questions en fr et niveau débutan
        try{
            JSONObject toutJson = (JSONObject) jsonP2.parse(new FileReader(fichier));
            // STOCK CATEGORIE
            Categorie c = new Categorie((String) toutJson.get("thème"));
            if(addCategorie(c)==null){
                return false;
            }
            //TRAITER QUESTION PAR QUESTION
            JSONObject quizz = (JSONObject) toutJson.get("quizz");
            quizz = (JSONObject) quizz.get("fr");
            JSONArray debutant = (JSONArray) quizz.get("débutant");
            for(Object jO : debutant) {
                JSONObject question = (JSONObject) jO;
                //STOCK BONNE REPONSE DANS REPONSE
                Reponse bonneRep = new Reponse((String) question.get("réponse"));
                if(addReponse(bonneRep)==null){
                    return false;
                }

                //STOCK LA QUESTION ET BONNE REPONSE DANS PROPOSITIONS
                Question q = new Question(bonneRep.getIdReponse(),c.getId(),(String) question.get("question"));
                if(addQuestion(q)==null){
                    return false;
                }
                if (!addProposition(q,bonneRep)){
                    return false;
                }


                //STOCK LES PROPOSITIONS DANS REPONSE ET PROPOSITIONS
                JSONArray propositions = (JSONArray) question.get("propositions");
                for (int i = 0; i < 4; i++) {
                    if (!bonneRep.getTextReponse().equals((String) propositions.get(i))){
                        Reponse r = new Reponse((String) propositions.get(i));
                        if (addReponse(r)==null){
                            return false;
                        }
                        if (!addProposition(q,r)){
                            return false;
                        }
                    }
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

}
