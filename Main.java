
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Perpustakaan perpustakaan = new Perpustakaan();

        boolean berjalan = true;

        perpustakaan.muatDataDariFile();

        while (berjalan) {
            tampilkanMenu();
            int pilihan;

            try {
                pilihan = Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Input tidak valid, masukkan angka menu yang benar.\n");
                continue;
            }

            switch (pilihan) {
                    case 1:
                        tambahBuku(scanner, perpustakaan);
                        break;
                    case 2:
                        tambahAnggota(scanner, perpustakaan);
                        break;
                    case 3:
                        pinjamBuku(scanner, perpustakaan);
                        break;
                    case 4:
                        kembalikanBuku(scanner, perpustakaan);
                        break;
                    case 5:
                        perpustakaan.tampilkanKoleksi();
                        break;
                    case 6:
                        perpustakaan.tampilkanAnggota();
                        break;
                    case 7:
                        perpustakaan.tampilkanTransaksi();
                        break;
                    case 8:
                        perpustakaan.simpanDataKeFile();
                        break;
                    case 9:
                        perpustakaan.muatDataDariFile();
                        break;
                    case 0:
                        berjalan = false;
                        System.out.println("Terima kasih, sampai jumpa!");
                        break;
                    default:
                        System.out.println("Menu tidak dikenali, coba lagi.");
                }
                System.out.println();
            }

        scanner.close();
        }

    private static void tampilkanMenu() {
        System.out.println("=== SISTEM PERPUSTAKAAN STEIN ===");
        System.out.println("1. Tambah Buku");
        System.out.println("2. Tambah Anggota");
        System.out.println("3. Pinjam Buku");
        System.out.println("4. Kembalikan Buku (hitung denda)");
        System.out.println("5. Tampilkan Semua Buku");
        System.out.println("6. Tampilkan Semua Anggota");
        System.out.println("7. Tampilkan Semua Transaksi");
        System.out.println("8. Simpan Data ke File");
        System.out.println("9. Muat Data dari File");
        System.out.println("0. Keluar");
        System.out.print("Pilih menu: ");
    }

    private static void tambahBuku(Scanner scanner, Perpustakaan perpustakaan) {
        System.out.print("Kode buku: ");
        String kode = scanner.nextLine().trim();
        System.out.print("Judul buku: ");
        String judul = scanner.nextLine().trim();
        System.out.print("Stok: ");

        int stok;
        try {
            stok = Integer.parseInt(scanner.nextLine().trim());
        } catch (NumberFormatException e) {
            System.out.println("Stok harus angka, buku disimpan dengan stok 0.");
            stok = 0;
        }

        perpustakaan.tambahBuku(new Buku(kode, judul, stok));
    }

     private static void tambahAnggota(Scanner scanner, Perpustakaan perpustakaan) {
        System.out.print("Tipe anggota (1=Mahasiswa, 2=Dosen): ");
        String tipe = scanner.nextLine().trim();

        System.out.print("ID anggota: ");
        String id = scanner.nextLine().trim();
        System.out.print("Nama: ");
        String nama = scanner.nextLine().trim();

        if (tipe.equals("1")) {
            System.out.print("NIM: ");
            String nim = scanner.nextLine().trim();
            perpustakaan.tambahAnggota(new Mahasiswa(nama, id, nim));
        } else if (tipe.equals("2")) {
            System.out.print("NIP: ");
            String nip = scanner.nextLine().trim();
            perpustakaan.tambahAnggota(new Dosen(nama, id, nip));
        } else {
            System.out.println("Tipe tidak dikenali, anggota tidak ditambahkan.");
        }
    }

     private static void pinjamBuku(Scanner scanner, Perpustakaan perpustakaan) {
        System.out.print("Kode buku yang dipinjam: ");
        String kodeBuku = scanner.nextLine().trim();
        System.out.print("ID anggota peminjam: ");
        String idAnggota = scanner.nextLine().trim();

        perpustakaan.pinjamBuku(kodeBuku, idAnggota);
    }

    private static void kembalikanBuku(Scanner scanner, Perpustakaan perpustakaan) {
        System.out.print("ID transaksi yang dikembalikan (contoh: TRN1): ");
        String idTransaksi = scanner.nextLine().trim();
        perpustakaan.kembalikanBuku(idTransaksi);
    }
}