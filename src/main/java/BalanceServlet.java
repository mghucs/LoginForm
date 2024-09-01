

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet("/BalanceServlet")
public class BalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BalanceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		BigDecimal amount = BigDecimal.valueOf(Double.valueOf(request.getParameter("amount")));
		Connection connection = DBRetrieval.getConnection();
		
		try {

			HttpSession session = request.getSession();

			PreparedStatement preparedStatement = connection.prepareStatement("SELECT balance FROM logininfo" + " WHERE username = ?");
			preparedStatement.setString(1, username);
			
			ResultSet result = preparedStatement.executeQuery();

			if (result.next()) {
				BigDecimal balance = result.getBigDecimal("balance");

				preparedStatement = connection
						.prepareStatement("UPDATE accountlogin.logininfo" +
					" SET balance = balance + ? WHERE username = ?");
				
				preparedStatement.setString(2, username);
				if (request.getParameter("deposit") != null) {
					preparedStatement.setBigDecimal(1, amount);
				}
				else if (request.getParameter("withdraw") != null) {
					if (balance.add(amount.negate()).compareTo(BigDecimal.ZERO) == -1) {
						session.setAttribute("insufficient", true);
						preparedStatement.setBigDecimal(1, BigDecimal.ZERO);
					}
					else preparedStatement.setBigDecimal(1, amount.negate());
				}
				
				preparedStatement.executeUpdate();

				preparedStatement = connection
						.prepareStatement("SELECT balance FROM logininfo" + " WHERE username = ?");
				preparedStatement.setString(1, username);
				result = preparedStatement.executeQuery();

				if (result.next()) {
					session.setAttribute("username", username);
					session.setAttribute("balance", result.getDouble("balance"));
					response.sendRedirect(request.getContextPath() + "/welcome.jsp");
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
