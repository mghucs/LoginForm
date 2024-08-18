

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class HelloServlet
 */
@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");


		Connection connection = DBRetrieval.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT password, balance FROM logininfo");
	        ResultSet result = preparedStatement.executeQuery();

			PrintWriter writer = response.getWriter();
			
			while(result.next()) {
		        if (result.getString("password").equals(password)) {
					writer.println("<h1>Your balance is " + result.getString("balance") + "</h1>");
		        }
		        else writer.println("Wrong credentials");
			}
			writer.close();
		} catch (SQLException e) {
			e.printStackTrace();
			response.getWriter().println("Error");
		}
	}

}
