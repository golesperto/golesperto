package com.sdp.managed;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.apache.log4j.Logger;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.sdp.bo.EmpresaProdutoBO;
import com.sdp.bo.MarcaBO;
import com.sdp.bo.ProdutoBO;
import com.sdp.bo.ProdutoTipoBO;
import com.sdp.entity.Empresa;
import com.sdp.entity.EmpresaProduto;
import com.sdp.entity.Marca;
import com.sdp.entity.Produto;
import com.sdp.entity.ProdutoTipo;
import com.sdp.util.Mapa;
import com.sdp.util.Mensagens;
import com.sdp.util.Utils;

@ManagedBean
@ViewScoped
public class DashboardMB extends GenericManagedBean<EmpresaProduto> {
	private static final long serialVersionUID = 1L;

	private static Logger log = Logger.getLogger(DashboardMB.class);

	private boolean mostrarDetalhes = false;

	private MapModel mapModel;

	private String centerGeoMap = "25.4340512, -49.275354199999995";

	private Double lat, lng;

	private List<Produto> produtos;

	private Produto produtoSelecionado;

	private ProdutoBO produtoBO;

	private List<ProdutoTipo> produtoTipos;

	private ProdutoTipo produtoTipoSelecionado;

	private ProdutoTipoBO produtoTipoBO;

	private List<Marca> marcas;

	private Marca marcaSelecionada;

	private MarcaBO marcaBO;

	private Integer distancia;

	public DashboardMB() {
		setClazz(EmpresaProduto.class);
		setBo(new EmpresaProdutoBO());
		produtoBO = new ProdutoBO();
		produtoTipoBO = new ProdutoTipoBO();
		marcaBO = new MarcaBO();
		carregarMarcas();
		carregarProdutos();
		carregarProdutoTipos();
		carregarEntityList();
		mapModel = new DefaultMapModel();
	}

	public void carregarMarcas() {
		try {
			marcas = marcaBO.listAllOrdenado();
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarProdutos() {
		try {
			produtos = produtoBO.listAllOrdenado();
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarProdutoTipos() {
		try {
			produtoTipos = produtoTipoBO.listAllOrdenado();
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarEntityList() {
		try {
			List<Empresa> empresas;
			if (distancia != null && lat != null && lng != null) {
				empresas = new ArrayList<>();
				for (Empresa e : getEmpresas()) {
					if (Utils.isNotNullOrNotEmpty(e.getEmpStrLatitude())
							&& Utils.isNotNullOrNotEmpty(e.getEmpStrLongitude())) {
						if (Mapa.distanciaEntrePontosKM(lat, lng, Double.parseDouble(e.getEmpStrLatitude()),
								Double.parseDouble(e.getEmpStrLongitude())) <= distancia) {
							empresas.add(e);
						}
					}
				}
				if (!empresas.isEmpty()) {
					setEntityList(((EmpresaProdutoBO) getBo()).listByFiltro(empresas, marcaSelecionada,
							produtoTipoSelecionado, produtoSelecionado));
				} else {
					setEntityList(new ArrayList<>());
				}
			} else {
				setEntityList(((EmpresaProdutoBO) getBo()).listByFiltro(getEmpresaSelecionada(), marcaSelecionada,
						produtoTipoSelecionado, produtoSelecionado));
			}
		} catch (Exception e) {
			e.printStackTrace();
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		getEntity().setEmpresa(getEmpresaSelecionada());
		super.actionPersist(event);
		carregarEntityList();
	}

	@Override
	public void actionRemove(ActionEvent event) {
		super.actionRemove(event);
		carregarEntityList();
	}

	public void actionVoltar() {
		mostrarDetalhes = false;
	}

	private void carregarGMaps(Empresa entity) {
		if (entity.getEmpStrLatitude() != null && !entity.getEmpStrLatitude().isEmpty()
				&& entity.getEmpStrLongitude() != null && !entity.getEmpStrLongitude().isEmpty()) {
			Double latSelecionado = Double.parseDouble(entity.getEmpStrLatitude());
			Double lngSelecionado = Double.parseDouble(entity.getEmpStrLongitude());
			centerGeoMap = latSelecionado + "," + lngSelecionado;
			LatLng center = new LatLng(latSelecionado, lngSelecionado);
			mapModel.addOverlay(new Marker(center, entity.getEmpStrEndereco()));
		}
	}

	@Override
	public void actionNew(ActionEvent event) {
		setEntity(new EmpresaProduto());
	}

	public void onGeocode(GeocodeEvent event) {
		List<GeocodeResult> results = event.getResults();
		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();
		}
	}

	public void setCarregarDetalhes(EmpresaProduto empresaProduto) {
		setEntity(empresaProduto);
		carregarGMaps(getEntity().getEmpresa());
		mostrarDetalhes = true;
	}

	public boolean isMostrarDetalhes() {
		return mostrarDetalhes;
	}

	public void setMostrarDetalhes(boolean mostrarDetalhes) {
		this.mostrarDetalhes = mostrarDetalhes;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
	}

	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public Produto getProdutoSelecionado() {
		return produtoSelecionado;
	}

	public void setProdutoSelecionado(Produto produtoSelecionado) {
		this.produtoSelecionado = produtoSelecionado;
	}

	public List<ProdutoTipo> getProdutoTipos() {
		return produtoTipos;
	}

	public void setProdutoTipos(List<ProdutoTipo> produtoTipos) {
		this.produtoTipos = produtoTipos;
	}

	public ProdutoTipo getProdutoTipoSelecionado() {
		return produtoTipoSelecionado;
	}

	public void setProdutoTipoSelecionado(ProdutoTipo produtoTipoSelecionado) {
		this.produtoTipoSelecionado = produtoTipoSelecionado;
	}

	public List<Marca> getMarcas() {
		return marcas;
	}

	public void setMarcas(List<Marca> marcas) {
		this.marcas = marcas;
	}

	public Marca getMarcaSelecionada() {
		return marcaSelecionada;
	}

	public void setMarcaSelecionada(Marca marcaSelecionada) {
		this.marcaSelecionada = marcaSelecionada;
	}

	public Integer getDistancia() {
		return distancia;
	}

	public void setDistancia(Integer distancia) {
		this.distancia = distancia;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLng() {
		return lng;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}
}