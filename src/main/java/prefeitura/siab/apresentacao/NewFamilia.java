package prefeitura.siab.apresentacao;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import prefeitura.siab.controller.AcsController;
import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.DoencaController;
import prefeitura.siab.controller.EnderecoController;
import prefeitura.siab.controller.EscolaridadeController;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.controller.RacaController;
import prefeitura.siab.controller.VinculoController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Endereco;
import prefeitura.siab.tabela.Escolaridade;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;
import prefeitura.siab.tabela.Raca;
import prefeitura.siab.tabela.VinculoEmpregaticio;


@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class NewFamilia {

	//ATRIBUTOS
	private @Autowired FamiliaController familiaController;
	//private Facade facade;
	private AcsController controllerAcs;
	private EnderecoController controllerEndereco;
	private PessoaController controllerPessoa;
	private Familia familia;
	private Pessoa aux;
	private List<Pessoa> pessoas;
	private List<Endereco> endereco;
	private List<Acs> agentes; 
	
	//RAÇAS
	private RacaController controllerRaca;
	private List<Raca> racas;
	//ESCOLARIDADES
	private EscolaridadeController controllerEscolaridade;
	private List<Escolaridade> escolaridades;
	//VINCULOS EMPREGATICIOS
	private VinculoController controllerVinculo;
	private List<VinculoEmpregaticio> vinculos;
	//DOENÇAS
	private DoencaController controllerDoenca;
	private List<Doenca> doencas;
	
	//PROPRIEDADES
	
	public Familia getFamilia() {
		return familia;
	}
	public List<Raca> getRacas() {
		return racas;
	}
	public void setRacas(List<Raca> racas) {
		this.racas = racas;
	}
	public List<Escolaridade> getEscolaridades() {
		return escolaridades;
	}
	public void setEscolaridades(List<Escolaridade> escolaridades) {
		this.escolaridades = escolaridades;
	}
	public List<VinculoEmpregaticio> getVinculos() {
		return vinculos;
	}
	public void setVinculos(List<VinculoEmpregaticio> vinculos) {
		this.vinculos = vinculos;
	}
	public List<Doenca> getDoencas() {
		return doencas;
	}
	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	public Pessoa getAux() {
		return aux;
	}
	public void setAux(Pessoa aux) {
		this.aux = aux;
	}
	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}
	public List<Endereco> getEndereco() {
		return endereco;
	}
	public void setEndereco(List<Endereco> endereco) {
		this.endereco = endereco;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	
	//CONSTRUTOR
	public NewFamilia() {
		
		resetFamilia();
		resetAux();
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerEndereco = applicationContext.getBean(EnderecoController.class);
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		endereco = controllerEndereco.searchListEndereco(new Endereco());
		
		controllerPessoa = applicationContext.getBean(PessoaController.class);
		controllerRaca = applicationContext.getBean(RacaController.class);
		controllerEscolaridade = applicationContext.getBean(EscolaridadeController.class);
		controllerVinculo = applicationContext.getBean(VinculoController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
	
		racas = controllerRaca.searchRaca(new Raca());
		escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
		vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
		doencas = controllerDoenca.searchListDoenca(new Doenca());
	}
	
	public void resetFamilia(){
		familia = new Familia();
		familia.setAgente(new Acs());
		familia.setRua(new Endereco());
		List<Pessoa> lista = new ArrayList<>();
		familia.setPessoas(lista);
	}
	
	public void resetAux(){
		aux = new Pessoa();
		aux.setFamilia(new Familia());
		aux.setEscolaridade(new Escolaridade());
		aux.setRaca(new Raca());
		aux.setVinculo(new VinculoEmpregaticio());
		List<Doenca> situacao = new ArrayList<>();
		aux.setSituacao(situacao);
	}

	//MÉTODOS
	public String saveFamilia(){
		FacesMessage message = new FacesMessage();
		try{
			if(familia.getPessoas().size() != 0){
				familiaController.salvarFamilia(familia);
				for(Pessoa pessoa: familia.getPessoas()){
					pessoa.setFamilia(familia);
					controllerPessoa.salvarPessoa(pessoa);
				}
				resetFamilia();
				message.setSummary("Família Cadastrada com Sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
			}else{
				message.setSummary("Não é possível cadastrar uma família sem Pessoas. Favor Adicionar Pessoas");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			}
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	public void setAcsMatricula(Integer matricula){
		if(matricula == 0 || matricula == null){
			familia.getRua().setAgente(null);
		}else{
			for(Acs agente: agentes){
				if(agente.getMatricula().equals(matricula)){
					familia.getRua().setAgente(agente);
					Endereco aux = new Endereco();
					aux.setAgente(agente);
					endereco = controllerEndereco.searchListEndereco(aux);
					break;
				}
			}
		}
	}
	
	public Integer getAcsMatricula(){
		if(familia.getRua().getAgente() == null){
			return null;			
		}else{
			return familia.getRua().getAgente().getMatricula();
		}
	}
	
	public void setEnderecoFamilia(Integer cep){
		if(cep == null || cep == 0){
			familia.getRua().setRua(null);
		}else{
			for(Endereco rua: endereco){
				if(rua.getCep().equals(cep)){
					familia.setRua(rua);
					familia.setAgente(rua.getAgente());
					break;
				}
			}
		}
	}
	
	public Integer getEnderecoFamilia(){
		if(familia.getRua() == null){
			return null;			
		}else{
			return familia.getRua().getCep();
		}
	}
	
	
	//CARREGA A LISTA DE RAÇAS NO SELECTMENU
	public void setRacaPessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			aux.getRaca().setCodigo(codigo);
		}else{
			for(Raca raca: racas){
				if(raca.getCodigo().equals(codigo)){
					aux.setRaca(raca);
					racas = controllerRaca.searchRaca(new Raca());
					break;
				}
			}
		}
	}
	public Integer getRacaPessoa(){
		if(aux.getRaca().getCodigo() == null){
			return null;
		}else{
			return aux.getRaca().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE ESCOLARIDADE NO SELECTMENU
	public void setEscolaridadePessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			aux.getEscolaridade().setCodigo(codigo);
		}else{
			for(Escolaridade escolaridade: escolaridades){
				if(escolaridade.getCodigo().equals(codigo)){
					aux.setEscolaridade(escolaridade);
					escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
					break;
				}
			}
		}
	}
	public Integer getEscolaridadePessoa(){
		if(aux.getEscolaridade().getCodigo() == null){
			return null;
		}else{
			return aux.getEscolaridade().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE VINCULOS EMPREGATICIOS NO SELECTMENU
	public void setVinculoPessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			aux.getVinculo().setCodigo(codigo);
		}else{
			for(VinculoEmpregaticio vinculo: vinculos){
				if(vinculo.getCodigo().equals(codigo)){
					aux.setVinculo(vinculo);
					vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
					break;
				}
			}
		}
	}
	public Integer getVinculoPessoa(){
		if(aux.getVinculo().getCodigo() == null){
			return null;
		}else{
			return aux.getVinculo().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE DOENÇAS DO SELECT MANY CHECKBOX
	public void setDoencasPessoa(List<String> siglas){
		aux.getSituacao().clear();
		for(String sigla: siglas){
			for(Doenca doencaLista: doencas){
				if(doencaLista.getSigla().toString().equals(sigla)){
					aux.getSituacao().add(doencaLista);
				}
			}
		}
	}
	
	public List<String> getDoencasPessoa() {		
		List<String> siglas = new ArrayList<>();
		
		for(Doenca doenca: aux.getSituacao()){
			siglas.add(doenca.getSigla().toString());
		}
		
		return siglas;
	}
	
	public void addList() throws BusinessException{
		boolean achou = false;
		FacesMessage message = new FacesMessage();
	
		Pessoa pAux = controllerPessoa.searchPessoaSus(aux.getSus());
		if(pAux == null){
			for(Pessoa pessoa: familia.getPessoas()){
				if(pessoa.getSus().equals(aux.getSus())){
					achou = true;
					message.setSummary("O número do SUS informado, já foi Cadastrado!");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
				}
			}
			if(!achou){
				familia.getPessoas().add(aux);
				resetAux();
				message.setSummary("Pessoa adicionada com Sucesso!");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
			}
		}else{
			message.setSummary("O número do SUS informado, já foi Cadastrado!");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
	}
	
	public void removeList(Pessoa pessoa){
		if(familia.getPessoas().contains(pessoa)){
			familia.getPessoas().remove(pessoa);
		}
	}
}
