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

import java.util.ArrayList;
/**
 *
 * @author tonyxp
 */
public class PartnerRequestDAO implements DAOInterface <PartnerRequest, Integer> {
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
 
    @Override
    public PartnerRequest create(PartnerRequest partnerRequest) {
        
        sql = "insert into partner_request (REQUESTER_USER_NAME, REQUESTEE_USER_NAME, "
                + "PARTNER_REQUEST_EVENT, accepted) "
                + "values (?, ?, ?, ?)";
        
        ResultSet generatedKeys;
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, partnerRequest.getRequester().getUserName());
            stmt.setString(2, partnerRequest.getRequestee().getUserName());
            stmt.setInt(3, partnerRequest.getLinkedEvent().getEventID());
            stmt.setBoolean(4, partnerRequest.getAccepted());
            
            stmt.executeUpdate();
            
            generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                
                partnerRequest.setPartnerRequestID(generatedKeys.getInt(1));
                
            }
            
            generatedKeys.close();
            stmt.close();
            connection.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        return partnerRequest;
    }
    
    @Override
    public PartnerRequest update(PartnerRequest partnerRequest) {
        
        sql = "update partner_request set REQUESTER_USER_NAME = ?, "
                + "REQUESTEE_USER_NAME = ?, "
                + "PARTNER_REQUEST_EVENT = ?, "
                + "accepted = ? "
                + "where idPARTNER_REQUEST = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, partnerRequest.getRequester().getUserName());
            stmt.setString(2, partnerRequest.getRequestee().getUserName());
            stmt.setInt(3, partnerRequest.getLinkedEvent().getEventID());
            stmt.setBoolean(4, partnerRequest.getAccepted());
            
            stmt.setInt(5, partnerRequest.getPartnerRequestID());
            
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
        return partnerRequest;
    }
    
    @Override
    public PartnerRequest read(Integer partnerRequestID) {
        
        PartnerRequest partnerRequest = new PartnerRequest();
        
        sql = "select * from partner_request where idPARTNER_REQUEST = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, partnerRequestID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                User requester;
                User requestee;
                Event linkedEvent;
                UserDAO userDAO = new UserDAO();
                EventDAO eventDAO = new EventDAO();
                
                requester = userDAO.read(resultSet.getString("REQUESTER_USER_NAME"));
                partnerRequest.setRequester(requester);
                
                requestee = userDAO.read(resultSet.getString("REQUESTEE_USER_NAME"));
                partnerRequest.setRequestee(requestee);
                
                linkedEvent = eventDAO.read(resultSet.getInt("PARTNER_REQUEST_EVENT"));
                partnerRequest.setLinkedEvent(linkedEvent);
                
                partnerRequest.setAccepted(resultSet.getBoolean("accepted"));
                
                partnerRequest.setPartnerRequestID(resultSet.getInt("idPARTNER_REQUEST"));
                
            }
            else {
                partnerRequest = null;
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
        
        return partnerRequest;
    }
    
    @Override
    public void delete(PartnerRequest partnerRequest) {
        
        sql = "delete from partner_request where idPARTNER_REQUEST = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, partnerRequest.getPartnerRequestID());
            
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
    }
    
    //helper routine to get request user has made
    
    public ArrayList<PartnerRequest> madeRequests(User user) {
        
        ArrayList<PartnerRequest> returnArray = new ArrayList();
        
        sql = "select * from partner_request where REQUESTER_USER_NAME = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                PartnerRequest partnerRequest;
                partnerRequest = this.read(resultSet.getInt("idPARTNER_REQUEST"));
                returnArray.add(partnerRequest);
                
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
        return returnArray;
        
    }
    
    //helper routine to get pending requests
        
    public ArrayList<PartnerRequest> pendingRequests(User user) {
        
        ArrayList<PartnerRequest> returnArray = new ArrayList();
        
        sql = "select * from partner_request where REQUESTEE_USER_NAME = ?";
               
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                PartnerRequest partnerRequest;
                partnerRequest = this.read(resultSet.getInt("idPARTNER_REQUEST"));
                returnArray.add(partnerRequest);
                
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
        
        return returnArray;
    }
    
    public void deleteAllOthers(PartnerRequest partnerRequest) {
        
        
        //Delete all partner request for EVENT for requestee
        sql = "delete from partner_request where REQUESTER_USER_NAME = ? and "
                + "PARTNER_REQUEST_EVENT = ? and accepted = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, partnerRequest.getRequestee().getUserName());
            stmt.setInt(2, partnerRequest.getLinkedEvent().getEventID());
            stmt.setBoolean(3, false);
            
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
        //Reject all partner request for EVENT for requester
        sql = "delete from partner_request where REQUESTER_USER_NAME = ? and "
                + "PARTNER_REQUEST_EVENT = ? and accepted = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, partnerRequest.getRequester().getUserName());
            stmt.setInt(2, partnerRequest.getLinkedEvent().getEventID());
            stmt.setBoolean(3, false);
            
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
        
    }
    
    
    public Boolean doesRequestExist(String requesterUserName, 
            String requesteeUserName, int eventID) {
        
        Boolean returnValue = false; 
        
        sql = "select * from partner_request where REQUESTER_USER_NAME = ? and "
                + "REQUESTEE_USER_NAME = ? and PARTNER_REQUEST_EVENT = ?"; 
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, requesterUserName);
            stmt.setString(2, requesteeUserName);
            stmt.setInt(3, eventID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                
                returnValue = true;
                
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
        return returnValue;
    }
    
}