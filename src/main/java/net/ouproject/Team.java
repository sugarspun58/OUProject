/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

/**
 *
 * @author tonyxp
 */
public class Team extends Group{
    
    private int teamID;
    private String homeVenue;   
    
    // affilated club
    
    private Club affliatedClub;
    
    
    
    //constructor
    
    public Team() {
        
    }
    
    public Team(String teamName, boolean isVirtual, String description, 
            String homeVenue, Club affilatedClub){
        
        super(teamName, isVirtual, description);
        this.homeVenue = homeVenue;
        this.affliatedClub = affliatedClub;
    }
    
    // getter setter methods
    
    public void setTeamID(int teamID) {
        this.teamID = teamID;
    }   
    
    public int getTeamID() {
        return teamID;
    }
    
    public void setHomeVenue(String homeVenue) {
        this.homeVenue = homeVenue;
    }
    
    public String getHomeVenue() {
        return homeVenue;
    }
    
    // getter setter methods for affilated club
    
    public void setAffliatedClub(Club affilatedClub){
        this.affliatedClub = affilatedClub;
    }
    
    public Club getAffliatedClub(){
        return affliatedClub;
    }
    
    // method for creating team in database
    
    public Team create() {
        
        TeamDAO teamDAO = new TeamDAO();
        
        teamDAO.create(this);
        
        return this;
        
    }
    
    public Team update() {
        
        TeamDAO teamDAO = new TeamDAO();
        
        teamDAO.update(this);
        
        return this;
        
    }
    
    public void delete() {
        
        TeamDAO teamDAO = new TeamDAO();
        
        teamDAO.delete(this);
        
    }
    
    
}
