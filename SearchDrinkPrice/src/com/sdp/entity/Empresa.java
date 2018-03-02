package com.sdp.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.sdp.util.Status;
import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_EMPRESA")
public class Empresa implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "EMP_INT_COD")
	private Integer empIntCod;

	@Column(name = "EMP_STR_NOME", nullable = false, length = 150)
	private String empStrNome;

	@Column(name = "EMP_STR_NOME_FANTASIA", length = 150)
	private String empStrNomeFantasia;

	@Column(name = "EMP_STR_TELEFONE", nullable = false, length = 12)
	private String empStrTelefone;

	@Column(name = "EMP_STR_CELULAR", length = 12)
	private String empStrCelular;

	@Column(name = "EMP_STR_CNPJ", length = 14)
	private String empStrCnpj;

	@Column(name = "EMP_STR_EMAIL", nullable = false, length = 50)
	private String empStrEmail;

	@Column(name = "EMP_CHA_STATUS", nullable = false, length = 1)
	private Character empChaStatus;

	@Column(name = "EMP_STR_CEP", length = 8)
	private String empStrCep;

	@Column(name = "EMP_CHA_UF", length = 2)
	private String empChaUf;

	@Column(name = "EMP_STR_BAIRRO", length = 50)
	private String empStrBairro;

	@Column(name = "EMP_STR_CIDADE", length = 50)
	private String empStrCidade;

	@Column(name = "EMP_STR_ENDERECO", length = 100)
	private String empStrEndereco;

	@Column(name = "EMP_STR_RESPONSAVEL", length = 100)
	private String empStrResponsavel;

	@Column(name = "EMP_STR_LATITUDE", length = 100)
	private String empStrLatitude;

	@Column(name = "EMP_STR_LONGITUDE", length = 100)
	private String empStrLongitude;

	@Column(name = "EMP_STR_DESCRICAO", length = 255)
	private String empStrDescricao;

	@OneToMany(mappedBy = "empresa", cascade = CascadeType.ALL)
	private Set<EmpresaDiaFuncionamento> empresaDias;

	@OneToMany(mappedBy = "empresa")
	private List<EmpresaProduto> empresaProdutos;

	@Column(name = "EMP_STR_URL", length = 100)
	private String empStrUrl;

	public Empresa() {
	}

	public Integer getEmpIntCod() {
		return empIntCod;
	}

	public void setEmpIntCod(Integer empIntCod) {
		this.empIntCod = empIntCod;
	}

	public String getEmpStrNome() {
		return empStrNome;
	}

	public void setEmpStrNome(String empStrNome) {
		this.empStrNome = empStrNome;
	}

	public String getEmpStrNomeFantasia() {
		return empStrNomeFantasia;
	}

	public void setEmpStrNomeFantasia(String empStrNomeFantasia) {
		this.empStrNomeFantasia = empStrNomeFantasia;
	}

	public String getEmpStrTelefone() {
		return empStrTelefone;
	}

	public void setEmpStrTelefone(String empStrTelefone) {
		this.empStrTelefone = empStrTelefone;
	}

	public String getEmpStrCelular() {
		return empStrCelular;
	}

	public void setEmpStrCelular(String empStrCelular) {
		this.empStrCelular = empStrCelular;
	}

	public String getEmpStrCnpj() {
		return empStrCnpj;
	}

	public void setEmpStrCnpj(String empStrCnpj) {
		this.empStrCnpj = empStrCnpj;
	}

	public String getEmpStrEmail() {
		return empStrEmail;
	}

	public void setEmpStrEmail(String empStrEmail) {
		this.empStrEmail = empStrEmail;
	}

	public Character getEmpChaStatus() {
		return empChaStatus;
	}

	public void setEmpChaStatus(Character empChaStatus) {
		this.empChaStatus = empChaStatus;
	}

	public String getEmpStrCep() {
		return empStrCep;
	}

	public void setEmpStrCep(String empStrCep) {
		this.empStrCep = empStrCep;
	}

	public String getEmpChaUf() {
		return empChaUf;
	}

	public void setEmpChaUf(String empChaUf) {
		this.empChaUf = empChaUf;
	}

	public String getEmpStrBairro() {
		return empStrBairro;
	}

	public void setEmpStrBairro(String empStrBairro) {
		this.empStrBairro = empStrBairro;
	}

	public String getEmpStrCidade() {
		return empStrCidade;
	}

	public void setEmpStrCidade(String empStrCidade) {
		this.empStrCidade = empStrCidade;
	}

	public String getEmpStrEndereco() {
		return empStrEndereco;
	}

	public void setEmpStrEndereco(String empStrEndereco) {
		this.empStrEndereco = empStrEndereco;
	}

	public String getEmpStrResponsavel() {
		return empStrResponsavel;
	}

	public void setEmpStrResponsavel(String empStrResponsavel) {
		this.empStrResponsavel = empStrResponsavel;
	}

	public String getEmpStrUrl() {
		return empStrUrl;
	}

	public void setEmpStrUrl(String empStrUrl) {
		this.empStrUrl = empStrUrl;
	}

	public String getCnpjFormatado() {
		if (empStrCnpj != null && !empStrCnpj.isEmpty()) {
			return Utils.formataCNPJ(empStrCnpj);
		} else {
			return "";
		}
	}

	public String getTelefoneFormatado() {
		if (empStrTelefone != null && !empStrTelefone.isEmpty()) {
			return Utils.format("(##)####-####", empStrTelefone);
		} else {
			return "";
		}
	}

	public String getCelularFormatado() {
		if (empStrCelular != null && !empStrCelular.isEmpty()) {
			return Utils.format("(##)#####-####", empStrCelular);
		} else {
			return "";
		}
	}

	public String getStatusStr() {
		return empChaStatus != null && empChaStatus.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((empIntCod == null) ? 0 : empIntCod.hashCode());
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
		Empresa other = (Empresa) obj;
		if (empIntCod == null) {
			if (other.empIntCod != null)
				return false;
		} else if (!empIntCod.equals(other.empIntCod))
			return false;
		return true;
	}

	public String getEmpStrLatitude() {
		return empStrLatitude;
	}

	public void setEmpStrLatitude(String empStrLatitude) {
		this.empStrLatitude = empStrLatitude;
	}

	public String getEmpStrLongitude() {
		return empStrLongitude;
	}

	public void setEmpStrLongitude(String empStrLongitude) {
		this.empStrLongitude = empStrLongitude;
	}

	public String getEmpStrDescricao() {
		return empStrDescricao;
	}

	public void setEmpStrDescricao(String empStrDescricao) {
		this.empStrDescricao = empStrDescricao;
	}

	public Set<EmpresaDiaFuncionamento> getEmpresaDias() {
		return empresaDias;
	}

	public void setEmpresaDias(Set<EmpresaDiaFuncionamento> empresaDias) {
		this.empresaDias = empresaDias;
	}

	public List<EmpresaProduto> getEmpresaProdutos() {
		return empresaProdutos;
	}

	public void setEmpresaProdutos(List<EmpresaProduto> empresaProdutos) {
		this.empresaProdutos = empresaProdutos;
	}
}
