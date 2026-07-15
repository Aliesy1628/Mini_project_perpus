

public abstract class Anggota implements Peminjam {

    protected String nama;
    protected String id;

    public Anggota(String nama, String id) {
        this.nama = nama;
        this.id = id;
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public abstract void tampilkanInfo();

    public abstract int getMaksHariPinjam();

    public abstract double getDendaPerHari();

    @Override
    public void pinjamBuku(Buku buku) {
        System.out.println(nama + " meminjam buku: " + buku.getJudul());
    }

    @Override
    public double hitungDenda(long HariTerlambat) {
        if (HariTerlambat <= 0) {
            return 0;
        } else {
            return HariTerlambat * getDendaPerHari();
        }
    }

    public abstract String toFileString();

    @Override
    public String toString() {
        return "[" + id + "] " + nama;
    }
}
