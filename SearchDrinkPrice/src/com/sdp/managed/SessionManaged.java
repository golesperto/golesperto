package com.sdp.managed;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuModel;

import com.sdp.bo.EmpresaBO;
import com.sdp.bo.MenuBO;
import com.sdp.bo.ObjetoBO;
import com.sdp.bo.PerfilBO;
import com.sdp.bo.PerfilObjetoBO;
import com.sdp.entity.Empresa;
import com.sdp.entity.Menu;
import com.sdp.entity.Objeto;
import com.sdp.entity.PerfilObjeto;
import com.sdp.entity.Usuario;
import com.sdp.exception.BancoDadosException;
import com.sdp.util.JSFHelper;
import com.sdp.util.Status;

@ManagedBean
@SessionScoped
public class SessionManaged extends GenericManagedBean<Usuario> {
	private static final long serialVersionUID = 1L;

	private Usuario usuarioLogado, usuarioSelecionado;

	private Empresa empresaLogada, empresaSelecionada;

	private static Logger log = Logger.getLogger(SessionManaged.class);

	private MenuModel menu = new DefaultMenuModel();

	private List<Usuario> usuarios;

	private List<Empresa> empresas;

	private List<Objeto> objetos;

	private EmpresaBO empresaBO;

	private PerfilBO perfilBO;

	private ObjetoBO objetoBO;

	private PerfilObjetoBO perfilObjetoBO;

	private MenuBO menuBO;

	public SessionManaged() {
		setClazz(Usuario.class);
		empresaBO = new EmpresaBO();
		perfilBO = new PerfilBO();
		objetoBO = new ObjetoBO();
		perfilObjetoBO = new PerfilObjetoBO();
		menuBO = new MenuBO();
		carregarEmpresas();
	}

	public void carregarMenu() {
		try {
			if (usuarioLogado != null) {
				objetos = new ArrayList<>();
				List<PerfilObjeto> listPerfilObjetos = perfilObjetoBO
						.listByIdPerfil(usuarioLogado.getPerfil().getPerIntCod());
				if (listPerfilObjetos != null && !listPerfilObjetos.isEmpty()) {
					menu = new DefaultMenuModel();
					Set<Menu> menusSet = new HashSet<>();
					listPerfilObjetos.forEach(po -> {
						if (po.getObjeto() != null && po.getObjeto().getMenu() != null) {
							menusSet.add(po.getObjeto().getMenu());
						}
					});
					listPerfilObjetos
							.sort((o1, o2) -> o1.getObjeto().getObjIntCod().compareTo(o2.getObjeto().getObjIntCod()));
					List<Menu> menus = new ArrayList<>(menusSet);
					menus.sort((o1, o2) -> o1.getMenStrNome().compareTo(o2.getMenStrNome()));
					menus.forEach(m -> {
						DefaultSubMenu menuMod = new DefaultSubMenu(m.getMenStrNome());
						if (m.getMenStrIcone() != null && !m.getMenStrIcone().isEmpty()) {
							menuMod.setIcon(m.getMenStrIcone());
						}
						listPerfilObjetos.forEach(obj -> {
							if (obj != null && obj.getObjeto() != null
									&& obj.getObjeto().getMenu().getMenIntCod() == m.getMenIntCod()
									&& obj.getObjeto().getObjStrDescricao() != null
									&& !obj.getObjeto().getObjStrDescricao().isEmpty()
									&& obj.getObjeto().getObjStrObjeto() != null
									&& !obj.getObjeto().getObjStrObjeto().isEmpty()) {
								objetos.add(obj.getObjeto());
								addMenuItem(menuMod, obj.getObjeto());
							}
						});
						if (menuMod.getElementsCount() > 0)
							menu.addElement(menuMod);
					});
				} else {
					JSFHelper.redirect("login.jsf");
				}
				DefaultSubMenu miSenha = new DefaultSubMenu("Configurações");
				miSenha.setIcon("fa fa-cog");
				DefaultMenuItem itemSenha = new DefaultMenuItem("Trocar Senha");
				itemSenha.setIcon("fa fa-key");
				itemSenha.setUrl("troca.jsf");
				itemSenha.setId("troca");
				DefaultMenuItem miLogoff = new DefaultMenuItem();
				miLogoff.setUrl("login.jsf");
				miLogoff.setValue("Sair");
				miLogoff.setIcon("fa fa-power-off");
				miSenha.addElement(itemSenha);
				menu.addElement(miSenha);
				menu.addElement(miLogoff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isUsuarioAdministrador() {
		if (getUsuarioLogado().getPerfil().getPerChaTipo().equals("A")) {
			return true;
		} else {
			return false;
		}
	}

	private void addMenuItem(DefaultSubMenu menuMod, Objeto obj) {
		DefaultMenuItem itemMod = new DefaultMenuItem(obj.getObjStrDescricao());
		itemMod.setUrl(obj.getObjStrObjeto());
		itemMod.setEscape(false);
		itemMod.setId(obj.getObjStrObjeto());
		menuMod.addElement(itemMod);
		if (obj.getObjStrIcone() != null && !obj.getObjStrIcone().isEmpty()) {
			itemMod.setIcon(obj.getObjStrIcone());
		}
	}

	public void carregarEmpresas() {
		try {
			empresas = empresaBO.listAll();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Usuario getUsuarioLogado() {
		return usuarioLogado;
	}

	public void setUsuarioLogado(Usuario usuarioLogado) {
		this.usuarioLogado = usuarioLogado;
		setUsuarioSelecionado(usuarioLogado);
		if (usuarioLogado != null) {
			setEmpresaLogada(usuarioLogado.getEmpresa());
		}
	}

	public MenuModel getMenu() {
		return menu;
	}

	public void setMenu(MenuModel menu) {
		this.menu = menu;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

	public List<Empresa> getEmpresas() {
		if (empresas == null) {
			carregarEmpresas();
		}
		return empresas;
	}

	public void setEmpresas(List<Empresa> empresas) {
		this.empresas = empresas;
	}

	public Empresa getEmpresaLogada() {
		return empresaLogada;
	}

	public void setEmpresaLogada(Empresa empresaLogada) {
		this.empresaLogada = empresaLogada;
	}

	public Empresa getEmpresaSelecionada() {
		return empresaSelecionada;
	}

	public void setEmpresaSelecionada(Empresa empresaSelecionada) {
		this.empresaSelecionada = empresaSelecionada;
	}

	public List<Objeto> getObjetos() {
		return objetos;
	}

	public void setObjetos(List<Objeto> objetos) {
		this.objetos = objetos;
	}
}
