package com.apap.tugas1.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

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
public class PegawaiController {
	
	@Autowired
	private PegawaiService pegawaiService;
	
	@Autowired
	private InstansiService instansiService;
	
	@Autowired
	private ProvinsiService provinsiService;
	
	@Autowired
	private JabatanService jabatanService;
	
	@RequestMapping("/")
	public String index(Model model) {
		List<JabatanModel> jabatanq = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", jabatanq);
		
		List<InstansiModel> instansiq = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", instansiq);
		return "home";
	}
	
	/*
	 *  Buat nambahin pegawai
	 */
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.GET)
	private String add(Model model) {
		PegawaiModel pegawai = new PegawaiModel();
		pegawai.setJabatan(new ArrayList<JabatanModel>());
		pegawai.getJabatan().add(new JabatanModel());
		model.addAttribute("pegawai", pegawai);
		
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", new HashSet(listInstansi));
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", new HashSet(listJabatan));
		
		
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		model.addAttribute("tanggalLahir", "");
		model.addAttribute("title", "Tambah Pegawai");
		return "tambahPegawai";
	}
	
	@RequestMapping(value = "/pegawai/tambah", method = RequestMethod.POST)
	public String tambahPegawai (@ModelAttribute PegawaiModel pegawai, Model model) {
		
		Date tanggalLahir = pegawai.getTanggalLahir();
		String tahunMasuk = pegawai.getTahunMasuk();
		InstansiModel instansi = pegawai.getInstansi();
		
		int noPegawai = pegawaiService.getPegawaiByInstansiAndTanggalLahirAndTahunMasuk(instansi, tanggalLahir, tahunMasuk).size()+1;
		
		String instansiKode = Long.toString(instansi.getId());
		
		String tanggalan = "dd-MM-yy";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(tanggalan);
		
		String tanggalLahirString = simpleDateFormat.format(tanggalLahir).replaceAll("-", "");
		
		String stringPegawai = "";
		if(noPegawai/10 ==0) {
			stringPegawai += ("0" + Integer.toString(noPegawai)); 
		}
		else {
			stringPegawai += Integer.toString(noPegawai);
		}
		
		String nip = instansiKode + tanggalLahirString + tahunMasuk + stringPegawai;
		
		pegawai.setNip(nip);
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("title", "Tambah Pegawai Sukses");
		
		return "pegawaiDitambah";
}
	
	/*
	 * Menampilkan pegawai sesuai NIP
	 */
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.GET)
	private String view(@RequestParam("nip") String nip,Model model) {
		PegawaiModel pegawai = pegawaiService.getPegawaiByNip(nip);
		model.addAttribute("pegawai",pegawai);
		model.addAttribute("listJabatan", pegawai.getJabatan());
		
		String gaji = "Rp. " + (int)pegawai.getGaji();
		
		
		model.addAttribute("gajiTot",gaji);
		
		
		return "viewPegawai";
	}
	
	@RequestMapping(value = "/pegawai", method = RequestMethod.POST)
	private String viewSubmit(Model model) {
		
		return "addPegawai";
	}
	
	/*
	 * @RequestMapping("/generator")
	public String generator(@RequestParam("a") int aValue, @RequestParam("b") int bValue, Model model) {
	 */
	
	/*
	 * mencari pegawai berdasarkan idprovinsi/instansi/jabatan
	 */
	@RequestMapping(value= "/pegawai/cari", method = RequestMethod.GET)
	private String cariPegawai(Model model, @RequestParam("idProvinsi") String idProvinsi,
											@RequestParam("idInstansi") String idInstansi,
											@RequestParam("idJabatan") String idJabatan) {

		
		
		return "cariPegawai";
	}
	
	/*
	 * 
	 */
	
	@RequestMapping(value= "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String pegawaiTertuaTermuda(@RequestParam(value="idInstansi") long id, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.listPegawaiInstansi(instansiService.getInstansiById(id));
		
		model.addAttribute("pegawaiTua", listPegawai.get(0));
		model.addAttribute("pegawaiMuda", listPegawai.get(listPegawai.size()-1));
		
		
		return "pegawaiTermuda-tertua";
	}
	
	
}
