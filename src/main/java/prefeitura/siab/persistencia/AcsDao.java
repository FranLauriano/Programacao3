package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.apresentacao.AcsSearchOptions;
import prefeitura.siab.tabela.Acs;

@Component
public class AcsDao {

	private @PersistenceContext EntityManager manager;

	public void insert(Acs acs){
		manager.persist(acs);
	}

	public void updateAcs(Acs acs) {
		manager.merge(acs);
	}

	public void delete(Acs acs) {
		Acs acsAux = manager.find(Acs.class, acs.getMatricula());
		manager.remove(acsAux);
	}

	public Acs searchAcs(Acs agente) {
		StringBuilder predicate = new StringBuilder("");
		
		if (agente.getMatricula() != null && agente.getMatricula() != 0 && agente.getMicroarea() != null && agente.getMicroarea() != 0) {
			predicate.append("acs.matricula = :acsMatricula and acs.microarea = :acsMicroarea");
		}
		String jpql = "Select acs from Acs acs where " + predicate;
		System.out.println(jpql);
		TypedQuery<Acs> query = manager.createQuery(jpql, Acs.class);
		if (agente.getMatricula() != null && agente.getMatricula() != 0 && agente.getMicroarea() != null && agente.getMicroarea() != 0) {
			query.setParameter("acsMatricula", agente.getMatricula());
			query.setParameter("acsMicroarea", agente.getMicroarea());
		}
		List<Acs> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}
	
	public List<Acs> searchListAcs(AcsSearchOptions options) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (options.getMatricula() != null && options.getMatricula() != 0) {
			predicate.append(" and acs.matricula = :acsMatricula");
		}
		if (options.getNome() != null && options.getNome().length() >= 1) {
			predicate.append(" and upper(acs.nome) like :acsNome");
		}
		if (options.getMicroarea() != null && options.getMicroarea() != 0) {
			predicate.append(" and acs.microarea = :acsMicroarea");
		}
		if (options.getArea() != null && options.getArea() != 0) {
			predicate.append(" and acs.area = :acsArea");
		}
		if (options.getMicroregiao() != null && options.getMicroregiao() != 0) {
			predicate.append(" and acs.microregiao = :acsMicroregiao");
		}
		String jpql = "Select acs from Acs acs where " + predicate;
		TypedQuery<Acs> query = manager.createQuery(jpql, Acs.class);
		if (options.getMatricula() != null && options.getMatricula() != 0) {
			query.setParameter("acsMatricula", options.getMatricula());
		}
		if (options.getNome() != null && options.getNome().length() >= 1) {
			query.setParameter("acsNome", "%"+options.getNome().toUpperCase()+"%");
		}
		if (options.getMicroarea() != null && options.getMicroarea() != 0) {
			query.setParameter("acsMicroarea", options.getMicroarea());
		}
		if (options.getArea() != null && options.getArea() != 0) {
			query.setParameter("acsArea", options.getArea());
		}
		if (options.getMicroregiao() != null && options.getMicroregiao() != 0) {
			query.setParameter("acsMicroregiao", options.getMicroregiao());
		}
		List<Acs> result = query.getResultList();
		return result;
	}

	public Acs searchAcsName(String nome) {
		TypedQuery<Acs> query = manager.createQuery("Select acs from Acs acs where upper(acs.nome) like :acsNome", Acs.class);
		query.setParameter("acsNome", nome.toUpperCase());
		List<Acs> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}

	public Acs searchAcsMatricula(int matricula) {
		TypedQuery<Acs> query = manager.createQuery("Select acs from Acs acs where acs.matricula = :acsMatricula", Acs.class);
		query.setParameter("acsMatricula", matricula);
		List<Acs> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
	
	public Acs searchAcs(AcsSearchOptions options) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (options.getMatricula() != null && options.getMatricula() != 0) {
			predicate.append(" and acs.matricula = :acsMatricula");
		}
		if (options.getNome() != null && options.getNome().length() >= 1) {
			predicate.append(" and upper(acs.nome) like :acsNome");
		}
		if (options.getMicroarea() != null && options.getMicroarea() != 0) {
			predicate.append(" and acs.microarea = :acsMicroarea");
		}
		if (options.getArea() != null && options.getArea() != 0) {
			predicate.append(" and acs.area = :acsArea");
		}
		if (options.getMicroregiao() != null && options.getMicroregiao() != 0) {
			predicate.append(" and acs.microregiao = :acsMicroregiao");
		}
		String jpql = "Select acs from Acs acs where " + predicate;
		TypedQuery<Acs> query = manager.createQuery(jpql, Acs.class);
		if (options.getMatricula() != null && options.getMatricula() != 0) {
			query.setParameter("acsMatricula", options.getMatricula());
		}
		if (options.getNome() != null && options.getNome().length() >= 1) {
			query.setParameter("acsNome", "%"+options.getNome().toUpperCase()+"%");
		}
		if (options.getMicroarea() != null && options.getMicroarea() != 0) {
			query.setParameter("acsMicroarea", options.getMicroarea());
		}
		if (options.getArea() != null && options.getArea() != 0) {
			query.setParameter("acsArea", options.getArea());
		}
		if (options.getMicroregiao() != null && options.getMicroregiao() != 0) {
			query.setParameter("acsMicroregiao", options.getMicroregiao());
		}
		List<Acs> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}

}
