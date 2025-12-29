public class Barang {
    private int id_barang;
    private String nama;
    private int harga;
    private int stok;

    public Barang(int id, String nama, int harga, int stok) {
        this.id_barang = id;
        this.nama = nama;
        this.harga = harga;
        this.stok = stok;
    }

    // Tambahkan method ini untuk menghilangkan garis merah di VS Code
    public String getNama() { return nama; }
    public int getHarga() { return harga; }
    public int getStok() { return stok; }
}