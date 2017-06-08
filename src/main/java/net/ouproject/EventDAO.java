/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;


import java.sql.Statement;
import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;

import java.util.Calendar;
import java.util.ArrayList;

/**
 *
 * @author tonyxp
 */
public class EventDAO implements DAOInterface<Event, Integer> {
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
    
    @Override
    public Event create(Event event) {
        
        //insert into event table
        sql = "insert into event (EVENT_MANAGER_user_name, EVENT_GROUP, "
                + "start_date_time, event_name, description, duration, "
                + "venue, isCancelled, cancelReason) values "
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        ResultSet generatedKeys;
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, event.getManager().getUserName());
            stmt.setInt(2, event.getLinkedGroup().getGroupID());
            //convert util.Date to sql.Date
            Timestamp sqlDate = new Timestamp((event.getStartDateAndTime().getTime().getTime()));
            stmt.setTimestamp(3, sqlDate);
            stmt.setString(4, event.getEventName());
            stmt.setString(5, event.getDescription());
            stmt.setInt(6, event.getDuration());
            stmt.setString(7, event.getVenue());
            stmt.setBoolean(8, event.getIsCancelled());
            stmt.setString(9, event.getCancelReason());
            
            stmt.executeUpdate();
            
            //code for retreiving auto increment value
            
            generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                
                event.setEventID(generatedKeys.getInt(1));
                
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
        
        return event;
    }
    
    @Override
    public Event update(Event event) {
        
        sql = "update event set EVENT_MANAGER_user_name = ?, EVENT_GROUP = ?, "
                + "start_date_time = ?, event_name = ?, description = ?, "
                + "duration = ?, venue = ?, isCancelled = ?, cancelReason = ? "
                + "where idEVENT = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, event.getManager().getUserName());
            stmt.setInt(2, event.getLinkedGroup().getGroupID());
            //convert util.Date to sql.Date
            Timestamp sqlDate = new Timestamp(event.getStartDateAndTime().getTime().getTime());
            stmt.setTimestamp(3, sqlDate);
            stmt.setString(4, event.getEventName());
            stmt.setString(5, event.getDescription());
            stmt.setInt(6, event.getDuration());
            stmt.setString(7, event.getVenue());
            stmt.setBoolean(8, event.getIsCancelled());
            stmt.setString(9, event.getCancelReason());
            stmt.setInt(10, event.getEventID());
            
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
        return event;
    }
    
    
    
    @Override
    public Event read(Integer eventID) {
        
        Event event = new Event();
        
        sql = "select * from event where idEVENT = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, eventID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                UserDAO userDAO = new UserDAO();
                event.setManager(userDAO.read(resultSet.getString("EVENT_MANAGER_user_name")));
                event.setEventID(resultSet.getInt("idEVENT"));
                GroupDAO groupDAO = new GroupDAO();
                event.setLinkedGroup(groupDAO.read(resultSet.getInt("EVENT_GROUP")));
                
                //set startDateAndTime
                Timestamp sqlDate = resultSet.getTimestamp("start_date_time");
                
                Calendar startDateAndTime = Calendar.getInstance();
                startDateAndTime.setTime(sqlDate);
                event.setStartDateAndTime((startDateAndTime));
                
                event.setEventName(resultSet.getString("event_name"));
                event.setDescription(resultSet.getString("description"));
                event.setDuration(resultSet.getInt("duration"));
                event.setVenue(resultSet.getString("venue"));
                event.setIsCancelled(resultSet.getBoolean("isCancelled"));
                event.setCancelReason(resultSet.getString("cancelReason"));
                
                event.setEventID(eventID);
               
            }
            else {
                event = null;
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
        return event;
    }
  
    @Override
    public void delete(Event event) {
        
        sql = "delete from event where idEVENT = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, event.getEventID());
            
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
    
    public ArrayList<Event> getEventsUserAvailableFor(User user) {
        
        ArrayList<Event> returnArray = new ArrayList();
        
        sql = "select * from available_for where user_name = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                EventDAO eventDAO = new EventDAO();
                Event event = new Event();
                
                event = eventDAO.read(resultSet.getInt("AVAILABLE_FOR_EVENT"));
                
                returnArray.add(event);
                
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
    
    public ArrayList<User> getUsersAvailableFor(Event event) {
        
        ArrayList<User> returnArray = new ArrayList();
        
        sql = "select * from available_for where AVAILABLE_FOR_EVENT = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, event.getEventID());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                UserDAO userDAO = new UserDAO();
                User user = new User();
                
                user = userDAO.read(resultSet.getString("user_name"));
                
                returnArray.add(user);           
              
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
    
    public int getAvailableID(User user, Event event) {
        
        int returnValue = 0;
        
        sql = "select idAVAILABLE_FOR from available_for where user_name = ? "
                + "and AVAILABLE_FOR_EVENT = ?";
                
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, event.getEventID());
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next()) {
                returnValue = resultSet.getInt("idAVAILABLE_FOR");
            } else {
                returnValue = 0;
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
    
    public void addAvailableUser(User user, Event event) {
        
        sql = "insert into available_for (user_name, AVAILABLE_FOR_EVENT) "
                + "values (?, ?)";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, event.getEventID());          
            
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
    
    public void removeAvailableUser(User user, Event event) {
        
        sql = "delete from available_for where user_name = ? "
                + "and AVAILABLE_FOR_EVENT = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, event.getEventID());
            
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
    
    public ArrayList<Event> getLinkedEvents(Group group) {
        
        ArrayList<Event> returnArray = new ArrayList();
        
        sql = "select * from event where EVENT_GROUP = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, group.getGroupID());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                int eventID;
                EventDAO eventDAO = new EventDAO();
                Event event = new Event();
                
                eventID = resultSet.getInt("idEVENT");
                event = eventDAO.read(eventID);
                returnArray.add(event);
                
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
}
