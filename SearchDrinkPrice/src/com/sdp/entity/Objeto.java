package com.sdp.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.sdp.util.Status;
import com.sdp.util.Utils;

@SuppressWarnings("serial")
@Entity
@Table(name = "sdp_OBJETO")
public class Objeto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "OBJ_INT_COD")
	private Integer objIntCod;

	@Column(name = "OBJ_STR_OBJETO", nullable = false, length = 50)
	private String objStrObjeto;

	@Column(name = "OBJ_STR_DESCRICAO", nullable = false, length = 255)
	private String objStrDescricao;

	@Column(name = "OBJ_CHA_STATUS", nullable = false, length = 1)
	private Character objChaStatus;

	@Column(name = "OBJ_STR_ICONE", nullable = false, length = 50)
	private String objStrIcone;

	@ManyToOne
	@JoinColumn(name = "MEN_INT_COD", nullable = false)
	private Menu menu;

	public Objeto() {
	}

	public String getStatusStr() {
		return objChaStatus != null && objChaStatus.equals(Status.STATUS_NORMAL) ? "Normal" : "Bloqueado";
	}

	public Integer getObjIntCod() {
		return objIntCod;
	}

	public void setObjIntCod(Integer objIntCod) {
		this.objIntCod = objIntCod;
	}

	public String getObjStrObjeto() {
		return objStrObjeto;
	}

	public void setObjStrObjeto(String objStrObjeto) {
		this.objStrObjeto = objStrObjeto;
	}

	public String getObjStrDescricao() {
		return objStrDescricao;
	}

	public void setObjStrDescricao(String objStrDescricao) {
		this.objStrDescricao = objStrDescricao;
	}

	public String getObjStrIcone() {
		return objStrIcone;
	}
	
	public String getIconeImagem() {
		return Utils.getIconeImagem(objStrIcone);
	}

	public void setObjStrIcone(String objStrIcone) {
		this.objStrIcone = objStrIcone;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((objIntCod == null) ? 0 : objIntCod.hashCode());
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
		Objeto other = (Objeto) obj;
		if (objIntCod == null) {
			if (other.objIntCod != null)
				return false;
		} else if (!objIntCod.equals(other.objIntCod))
			return false;
		return true;
	}

	public Character getObjChaStatus() {
		return objChaStatus;
	}

	public void setObjChaStatus(Character objChaStatus) {
		this.objChaStatus = objChaStatus;
	}
}
