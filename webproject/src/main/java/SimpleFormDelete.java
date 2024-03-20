import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SimpleFormDelete")
public class SimpleFormDelete extends HttpServlet {
   private static final long serialVersionUID = 1L;

   public SimpleFormDelete() {
      super();
   }

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      String keyword = request.getParameter("taskNumber");
      deleteTask(Integer.parseInt(keyword), response);
   }

   void deleteTask(int keyword, HttpServletResponse response) throws IOException {
      response.setContentType("text/html");
      PrintWriter out = response.getWriter();
      String docType = "<!doctype html public \"-//w3c//dtd html 4.0 " + //
            "transitional//en\">\n"; //
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
              "    width: 450px;\n" +
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
              "<h1>Delete Task</h1>\n" +
              "</header>\n" +
              
              "<body bgcolor=\"#FFFFFF\">\n" +
              "<nav>\n" +
              "<a href=\"/webproject/SimpleFormShowTask\">My Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormInsert.html\">Create Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormSearch.html\">Find Task</a> <br>\n" +
              "<a href=\"/webproject/simpleFormDelete.html\">Delete Task</a> <br>\n" +
              "</nav>\n" +
              "<section>\n");
   
      Connection connection = null;
      PreparedStatement preparedStatement = null;
      try {
    	  DBConnection.getDBConnection(getServletContext());
          connection = DBConnection.connection;
         

          String deleteSQL = "DELETE FROM listTable WHERE id=?";
          preparedStatement = connection.prepareStatement(deleteSQL);
          preparedStatement.setInt(1, keyword);
          int rowsAffected = preparedStatement.executeUpdate();

          if (rowsAffected > 0) {
        	  out.println("Task #" + keyword + " deleted successfully.");
          }  else {
        	  out.println("No Task deleted for Task #" + keyword + ". It might not exist.");
          }
          
          preparedStatement.close();
          
          String countRowsSQL = "SELECT COUNT(*) FROM listTable";
          preparedStatement = connection.prepareStatement(countRowsSQL);
          ResultSet rs = preparedStatement.executeQuery();

          rs.next();
          int rowCount = rs.getInt(1);

          if (rowCount == 0) {
              // Reset the ID sequence to 1
              String resetIDSQL = "ALTER TABLE listTable AUTO_INCREMENT = 1";
              preparedStatement = connection.prepareStatement(resetIDSQL);
              preparedStatement.executeUpdate();
          }
          

         
         out.println("</section>\n");
         out.println("<footer>Angel Nunez</footer>");
         out.println("</body></html>");
        
      } catch (SQLException se) {
         se.printStackTrace();
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         try {
            if (preparedStatement != null)
               preparedStatement.close();
         } catch (SQLException se2) {
         }
         try {
            if (connection != null)
               connection.close();
         } catch (SQLException se) {
            se.printStackTrace();
         }
      }
   }

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
      doGet(request, response);
   }

}
