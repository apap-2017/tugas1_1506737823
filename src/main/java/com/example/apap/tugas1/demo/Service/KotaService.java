package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.Model.Kota;

import java.util.List;

public interface KotaService {
    Kota selectKota(String id);
    List<Kota> selectAllKota();
}

