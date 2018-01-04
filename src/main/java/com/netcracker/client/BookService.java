package com.netcracker.client;

import com.netcracker.shared.Book;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

public interface BookService extends RestService {

    @POST
    @Path("api/bookService/book")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void addBook(Book request,
                 MethodCallback<List<Book>> callback);

    @POST
    @Path("api/bookService/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void deleteBook(Integer request,
              MethodCallback<List<Book>> callback);

    @POST
    @Path("api/bookService/get")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void getBooks(MethodCallback<List<Book>> callback);

    @POST
    @Path("api/bookService/sort")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    void sortBooks(Integer request, MethodCallback<List<Book>> callback);




}
