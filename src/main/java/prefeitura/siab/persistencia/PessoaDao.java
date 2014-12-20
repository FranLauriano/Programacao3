package prefeitura.siab.persistencia;

import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.apresentacao.Login;
import prefeitura.siab.apresentacao.PessoaSearchOptions;
import prefeitura.siab.tabela.Pessoa;
import prefeitura.siab.tabela.Usuario;

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
		Pessoa pessoaAux = manager.find(Pessoa.class, pessoa.getCodigo());
		manager.remove(pessoaAux);
	}

	public Pessoa searchPessoaCodigo(Integer codigo) {
		TypedQuery<Pessoa> query = manager.createQuery("Select pessoa from Pessoa pessoa where pessoa.codigo = :pessoaCodigo", Pessoa.class);
		query.setParameter("pessoaCodigo", codigo);
		List<Pessoa> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
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
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Map<String, Object> mapa = externalContext.getSessionMap();
		Login bean = (Login) mapa.get("login");
		Usuario usuario = bean.getUsuario();
		
		if (usuario.getAcs() != null) {
			predicate.append(" and pessoa.familia.agente.matricula = :agenteMatricula");
		}else if(pessoa.getAgente() != null && pessoa.getAgente().getMatricula() != 0){
			predicate.append(" and pessoa.familia.agente.matricula = :agenteMatricula");
		}
	
		if(usuario.getEnfermeira() != null){
			predicate.append(" and pessoa.familia.agente.supervisor.matricula = :supervisorMatricula");
		}else if(pessoa.getEnfermeira() != null && pessoa.getEnfermeira().getMatricula() != 0){
			predicate.append(" and pessoa.familia.agente.supervisor.matricula = :supervisorMatricula");
		}
	
				
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
		if (pessoa.getSexo() != null && (pessoa.getSexo().equals("Masculino") || pessoa.getSexo().equals("Feminino"))) {
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
		
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getNumeroFamilia() != null && pessoa.getFamilia().getNumeroFamilia() != 0) {
			predicate.append(" and pessoa.familia.numeroFamilia = :pessoaFamiliaCodigo");
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
		if (pessoa.getSexo() != null && (pessoa.getSexo().equals("Masculino") || pessoa.getSexo().equals("Feminino"))) {
			query.setParameter("pessoaSexo", pessoa.getSexo().toUpperCase());
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
		if (usuario.getAcs() != null) {
			query.setParameter("agenteMatricula", usuario.getAcs().getMatricula());
		}else if(pessoa.getAgente() != null && pessoa.getAgente().getMatricula() != 0){
			query.setParameter("agenteMatricula", pessoa.getAgente().getMatricula());
		}

		if(usuario.getEnfermeira() != null){
			query.setParameter("supervisorMatricula", usuario.getEnfermeira().getMatricula());
		}else if(pessoa.getEnfermeira() != null && pessoa.getEnfermeira().getMatricula() != 0){
			query.setParameter("supervisorMatricula", pessoa.getEnfermeira().getMatricula());
		}
	
		if (pessoa.getFamilia() != null && pessoa.getFamilia().getNumeroFamilia() != null && pessoa.getFamilia().getNumeroFamilia() != 0) {
			query.setParameter("pessoaFamiliaCodigo", pessoa.getFamilia().getNumeroFamilia());
		}

		List<Pessoa> result = query.getResultList();
		System.out.println(result.size());
		return result;
	}
	
}
