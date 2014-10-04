package prefeitura.siab.persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Component;

import prefeitura.siab.tabela.Endereco;

@Component
public class EnderecoDao {

	private @PersistenceContext EntityManager manager;

	public Endereco searchEndereco(Endereco endereco) {
		StringBuilder predicate = new StringBuilder("1 = 1");
		if (endereco.getCep() != null && endereco.getCep() != 0) {
			predicate.append(" and endereco.cep = :enderecoCep");
		}
		String jpql = "Select endereco from Endereco endereco where " + predicate;
		TypedQuery<Endereco> query = manager.createQuery(jpql, Endereco.class);
		if (endereco.getCep() != null && endereco.getCep() != 0) {
			query.setParameter("enderecoCep", endereco.getCep());
		}
		List<Endereco> result = query.getResultList();
		if(result.isEmpty()){
			return null;			
		}else{
			return result.get(0);
		}
	}

	public void insert(Endereco endereco) {
		manager.persist(endereco);
	}
	
}
