/**
 * 
 */
package sgcd.gov.mz.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Lee dos Santos
 *
 */
@Entity
@Table(name = "decisao_entidade")
public class DecisaoEntidade {
	
	@EmbeddedId
	private DecisaoEntidadeId id;

	public DecisaoEntidadeId getId() {
		return id;
	}

	public void setId(DecisaoEntidadeId id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DecisaoEntidade other = (DecisaoEntidade) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
