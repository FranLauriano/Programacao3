package prefeitura.siab.apresentacao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Enfermeira;

public class SupForm {
	
	//ATRIBUTOS
	private @Autowired AcsController controllerAcs;
	private List<Acs> agentes;
	private List<AgenteForm> result;
	private Integer tfamilias;
	private Integer tpessoas;
	private Integer thipertensos;
	private Integer tdiabetes;
	private Integer ttubercolosos;
	private Integer thanseniase;
	private Integer tmenosde1;
	private Integer tmenosde2;
	private Integer tmenosde5;
	private Integer tacimade60;
	private Enfermeira supervisora;
	
	//PROPRIEDADES
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	public List<AgenteForm> getResult() {
		return result;
	}
	public void setResult(List<AgenteForm> result) {
		this.result = result;
	}
	public Integer getTfamilias() {
		return tfamilias;
	}
	public void setTfamilias(Integer tfamilias) {
		this.tfamilias = tfamilias;
	}
	public Integer getTpessoas() {
		return tpessoas;
	}
	public void setTpessoas(Integer tpessoas) {
		this.tpessoas = tpessoas;
	}
	public Integer getThipertensos() {
		return thipertensos;
	}
	public void setThipertensos(Integer thipertensos) {
		this.thipertensos = thipertensos;
	}
	public Integer getTdiabetes() {
		return tdiabetes;
	}
	public void setTdiabetes(Integer tdiabetes) {
		this.tdiabetes = tdiabetes;
	}
	public Integer getTtubercolosos() {
		return ttubercolosos;
	}
	public void setTtubercolosos(Integer ttubercolosos) {
		this.ttubercolosos = ttubercolosos;
	}
	public Integer getThanseniase() {
		return thanseniase;
	}
	public void setThanseniase(Integer thanseniase) {
		this.thanseniase = thanseniase;
	}
	public Integer getTmenosde1() {
		return tmenosde1;
	}
	public void setTmenosde1(Integer tmenosde1) {
		this.tmenosde1 = tmenosde1;
	}
	public Integer getTmenosde2() {
		return tmenosde2;
	}
	public void setTmenosde2(Integer tmenosde2) {
		this.tmenosde2 = tmenosde2;
	}
	public Integer getTmenosde5() {
		return tmenosde5;
	}
	public void setTmenosde5(Integer tmenosde5) {
		this.tmenosde5 = tmenosde5;
	}
	public Integer getTacimade60() {
		return tacimade60;
	}
	public void setTacimade60(Integer tacimade60) {
		this.tacimade60 = tacimade60;
	}
	public Enfermeira getSupervisora() {
		return supervisora;
	}
	public void setSupervisora(Enfermeira supervisora) {
		this.supervisora = supervisora;
	}
	
	//CONSTRUTOR
	public SupForm() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);

		inicializar();
	}
	
	public void inicializar(){
		if(supervisora != null){
			initVariaveis();
			AcsSearchOptions auxiliar = new AcsSearchOptions();
			auxiliar.setSupervisora(supervisora);
			agentes = controllerAcs.searchListAcs(auxiliar);
			for(Acs agente: agentes){
				AgenteForm form = new AgenteForm();
				form.setAgente(agente);
				form.inicializar();
				result.add(form);
				this.tfamilias += form.getFamilias();
				this.tpessoas += form.getPessoas();
				this.thipertensos += form.getHipertensos();
				this.tdiabetes += form.getDiabetes();
				this.ttubercolosos += form.getTubercolosos();
				this.thanseniase += form.getHanseniase();
				this.tmenosde1 += form.getMenosde1();
				this.tmenosde2 += form.getMenosde2();
				this.tmenosde5 += form.getMenosde5();
				this.tacimade60 += form.getAcimade60();
			}
		}
	}
	
	public void initVariaveis(){
		this.tfamilias = 0;
		this.tpessoas = 0;
		this.tdiabetes = 0;
		this.thanseniase = 0;
		this.ttubercolosos = 0;
		this.thipertensos = 0;
		this.tacimade60 = 0;
		this.tmenosde1 = 0;
		this.tmenosde2 = 0;
		this.tmenosde5 = 0;
		result = new ArrayList<>();
	}
	
}
