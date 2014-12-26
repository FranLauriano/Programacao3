package prefeitura.siab.tabela;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="TB_ACS")
public class Acs {

	//ATRIBUTOS
	private Integer matricula;
	private String nome;
	private Integer microarea;
	private Integer area;
	private Double microregiao;
	private List<Endereco> ruas;
	private List<Familia> familias;
	private Usuario usuario;
	private Enfermeira supervisor;
	
	//PROPRIEDADES
	@Id
	@Column(name="ACS_MATRICULA")
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}

	@Column(name="ACS_MICROREGIAO")	
	public Double getMicroregiao() {
		return microregiao;
	}
	public void setMicroregiao(Double microregiao) {
		this.microregiao = microregiao;
	}
	
	@Column(name="ACS_MICROAREA")
	public Integer getMicroarea() {
		return microarea;
	}
	public void setMicroarea(Integer microarea) {
		this.microarea = microarea;
	}
	
	@Column(name="ACS_AREA")
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	
	@ManyToMany(mappedBy="agentes")
	public List<Endereco> getRuas() {
		return ruas;
	}
	public void setRuas(List<Endereco> ruas) {
		this.ruas = ruas;
	}
	
	@OneToMany(mappedBy="agente")
	public List<Familia> getFamilias() {
		return familias;
	}
	public void setFamilias(List<Familia> familias) {
		this.familias = familias;
	}
	
	@OneToOne(mappedBy="acs")
	public Usuario getUsuario() {
		return usuario;
	}
	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	@ManyToOne
	@JoinColumn(name="ACS_SUPERVISOR")
	public Enfermeira getSupervisor() {
		return supervisor;
	}
	public void setSupervisor(Enfermeira supervisor) {
		this.supervisor = supervisor;
	}
	
	@Column(name="ACS_NOME")
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
	
	
	
}
