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

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.SortDirection;
import com.google.gson.Gson;
import java.io.IOException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

/** Servlet that returns some example content. TODO: modify this file to handle comments data */

@WebServlet("/data")
public class DataServlet extends HttpServlet {
    public class UserComment{
        long timestamp;
        String emailaddress;
        String nickname;
        String body;
        public UserComment(long ts, String cmt){
            this.timestamp =ts;
            this.body = cmt;
        }
    } 

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Query query = new Query("Comment").addSort("timestamp", SortDirection.ASCENDING);
    DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
    PreparedQuery results = datastore.prepare(query);
    List<UserComment> comments = new ArrayList<>();
    for (Entity entity : results.asIterable()) { 
             
        String commentBody = (String) entity.getProperty("body");
        long timestamp = (long) entity.getProperty("timestamp");  
        String emailaddress = (String) entity.getProperty("emailaddress");
        UserComment newComment = new UserComment(timestamp, commentBody);
        comments.add(newComment);      
    }
    Gson gson = new Gson();
    response.setContentType("application/json;");
    response.getWriter().println(gson.toJson(comments));
  }
  
  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {        
       
        UserService userService = UserServiceFactory.getUserService();         
        //retrieve comment from user input    
    if (userService.isUserLoggedIn()) { 
        String commentBody = request.getParameter("comment");
        long timestamp = System.currentTimeMillis();
        String emailaddress = userService.getCurrentUser().getEmail();
        Entity commentEntity = new Entity("Comment");
        commentEntity.setProperty("body", commentBody);
        commentEntity.setProperty("timestamp", timestamp);
        commentEntity.setProperty("emailaddress", emailaddress);
        DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
        datastore.put(commentEntity);        
        //redirects to homepage so the user can see the comment
        response.sendRedirect("/index.html");
    }
  }
  
 
}
