package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Familia;

@Component
public class FamiliaDao {

	private @PersistenceContext EntityManager manager;
	
	public void insert(Familia familia){
		manager.persist(familia);
	}

	public void updateFamilia(Familia familia) {
		manager.merge(familia);
	}

	public void delete(Familia familia) {
		Familia familiaAux = manager.find(Familia.class, familia.getCodigo());
		manager.remove(familiaAux);
	}

	public Familia searchFamilia(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");

		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			predicate.append(" and familia.codigo = :familiaCodigo");
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			predicate.append(" and familia.agente.matricula = :familiaAgenteMatricula");
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			predicate.append(" and upper(familia.rua.rua) like :familiaRuaRua");
		}
		/*
		if (familia.getPessoas() != null && familia.getPessoas().size() != 0) {
			String operadorAnd = (familia.getPessoas().size() > 1) ? " and" : "";
			predicate.append(" and (");
			
			for (int i = 0; i < familia.getPessoas().size(); i++) {
				predicate.append(" upper(familia.pessoas.nome) like :familiaPessoaNome" + i);
				predicate.append(operadorAnd);
			}
			predicate.append(" )");
		}
		*/
		
		String jpql = "Select familia from Familia familia where " + predicate;
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			query.setParameter("familiaCodigo", familia.getCodigo());
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			query.setParameter("familiaAgenteMatricula", familia.getAgente().getMatricula());
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			query.setParameter("familiaRuaRua", familia.getRua().getRua().toUpperCase());
		}
		/*
		if (familia.getPessoas() != null && familia.getPessoas().size() != 0) {
			for (int i = 0; i < familia.getPessoas().size(); i++) {
				query.setParameter("familiaPessoaNome" + i, familia.getPessoas().get(i).getNome().toUpperCase());
			}
		}
		*/

		List<Familia> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<Familia> searchListFamilia(Familia familia) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			predicate.append(" and familia.codigo = :familiaCodigo");
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			predicate.append(" and familia.agente.matricula = :familiaAgenteMatricula");
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			predicate.append(" and upper(familia.rua.rua) like :familiaRuaRua");
		}
		/*
		if (familia.getPessoas() != null && familia.getPessoas().size() != 0) {
			String operadorAnd = (familia.getPessoas().size() > 1) ? " and" : "";
			predicate.append(" and (");
			
			for (int i = 0; i < familia.getPessoas().size(); i++) {
				predicate.append(" upper(familia.pessoas.nome) like :familiaPessoaNome" + i);
				predicate.append(operadorAnd);
			}
			predicate.append(" )");
		}
		*/
		String jpql = "Select familia from Familia familia where " + predicate;
		TypedQuery<Familia> query = manager.createQuery(jpql, Familia.class);
		
		if (familia.getCodigo() != null && familia.getCodigo() != 0) {
			query.setParameter("familiaCodigo", familia.getCodigo());
		}
		if (familia.getAgente() != null && familia.getAgente().getMatricula() != 0) {
			query.setParameter("familiaAgenteMatricula", familia.getAgente().getMatricula());
		}
		if (familia.getRua() != null && familia.getRua().getRua().length() != 0) {
			query.setParameter("familiaRuaRua", familia.getRua().getRua().toUpperCase());
		}
		/*
		if (familia.getPessoas() != null && familia.getPessoas().size() != 0) {
			for (int i = 0; i < familia.getPessoas().size(); i++) {
				query.setParameter("familiaPessoaNome" + i, familia.getPessoas().get(i).getNome().toUpperCase());
			}
		}
		 */
		List<Familia> result = query.getResultList();
		return result;
	}

	public Familia searchFamiliaCodigo(int codigoFamilia) {
		TypedQuery<Familia> query = manager.createQuery("Select familia from Familia familia where familia.codigo = :familiaCodigo", Familia.class);
		query.setParameter("familiaCodigo", codigoFamilia);
		List<Familia> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

}
