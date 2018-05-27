/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.db;

public interface GenericDao<T, K> {

    T save(T t);

    void delete(T t);

    void update(T t);

    T findById(K id);
}
