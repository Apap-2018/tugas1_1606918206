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
	public List<PegawaiModel> getPegawaiByInstansi(InstansiModel instansi) {
		return pegawaiDb.findByInstansiOrderByTanggalLahirAsc(instansi);
		// TODO Auto-generated method stub
		
	}

	@Override
	public String makeNip(PegawaiModel pegawai, InstansiModel instansi) {
		
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAa");
		System.out.println("kntl");
		String finalNip = "";
		Date tanggalLahir = pegawai.getTanggalLahir();
		System.out.println("kntl");
		String temp = "" + tanggalLahir;
		String[] tempTtl = temp.split("-");
		System.out.println("kntl5");
		System.out.println("instansiq " + pegawai.getInstansi().getId());
		finalNip+= pegawai.getInstansi().getId();
		
		for (int i = tempTtl.length-1; i >= 0; i--) {
			int tglSize = tempTtl[i].length();
			finalNip += tempTtl[i].substring(tglSize-2, tglSize);
		}

		finalNip+= pegawai.getTahunMasuk();
		
		
		List<PegawaiModel> pegawaiq = pegawaiDb.findByInstansiAndTanggalLahirAndTahunMasuk(pegawai.getInstansi(), pegawai.getTanggalLahir(), pegawai.getTahunMasuk());
		if(pegawaiq.size()>9) {
			finalNip += pegawaiq.size();
		}
		else {
			finalNip += ("0" + (pegawaiq.size()+1)); 
		}
		
		
		return finalNip;
	}

	@Override
	public List<PegawaiModel> getAllPegawai() {
		
		return pegawaiDb.findAll();
	}
	
	
}
