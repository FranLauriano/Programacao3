package prefeitura.siab.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.DoencaDao;
import prefeitura.siab.tabela.Doenca;

@Component
public class DoencaController {

	private @Autowired DoencaDao dao;

	@Transactional
	public void salvarDoenca(Doenca doenca) throws BusinessException{
		Doenca auxiliar = dao.searchDoenca(doenca);
		if(auxiliar != null){
			if(auxiliar.getSigla().equals(doenca.getSigla()) && auxiliar.getNome().equals(doenca.getNome())){
				throw new BusinessException("Essa Doença/Condição já está cadastrada!");
			}else if(auxiliar.getSigla().equals(doenca.getSigla())){
				throw new BusinessException("Essa Sigla já está cadastrada!");
			}else if(auxiliar.getNome().equals(doenca.getNome())){
				throw new BusinessException("O nome: "+ doenca.getNome() + " já está cadastrado!");
			}
		}
		dao.insert(doenca);
	}
	
	public List<Doenca> searchListDoenca(Doenca doenca) {
		return dao.searchListDoenca(doenca);
	}

	@Transactional
	public void updateDoenca(Doenca doenca) throws BusinessException {
		Doenca doencaAux = dao.searchDoencaName(doenca.getNome());
		if(doencaAux == null){
			dao.updateDoenca(doenca);
		}else{
			if(doenca.getSigla() == (doencaAux.getSigla())){
				dao.updateDoenca(doenca);
			}else{
				throw new BusinessException("Já existe uma doença com o nome: "+ doenca.getNome());
			}
		}
	}
	
	@Transactional
	public void deleteDoenca(Doenca doenca) throws BusinessException{
		Doenca doencaAux = dao.searchDoencaName(doenca.getNome());
		if(doencaAux != null){
			dao.delete(doencaAux);
		}else{
			throw new BusinessException("Impossível deletar, pois a doença "+ doenca.getNome() + " não existe!");
		}
	}
}
