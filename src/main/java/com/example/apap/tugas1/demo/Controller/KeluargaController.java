package com.example.apap.tugas1.demo.Controller;

import com.example.apap.tugas1.demo.Model.*;
import com.example.apap.tugas1.demo.Service.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.naming.Binding;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class KeluargaController {

    @Autowired
    KeluargaService keluargaService;

    @Autowired
    PendudukService pendudukService;

    @Autowired
    KelurahanService kelurahanService;

    @Autowired
    KecamatanService kecamatanService;

    @Autowired
    KotaService kotaService;

    @GetMapping("/keluarga")
    public String cariNkk(@RequestParam(value = "nkk", required = false) String nkk, Model model){

        if(nkk == null){
            model.addAttribute("error", "Masukkan input nkk");
            return "view/error";
        }

        Keluarga keluarga = keluargaService.selectKeluarga(nkk);

        if(keluarga == null){
            model.addAttribute("error", "NKK tidak ditemukan");
            return "view/error";
        }

        List<Penduduk> anggota = pendudukService.selectAnggotaKeluarga(keluarga.getId());
        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());
        Kecamatan kecamatan = kecamatanService.selectKecamatan(kelurahan.getId_kecamatan());
        Kota kota = kotaService.selectKota(kecamatan.getId_kota());

        model.addAttribute("keluarga", keluarga);
        model.addAttribute("anggota", anggota);
        model.addAttribute("kelurahan", kelurahan);
        model.addAttribute("kecamatan", kecamatan);
        model.addAttribute("kota", kota);

        for(int i = 0 ; i < anggota.size(); i++){
            if(anggota.get(i).getIs_wni().equals("1")){
                anggota.get(i).setIs_wni("WNI");
            }
            else{
                anggota.get(i).setIs_wni("WNA");
            }

            if(anggota.get(i).getJenis_kelamin().equals("1")){
                anggota.get(i).setJenis_kelamin("Perempuan");
            }
            else{
                anggota.get(i).setJenis_kelamin("Laki-laki");
            }
        }

        return "view/keluarga";
    }

    @GetMapping("/keluarga/tambah")
    public String tambahKeluarga(Model model){
        Keluarga keluarga = new Keluarga();
        keluarga.setId_kelurahan("");
        model.addAttribute("keluarga", keluarga);
        List<Kelurahan> kelurahan = kelurahanService.selectAllKelurahan();
        List<Kecamatan> kecamatan = kecamatanService.selectAllKecamatan();
        List<Kota> kota = kotaService.selectAllKota();
        for(int i = 0; i < kelurahan.size(); i++){
            for(int j = 0; j < kecamatan.size(); j++){
                for(int k = 0 ; k < kota.size(); k++){
                    if(kelurahan.get(i).getId_kecamatan().equals(kecamatan.get(j).getId())){
                        if(kecamatan.get(j).getId_kota().equals(kota.get(k).getId())){
                            String namaKelurahan = kota.get(k).getNama_kota()+
                                    " / " + kecamatan.get(j).getNama_kecamatan() +
                                    " / " + kelurahan.get(i).getNama_kelurahan();
                            kelurahan.get(i).setNama_kelurahan(namaKelurahan);
                        }
                    }
                }
            }
        }

        model.addAttribute("kelurahan", kelurahan);

        return "view/registrasiKeluarga";
    }

    @PostMapping("/keluarga/tambah")
    public String tambahKeluargaSubmit(@Valid @ModelAttribute Keluarga keluarga, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("error", result.getModel().toString());
            return "view/error";
        }

        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());

        String nkk = kelurahan.getKode_kelurahan().substring(0,6);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate localDate = LocalDate.now();

        String[] date = dtf.format(localDate).split("/");
        nkk += date[2] + date[1] + date[0].substring(2);

        List<Keluarga> similar = keluargaService.selectSimilarNKK(nkk + "%");
        String nomor = "0001";
        if(similar.size() > 0){
            int nomorSimilar = Integer.parseInt(similar.get(similar.size()-1).getNomor_kk().substring(12)) + 1;
            nomor = nomorSimilar + "";
        }

        int counter = 4 - nomor.length();
        for(int i = 0; i < counter; i++){
            nomor = "0" + nomor;
        }

        nkk += nomor;
        keluarga.setNomor_kk(nkk);
        keluarga.setIs_tidak_berlaku("0");

        List<Keluarga> keluargaList = keluargaService.selectAllKeluarga();
        keluarga.setId("" + (keluargaList.size() + 1));;

        String rt = keluarga.getRt();
        counter = 3 - rt.length();
        for(int i = 0; i < counter; i++){
            rt = "0" + rt;
        }

        String rw = keluarga.getRw();
        counter = 3 - rw.length();
        for(int i = 0; i < counter; i++){
            rw = "0" + rw;
        }

        System.out.println(rt + rw);

        keluarga.setRt(rt);
        keluarga.setRw(rw);

        keluargaService.addKeluarga(keluarga);

        model.addAttribute("confirmation", "Keluarga dengan NKK " + nkk + " berhasil ditambahkan");

        return "view/confirmation";
    }

    @GetMapping("/keluarga/ubah/{nkk}")
    public String ubahKeluarga(@PathVariable(value = "nkk") String nkk, Model model){

        Keluarga keluarga = keluargaService.selectKeluarga(nkk);

        if(keluarga != null){
            model.addAttribute("keluarga", keluarga);
        }
        else{
            model.addAttribute("error", "NKK tidak temukan");
            return "view/error";
        }

        List<Kelurahan> kelurahan = kelurahanService.selectAllKelurahan();
        List<Kecamatan> kecamatan = kecamatanService.selectAllKecamatan();
        List<Kota> kota = kotaService.selectAllKota();

        for(int i = 0; i < kelurahan.size(); i++){
            for(int j = 0; j < kecamatan.size(); j++){
                for(int k = 0 ; k < kota.size(); k++){
                    if(kelurahan.get(i).getId_kecamatan().equals(kecamatan.get(j).getId())){
                        if(kecamatan.get(j).getId_kota().equals(kota.get(k).getId())){
                            String namaKelurahan = kota.get(k).getNama_kota()+
                                    " / " + kecamatan.get(j).getNama_kecamatan() +
                                    " / " + kelurahan.get(i).getNama_kelurahan();
                            kelurahan.get(i).setNama_kelurahan(namaKelurahan);
                        }
                    }
                }
            }
        }

        model.addAttribute("kelurahan", kelurahan);


        return "view/updateKeluarga";
    }

    @PostMapping("/keluarga/ubah/{nkk}")
    public String ubahKeluargaSubmit(@PathVariable(value="nkk") String nkkLama, @Valid @ModelAttribute Keluarga keluarga, BindingResult result, Model model){

        if(result.hasErrors()){
            model.addAttribute("error", result.getModel().toString());
            return "view/error";
        }

        Keluarga keluargaLama = keluargaService.selectKeluarga(nkkLama);
        Kelurahan kelurahan = kelurahanService.selectKelurahan(keluarga.getId_kelurahan());

        if(keluarga.getId_kelurahan().equals(keluargaLama.getId_kelurahan())){
            String nkk = kelurahan.getKode_kelurahan().substring(0,6);

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            LocalDate localDate = LocalDate.now();

            String[] date = dtf.format(localDate).split("/");
            nkk += date[2] + date[1] + date[0].substring(2);

            List<Keluarga> similar = keluargaService.selectSimilarNKK(nkk + "%");
            String nomor = "0001";
            if(similar.size() > 0){
                int nomorSimilar = Integer.parseInt(similar.get(similar.size()-1).getNomor_kk().substring(12)) + 1;
                nomor = nomorSimilar + "";
            }

            int counter = 4 - nomor.length();
            for(int i = 0; i < counter; i++){
                nomor = "0" + nomor;
            }

            nkk += nomor;
            keluarga.setNomor_kk(nkk);
        } else {
            keluarga.setNomor_kk(keluargaLama.getNomor_kk());
        }

        keluarga.setId(keluargaLama.getId());

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

        model.addAttribute("confirmation", "Keluarga dengan NKK " + nkkLama + " berhasil diubah");

        keluargaService.updateKeluarga(keluarga);

        return "view/confirmation";
    }
}
