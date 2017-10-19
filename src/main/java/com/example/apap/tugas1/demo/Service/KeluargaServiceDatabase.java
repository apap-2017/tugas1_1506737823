package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.KeluargaDAO;
import com.example.apap.tugas1.demo.Model.Keluarga;
import com.example.apap.tugas1.demo.Model.Penduduk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class KeluargaServiceDatabase implements KeluargaService {

    @Autowired
    private KeluargaDAO keluargaDAO;

    @Override
    public List<Keluarga> selectAllKeluarga ()
    {
        return keluargaDAO.selectAllKeluarga();
    }

    @Override
    public Keluarga selectKeluargaByID(String id){
        return keluargaDAO.selectKeluargaByID(id);
    }

    @Override
    public Keluarga selectKeluarga(String nkk){
        return keluargaDAO.selectKeluarga(nkk);
    }

    @Override
    public List<Keluarga> selectSimilarNKK(String nkk) {
        return keluargaDAO.selectSimilarNKK(nkk);
    }

    @Override
    public void addKeluarga(Keluarga keluarga){
        keluargaDAO.addKeluarga(keluarga);
    }

    @Override
    public void updateKeluarga(Keluarga keluarga){
        keluargaDAO.updateKeluarga(keluarga);
    }
}
