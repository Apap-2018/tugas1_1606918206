package com.apap.tugas1.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;


@Repository
public interface InstansiDB extends JpaRepository<InstansiModel, Long> {

	List<InstansiModel> findByProvinsi(ProvinsiModel provinsi);

	List<InstansiModel> findByNama(String nama);
	
}
