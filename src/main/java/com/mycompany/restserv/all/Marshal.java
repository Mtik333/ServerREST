/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.all;

import com.mycompany.restserv.moviedto.RsiReservation;
import com.mycompany.restserv.moviedto.RsiSeat;

/**
 *
 * @author Mateusz
 */

public class Marshal {
    private RsiReservation rsiReservation;
    private RsiSeat rsiSeat;

    public RsiReservation getRsiReservation() {
        return rsiReservation;
    }

    public void setRsiReservation(RsiReservation rsiReservation) {
        this.rsiReservation = rsiReservation;
    }

    public RsiSeat getRsiSeat() {
        return rsiSeat;
    }

    public void setRsiSeat(RsiSeat rsiSeat) {
        this.rsiSeat = rsiSeat;
    }
    
}
