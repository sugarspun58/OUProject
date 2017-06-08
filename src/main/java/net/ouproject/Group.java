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
public class Group {
    
    //properties
    
    private String groupName;
    private boolean isVirtual;
    private String description;
    private int groupID;
    
    //group type
    private String type;
    
    //members of group
    
    private ArrayList<User> members;
    
    //Manager of group
    
    private User manager;
    
    // linked Events
    
    private ArrayList<Event> events;
    
    //constructor
    
    public Group() {
        
    }
    
    public Group(String groupName, boolean isVirtual, String description){
        
        this.groupName = groupName;
        this.isVirtual = isVirtual;
        this.description = description;
    }
    
    // getter and setter methods
    
    public void setGroupName(String groupName){
        this.groupName = groupName;
    }
    
    public String getGroupName(){
        return groupName;
    }
    
    public void setIsVirtual(boolean isVirtual){
        this.isVirtual = isVirtual;
    }
    
    public boolean getIsVirtual(){
        return isVirtual;
    }
    
    public void setDescription(String description){
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    public int getGroupID() {
        return groupID;
    }
    
    public void setGroupID(int groupID){
        this.groupID = groupID;
    }
    
    public String getType() {
        return type;
    }
    
    public void setType(String type) {
        this.type = type;
    }
    
    
    // get set manager methods
    
    public void setManager(User manager){
        this.manager = manager;
    }
    
    public User getManager(){
        return manager;
    }
       
    // get members method
    
    public ArrayList<User> getMembers(){
        
        GroupDAO groupDAO = new GroupDAO();
        
        members = groupDAO.getMemberships(this);
        
        return members;
    }
    
    // get events method
    
    public ArrayList<Event> getEvents(){
        
        EventDAO eventDAO = new EventDAO();
        
        events = eventDAO.getLinkedEvents(this);
        
        return events;
    }
    
    public User joinGroup(User user) {
        
        GroupDAO groupDAO = new GroupDAO();
        
        user = groupDAO.addMember(user, this);
        
        return user;
        
    }
    
    public void leaveGroup(User user) {
        
        GroupDAO groupDAO = new GroupDAO();
        
        groupDAO.removeMember(user, this);
        
    }
    
    
    
    // helper to check if user is a member of group
    public Boolean isMember(User user) {
        
        GroupDAO groupDAO = new GroupDAO();
        
        return groupDAO.isMemberOf(user, this);
    }
    
    
}
