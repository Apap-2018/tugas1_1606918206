package com.apap.tugas1.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.repository.PegawaiDB;

@Service
@Transactional
public class PegawaiServiceImpl implements PegawaiService {
	
	@Autowired
	private PegawaiDB pegawaiDb;
	
	public List<PegawaiModel> getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(InstansiModel instansi, Date tanggalLahir,
			String tahunMasuk){
		return pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk);
		
		
	}

	public void addPegawai(PegawaiModel pegawai){
		pegawaiDb.save(pegawai);
	}
	
	public PegawaiModel getPegawaiByNip(String nip) {
		return pegawaiDb.findByNip(nip).get(0);
	}

	@Override
	public List<PegawaiModel> listPegawaiInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		// TODO Auto-generated method stub
		
	}
}
