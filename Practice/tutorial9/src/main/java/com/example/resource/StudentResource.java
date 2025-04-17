package com.example.resource;

import com.example.dao.ModuleDAO;
import com.example.dao.StudentDAO;
import com.example.model.Student;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/students")
public class StudentResource {
    private final StudentDAO studentDAO = new StudentDAO();
    private final ModuleDAO moduleDAO = new ModuleDAO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudents() {
        return Response.ok(studentDAO.getAllStudents()).build();
    }
}
