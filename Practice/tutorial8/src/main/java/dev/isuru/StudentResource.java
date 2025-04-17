package dev.isuru;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

@Path("/students")
public class StudentResource {

    private static final ConcurrentHashMap<String, Student> studentStore = new ConcurrentHashMap<>();

    static {
        Student s1 = new Student( "Alice", "Smith");
        Student s2 = new Student("Bob", "Carpenter");
        Student s3 = new Student( "John", "White");
        studentStore.put(s1.getId(), s1);
        studentStore.put(s2.getId(), s2);
        studentStore.put(s3.getId(), s3);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudents() {
        return Response.ok(new ArrayList<>(studentStore.values())).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    public Response getStudentById(@PathParam("id") String id) {
        if (studentStore.containsKey(id)) {
            return Response.ok(studentStore.get(id)).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createStudent(Student student) {
        String firstName = student.getFirstName();
        String lastName = student.getLastName();
        if (firstName.isEmpty() || lastName.isEmpty()) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        } else {
            studentStore.put(student.getId(), student);
            return Response.status(Response.Status.CREATED).build();
        }
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateStudent(Student updatedStudent) {
        String id = updatedStudent.getId();
        if (studentStore.containsKey(id)) {
            Student student = studentStore.get(id);
            student.setFirstName(student.getFirstName());
            student.setLastName(student.getLastName());
            return Response.status(Response.Status.ACCEPTED).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    public Response deleteStudent(@PathParam("id") String id) {
        if (studentStore.containsKey(id)) {
            studentStore.remove(id);
            return Response.status(Response.Status.NO_CONTENT).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

}
