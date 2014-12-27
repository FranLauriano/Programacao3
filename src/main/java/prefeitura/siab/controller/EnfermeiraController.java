package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.EnfermeiraDao;
import prefeitura.siab.tabela.Enfermeira;


@Component
public class EnfermeiraController{


	private @Autowired EnfermeiraDao dao;
		
	@Transactional
	public void salvarEnfermeira(Enfermeira enfermeira) throws BusinessException {
		Enfermeira auxiliar = dao.searchEnfermeira(enfermeira);
		if(auxiliar != null){
			if(auxiliar.getCoren().equals(enfermeira.getCoren())){
				throw new BusinessException("O COREN já foi Cadastrado!");
			}
		}
		if(enfermeira.getMatricula() == null || enfermeira.getMatricula() == 0){
			throw new BusinessException("Impossível salvar um Enfermeiro(a) sem Matrícula");
		}
		if(enfermeira.getCoren() == null || enfermeira.getCoren() == 0){
			throw new BusinessException("COREN Inválido!");
		}
		if(enfermeira.getEquipe() == null || enfermeira.getEquipe().length() < 5){
			throw new BusinessException("Nome da Equipe Inválida! Favor adicionar um nome Válido (5 Caracteres)");
		}
		dao.insert(enfermeira);
	}

	@Transactional
	public void updateEnfermeira(Enfermeira enfermeira) throws BusinessException {
		dao.update(enfermeira);			
	}
	
	@Transactional
	public void deleteEnfermeira(Enfermeira enfermeira) throws BusinessException{
		Enfermeira auxiliar = dao.searchEnfermeira(enfermeira);
		if(auxiliar != null){
			dao.delete(auxiliar);
		}else{
			throw new BusinessException("Impossível deletar, pois a matricula "+ enfermeira.getMatricula() + " não existe!");
		}
	}
	
	public List<Enfermeira> searchListEnfermeira(Enfermeira enfermeira){
		return dao.searchListEnfermeira(enfermeira);
	}
	
	public Enfermeira searchEnfermeiraMatricula(Integer matricula){
		return dao.searchEnfermeiraMatricula(matricula);
	}
}
