package com.example.apap.tugas1.demo.DAO;

import com.example.apap.tugas1.demo.Model.Kota;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KotaDAO {

    @Select("select * from kota where id = #{id}")
    Kota selectKota(String id);

    @Select("select * from kota")
    List<Kota> selectAllKota();
}
