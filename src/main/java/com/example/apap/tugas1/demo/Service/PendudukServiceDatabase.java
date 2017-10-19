package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.PendudukDAO;
import com.example.apap.tugas1.demo.Model.Penduduk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PendudukServiceDatabase implements PendudukService{

    @Autowired
    private PendudukDAO pendudukDAO;

    @Override
    public List<Penduduk> selectAllPenduduk(){
        return pendudukDAO.selectAllPenduduk();
    }

    @Override
    public Penduduk selectPenduduk(String nik){
        return pendudukDAO.selectPenduduk(nik);
    }

    @Override
    public List<Penduduk> selectAnggotaKeluarga(String id){
        return pendudukDAO.selectAnggotaKeluarga(id);
    }

    @Override
    public List<Penduduk> selectSimilarNIK(String nik){
        return pendudukDAO.selectSimilarNIK(nik + "%");
    }

    @Override
    public void addPenduduk(Penduduk penduduk){
        pendudukDAO.addPenduduk(penduduk);
    }

    @Override
    public void updatePenduduk (Penduduk penduduk){
        pendudukDAO.updatePenduduk(penduduk);
    }

    @Override
    public void nonaktifkanPenduduk(String nik){
        pendudukDAO.nonaktifkanPenduduk(nik);
    }

    @Override
    public List<Penduduk> selectPendudukByKelurahan(String id_kelurahan){
        return pendudukDAO.selectPendudukByKelurahan(id_kelurahan);
    }
}
