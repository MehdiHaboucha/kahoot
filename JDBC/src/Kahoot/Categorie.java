package Kahoot;
public class Categorie {
    private String nomCategorie;
    private int id;

    public Categorie(int id) {
        this.id = id;
    }
    public Categorie( int id,String nomCategorie) {
        this.nomCategorie = nomCategorie;
        this.id = id;
    }

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }


    public void setNomCategorie(String nomCategorie) {
		this.nomCategorie = nomCategorie;
	}
	public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getNomCategorie() {
        return nomCategorie;
    }




    @Override
    public String toString() {
        return "\t"+ "id:"+id + ". categorie:" + nomCategorie+"\n";
    }
}
