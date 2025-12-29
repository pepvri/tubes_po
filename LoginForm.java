import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class LoginForm extends JFrame {
    private JTextField txtUsername = new JTextField(20);
    private JPasswordField txtPassword = new JPasswordField(20);
    private JButton btnLogin = new JButton("Login");

    public LoginForm() {
        setTitle("Login System - Tubes PO1");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel(" Username:"));
        add(txtUsername);
        add(new JLabel(" Password:"));
        add(txtPassword);
        add(new JLabel(""));
        add(btnLogin);

        btnLogin.addActionListener(e -> prosesLogin());
        setLocationRelativeTo(null);
    }

    private void prosesLogin() {
        String user = txtUsername.getText();
        String pass = new String(txtPassword.getPassword());

        try (Connection conn = DatabaseConfig.getConnection()) {
            // Join dengan tabel role untuk tahu hak aksesnya
            String sql = "SELECT u.*, r.nama_role FROM user u " +
                    "JOIN role r ON u.id_role = r.id_role " +
                    "WHERE u.username=? AND u.password=?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user);
            ps.setString(2, pass);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Simpan sesi user
                UserSession.setUser(rs.getInt("id_user"), rs.getString("nama"), rs.getString("nama_role"));

                JOptionPane.showMessageDialog(this, "Selamat Datang, " + UserSession.getNama());

                // Buka Menu Utama dan tutup form login
                new MainMenu().setVisible(true);
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Username atau Password Salah!");
            }
            int roleId = rs.getInt("id_role");
            if (roleId == 1) {
                new AdminDashboard().setVisible(true);
                this.dispose();
            } else {
                // Tampilan untuk Kasir (Role 2)
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginForm().setVisible(true));
    }
}