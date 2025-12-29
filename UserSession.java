public class UserSession {
    private static int idUser;
    private static String nama;
    private static String roleName;

    // Getter dan Setter static agar bisa diakses dari semua screen GUI
    public static void setUser(int id, String n, String r) {
        idUser = id; nama = n; roleName = r;
    }
    public static String getNama() { return nama; }
    public static String getRole() { return roleName; }
}