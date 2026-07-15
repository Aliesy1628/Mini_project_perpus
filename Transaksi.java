
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Transaksi {
    
    private static int counter = 0;

    private String idTransaksi;
    private Buku buku;
    private Anggota peminjam;
    private LocalDate tanggalPinjam;
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

}
