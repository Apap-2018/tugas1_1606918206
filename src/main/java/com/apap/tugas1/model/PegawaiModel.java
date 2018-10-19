package com.apap.tugas1.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Entity
@Table(name = "pegawai")
public class PegawaiModel implements Serializable {

    public void setId(long id) {
		this.id = id;
	}

	public void setNip(String nip) {
		this.nip = nip;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public void setTempatLahir(String tempatLahir) {
		this.tempatLahir = tempatLahir;
	}

	public void setTanggalLahir(Date tanggalLahir) {
		this.tanggalLahir = tanggalLahir;
	}

	public void setTahunMasuk(String tahunMasuk) {
		this.tahunMasuk = tahunMasuk;
	}

	public void setInstansi(InstansiModel instansi) {
		this.instansi = instansi;
	}

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    public long getId() {
        return id;
    }

    public String getNip() {
        return nip;
    }

    public String getNama() {
        return nama;
    }

    public String getTempatLahir() {
        return tempatLahir;
    }

    public Date getTanggalLahir() {
        return tanggalLahir;
    }

    public String getTahunMasuk() {
        return tahunMasuk;
    }

    public InstansiModel getInstansi() {
        return instansi;
    }

    @NotNull
    @Size(max = 255)
    @Column(name = "nip", nullable = false, unique = true)
    private String nip;

    @NotNull
    @Size(max = 255)
    @Column(name = "nama", nullable = false)
    private String nama;

    @NotNull
    @Size(max = 255)
    @Column(name = "tempat_lahir", nullable = false)
    private String tempatLahir;

    @NotNull
    @Column(name = "tanggal_lahir", nullable = false)
    private Date tanggalLahir;

    @NotNull
    @Size(max = 255)
    @Column(name = "tahun_masuk", nullable = false)
    private String tahunMasuk;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_instansi",referencedColumnName = "id", nullable = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private InstansiModel instansi;
    
    @ManyToMany(cascade = { 
            CascadeType.PERSIST, 
            CascadeType.MERGE
        })
	@JoinTable(name = "jabatan_pegawai", joinColumns = { @JoinColumn(name = "id_pegawai") }, inverseJoinColumns = { @JoinColumn(name = "id_jabatan") })
    private List<JabatanModel> jabatan = new ArrayList<JabatanModel>();

	public List<JabatanModel> getJabatan() {
		return jabatan;
	}

	public void setJabatan(List<JabatanModel> jabatan) {
		this.jabatan = jabatan;
	}

	public double getGaji() {
		double gatot = 0;
		List<JabatanModel> jabatanq = this.getJabatan();
		InstansiModel instansiq = this.getInstansi();
		
		for(JabatanModel jabatan : jabatanq) {
			Double gajiPokok = jabatan.getGajiPokok();
			Double tunjangan = instansiq.getProvinsi().getPresentaseTunjangan();
			Double tambahan = (tunjangan * gajiPokok / 100);
			Double gatotTemp = (gajiPokok + tambahan);
			if (gatotTemp > gatot) {
				gatot = gatotTemp;
			}
		}
		
		
		return gatot;
	}
}