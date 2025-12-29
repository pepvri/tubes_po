import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AdminDashboard extends JFrame {
    public AdminDashboard() {
        setTitle("APK KASIR - Dashboard Admin");
        setSize(1000, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- SIDEBAR (Panel Biru di Kiri) ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(51, 102, 204));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel logo = new JLabel("APK KASIR");
        logo.setForeground(Color.WHITE);
        logo.setFont(new Font("Arial", Font.BOLD, 20));
        logo.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        sidebar.add(logo);
        sidebar.add(createSidebarButton("Dashboard"));
        sidebar.add(createSidebarButton("Data Petugas"));
        sidebar.add(createSidebarButton("Data Barang"));
        sidebar.add(createSidebarButton("Laporan"));

        // --- MAIN CONTENT ---
        JPanel mainContent = new JPanel(null);
        mainContent.setBackground(Color.WHITE);

        JLabel lblTitle = new JLabel("Dashboard");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitle.setBounds(30, 20, 200, 30);
        mainContent.add(lblTitle);

        // Card Total Petugas
        JPanel cardPetugas = createStatCard("TOTAL PETUGAS", "1", new Color(255, 255, 255));
        cardPetugas.setBounds(30, 70, 250, 100);
        mainContent.add(cardPetugas);

        // Card Total Pendapatan
        JPanel cardPendapatan = createStatCard("TOTAL PENDAPATAN", "Rp 150.700", new Color(255, 255, 255));
        cardPendapatan.setBounds(300, 70, 250, 100);
        mainContent.add(cardPendapatan);

        add(sidebar, BorderLayout.WEST);
        add(mainContent, BorderLayout.CENTER);
    }

    private JButton createSidebarButton(String text) {
        JButton btn = new JButton(text);
        btn.setMaximumSize(new Dimension(200, 40));
        btn.setBackground(new Color(51, 102, 204));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setAlignmentX(Component.LEFT_ALIGNMENT);
        return btn;
    }

    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new GridLayout(2, 1));
        card.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230), 2));
        card.setBackground(color);
        
        JLabel lblTitle = new JLabel(" " + title);
        lblTitle.setForeground(Color.GRAY);
        
        JLabel lblValue = new JLabel(" " + value);
        lblValue.setFont(new Font("Arial", Font.BOLD, 20));

        card.add(lblTitle);
        card.add(lblValue);
        return card;
    }
}