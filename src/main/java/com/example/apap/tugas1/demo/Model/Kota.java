package com.example.apap.tugas1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kota {
    private String id;
    private String kode_kota;
    private String nama_kota;
}