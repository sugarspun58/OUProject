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
public class GroupDAO implements DAOInterface<Group, Integer>{
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
    
    public Group create(Group group) {
        
        String type = "";
        
        if (group.getClass() == Team.class) {
            type = "team";
        }
        else if (group.getClass() == Club.class) {
            type = "club";
        }
        
        
        //insert into group table
        sql = "insert into group_table (group_name, description, is_virtual, type) "
                + "values (?, ?, ?, ?)";
        
        ResultSet generatedKeys;
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, group.getGroupName());
            stmt.setString(2, group.getDescription());
            stmt.setBoolean(3, group.getIsVirtual());
            stmt.setString(4, type);
            
            stmt.executeUpdate();
            
            // code for retreiving auto increment value
            generatedKeys = stmt.getGeneratedKeys();
            
            if (generatedKeys.next()) {
                
                group.setGroupID(generatedKeys.getInt(1));
                
            }
            
            generatedKeys.close();
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e){
            e.printStackTrace();
        }
        
        //insert manager (User) to manager table
        
        sql = "insert into manager (user_name, MANAGER_GROUP) "
                + "values (?, ?)";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, group.getManager().getUserName());
            stmt.setInt(2, group.getGroupID());
            
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
        
        return group;
        
    }
    
    @Override
    public Group update(Group group) {
        
        //update group table
        sql = "update group_table set group_name = ?, description = ?, is_virtual = ? "
                + "where idGROUP = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, group.getGroupName());
            stmt.setString(2, group.getDescription());
            stmt.setBoolean(3, group.getIsVirtual());
            
            stmt.setInt(4, group.getGroupID());
            
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
        
        //update manager (User) to manager table
        
        sql = "update manager set user_name = ? where MANAGER_GROUP = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, group.getManager().getUserName());
            stmt.setInt(2, group.getGroupID());
            
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
        
        return group;
    }
    
    @Override   
    public Group read(Integer groupID){
        
        //get data from group table
        Group group = new Group();
        
        sql = "select group_name, description, is_virtual, type from group_table where idGROUP = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                group.setGroupName(resultSet.getString("group_name"));
                group.setDescription(resultSet.getString("description"));
                group.setIsVirtual(resultSet.getBoolean("is_virtual"));
                group.setType(resultSet.getString("type"));
                
                
                group.setGroupID(groupID);
                
                
                
            }    
            else {
                group = null;
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
        
        // load manager (User)
        
        sql = "select user_name from manager where MANAGER_GROUP = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, groupID);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                User manager = new User();
                UserDAO userDAO = new UserDAO();
                
                manager = userDAO.read(resultSet.getString("user_name"));
                group.setManager(manager);
                
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
        
        return group;
        
    }
    
    @Override
    public void delete(Group group) {
        //delete from group table
        sql = "delete from group_table where idGROUP = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, group.getGroupID());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch (NamingException e) {
            e.printStackTrace();
        }
        
        //delete manager table entry
        
        sql = "delete from manager where MANAGER_GROUP = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, group.getGroupID());
            
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
    
    public ArrayList<Group> getManagedGroups(User user) {
        
        //helper routine to load all groups managed by a user
        
        ArrayList<Group> returnArray = new ArrayList(); 
        
        sql = "select MANAGER_GROUP from manager where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
                      
            while (resultSet.next()) {
                
                int groupID;
                Group group = new Group();
                               
                groupID = resultSet.getInt("MANAGER_GROUP");
                group = read(groupID);
                returnArray.add(group);
                
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
    
    public Boolean isMemberOf(User user, Group group) {
        
        //helper to check is user is a memeber of group
        Boolean returnValue = false;
        
        sql = "select * from member_of where MEMBER_OF_GROUP = ? "
                + "and user_name = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, group.getGroupID());
            stmt.setString(2, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                returnValue = true;
            }
            
            resultSet.close();
            stmt.close();
            connection.close();
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch (NamingException e){
            e.printStackTrace();
        }
        
        return returnValue;
    }
    
    
    public ArrayList<User> getMemberships(Group group) {
        //helper routine to get all users who are a memeber of group
        
        ArrayList<User> returnArray = new ArrayList();
        
        sql = "select user_name from member_of where MEMBER_OF_GROUP = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setInt(1, group.getGroupID());
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                String userName;
                UserDAO userDAO = new UserDAO();
                User user = new User();
                
                userName = resultSet.getString("user_name");                
                user = userDAO.read(userName);
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
    
    
    
    public ArrayList<Group> getMemberOf(User user) {
        //helper routine to get all groups user is a memeber of
        
        ArrayList<Group> returnArray = new ArrayList();
        
        sql = "select MEMBER_OF_GROUP from member_of where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            ResultSet resultSet = stmt.executeQuery();
                      
            while (resultSet.next()) {
                
                int groupID;
                Group group = new Group();
                               
                groupID = resultSet.getInt("MEMBER_OF_GROUP");
                group = read(groupID);
                returnArray.add(group);
                
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
    
    
    public ArrayList<Group> searchOnGroupName(String searchString) {
        
        ArrayList<Group> returnArray = new ArrayList();
        
        sql = "select idGROUP from group_table where group_name like ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, "%" + searchString + "%");
            
            ResultSet resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                int groupID;
                Group group = new Group();
                
                groupID = resultSet.getInt("idGROUP");
                group = read(groupID);
                returnArray.add(group);
                
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
    
    public User addMember(User user, Group group) {
        
        //check user is not already member of the group
        
        Boolean alreadyMember = false;
        
        sql = "select * from member_of where user_name = ? and "
                + "MEMBER_OF_GROUP = ?";
        
        ResultSet resultSet;
                
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
                        
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, group.getGroupID());
            
            resultSet = stmt.executeQuery();
            
            while (resultSet.next()) {
                
                // if result is found set 
                alreadyMember = true;
                
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
        
        // if alreadyMember == false then create membership
        
        if (alreadyMember == false) {
            
            try {
                
                sql = "insert into member_of (user_name, MEMBER_OF_GROUP) "
                        + "values (?, ?)";
                
                InitialContext initialContext = new InitialContext();
            
                dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
                        
                Connection connection = dataSource.getConnection();
                stmt = connection.prepareStatement(sql);
                stmt.setString(1, user.getUserName());
                stmt.setInt(2, group.getGroupID());
                
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
        
        return user;
    }
    
    public void removeMember(User user, Group group) {
        //remove entry from member_of table
        
        sql = "delete from member_of where user_name = ? and "
                + "MEMBER_OF_GROUP = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            stmt.setInt(2, group.getGroupID());
            
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
     
}
