package com.apap.tugas1.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.ProvinsiModel;


public interface InstansiService {

	List<InstansiModel> getAllInstansi();
	InstansiModel getInstansiById(long id);
	List<InstansiModel> viewByNama(String namaInstansi);
	List<InstansiModel> viewByProvinsi(ProvinsiModel provinsi);
	

}
