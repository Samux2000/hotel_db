package hotel_db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class TestMySQL {
    private static final String URL = "jdbc:mysql://localhost:3306/hotel_db"; // Cambia con il nome del tuo database
    private static final String USER = "root"; // Utente predefinito di XAMPP
    private static final String PASSWORD = ""; // Password predefinita (vuota)

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            System.out.println("✅ Connessione a MySQL riuscita!");
        } catch (SQLException e) {
            System.out.println("❌ Errore di connessione:");
            e.printStackTrace();
        }
    }
}

