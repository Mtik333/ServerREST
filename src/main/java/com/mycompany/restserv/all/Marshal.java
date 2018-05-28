/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.all;

import com.mycompany.restserv.moviedto.RsiReservation;
import com.mycompany.restserv.moviedto.RsiSeat;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Mateusz
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.NONE)
public class Marshal {
    @XmlElement(name = "reservation")
    private RsiReservation rsiReservation;
    @XmlElement(name = "seat")
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
