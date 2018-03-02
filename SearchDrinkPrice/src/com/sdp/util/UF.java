package com.sdp.util;

import java.util.ArrayList;
import java.util.List;

public enum UF {

    AC("AC", "Acre"),
    AL("AL", "Alagoas"),
    AP("AP", "Amap�"),
    AM("AM", "Amazonas"),
    BA("BA", "Bahia"),
    CE("CE", "Cear�"),
    DF("DF", "Distrito Federal"),
    ES("ES", "Espirito Santo"),
    GO("GO", "Goi�s"),
    MA("MA", "Maranh�o"),
    MT("MT", "Mato Grosso"),
    MS("MS", "Mato Grosso do Sul"),
    MG("MG", "Minas Gerais"),
    PA("PA", "Par�"),
    PB("PB", "Para�ba"),
    PR("PR", "Paran�"),
    PE("PE", "Pernambuco"),
    PI("PI", "Piaui"),
    RJ("RJ", "Rio de Janeiro"),
    RN("RN", "Rio Grande do Norte"),
    RS("RS", "Rio Grande do Sul"),
    RO("RO", "Rond�nia"),
    RR("RR", "Roraima"),
    SC("SC", "Santa Catarina"),
    SP("SP", "S�o Paulo"),
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
