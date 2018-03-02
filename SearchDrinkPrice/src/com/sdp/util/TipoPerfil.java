package com.sdp.util;

import java.util.ArrayList;
import java.util.List;

public enum TipoPerfil {
	ADMINISTRADOR('A', "Administrador"), GESTOR('G', "Gestor"), BASICO('B', "Básico");
	public static final Character PERFIL_ADMINISTRADOR = 'A';

	public static final Character PERFIL_GESTOR = 'G';
	
	public static final Character PERFIL_BASICO = 'B';

	private Character chave;

	private String valor;

	private static List<TipoPerfil> list;

	private TipoPerfil(Character chave, String valor) {
		this.chave = chave;
		this.valor = valor;
	}

	public static List<TipoPerfil> getList() {
		if (list == null) {
			list = new ArrayList<TipoPerfil>();
			for (TipoPerfil uf : values()) {
				list.add(uf);
			}
		}
		return list;
	}

	public Character getChave() {
		return chave;
	}

	public String getValor() {
		return valor;
	}
}
