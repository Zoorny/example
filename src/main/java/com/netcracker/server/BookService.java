package com.netcracker.server;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.netcracker.shared.Book;

import javax.servlet.ServletContext;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import java.io.*;
import java.util.Comparator;
import java.util.List;


/**
 * The server side implementation of the RPC service.
 */
@Path("bookService")
public class BookService {
  @Context
  ServletContext context;


  @Path("/get")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  public List<Book> getBooks() throws IOException {
    return readFromXml().getBookList();
  }

  @Path("/delete")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> deleteBook(Integer input) throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    Books booksFromXml = readFromXml();
    List<Book> list = booksFromXml.getBookList();
    list.remove(list.get(input));
    booksFromXml.setBookList(list);
    xmlMapper.writeValue(new File(context.getRealPath("/WEB-INF/classes/com/netcracker/server/booksContainer.xml")), booksFromXml);
    return booksFromXml.getBookList();
  }

  @Path("/book")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> addBook(Book input) throws IllegalArgumentException, IOException {
    XmlMapper xmlMapper = new XmlMapper();
    Books booksFromXml = readFromXml();
    booksFromXml.getBookList().add(input);
    xmlMapper.writeValue(new File(context.getRealPath("/WEB-INF/classes/com/netcracker/server/booksContainer.xml")), booksFromXml);

    return booksFromXml.getBookList();
  }

  @Path("/sort")
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  public List<Book> sortBooks(int column) throws IllegalArgumentException, IOException {
    XmlMapper xmlMapper = new XmlMapper();
    Books booksFromXml = readFromXml();
    List<Book> list = booksFromXml.getBookList();
    list.sort(new Comparator<Book>() {
      @Override
      public int compare(Book b1, Book b2) {
        if (b1 == b2) {
          return 0;
        }
        if ((b1 != null)&& (column == 0)) {
          return (b2 != null) ? b1.getName().compareTo(b2.getName()) : 1;
        }
        else if ((b1 != null)&& (column == 1)){
          return (b2 != null) ? b1.getAuthor().compareTo(b2.getAuthor()) : 1;
        }
        else if ((b1 != null)&& (column == 2)){
          return (b2 != null) ? (b1.getPageNum() - b2.getPageNum()) : 1;
        }
        else if ((b1 != null)&& (column == 3)){
          return (b2 != null) ? (b1.getYear() - b2.getYear()) : 1;
        }
        else if ((b1 != null)&& (column == 4)){
          return (b2 != null) ? b1.getDateAdded().compareTo(b2.getDateAdded()) : 1;
        }
        return -1;
      }
    });
    booksFromXml.setBookList(list);
    xmlMapper.writeValue(new File(context.getRealPath("/WEB-INF/classes/com/netcracker/server/booksContainer.xml")), booksFromXml);

    return booksFromXml.getBookList();
  }



  public Books readFromXml() throws IOException {
    XmlMapper xmlMapper = new XmlMapper();
    String filePath = context.getRealPath("/WEB-INF/classes/com/netcracker/server/booksContainer.xml");

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
    return booksFromXml;
  }

}
