package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.apap.tugas1.model.InstansiModel;
import com.apap.tugas1.model.JabatanModel;
import com.apap.tugas1.model.PegawaiModel;
import com.apap.tugas1.model.ProvinsiModel;
import com.apap.tugas1.service.InstansiService;
import com.apap.tugas1.service.JabatanService;
import com.apap.tugas1.service.PegawaiService;
import com.apap.tugas1.service.ProvinsiService;

@Controller
public class JabatanController {
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping(value= "/jabatan/tambah",  method = RequestMethod.GET)
	public String tambah(Model model) {
		model.addAttribute("jabatan", new JabatanModel());
		
		return "tambahJabatan";
	}
	
	@RequestMapping(value= "/jabatan/tambah",  method = RequestMethod.POST)
	public String tambahSubmit(@ModelAttribute JabatanModel jabatan,Model model) {
		
		jabatanService.addJabatan(jabatan);
		return "jabatanDitambah";
	}
	
	
	@RequestMapping(value= "/jabatan/view", method = RequestMethod.GET)
	public String view(@RequestParam(value="idJabatan", required=true) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id).get();
		model.addAttribute("jabatan",jabatan);
		
		return "viewJabatan";
	}
	
	@RequestMapping(value= "/jabatan/ubah", method = RequestMethod.GET)
	public String ubah(@RequestParam(value="idJabatan", required=true) long id, Model model) {
		JabatanModel jabatan = jabatanService.getJabatanById(id).get();
		model.addAttribute("jabatan", jabatan);
		
		return "ubahJabatan";
	}
	
	@RequestMapping(value= "/jabatan/ubah", method = RequestMethod.POST)
	public String ubahSubmit( Model model, 	@RequestParam(value="idJabatan", required=true) long id,
											@RequestParam(value="nama", required=true) String nama,
											@RequestParam(value="deskripsi", required=true) String deskripsi,
											@RequestParam(value="gajiPokok", required=true) double gajiPokok) {
		JabatanModel jabatan = jabatanService.getJabatanById(id).get();
		jabatanService.updateJabatan(jabatan, nama, deskripsi, gajiPokok);
		
		return "jabatanDiubah";
	}
	
	@RequestMapping(value = "/jabatan/hapus", method = RequestMethod.POST)
	public String hapusSubmit(@RequestParam(value="idJabatan", required=true) long id) {
		jabatanService.deleteJabatan(id);
		
		return "jabatanDihapus";
	}
	
	@RequestMapping(value = "/jabatan/viewAllJabatan", method = RequestMethod.GET)
	public String viewAll(Model model) {
		List<JabatanModel> jabatanq = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", jabatanq);
		
		return "viewAllJabatan";
	}
	
}
