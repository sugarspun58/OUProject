/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.ouproject;

/**
 *
 * @author tonyxp
 */

public interface DAOInterface <T, PK> {
    // CRUD opertaions
    
    public T create(T o);
    public T update(T o);
    public T read(PK pk);
    public void delete(T o);
    
}
