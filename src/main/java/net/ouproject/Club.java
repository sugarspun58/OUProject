/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.util.ArrayList;

/**
 *
 * @author tonyxp
 */
public class Club extends Group {
    
    //properties
    
    private int clubID;
    private String location;
    
    // teams affliated to club
    
    private ArrayList<Team> affliatedTeams;
    
    
    //constructor
    
    public Club() {
        
    }
    
    public Club(String clubName, boolean isVirtual, String description, String location){
        
        super(clubName, isVirtual, description);
        this.location = location;
        
    }
    
    // getter and setter methods
    
    public void setClubID(int clubID) {
        this.clubID = clubID;
    }
    
    public int getClubID() {
        return clubID;
    }
    
    public void setLocation(String location){
        this.location = location;
    }
    
    public String getLocation(){
        return location;
    }
    
    // getter method for affliatedTeams
    
    public ArrayList<Team> getAffliatedTeams(){
        return affliatedTeams;
    }
    
    //method for creating club in database
    
    public Club create() {
        
        ClubDAO clubDAO = new ClubDAO();
        
        clubDAO.create(this);
        
        return this;       
    }
    
    public Club update() {
        
        ClubDAO clubDAO = new ClubDAO();
        
        clubDAO.update(this);
        
        return this;
        
    }
    
    public void delete() {
        
        ClubDAO clubDAO = new ClubDAO();
        
        clubDAO.delete(this);
        
    }
    
}
