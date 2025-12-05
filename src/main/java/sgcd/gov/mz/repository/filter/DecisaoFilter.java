/**
 * 
 */
package sgcd.gov.mz.repository.filter;

import java.time.LocalDate;
import java.util.List;

import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.model.PontoSituacao;

/**
 * @author Lee dos Santos
 *
 */
public class DecisaoFilter {

	private String termoGeral;

	private String ambito;

	private LocalDate dataDecisao;

	private PontoSituacao pontoSituacao;

	private Entidade entidade;

	private String descricaoDecisao;

	private List<Entidade> pontosFocais;

	public String getAmbito() {
		return ambito;
	}

	public LocalDate getDataDecisao() {
		return dataDecisao;
	}

	public PontoSituacao getPontoSituacao() {
		return pontoSituacao;
	}

	public Entidade getEntidade() {
		return entidade;
	}

	public void setAmbito(String ambito) {
		this.ambito = ambito;
	}

	public void setDataDecisao(LocalDate dataDecisao) {
		this.dataDecisao = dataDecisao;
	}

	public String getTermoGeral() {
		return termoGeral;
	}

	public void setTermoGeral(String termoGeral) {
		this.termoGeral = termoGeral;
	}

	public void setPontoSituacao(PontoSituacao pontoSituacao) {
		this.pontoSituacao = pontoSituacao;
	}

	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}

	public String getDescricaoDecisao() {
		return descricaoDecisao;
	}

	public void setDescricaoDecisao(String descricaoDecisao) {
		this.descricaoDecisao = descricaoDecisao;
	}

	public List<Entidade> getPontosFocais() {
		return pontosFocais;
	}

	public void setPontosFocais(List<Entidade> pontosFocais) {
		this.pontosFocais = pontosFocais;
	}

}
