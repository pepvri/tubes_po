import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;

public class DataBarangKasir extends JFrame {

    DefaultTableModel model;
    JTable table;

    public DataBarangKasir() {
        setTitle("APK Kasir - Data Barang");
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
        sidebar.add(menuBtn("Data Barang"));
        sidebar.add(menuBtn("Laporan"));

        /* ================= CONTENT ================= */
        JPanel content = new JPanel(new BorderLayout());
        content.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        JLabel title = new JLabel("Data Barang");
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
                new String[]{"No","Nama Barang","Harga","Stok","Jenis Barang","Aksi"}, 0);

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
                if (table.getSelectedColumn() == 5) {
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
        JDialog d = modalBase("Tambah Barang", 450, 420);

        JTextField nama = new JTextField();
        JTextField harga = new JTextField();
        JTextField stok = new JTextField();
        JComboBox<String> jenis = new JComboBox<>(new String[]{"Pilih Jenis","Makanan","Minuman"});

        JButton simpan = modalButton("Simpan", new Color(78,115,223));
        simpan.addActionListener(e -> {
            if (jenis.getSelectedIndex() == 0) {
                JOptionPane.showMessageDialog(d, "Pilih jenis barang!");
                return;
            }

            model.addRow(new Object[]{
                    model.getRowCount()+1,
                    nama.getText(),
                    harga.getText(),
                    stok.getText(),
                    jenis.getSelectedItem(),
                    "Edit | Hapus"
            });
            d.dispose();
        });

        buildForm(d,
                new String[]{"Nama Barang","Harga","Stok","Jenis Barang"},
                new JComponent[]{nama, harga, stok, jenis},
                simpan);
    }

    /* ================= MODAL EDIT ================= */
    void modalEdit(int row) {
        JDialog d = modalBase("Edit Data Barang", 450, 440);

        JTextField nama = new JTextField(model.getValueAt(row,1).toString());
        JTextField harga = new JTextField(model.getValueAt(row,2).toString());
        JTextField stok = new JTextField(model.getValueAt(row,3).toString());
        JComboBox<String> jenis = new JComboBox<>(new String[]{"Makanan","Minuman"});
        jenis.setSelectedItem(model.getValueAt(row,4).toString());

        JButton simpan = modalButton("Simpan Perubahan", new Color(246,194,62));
        simpan.addActionListener(e -> {
            model.setValueAt(nama.getText(), row, 1);
            model.setValueAt(harga.getText(), row, 2);
            model.setValueAt(stok.getText(), row, 3);
            model.setValueAt(jenis.getSelectedItem(), row, 4);
            d.dispose();
        });

        buildForm(d,
                new String[]{"Nama Barang","Harga","Stok","Jenis Barang"},
                new JComponent[]{nama, harga, stok, jenis},
                simpan);
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

    /* ================= FORM BUILDER ================= */
    void buildForm(JDialog d, String[] labels, JComponent[] fields, JButton btn) {
        JPanel form = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6,20,6,20);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;

        for (int i = 0; i < labels.length; i++) {
            c.gridy++;
            form.add(new JLabel(labels[i]), c);
            c.gridy++;
            form.add(fields[i], c);
        }

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
        new DataBarangKasir().setVisible(true);
    }
}
