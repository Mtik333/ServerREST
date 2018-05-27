/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverrest;

import com.mycompany.restserv.moviedto.RsiAuditorium;
import com.mycompany.restserv.moviedto.RsiClient;
import com.mycompany.restserv.moviedto.RsiMovie;
import com.mycompany.restserv.moviedto.RsiReservation;
import com.mycompany.restserv.moviedto.RsiScreening;
import com.mycompany.restserv.moviedto.RsiSeat;
import com.mycompany.restserv.moviedto.RsiSeatReserved;
import java.awt.Image;
import java.util.List;

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
    
    Boolean authenticateClient() throws Exception;
    
    Image downloadImage(String name);
    
    void createReservation(RsiReservation reservationId, RsiSeat rsiSeat);
    
    byte[] pdfReservation(RsiReservation reservation);
    
    void removeReservation(RsiReservation reservationId);
    
    void changeReservation(RsiReservation reservation, RsiSeat rsiSeat);
}
