package prefeitura.siab.apresentacao;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.tabela.Acs;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class Login implements Serializable{

	private static final long serialVersionUID = 1L;

	//ATRIBUTOS
	private @Autowired AcsController controller;
	private Acs servidor;
	private Integer matricula;
	private Integer senha;

	//PROPRIEDADES
	public Acs getServidor() {
		return servidor;
	}
	public void setServidor(Acs servidor) {
		this.servidor = servidor;
	}
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public Integer getSenha() {
		return senha;
	}
	public void setSenha(Integer senha) {
		this.senha = senha;
	}
	
	
	//CONSTRUTOR
	public Login() {
		
	}
	
	//MÉTODOS
	public String entrar(){
		FacesMessage message = new FacesMessage();
		if(matricula != null && matricula != 0){
			Acs aux = new Acs();
			aux.setMatricula(matricula);
			aux.setMicroarea(senha);
			servidor = controller.searchServidor(aux);
			if(servidor == null){
				message.setSummary("Matricula e/ou Senha Incorretas");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				matricula = null;
				senha = null;
			}else{
				matricula = null;
				senha = null;
				return "inicio";
			}
		}else{
			message.setSummary("Matricula e/ou Senha Incorretas");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			matricula = null;
			senha = null;
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public String sair(){
		this.servidor = null;
		return "sair";
	}
	
	public String voltar(){
		return "inicio";
	}
	
	
}
