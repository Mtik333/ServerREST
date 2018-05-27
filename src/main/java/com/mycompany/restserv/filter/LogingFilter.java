/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.restserv.filter;


import java.io.IOException;
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
//            try {
//                credentials = new String(Base64.decode(base64Credentials),
//                        Charset.forName("UTF-8"));
//                final String[] values = credentials.split(":",2);
//                if (values[0].contentEquals("test") && values[1].contentEquals("test")){
//                    System.out.println("ok");
//                    return;
//                }
//            } catch (Base64DecodingException ex) {
//                Logger.getLogger(LogingFilter.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        Response response = Response.status(Response.Status.UNAUTHORIZED).build();
        crc.abortWith(response);
    }
}
