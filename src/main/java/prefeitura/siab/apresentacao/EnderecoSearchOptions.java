package prefeitura.siab.apresentacao;

import prefeitura.siab.tabela.Acs;

public class EnderecoSearchOptions {

		//ATRIBUTOS
		private String rua;
		private String bairro;
		private String municipio;
		private String uf;
		private Integer cep;
		private Acs agente;

		//PROPRIEDADES
		public String getRua() {
			return rua;
		}
		public void setRua(String rua) {
			this.rua = rua;
		}
		
		public String getBairro() {
			return bairro;
		}
		public void setBairro(String bairro) {
			this.bairro = bairro;
		}
		
		public String getMunicipio() {
			return municipio;
		}
		public void setMunicipio(String municipio) {
			this.municipio = municipio;
		}
		
		public String getUf() {
			return uf;
		}
		public void setUf(String uf) {
			this.uf = uf;
		}
		
		public Integer getCep() {
			return cep;
		}
		public void setCep(Integer cep) {
			this.cep = cep;
		}
		
		public Acs getAgente() {
			return agente;
		}
		public void setAgente(Acs agente) {
			this.agente = agente;
		}	
		
		
}
