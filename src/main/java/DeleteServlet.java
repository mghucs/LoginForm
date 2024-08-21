

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@WebServlet("/DeleteServlet")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DeleteServlet() {
        super();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");

		Connection connection = DBRetrieval.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM logininfo"
					+ " WHERE username = ?");
			preparedStatement.setString(1, username);
	        preparedStatement.executeUpdate();
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
