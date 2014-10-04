package prefeitura.siab.controller;

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
}
