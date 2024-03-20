
/**
 * @file SimpleFormInsert.java
 */
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormInsert")
public class SimpleFormInsert extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormInsert() {
      super();
   }

   @Override
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String task = request.getParameter("task");
      String due = request.getParameter("due");

      Connection connection = null;
      String insertSql = " INSERT INTO listTable (id, TASK, DUE) values (default, ?, ?)";

      try {
         DBConnection.getDBConnection(getServletContext());
         connection = DBConnection.connection;
         PreparedStatement preparedStmt = connection.prepareStatement(insertSql);
         preparedStmt.setString(1, task);
         preparedStmt.setString(2, due);
    
         preparedStmt.execute();
         connection.close();
      } catch (Exception e) {
         e.printStackTrace();
      }

      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + "transitional//en\">\n";
      out.println(docType + //
    		  "<html>\n" +
              "<head>" +
              "<style>\n" +
              
              "header {" +
              "    background-color: #0B5345;" +
              "    color: white;" +
              "    text-align: center;" +
              "    padding: 5px;" +
              "}\n" +
              
              "section {\n" +
              "    width: 700px;\n" +
              "    float: left;\n" +
              "    padding: 10px;\n" +
              "    font-size: 18px;\n" +
              "}\n" +
              
              
 			  "nav {" +
 			  "   line-height: 30px;"+
 			  "   background-color: #eeeeee;"+
 			  "   height: 300px;" +
 			  "   width: 100px;" +
 			  "   float: left;" +
 			  "   padding: 5px;" +
 			  "}" +
 			  
			  "footer {" +
			  "background-color:#0B5345;" +
			  "color:white;" +
			  "clear:both;" +
			  "text-align:center;" +
			  "padding:5px;" +	 	 
			  "}" +
                 
              "</style>" + 
              "</head>\n" +
              
              "<header>\n" +
              "<h1>You Have Created a New Task</h1>\n" +
              "</header>\n" +
              
              "<body bgcolor=\"#FFFFFF\">\n" +
              "<nav>\n" +
              "<a href=\"/webproject/SimpleFormShowTask\">My Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormInsert.html\">Create Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormSearch.html\">Find Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormDelete.html\">Delete Task</a> <br>\n" +
              "</nav>\n" +
              
              "<section>\n" +
      		  "  <li><b>Task</b>: " + task + "\n" + //
      		  "  <li><b>Due Date</b>: " + due + "<br>" + //
    		  "</section>\n");
              
      out.println("<footer>Angel Nunez</footer>");
      out.println("</body></html>");
   }

   @Override
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
