/**
 * 
 */
package sgcd.gov.mz.repository.helper.entidade;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.filter.EntidadeFilter;
import sgcd.gov.mz.repository.paginacao.PaginacaoUtil;

/**
 * @author Lee dos Santos
 *
 */
public class EntidadesImpl implements EntidadesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(readOnly = true)
	public Page<Entidade> filtrar(EntidadeFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Entidade.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);
		

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(EntidadeFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Entidade.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(EntidadeFilter filtro, Criteria criteria) {
		if (filtro != null) {
			if (!StringUtils.isEmpty(filtro.getNome())) {
				criteria.add(Restrictions.ilike("nome", filtro.getNome(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getPontoFocal())) {
				criteria.add(Restrictions.ilike("pontoFocal", filtro.getPontoFocal(), MatchMode.ANYWHERE));
			}
			
			if (!StringUtils.isEmpty(filtro.getEmail())) {
				criteria.add(Restrictions.ilike("email", filtro.getEmail(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getTelefone())) {
				criteria.add(Restrictions.eq("telefone", filtro.getTelefone()));
			}
		}
	}
}
