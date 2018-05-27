/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.serverrest;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.mycompany.restserv.db.AuditoriumDAO;
import com.mycompany.restserv.db.ClientDAO;
import com.mycompany.restserv.db.JpaAuditoriumDAO;
import com.mycompany.restserv.db.JpaClientDAO;
import com.mycompany.restserv.db.JpaMovieDAO;
import com.mycompany.restserv.db.JpaReservationDAO;
import com.mycompany.restserv.db.JpaScreeningDAO;
import com.mycompany.restserv.db.JpaSeatDAO;
import com.mycompany.restserv.db.JpaSeatReservedDAO;
import com.mycompany.restserv.db.MovieDAO;
import com.mycompany.restserv.db.ReservationDAO;
import com.mycompany.restserv.db.ScreeningDAO;
import com.mycompany.restserv.db.SeatDAO;
import com.mycompany.restserv.db.SeatReservedDAO;
import com.mycompany.restserv.moviedto.RsiAuditorium;
import com.mycompany.restserv.moviedto.RsiClient;
import com.mycompany.restserv.moviedto.RsiMovie;
import com.mycompany.restserv.moviedto.RsiReservation;
import com.mycompany.restserv.moviedto.RsiScreening;
import com.mycompany.restserv.moviedto.RsiSeat;
import com.mycompany.restserv.moviedto.RsiSeatReserved;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mateusz
 */
@Path("cinema")
public class CinemaImpl implements Cinema{

    private MovieDAO movieDAO;
    private AuditoriumDAO auditoriumDAO;
    private ClientDAO clientDao;
    private ReservationDAO reservationDao;
    private ScreeningDAO screeningDao;
    private SeatDAO seatDao;
    private SeatReservedDAO seatRDao;
    
    @GET
    @Path("auditoriums")
    @Override
    public List<RsiAuditorium> getAuditoriums() {
        this.auditoriumDAO = new JpaAuditoriumDAO();
        return auditoriumDAO.findAll();
    }

    @Override
    public List<RsiClient> getClients() {
        this.clientDao = new JpaClientDAO();
        return clientDao.findAll();
    }

    @Override
    public List<RsiMovie> getMovies() {
        this.movieDAO = new JpaMovieDAO();
        return movieDAO.findAllMovies();
    }

    @Override
    public List<RsiReservation> getReservations() {
        this.reservationDao = new JpaReservationDAO();
        return reservationDao.findAll();
    }

    @Override
    public List<RsiScreening> getScreenings() {
        this.screeningDao = new JpaScreeningDAO();
        return screeningDao.findAll();
    }

    @Override
    public List<RsiSeat> getSeats() {
        this.seatDao = new JpaSeatDAO();
        return seatDao.findAll();
    }

    @Override
    public List<RsiSeatReserved> getReservedSeats() {
        this.seatRDao = new JpaSeatReservedDAO();
        return seatRDao.findAll();
    }

    @Override
    public Boolean authenticateClient() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Image downloadImage(String name) {
        try {
            File f = new File(CinemaImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println(f.getAbsolutePath() + ";\n" + f.getCanonicalPath() + "+\n" + f.getPath());
            File test = new File("D:" + File.separator + "posters" + File.separator + name);
            System.out.println(test.getPath());
            System.out.println(test.getAbsolutePath());
            return ImageIO.read(test.getAbsoluteFile());
        } catch (IOException e) {
            e.printStackTrace();
            return null;

        }
    }

    @Override
    public void createReservation(RsiReservation reservationId, RsiSeat rsiSeat) {
        this.reservationDao = new JpaReservationDAO();
        reservationId = reservationDao.save(reservationId);
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved rsiSeatReserved = new RsiSeatReserved();
        rsiSeatReserved.setReservationId(reservationId);
        rsiSeatReserved.setScreeningId(reservationId.getScreeningId());
        rsiSeatReserved.setSeatId(rsiSeat);
        seatRDao.save(rsiSeatReserved);
    }

    @Override
    public byte[] pdfReservation(RsiReservation reservation) {
        try {
            File file = new File("itext-test.pdf");
            FileOutputStream fileout = new FileOutputStream(file);
            Document document = new Document();
            PdfWriter.getInstance(document, fileout);
            document.addAuthor("RSI Movie");
            document.addTitle("Reservation no. "+reservation.getId());
            document.open();
            this.seatRDao = new JpaSeatReservedDAO();
            RsiSeatReserved seatReserved = seatRDao.findByReservationId(reservation);
            System.out.println(seatReserved.getSeatId());
            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
            Chunk chunk = new Chunk("Reservation", font);
            Chunk chunk2 = new Chunk("Client: "+reservation.getClientReserverId().getUsername());
            Chunk chunk3 = new Chunk("Movie: "+reservation.getScreeningId().getMovieId().getTitle());
            Chunk chunk4 = new Chunk("Auditorium: "+seatReserved.getScreeningId().getAuditoriumId().getName());
            Chunk chunk5 = new Chunk("Screening time: "+reservation.getScreeningId().getScreeningStart().toString());
            Chunk chunk6 = new Chunk("Seat: "+String.valueOf((seatReserved.getSeatId().getSeatNumber() - 1) * 5 + seatReserved.getSeatId().getSeatRow()));
            try {
                document.add(chunk);
                Paragraph preface = new Paragraph();
                preface.add(new Paragraph(" "));
                document.add(preface);
                document.add(chunk2);
                document.add(preface);
                document.add(chunk3);
                document.add(preface);
                document.add(chunk4);
                document.add(preface);
                document.add(chunk5);
                document.add(preface);
                document.add(chunk6);
            } catch (DocumentException ex) {
                Logger.getLogger(CinemaImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            document.close();
            byte[] data = Files.readAllBytes(Paths.get(file.getAbsolutePath()));
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(CinemaImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public void removeReservation(RsiReservation reservationId) {
        this.reservationDao = new JpaReservationDAO();
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved seatReserved = seatRDao.findByReservationId(reservationId);
        seatRDao.delete(seatReserved);
        reservationDao.delete(reservationId);
    }

    @Override
    public void changeReservation(RsiReservation reservation, RsiSeat rsiSeat) {
        this.reservationDao = new JpaReservationDAO();
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved seatReserved = seatRDao.findByReservationId(reservation);
        seatReserved.setSeatId(rsiSeat);
        seatRDao.update(seatReserved);
    }
    @GET
    @Path("/headers")
    public Response getHeaders(@Context HttpHeaders headers) {
        StringBuilder sb = new StringBuilder();
        for (String header : headers.getRequestHeaders().keySet()) {
            sb.append(header).append(":").append(headers.getHeaderString(header)).append("<br>");
        }
        return Response.status(200).entity(sb.toString()).build();
    }
}
