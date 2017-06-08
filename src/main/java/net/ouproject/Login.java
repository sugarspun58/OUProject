/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

/**
 *
 * @author tonyxp
 */
public class Login {
    
    // properties
    
    private String userName;
    private String password;
    
    //linkedUser Type User
    
    private User linkedUser;
        
    //constructor
    
    public Login(){
        //default constructor
    }
    
    
    public Login(String userName, String password){
        this.userName = userName;
        this.password = password;        
    }
    
    // getter setter methods
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserName(String userName){
        this.userName = userName;
    }
    
    public String getPassword(){
        return password;
    }
    
    public void setPassword(String password){
        this.password = password;
    }
    
    public User getLinkedUser() {
        return linkedUser;
    }
    
    public void setLinkedUser(User user) {
        this.linkedUser = user;
    }
    
    // loads the linked user object
    
    public User loadLinkedUser() {
        
        UserDAO userDAO = new UserDAO();
        User user = new User();
        
        // get the user from DB
        user = userDAO.read(this.getUserName());
        
        // link the user and login objects
        
        this.setLinkedUser(user);
        user.setLinkedLogin(this);
        
        // return the user object
        
        return user;
        
    }
    
    //delete login object
    
    public void delete() {
        
        LoginDAO loginDAO = new LoginDAO();
        
        loginDAO.delete(this);
        
    }
    
    
}
