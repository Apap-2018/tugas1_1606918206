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
	public String home(Model model) {
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
		System.out.println("BBBBBBBBBBBBB");
		System.out.println(pegawai.getInstansi().getNama());
		System.out.println("CCCCCCCCCCCCC");
		
		String nipq = pegawaiService.makeNip(pegawai, pegawai.getInstansi());
		pegawai.setNip(nipq);
		
		pegawaiService.addPegawai(pegawai);
		
		model.addAttribute("pegawai", pegawai);
		model.addAttribute("headerTitle", "Tambah Pegawai Sukses");

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
	@RequestMapping(value= "/pegawai/cari")
	private String cariPegawai(Model model) {
		model.addAttribute("listPegawai", pegawaiService.getAllPegawai());
		
		List<InstansiModel> listInstansi = instansiService.getAllInstansi();
		model.addAttribute("listInstansi", listInstansi);
		
		List<JabatanModel> listJabatan = jabatanService.getAllJabatan();
		model.addAttribute("listJabatan", listJabatan);
		
		List<ProvinsiModel> listProvinsi = provinsiService.getAllProvinsi();
		model.addAttribute("listProvinsi", listProvinsi);
		
		
		return "cariPegawai";
	}
	
	@RequestMapping(value = "/pegawai/cari", method = RequestMethod.GET, params = {"idProvinsi","idInstansi","idJabatan"})
	private String cariPegawai(@RequestParam("idProvinsi") long idProvinsi, @RequestParam("idInstansi") long idInstansi, @RequestParam("idJabatan") long idJabatan, Model model) {
		model.addAttribute("listProvinsi", provinsiService.getAllProvinsi());
		model.addAttribute("listInstansi", instansiService.getAllInstansi());
		model.addAttribute("listJabatan", jabatanService.getAllJabatan());
		
		if(idProvinsi==0&&idJabatan==0) {
			InstansiModel instansi = instansiService.getInstansiById(idInstansi);
			model.addAttribute("allPegawai", pegawaiService.getPegawaiByInstansi(instansi));
		}
		
		else if(idJabatan==0&&idInstansi==0) {
			ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi);
			List<InstansiModel> allInstansiInProvinsi = instansiService.viewByProvinsi(provinsi);
				
			List<PegawaiModel> allPegawai = pegawaiService.getAllPegawai();
			List<PegawaiModel> allPegawaiInProvinsi = new ArrayList<PegawaiModel>();
			
			for(int i=0;i<allPegawai.size();i++) {
				if(allInstansiInProvinsi.contains(allPegawai.get(i).getInstansi())) {
					allPegawaiInProvinsi.add(allPegawai.get(i));
				}
			}
			model.addAttribute("listPegawai",allPegawaiInProvinsi);
		}
		
		else if(idProvinsi==0&&idInstansi==0) {
			JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
			List<PegawaiModel> allPegawai = pegawaiService.getAllPegawai();
			List<PegawaiModel> allPegawaiInJabatan = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawai.size();i++) {
				for(int j=0;j<allPegawai.get(i).getJabatan().size();j++) {
					if(allPegawai.get(i).getJabatan().get(j)==jabatan)
						allPegawaiInJabatan.add(allPegawai.get(i));
				}
			}
			model.addAttribute("listPegawai",allPegawaiInJabatan);
		}
		
		else if(idProvinsi==0) {
			InstansiModel instansi = instansiService.getInstansiById(idInstansi);
			List<PegawaiModel> allPegawaiInInstansi = pegawaiService.getPegawaiByInstansi(instansi);
			
			JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
			List<PegawaiModel> allPegawaiInJabatanInstansi = new ArrayList<PegawaiModel>();
			
			for(int i=0;i<allPegawaiInInstansi.size();i++) {
				for(int j=0;j<allPegawaiInInstansi.get(i).getJabatan().size();j++) {
					if(allPegawaiInInstansi.get(i).getJabatan().get(j)==jabatan)
						allPegawaiInJabatanInstansi.add(allPegawaiInInstansi.get(i));
				}
			}
			
			model.addAttribute("listPegawai",allPegawaiInJabatanInstansi);
		}
		else if(idInstansi==0) {
			ProvinsiModel provinsi = provinsiService.getProvinsiById(idProvinsi);
			List<InstansiModel> instansiInProvinsiq = instansiService.viewByProvinsi(provinsi);
			
			JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
			List<PegawaiModel> pegawaiInProvinsiJabatanq = new ArrayList<PegawaiModel>();
			List<PegawaiModel> pegawaiQ = pegawaiService.getAllPegawai();
			
			for(int i=0;i<pegawaiQ.size();i++) {
				if(instansiInProvinsiq.contains(pegawaiQ.get(i).getInstansi())) {
					for(int j=0;j<pegawaiQ.get(i).getJabatan().size();j++) {
						if(pegawaiQ.get(i).getJabatan().get(j)==jabatan) {
							pegawaiInProvinsiJabatanq.add(pegawaiQ.get(i));
						}
					}
				}
			}
		
			model.addAttribute("listPegawai",pegawaiInProvinsiJabatanq);
		}
		else if(idJabatan==0) {
			InstansiModel instansi = instansiService.getInstansiById(idInstansi);
			List<PegawaiModel> allPegawai = pegawaiService.getPegawaiByInstansi(instansi);
			model.addAttribute("listPegawai",allPegawai);
		}
		else {
			InstansiModel instansi = instansiService.getInstansiById(idInstansi);
			List<PegawaiModel> allPegawai = pegawaiService.getPegawaiByInstansi(instansi);
			
			JabatanModel jabatan = jabatanService.getJabatanById(idJabatan).get();
			List<PegawaiModel> allPegawaiInJabatanInstansiProvinsi = new ArrayList<PegawaiModel>();
			for(int i=0;i<allPegawai.size();i++) {
				for(int j=0;j<allPegawai.get(i).getJabatan().size();j++) {
					if(allPegawai.get(i).getJabatan().get(j)==jabatan)
						allPegawaiInJabatanInstansiProvinsi.add(allPegawai.get(i));
				}
			}
			model.addAttribute("listPegawai",allPegawaiInJabatanInstansiProvinsi);
		}
		
		return "cariPegawai";
}
	
	/*
	 *  Show pegawai tertua dan termuda dari setiap instansi
	 */
	
	@RequestMapping(value= "/pegawai/termuda-tertua", method = RequestMethod.GET)
	private String pegawaiTertuaTermuda(@RequestParam(value="idInstansi") long id, Model model) {
		List<PegawaiModel> listPegawai = pegawaiService.getPegawaiByInstansi(instansiService.getInstansiById(id));
		
		model.addAttribute("pegawaiTua", listPegawai.get(0));
		model.addAttribute("pegawaiMuda", listPegawai.get(listPegawai.size()-1));
		
		
		return "pegawaiTermuda-tertua";
	}
	
	
	/*
	 * Tambah row Jabatan
	 */
	@RequestMapping(value = "/pegawai/fromInstansi", method = RequestMethod.GET)
	@ResponseBody
	public List<InstansiModel> getInstansi(@RequestParam (value = "provinsiId", required = true) long provinsiId) {
	    ProvinsiModel provinsi = provinsiService.getProvinsiById(provinsiId);
		return instansiService.viewByProvinsi(provinsi);
	}
	
	@RequestMapping(value = "/pegawai/fromProvinsi", method = RequestMethod.GET)
	@ResponseBody
	public List<ProvinsiModel> getProvinsi(@RequestParam (value = "instansiId", required = true) long instansiId) {
	    String namaInstansi = instansiService.getInstansiById(instansiId).getNama();
	    List<InstansiModel> daftarInstansi= instansiService.viewByNama(namaInstansi);
	    List<ProvinsiModel> provinsi = new ArrayList<ProvinsiModel>();
	    
	    for(int i=0; i<daftarInstansi.size(); i++) {
	    	provinsi.add(daftarInstansi.get(i).getProvinsi());
	    }
		return provinsi;
}
	
	
}
