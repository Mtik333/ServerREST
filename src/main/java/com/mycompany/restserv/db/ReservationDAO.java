/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.db;

import com.mycompany.restserv.moviedto.RsiReservation;
import java.util.List;

/**
 *
 * @author Mateusz
 */
public interface ReservationDAO extends GenericDao<RsiReservation, Integer>{
    
    List findAll();
}
