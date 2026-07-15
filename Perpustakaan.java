
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Perpustakaan {

    private List<Buku> koleksi;
    private List<Anggota> daftarAnggota;
    private List<Transaksi> daftarTransaksi;

    private static  String FILE_BUKU = "data_buku.txt";
    private static String FILE_ANGGOTA = "data_anggota.txt";

    public Perpustakaan() {
        this.koleksi = new ArrayList<>();
        this.daftarAnggota = new ArrayList<>();
        this.daftarTransaksi = new ArrayList<>();
    }

    // Buku
    public void tambahBuku(Buku buku) {
        koleksi.add(buku);
        System.out.println("Buku telah ditambahkan: " + buku);
    }

    public Buku cariBukuByKode(String kode) {
        for (Buku b : koleksi) {
            if (b.getKodeBuku().equalsIgnoreCase(kode)) {
                return b;
            }
        }
        return null;
    }

    public void tampilkanKoleksi() {
        for (Buku b : koleksi) {
            System.out.println(b.getInfo());
        }
    }

    // Anggota
    public void tambahAnggota(Anggota anggota) {
        daftarAnggota.add(anggota);
        System.out.println("Anggota telah ditambahkan: " + anggota);
    }

    public Anggota cariAnggotaById(String id) {
        for (Anggota a : daftarAnggota) {
            if (a.getId().equalsIgnoreCase(id)) {
                return a;
            }
        }
        return null;
    }

    public void tampilkanAnggota() {
        for (Anggota a : daftarAnggota) {
            a.tampilkanInfo();
        }
    }

    //Transaksi
    public boolean pinjamBuku(String kodeBuku, String id) {
        Buku buku = cariBukuByKode(kodeBuku);
        Anggota anggota = cariAnggotaById(id);

        if (buku == null) {
            System.out.println("Buku kode " + kodeBuku + " tidak ditemukan");
            return false;
        }
        if (anggota == null) {
            System.out.println("Anggota dengan id " + id + " tidak ditemukan");
            return false;
        }
        if (!buku.kurangiStok()) {
            System.out.println("Stok buku " + buku.getJudul() + " Habis");
            return false;
        }

        anggota.pinjamBuku(buku);
        Transaksi transaksi = new Transaksi(buku, anggota);
        daftarTransaksi.add(transaksi);
        System.out.println("Peminjaman berhasil dicatat " + transaksi.getIdTransaksi());
        return true;
    }

    public void kembalikanBuku(String idTransaksi) {
        for (Transaksi t : daftarTransaksi) {
            if (t.getIdTransaksi().equalsIgnoreCase(idTransaksi) && !t.isSudahKembali()) {
                double denda = t.kembalikanBuku();
                System.out.println("Buku " + t.getBuku().getJudul() + " Berhasil dikembalikan");
                if (denda > 0) {
                    System.out.println("Mengembalikan terlambat, denda yang harus dibayar: Rp" + denda);
                } else {
                    System.out.println("Tidak terlambat, tidak ada denda");
                }
                return;
            }
        }
        System.out.println("Transaksi dengan id " + idTransaksi + " Tidak ditemukan");
    }

    public void simpanDataKeFile() {
        try (BufferedWriter writerBuku = new BufferedWriter(new FileWriter(FILE_BUKU))) {
            for (Buku b : koleksi) {
                writerBuku.write(b.toFileString());
                writerBuku.newLine();
            }
        }catch (IOException e) {
            System.out.println("Gagal menyimpan data buku: " + e.getMessage());
            return;
        }

        try (BufferedWriter writerAnggota = new BufferedWriter(new FileWriter(FILE_ANGGOTA))) {
            for (Anggota a : daftarAnggota) {
                writerAnggota.write(a.toFileString());
                writerAnggota.newLine();
            }
        } catch (IOException e) {
            System.out.println("Gagal menyimpan data anggota: " + e.getMessage());
            return;
        }

        System.out.println("Data telah disimpan");
    }

    public void muatDataDariFile() {
        try (BufferedReader readerBuku = new BufferedReader(new FileReader(FILE_BUKU))) {
            koleksi.clear();
            String baris;
            while ((baris = readerBuku.readLine()) != null) {
                if (baris.trim().isEmpty()) {
                    continue;
                }
                try {
                    koleksi.add(Buku.fromFileString(baris));
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Baris buku rusak/dilewati: " + baris);
                }
            }
        } catch (IOException e) {
            System.out.println("Belum ada file data buku tersimpan (" + FILE_BUKU + ").");
        }

        try (BufferedReader readerAnggota = new BufferedReader(new FileReader(FILE_ANGGOTA))) {
            daftarAnggota.clear();
            String baris;
            while ((baris = readerAnggota.readLine()) != null) {
                if (baris.trim().isEmpty()) {
                    continue;
                }
                try {
                    String[] bagian = baris.split(";");
                    String tipe = bagian[0];
                    String id = bagian[1];
                    String nama = bagian[2];
                    String infoTambahan = bagian[3];

                    if (tipe.equalsIgnoreCase("MAHASISWA")) { 
                        daftarAnggota.add(new Mahasiswa(nama, id, infoTambahan)); 
                    } else if (tipe.equalsIgnoreCase("DOSEN")) { 
                        daftarAnggota.add(new Dosen(nama, id, infoTambahan));
                    } else {
                        System.out.println("Tipe anggota tidak dikenali, baris dilewati: " + baris);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println("Baris anggota rusak/dilewati: " + baris);
                }
            }
        } catch (IOException e) {
            System.out.println("Belum ada file data anggota tersimpan (" + FILE_ANGGOTA + ").");
        }

        System.out.println("Proses memuat data selesai.");
    }
}
