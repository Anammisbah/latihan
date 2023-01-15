import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class App {
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/buku";
    static final String USER = "root";
    static final String PASS = "";

    static Connection conn;
    static PreparedStatement stmt;
    static ResultSet rs;

    static BufferedReader input = new BufferedReader(new InputStreamReader(System.in));

    static void showBuku() throws Exception {
        String sql = "SELECT * FROM buku";
        stmt = conn.prepareStatement(sql);
        rs = stmt.executeQuery();
        System.out.println("| DATA BUKU |");
        while (rs.next()){
            int id = rs.getInt("id");
            String judul = rs.getString("judul");
            String penulis = rs.getString("penulis");
            System.out.println(String.format("%d. %s ===> (%s)", id, judul, penulis));
        }
    }

    static void insertBuku() throws Exception {
        System.out.print("Masukkan judul buku: ");
        String judul = input.readLine();
        System.out.print("Masukkan penulis buku: ");
        String penulis = input.readLine();
        String sql = "INSERT INTO buku (judul, penulis) VALUES (?, ?)";
        stmt = conn.prepareStatement(sql);
        stmt.setString(1, judul);
        stmt.setString(2, penulis);
        stmt.executeUpdate();
        System.out.println("Data buku berhasil ditambahkan!");
    }

    static void deleteBuku() throws Exception {
        System.out.print("Masukkan ID buku yang ingin dihapus: ");
        int id = Integer.parseInt(input.readLine());
        String sql = "DELETE FROM buku WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Data buku berhasil dihapus!");
    }

    public static void main(String[] args) {
        try {
            Class.forName(JDBC_DRIVER);
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            while (!conn.isClosed()) {
                System.out.print("Pilih aksi (tambah/lihat/hapus/keluar): ");
                String action = input.readLine();
                if (action.equals("tambah")) {
                insertBuku();
                } else if (action.equals("lihat")) {
                showBuku();
                } else if (action.equals("hapus")) {
                deleteBuku();
                } else if (action.equals("keluar")) {
                conn.close();
                }
                }
                } catch (Exception e) {
                e.printStackTrace();
                } finally {
                try {
                if (rs != null) {
                rs.close();
                }
                if (stmt != null) {
                stmt.close();
                }
                if (conn != null) {
                conn.close();
                }
                input.close();
                } catch (Exception e) {
                e.printStackTrace();
                }
                }
                }
                }