package com.sdp.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Query;

import org.apache.log4j.Logger;

import com.sdp.entity.Empresa;
import com.sdp.entity.EmpresaProduto;
import com.sdp.entity.Marca;
import com.sdp.entity.Produto;
import com.sdp.entity.ProdutoTipo;
import com.sdp.exception.BancoDadosException;
import com.sdp.util.Utils;

public class EmpresaProdutoBO extends BO<EmpresaProduto> {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(EmpresaProdutoBO.class);

	public EmpresaProdutoBO() {
		super();
		setClazz(EmpresaProduto.class);
	}

	public List<EmpresaProduto> listByEmpresa(Integer empIntCod) throws BancoDadosException {
		Map<String, Object> filtros = new HashMap<String, Object>();
		filtros.put("empresa.empIntCod", empIntCod);
		return super.listByFields(filtros);
	}

	public List<EmpresaProduto> listByFiltro(Empresa empresa, Marca marca, ProdutoTipo produtoTipo, Produto produto)
			throws Exception {
		List<Empresa> empresas = new ArrayList<>();
		empresas.add(empresa);
		return listByFiltro(empresas, marca, produtoTipo, produto);
	}

	public List<EmpresaProduto> listByFiltro(List<Empresa> empresas, Marca marca, ProdutoTipo produtoTipo,
			Produto produto) throws Exception {
		String filtroProduto = "";
		String filtroMarca = "";
		String filtroProdutoTipo = "";
		String filtroEmpresa = "";
		if (empresas != null && !empresas.isEmpty()) {
			String in = tratarCamposInEmpresa(empresas);
			if (Utils.isNotNullOrNotEmpty(in)) {
				filtroEmpresa = "AND EMP.EMP_INT_COD IN (" + tratarCamposInEmpresa(empresas) + ") ";
			}
		}
		if (marca != null) {
			filtroMarca = "AND MAR.MAR_INT_COD = " + marca.getMarIntCod() + " ";
		}
		if (produtoTipo != null) {
			filtroProdutoTipo = "AND PRT.PRT_INT_COD = " + produtoTipo.getPrtIntCod() + " ";
		}
		if (produto != null) {
			filtroProduto = "AND PRO.PRO_INT_COD = " + produto.getProIntCod() + " ";
		}
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT EPR.* FROM SDP_EMPRESA_PRODUTO EPR ");
		sb.append("INNER JOIN SDP_EMPRESA EMP ON EMP.EMP_INT_COD = EPR.EMP_INT_COD " + filtroEmpresa);
		sb.append("INNER JOIN SDP_PRODUTO PRO ON PRO.PRO_INT_COD = EPR.PRO_INT_COD " + filtroProduto);
		sb.append("INNER JOIN SDP_PRODUTO_TIPO PRT ON PRT.PRT_INT_COD = PRO.PRT_INT_COD " + filtroProdutoTipo);
		sb.append("INNER JOIN SDP_MARCA MAR ON MAR.MAR_INT_COD = PRO.MAR_INT_COD " + filtroMarca);
		Query q = getDao().createNativeQuery(sb.toString(), EmpresaProduto.class);
		return q.getResultList();
	}
}