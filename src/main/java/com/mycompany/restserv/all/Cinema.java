/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.all;

import com.mycompany.restserv.moviedto.RsiAuditorium;
import com.mycompany.restserv.moviedto.RsiClient;
import com.mycompany.restserv.moviedto.RsiMovie;
import com.mycompany.restserv.moviedto.RsiReservation;
import com.mycompany.restserv.moviedto.RsiScreening;
import com.mycompany.restserv.moviedto.RsiSeat;
import com.mycompany.restserv.moviedto.RsiSeatReserved;
import java.awt.Image;
import java.util.List;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mateusz
 */

public interface Cinema {
        
    List<RsiAuditorium> getAuditoriums();
    
    List<RsiClient> getClients();
    
    List<RsiMovie> getMovies();
    
    List<RsiReservation> getReservations();
    
    List<RsiScreening> getScreenings();
    
    List<RsiSeat> getSeats();
    
    List<RsiSeatReserved> getReservedSeats();
    
    Response authenticateClient() throws Exception;
    
    Image downloadImage(String name);
    
    void createReservation(Marshal marshal);
    
    byte[] pdfReservation(RsiReservation reservation);
    
    void removeReservation(@PathParam("id") Integer reservationId);
    
    void changeReservation(Marshal marshal);
    
//    void changeReservation(RsiReservation reservation);
//    
//    void changeSeat(RsiSeat rsiSeat, @PathParam("id") Integer reservation);
}
