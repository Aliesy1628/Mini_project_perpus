public class Buku {
    
    private String kodeBuku;
    private String judul;
    int stok;

    //Overloading 1
    public Buku(String kodeBuku, String judul) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.stok = 0;
    }

    //Overloading 2
    public Buku(String kodeBuku, String judul, int stok) {
        this.kodeBuku = kodeBuku;
        this.judul = judul;
        this.stok = stok;
    }

    public String getKodeBuku() {
        return kodeBuku;
    }

    public String getJudul() {
        return judul;
    }

    public int getStok() {
        return stok;
    }

    public void setJudul() {
        this.judul = judul;
    }

    public void setStok(int stok) {
        if (stok < 0) {
            this.stok = 0;
        } else {
            this.stok = stok;
        }
    }

    public boolean kurangiStok() {
        if (stok > 0) {
            stok --;
            return true;
        } return false;
    }

    public void tambahStok() {
        stok++;
    }

    public String getInfo() {
        return "Kode Buku: " + kodeBuku + " Judul: " + judul + ", Stok: " + stok;
    }

    public String toFileString() {
        return kodeBuku + ";" + judul + ";" + stok; 
    }

    public static Buku fromFileString(String baris) {
        String[] bagian = baris.split(";");
        String kode = bagian[0];
        String judul = bagian[1];
        int stok = Integer.parseInt(bagian[2]);
        return new Buku(kode, judul, stok);
    }

    @Override
    public String toString() {
        return "[" + kodeBuku + "] " + judul + " - " + " (stok: " + stok + ")";
    }
}