
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Base64;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	private void handleIncorrectCredentials(HttpServletRequest request, HttpServletResponse response, String username) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("incorrect", true);
		request.setAttribute("username", username);
		response.sendRedirect(request.getContextPath() + "/index.jsp");
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Connection connection = DBRetrieval.getConnection();
		try {
			PreparedStatement preparedStatement = connection
					.prepareStatement("SELECT salt, password_hash, balance FROM logininfo" + " WHERE username = ?");
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();

			if (result.next()) {
				Base64.Decoder dec = Base64.getDecoder();
				byte[] salt = dec.decode(result.getString("salt"));
				byte[] password_hash = dec.decode(result.getString("password_hash"));

				// Make hash from inputed password
				KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
				SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
				byte[] input_password_hash = factory.generateSecret(spec).getEncoded();

				if (Arrays.equals(input_password_hash, password_hash)) {
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					session.setAttribute("balance", result.getDouble("balance"));
					response.sendRedirect(request.getContextPath() + "/welcome.jsp");
				} else {
					// mindset of using application feature not browser feature
					handleIncorrectCredentials(request, response, username);
//					response.sendRedirect(request.getContextPath() + "/wrongcredentials.jsp"); // add pop up instead of going other page, same with account already created CHECK
					// put there company logo in the page 
					// make more css, add text box animation, align text, refer to other sign in
					// platforms
					// have a higher standard, reference other login forms

					// add button to increase or decrease balance for update, CRUD CHECK
				}
			} else {
				handleIncorrectCredentials(request, response, username);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

}
