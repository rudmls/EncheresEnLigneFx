package app.dao;

import app.model.Categorie;
import app.model.OptionEnchere;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CategorieDao extends DAO<Categorie> {

    private static final String SELECT_BY_ID = "SELECT * FROM categorie WHERE categorie_id = ?";
    private static final String SELECT_SOUS_CATEGORIES = "SELECT * FROM categorie WHERE categorie_id IN (SELECT categorie_id FROM categorie WHERE categorie_id = ?)";
    private static final String SELECT_ALL = "SELECT * FROM categorie";


    private ArrayList<Categorie> getResultSet(ResultSet resultSet) throws SQLException {
        ArrayList<Categorie> categories = new ArrayList<>();
        Categorie categorie;
        while (resultSet.next()) {
            categorie = new Categorie();
            categorie.setId(resultSet.getLong("categorie_id"));
            categorie.setLibelle(resultSet.getString("categorie_libelle"));
            categories.add(categorie);
        }
        return categories;
    }


    public ArrayList<Categorie> findAll(){
        ArrayList<Categorie> categories= new ArrayList<>();
        try{
            Statement statement = super.connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SELECT_ALL);
            categories = getResultSet(resultSet);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;

    }

    public ArrayList<Categorie> findAllSuperCategories(){
        String query="SELECT categorie_id,categorie_libelle FROM categorie WHERE super_categorie_id IS null";
        ArrayList<Categorie> categories=new ArrayList<>();
        try{
            PreparedStatement stmt= super.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                Categorie categorie=new Categorie(rs.getLong("categorie_id"),rs.getString("categorie_libelle"));
                categories.add(categorie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categories;
    }

    public ArrayList<Categorie> findAllSousCategorie(long id){
        String query="SELECT categorie_id,categorie_libelle FROM categorie WHERE super_categorie_id ="+id;
        ArrayList<Categorie> sousCategories=new ArrayList<>();
        try{
            PreparedStatement stmt= super.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs=stmt.executeQuery();
            while (rs.next()){
                Categorie categorie=new Categorie(rs.getLong("categorie_id"),rs.getString("categorie_libelle"));
                sousCategories.add(categorie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sousCategories;
    }

    public Categorie find(long id) {
        String query = "SELECT categorie_id,categorie_libelle FROM categorie WHERE categorie_id ="+id;
        try{
            PreparedStatement stmt= super.connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ResultSet rs=stmt.executeQuery();
            while(rs.next()){
                Categorie categorie = new Categorie(rs.getLong("categorie_id"),rs.getString("categorie_libelle"));
                return categorie;}
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Categorie findById(long id) {
        Categorie categorie = null;
        try{
            PreparedStatement preparedStatement = super.connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                categorie = new Categorie();
                categorie.setId(resultSet.getLong("categorie_id"));
                categorie.setLibelle(resultSet.getString("categorie_libelle"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return categorie;
    }

    @Override
    public Categorie create(Categorie obj) {
        return null;
    }

    @Override
    public Categorie update(Categorie obj) {
        return null;
    }

    @Override
    public void delete(Categorie obj) {

    }
}
