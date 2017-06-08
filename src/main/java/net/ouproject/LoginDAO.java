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
public class LoginDAO implements DAOInterface<Login, String>{
    
    
    private static final String JNDI_LOOKUP = "jdbc/__4spadesnet";    
    private DataSource dataSource;
    
    private String sql;
    
    private PreparedStatement stmt;
        
    @Override
    public Login create(Login login){
        sql = "insert into login (user_name, password) values (?, ?)";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login.getUserName());
            stmt.setString(2, login.getPassword());
            
            stmt.executeUpdate();
            
            stmt.close();
            connection.close();
        }
        catch (SQLException e){
            //add error handling feedback
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
        
        return login;
    }
    
    @Override
    public Login update(Login login){
        
        sql = "update login set password = ? where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();

            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login.getPassword());
            stmt.setString(2, login.getUserName());
            
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
        return login;
    }
    
    @Override
    public Login read(String username){
        
        Login login = new Login();
        
        sql = "select user_name, password from login where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            
            ResultSet resultSet = stmt.executeQuery();
            
            if (resultSet.next() != false) {
            
                login.setUserName(resultSet.getString("user_name"));
                login.setPassword(resultSet.getString("password"));
            
                
            }
            else {
                login = null;                
            }
            resultSet.close();
            stmt.close();
            connection.close();
        }
        
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(NamingException e){
            e.printStackTrace();
        }
        
        return login;
    }
    
    @Override
    public void delete(Login login){
        
        sql = "delete from login where user_name = ?";
        
        try {
            
            InitialContext initialContext = new InitialContext();
            
            dataSource = (DataSource) initialContext.lookup(JNDI_LOOKUP);
            
            Connection connection = dataSource.getConnection();
            
            stmt = connection.prepareStatement(sql);
            stmt.setString(1, login.getUserName());
            
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
    }
    
    public boolean checkAvailability(String username) {
        
        Login checkUser;
        
        boolean returnVal;
        
        checkUser = this.read(username);
        
        if (checkUser == null) {
            returnVal = true;
        }
        else {
            returnVal = false;
        }
        
        return returnVal;
  
    }
    
    public Login authenticate(String username, String password) {
        
        Login login = this.read(username);
                
        if (login != null) {
        
            if (password.equals(login.getPassword())) {
                return login;
            }
            else {
                return null;
            }
        }
        else {
            return null;
        }
            
    }
    
}
