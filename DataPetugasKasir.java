import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DataPetugasKasir extends JFrame {

    DefaultTableModel model;
    JTable table;

    public DataPetugasKasir() {
        setTitle("APK Kasir - Data Petugas");
        setSize(1100, 650);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        /* ================= SIDEBAR ================= */
        JPanel sidebar = new JPanel();
        sidebar.setPreferredSize(new Dimension(220, 0));
        sidebar.setBackground(new Color(78,115,223));
        sidebar.setLayout(new BoxLayout(sidebar, BoxLayout.Y_AXIS));

        JLabel app = new JLabel("APK KASIR");
        app.setForeground(Color.WHITE);
        app.setFont(new Font("Arial", Font.BOLD, 20));
        app.setAlignmentX(Component.CENTER_ALIGNMENT);
        app.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));

        sidebar.add(app);
        sidebar.add(menuBtn("Dashboard"));
        sidebar.add(menuBtn("Data Petugas"));
        sidebar.add(menuBtn("Data Menu"));
        sidebar.add(menuBtn("Laporan"));

        /* ================= CONTENT ================= */
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Data Petugas");
        title.setFont(new Font("Arial", Font.PLAIN, 24));

        JButton tambah = new JButton("Tambah Data+");
        tambah.setBackground(new Color(78,115,223));
        tambah.setForeground(Color.WHITE);
        tambah.setMaximumSize(new Dimension(140,32));

        JPanel header = new JPanel();
        header.setLayout(new BoxLayout(header, BoxLayout.Y_AXIS));
        header.setBackground(Color.WHITE);
        header.add(title);
        header.add(Box.createVerticalStrut(10));
        header.add(tambah);

        model = new DefaultTableModel(
                new String[]{"No","Nama","Username","Password","Alamat","Role","Aksi"}, 0);

        table = new JTable(model);
        table.setRowHeight(28);

        JScrollPane scroll = new JScrollPane(table);

        content.add(header, BorderLayout.NORTH);
        content.add(scroll, BorderLayout.CENTER);

        add(sidebar, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        /* ================= ACTION ================= */
        tambah.addActionListener(e -> modalTambah());

        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                int row = table.getSelectedRow();
                if (table.getSelectedColumn() == 6) {
                    Object[] opsi = {"Edit","Hapus"};
                    int pilih = JOptionPane.showOptionDialog(
                            null,"Pilih Aksi","Aksi",
                            JOptionPane.DEFAULT_OPTION,
                            JOptionPane.INFORMATION_MESSAGE,
                            null,opsi,opsi[0]);

                    if (pilih == 0) modalEdit(row);
                    if (pilih == 1) {
                        model.removeRow(row);
                        resetNo();
                    }
                }
            }
        });
    }

    /* ================= MODAL TAMBAH ================= */
    void modalTambah() {
        JDialog d = modalBase("Tambah Petugas", 450, 420);

        JTextField nama = new JTextField();
        JTextField user = new JTextField();
        JTextField pass = new JTextField();
        JTextArea alamat = new JTextArea(3,20);

        JButton simpan = modalButton("Simpan", new Color(78,115,223));
        simpan.addActionListener(e -> {
            model.addRow(new Object[]{
                    model.getRowCount()+1,
                    nama.getText(),
                    user.getText(),
                    pass.getText(),
                    alamat.getText(),
                    "Petugas",
                    "Edit | Hapus"
            });
            d.dispose();
        });

        buildForm(d, nama, user, pass, alamat, simpan);
    }

    /* ================= MODAL EDIT ================= */
    void modalEdit(int row) {
        JDialog d = modalBase("Edit Data Petugas", 450, 440);

        JTextField nama = new JTextField(model.getValueAt(row,1).toString());
        JTextField user = new JTextField(model.getValueAt(row,2).toString());
        JTextField pass = new JTextField();
        JTextArea alamat = new JTextArea(model.getValueAt(row,4).toString(),3,20);

        JButton simpan = modalButton("Simpan Perubahan", new Color(246,194,62));
        simpan.addActionListener(e -> {
            model.setValueAt(nama.getText(),row,1);
            model.setValueAt(user.getText(),row,2);
            if (!pass.getText().isEmpty())
                model.setValueAt(pass.getText(),row,3);
            model.setValueAt(alamat.getText(),row,4);
            d.dispose();
        });

        buildForm(d, nama, user, pass, alamat, simpan);
    }

    /* ================= MODAL BASE ================= */
    JDialog modalBase(String title, int w, int h) {
        JDialog d = new JDialog(this, title, true);
        d.setSize(w, h);
        d.setLocationRelativeTo(this);
        d.setResizable(false);
        d.setLayout(new BorderLayout());

        JLabel judul = new JLabel(title);
        judul.setFont(new Font("Arial", Font.BOLD, 18));
        judul.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));

        JPanel top = new JPanel(new BorderLayout());
        top.add(judul, BorderLayout.WEST);

        d.add(top, BorderLayout.NORTH);
        return d;
    }

    /* ================= FORM ================= */
    void buildForm(JDialog d, JTextField nama, JTextField user,
                   JTextField pass, JTextArea alamat, JButton btn) {

        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,20,6,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        c.gridy = 0; form.add(new JLabel("Nama"), c);
        c.gridy++; form.add(nama, c);

        c.gridy++; form.add(new JLabel("Username"), c);
        c.gridy++; form.add(user, c);

        c.gridy++; form.add(new JLabel("Password"), c);
        c.gridy++; form.add(pass, c);

        c.gridy++; form.add(new JLabel("Alamat"), c);
        c.gridy++;
        JScrollPane sp = new JScrollPane(alamat);
        form.add(sp, c);

        c.gridy++;
        btn.setPreferredSize(new Dimension(160,35));
        form.add(btn, c);

        d.add(form, BorderLayout.CENTER);
        d.setVisible(true);
    }

    JButton modalButton(String text, Color color) {
        JButton b = new JButton(text);
        b.setBackground(color);
        b.setForeground(Color.WHITE);
        return b;
    }

    JButton menuBtn(String text) {
        JButton b = new JButton(text);
        b.setMaximumSize(new Dimension(200,40));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBackground(new Color(78,115,223));
        b.setForeground(Color.WHITE);
        b.setBorderPainted(false);
        return b;
    }

    void resetNo() {
        for (int i = 0; i < model.getRowCount(); i++)
            model.setValueAt(i+1, i, 0);
    }

    public static void main(String[] args) {
        new DataPetugasKasir().setVisible(true);
    }
}
