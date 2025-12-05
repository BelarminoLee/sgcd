/**
 * 
 */
package sgcd.gov.mz.repository.helper.entidade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.filter.EntidadeFilter;

/**
 * @author Lee dos Santos
 *
 */
public interface EntidadesQueries {

	public Page<Entidade> filtrar(EntidadeFilter filtro, Pageable pageable);
}
