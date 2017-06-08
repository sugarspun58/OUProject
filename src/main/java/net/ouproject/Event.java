/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.util.Calendar;
import java.util.ArrayList;

/**
 *
 * @author tonyxp
 */
public class Event {
    
    //properties
    
    private int eventID;
    private String eventName;
    private Calendar startDateAndTime;
    private int duration;
    private String venue;
    private boolean isCancelled;
    private String cancelReason;
    private String description;
    
    // linked Group
    
    private Group linkedGroup;
    
    // linked manager
    
    private User linkedManager;
    
    // users available for event (ie seeking a partner)
    
    private ArrayList<User> usersAvailable;
    
    
    //constructor
    
    //default
    
    public Event() {
        
        isCancelled = false;
        cancelReason = "";
        
    }
    
    public Event(String eventName, Calendar start, int duration, String venue, String description){
        
        isCancelled = false;
        cancelReason = "";
        this.eventName = eventName;
        this.startDateAndTime = start;
        this.duration = duration;
        this.venue = venue;
        this.description = description;
    }
    
    
    // getter and setter methods
    
    public void setEventID(int eventID) {
        this.eventID = eventID;
    }
    
    public int getEventID() {
        return eventID;
    }
    
    public void setEventName(String eventName){
        this.eventName = eventName;
    }
    
    public String getEventName(){
        return eventName;
    }
    
    public void setStartDateAndTime(Calendar dateAndTime){
        this.startDateAndTime = dateAndTime;
    }
    
    public Calendar getStartDateAndTime(){
        return startDateAndTime;
    }
    
    public void setDuration(int duration){
        this.duration = duration;
    }
    
    public int getDuration(){
        return duration;
    }
    
    public void setVenue(String venue){
        this.venue = venue;
    }
    
    public String getVenue(){
        return venue;
    }
    
    public void setIsCancelled(boolean isCancelled){
        this.isCancelled = isCancelled;
    }
    
    public boolean getIsCancelled(){
        return isCancelled;
    }
    
    public void setCancelReason(String cancelReason){
        this.cancelReason = cancelReason;
    }
    
    public String getCancelReason(){
        return cancelReason;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription(){
        return description;
    }
    
    // getter setter method for linkedGroup
    
    public void setLinkedGroup(Group linkedGroup){
        this.linkedGroup = linkedGroup;
    }
    
    public Group getLinkedGroup(){
        return linkedGroup;
    }
    
    // getter setter method for linkedManager
    
    public void setManager(User manager){
        this.linkedManager = manager;
    }
    
    public User getManager(){
        return linkedManager;
    }
    
    // getter method for usersAvailable
    
    public ArrayList<User> getUsersAvailable(){
        
        EventDAO eventDAO = new EventDAO();
                
        usersAvailable = eventDAO.getUsersAvailableFor(this);
                
        return usersAvailable;
    }
    
    //helper methods for adding and removing available users for event
    
    public void addAvailableUser(User user) {
        
        EventDAO eventDAO = new EventDAO();
        
        eventDAO.addAvailableUser(user, this);
        
    }
    
    public void removeAvailableUser(User user) {
        
        EventDAO eventDAO = new EventDAO();
        
        eventDAO.removeAvailableUser(user, this);
        
    }
    
    
    public void cancelEvent(String cancelReason) {
        
        this.setIsCancelled(true);
        this.setCancelReason(cancelReason);
        this.update();
    }
    
    public void update() {
        
        EventDAO eventDAO = new EventDAO();
        
        eventDAO.update(this);
        
    }
    
    public void delete() {
        
        EventDAO eventDAO = new EventDAO();
        
        eventDAO.delete(this);
        
    }
    
}

