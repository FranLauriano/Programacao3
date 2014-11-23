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

import prefeitura.siab.controller.BusinessException;
import prefeitura.siab.controller.DoencaController;
import prefeitura.siab.controller.EscolaridadeController;
import prefeitura.siab.controller.FamiliaController;
import prefeitura.siab.controller.PessoaController;
import prefeitura.siab.controller.RacaController;
import prefeitura.siab.controller.VinculoController;
import prefeitura.siab.tabela.Acs;
import prefeitura.siab.tabela.Doenca;
import prefeitura.siab.tabela.Escolaridade;
import prefeitura.siab.tabela.Familia;
import prefeitura.siab.tabela.Pessoa;
import prefeitura.siab.tabela.Raca;
import prefeitura.siab.tabela.VinculoEmpregaticio;

@Component
@Scope(WebApplicationContext.SCOPE_SESSION)
public class SearchPessoa {

	//ATRIBUTOS
	private @Autowired PessoaController controller;
	private PessoaSearchOptions options;
	private List<Pessoa> result;
	private Pessoa pessoa;
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
	
	//PROPRIEDADES
	
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
	
	//CONSTRUTOR
	public SearchPessoa() {
		result = null;
		reset();
		
		pessoa = new Pessoa();
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerFamilia = applicationContext.getBean(FamiliaController.class);
		controllerRaca = applicationContext.getBean(RacaController.class);
		controllerEscolaridade = applicationContext.getBean(EscolaridadeController.class);
		controllerVinculo = applicationContext.getBean(VinculoController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
		
		familias = controllerFamilia.searchFamilia(new Familia());
		racas = controllerRaca.searchRaca(new Raca());
		escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
		vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
		doencas = controllerDoenca.searchListDoenca(new Doenca());
	}
	
	//MÉTODOS
	
	public void reset(){
		options = new PessoaSearchOptions();
		options.setFamilia(new Familia());
		options.setEscolaridade(new Escolaridade());
		options.setRaca(new Raca());
		options.setVinculo(new VinculoEmpregaticio());
		List<Doenca> situacao = new ArrayList<>();
		options.setDoencas(situacao);
	}
	public String search(){
		result = controller.searchListPessoa(options);
		return null;
	}
	
	//CARREGA A LISTA DE FAMÍLIAS NO SELECTMENU
		public void setFamiliaPessoa(Integer codigo){
			if(codigo == null || codigo == 0){
				options.getFamilia().setCodigo(codigo);
			}else{
				for(Familia familia: familias){
					if(familia.getCodigo().equals(codigo)){
						options.setFamilia(familia);
						familias = controllerFamilia.searchFamilia(new Familia());
						break;
					}
				}
			}
		}
		
		public Integer getFamiliaPessoa(){
			if(options.getFamilia().getCodigo() == null){
				return null;
			}else{
				return options.getFamilia().getCodigo();
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
		this.pessoa = pessoaAux;
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
		controller.deletePessoa(pessoa);
		options = new PessoaSearchOptions();	
		for(int i = 0; i < result.size(); i++){
			if(result.get(i).equals(pessoa)){
				result.remove(i);
			}
		}
		FacesMessage message = new FacesMessage();
		message.setSummary("Pessoa foi Deletado!");
		message.setSeverity(FacesMessage.SEVERITY_INFO);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}

}