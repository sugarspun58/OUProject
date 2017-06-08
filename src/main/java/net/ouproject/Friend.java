/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

/**
 *
 * @author tonyxp
 */
public class Friend extends User {
    
    // properties
    
    private String partnerType;
    
    // constructor
    
    public Friend(String userName, String firstName, String surname,
            char sex, String email, String location, String country,
            String partnerType){
        
        super(userName, firstName, surname, sex, email, location, country);
        this.partnerType = partnerType;
        
    }
    
    
    // getter and setter methods
    
    public void setPartnerType(String partnerType){
        this.partnerType = partnerType;
    }
    
    public String getPartnerType(){
        return partnerType;
    }
    
    
}
