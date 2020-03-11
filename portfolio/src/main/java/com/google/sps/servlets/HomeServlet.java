package com.google.sps.servlets;

import com.google.gson.Gson;
import java.util.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.PrintWriter;

@WebServlet("/home")
public class HomeServlet extends HttpServlet {
  public class UserData{
        String userEmail;
        String userID;
        String userNickname;
        String loginUrl;  
        String logoutUrl;  
        boolean isloggedIn;    
    }
  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    
    UserData userSession = new UserData();
    UserService userService = UserServiceFactory.getUserService();
    if (userService.isUserLoggedIn()) {
      userSession.isloggedIn = true;
      String userEmail = userService.getCurrentUser().getEmail();
      String urlToRedirectToAfterUserLogsOut = "/index.html";
      String logoutUrl = userService.createLogoutURL("urlToRedirectToAfterUserLogsOut");
      userSession.userEmail = userEmail;
      userSession.logoutUrl = "\""+logoutUrl+"\"";      
    } else {
      String urlToRedirectToAfterUserLogsIn = "/index.html";
      String loginUrl = userService.createLoginURL(urlToRedirectToAfterUserLogsIn);
      userSession.loginUrl = "\""+loginUrl+"\"";      
    }
    Gson gson = new Gson();
    response.getWriter().println(gson.toJson(userSession));

  }
}