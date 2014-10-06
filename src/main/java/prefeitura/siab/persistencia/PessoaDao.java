package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Pessoa;

@Component
public class PessoaDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Pessoa pessoa){
		manager.persist(pessoa);
	}

	public void updateVinculo(Pessoa pessoa) {
		manager.merge(pessoa);
	}

	public void delete(Pessoa pessoa) {
		Pessoa pessoaAux = manager.find(Pessoa.class, pessoa.getCodigo());
		manager.remove(pessoaAux);
	}

	public Pessoa searchPessoa(Pessoa pessoa) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (pessoa.getCodigo() != 0) {
			predicate.append(" and pessoa.codigo = :pessoaCodigo");
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

		if (pessoa.getCodigo() != 0) {
			query.setParameter("pessoaCodigo", pessoa.getCodigo());
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
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<Pessoa> searchListPessoa(Pessoa pessoa) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (pessoa.getCodigo() != 0) {
			predicate.append(" and pessoa.codigo = :pessoaCodigo");
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

		if (pessoa.getCodigo() != 0) {
			query.setParameter("pessoaCodigo", pessoa.getCodigo());
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

	public Pessoa searchPessoaName(String nomePessoa) {
		TypedQuery<Pessoa> query = manager.createQuery("Select pessoa from Pessoa pessoa where upper(pessoa.nome) like :pessoaNome", Pessoa.class);
		query.setParameter("pessoaNome", nomePessoa.toUpperCase());
		List<Pessoa> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
}