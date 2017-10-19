package com.example.apap.tugas1.demo.Service;

import com.example.apap.tugas1.demo.DAO.KotaDAO;
import com.example.apap.tugas1.demo.Model.Kota;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class KotaServiceDatabase implements KotaService {

    @Autowired
    KotaDAO kotaDAO;

    @Override
    public Kota selectKota(String id){
        return kotaDAO.selectKota(id);
    }

    @Override
    public List<Kota> selectAllKota(){
        return kotaDAO.selectAllKota();
    }
}
