package com.example.apap.tugas1.demo.Controller;

import com.example.apap.tugas1.demo.Model.*;
import com.example.apap.tugas1.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.transform.Result;
import java.util.List;

@Controller
public class PendudukController {

    @Autowired
    PendudukService pendudukService;

    @Autowired
    KeluargaService keluargaService;

    @Autowired
    KelurahanService kelurahanService;

    @Autowired
    KecamatanService kecamatanService;

    @Autowired
    KotaService kotaService;

    @GetMapping("/penduduk")
    public String cariNik(@RequestParam(value = "nik", required = false) String nik, Model model){
        if(nik == null){
            model.addAttribute("error", "Masukkan input NIK");
            return "view/error";
        }

        Penduduk penduduk = pendudukService.selectPenduduk(nik);
        if(penduduk == null){
            model.addAttribute("error", "NIK tidak ditemukan");
            return "view/error";
        }

        Keluarga keluarga = keluargaService.selectKeluargaByID(penduduk.getId_keluarga());
        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());
        Kecamatan kecamatan = kecamatanService.selectKecamatan(kelurahan.getId_kecamatan());
        Kota kota = kotaService.selectKota(kecamatan.getId_kota());

        model.addAttribute("penduduk", penduduk);
        model.addAttribute("keluarga", keluarga);
        model.addAttribute("kelurahan", kelurahan);
        model.addAttribute("kecamatan", kecamatan);
        model.addAttribute("kota", kota);

        if(penduduk.getJenis_kelamin().equals("1")){
            penduduk.setJenis_kelamin("Perempuan");
        } else{
            penduduk.setJenis_kelamin("Laki-laki");
        }

        if(penduduk.getIs_wni().equals("1")){
            penduduk.setIs_wni("WNI");
        }
        else{
            penduduk.setIs_wni("WNA");
        }

        if(penduduk.getIs_wafat().equals("1")){
            penduduk.setIs_wafat("Mati");
        }
        else{
            penduduk.setIs_wafat("Hidup");
        }

        return "view/penduduk";
    }

    @PostMapping("/penduduk/tambah")
    public String tambahPendudukSubmit(@Valid @ModelAttribute Penduduk penduduk, Model model, BindingResult result){

        if(result.hasErrors()){
            model.addAttribute("error", result.getModel().toString());
            return "view/error";
        }

        Keluarga keluarga = keluargaService.selectKeluargaByID(penduduk.getId_keluarga());

        if(keluarga == null){
            model.addAttribute("error", "ID Keluarga tidak ditemukan");
            return "view/error";
        }

        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());
        Kecamatan kecamatan = kecamatanService.selectKecamatan(kelurahan.getId_kecamatan());

        String[] tglLahir = penduduk.getTanggal_lahir().split("-");
        if(penduduk.getJenis_kelamin().equals("1")){
            tglLahir[2] = (Integer.parseInt(tglLahir[2]) + 40) + "";
        }
        String nik = kecamatan.getKode_kecamatan().substring(0,6) + tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2);

        List<Penduduk> similar = pendudukService.selectSimilarNIK(nik + "%");
        String nomor = "0001";
        if(similar.size() > 0){
            int nomorSimilar = Integer.parseInt(similar.get(similar.size()-1).getNik().substring(12)) + 1;
            nomor = nomorSimilar + "";
        }

        int counter = 4 - nomor.length();
        for(int i = 0; i < counter; i++){
            nomor = "0" + nomor;
        }

        nik = nik + nomor;
        penduduk.setNik(nik);

        List<Penduduk> pendudukList = pendudukService.selectAllPenduduk();
        penduduk.setId("" + (pendudukList.size() + 1));;

        pendudukService.addPenduduk(penduduk);

        model.addAttribute("confirmation", "Penduduk dengan NIK " + nik + " berhasil ditambahkan");

        return "view/confirmation";
    }

    @GetMapping("/penduduk/tambah")
    public String tambahPenduduk(Model model){

        Penduduk penduduk = new Penduduk();
        penduduk.setGolongan_darah("");
        penduduk.setJenis_kelamin("");
        penduduk.setIs_wni("");
        penduduk.setStatus_perkawinan("");
        penduduk.setIs_wafat("");

        model.addAttribute("penduduk",penduduk);

        return "view/registrasiPenduduk";
    }

    @GetMapping("/penduduk/ubah/{nik}")
    public String ubahPenduduk(@PathVariable(value = "nik") String nik, Model model){
        Penduduk penduduk = pendudukService.selectPenduduk(nik);

        if(penduduk != null){
            penduduk.setGolongan_darah(penduduk.getGolongan_darah().substring(0,1));
            model.addAttribute("penduduk", penduduk);
        } else {
            model.addAttribute("error", "NKK tidak ditemukan");
            return "view/error";
        }

        return "view/updatePenduduk";
    }

    @PostMapping("/penduduk/ubah/{nik}")
    public String ubahPendudukSubmit(@PathVariable(value = "nik") String nikLama, @Valid @ModelAttribute Penduduk penduduk, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("error", result.getModel().toString());
            return "view/error";
        }

        Penduduk penduduklama = pendudukService.selectPenduduk(nikLama);

        Keluarga keluarga = keluargaService.selectKeluargaByID(penduduk.getId_keluarga());
        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());
        Kecamatan kecamatan = kecamatanService.selectKecamatan(kelurahan.getId_kecamatan());

        String[] tglLahir = penduduk.getTanggal_lahir().split("-");
        if(penduduk.getJenis_kelamin().equals("1")){
            tglLahir[2] = (Integer.parseInt(tglLahir[2]) + 40) + "";
        }String nik = kecamatan.getKode_kecamatan().substring(0,6) + tglLahir[2] + tglLahir[1] + tglLahir[0].substring(2);

        List<Penduduk> similar = pendudukService.selectSimilarNIK(nik + "%");
        String nomor = "0001";
        if(similar.size() > 0){
            int nomorSimilar = Integer.parseInt(similar.get(similar.size()-1).getNik().substring(12)) + 1;
            nomor = nomorSimilar + "";
        }

        int counter = 4 - nomor.length();
        for(int i = 0; i < counter; i++){
            nomor = "0" + nomor;
        }

        nik = nik + nomor;
        penduduk.setNik(nik);

        penduduk.setId(penduduklama.getId());

        pendudukService.updatePenduduk(penduduk);

        model.addAttribute("confirmation", "Penduduk dengan NIK " + nikLama + " berhasil diubah");

        return "view/confirmation";
    }

    @PostMapping("/penduduk/mati")
    public String nonaktifPenduduk(@RequestParam(value="nik") String nik, Model model){

        pendudukService.nonaktifkanPenduduk(nik);

        Penduduk penduduk = pendudukService.selectPenduduk(nik);
        Keluarga keluarga = keluargaService.selectKeluargaByID(penduduk.getId_keluarga());

        keluarga.setIs_tidak_berlaku("1");
        List<Penduduk> anggotaKeluarga = pendudukService.selectAnggotaKeluarga(keluarga.getId());

        if(anggotaKeluarga.size() == 0){
            keluarga.setIs_tidak_berlaku("0");

        }else{
            for(int i = 0 ; i < anggotaKeluarga.size(); i++){
                if(anggotaKeluarga.get(i).getIs_wafat().equals("0")){
                    keluarga.setIs_tidak_berlaku("0");
                }
            }
        }

        keluargaService.updateKeluarga(keluarga);

        model.addAttribute("confirmation", "Penduduk dengan NIK " + nik + " sudah tidak aktif");
        model.addAttribute("mati", nik);

        return "view/confirmation";
    }


    @GetMapping("/penduduk/cari")
    public String cariPenduduk(@RequestParam(value="kt", required=false) String kota,
                               @RequestParam(value="kc", required=false) String kecamatan,
                               @RequestParam(value="kl", required=false) String kelurahan,
                               Model model){
        if(kota == null){
            model.addAttribute("kota", kotaService.selectAllKota());
            return "view/cariPendudukKota";
        } else{
            if(kecamatan == null){
                model.addAttribute("kota", kotaService.selectKota(kota));
                model.addAttribute("kecamatan", kecamatanService.selectAllKecamatan(kota));
                return "view/cariPendudukKecamatan";
            } else{
                if(kelurahan == null){
                    model.addAttribute("kota", kotaService.selectKota(kota));
                    model.addAttribute("kecamatan", kecamatanService.selectKecamatan(kecamatan));
                    model.addAttribute("kelurahan", kelurahanService.selectAllKelurahan(kecamatan));
                    return "view/cariPendudukKelurahan";
                }
            }
        }

        List<Penduduk> pendudukList = pendudukService.selectPendudukByKelurahan(kelurahan);
        Penduduk muda = pendudukList.get(0);
        Penduduk tua = pendudukList.get(0);
        for(int i = 0 ; i< pendudukList.size(); i++){
            if(pendudukList.get(i).getJenis_kelamin().equals("1")){
                pendudukList.get(i).setJenis_kelamin("Perempuan");
            } else{
                pendudukList.get(i).setJenis_kelamin("Laki-laki");
            }

            if(muda.getTanggal_lahir().compareTo(pendudukList.get(i).getTanggal_lahir()) < 0){
                muda = pendudukList.get(i);
            }

            if(tua.getTanggal_lahir().compareTo(pendudukList.get(i).getTanggal_lahir()) > 0){
                tua = pendudukList.get(i);
            }
        }

        model.addAttribute("tua", tua);
        model.addAttribute("muda", muda);
        model.addAttribute("penduduk", pendudukList);

        model.addAttribute("kota", kotaService.selectKota(kota));
        model.addAttribute("kecamatan", kecamatanService.selectKecamatan(kecamatan));
        model.addAttribute("kelurahan", kelurahanService.selectKelurahan(kelurahan));

        return "view/hasilPencarian";
    }
}
