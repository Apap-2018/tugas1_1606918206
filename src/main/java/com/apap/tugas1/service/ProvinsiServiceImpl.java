package com.apap.tugas1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.repository.ProvinsiDB;

@Service
@Transactional
public class ProvinsiServiceImpl implements ProvinsiService{
	
	@Autowired
	private ProvinsiDB provinsiDb;

	@Override
	public List<ProvinsiModel> getAllProvinsi() {
		
		return provinsiDb.findAll();
	}

	@Override
	public ProvinsiModel getProvinsiById(long provinsiId) {
		// TODO Auto-generated method stub
		return provinsiDb.findById(provinsiId).get();
	}
}
