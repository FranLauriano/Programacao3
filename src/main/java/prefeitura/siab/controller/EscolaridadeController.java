package prefeitura.siab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import prefeitura.siab.persistencia.EscolaridadeDao;
import prefeitura.siab.tabela.Escolaridade;

@Component
public class EscolaridadeController {

	private @Autowired
	EscolaridadeDao dao;

	@Transactional
	public void salvarEscolaridade(Escolaridade escolaridade) throws BusinessException{
		Escolaridade auxiliar = dao.searchEscolaridade(escolaridade);
		if (auxiliar != null) {
			if (auxiliar.getCodigo().equals(escolaridade.getCodigo()) && auxiliar.getNome().equals(escolaridade.getNome())) {
				throw new BusinessException("Essa Escolaridade já está cadastrada!!!");
			} else if (auxiliar.getCodigo().equals(escolaridade.getCodigo())) {
				throw new BusinessException("Esse Código já foi Cadastrado, favor modificar.");
			} else if (auxiliar.getNome().equals(escolaridade.getNome())) {
				throw new BusinessException("Esse Nome já foi Cadastrado, favor modificar. ");
			}
			throw new BusinessException("Impossível cadastrar essa Escolaridade!");
		}
		dao.insert(escolaridade);

	}

}
