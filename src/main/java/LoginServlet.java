

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");

		Connection connection = DBRetrieval.getConnection();
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT salt, password_hash, balance FROM logininfo"
					+ " WHERE username = ?");
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
					request.setAttribute("username", username);
					request.setAttribute("balance", result.getInt("balance"));
					request.getRequestDispatcher("/welcome.jsp").forward(request, response);
		        }
			}
			else {
				response.sendRedirect(request.getContextPath() + "/wrongcredentials.jsp");
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
