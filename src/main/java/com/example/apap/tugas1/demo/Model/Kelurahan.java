package com.example.apap.tugas1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Kelurahan {
    private String id;
    private String Kode_kelurahan;
    private String id_kecamatan;
    private String nama_kelurahan;
    private String kode_pos;
}
