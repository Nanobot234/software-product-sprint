package com.google.sps.servlets;

import com.google.appengine.api.datastore.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/Login")
public class LoginStatusServlet extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
    PrintWriter out = response.getWriter();


      UserService userService = UserServiceFactory.getUserService();

           //checks to see if user is logged in. If not then 
         if (!userService.isUserLoggedIn()) {
      String loginUrl = userService.createLoginURL("/Login");
      out.println("<p>Login <a href=\"" + loginUrl + "\">here</a>.</p>");
      return;
    }

     String nickname = getUserNickname(userService.getCurrentUser().getUserId());
    if (nickname == null) {
      response.sendRedirect("/Nickname");
      return;
    }
           String logoutUrl = userService.createLogoutURL("/Login");
        //   String loginStatus = " " + 
    out.println("<h1>Home</h1>");
    out.println("<p>Hello " + nickname + "!</p>");
    out.println("<p>Logout <a href=\"" +  logoutUrl + "\">here</a>.</p>");
    out.println("<p>Change your nickname <a href=\"/Nickname\">here</a>.</p>");



    // User is logged in and has a nickname, so the request can proceed
 
  //  response.setContentType()

  }


    /**
   * Returns the nickname of the user with id, or empty String if the user has not set a nickname.
   */
  private String getUserNickname(String id) {
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    Query query =
        new Query("UserInfo")
            .setFilter(new Query.FilterPredicate("id", Query.FilterOperator.EQUAL, id));
    PreparedQuery results = datastore.prepare(query);
    Entity entity = results.asSingleEntity();
    if (entity == null) {
      return "";
    }
    String nickname = (String) entity.getProperty("nickname");
    return nickname;
  }
}

