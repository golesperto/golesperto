package com.sdp.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sdp.util.Status;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_PERFIL_OBJETO")
public class PerfilObjeto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "POB_INT_COD")
	private Long pobIntCod;

	@ManyToOne
	@JoinColumn(name = "PER_INT_COD", nullable = false)
	private Perfil perfil;

	@ManyToOne
	@JoinColumn(name = "OBJ_INT_COD", nullable = false)
	private Objeto objeto;

	@Transient
	private List<Objeto> objetos;

	public PerfilObjeto() {
	}

	public PerfilObjeto(Perfil perfil, List<Objeto> objetos) {
		this.perfil = perfil;
		this.objetos = objetos;
	}

	public PerfilObjeto(Perfil perfil, Objeto objeto) {
		this.perfil = perfil;
		this.objeto = objeto;
	}

	public Perfil getPerfil() {
		return perfil;
	}

	public void setPerfil(Perfil perfil) {
		this.perfil = perfil;
	}

	public Objeto getObjeto() {
		return objeto;
	}

	public void setObjeto(Objeto objeto) {
		this.objeto = objeto;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}

	public Long getPobIntCod() {
		return pobIntCod;
	}

	public void setPobIntCod(Long pobIntCod) {
		this.pobIntCod = pobIntCod;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objeto == null) ? 0 : objeto.hashCode());
		result = prime * result + ((perfil == null) ? 0 : perfil.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PerfilObjeto other = (PerfilObjeto) obj;
		if (objeto == null) {
			if (other.objeto != null)
				return false;
		} else if (!objeto.equals(other.objeto))
			return false;
		if (perfil == null) {
			if (other.perfil != null)
				return false;
		} else if (!perfil.equals(other.perfil))
			return false;
		return true;
	}

}
