

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;
import java.util.Random;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

@WebServlet("/SignUpServlet")
public class SignUpServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignUpServlet() {
        super();
    }
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		Connection connection = DBRetrieval.getConnection();
		
		try {
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT username, salt FROM logininfo"
					+ " WHERE username = ?");
			preparedStatement.setString(1, username);
			ResultSet result = preparedStatement.executeQuery();
			HttpSession session = request.getSession();
			if (result.next()) {
				session.setAttribute("created", true);
				response.sendRedirect(request.getContextPath() + "/signup.jsp");
				return;
			}
			
			preparedStatement = connection.prepareStatement("INSERT INTO logininfo values(?,?,?,?)");
	        Random rand = new Random();
	        
			SecureRandom random = new SecureRandom();
			byte[] salt = new byte[16];
			random.nextBytes(salt);
			
			// Make hash from new password
			KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, 65536, 128);
			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
			byte[] password_hash = factory.generateSecret(spec).getEncoded();
			
			Base64.Encoder enc = Base64.getEncoder();
			
			preparedStatement.setString(1, username);
			preparedStatement.setString(2, enc.encodeToString(salt));
			preparedStatement.setString(3, enc.encodeToString(password_hash));
			preparedStatement.setInt(4, 0);
			preparedStatement.executeUpdate();

			session.setAttribute("username", username);
			session.setAttribute("balance", 0);
			response.sendRedirect(request.getContextPath() + "/welcome.jsp");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		}
	}

}
