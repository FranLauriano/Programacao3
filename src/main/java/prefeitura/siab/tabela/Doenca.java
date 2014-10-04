package prefeitura.siab.tabela;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="TB_DOENCA")
public class Doenca {

	//ATRIBUTOS
	private String sigla;
	private String nome;
	private List<Pessoa> pessoa;

	
	//PROPRIEDADES
	@Id
	@Column(name="DOE_SIGLA")
	public String getSigla() {
		return sigla;
	}
	public void setSigla(String sigla) {
		this.sigla = sigla;
	}
	
	@Column(name="DOE_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@ManyToMany(mappedBy="situacao")
	public List<Pessoa> getPessoa() {
		return pessoa;
	}
	public void setPessoa(List<Pessoa> pessoa) {
		this.pessoa = pessoa;
	}
	
}
