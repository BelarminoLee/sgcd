/**
 * 
 */
package sgcd.gov.mz.model;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Lee dos Santos
 *
 */
@Embeddable
public class DecisaoEntidadeId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToOne
	@JoinColumn(name="codigo_entidade")
	private Entidade entidade;
	
	
	@ManyToOne
	@JoinColumn(name="codigo_decisao")
	private Decisao decisao;




	public Entidade getEntidade() {
		return entidade;
	}


	public Decisao getDecisao() {
		return decisao;
	}


	public void setEntidade(Entidade entidade) {
		this.entidade = entidade;
	}


	public void setDecisao(Decisao decisao) {
		this.decisao = decisao;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((decisao == null) ? 0 : decisao.hashCode());
		result = prime * result + ((entidade == null) ? 0 : entidade.hashCode());
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
		DecisaoEntidadeId other = (DecisaoEntidadeId) obj;
		if (decisao == null) {
			if (other.decisao != null)
				return false;
		} else if (!decisao.equals(other.decisao))
			return false;
		if (entidade == null) {
			if (other.entidade != null)
				return false;
		} else if (!entidade.equals(other.entidade))
			return false;
		return true;
	}


	
	
	
	

}
