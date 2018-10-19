package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface JabatanService {

	List<JabatanModel> getAllJabatan();

	void addJabatan(JabatanModel jabatan);
	Optional<JabatanModel> getJabatanById(long id);
	void updateJabatan(JabatanModel jabatan,String nama, String deskripsi, double gajiPokok);
	void deleteJabatan(long id);
	
}
