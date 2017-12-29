package com.netcracker.client;

import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.cellview.client.TextColumn;
import com.google.gwt.view.client.ListDataProvider;
import com.netcracker.shared.Book;
import com.netcracker.shared.FieldVerifier;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import org.fusesource.restygwt.client.Defaults;
import org.fusesource.restygwt.client.Method;
import org.fusesource.restygwt.client.MethodCallback;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class gwt implements EntryPoint {
  /**
   * The message displayed to the user when the server cannot be reached or
   * returns an error.
   */
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";

  private final Messages messages = GWT.create(Messages.class);

    private static List<Book> books = null;


  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {
    String root=Defaults.getServiceRoot();
    root=root.replace("gwt/","");
    Defaults.setServiceRoot(root);

    final Button sendButton = new Button( messages.sendButton() );
    final Button addButton = new Button(messages.addButton());
    final Button deleteButton = new Button(messages.deleteButton());
    final Label loadErrorLabel = new Label();

    final CellTable<Book> table = new CellTable<Book>();


    // We can add style names to widgets
      sendButton.addStyleName("sendButton");

      BookService service = GWT.create(BookService.class);
      ListDataProvider<Book> dataProvider = new ListDataProvider<Book>();
      List<Book> list = dataProvider.getList();
      MethodCallback<List<Book>> methodCallbackSet = new MethodCallback<List<Book>>() {

          public void onSuccess(Method method, List<Book> response) {
              books = response;
              for (Book book : books) {
                  list.add(book);
              }
          }

          public void onFailure(Method method, Throwable exception) {
              loadErrorLabel.setText("Something went wrong");
          }};
      service.getBooks(methodCallbackSet);


      // Connect the table to the data provider.
      dataProvider.addDataDisplay(table);
      // Add the data to the data provider, which automatically pushes it to the
      // widget.


    TextColumn<Book> authorColumn = new TextColumn<Book>() {
      @Override
      public String getValue(Book book) {
        return book.getAuthor();
      }
    };
    TextColumn<Book> nameColumn = new TextColumn<Book>() {
      @Override
      public String getValue(Book book) {
        return book.getName();
      }
    };
    TextColumn<Book> pageNumColumn = new TextColumn<Book>() {
      @Override
      public String getValue(Book book) { return String.valueOf(book.getPageNum());
      }
    };
    TextColumn<Book> yearColumn = new TextColumn<Book>() {
      @Override
      public String getValue(Book book) {
        return String.valueOf(book.getYear());
      }
    };
    TextColumn<Book> dateAddedColumn = new TextColumn<Book>() {
      @Override
      public String getValue(Book book) {
        return String.valueOf(book.getDateAdded());
      }
    };

    authorColumn.setSortable(true);
    nameColumn.setSortable(true);
    pageNumColumn.setSortable(true);
    yearColumn.setSortable(true);
    dateAddedColumn.setSortable(true);

    table.addColumn(authorColumn, "Author");
    table.addColumn(nameColumn, "Name");
    table.addColumn(pageNumColumn, "Pages");
    table.addColumn(yearColumn, "Year");
    table.addColumn(dateAddedColumn, "Date Added");


      ColumnSortEvent.ListHandler<Book> columnSortHandler = new ColumnSortEvent.ListHandler<Book>(list);
      columnSortHandler.setComparator(nameColumn, new Comparator<Book>() {
                  public int compare(Book b1, Book b2) {
                      if (b1 == b2) {
                          return 0;
                      }
                      if (b1 != null) {
                          return (b2 != null) ? b1.getName().compareTo(b2.getName()) : 1;
                      }
                      return -1;
                  }
      });
      columnSortHandler.setComparator(authorColumn, new Comparator<Book>() {
          public int compare(Book b1, Book b2) {
              if (b1 == b2) {
                  return 0;
              }
              if (b1 != null) {
                  return (b2 != null) ? b1.getAuthor().compareTo(b2.getAuthor()) : 1;
              }
              return -1;
          }
      });
      columnSortHandler.setComparator(pageNumColumn, new Comparator<Book>() {
          public int compare(Book b1, Book b2) {
              if (b1 == b2) {
                  return 0;
              }
              if (b1 != null) {
                  return (b2 != null) ? (b1.getPageNum() - b2.getPageNum()) : 1;
              }
              return -1;
          }
      });
      columnSortHandler.setComparator(yearColumn, new Comparator<Book>() {
          public int compare(Book b1, Book b2) {
              if (b1 == b2) {
                  return 0;
              }
              if (b1 != null) {
                  return (b2 != null) ? (b1.getYear() - b2.getYear()) : 1;
              }
              return -1;
          }
      });
      columnSortHandler.setComparator(dateAddedColumn, new Comparator<Book>() {
          public int compare(Book b1, Book b2) {
              if (b1 == b2) {
                  return 0;
              }
              if (b1 != null) {
                  return (b2 != null) ? b1.getDateAdded().compareTo(b2.getDateAdded()) : 1;
              }
              return -1;
          }
      });

      table.addColumnSortHandler(columnSortHandler);
      table.getColumnSortList().push(authorColumn);

      RootPanel.get("tableContainer").add(table);
      RootPanel.get("tableContainer").add(loadErrorLabel);
      RootPanel.get("buttonContainer").add(addButton);
      RootPanel.get("buttonContainer").add(deleteButton);
      RootPanel.get("sendButtonContainer").add(sendButton);

    // Focus the cursor on the name field when the app loads


    // Create the popup dialog box
    final DialogBox dialogBox = new DialogBox();
    dialogBox.setText("Add new book");
    dialogBox.setAnimationEnabled(true);
    final Button closeButton = new Button("Close");
    final Label errorLabel = new Label();
    errorLabel.setStyleName("errorLabel");
    final TextBox authorField = new TextBox();
    final TextBox nameField = new TextBox();
    final TextBox pagesField = new TextBox();
    final TextBox yearField = new TextBox();
    authorField.setText( messages.authorField() );
    nameField.setText( messages.nameField() );
    pagesField.setText( messages.pagesField() );
    yearField.setText( messages.yearField() );
    // We can set the id of a widget by accessing its Element
    closeButton.getElement().setId("closeButton");
    final HTML serverResponseLabel = new HTML();
    VerticalPanel dialogVPanel = new VerticalPanel();
    dialogVPanel.addStyleName("dialogVPanel");
    dialogVPanel.add(new HTML("<b>Sending book to the server:</b>"));
    dialogVPanel.add(authorField);
    dialogVPanel.add(nameField);
    dialogVPanel.add(pagesField);
    dialogVPanel.add(yearField);
    dialogVPanel.add(errorLabel);
    dialogVPanel.add(new HTML("<br><b>Book added:</b>"));
    dialogVPanel.add(serverResponseLabel);
    dialogVPanel.setHorizontalAlignment(VerticalPanel.ALIGN_RIGHT);
    dialogVPanel.add(sendButton);
    dialogVPanel.add(closeButton);
    dialogBox.setWidget(dialogVPanel);

    // Add a handler to close the DialogBox
    closeButton.addClickHandler(new ClickHandler() {
      public void onClick(ClickEvent event) {
        dialogBox.hide();
        sendButton.setEnabled(true);
        sendButton.setFocus(true);
      }
    });

    // Create a handler for the sendButton and nameField
    class SendHandler implements ClickHandler {
      /**
       * Fired when the user clicks on the sendButton.
       */
      public void onClick(ClickEvent event) {
          sendBookToServer();
      }

      /**
       * Send the name from the nameField to the server and wait for a response.
       */

      private void sendBookToServer() {
        // First, we validate the input.
        errorLabel.setText("");
        String authorToServer = authorField.getText();
        String nameToServer = nameField.getText();
        String pagesToServer = pagesField.getText();
        String yearToServer = yearField.getText();
        if (!FieldVerifier.isValidAuthor(authorToServer)) {
              errorLabel.setText("Invalid author field");
              return;
          }
        if (!FieldVerifier.isValidName(nameToServer)) {
          errorLabel.setText("Invalid name field");
          return;
        }
          if (!FieldVerifier.isValidPages(pagesToServer)) {
              errorLabel.setText("Incorrect pages number");
              return;
          }
          if (!FieldVerifier.isValidYear(yearToServer)) {
              errorLabel.setText("Incorrect year");
              return;
          }


        // Then, we send the input to the server.

        serverResponseLabel.setText("");
        Book book = new Book(1,authorToServer,nameToServer,Integer.parseInt(pagesToServer),Integer.parseInt(yearToServer), (new Date()).toString());
        service.addBook(book, new MethodCallback<List<Book>>() {

          public void onSuccess(Method method, List<Book> response) {
            serverResponseLabel.removeStyleName("serverResponseLabelError");
              books = response;
              list.clear();
              for (Book book : books) {
                  list.add(book);
              }
            dialogBox.center();
            closeButton.setFocus(true);
          }

          public void onFailure(Method method, Throwable exception) {
            // Show the RPC error message to the user
            dialogBox.setText("Book sender - Failure");
            serverResponseLabel.addStyleName("serverResponseLabelError");
            serverResponseLabel.setHTML(SERVER_ERROR);
            dialogBox.center();
            closeButton.setFocus(true);
          }});
      }
    }
    class AddHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent clickEvent) {
            dialogBox.center();
            authorField.setFocus(true);
            authorField.selectAll();
        }
    }
    class DeleteHandler implements ClickHandler{

        @Override
        public void onClick(ClickEvent clickEvent) {
            service.deleteBook(table.getKeyboardSelectedRow(),new MethodCallback<List<Book>>() {

                public void onSuccess(Method method, List<Book> response) {
                    books = response;
                    list.clear();
                    for (Book book : books) {
                        list.add(book);
                    }
                }

                public void onFailure(Method method, Throwable exception) {
                    loadErrorLabel.setText("Something went wrong");
                }});


            //list.remove(table.getKeyboardSelectedRow());
        }
    }

    // Add a handler to add, delete and send a book

    SendHandler sendHandler = new SendHandler();
    DeleteHandler deleteHandler = new DeleteHandler();
    AddHandler addHandler = new AddHandler();
    sendButton.addClickHandler(sendHandler);
    addButton.addClickHandler(addHandler);
    deleteButton.addClickHandler(deleteHandler);

  }
}
