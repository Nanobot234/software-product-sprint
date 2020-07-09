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
import java.util.Collections;
import java.time.*; 
import com.google.gson.Gson;
import java.io.IOException;
import java.util.Arrays;
import javax.servlet.annotation.WebServlet;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */
@WebServlet("/data")
public class DataServlet extends HttpServlet {
     ArrayList<String> StringList = new ArrayList<String>();
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();

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
  //storing the string in database
      
    StringList.add(Arrays.toString(words));

        //Creating a new entity to place in the database
    Entity taskEntity = new Entity("Task");

   LocalDateTime localdate = LocalDateTime.now();

    String dateandtime = " " + localdate;
    taskEntity.setProperty("Comments", StringList);
    taskEntity.setProperty("timeStamp",dateandtime);
   
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
      
       Query query = new Query("Task").addSort("timeStamp", SortDirection.DESCENDING);
       PreparedQuery results = datastore.prepare(query);

        //creating an array list that will store the date + comments returned from database
       ArrayList<String> AllComments = new ArrayList<String>();
        
       for (Entity entity : results.asIterable()) {
          //  String timeing = " " + entity.getProperty("timeStamp");

        @SuppressWarnings("unchecked")
             ArrayList<String> CurrComment = (ArrayList<String>) entity.getProperty("Comments");
       
       //should put everything youve gotten from nitity intot this ArrayList
       for(int i = 0; i < CurrComment.size(); i++){
           AllComments.add(CurrComment.get(i) + "\n");
       }

    }
        //this part saves the 
   String jsonString = new Gson().toJson(AllComments);
    response.getWriter().println(jsonString);
  }


  
   private String getParameter(HttpServletRequest request, String name, String defaultValue) {
    String value = request.getParameter(name);
    if (value == null) {
      return defaultValue;
    }
    return value;
  }

  private String getUserNickname(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =
        new Query("UserInfo")
            .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return null;
    }
    String nickname = (String) entity.getProperty("nickname");
    return nickname;
  }

}
