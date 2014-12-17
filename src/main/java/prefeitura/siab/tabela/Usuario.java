package prefeitura.siab.tabela;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;

@Entity
@Table(name="TB_USUARIO")
public class Usuario implements Serializable{
	
	private static final long serialVersionUID = 1L;

	//ATRIBUTOS
	private Integer matricula;
	private String senha;
	private TipoUsuario tipo;
	private String nome;
	private String email;
	private Acs acs;
	private Enfermeira enfermeira;
	
	
	@Id
	@Column(name="USU_MATRICULA")
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	
	@NotNull
	@Column(name="USU_SENHA")
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	@Column(name="USU_TIPO")
	public TipoUsuario getTipo() {
		return tipo;
	}
	public void setTipo(TipoUsuario tipo) {
		this.tipo = tipo;
	}
	
	@Column(name="USU_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@Email
	@Column(name="USU_EMAIL")
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="USU_AGENTE")
	public Acs getAcs() {
		return acs;
	}
	public void setAcs(Acs acs) {
		this.acs = acs;
	}
	
	@OneToOne(cascade=CascadeType.REMOVE)
	@JoinColumn(name="USU_ENFERMEIRA")
	public Enfermeira getEnfermeira() {
		return enfermeira;
	}
	public void setEnfermeira(Enfermeira enfermeira) {
		this.enfermeira = enfermeira;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((matricula == null) ? 0 : matricula.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (matricula == null) {
			if (other.matricula != null)
				return false;
		} else if (!matricula.equals(other.matricula))
			return false;
		return true;
	}
	

	
	
}
