package com.example.apap.tugas1.demo.DAO;

import com.example.apap.tugas1.demo.Model.Keluarga;
import com.example.apap.tugas1.demo.Model.Penduduk;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface KeluargaDAO {

    @Select("select * from keluarga")
    List<Keluarga> selectAllKeluarga();

    @Select("select * from keluarga where nomor_kk = #{nkk}")
    Keluarga selectKeluarga(@Param("nkk") String nkk);

    @Select("select * from keluarga where id = #{id}")
    Keluarga selectKeluargaByID(@Param("id") String id);

    @Select("select * from keluarga where nomor_kk LIKE #{nkk}")
    List<Keluarga> selectSimilarNKK(@Param("nkk") String nkk);

    @Insert("insert into keluarga (id, nomor_kk, alamat, rt, rw, id_kelurahan, is_tidak_berlaku) values (#{id}, #{nomor_kk}, #{alamat}, #{rt}, #{rw}, #{id_kelurahan}, #{is_tidak_berlaku})")
    void addKeluarga(Keluarga keluarga);

    @Update("update keluarga set nomor_kk = #{nomor_kk}, alamat = #{alamat}, rt = #{rt}, rw = #{rw}, id_kelurahan = #{id_kelurahan}, is_tidak_berlaku = #{is_tidak_berlaku} where id = #{id}")
    void updateKeluarga(Keluarga keluarga);
}
