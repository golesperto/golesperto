package com.sdp.managed;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.apache.log4j.Logger;
import org.primefaces.event.map.GeocodeEvent;
import org.primefaces.model.map.DefaultMapModel;
import org.primefaces.model.map.GeocodeResult;
import org.primefaces.model.map.LatLng;
import org.primefaces.model.map.MapModel;
import org.primefaces.model.map.Marker;

import com.sdp.bo.DiaFuncionamentoBO;
import com.sdp.bo.EmpresaBO;
import com.sdp.bo.EmpresaDiaFuncionamentoBO;
import com.sdp.entity.DiaFuncionamento;
import com.sdp.entity.Empresa;
import com.sdp.entity.EmpresaDiaFuncionamento;
import com.sdp.util.Mensagens;
import com.sdp.util.Status;
import com.sdp.util.UF;

@ManagedBean
@ViewScoped
public class EmpresaMB extends GenericManagedBean<Empresa> {

	private static final long serialVersionUID = 9116181950356658019L;

	private static Logger log = Logger.getLogger(MenuMB.class);

	private List<SelectItem> status, ufs;

	private EmpresaDiaFuncionamento empresaDiaFuncionamento;

	private EmpresaDiaFuncionamentoBO empresaDiaFuncionamentoBO;

	private List<DiaFuncionamento> diasFuncionamento, diasFuncionamentoSelecionado;

	private DiaFuncionamentoBO diaFuncionamentoBO;

	private MapModel mapModel;

	private Double lat, latSelecionado, lng, lngSelecionado;

	private String centerGeoMap = "-25.4340512, -49.275354199999995";

	public EmpresaMB() {
		setBo(new EmpresaBO());
		setClazz(Empresa.class);
		this.empresaDiaFuncionamentoBO = new EmpresaDiaFuncionamentoBO();
		this.diaFuncionamentoBO = new DiaFuncionamentoBO();
		mapModel = new DefaultMapModel();
		carregarEntityList();
		carregarStatus();
		carregarUfs();
		carregarDiasFuncionamento();
	}

	public void handlePointClick() {

	}

	public void carregarDiasFuncionamento() {
		try {
			this.diasFuncionamento = this.diaFuncionamentoBO.listAll();
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void carregarStatus() {
		this.status = new ArrayList<>();
		Status.getList().forEach(s -> this.status.add(new SelectItem(s.getChave(), s.getValor())));
	}

	public void carregarUfs() {
		this.ufs = new ArrayList<>();
		UF.getList().forEach(u -> this.ufs.add(new SelectItem(u.getChave(), u.getValor())));
	}

	private void carregarEntityList() {
		try {
			if (isUsuarioAdministrador()) {
				setEntityList(((EmpresaBO) getBo()).listAll());
			} else {
				setEntityList(((EmpresaBO) getBo()).listAllSemAdm());
			}
		} catch (Exception e) {
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), "");
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_BUSCAR_REGISTROS), e);
		}
	}

	public void onGeocode(GeocodeEvent event) {
		List<GeocodeResult> results = event.getResults();
		if (results != null && !results.isEmpty()) {
			LatLng center = results.get(0).getLatLng();
			centerGeoMap = center.getLat() + "," + center.getLng();
		}
	}

	@Override
	public void setEntity(Empresa entity) {
		super.setEntity(entity);
		carregarGMaps(entity);
	}

	private void carregarGMaps(Empresa entity) {
		if (entity.getEmpStrLatitude() != null && !entity.getEmpStrLatitude().isEmpty()
				&& entity.getEmpStrLongitude() != null && !entity.getEmpStrLongitude().isEmpty()) {
			latSelecionado = Double.parseDouble(entity.getEmpStrLatitude());
			lngSelecionado = Double.parseDouble(entity.getEmpStrLongitude());
			centerGeoMap = latSelecionado + "," + lngSelecionado;
			LatLng center = new LatLng(latSelecionado, lngSelecionado);
			mapModel.addOverlay(new Marker(center, entity.getEmpStrEndereco()));
		}
	}

	@Override
	public void actionPersist(ActionEvent event) {
		try {
			getEntity().setEmpStrLatitude(latSelecionado + "");
			getEntity().setEmpStrLongitude(lngSelecionado + "");
			((EmpresaBO) getBo()).persist(getEntity());
			carregarEntityList();
			carregarEmpresas();
			actionNew(event);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_SALVO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
		}
	}

	public void actionPersistDias() {
		try {
			Set<EmpresaDiaFuncionamento> edfs = new HashSet<>();
			for (DiaFuncionamento df : this.diasFuncionamentoSelecionado) {
				EmpresaDiaFuncionamento edf = null;
				edf = new EmpresaDiaFuncionamento(getEntity(), df, this.empresaDiaFuncionamento.getDfuDtmAbertura(),
						this.empresaDiaFuncionamento.getDfuDtmFechamento());
				edfs.add(edf);
			}
			if (getEntity().getEmpresaDias() != null) {
				getEntity().getEmpresaDias().addAll(edfs);
			} else {
				getEntity().setEmpresaDias(edfs);
			}
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_SALVAR_REGISTRO), "");
		}
	}

	@Override
	public void actionRemove(ActionEvent event) {
		try {
			((EmpresaBO) getBo()).remove(getEntity());
			carregarEntityList();
			carregarEmpresas();
			actionNew(event);
			addInfo(Mensagens.getMensagem(Mensagens.REGISTRO_REMOVIDO_COM_SUCESSO), "");
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public void actionRemoveDias() {
		try {
			getEntity().getEmpresaDias().remove(this.empresaDiaFuncionamento);
			if (this.empresaDiaFuncionamento.getEdiIntCod() != null) {
				this.empresaDiaFuncionamentoBO.remove(this.empresaDiaFuncionamento);
			}
		} catch (Exception e) {
			log.error(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), e);
			addError(Mensagens.getMensagem(Mensagens.ERRO_AO_REMOVER_REGISTRO), "");
		}
	}

	public void actionNewDias() {
		this.empresaDiaFuncionamento = new EmpresaDiaFuncionamento();
		diasFuncionamentoSelecionado = new ArrayList<>();
	}

	public List<SelectItem> getStatus() {
		return this.status;
	}

	public void setStatus(List<SelectItem> status) {
		this.status = status;
	}

	public List<SelectItem> getUfs() {
		return this.ufs;
	}

	public void setUfs(List<SelectItem> ufs) {
		this.ufs = ufs;
	}

	public EmpresaDiaFuncionamento getEmpresaDiaFuncionamento() {
		return this.empresaDiaFuncionamento;
	}

	public void setEmpresaDiaFuncionamento(EmpresaDiaFuncionamento empresaDiaFuncionamento) {
		this.empresaDiaFuncionamento = empresaDiaFuncionamento;
		diasFuncionamentoSelecionado = new ArrayList<>();
		diasFuncionamentoSelecionado.add(empresaDiaFuncionamento.getDiasFuncionamento());
	}

	public List<DiaFuncionamento> getDiasFuncionamento() {
		return this.diasFuncionamento;
	}

	public void setDiasFuncionamento(List<DiaFuncionamento> diasFuncionamento) {
		this.diasFuncionamento = diasFuncionamento;
	}

	public EmpresaDiaFuncionamentoBO getEmpresaDiaFuncionamentoBO() {
		return this.empresaDiaFuncionamentoBO;
	}

	public void setEmpresaDiaFuncionamentoBO(EmpresaDiaFuncionamentoBO empresaDiaFuncionamentoBO) {
		this.empresaDiaFuncionamentoBO = empresaDiaFuncionamentoBO;
	}

	public List<DiaFuncionamento> getDiasFuncionamentoSelecionado() {
		return this.diasFuncionamentoSelecionado;
	}

	public void setDiasFuncionamentoSelecionado(List<DiaFuncionamento> diasFuncionamentoSelecionado) {
		this.diasFuncionamentoSelecionado = diasFuncionamentoSelecionado;
	}

	public MapModel getMapModel() {
		return mapModel;
	}

	public void setMapModel(MapModel mapModel) {
		this.mapModel = mapModel;
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

	public Double getLatSelecionado() {
		return latSelecionado;
	}

	public void setLatSelecionado(Double latSelecionado) {
		this.latSelecionado = latSelecionado;
	}

	public Double getLngSelecionado() {
		return lngSelecionado;
	}

	public void setLngSelecionado(Double lngSelecionado) {
		this.lngSelecionado = lngSelecionado;
	}

	public String getCenterGeoMap() {
		return centerGeoMap;
	}

	public void setCenterGeoMap(String centerGeoMap) {
		this.centerGeoMap = centerGeoMap;
	}
}
