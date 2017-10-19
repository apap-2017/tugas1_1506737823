package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.Model.Keluarga;
import com.example.apap.tugas1.demo.Model.Penduduk;

import java.util.List;

public interface KeluargaService {
    List<Keluarga> selectAllKeluarga();
    List<Keluarga> selectSimilarNKK(String nkk);
    Keluarga selectKeluarga(String nkk);
    Keluarga selectKeluargaByID(String id);
    void addKeluarga(Keluarga keluarga);
    void updateKeluarga(Keluarga keluarga);
}
