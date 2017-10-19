package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.Model.Kelurahan;

import java.util.List;

public interface KelurahanService {

    Kelurahan selectKelurahan(String id);
    List<Kelurahan> selectAllKelurahan(String kecamatan);
    List<Kelurahan> selectAllKelurahan();
}
