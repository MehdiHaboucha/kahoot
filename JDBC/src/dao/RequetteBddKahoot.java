package dao;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Kahoot.Categorie;
import Kahoot.Joueur;
import Kahoot.Partie;
import Kahoot.Question;
import Kahoot.Reponse;

import java.io.FileReader;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


public class RequetteBddKahoot {
    private static Connection connect;
	 private static RequetteBddKahoot instance;
	public  static RequetteBddKahoot getInstance() throws ClassNotFoundException, SQLException{
       if (instance==null)
       {
           instance = new RequetteBddKahoot();
       }
       
       return instance;
        
       
   }
   
   public RequetteBddKahoot() throws ClassNotFoundException, SQLException{
       connect();
   }

   /**
    *     
    * @throws SQLException
    * @throws ClassNotFoundException 
    */  
     public static void connect() throws SQLException, ClassNotFoundException{
         String dbUrl="jdbc:mysql://localhost:3306/kahootbinks?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";
         String user="root";
         String pwd="";
         connect=DriverManager.getConnection(dbUrl, user, pwd);
      
        
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
    public int addPartie(int nbrJoueur,int idCategorie)
    {
    	int idPartie=0;
        try {
        	
        	SimpleDateFormat formatter= new SimpleDateFormat("yyyy-MM-dd");
        	Date date = new Date(System.currentTimeMillis());
        	
            String requete = "INSERT INTO partie VALUES(NULL,?,?,?)";
            PreparedStatement pstmt = null;
            pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setDate(1, date); 
            pstmt.setInt(2,idCategorie); 
            pstmt.setInt(3,nbrJoueur); 
            pstmt.executeUpdate();
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
                if (generatedKeys.next()) {
                     idPartie =(int) generatedKeys.getLong(1);
                }
                else {
                    throw new SQLException("Creating user failed, no ID obtained.");
                }
        }
        catch(SQLException sqle){
            System.out.println("Erreur SQl: "+sqle.getMessage());
        }
        return idPartie;
    }

    public   List<Reponse> ListerReponseByQuestion(int idQuestion) throws SQLException {
		List<Reponse> lr = new ArrayList<Reponse>();
		// 2 create a statement
		   String requete="SELECT r.* FROM proposition AS p,reponse AS r WHERE `Question_idQuestion`=? AND p.Reponse_idReponse=r.idReponse ;";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
	        pstmt.setInt(1, idQuestion);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			   Reponse r=new Reponse(res.getInt("idReponse"),res.getString("texteReponse"));
			   lr.add(r);
			}
		   
		   return lr;
}
    public  List<Question> listerQuestionsByCategorie(int idCategorie) throws SQLException {
		List<Question> lq = new ArrayList<Question>();
		// 2 create a statement
		   String requete="select * from question WHERE Categorie_idCategorie=?";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
	        pstmt.setInt(1, idCategorie);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			   Question q=new Question(res.getInt("idQuestion"),res.getInt("Reponse_idReponse"),res.getInt("Categorie_idCategorie"),res.getString("texteQuestion"));
			   lq.add(q);
			}
		   return lq;
}
    public  List<Categorie> listerCategorie() throws SQLException {
  		List<Categorie> lc = new ArrayList<Categorie>();
  		// 2 create a statement
  		   String requete="select * from categorie ORDER BY idCategorie";
  		  Statement stmt = connect.createStatement();
  	        ResultSet res = stmt.executeQuery(requete);
  		   while(res.next()) {
  			   Categorie c=new Categorie(res.getInt("idCategorie"),res.getString("nomCategorie"));
  			   lc.add(c);
  			}
  		   return lc;
  }
    public  Partie findPartieById(int idPartie) throws SQLException, ClassNotFoundException {
  		// 2 create a statement
    	Partie p=null;
  		   String requete="select * from partie  WHERE idPartie=?";
  		 PreparedStatement pstmt = connect.prepareStatement(requete);
  		 pstmt.setInt(1, idPartie);
  	        ResultSet res = pstmt.executeQuery();
  		   while(res.next()) {
  			    p=new Partie(res.getInt("idPartie"),res.getDate("datePartie"),res.getInt("Categorie_idCategorie"),res.getInt("nbrJoueur"));
  			}
  		   return p;
  }
    public  String listeBonneReponse(int idQuestion) throws SQLException {
		// 2 create a statement
    		String reponse = null;
		   String requete="SELECT r.texteReponse FROM QUESTION AS q, Reponse As r WHERE q.idQuestion=? AND q.Reponse_idReponse=r.idReponse;";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
	        pstmt.setInt(1, idQuestion);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			    reponse=res.getString("texteReponse");
			}
		   return reponse;
}
    public  void attribuerScore(int score,int idJoueur,int idPartie) throws SQLException {    		
		   String requete="UPDATE `joueur_partie` SET `score`=? WHERE `Joueur_idJoueur`=? AND `Partie_idPartie`=?;";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
		   pstmt.setInt(1, score);
	        pstmt.setInt(2, idJoueur);
	        pstmt.setInt(3, idPartie);
	        pstmt.executeUpdate();		  
}
    public int  getScore(int idJoueur,int idPartie) throws SQLException {
    	int score = 0;
		   String requete="select * FROM joueur_partie where Joueur_idJoueur=? AND Partie_idPartie=?;";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
	        pstmt.setInt(1, idJoueur);
	        pstmt.setInt(2, idPartie);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			    score=res.getInt("score");
			}
		   return score;
    }
    
    public  Joueur getJoueurByPseudo(String pseudo) throws SQLException {
		// 2 create a statement
    		Joueur joueur = null;
		   String requete="SELECT * from joueur Where pseudo=?";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
	        pstmt.setString(1, pseudo);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			    joueur=new Joueur(res.getInt("idJoueur"),res.getString("pseudo"));
			}
		   return joueur;
}
    public  String getJoueurById(int idJoueur) throws SQLException {
  		// 2 create a statement
      		String joueur = null;
  		   String requete="SELECT * from joueur Where idJoueur=?";
  		   PreparedStatement pstmt = connect.prepareStatement(requete);
  	        pstmt.setInt(1, idJoueur);
  	        ResultSet res = pstmt.executeQuery();
  		   while(res.next()) {
  			    joueur=res.getString("pseudo");
  			}
  		   return joueur;
  }
    public  boolean verifierJoueurPartie(int idJoueur,int idPartie) throws SQLException {
		// 2 create a statement
    	int cpt=0;
		   String requete="select * FROM joueur_partie where Joueur_idJoueur=? AND Partie_idPartie=?;";
		   PreparedStatement pstmt = connect.prepareStatement(requete);
		   pstmt.setInt(1, idJoueur);
	        pstmt.setInt(2, idPartie);
	        ResultSet res = pstmt.executeQuery();
		   while(res.next()) {
			   cpt++;
			}
		 if(cpt==0)
			 return true;
		else
			 return false;
}
    
    public void addJoueurPartie(int idJoueur,int idPartie) throws SQLException
    {
            String requete = "INSERT INTO joueur_partie VALUES(?,?,?)";
            PreparedStatement pstmt = null;
            pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, idJoueur);
	        pstmt.setInt(2, idPartie);
	        pstmt.setInt(3, 0);
	        pstmt.executeUpdate();	
    }
    public void Historique(int idJoueur,int idPartie,int idQuestion,int idReponse,int score) throws SQLException
    {	
            String requete = "INSERT INTO questions_partie VALUES(?,?,?,?,?)";
            PreparedStatement pstmt = null;
            pstmt = connect.prepareStatement(requete,Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, idQuestion);
	        pstmt.setInt(2, idPartie);
	        pstmt.setInt(3, idJoueur);
	        if(idReponse==0)
	        pstmt.setNull(4, java.sql.Types.INTEGER);
	        else
	        pstmt.setInt(4, idReponse);
	        pstmt.setInt(5,score);
	        pstmt.executeUpdate();	
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
