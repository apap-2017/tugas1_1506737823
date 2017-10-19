package com.example.apap.tugas1.demo.DAO;

import com.example.apap.tugas1.demo.Model.Kecamatan;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KecamatanDAO {

    @Select("select * from kecamatan where id = #{id}")
    Kecamatan selectKecamatan(String id);

    @Select("select * from kecamatan where id_kota = #{kota}")
    List<Kecamatan> selectAllKecamatan(String kota);

    @Select("select * from kecamatan")
    List<Kecamatan> selectAllKecamatans();
}
