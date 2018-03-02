package com.sdp.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.sdp.util.Utils;

@Entity
@Table(name = "SDP_EMPRESA_DIA_FUNCIONAMENTO")
public class EmpresaDiaFuncionamento implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "EDI_INT_COD")
    private Integer ediIntCod;

    @ManyToOne
    @JoinColumn(name = "EMP_INT_COD")
    private Empresa empresa;

    @ManyToOne
    @JoinColumn(name = "DFU_INT_COD")
    private DiaFuncionamento diasFuncionamento;

    @Temporal(TemporalType.TIME)
    @Column(name = "DFU_DTM_ABERTURA")
    private Date dfuDtmAbertura;

    @Temporal(TemporalType.TIME)
    @Column(name = "DFU_DTM_FECHAMENTO")
    private Date dfuDtmFechamento;

    public EmpresaDiaFuncionamento() {

    }

    public EmpresaDiaFuncionamento(Empresa empresa, DiaFuncionamento diasFuncionamento, Date dfuDtmAbertura, Date dfuDtmFechamento) {
        this.empresa = empresa;
        this.diasFuncionamento = diasFuncionamento;
        this.dfuDtmAbertura = dfuDtmAbertura;
        this.dfuDtmFechamento = dfuDtmFechamento;
    }

    public Integer getEdiIntCod() {
        return this.ediIntCod;
    }

    public void setEdiIntCod(Integer ediIntCod) {
        this.ediIntCod = ediIntCod;
    }

    public Empresa getEmpresa() {
        return this.empresa;
    }

    public void setEmpresa(Empresa empresa) {
        this.empresa = empresa;
    }

    public DiaFuncionamento getDiasFuncionamento() {
        return this.diasFuncionamento;
    }

    public void setDiasFuncionamento(DiaFuncionamento diasFuncionamento) {
        this.diasFuncionamento = diasFuncionamento;
    }

    public Date getDfuDtmAbertura() {
        return this.dfuDtmAbertura;
    }

    public String getDfuDtmAberturaStr() {
        return Utils.dateToString(this.dfuDtmAbertura, "HH:mm");
    }

    public void setDfuDtmAbertura(Date dfuDtmAbertura) {
        this.dfuDtmAbertura = dfuDtmAbertura;
    }

    public Date getDfuDtmFechamento() {
        return this.dfuDtmFechamento;
    }

    public String getDfuDtmFechamentoStr() {
        return Utils.dateToString(this.dfuDtmFechamento, "HH:mm");
    }

    public void setDfuDtmFechamento(Date dfuDtmFechamento) {
        this.dfuDtmFechamento = dfuDtmFechamento;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((this.diasFuncionamento == null) ? 0 : this.diasFuncionamento.hashCode());
        result = prime * result + ((this.empresa == null) ? 0 : this.empresa.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        EmpresaDiaFuncionamento other = (EmpresaDiaFuncionamento) obj;
        if (this.diasFuncionamento == null) {
            if (other.diasFuncionamento != null) {
                return false;
            }
        } else if (!this.diasFuncionamento.equals(other.diasFuncionamento)) {
            return false;
        }
        if (this.empresa == null) {
            if (other.empresa != null) {
                return false;
            }
        } else if (!this.empresa.equals(other.empresa)) {
            return false;
        }
        return true;
    }
}
