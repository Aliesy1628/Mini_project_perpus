
public class Dosen extends Anggota {

    String nip;
    
    public Dosen(String nama, String id, String nip) {
        super(nama,id);
        this.nip = nip;
    }

    public String getNip() {
        return nip;
    }

    @Override
    public void tampilkanInfo() {
        System.out.println("Nama Dosen: " + nama + " Maks pinjam: " + 
        getMaksHariPinjam() + " hari " + "Denda: Rp" +
        getDendaPerHari() + "/hari");
    }
    
    public void mengajar(Mahasiswa m) { //Asosisasi
        System.out.println(nama + " " + "Mengajar" + " " + m.getNama());
    }

    @Override
    public int getMaksHariPinjam() {
        return 30;
    }

    @Override
    public double getDendaPerHari() {
        return 500.0;
    }

    @Override
    public String toFileString() {
        return "DOSEN;" + id + ";" + nama + ";" + nip;
    }
}
