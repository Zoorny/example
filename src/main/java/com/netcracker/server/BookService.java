package com.netcracker.server;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.netcracker.shared.Book;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.List;


/**
 * The server side implementation of the RPC service.
 */
@Path("bookService")
public class BookService {

private static final String filePath = "D:\\resty-gwt\\target\\example-1.0-SNAPSHOT\\WEB-INF\\classes\\com\\netcracker\\server\\booksContainer.xml";

  @Path("/get")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public List<Book> getBooks() throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
    {
      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null)
      {
        contentBuilder.append(sCurrentLine).append("\n");
      }
    }
    String xml = contentBuilder.toString();
    Books booksFromXml = xmlMapper.readValue(xml, Books.class);
    return booksFromXml.getBookList();
  }

  @Path("/delete")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> deleteBook(Integer input) throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
    {

      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null)
      {
        contentBuilder.append(sCurrentLine).append("\n");
      }
    }

    String xml = contentBuilder.toString();
    Books booksFromXml = xmlMapper.readValue(xml, Books.class);
    List<Book> list = booksFromXml.getBookList();
    list.remove(list.get(input));
    booksFromXml.setBookList(list);
    xmlMapper.writeValue(new File(filePath), booksFromXml);
    return booksFromXml.getBookList();
  }

  @Path("/book")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> addBook(Book input) throws IllegalArgumentException, IOException {
    XmlMapper xmlMapper = new XmlMapper();
    StringBuilder contentBuilder = new StringBuilder();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath)))
    {

      String sCurrentLine;
      while ((sCurrentLine = br.readLine()) != null)
      {
        contentBuilder.append(sCurrentLine).append("\n");
      }
    }

    String xml = contentBuilder.toString();
    Books booksFromXml = xmlMapper.readValue(xml, Books.class);
    booksFromXml.getBookList().add(input);
    xmlMapper.writeValue(new File(filePath), booksFromXml);

    return booksFromXml.getBookList();
  }
}
