package com.curtisnewbie.boundary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.curtisnewbie.authentication.Authenticator;

@Path("/admin")
@RequestScoped
public class AdminResource {

    @Inject
    Authenticator auth;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response getToken(@HeaderParam("authorization") String basicAuthStr) throws Exception {
        if (basicAuthStr == null)
            return Response.status(401).build();

        String[] nameAndPw = auth.decode(basicAuthStr);
        if (auth.isAuthenticated(nameAndPw[0], nameAndPw[1]))
            return Response.ok(auth.generateJWT(nameAndPw[0])).build();
        else
            return Response.status(401).build();
    }

}