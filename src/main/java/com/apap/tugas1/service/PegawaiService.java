package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;

public interface PegawaiService {

	List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir,
			String tahunMasuk);

	void addPegawai(PegawaiModel pegawai);
	PegawaiModel getPegawaiByNip(String nip);
	
	List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi);
	String makeNip(PegawaiModel pegawai, InstansiModel instansi);

	List<PegawaiModel> getAllPegawai();
	
}
