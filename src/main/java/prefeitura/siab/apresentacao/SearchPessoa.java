package prefeitura.siab.apresentacao;

import java.util.ArrayList;
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
import prefeitura.siab.controller.DoencaController;
import prefeitura.siab.controller.EnfermeiraController;
import prefeitura.siab.controller.EscolaridadeController;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.controller.RacaController;
import prefeitura.siab.controller.VinculoController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Enfermeira;
import prefeitura.siab.tabela.Escolaridade;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;
import prefeitura.siab.tabela.Raca;
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;
import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchPessoa {

	//ATRIBUTOS
	private @Autowired PessoaController controller;
	private PessoaSearchOptions options;
	private List<Pessoa> result;
	private Pessoa pessoa;
	private PessoaForm form;
	private boolean disabled;
	private boolean disabledSuper;
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
	//ACS
	private AcsController controllerAcs;
	private List<Acs> agentes;
	//ENFERMEIRAS
	private EnfermeiraController controllerEnfermeira;
	private List<Enfermeira> supervisores;
	
	
	//PROPRIEDADES
	
	public PessoaForm getForm() {
		return form;
	}
	public boolean isDisabledSuper() {
		return disabledSuper;
	}
	public void setDisabledSuper(boolean disabledSuper) {
		this.disabledSuper = disabledSuper;
	}
	public void setForm(PessoaForm form) {
		this.form = form;
	}
	public List<Pessoa> getResult() {
		return result;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
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
	public PessoaSearchOptions getOptions() {
		return options;
	}
	public void setOptions(PessoaSearchOptions options) {
		this.options = options;
	}
	public void setResult(List<Pessoa> result) {
		this.result = result;
	}
	public List<Acs> getAgentes() {
		return agentes;
	}
	public void setAgentes(List<Acs> agentes) {
		this.agentes = agentes;
	}
	public boolean isDisabled() {
		return disabled;
	}
	public void setDisabled(boolean disabled) {
		this.disabled = disabled;
	}
	public List<Enfermeira> getSupervisores() {
		return supervisores;
	}
	public void setSupervisores(List<Enfermeira> supervisores) {
		this.supervisores = supervisores;
	}
	//CONSTRUTOR
	public SearchPessoa() {
		result = null;
		reset();
	}
	

	//MÉTODOS
	public void inicializar(){
		
		form = new PessoaForm();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerFamilia = applicationContext.getBean(FamiliaController.class);
		controllerRaca = applicationContext.getBean(RacaController.class);
		controllerEscolaridade = applicationContext.getBean(EscolaridadeController.class);
		controllerVinculo = applicationContext.getBean(VinculoController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
		controllerAcs = applicationContext.getBean(AcsController.class);
		controllerEnfermeira = applicationContext.getBean(EnfermeiraController.class);
		
		init_familia();
		racas = controllerRaca.searchRaca(new Raca());
		escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
		vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
		doencas = controllerDoenca.searchListDoenca(new Doenca());
		supervisores = controllerEnfermeira.searchListEnfermeira(new Enfermeira());
	}
	
	
	public void reset(){
		
		options = new PessoaSearchOptions();
		options.setFamilia(new Familia());
		options.setEscolaridade(new Escolaridade());
		options.setRaca(new Raca());
		options.setVinculo(new VinculoEmpregaticio());
		List<Doenca> situacao = new ArrayList<>();
		options.setDoencas(situacao);
		
		pessoa = new Pessoa();
		pessoa.setFamilia(new Familia());
		pessoa.setEscolaridade(new Escolaridade());
		pessoa.setRaca(new Raca());
		pessoa.setVinculo(new VinculoEmpregaticio());
		List<Doenca> situacao1 = new ArrayList<>();
		pessoa.setSituacao(situacao1);
	}
	public String search(){
		result = controller.searchListPessoa(options);
		return null;
	}
	
	public void init_familia(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login autenticacaoBean = (Login) mapa.get("login");
		Usuario usuario = autenticacaoBean.getUsuario();
		if(usuario != null){
			Familia auxiliar = new Familia();
			if(usuario.getTipo().equals(TipoUsuario.ACS)){
				auxiliar.setAgente(usuario.getAcs());
				familias = controllerFamilia.searchFamilia(auxiliar);
			}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
				Acs acsAux = new Acs();
				acsAux.setSupervisor(usuario.getEnfermeira());
				auxiliar.setAgente(acsAux);
				familias = controllerFamilia.searchFamilia(auxiliar);
			}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
				if(usuario.getAcs() == null && usuario.getEnfermeira() == null){
					familias = controllerFamilia.searchFamilia(new Familia());
				}else if(usuario.getAcs() == null){
					Acs acsAux = new Acs();
					acsAux.setSupervisor(usuario.getEnfermeira());
					auxiliar.setAgente(acsAux);
					familias = controllerFamilia.searchFamilia(auxiliar);
				}else if(usuario.getEnfermeira() == null){
					auxiliar.setAgente(usuario.getAcs());
					familias = controllerFamilia.searchFamilia(auxiliar);
				}
			}
		}
	}
	
	//CARREGA A LISTA DE FAMÍLIAS NO SELECTMENU
		public void setFamiliaPessoa(Integer codigo){
			if(codigo == null || codigo == 0){
				options.getFamilia().setNumeroFamilia(null);
			}else{
				for(Familia familia: familias){
					if(familia.getNumeroFamilia().equals(codigo)){
						options.setFamilia(familia);
						//familias = controllerFamilia.searchFamilia(new Familia());
						break;
					}
				}
			}
		}
		
		public Integer getFamiliaPessoa(){
			if(options.getFamilia().getNumeroFamilia() == null){
				return null;
			}else{
				return options.getFamilia().getNumeroFamilia();
			}
		}
		
		//CARREGA A LISTA DE RAÇAS NO SELECTMENU
		public void setRacaPessoa(Integer codigo){
			if(codigo == null || codigo == 0){
				options.getRaca().setCodigo(codigo);
			}else{
				for(Raca raca: racas){
					if(raca.getCodigo().equals(codigo)){
						options.setRaca(raca);
						racas = controllerRaca.searchRaca(new Raca());
						break;
					}
				}
			}
		}
		public Integer getRacaPessoa(){
			if(options.getRaca().getCodigo() == null){
				return null;
			}else{
				return options.getRaca().getCodigo();
			}
		}
		
		//CARREGA A LISTA DE ESCOLARIDADE NO SELECTMENU
		public void setEscolaridadePessoa(Integer codigo){
			if(codigo == null || codigo == 0){
				options.getEscolaridade().setCodigo(codigo);
			}else{
				for(Escolaridade escolaridade: escolaridades){
					if(escolaridade.getCodigo().equals(codigo)){
						options.setEscolaridade(escolaridade);
						escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
						break;
					}
				}
			}
		}
		public Integer getEscolaridadePessoa(){
			if(options.getEscolaridade().getCodigo() == null){
				return null;
			}else{
				return options.getEscolaridade().getCodigo();
			}
		}
		
		//CARREGA A LISTA DE VINCULOS EMPREGATICIOS NO SELECTMENU
		public void setVinculoPessoa(Integer codigo){
			if(codigo == null || codigo == 0){
				options.getVinculo().setCodigo(codigo);
			}else{
				for(VinculoEmpregaticio vinculo: vinculos){
					if(vinculo.getCodigo().equals(codigo)){
						options.setVinculo(vinculo);
						vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
						break;
					}
				}
			}
		}
		public Integer getVinculoPessoa(){
			if(options.getVinculo().getCodigo() == null){
				return null;
			}else{
				return options.getVinculo().getCodigo();
			}
		}
		
		//CARREGA A LISTA DE DOENÇAS DO SELECT MANY CHECKBOX
		public void setDoencasPessoa(List<String> siglas){
			options.getDoencas().clear();
			for(String sigla: siglas){
				for(Doenca doencaLista: doencas){
					if(doencaLista.getSigla().toString().equals(sigla)){
						options.getDoencas().add(doencaLista);
					}
				}
			}
		}
		
		public List<String> getDoencasPessoa() {		
			List<String> siglas = new ArrayList<>();
			
			for(Doenca doenca: options.getDoencas()){
				siglas.add(doenca.getSigla().toString());
			}
			
			return siglas;
		}
		
		public void setAcsMatricula(Integer matricula){
			if(matricula == 0 || matricula == null){
				options.setAgente(null);
			}else{
				for(Acs agente: agentes){
					if(agente.getMatricula().equals(matricula)){
						options.setAgente(agente);
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
			Usuario usuario = autenticacaoBean.getUsuario();
			if(usuario != null){
				if(usuario.getTipo().equals(TipoUsuario.ACS)){
					options.setAgente(usuario.getAcs());
					this.disabled = true;
					Familia aux = new Familia();
					aux.setAgente(usuario.getAcs());
					familias = controllerFamilia.searchFamilia(aux);
					return usuario.getAcs().getMatricula();
				}else{
					if(options.getAgente() != null){
						return options.getAgente().getMatricula();						
					}else{
						return null;
					}
				}
			}
			return null;
		}
		
		public void setSupervisorMatricula(Integer matricula){
			if(matricula == 0 || matricula == null){
				options.setEnfermeira(null);
			}else{
				for(Enfermeira enfermeira: supervisores){
					if(enfermeira.getMatricula().equals(matricula)){
						options.setEnfermeira(enfermeira);
						AcsSearchOptions aux = new AcsSearchOptions();
						aux.setSupervisora(enfermeira);
						agentes = controllerAcs.searchListAcs(aux);
						break;
					}
				}
				
			}
		}
		
		public Integer getSupervisorMatricula(){
			FacesContext facesContext = FacesContext.getCurrentInstance();
			ExternalContext externalContext = facesContext.getExternalContext();
			Map<String, Object> mapa = externalContext.getSessionMap();
			Login autenticacaoBean = (Login) mapa.get("login");
			Usuario usuario = autenticacaoBean.getUsuario();
			if(usuario != null){
				if(usuario.getTipo().equals(TipoUsuario.ACS)){
					options.setEnfermeira(usuario.getAcs().getSupervisor());
					this.disabledSuper = true;
					agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
					return usuario.getAcs().getSupervisor().getMatricula();
				}else if(usuario.getTipo().equals(TipoUsuario.ENFERMEIRA)){
					options.setEnfermeira(usuario.getEnfermeira());
					this.disabledSuper = true;
					agentes = usuario.getEnfermeira().getAgentes();
					return usuario.getEnfermeira().getMatricula();
				}else if(usuario.getTipo().equals(TipoUsuario.ADMINISTRADOR)){
					agentes = controllerAcs.searchListAcs(new AcsSearchOptions());
					if(usuario.getAcs() == null && usuario.getEnfermeira() == null){
						this.disabled = false;
						this.disabledSuper = false;
						return null;
					}else if(usuario.getAcs() == null){
						options.setEnfermeira(usuario.getEnfermeira());
						this.disabledSuper = true;
						return usuario.getEnfermeira().getMatricula();
					}else if(usuario.getEnfermeira() == null){
						this.disabled = true;
						this.disabledSuper = true;
						options.setAgente(usuario.getAcs());
						options.setEnfermeira(usuario.getEnfermeira());
						return usuario.getAcs().getSupervisor().getMatricula();
					}
				}
			}
			return null;
		}
		
		public void setSexoPessoa(char sexo){
			if(sexo == 'f'){
				options.setSexo("Feminino");
			}else if(sexo == 'm'){
				options.setSexo("Masculino");
			}else{
				options.setSexo(null);
			}
		}
		
		public char getSexoPessoa(){
			if(options.getSexo() != null){
				if(options.getSexo().equals("Feminino")){
					return 'f';
				}else{
					return 'm';
				}
			}else{
				return 'a';
			}
		}
		

	public String update(Pessoa pessoa){
		Pessoa pessoaAux = new Pessoa();
		pessoaAux.setBolsaescola(pessoa.getBolsaescola());
		pessoaAux.setDtnascimento(pessoa.getDtnascimento());
		pessoaAux.setEscolaridade(pessoa.getEscolaridade());
		pessoaAux.setFamilia(pessoa.getFamilia());
		pessoaAux.setFrequentaescola(pessoa.getFrequentaescola());
		pessoaAux.setIdade(pessoa.getIdade());
		pessoaAux.setMae(pessoa.getMae());
		pessoaAux.setNome(pessoa.getNome());
		pessoaAux.setParentesco(pessoa.getParentesco());
		pessoaAux.setRaca(pessoa.getRaca());
		pessoaAux.setSexo(pessoa.getSexo());
		pessoaAux.setSituacao(pessoa.getSituacao());
		pessoaAux.setSus(pessoa.getSus());
		pessoaAux.setVinculo(pessoa.getVinculo());
		pessoaAux.setCodigo(pessoa.getCodigo());
		this.pessoa = pessoaAux;
		form.setPessoa(pessoaAux);
		return "updatePessoa";
	}
	
	
	
	public String confirmUpdate(){
		FacesMessage message = new FacesMessage();
		try{
			controller.updatePessoa(pessoa);
			reset();
			result = null;
			message.setSummary("Pessoa atualizado com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	
	public String confirmDeletion(Pessoa pessoa) throws BusinessException{
		if(pessoa.getFamilia().getPessoas().size() > 1){
			controller.deletePessoa(pessoa);
			boolean achou = false;
			for(Pessoa pes: result){
				if(pes.getCodigo().equals(pessoa.getCodigo())){
					achou = true;
					break;
				}
			}
			if(achou){
				result.remove(pessoa);
			}
			FacesMessage message = new FacesMessage();
			message.setSummary("Pessoa foi Deletado!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			return null;
		}else{
			FacesMessage message = new FacesMessage();
			message.setSummary("Impossível deletar essa pessoa. A Familia " + pessoa.getFamilia().getNumeroFamilia() + " só tem essa Pessoa cadastrada. Contudo você terá que deletar a Família.");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
			FacesContext context = FacesContext.getCurrentInstance();
			context.addMessage(null, message);
			return null;
		}
		
	}

}