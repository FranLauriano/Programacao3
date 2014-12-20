package prefeitura.siab.apresentacao;

import prefeitura.siab.tabela.Enfermeira;

public class AcsSearchOptions {
	
	//ATRIBUTOS
	private Integer matricula;
	private String nome;
	private Integer microarea;
	private Integer area;
	private Double microregiao;
	private Enfermeira supervisora;
	
	//PROPRIEDADES
	public Integer getMatricula() {
		return matricula;
	}
	public void setMatricula(Integer matricula) {
		this.matricula = matricula;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getMicroarea() {
		return microarea;
	}
	public void setMicroarea(Integer microarea) {
		this.microarea = microarea;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Double getMicroregiao() {
		return microregiao;
	}
	public void setMicroregiao(Double microregiao) {
		this.microregiao = microregiao;
	}
	public Enfermeira getSupervisora() {
		return supervisora;
	}
	public void setSupervisora(Enfermeira supervisora) {
		this.supervisora = supervisora;
	}

}
