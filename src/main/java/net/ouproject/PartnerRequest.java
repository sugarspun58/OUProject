/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

/**
 *
 * @author tonyxp
 */
public class PartnerRequest {
    
    private int partnerRequestID;
    private User requestee;
    private User requester;
    private Event linkedEvent;
    private Boolean accepted;
    
    public PartnerRequest() {
        
        accepted = false;
        
    }
    
    public void setPartnerRequestID(int partnerRequestID) {
        
        this.partnerRequestID = partnerRequestID;
        
    }
    
    public int getPartnerRequestID() {
        
        return partnerRequestID;
        
    }
    
    
    public void setRequestee(User requestee) {
        
        this.requestee = requestee;
    }
    
    public User getRequestee() {
        
        return requestee;
        
    }
    
    public void setRequester(User requester) {
        
        this.requester = requester;
    }
    
    public User getRequester() {
        
        return requester;
        
    }
    
    public void setLinkedEvent(Event linkedEvent) {
        
        this.linkedEvent = linkedEvent;
        
    }
    
    public Event getLinkedEvent() {
        
        return linkedEvent;
        
    }
    
    public void setAccepted(Boolean accepted) {
        
        this.accepted = accepted;
        
    }
    
    public Boolean getAccepted() {
        
        return accepted;
        
    }
    
    public void update() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
        
        partnerRequestDAO.update(this);
        
    }
    
    public void create() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
       
        partnerRequestDAO.create(this);
        
        
    }
    //request is accepted
    public void accept() {
        
        this.setAccepted(true);
        this.update();
        this.rejectAllOthers();
        linkedEvent.removeAvailableUser(requester);
        linkedEvent.removeAvailableUser(requestee);
    }
    
    
    //request is rejected
    
    public void reject() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
        
        partnerRequestDAO.delete(this);
        
        
    }
    
    public void rejectAllOthers() {
        
        PartnerRequestDAO partnerRequestDAO = new PartnerRequestDAO();
        
        partnerRequestDAO.deleteAllOthers(this);
        
    }
    
}
