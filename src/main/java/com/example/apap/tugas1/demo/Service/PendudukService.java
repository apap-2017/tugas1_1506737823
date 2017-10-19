package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.Model.Penduduk;

import java.util.List;

public interface PendudukService {

    List<Penduduk> selectAllPenduduk();
    List<Penduduk> selectAnggotaKeluarga(String id);
    List<Penduduk> selectSimilarNIK(String nik);
    Penduduk selectPenduduk(String nik);
    void addPenduduk(Penduduk penduduk);
    void updatePenduduk(Penduduk penduduk);
    void nonaktifkanPenduduk(String nik);
    List<Penduduk> selectPendudukByKelurahan(String id_kelurahan);
}
