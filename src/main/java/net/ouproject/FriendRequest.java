/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;



/**
 *
 * @author tonyxp
 */
public class FriendRequest {
    
    // properties
    
    private User requester;
    private User requestee;
    
    //Constructor
    
    public FriendRequest(User requester, User requestee){
        
        this.requester = requester;
        this.requestee = requestee;
        
    }
    
    // getter methods
    
    public User getRequester(){
        return requester;
    }
    
    public User getRequestee(){
        return requestee;
    }
    
    
    
}
