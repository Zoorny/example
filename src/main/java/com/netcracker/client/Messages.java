package com.netcracker.client;

import com.google.gwt.i18n.client.LocalizableResource.Generate;

@Generate(format = "com.google.gwt.i18n.server.PropertyCatalogFactory")
public interface Messages extends com.google.gwt.i18n.client.Messages {


  @DefaultMessage("Enter author")
  String authorField();

  @DefaultMessage("Enter book name")
  String nameField();

  @DefaultMessage("Enter number of pages")
  String pagesField();

  @DefaultMessage("Enter year")
  String yearField();

  @DefaultMessage("Add")
  String addButton();

  @DefaultMessage("Delete")
  String deleteButton();

  @DefaultMessage("Send")
  String sendButton();
}
