package prefeitura.siab.persistencia;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Enfermeira;

@Component
public class EnfermeiraDao implements Serializable{
	
	private static final long serialVersionUID = 1L;

	
	private @PersistenceContext EntityManager manager;

	public void insert(Enfermeira enfermeira){
		manager.persist(enfermeira);
	}

	public void update(Enfermeira enfermeira) {
		manager.merge(enfermeira);
	}

	public void delete(Enfermeira enfermeira) {
		Enfermeira auxiliar = manager.find(Enfermeira.class, enfermeira.getMatricula());
		manager.remove(auxiliar);
	}

	public Enfermeira searchEnfermeira(Enfermeira enfermeira) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (enfermeira.getCoren() != null && enfermeira.getCoren() != 0){
			predicate.append(" and enfermeira.coren = :enfCoren");
		}
		
		String jpql = "Select enfermeira from Enfermeira enfermeira where " + predicate;
		TypedQuery<Enfermeira> query = manager.createQuery(jpql, Enfermeira.class);
		
		if (enfermeira.getCoren() != null && enfermeira.getCoren() != 0){
			query.setParameter("enfCoren", enfermeira.getCoren());
		}
		
		List<Enfermeira> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}

	public List<Enfermeira> searchListEnfermeira(Enfermeira enfermeira) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		
		if (enfermeira.getCoren() != null && enfermeira.getCoren() != 0){
			predicate.append(" and enfermeira.coren = :enfCoren");
		}
		if(enfermeira.getEquipe() != null && enfermeira.getEquipe().length() < 5){
			predicate.append(" and enfermeira.equipe like :enfEquipe");
		}
		if(enfermeira.getMatricula() != null && enfermeira.getMatricula() != 0){
			predicate.append(" and enfermeira.matricula = :enfMatricula");
		}
		String jpql = "Select enfermeira from Enfermeira enfermeira where " + predicate;
		TypedQuery<Enfermeira> query = manager.createQuery(jpql, Enfermeira.class);
		
		if (enfermeira.getCoren() != null && enfermeira.getCoren() != 0){
			query.setParameter("enfCoren", enfermeira.getCoren());
		}
		if(enfermeira.getEquipe() != null && enfermeira.getEquipe().length() < 5){
			query.setParameter("enfEquipe", "%"+enfermeira.getEquipe()+"%");
		}
		if(enfermeira.getMatricula() != null && enfermeira.getMatricula() != 0){
			query.setParameter("enfMatricula", enfermeira.getMatricula());
		}
		List<Enfermeira> result = query.getResultList();

		return result;
	}

	public Enfermeira searchEnfermeiraMatricula(Integer matricula) {
		TypedQuery<Enfermeira> query = manager.createQuery("Select enfermeira from Enfermeira enfermeira where enfermeira.matricula = :enfermeiraMatricula", Enfermeira.class);
		query.setParameter("enfermeiraMatricula", matricula);
		List<Enfermeira> result = query.getResultList();
		
		if(result.isEmpty()){
			return null;
		}else{
			return result.get(0);
		}
	}
}