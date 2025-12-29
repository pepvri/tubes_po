import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BarangDAO {
    
    // Menambah Barang Baru
    public void tambahBarang(Barang barang) throws Exception {
        String sql = "INSERT INTO barang (nama, harga, stok) VALUES (?, ?, ?)";
        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, barang.getNama());
            pstmt.setInt(2, barang.getHarga());
            pstmt.setInt(3, barang.getStok());
            pstmt.executeUpdate();
        }
    }

    // Mengambil Semua Data Barang
    public List<Barang> getAllBarang() throws Exception {
        List<Barang> listBarang = new ArrayList<>();
        String sql = "SELECT * FROM barang";
        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                listBarang.add(new Barang(
                    rs.getInt("id_barang"),
                    rs.getString("nama"),
                    rs.getInt("harga"),
                    rs.getInt("stok")
                ));
            }
        }
        return listBarang;
    }
}