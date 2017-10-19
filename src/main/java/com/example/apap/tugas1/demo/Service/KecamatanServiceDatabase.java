package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.KecamatanDAO;
import com.example.apap.tugas1.demo.Model.Kecamatan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KecamatanServiceDatabase implements KecamatanService {

    @Autowired
    KecamatanDAO kecamatanDAO;

    @Override
    public Kecamatan selectKecamatan(String id){
        return kecamatanDAO.selectKecamatan(id);
    }

    @Override
    public List<Kecamatan> selectAllKecamatan(String kota){
        return kecamatanDAO.selectAllKecamatan(kota);
    }

    @Override
    public List<Kecamatan> selectAllKecamatan(){
        return kecamatanDAO.selectAllKecamatans();
    }
}
