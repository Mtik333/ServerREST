/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.moviedto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Mateusz
 */
@Entity
@Table(name = "RSI_SEAT_RESERVED")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RsiSeatReserved.findAll", query = "SELECT r FROM RsiSeatReserved r")
    , @NamedQuery(name = "RsiSeatReserved.findById", query = "SELECT r FROM RsiSeatReserved r WHERE r.id = :id")
    ,
    @NamedQuery(name = "RsiSeatReserver.findByReservationId", query = "SELECT r from RsiSeatReserved r WHERE r.reservationId = :reservationId")
})
public class RsiSeatReserved implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Integer id;
    @XmlElement
    @JoinColumn(name = "RESERVATION_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RsiReservation reservationId;
    @XmlElement
    @JoinColumn(name = "SCREENING_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RsiScreening screeningId;
    @XmlElement
    @JoinColumn(name = "SEAT_ID", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private RsiSeat seatId;

    public RsiSeatReserved() {
    }

    public RsiSeatReserved(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @XmlTransient
    public RsiReservation getReservationId() {
        return reservationId;
    }

    public void setReservationId(RsiReservation reservationId) {
        this.reservationId = reservationId;
    }
    @XmlTransient
    public RsiScreening getScreeningId() {
        return screeningId;
    }

    public void setScreeningId(RsiScreening screeningId) {
        this.screeningId = screeningId;
    }
    @XmlTransient
    public RsiSeat getSeatId() {
        return seatId;
    }

    public void setSeatId(RsiSeat seatId) {
        this.seatId = seatId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RsiSeatReserved)) {
            return false;
        }
        RsiSeatReserved other = (RsiSeatReserved) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.soapserv.moviedto.RsiSeatReserved[ id=" + id + " ]";
    }
    
}
