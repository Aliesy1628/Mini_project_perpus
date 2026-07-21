
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaksi {
    
    private static int counter = 0;

    private String idTransaksi;
    private final Buku buku;
    private final Anggota peminjam;
    private final LocalDate tanggalPinjam;
    private LocalDate tanggalKembali;
    private boolean sudahKembali;

    public Transaksi(Buku buku, Anggota peminjam) {
        counter++;
        this.idTransaksi = "TRN" + counter;
        this.buku = buku;
        this.peminjam = peminjam;
        this.tanggalPinjam = LocalDate.now();
        this.sudahKembali = false;
    }

    public String getIdTransaksi() {
        return idTransaksi;
    }

    public Buku getBuku() {
        return buku;
    }

    public Anggota getPeminjam() {
        return peminjam;
    }

    public LocalDate getTanggalPinjam() {
        return tanggalPinjam;
    }

    public boolean isSudahKembali() {
        return sudahKembali;
    }

    public double kembalikanBuku() {
        this.tanggalKembali = LocalDate.now();
        this.sudahKembali = true;
        buku.tambahStok();

        long lamaPinjam = ChronoUnit.DAYS.between(tanggalPinjam, tanggalKembali);
        long maksHari = peminjam.getMaksHariPinjam();
        long hariTerlambat = lamaPinjam - maksHari;

        return peminjam.hitungDenda(hariTerlambat);
    }

    public static Transaksi fromFileString(String baris, Perpustakaan perpustakaan) {
        String[] bagian = baris.split(";");

        String idTransaksi = bagian[0].trim();
        String judul = bagian[1].trim();
        String kodeBuku = bagian[6].trim();
        String namaPeminjam = bagian[2].trim();
        String tipeTerpilih = bagian[5].trim();

        Buku buku = perpustakaan.cariBukuByKode(kodeBuku);
        if (buku == null) {
            buku = perpustakaan.cariBukuByJudul(judul);
        }
        if (buku == null) {
            buku = new Buku(kodeBuku, judul, 1);
            perpustakaan.getKoleksi().add(buku);
        }
        
        Anggota peminjam;
        if (tipeTerpilih.equals("1")) {
            peminjam = new Mahasiswa(namaPeminjam, "", "");
        } else {
            peminjam = new Dosen(namaPeminjam, "", "");
        }

            Transaksi t = new Transaksi(buku, peminjam);
            t.idTransaksi = idTransaksi;  

        return t;
    }

    

    @Override
    public String toString() {
        return "[" + idTransaksi + "] ";
    }

    public String toFileString() {
        String angkaTipe = (peminjam instanceof Mahasiswa) ? "1" : "2";
        return idTransaksi + ";" + buku.getJudul() + ";" + peminjam.getNama() + ";" 
            + tanggalPinjam + ";" + sudahKembali + ";" + angkaTipe + ";" + buku.getKodeBuku();
    }

    public String tampilkanInfo() {
        return "ID Transaksi: " + idTransaksi + ", " + "Sudah selesai dikembalikan : " + sudahKembali;
    }
}
