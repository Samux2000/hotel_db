package hotel_db;

import java.sql.*;
import java.util.Scanner;

public class HotelApp {

    // Metodo per connettersi al database tramite JDBC
    public static Connection getConnection() throws SQLException {
        // Configura i parametri di connessione al database
        String url = "jdbc:mysql://localhost:3306/hotel"; // Modifica l'URL in base al tuo DB
        String user = "root";       // Sostituisci con il tuo username
        String password = "";   	// Sostituisci con la tua password
        return DriverManager.getConnection(url, user, password);
    }

    // Metodo per inserire un nuovo ospite nella tabella 'ospiti'
    public static void insertGuest(Connection conn, Scanner scanner) {
        System.out.println("Inserisci il nome dell'ospite:");
        String nome = scanner.nextLine();

        String sql = "INSERT INTO ospiti (nome) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nome);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Ospite inserito correttamente.");
            } else {
                System.out.println("Errore nell'inserimento dell'ospite.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Metodo per effettuare una prenotazione
    public static void bookReservation(Connection conn, Scanner scanner) {
        System.out.println("Inserisci l'ID dell'ospite:");
        int idOspite = Integer.parseInt(scanner.nextLine());
        System.out.println("Inserisci l'ID della camera:");
        int idCamera = Integer.parseInt(scanner.nextLine());

        String sql = "INSERT INTO prenotazioni (id_ospite, id_camera) VALUES (?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idOspite);
            ps.setInt(2, idCamera);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Prenotazione effettuata correttamente.");
            } else {
                System.out.println("Errore nella prenotazione.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Metodo per visualizzare tutte le prenotazioni con dettagli su ospiti e camere
    public static void showReservations(Connection conn) {
    	String sql = "SELECT p.id_prenotazione, p.id_ospite, p.id_camera,"
    			+ " o.nome FROM prenotazioni p "
    			+ "JOIN ospiti o ON p.id_ospite = o.id_ospite";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            System.out.println("Elenco prenotazioni:");
            while (rs.next()) {
                int id_prenotazione = rs.getInt("id_prenotazione");
                int id_ospite = rs.getInt("id_ospite");
                int id_camera = rs.getInt("id_camera");
                String nome = rs.getString("nome");

                System.out.println("ID Prenotazione: " + id_prenotazione +
                        "Ospite: " + nome + "ID Ospite: " + id_ospite +
                        "Camera: " + id_camera);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Metodo main per l'interfaccia CLI
    public static void main(String[] args) {
        try (Connection conn = getConnection();
             Scanner scanner = new Scanner(System.in)) {

            while (true) {
                System.out.println("\nScegli un'operazione:");
                System.out.println("1. Inserire nuovo ospite");
                System.out.println("2. Effettuare una prenotazione");
                System.out.println("3. Visualizzare tutte le prenotazioni");
                System.out.println("0. Uscire");

                String choice = scanner.nextLine();

                switch (choice) {
                    case "1":
                        insertGuest(conn, scanner);
                        break;
                    case "2":
                        bookReservation(conn, scanner);
                        break;
                    case "3":
                        showReservations(conn);
                        break;
                    case "0":
                        System.out.println("Uscita...");
                        return;
                    default:
                        System.out.println("Scelta non valida. Riprova.");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
	
