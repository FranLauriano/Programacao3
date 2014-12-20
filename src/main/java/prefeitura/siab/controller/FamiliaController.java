package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.FamiliaDao;
import prefeitura.siab.tabela.Familia;

@Component
public class FamiliaController {

	private @Autowired FamiliaDao dao;

	@Transactional
	public void salvarFamilia(Familia familia) throws BusinessException {
		Familia auxiliar = dao.searchFamilia(familia);
		if(auxiliar != null){
			if(auxiliar.getCodigo().equals(familia.getCodigo())){
				throw new BusinessException("Já existe Família cadastrada com esse Código");
			}
		}
		if(familia.getAgente() == null || familia.getAgente().getMatricula() == 0){
			throw new BusinessException("Favor selecionar um Agente Comunitário de Saúde Válido!");
		}
		if(familia.getRua() == null || familia.getRua().getRua() == null || familia.getRua().getRua().length() < 1){
			throw new BusinessException("Favor selecionar um Endereço Válido!");
		}
		Familia aux = dao.searchCodAcs(familia);
		if(aux != null){
			throw new BusinessException("Código da Família: Já existe uma Família com esse Código");
		}
		dao.insert(familia);
		
	}
	
	public List<Familia> searchFamilia(Familia familia) {
		return dao.searchListFamilia(familia);
	}

	@Transactional
	public void updateFamilia(Familia familia) throws BusinessException {
		Familia familiaAux = dao.searchFamiliaCodigo(familia.getCodigo());
		if(familiaAux == null){
			dao.updateFamilia(familia);
		}else{
			if(familia.getCodigo() == (familiaAux.getCodigo())){
				dao.updateFamilia(familia);
			}else{
				throw new BusinessException("Já existe uma Família com o código: "+ familia.getCodigo());
			}
		}
	}

	@Transactional
	public void deleteFamilia(Familia familia) throws BusinessException{
		Familia familiaAux = dao.searchFamiliaCodigo(familia.getCodigo());
		if(familiaAux != null){
			dao.delete(familia);
		}else{
			throw new BusinessException("Impossível deletar, pois essa família '"+ familia.getCodigo() + "' não existe!");
		}
	}

}
