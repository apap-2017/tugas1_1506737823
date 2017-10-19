package com.example.apap.tugas1.demo.DAO;

import com.example.apap.tugas1.demo.Model.Keluarga;
import com.example.apap.tugas1.demo.Model.Penduduk;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface PendudukDAO{

    @Select("select * from penduduk")
    List<Penduduk> selectAllPenduduk();

    @Select("select * from penduduk where nik = #{nik}")
    Penduduk selectPenduduk(@Param("nik") String nik);

    @Select("select * from penduduk where nik LIKE #{nik}")
    List<Penduduk> selectSimilarNIK(@Param("nik") String nik);

    @Select("select * from penduduk where id_keluarga = #{id}")
    List<Penduduk> selectAnggotaKeluarga(@Param("id") String id);

    @Insert("insert into penduduk (id, nik, nama, tempat_lahir, tanggal_lahir, jenis_kelamin, is_wni, id_keluarga, agama, pekerjaan, status_perkawinan, status_dalam_keluarga, golongan_darah, is_wafat) values (#{id}, #{nik}, #{nama}, #{tempat_lahir}, #{tanggal_lahir}, #{jenis_kelamin}, #{is_wni}, #{id_keluarga}, #{agama}, #{pekerjaan}, #{status_perkawinan}, #{status_dalam_keluarga}, #{golongan_darah}, #{is_wafat})")
    void addPenduduk(Penduduk penduduk);

    @Update("update penduduk set is_wafat = 1 where nik = #{nik}")
    void nonaktifkanPenduduk(String nik);

    @Update("update penduduk set nik = #{nik}, nama = #{nama}, tempat_lahir = #{tempat_lahir}, tanggal_lahir = #{tanggal_lahir}, jenis_kelamin = #{jenis_kelamin}, is_wni = #{is_wni}, id_keluarga = #{id_keluarga}, agama = #{agama}, pekerjaan = #{pekerjaan}, status_perkawinan = #{status_perkawinan}, status_dalam_keluarga = #{status_dalam_keluarga}, golongan_darah = #{golongan_darah}, is_wafat = #{is_wafat} where id = #{id}")
    void updatePenduduk(Penduduk penduduk);

    @Select("select * from penduduk p, keluarga k where p.id_keluarga = k.id and id_kelurahan = #{id_kelurahan}")
    List<Penduduk> selectPendudukByKelurahan(String id_kelurahan);
}
