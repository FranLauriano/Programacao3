package prefeitura.siab.apresentacao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.EnfermeiraController;
import prefeitura.siab.controller.UsuarioController;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;

@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class MyAccount implements Serializable{

	private static final long serialVersionUID = 1L;

	//ATRIBUTOS
	private @Autowired AcsController controllerAcs;
	private @Autowired UsuarioController controllerUsuario;
	private @Autowired EnfermeiraController controllerEnfermeira;
	private List<Enfermeira> supervisores;
	private Usuario usuario;
	private String senhaAntiga;
	private String senha1;
	private String senha2;
	private boolean admin;
	private boolean enfermeira;
	private boolean acs;

	//PROPRIEDADES
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	public String getSenhaAntiga() {
		return senhaAntiga;
	}
	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}
	public String getSenha1() {
		return senha1;
	}
	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}
	public String getSenha2() {
		return senha2;
	}
	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
	public boolean isAdmin() {
		return admin;
	}
	public void setAdmin(boolean admin) {
		this.admin = admin;
	}
	public boolean isEnfermeira() {
		return enfermeira;
	}
	public void setEnfermeira(boolean enfermeira) {
		this.enfermeira = enfermeira;
	}
	public boolean isAcs() {
		return acs;
	}
	public void setAcs(boolean acs) {
		this.acs = acs;
	}
	public List<Enfermeira> getSupervisores() {
		return supervisores;
	}
	public void setSupervisores(List<Enfermeira> supervisores) {
		this.supervisores = supervisores;
	}
	
	//CONSTRUTOR
	public MyAccount() {
		inicializar();	
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerEnfermeira = applicationContext.getBean(EnfermeiraController.class);
		supervisores = controllerEnfermeira.searchListEnfermeira(new Enfermeira());
	}


	//MÉTODOS
	public String saveUpdate(){
		FacesMessage message = new FacesMessage();
		
		try{
			controllerUsuario.updateMyAccount(this.usuario, this.senha1, this.senha2, this.senhaAntiga);
			message.setSummary("Parabéns! Alterações Salva com Sucesso.");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		reset();
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void reset(){
		this.setSenha1(null);
		this.setSenha2(null);
		this.setSenhaAntiga(null);
	}
	
	public void inicializar(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login bean = (Login) mapa.get("login");
		this.usuario = bean.getUsuario();
		if(this.usuario.getTipo().equals(TipoUsuario.ACS)){
			this.acs = true;
			this.enfermeira = false;
			this.admin = false;
		}else if(this.usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
			this.acs = false;
			this.enfermeira = true;
			this.admin = false;
		}else if(this.usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
			this.acs = false;
			this.enfermeira = false;
			this.admin = true;
		}
	}
	
	public void setEnfermeiras(Integer matricula){
		if(matricula == null || matricula == 0){
			usuario.getAcs().getSupervisor().setMatricula(null);
		}else{
			for(Enfermeira sup: supervisores){
				if(sup.getMatricula().equals(matricula)){
					usuario.getAcs().getSupervisor().setMatricula(matricula);
					break;
				}
			}
		}
	}
	public Integer getEnfermeiras(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				this.usuario.getAcs().getSupervisor().setMatricula(usuario.getMatricula());
				return usuario.getMatricula();
			}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
				if(this.usuario.getAcs().getSupervisor() != null){
					return this.usuario.getAcs().getSupervisor().getMatricula();					
				}else{
					return null;
				}
			}
		}
		return null;
	}
	
}
