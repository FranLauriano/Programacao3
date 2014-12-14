package prefeitura.siab.apresentacao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.DoencaController;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;

public class AgenteForm {

	//ATRIBUTOS
	private @Autowired AcsController controllerAcs;
	private @Autowired FamiliaController controllerFamilia;
	private @Autowired DoencaController controllerDoenca;
	private @Autowired PessoaController controllerPessoa;
	private Acs agente;
	private Integer familias;
	private Integer pessoas;
	private Integer hipertensos;
	private Integer diabetes;
	private Integer tubercolosos;
	private Integer hanseniase;
	private Integer menosde1;
	private Integer menosde2;
	private Integer menosde5;
	private Integer acimade60;
	
	//PROPRIEDADES
	public Acs getAgente() {
		return agente;
	}
	public void setAgente(Acs agente) {
		this.agente = agente;
	}
	public Integer getFamilias() {
		return familias;
	}
	public void setFamilias(Integer familias) {
		this.familias = familias;
	}
	public Integer getPessoas() {
		return pessoas;
	}
	public void setPessoas(Integer pessoas) {
		this.pessoas = pessoas;
	}
	public Integer getHipertensos() {
		return hipertensos;
	}
	public void setHipertensos(Integer hipertensos) {
		this.hipertensos = hipertensos;
	}
	public Integer getDiabetes() {
		return diabetes;
	}
	public void setDiabetes(Integer diabetes) {
		this.diabetes = diabetes;
	}
	public Integer getTubercolosos() {
		return tubercolosos;
	}
	public void setTubercolosos(Integer tubercolosos) {
		this.tubercolosos = tubercolosos;
	}
	public Integer getHanseniase() {
		return hanseniase;
	}
	public void setHanseniase(Integer hanseniase) {
		this.hanseniase = hanseniase;
	}
	public Integer getMenosde1() {
		return menosde1;
	}
	public void setMenosde1(Integer menosde1) {
		this.menosde1 = menosde1;
	}
	public Integer getMenosde2() {
		return menosde2;
	}
	public void setMenosde2(Integer menosde2) {
		this.menosde2 = menosde2;
	}
	public Integer getMenosde5() {
		return menosde5;
	}
	public void setMenosde5(Integer menosde5) {
		this.menosde5 = menosde5;
	}
	public Integer getAcimade60() {
		return acimade60;
	}
	public void setAcimade60(Integer acimade60) {
		this.acimade60 = acimade60;
	}
	
	
	//CONSTRUTOR
	public AgenteForm() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerFamilia = applicationContext.getBean(FamiliaController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
		controllerPessoa = applicationContext.getBean(PessoaController.class);

		inicializar();
	}
		
	//MÉTODOS
	public void inicializar(){
		if(agente != null){
			initVariaveis();
			//QUANTIDADE DE FAMÍLIAS
			Familia familiaAuxiliar = new Familia();
			familiaAuxiliar.setAgente(agente);
			List<Familia> lista = controllerFamilia.searchFamilia(familiaAuxiliar);
			familias = lista.size();
			
			//QUANTIDADE DE PESSOAS
			for(Familia familia: lista){
				pessoas += familia.getPessoas().size();					
			}
			
			
			//QUANTITATIVO DE DOENÇAS
			List<Doenca> listaDoencas = controllerDoenca.searchListDoenca(new Doenca());
			for(Doenca doenca: listaDoencas){
				
				if(doenca.getSigla().toLowerCase().equals("dia")){
					PessoaSearchOptions pessoaAuxiliar = new PessoaSearchOptions();
					pessoaAuxiliar.setDoencas(new ArrayList<Doenca>());
					pessoaAuxiliar.getDoencas().add(doenca);
					diabetes = controllerPessoa.searchListPessoa(pessoaAuxiliar).size();
				}else if(doenca.getSigla().toLowerCase().equals("ha")){
					PessoaSearchOptions pessoaAuxiliar = new PessoaSearchOptions();
					pessoaAuxiliar.setDoencas(new ArrayList<Doenca>());
					pessoaAuxiliar.getDoencas().add(doenca);
					hipertensos = controllerPessoa.searchListPessoa(pessoaAuxiliar).size();
				}else if(doenca.getSigla().toLowerCase().equals("han")){
					PessoaSearchOptions pessoaAuxiliar = new PessoaSearchOptions();
					pessoaAuxiliar.setDoencas(new ArrayList<Doenca>());
					pessoaAuxiliar.getDoencas().add(doenca);
					hanseniase = controllerPessoa.searchListPessoa(pessoaAuxiliar).size();
				}else if(doenca.getSigla().toLowerCase().equals("tb")){
					PessoaSearchOptions pessoaAuxiliar = new PessoaSearchOptions();
					pessoaAuxiliar.setDoencas(new ArrayList<Doenca>());
					pessoaAuxiliar.getDoencas().add(doenca);
					tubercolosos = controllerPessoa.searchListPessoa(pessoaAuxiliar).size();
				}
			}
			
			//QUANTITATIVO DE CRIANÇAS
			List<Pessoa> listaPessoas = controllerPessoa.searchPessoa(new Pessoa());
			for(Pessoa pessoa: listaPessoas){
				if(pessoa.getIdade() < 1){
					menosde1++;
				}else if(pessoa.getIdade() < 2){
					menosde2++;
				}else if(pessoa.getIdade() < 5){
					menosde5++;
				}else if(pessoa.getIdade() > 60){
					acimade60++;
				}
			}
		}
	}
	
	
	public void initVariaveis(){
		this.familias = 0;
		this.pessoas = 0;
		this.diabetes = 0;
		this.hanseniase = 0;
		this.tubercolosos = 0;
		this.hipertensos = 0;
		this.acimade60 = 0;
		this.menosde1 = 0;
		this.menosde2 = 0;
		this.menosde5 = 0;
	}
}
