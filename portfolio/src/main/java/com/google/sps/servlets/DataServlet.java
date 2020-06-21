// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps.servlets;
import java.util.*;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
     ArrayList<String> StringList = new ArrayList<String>();


 public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    // Get the input from the form.
    String text = getParameter(request, "thisName", "");
    text += "\n";
    text +=   getParameter(request, "thisComments", "");
    boolean upperCase = Boolean.parseBoolean(getParameter(request, "upper-case", "false"));
    boolean sort = Boolean.parseBoolean(getParameter(request, "sort", "false"));

    // Convert the text to upper case.
    if (upperCase) {
      text = text.toUpperCase();
    }

    // Break the text into individual words.
    String[] words = text.split("\\s*,\\s*");

    // Sort the words.
    if (sort) {
      Arrays.sort(words);
    }

      DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    StringList.add(Arrays.toString(words));

    Entity taskEntity = new Entity("Task");
   
    taskEntity.setProperty("Comments", StringList);

    datastore.put(taskEntity);

    // Respond with the result.
    response.setContentType("text/html;");
    response.getWriter().println(Arrays.toString(words));

   response.sendRedirect("/contacts.html");
  }


    @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

   response.setContentType("application/json");

   //loop through arrayList and return eacch string inside whcich 
        String s = new String("Hi");
   String jsonString = new Gson().toJson(StringList);
    response.getWriter().println(jsonString);
  }


  
   private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private String convertToJson(ArrayList list) {
    String json = "{";
    json += "\"firstthing\": ";
    json += "\"" + list.get(0) + "\"";
    json += ", ";
    json += "\"secondthing\": ";
    json += "\"" + list.get(1) + "\"";
    json += ", ";
    json += "\"thirdthing\": ";
    json +=  "\"" + list.get(2) + "\"";
    json += "}";
    return json;
  }

}
