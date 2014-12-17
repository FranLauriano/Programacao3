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
			if(auxiliar.getMicroarea().equals(agente.getMicroarea())){
				throw new BusinessException("Essa Microarea já pertence a algum ACS cadastrado!");
			}
		}
		if(agente.getMatricula() == null || agente.getMatricula() == 0){
			throw new BusinessException("Impossível salvar um ACS sem Matrícula");
		}
		if(agente.getMicroarea() == null || agente.getMicroarea() == 0){
			throw new BusinessException("Micro-Área Inválida!");
		}
		if(agente.getArea() == null || agente.getArea() == 0){
			throw new BusinessException("Área Inválida!");
		}
		if(agente.getMicroregiao() == null || agente.getMicroregiao() == 0){
			throw new BusinessException("Micro-Região Inválida!");
		}
		dao.insert(agente);
	}
	
	public List<Acs> searchListAcs(AcsSearchOptions options){
		return dao.searchListAcs(options);
	}
	
	public Acs searchAcs(AcsSearchOptions options){
		return dao.searchAcs(options);
	}
	
	public Acs searchServidor(Acs acs){
		return dao.searchAcs(acs);
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
