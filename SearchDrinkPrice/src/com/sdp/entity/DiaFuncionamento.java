package com.sdp.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SDP_DIA_FUNCIONAMENTO")
public class DiaFuncionamento implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "DFU_INT_COD")
	private Integer dfuIntCod;

	@Column(name = "DFU_STR_NOME", length = 50)
	private String dfuStrNome;

	@Column(name = "DFU_INT_DIA")
	private Integer dfuIntDia;

	@OneToMany(mappedBy = "diasFuncionamento")
	private List<EmpresaDiaFuncionamento> empresaDias;

	public DiaFuncionamento() {

	}

	public Integer getDfuIntCod() {
		return dfuIntCod;
	}

	public void setDfuIntCod(Integer dfuIntCod) {
		this.dfuIntCod = dfuIntCod;
	}

	public String getDfuStrNome() {
		return dfuStrNome;
	}

	public void setDfuStrNome(String dfuStrNome) {
		this.dfuStrNome = dfuStrNome;
	}

	public Integer getDfuIntDia() {
		return dfuIntDia;
	}

	public void setDfuIntDia(Integer dfuIntDia) {
		this.dfuIntDia = dfuIntDia;
	}

	public List<EmpresaDiaFuncionamento> getEmpresaDias() {
		return empresaDias;
	}

	public void setEmpresaDias(List<EmpresaDiaFuncionamento> empresaDias) {
		this.empresaDias = empresaDias;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dfuIntDia == null) ? 0 : dfuIntDia.hashCode());
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
		DiaFuncionamento other = (DiaFuncionamento) obj;
		if (dfuIntDia == null) {
			if (other.dfuIntDia != null)
				return false;
		} else if (!dfuIntDia.equals(other.dfuIntDia))
			return false;
		return true;
	}

}
