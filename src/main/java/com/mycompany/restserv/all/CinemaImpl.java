/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.all;

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
import com.mycompany.restserv.filter.AnnotateAuth;
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
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
/**
 *
 * @author Mateusz
 */
@Path("cinema")
public class CinemaImpl{

    private MovieDAO movieDAO;
    private AuditoriumDAO auditoriumDAO;
    private ClientDAO clientDao;
    private ReservationDAO reservationDao;
    private ScreeningDAO screeningDao;
    private SeatDAO seatDao;
    private SeatReservedDAO seatRDao;
    
    @GET
    @Path("auditoriums")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiAuditorium> getAuditoriums() {
        this.auditoriumDAO = new JpaAuditoriumDAO();
        return auditoriumDAO.findAll();
    }

    @GET
    @Path("clients")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiClient> getClients() {
        this.clientDao = new JpaClientDAO();
        return clientDao.findAll();
    }

    @GET
    @Path("movies")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiMovie> getMovies() {
        this.movieDAO = new JpaMovieDAO();
        return movieDAO.findAllMovies();
    }

    @GET
    @Path("reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiReservation> getReservations() {
        this.reservationDao = new JpaReservationDAO();
        return reservationDao.findAll();
    }

    @GET
    @Path("screenings")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiScreening> getScreenings() {
        this.screeningDao = new JpaScreeningDAO();
        return screeningDao.findAll();
    }

    @GET
    @Path("seats")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiSeat> getSeats() {
        this.seatDao = new JpaSeatDAO();
        return seatDao.findAll();
    }

    @GET
    @Path("reservedseats")
    @Produces(MediaType.APPLICATION_JSON)
    public List<RsiSeatReserved> getReservedSeats() {
        this.seatRDao = new JpaSeatReservedDAO();
        return seatRDao.findAll();
    }
    
    @GET
    @Path("authenticate")
    @AnnotateAuth
    public Response authenticateClient() throws Exception {
        return Response.accepted().build();
    }

    @GET
    @Path("image")
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

    @POST
    @Path("reservations")
    public void createReservation(Marshal marshal) {
        this.reservationDao = new JpaReservationDAO();
        marshal.setRsiReservation(reservationDao.save(marshal.getRsiReservation()));
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved rsiSeatReserved = new RsiSeatReserved();
        rsiSeatReserved.setReservationId(marshal.getRsiReservation());
        rsiSeatReserved.setScreeningId(marshal.getRsiReservation().getScreeningId());
        rsiSeatReserved.setSeatId(marshal.getRsiSeat());
        seatRDao.save(rsiSeatReserved);
    }

    @POST
    @Path("pdf")
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

    @DELETE
    @Path("reservations/{id}")
    public void removeReservation(@PathParam("id") Integer reservationId) {
        this.reservationDao = new JpaReservationDAO();
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved seatReserved = seatRDao.findByReservationId(reservationDao.findById(reservationId));
        seatRDao.delete(seatReserved);
        reservationDao.delete(reservationDao.findById(reservationId));
    }

    
    @PUT
    @Path("reservations/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public void changeReservation(Marshal marshal) {
        this.reservationDao = new JpaReservationDAO();
        this.seatRDao = new JpaSeatReservedDAO();
        RsiSeatReserved seatReserved = seatRDao.findByReservationId(marshal.getRsiReservation());
        seatReserved.setSeatId(marshal.getRsiSeat());
        seatRDao.update(seatReserved);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/headers")
    public Response getHeaders(@Context HttpHeaders headers) {
        StringBuilder sb = new StringBuilder();
        for (String header : headers.getRequestHeaders().keySet()) {
            sb.append(header).append(":").append(headers.getHeaderString(header)).append("<br>");
        }
        return Response.status(200).entity(sb.toString()).build();
    }
}