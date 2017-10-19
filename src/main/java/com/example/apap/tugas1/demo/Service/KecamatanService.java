package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.KecamatanDAO;
import com.example.apap.tugas1.demo.Model.Kecamatan;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface KecamatanService {

    Kecamatan selectKecamatan(String id);
    List<Kecamatan> selectAllKecamatan(String kota);
    List<Kecamatan> selectAllKecamatan();
}
