import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Main Menu - Role: " + UserSession.getRole());
        setSize(500, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel lblWelcome = new JLabel("Halo, " + UserSession.getNama() + " (" + UserSession.getRole() + ")", SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Arial", Font.BOLD, 16));
        add(lblWelcome, BorderLayout.NORTH);

        JPanel panelMenu = new JPanel(new GridLayout(2, 2, 10, 10));
        panelMenu.add(new JButton("Kelola Barang"));
        panelMenu.add(new JButton("Transaksi Baru"));
        panelMenu.add(new JButton("Riwayat Pesanan"));
        panelMenu.add(new JButton("Logout"));

        add(panelMenu, BorderLayout.CENTER);
        setLocationRelativeTo(null);
    }
}