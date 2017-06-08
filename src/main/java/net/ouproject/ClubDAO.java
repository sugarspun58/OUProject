/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;
import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;


/**
 *
 * @author tonyxp
 */
public class ClubDAO extends GroupDAO {
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
    
    public Club create(Club club) {
        
        // pass to GroupDAO to create entry in group table
        club = (Club) super.create(club);
        
        sql = "insert into club (location, CLUB_GROUP) "
                + "values (?, ?)";
        
        ResultSet generatedKeys;
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, club.getLocation());
            stmt.setInt(2, club.getGroupID());
            
            stmt.executeUpdate();
            
            //code for retreiving auto increment value
            
            generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                
                club.setClubID(generatedKeys.getInt(1));
                
            }
            
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }        
        catch (NamingException e) {
            e.printStackTrace();
        }
        
        return club;
    }
    
    public Club update(Club club) {
        
        //pass to GroupDAO to update group table
        club = (Club) super.update(club);
        
        //update club table
        
        sql = "update club set location = ?, CLUB_GROUP = ? "
                + "where idCLUB = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql); 
            
            stmt.setString(1, club.getLocation());
            stmt.setInt(2, club.getGroupID());
            stmt.setInt(3, club.getClubID());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        return club;
    }
    
    @Override
    public Club read(Integer clubID) {
        
        Club club = new Club();
        
        // get club object from club table
        
        sql = "select location, CLUB_GROUP from club "
                + "where idCLUB = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);            
            stmt.setInt(1, clubID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                club.setGroupID(resultSet.getInt("CLUB_GROUP"));
                club.setLocation(resultSet.getString("location"));
                
                club.setClubID(clubID);
                               
            }
            else {
                club = null;
            }
            
            resultSet.close();
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        // pass to GroupDAO to load group table data
        
        if (club != null) {
        
            Group group = new Group();
            
            group = super.read(club.getGroupID());
            
            club.setGroupName(group.getGroupName());
            club.setDescription(group.getDescription());
            club.setIsVirtual(group.getIsVirtual());
            club.setManager(group.getManager());
            
        }
                
        return club;
    }
    
    public void delete(Club club) {
        
        //delete from club table
        
        sql = "delete from club where idCLUB = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, club.getClubID());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        
        // pass to groupDAO to delete entry from group table
        
        super.delete(club);
        
    }
    public Club readByGroupID(int groupID) {
        
        Club club = new Club();
        
        sql = "select idCLUB from club where CLUB_GROUP = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                club = read(resultSet.getInt("idCLUB"));
            }
            else {
                club = null;
            }
            
            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        return club;
        
    }
}
