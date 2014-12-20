package prefeitura.siab.apresentacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
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

public class FamiliaForm {

	//ATRIBUTOS
	private @Autowired FamiliaController controller;
	private Familia familia;
	private Pessoa aux;
	private List<Pessoa> pessoas;
	//FAMÍLIAS
	private FamiliaController controllerFamilia;
	private List<Familia> familias;
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
	//ACS's
	private AcsController controllerAcs;
	private List<Acs> agentes;
	//ENDEREÇOS
	private EnderecoController controllerEndereco;
	private List<Endereco> enderecos;
	//Pessoa
	private PessoaController controllerPessoa;
	private boolean disabled;
	
		
	//CONSTRUTOR
	public FamiliaForm() {
		familia = new Familia();
		List<Pessoa> pessoas = new ArrayList<>();
		familia.setPessoas(pessoas);
		familia.setAgente(new Acs());
		familia.setRua(new Endereco());
		
		resetAux();
		
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerFamilia = applicationContext.getBean(FamiliaController.class);
		controllerRaca = applicationContext.getBean(RacaController.class);
		controllerEscolaridade = applicationContext.getBean(EscolaridadeController.class);
		controllerVinculo = applicationContext.getBean(VinculoController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerEndereco = applicationContext.getBean(EnderecoController.class);
		controllerPessoa = applicationContext.getBean(PessoaController.class);
		
		agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
		enderecos = controllerEndereco.searchListEndereco(new Endereco());
		familias = controllerFamilia.searchFamilia(new Familia());
		racas = controllerRaca.searchRaca(new Raca());
		escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
		vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
		doencas = controllerDoenca.searchListDoenca(new Doenca());
		pessoas = familia.getPessoas();
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
	
	//PROPRIEDADES
	
	public Pessoa getAux() {
		return aux;
	}
	public boolean isDisabled() {
		return disabled;
	}

	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}

	public List<Pessoa> getPessoas() {
		return pessoas;
	}
	public void setPessoas(List<Pessoa> pessoas) {
		this.pessoas = pessoas;
	}

	public void setAux(Pessoa aux) {
		this.aux = aux;
	}
	public List<Familia> getFamilias() {
		return familias;
	}
	public void setFamilias(List<Familia> familias) {
		this.familias = familias;
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
	public Familia getFamilia() {
		return familia;
	}
	public void setFamilia(Familia familia) {
		this.familia = familia;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	public List<Endereco> getEnderecos() {
		return enderecos;
	}
	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
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
	
	
	//Carrega todos os ACS's
		public void setAcsMatricula(Integer matricula){
			if(matricula == 0 || matricula == null){
				familia.setAgente(null);
				enderecos = controllerEndereco.searchListEndereco(new Endereco());
			}else{
				for(Acs agente: agentes){
					if(agente.getMatricula().equals(matricula)){
						familia.setAgente(agente);
						Endereco aux = new Endereco();
						aux.setAgente(agente);
						enderecos = controllerEndereco.searchListEndereco(aux);
						break;
					}
				}
				
			}
		}
		
		public Integer getAcsMatricula(){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> mapa = externalContext.getSessionMap();
			Login autenticacaoBean = (Login) mapa.get("login");
			Acs servidor = autenticacaoBean.getAgente().getAgente();
			if(servidor == null){
				return null;
			}else{
				Endereco aux = new Endereco();
				aux.setAgente(servidor);
				enderecos = controllerEndereco.searchListEndereco(aux);
				this.disabled = true;
				familia.setAgente(servidor);
				return servidor.getMatricula();
			}
		}
		
		//Carrega todos os Endereços
		public void setEnderecoFamilia(Integer cep){
			if(cep == 0 || cep == null){
				familia.setRua(null);
			}else{
				for(Endereco end: enderecos){
					if(end.getCep().equals(cep)){
						familia.setRua(end);
						familia.setAgente(end.getAgente());
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
		
		public void setSexoPessoa(char sexo){
			if(sexo == 'f'){
				aux.setSexo("Feminino");
			}else{
				aux.setSexo("Masculino");
			}
		}
		
		public char getSexoPessoa(){
			if(aux.getSexo() != null){
				if(aux.getSexo().equals("Feminino")){
					return 'f';
				}else{
					return 'm';
				}
			}else{
				return 'm';
			}
		}
		
		public void setFrequentaEscola(Integer frequentaEscola){
			if(frequentaEscola.equals(1)){
				aux.setFrequentaescola("Sim");
			}else{
				aux.setFrequentaescola("Não");
			}
		}
		
		public Integer getFrequentaEscola(){
			if(aux.getFrequentaescola() != null){
				if(aux.getFrequentaescola().equals("Sim")){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
		
		public void setBolsaEscola(Integer bolsaEscola){
			if(bolsaEscola.equals(1)){
				aux.setBolsaescola("Sim");
			}else{
				aux.setBolsaescola("Não");
			}
		}
		
		public Integer getBolsaEscola(){
			if(aux.getBolsaescola() != null){
				if(aux.getBolsaescola().equals("Sim")){
					return 1;
				}else{
					return 0;
				}
			}else{
				return 0;
			}
		}
		
		public void removeList(Pessoa pessoa) throws BusinessException{
			FacesMessage message = new FacesMessage();

			if(pessoas.size() == 1){
				message.setSummary("Não é possível excluir a última Pessoa da Família. Para uma Família existir é necessário pelo menos 1 Pessoa.");
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			}else{
				boolean achou = false;
				for(Pessoa p: familia.getPessoas()){
					if(p.getSus().equals(pessoa.getSus())){
						achou = true;
					}
				}
				if(achou){
					familia.getPessoas().remove(pessoa);
				}
				message.setSummary("Pessoa Removida com sucesso da Família");
				message.setSeverity(FacesMessage.SEVERITY_INFO);
			}

			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
		}
		
		public void addPessoa() throws BusinessException{
			boolean achou = false;
			FacesMessage message = new FacesMessage();

			Pessoa pAux = controllerPessoa.searchPessoaCodigo(aux.getCodigo());
			try{
				if(pAux == null){
					for(Pessoa pessoa: familia.getPessoas()){
						if(pessoa.getSus().equals(aux.getSus())){
							achou = true;
							message.setSummary("O número do SUS informado, já foi Cadastrado!");
							message.setSeverity(FacesMessage.SEVERITY_ERROR);
						}
					}
					if(!achou){
						aux.setFamilia(familia);
						controllerPessoa.verificaPessoa(aux);
						familia.getPessoas().add(aux);
						resetAux();
						message.setSummary("Pessoa adicionada com Sucesso!");
						message.setSeverity(FacesMessage.SEVERITY_INFO);
					}
				}else{
					message.setSummary("O número do SUS informado, já foi Cadastrado!");
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
				}
			}catch(BusinessException e){
				message.setSummary(e.getMessage());
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			}catch(Exception e){
				message.setSummary(e.getMessage());
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			}
		}
	
}
