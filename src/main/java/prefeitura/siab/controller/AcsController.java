package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.apresentacao.AcsSearchOptions;
import prefeitura.siab.persistencia.AcsDao;
import prefeitura.siab.tabela.Acs;

@Component
public class AcsController {

	private @Autowired AcsDao dao;
	
	@Transactional
	public void salvarAcs(Acs agente) throws BusinessException {
		Acs auxiliar = dao.searchAcs(agente);
		if(auxiliar != null){
			if(auxiliar.getMatricula().equals(agente.getMatricula()) && auxiliar.getMicroarea().equals(agente.getMicroarea())){
				throw new BusinessException("Esse ACS já foi Cadastrado!");
			}else if(auxiliar.getMatricula().equals(agente.getMatricula())){
				throw new BusinessException("Essa matricula já pertence a algum ACS cadastrado!");
			}else if(auxiliar.getMicroarea().equals(agente.getMicroarea())){
				throw new BusinessException("Essa Microarea já pertence a algum ACS cadastrado!");
			}
			throw new BusinessException("Impossível salvar esse ACS");
		}
		dao.insert(agente);
	}
	
	public List<Acs> searchListAcs(AcsSearchOptions options){
		return dao.searchListAcs(options);
	}

	@Transactional
	public void updateAcs(Acs acs) throws BusinessException {
		Acs acsAux = dao.searchAcs(acs); //TEM QUE MODIFICAR PARA ELE BUSCAR PELA MATRICULA
		if(acsAux != null){
			dao.updateAcs(acs);			
		}else{
			throw new BusinessException("Impossível atualizar, "+ acs.getMatricula() + " não existe!");
		}
	}
	
	@Transactional
	public void deleteAcs(Acs acs) throws BusinessException{
		Acs acsAux = dao.searchAcs(acs);
		if(acsAux != null){
			dao.delete(acsAux);
		}else{
			throw new BusinessException("Impossível deletar, pois a matricula "+ acs.getMatricula() + " não existe!");
		}
	}
	
}
