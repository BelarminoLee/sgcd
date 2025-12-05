/**
 * 
 */
package sgcd.gov.mz.dto;

import java.util.HashMap;
import java.util.Map;

import sgcd.gov.mz.model.PontoSituacao;

/**
 * @author Lee dos Santos
 *
 */
public class EstatisticaEntidadeDTO {

	private String nomeEntidade;
    private Map<PontoSituacao, Long> totaisPorSituacao = new HashMap<>();

    public EstatisticaEntidadeDTO(String nomeEntidade) {
        this.nomeEntidade = nomeEntidade;
    }

    public void adicionar(PontoSituacao situacao, Long total) {
        totaisPorSituacao.put(situacao, total);
    }

	public String getNomeEntidade() {
		return nomeEntidade;
	}

	public Map<PontoSituacao, Long> getTotaisPorSituacao() {
		return totaisPorSituacao;
	}

	public void setNomeEntidade(String nomeEntidade) {
		this.nomeEntidade = nomeEntidade;
	}

	public void setTotaisPorSituacao(Map<PontoSituacao, Long> totaisPorSituacao) {
		this.totaisPorSituacao = totaisPorSituacao;
	}
    
    
}
