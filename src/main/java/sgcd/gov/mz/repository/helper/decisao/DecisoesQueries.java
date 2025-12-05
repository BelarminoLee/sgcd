/**
 * 
 */
package sgcd.gov.mz.repository.helper.decisao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import sgcd.gov.mz.model.Decisao;
import sgcd.gov.mz.repository.filter.DecisaoFilter;

/**
 * @author Lee dos Santos
 *
 */
public interface DecisoesQueries {

	public Page<Decisao> filtrar(DecisaoFilter filtro, Pageable pageable);
	
	public Decisao buscarComResponsaveis(Long codigo);
	
	 List<Decisao> filtrar(DecisaoFilter filtro);
}
