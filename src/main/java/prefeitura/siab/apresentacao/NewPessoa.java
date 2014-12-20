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
import prefeitura.siab.tabela.TipoUsuario;
import prefeitura.siab.tabela.Usuario;
import prefeitura.siab.tabela.VinculoEmpregaticio;


@Component
@Scope(WebApplicationContext.SCOPE_REQUEST)
public class NewPessoa {

	//ATRIBUTOS
	private @Autowired PessoaController pessoaController;
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
	
	private Pessoa pessoa;

	
	//PROPRIEDADES
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
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public List<Doenca> getDoencas() {
		return doencas;
	}
	public void setDoencas(List<Doenca> doencas) {
		this.doencas = doencas;
	}
	
	
	//CONSTRUTOR
	public NewPessoa() {
		pessoa = new Pessoa();
		pessoa.setFamilia(new Familia());
		pessoa.setEscolaridade(new Escolaridade());
		pessoa.setRaca(new Raca());
		pessoa.setVinculo(new VinculoEmpregaticio());
		List<Doenca> situacao = new ArrayList<>();
		pessoa.setSituacao(situacao);
		
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		controllerFamilia = applicationContext.getBean(FamiliaController.class);
		controllerRaca = applicationContext.getBean(RacaController.class);
		controllerEscolaridade = applicationContext.getBean(EscolaridadeController.class);
		controllerVinculo = applicationContext.getBean(VinculoController.class);
		controllerDoenca = applicationContext.getBean(DoencaController.class);
		
		init_familia();
		racas = controllerRaca.searchRaca(new Raca());
		escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
		vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
		doencas = controllerDoenca.searchListDoenca(new Doenca());
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

	//MÉTODOS
	public String savePessoa(){
		FacesMessage message = new FacesMessage();
		try{
			pessoaController.salvarPessoa(pessoa);
			message.setSummary("Pessoa Cadastrada com Sucesso!");
			message.setSeverity(FacesMessage.SEVERITY_INFO);
		}catch(BusinessException e){
			message.setSummary(e.getMessage());
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}catch(Exception e){
			message.setSummary("Os Campos Família, Raça, Escolaridade e Vínculo Empregatício são Obrigatórios");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
		}
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, message);
		return null;
	}
	
	
	//CARREGA A LISTA DE FAMÍLIAS NO SELECTMENU
	public void setFamiliaPessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			pessoa.getFamilia().setNumeroFamilia(codigo);
		}else{
			for(Familia familia: familias){
				if(familia.getNumeroFamilia().equals(codigo)){
					pessoa.setFamilia(familia);
					familias = controllerFamilia.searchFamilia(new Familia());
					break;
				}
			}
		}
	}
	
	public Integer getFamiliaPessoa(){
		if(pessoa.getFamilia().getNumeroFamilia() == null){
			return null;
		}else{
			return pessoa.getFamilia().getNumeroFamilia();
		}
	}
	
	//CARREGA A LISTA DE RAÇAS NO SELECTMENU
	public void setRacaPessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			pessoa.getRaca().setCodigo(codigo);
		}else{
			for(Raca raca: racas){
				if(raca.getCodigo().equals(codigo)){
					pessoa.setRaca(raca);
					racas = controllerRaca.searchRaca(new Raca());
					break;
				}
			}
		}
	}
	public Integer getRacaPessoa(){
		if(pessoa.getRaca().getCodigo() == null){
			return null;
		}else{
			return pessoa.getRaca().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE ESCOLARIDADE NO SELECTMENU
	public void setEscolaridadePessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			pessoa.getEscolaridade().setCodigo(codigo);
		}else{
			for(Escolaridade escolaridade: escolaridades){
				if(escolaridade.getCodigo().equals(codigo)){
					pessoa.setEscolaridade(escolaridade);
					escolaridades = controllerEscolaridade.searchEscolaridade(new Escolaridade());
					break;
				}
			}
		}
	}
	public Integer getEscolaridadePessoa(){
		if(pessoa.getEscolaridade().getCodigo() == null){
			return null;
		}else{
			return pessoa.getEscolaridade().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE VINCULOS EMPREGATICIOS NO SELECTMENU
	public void setVinculoPessoa(Integer codigo){
		if(codigo == null || codigo == 0){
			pessoa.getVinculo().setCodigo(codigo);
		}else{
			for(VinculoEmpregaticio vinculo: vinculos){
				if(vinculo.getCodigo().equals(codigo)){
					pessoa.setVinculo(vinculo);
					vinculos = controllerVinculo.searchVinculo(new VinculoEmpregaticio());
					break;
				}
			}
		}
	}
	public Integer getVinculoPessoa(){
		if(pessoa.getVinculo().getCodigo() == null){
			return null;
		}else{
			return pessoa.getVinculo().getCodigo();
		}
	}
	
	//CARREGA A LISTA DE DOENÇAS DO SELECT MANY CHECKBOX
	public void setDoencasPessoa(List<String> siglas){
		pessoa.getSituacao().clear();
		for(String sigla: siglas){
			for(Doenca doencaLista: doencas){
				if(doencaLista.getSigla().toString().equals(sigla)){
					pessoa.getSituacao().add(doencaLista);
				}
			}
		}
	}
	
	public List<String> getDoencasPessoa() {		
		List<String> siglas = new ArrayList<>();
		
		for(Doenca doenca: pessoa.getSituacao()){
			siglas.add(doenca.getSigla().toString());
		}
		
		return siglas;
	}
	
	public void setSexoPessoa(char sexo){
		if(sexo == 'f'){
			pessoa.setSexo("Feminino");
		}else{
			pessoa.setSexo("Masculino");
		}
	}
	
	public char getSexoPessoa(){
		if(pessoa.getSexo() != null){
			if(pessoa.getSexo().equals("Feminino")){
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
			pessoa.setFrequentaescola("Sim");
		}else{
			pessoa.setFrequentaescola("Não");
		}
	}
	
	public Integer getFrequentaEscola(){
		if(pessoa.getFrequentaescola() != null){
			if(pessoa.getFrequentaescola().equals("Sim")){
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
			pessoa.setBolsaescola("Sim");
		}else{
			pessoa.setBolsaescola("Não");
		}
	}
	
	public Integer getBolsaEscola(){
		if(pessoa.getBolsaescola() != null){
			if(pessoa.getBolsaescola().equals("Sim")){
				return 1;
			}else{
				return 0;
			}
		}else{
			return 0;
		}
	}


}
