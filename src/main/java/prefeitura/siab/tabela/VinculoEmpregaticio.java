package prefeitura.siab.tabela;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;


@Entity
@Table(name="TB_VINCULO")
public class VinculoEmpregaticio {

	//ATRIBUTOS
	private Integer codigo;
	private String nome;
	private Pessoa pessoa;
	
	//PROPRIEDADES	
	@Id
	@Column(name="VIN_CODIGO")
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	@Column(name="VIN_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@OneToOne(mappedBy="vinculo")
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
}
