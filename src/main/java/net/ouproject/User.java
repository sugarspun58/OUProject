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
public class User {
    
    //properties
    
    private String userName;
    private String firstName;
    private String surname;
    private char sex;
    private String email;
    private String location;
    private String country;
        
    // linked Login
    
    private Login linkedLogin;
    
    //ArrayList hasFriend Type Friend
    
    private ArrayList<Friend> hasFriends;
    
    //ArrayList of group memberships
    
    private ArrayList<Group> memberOf;
    
    //availableFor events
    
    private ArrayList<Event> availableForEvents;
    
    //ArrayList of groups managed
    
    private ArrayList<Group> managerOf;
    
    //pending parnter requests
    
    private ArrayList<PartnerRequest> pendingPartnerRequests;
    
    //partnet requests made
    
    private ArrayList<PartnerRequest> madePartnerRequests;
    
    //constructors
    
    public User(){
        //default constructor
    }
    
    
    public User(String userName){
        
        this.userName = userName;
    }
    
    public User(String userName, String firstName, String surname,
            char sex, String email){
        this.userName = userName;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.email = email;
    }
    
    public User(String userName, String firstName, String surname,
            char sex, String email, String location, String country){
        this.userName = userName;
        this.firstName = firstName;
        this.surname = surname;
        this.sex = sex;
        this.email = email;
        this.location = location;
        this.country = country;
    }
    
    // getter setter methods
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }
    
    public String getFirstName(){
        return firstName;
    }
    
    public void setSurname(String surname){
        this.surname = surname;
    }
    
    public String getSurname(){
        return surname;
    }
    
    public void setSex(char sex){
        this.sex = sex;
    }
    
    public char getSex(){
        return sex;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setLocation(String location){
        this.location = location;
    }
    
    public String getLocation(){
        return location;
    }
    
    public void setCountry(String country){
        this.country = country;
    }
    
    public String getCountry(){
        return country;
    }
    
    public void setLinkedLogin(Login login) {
        this.linkedLogin = login;
    }
    
    public Login getLinkedLogin() {
        return linkedLogin;
    }
    
    // getter method for hasFriends
    
    public ArrayList<Friend> getFriends(){
        return hasFriends;
    }
    
    // getter method for member of Groups
    
    public ArrayList<Group> getMemberOf(){
        
        GroupDAO groupDAO = new GroupDAO();
        
        memberOf = groupDAO.getMemberOf(this);
               
        return memberOf;
    }
    
    // getter method for availableForEvents
    
    public ArrayList<Event> getAvailableForEvents(){
        
        EventDAO eventDAO = new EventDAO();
        
        availableForEvents = eventDAO.getEventsUserAvailableFor(this);
        
        return availableForEvents;
    }
    
    // getter method for managerOf
    
    public ArrayList<Group> getManagerOf() {
        
        GroupDAO groupDAO = new GroupDAO();
        
        managerOf = groupDAO.getManagedGroups(this);
        
        return managerOf;
    }
    
    //getter method for getting pending requests
    
    public ArrayList<PartnerRequest> getPendingPartnerRequests() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
        
        pendingPartnerRequests = partnerRequestDAO.pendingRequests(this);
        
        return pendingPartnerRequests;
        
        
    }
    
    public ArrayList<PartnerRequest> getMadePartnerRequests() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
        
        madePartnerRequests = partnerRequestDAO.madeRequests(this);
        
        return madePartnerRequests;
        
        
    }
    
    public void update() {
        // update database via UserDAO
        
        UserDAO userDAO = new UserDAO();
        
        userDAO.update(this);
        
    }
    
    public void delete() {
        
        UserDAO userDAO = new UserDAO();
        
        userDAO.delete(this);
        
    }
    
}
