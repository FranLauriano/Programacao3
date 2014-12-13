package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.apresentacao.PessoaSearchOptions;
import prefeitura.siab.tabela.Pessoa;

@Component
public class PessoaDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Pessoa pessoa){
		manager.persist(pessoa);
	}

	public void updatePessoa(Pessoa pessoa) {
		manager.merge(pessoa);
	}

	public void delete(Pessoa pessoa) {
		Pessoa pessoaAux = manager.find(Pessoa.class, pessoa.getSus());
		manager.remove(pessoaAux);
	}

	public Pessoa searchPessoa(Pessoa pessoa) {
		StringBuilder predicate = new StringBuilder("1 = 1");

		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			predicate.append(" and pessoa.sus = :pessoaSus");
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			predicate.append(" and upper(pessoa.nome) like :pessoaNome");
		}
		if (pessoa.getDtnascimento() != null) {
			predicate.append(" and pessoa.dtnascimento = :pessoaDtnascimento");
		}
		if (pessoa.getIdade() != 0) {
			predicate.append(" and pessoa.idade = :pessoaIdade");
		}
		if (pessoa.getSexo() != 0) {
			predicate.append(" and upper(pessoa.sexo) = :pessoaSexo");
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			predicate.append(" and pessoa.raca.codigo = :pessoaRacaCodigo");
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			predicate.append(" and pessoa.escolaridade.codigo = :pessoaEscolaridadeCodigo");
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			predicate.append(" and pessoa.vinculo.codigo = :pessoaVinculoCodigo");
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			predicate.append(" and upper(pessoa.mae) like :pessoaMae");
		}
		if (pessoa.getParentesco() != null && pessoa.getParentesco().length() > 1) {
			predicate.append(" and upper(pessoa.parentesco) like :pessoaParentesco");
		}
		/*
		if (pessoa.getSituacao() != null && pessoa.getSituacao().size() > 0) {
			String operadorAnd = (pessoa.getSituacao().size() > 1) ? " and" : "";
			predicate.append(" and (");
			
			for (int i = 0; i < pessoa.getSituacao().size(); i++) {
				predicate.append(" upper(pessoa.situacao.sigla) like :pessoaSituacaoSigla" + i);
				predicate.append(operadorAnd);
			}
			predicate.append(" )");
		}
		*/
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			predicate.append(" and pessoa.familia.codigo = :pessoaFamiliaCodigo");
		}

		
		String jpql = "Select pessoa from Pessoa pessoa where " + predicate;
		TypedQuery<Pessoa> query = manager.createQuery(jpql, Pessoa.class);

		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			query.setParameter("pessoaSus", pessoa.getSus());
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			query.setParameter("pessoaNome", pessoa.getNome().toUpperCase());
		}
		if (pessoa.getDtnascimento() != null) {
			query.setParameter("pessoaDtnascimento", pessoa.getDtnascimento());
		}
		if (pessoa.getIdade() != 0) {
			query.setParameter("pessoaIdade", pessoa.getIdade());
		}
		if (pessoa.getSexo() != 0) {
			query.setParameter("pessoaSexo", pessoa.getSexo());
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			query.setParameter("pessoaRacaCodigo", pessoa.getRaca().getCodigo());
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			query.setParameter("pessoaEscolaridadeCodigo", pessoa.getEscolaridade().getCodigo());
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			query.setParameter("pessoaVinculoCodigo", pessoa.getVinculo().getCodigo());
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			query.setParameter("pessoaMae", pessoa.getMae().toUpperCase());
		}
		if (pessoa.getParentesco() != null && pessoa.getParentesco().length() > 1) {
			query.setParameter("pessoaParentesco", pessoa.getParentesco().toUpperCase());
		}
		/*
		if (pessoa.getSituacao() != null && pessoa.getSituacao().size() > 0) {
			for (int i = 0; i < pessoa.getSituacao().size(); i++) {
				query.setParameter("pessoaSituacaoSigla" + i, pessoa.getSituacao().get(i).getSigla().toUpperCase());
			}
		}
		*/
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			query.setParameter("pessoaFamiliaCodigo", pessoa.getFamilia().getCodigo());
		}

		List<Pessoa> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<Pessoa> searchListPessoa(Pessoa pessoa) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			predicate.append(" and pessoa.sus = :pessoaSus");
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			predicate.append(" and upper(pessoa.nome) like :pessoaNome");
		}
		if (pessoa.getDtnascimento() != null) {
			predicate.append(" and pessoa.dtnascimento = :pessoaDtnascimento");
		}
		if (pessoa.getIdade() != 0) {
			predicate.append(" and pessoa.idade = :pessoaIdade");
		}
		if (pessoa.getSexo() != 0) {
			predicate.append(" and upper(pessoa.sexo) = :pessoaSexo");
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			predicate.append(" and pessoa.raca.codigo = :pessoaRacaCodigo");
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			predicate.append(" and pessoa.escolaridade.codigo = :pessoaEscolaridadeCodigo");
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			predicate.append(" and pessoa.vinculo.codigo = :pessoaVinculoCodigo");
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			predicate.append(" and upper(pessoa.mae) like :pessoaMae");
		}
		if (pessoa.getParentesco() != null && pessoa.getParentesco().length() > 1) {
			predicate.append(" and upper(pessoa.parentesco) like :pessoaParentesco");
		}
		if (pessoa.getSituacao() != null && pessoa.getSituacao().size() > 0) {
			String operadorAnd = (pessoa.getSituacao().size() > 1) ? " and" : "";
			predicate.append(" and (");
			
			for (int i = 0; i < pessoa.getSituacao().size(); i++) {
				predicate.append(" upper(pessoa.situacao.sigla) like :pessoaSituacaoSigla" + i);
				predicate.append(operadorAnd);
			}
			predicate.append(" )");
		}
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			predicate.append(" and pessoa.familia.codigo = :pessoaFamiliaCodigo");
		}

		
		String jpql = "Select pessoa from Pessoa pessoa where " + predicate;
		TypedQuery<Pessoa> query = manager.createQuery(jpql, Pessoa.class);

		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			query.setParameter("pessoaSus", pessoa.getSus());
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			query.setParameter("pessoaNome", pessoa.getNome().toUpperCase());
		}
		if (pessoa.getDtnascimento() != null) {
			query.setParameter("pessoaDtnascimento", pessoa.getDtnascimento());
		}
		if (pessoa.getIdade() != 0) {
			query.setParameter("pessoaIdade", pessoa.getIdade());
		}
		if (pessoa.getSexo() != 0) {
			query.setParameter("pessoaSexo", pessoa.getSexo());
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			query.setParameter("pessoaRacaCodigo", pessoa.getRaca().getCodigo());
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			query.setParameter("pessoaEscolaridadeCodigo", pessoa.getEscolaridade().getCodigo());
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			query.setParameter("pessoaVinculoCodigo", pessoa.getVinculo().getCodigo());
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			query.setParameter("pessoaMae", pessoa.getMae().toUpperCase());
		}
		if (pessoa.getParentesco() != null && pessoa.getParentesco().length() > 1) {
			query.setParameter("pessoaParentesco", pessoa.getParentesco().toUpperCase());
		}
		if (pessoa.getSituacao() != null && pessoa.getSituacao().size() > 0) {
			for (int i = 0; i < pessoa.getSituacao().size(); i++) {
				query.setParameter("pessoaSituacaoSigla" + i, pessoa.getSituacao().get(i).getSigla().toUpperCase());
			}
		}
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			query.setParameter("pessoaFamiliaCodigo", pessoa.getFamilia().getCodigo());
		}

		List<Pessoa> result = query.getResultList();
		return result;
		
	}

	public Pessoa searchPessoaSus(String sus) {
		TypedQuery<Pessoa> query = manager.createQuery("Select pessoa from Pessoa pessoa where pessoa.sus = :pessoaSus", Pessoa.class);
		query.setParameter("pessoaSus", sus);
		List<Pessoa> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	public List<Pessoa> searchListOptionsPessoa(PessoaSearchOptions pessoa) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			predicate.append(" and pessoa.sus = :pessoaSus");
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			predicate.append(" and upper(pessoa.nome) like :pessoaNome");
		}
		if (pessoa.getDtNascimento() != null) {
			predicate.append(" and pessoa.dtnascimento = :pessoaDtnascimento");
		}
		if (pessoa.getIdadeInicial() != null && pessoa.getIdadeFinal() != null && pessoa.getIdadeInicial() != 0 && pessoa.getIdadeFinal() != 0) {
			predicate
					.append(" and pessoa.idade between :idadeInicial and :idadeFinal");
		} else {
			if (pessoa.getIdadeInicial() != null && pessoa.getIdadeInicial() != 0) {
				predicate.append(" and pessoa.idade >= :idadeInicial");
			}
			if (pessoa.getIdadeFinal() != null && pessoa.getIdadeFinal() != 0) {
				predicate.append(" and pessoa.idade <= :idadeFinal");
			}
		}
		if (pessoa.getSexo() == 'm' || pessoa.getSexo() == 'f') {
			predicate.append(" and upper(pessoa.sexo) = :pessoaSexo");
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			predicate.append(" and pessoa.raca.codigo = :pessoaRacaCodigo");
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			predicate.append(" and pessoa.escolaridade.codigo = :pessoaEscolaridadeCodigo");
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			predicate.append(" and pessoa.vinculo.codigo = :pessoaVinculoCodigo");
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			predicate.append(" and upper(pessoa.mae) like :pessoaMae");
		}
		/*
		if (pessoa.getDoencas() != null && pessoa.getDoencas().size() > 0) {
			predicate.append(" and pessoa.situacao in :pessoaDoencas");
			
			String operadorAnd = (pessoa.getDoencas().size() > 1) ? " and" : "";
			predicate.append(" and (");
			
			for (int i = 0; i < pessoa.getDoencas().size(); i++) {
				predicate.append(" upper(pessoa.situacao.sigla) like :pessoaSituacaoSigla" + i);
				predicate.append(operadorAnd);
			}
			predicate.append(" )");
			
		}
		*/
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			predicate.append(" and pessoa.familia.codigo = :pessoaFamiliaCodigo");
		}

		
		String jpql = "Select pessoa from Pessoa pessoa where " + predicate;
		System.out.println(jpql);
		TypedQuery<Pessoa> query = manager.createQuery(jpql, Pessoa.class);

		if (pessoa.getSus() != null && pessoa.getSus().length() > 1) {
			query.setParameter("pessoaSus", pessoa.getSus());
		}
		if (pessoa.getNome() != null && pessoa.getNome().length() > 1) {
			query.setParameter("pessoaNome", "%" + pessoa.getNome().toUpperCase()+ "%");
		}
		if (pessoa.getDtNascimento() != null) {
			query.setParameter("pessoaDtnascimento", pessoa.getDtNascimento());
		}
		if (pessoa.getIdadeInicial() != null && pessoa.getIdadeFinal() != null && pessoa.getIdadeInicial() != 0 && pessoa.getIdadeFinal() != 0) {
			query.setParameter("idadeInicial", pessoa.getIdadeInicial());
			query.setParameter("idadeFinal", pessoa.getIdadeFinal());
		} else {
			if (pessoa.getIdadeInicial() != null && pessoa.getIdadeInicial() != 0) {
				query.setParameter("idadeInicial", pessoa.getIdadeInicial());
			}
			if (pessoa.getIdadeFinal() != null && pessoa.getIdadeFinal() != 0) {
				query.setParameter("idadeFinal", pessoa.getIdadeFinal());
			}
		}
		if (pessoa.getSexo() == 'm' || pessoa.getSexo() == 'f') {
			query.setParameter("pessoaSexo", Character.toUpperCase(pessoa.getSexo()));
		}
		if (pessoa.getRaca() != null && pessoa.getRaca().getCodigo() != 0) {
			query.setParameter("pessoaRacaCodigo", pessoa.getRaca().getCodigo());
		}
		if (pessoa.getEscolaridade() != null && pessoa.getEscolaridade().getCodigo() != 0) {
			query.setParameter("pessoaEscolaridadeCodigo", pessoa.getEscolaridade().getCodigo());
		}
		if (pessoa.getVinculo() != null && pessoa.getVinculo().getCodigo() != 0) {
			query.setParameter("pessoaVinculoCodigo", pessoa.getVinculo().getCodigo());
		}
		if (pessoa.getMae() != null && pessoa.getMae().length() > 1) {
			query.setParameter("pessoaMae", "%" + pessoa.getMae().toUpperCase() + "%");
		}
		/*
		if (pessoa.getDoencas() != null && pessoa.getDoencas().size() > 0) {
			query.setParameter("pessoaDoencas", pessoa.getDoencas());
			
			for (int i = 0; i < pessoa.getDoencas().size(); i++) {
				query.setParameter("pessoaSituacaoSigla" + i, pessoa.getDoencas().get(i).getSigla().toUpperCase());
			}
			
		}
		*/
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getCodigo() != 0) {
			query.setParameter("pessoaFamiliaCodigo", pessoa.getFamilia().getCodigo());
		}

		List<Pessoa> result = query.getResultList();
		System.out.println(result.size());
		return result;
	}
	
}
