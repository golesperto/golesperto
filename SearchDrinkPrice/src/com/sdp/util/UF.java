package com.sdp.util;

import java.util.ArrayList;
import java.util.List;

public enum UF {

    AC("AC", "Acre"),
    AL("AL", "Alagoas"),
    AP("AP", "Amapá"),
    AM("AM", "Amazonas"),
    BA("BA", "Bahia"),
    CE("CE", "Ceará"),
    DF("DF", "Distrito Federal"),
    ES("ES", "Espirito Santo"),
    GO("GO", "Goiás"),
    MA("MA", "Maranhão"),
    MT("MT", "Mato Grosso"),
    MS("MS", "Mato Grosso do Sul"),
    MG("MG", "Minas Gerais"),
    PA("PA", "Pará"),
    PB("PB", "Paraíba"),
    PR("PR", "Paraná"),
    PE("PE", "Pernambuco"),
    PI("PI", "Piaui"),
    RJ("RJ", "Rio de Janeiro"),
    RN("RN", "Rio Grande do Norte"),
    RS("RS", "Rio Grande do Sul"),
    RO("RO", "Rondônia"),
    RR("RR", "Roraima"),
    SC("SC", "Santa Catarina"),
    SP("SP", "São Paulo"),
    SE("SE", "Sergipe"),
    TO("TO", "Tocantins");
    
    private String chave;

    private String valor;
    
    private static List<UF> list;

    private UF(String chave, String valor) {
        this.chave = chave;
        this.valor = valor;
    }
    
    public static List<UF> getList() {
        if (list == null) {            
            list = new ArrayList<UF>();            
            for (UF uf : values()) {
                list.add(uf);
            }
        }
        return list;
    }

    public String getChave() {
        return chave;
    }

    public String getValor() {
        return valor;
    }

}
