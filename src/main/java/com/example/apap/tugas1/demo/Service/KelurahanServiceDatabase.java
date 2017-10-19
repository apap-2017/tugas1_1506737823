package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.KelurahanDAO;
import com.example.apap.tugas1.demo.Model.Kelurahan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KelurahanServiceDatabase implements KelurahanService{

    @Autowired
    KelurahanDAO kelurahanDAO;

    @Override
    public Kelurahan selectKelurahan(String id){
        return kelurahanDAO.selectKelurahan(id);
    }

    @Override
    public List<Kelurahan> selectAllKelurahan(String kecamatan){
        return kelurahanDAO.selectAllKelurahan(kecamatan);
    }

    @Override
    public List<Kelurahan> selectAllKelurahan(){
        return kelurahanDAO.selectAllKelurahans();
    }
}
