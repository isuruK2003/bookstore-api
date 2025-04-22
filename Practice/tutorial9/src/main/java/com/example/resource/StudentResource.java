package com.example.resource;

import com.example.dao.StudentDAO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/students")
public class StudentResource {
    private final StudentDAO studentDAO = new StudentDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        return Response.ok(studentDAO.getAllStudents()).build();
    }
}
