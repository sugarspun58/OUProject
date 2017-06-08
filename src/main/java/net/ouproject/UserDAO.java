/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
/**
 *
 * @author tonyxp
 */
public class UserDAO implements DAOInterface<User, String> {
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
    
    @Override
    public User create(User user){
        
        sql = "insert into user (user_name, sex, first_name, surname,"
                + " email, location, country)"
                + " values (?, ?, ?, ?, ?, ?, ?)";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());            
            stmt.setString(2, String.valueOf(user.getSex()));
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getSurname());
            stmt.setString(5, user.getEmail());
            stmt.setString(6, user.getLocation());
            stmt.setString(7, user.getCountry());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
        }            
        catch (SQLException e) {
            //add error handling feedback
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
        return user;
    }
    
    @Override
    public User update(User user){
        
        sql = "update user set sex = ?, first_name = ?, surname = ?, "
                + "email = ?, location = ?, country = ? where user_name = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, String.valueOf(user.getSex()));
            stmt.setString(2, user.getFirstName());
            stmt.setString(3, user.getSurname());
            stmt.setString(4, user.getEmail());
            stmt.setString(5, user.getLocation());
            stmt.setString(6, user.getCountry());
            
            stmt.setString(7, user.getUserName());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
                       
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
        return user;
    }
    
    
    @Override
    public User read(String username){
        
        User user = new User();
        
        sql = "select user_name, sex, first_name, surname, email, location,"
                + " country from user where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
                
                user.setUserName(resultSet.getString("user_name"));
                user.setSex(resultSet.getString("sex").charAt(0));
                user.setFirstName(resultSet.getString("first_name"));
                user.setSurname(resultSet.getString("surname"));
                user.setEmail(resultSet.getString("email"));
                user.setLocation(resultSet.getString("location"));
                user.setCountry(resultSet.getString("country"));
                
                resultSet.close();
                stmt.close();
                connection.close();
            }
            else {
                user = null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
        return user;
        
    }
    
    @Override
    public void delete(User user){
        
        sql = "delete from user where user_name = ?";
        
        try {
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, user.getUserName());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
            
            
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
    }
    
}
