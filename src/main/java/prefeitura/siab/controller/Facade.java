package prefeitura.siab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class Facade {

	//CONTROLADORES
	private @Autowired AcsController acsController;
	private @Autowired DoencaController doencaController;
	private @Autowired EnderecoController enderecoController;
	private @Autowired EscolaridadeController escolaridadeController;
	private @Autowired FamiliaController familiaController;
//	private @Autowired PessoaController pessoaController;
	private @Autowired RacaController racaController;
	private @Autowired VinculoController vinculoController;
	
	//INSTANCIA
	private Facade instance;

	//CONSTRUTOR
	private Facade(){
		
	}
	
	//RECUPERAR INSTANCIA DO FACADE
	public Facade getInstance(){
		if(instance == null){
			instance = new Facade();
		}
		return instance;
	}
}
