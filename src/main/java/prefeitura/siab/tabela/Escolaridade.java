package prefeitura.siab.tabela;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="TB_ESCOLARIDADE")
public class Escolaridade {

	//ATRIBUTOS
	private Integer codigo;
	private String nome;
	
	//PROPRIEDADES
	@Id
	@Column(name="ESC_CODIGO")
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer numero) {
		this.codigo = numero;
	}
	
	@Column(name="ESC_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}	
}
