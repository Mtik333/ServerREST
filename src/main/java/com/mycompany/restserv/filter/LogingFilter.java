/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.filter;


//import com.sun.xml.internal.messaging.saaj.util.Base64;
import com.mycompany.restserv.db.ClientDAO;
import com.mycompany.restserv.db.JpaClientDAO;
import com.mycompany.restserv.moviedto.RsiClient;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;


/**
 *
 * @author Mateusz
 */
@Provider
@AnnotateAuth
public class LogingFilter implements ContainerResponseFilter, ContainerRequestFilter {

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        System.out.println("response filter");
        System.out.println("headers " + responseContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext crc) throws IOException {
        String authorization = crc.getHeaders().getFirst("Authorization");
        if (authorization!=null){
            String base64Credentials = authorization.substring("Basic".length()).trim();
            String credentials;
            credentials = new String(Base64.getDecoder().decode(base64Credentials), StandardCharsets.UTF_8);
//            credentials = Base64.base64Decode(base64Credentials);
            final String[] values = credentials.split(":",2);
            ClientDAO clientDao = new JpaClientDAO();
            RsiClient client = clientDao.findByUsernamePassword(values[0], values[1]);
            if (client!=null){
                System.out.println("ok");
                return;
            }
        }
        Response response = Response.status(Response.Status.UNAUTHORIZED).build();
        crc.abortWith(response);
    }
}
