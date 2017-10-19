package com.example.apap.tugas1.demo.Model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Keluarga {
    private String id;
    private String nomor_kk;

    @NotNull
    @Size(min=1, max=256)
    private String alamat;

    @NotNull
    private String rt;

    @NotNull
    private String rw;

    @NotNull
    @Size(min=1, max=20)
    private String id_kelurahan;

    private String is_tidak_berlaku;
}
