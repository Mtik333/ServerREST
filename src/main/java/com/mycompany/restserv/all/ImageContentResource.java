/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.all;
import com.mycompany.restserv.db.JpaMovieDAO;
import com.mycompany.restserv.db.MovieDAO;
import com.mycompany.restserv.moviedto.RsiMovie;
import java.awt.image.BufferedImage;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.ws.rs.Produces;
import java.io.ByteArrayOutputStream;
/**
 *
 * @author walendziukm
 */
@Path("")
public class ImageContentResource {
    private MovieDAO movieDAO;
    public ImageContentResource(){}
    
    public ImageContentResource(Long id) {
        System.out.println(id);
        this.id=id;
    }
    
    Long id=1L;
    
    @GET
    public Response get(){
        this.movieDAO = new JpaMovieDAO();
        RsiMovie rsiMovie = movieDAO.findById(id.intValue());
        return Response.status(200).entity(rsiMovie).build();
    }
    
    @GET
    @Path("/image")
    @Produces("image/png")
    public Response getImage() {
        System.out.println("niewarto");
        this.movieDAO = new JpaMovieDAO();
        RsiMovie rsiMovie = movieDAO.findById(id.intValue());
        try {
            File f = new File(CinemaImpl.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            System.out.println(f.getAbsolutePath() + ";\n" + f.getCanonicalPath() + "+\n" + f.getPath());
            File test = new File("D:" + File.separator + "posters" + File.separator + rsiMovie.getId()+".png");
            System.out.println(test.getPath());
            System.out.println(test.getAbsolutePath());
            BufferedImage img = ImageIO.read(test.getAbsoluteFile());
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(img, "png", baos);
            byte[] imageData = baos.toByteArray();
            return Response.ok(imageData).build();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
