package com.apap.tugas1.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.repository.JabatanDB;

@Service
@Transactional
public class JabatanServiceImpl implements JabatanService {
	@Autowired
	private JabatanDB jabatanDb;
	@Override
	public List<JabatanModel> getAllJabatan() {
		
		return jabatanDb.findAll();
	}
	@Override
	public void addJabatan(JabatanModel jabatan) {
		// TODO Auto-generated method stub
		jabatanDb.save(jabatan);
	}
	
	@Override
	public Optional<JabatanModel> getJabatanById(long id) {
		// TODO Auto-generated method stub
		return jabatanDb.findById(id);
	}
	@Override
	public void updateJabatan(JabatanModel jabatan, String nama, String deskripsi, double gajiPokok) {
		// TODO Auto-generated method stub
		jabatan.setNama(nama);
		jabatan.setDeskripsi(deskripsi);
		jabatan.setGajiPokok(gajiPokok);
											
		jabatanDb.save(jabatan);
	}
	@Override
	public void deleteJabatan(long id) {
		// TODO Auto-generated method stub
		jabatanDb.deleteById(id);
	}
	

}
