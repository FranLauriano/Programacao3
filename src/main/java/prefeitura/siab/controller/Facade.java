package prefeitura.siab.controller;

import javax.faces.context.FacesContext;

import org.springframework.context.ApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

public class Facade {

	//CONTROLADORES
	public AcsController acsController;
	public DoencaController doencaController;
	public EnderecoController enderecoController;
	public EscolaridadeController escolaridadeController;
	public FamiliaController familiaController;
	public PessoaController pessoaController;
	public RacaController racaController;
	public VinculoController vinculoController;
	
	//INSTANCIA
	private Facade instance;

	//CONSTRUTOR
	private Facade(){
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ApplicationContext applicationContext = FacesContextUtils.getWebApplicationContext(facesContext);
		
		acsController = applicationContext.getBean(AcsController.class);
		doencaController = applicationContext.getBean(DoencaController.class);
		enderecoController = applicationContext.getBean(EnderecoController.class);
		escolaridadeController = applicationContext.getBean(EscolaridadeController.class);
		familiaController = applicationContext.getBean(FamiliaController.class);
		pessoaController = applicationContext.getBean(PessoaController.class);
		racaController = applicationContext.getBean(RacaController.class);
		vinculoController = applicationContext.getBean(VinculoController.class);
	}
	
	//RECUPERAR INSTANCIA DO FACADE
	public Facade getInstance(){
		if(instance == null){
			instance = new Facade();
		}
		return instance;
	}
}
