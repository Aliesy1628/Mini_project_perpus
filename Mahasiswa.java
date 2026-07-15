

public class Mahasiswa extends Anggota {
    
    private String nim;

    public Mahasiswa(String nama, String id, String nim) {
        super(nama, id);
        this.nim = nim;
    }

    public String getNim() {
        return nim;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Nama Mahasiswa : " + nama + " Maks pinjam: " + 
        getMaksHariPinjam() + " hari " + "Denda: Rp" +
        getDendaPerHari() + "/hari");
    }

    @Override
    public int getMaksHariPinjam() {
        return 7;
    }

    @Override
    public double getDendaPerHari() {
        return 1000.0;
    }

    @Override
    public String toFileString() {
        return "Mahasiswa;" + id + ";" + nama + ";" + nim;
    }
}
