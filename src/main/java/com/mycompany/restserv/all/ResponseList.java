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
import java.util.List;
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
public class ResponseList {
    @XmlElement(name = "auditoriums")
    private List<RsiAuditorium> auditoriums;
    @XmlElement(name = "clients")
    public List<RsiClient> clients;
    @XmlElement(name = "movies")
    public List<RsiMovie> movies;
    @XmlElement(name = "reservations")
    public List<RsiReservation> reservations;
    @XmlElement(name = "screenings")
    public List<RsiScreening> screenings;
    @XmlElement(name = "seats")
    public List<RsiSeat> seats;
    @XmlElement(name = "reservedseats")
    public List<RsiSeatReserved> seatsreserved;

    public List<RsiAuditorium> getAuditoriums() {
        return auditoriums;
    }

    public void setAuditoriums(List<RsiAuditorium> auditoriums) {
        this.auditoriums = auditoriums;
    }

    public List<RsiClient> getClients() {
        return clients;
    }

    public void setClients(List<RsiClient> clients) {
        this.clients = clients;
    }

    public List<RsiMovie> getMovies() {
        return movies;
    }

    public void setMovies(List<RsiMovie> movies) {
        this.movies = movies;
    }

    public List<RsiReservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<RsiReservation> reservations) {
        this.reservations = reservations;
    }

    public List<RsiScreening> getScreenings() {
        return screenings;
    }

    public void setScreenings(List<RsiScreening> screenings) {
        this.screenings = screenings;
    }

    public List<RsiSeat> getSeats() {
        return seats;
    }

    public void setSeats(List<RsiSeat> seats) {
        this.seats = seats;
    }

    public List<RsiSeatReserved> getSeatsreserved() {
        return seatsreserved;
    }

    public void setSeatsreserved(List<RsiSeatReserved> seatsreserved) {
        this.seatsreserved = seatsreserved;
    }
    
    
    public List<RsiAuditorium> getMessages() {
        return auditoriums;
    }

    public void setMessages(List<RsiAuditorium> messages) {
        this.auditoriums = messages;
    }
    
    
}
