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
public class TeamDAO extends GroupDAO {
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
    
    
    public Team create(Team team) {
        
        // pass to GroupDAO to create entry in group table
        team = (Team) super.create(team);
        
        sql = "insert into team (TEAM_GROUP, home_venue, AFFLIATED_idCLUB) "
                + "values (?, ?, ?)";
        
        ResultSet generatedKeys;
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, team.getGroupID());
            stmt.setString(2, team.getHomeVenue());
            
            if (team.getAffliatedClub() != null) {
                stmt.setInt(3, team.getAffliatedClub().getClubID());
            }
            else {
                stmt.setInt(3, 0);
            }
            
            stmt.executeUpdate();
            
            //code for retreiving auto increment value
            generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                
                team.setTeamID(generatedKeys.getInt(1));
                
            }
            
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (NamingException e){
            e.printStackTrace();
        }
        return team;
    }
    
    public Team update(Team team) {
        
        // pass to GroupDAO to update group table
        team = (Team) super.update(team);
        
        //update team table
        sql = "update team set TEAM_GROUP = ?, home_venue = ?, AFFLIATED_idCLUB = ? "
                + "where idTEAM = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, team.getGroupID());
            stmt.setString(2, team.getHomeVenue());
            
            if (team.getAffliatedClub() != null) {
                stmt.setInt(3, team.getAffliatedClub().getClubID());
            }
            else {
                stmt.setInt(3, 0);
            }
            
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
        
        return team;
        
    }
    
    @Override
    public Team read(Integer teamID) {
        
        Team team = new Team();
        
        int affliatedClubID = 0;
        
        // get team object from team table
        
        sql = "select TEAM_GROUP, home_venue, AFFLIATED_idCLUB from team " 
                + "where idTEAM = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, teamID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                team.setGroupID(resultSet.getInt("TEAM_GROUP"));
                team.setHomeVenue(resultSet.getString("home_venue"));
                affliatedClubID = resultSet.getInt("AFFLIATED_idCLUB");
                
                team.setTeamID(teamID);       
                
            }
            else {
                team = null;
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
        
        if (team != null) {
        
            Group group = new Group();
        
            group = super.read(team.getGroupID());
        
            team.setGroupName(group.getGroupName());
            team.setDescription(group.getDescription());
            team.setIsVirtual(group.getIsVirtual());
            team.setManager(group.getManager());
        
        }
// load affliated club
        
        if (affliatedClubID != 0) {
            Club affliatedClub = new Club();
            ClubDAO clubDAO = new ClubDAO();

            affliatedClub = clubDAO.read(affliatedClubID);


            team.setAffliatedClub(affliatedClub);
        }
        return team;
        
    }
    
    public void delete(Team team) {
        
        //delete from team table
        sql = "delete from team where idTEAM = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, team.getTeamID());
            
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
        
        super.delete(team);
    }
    
    public Team readByGroupID(int groupID) {
        
        Team team = new Team();
        
        sql = "select idTEAM from team where TEAM_GROUP = ?";
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                team = read(resultSet.getInt("idTEAM"));
            }
            else {
                team = null;
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
        return team;
        
    }
    
}
