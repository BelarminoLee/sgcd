/**
 * 
 */
package sgcd.gov.mz.repository.helper.decisao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import sgcd.gov.mz.model.Decisao;
import sgcd.gov.mz.model.DecisaoEntidade;
import sgcd.gov.mz.model.Entidade;
import sgcd.gov.mz.repository.filter.DecisaoFilter;
import sgcd.gov.mz.repository.paginacao.PaginacaoUtil;

/**
 * @author Lee dos Santos
 *
 */
public class DecisoesImpl implements DecisoesQueries {

	@PersistenceContext
	private EntityManager manager;

	@Autowired
	private PaginacaoUtil paginacaoUtil;

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Transactional(readOnly = true)
	@Override
	public Page<Decisao> filtrar(DecisaoFilter filtro, Pageable pageable) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Decisao.class);

		paginacaoUtil.preparar(criteria, pageable);
		adicionarFiltro(filtro, criteria);

		return new PageImpl<>(criteria.list(), pageable, total(filtro));
	}

	private Long total(DecisaoFilter filtro) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Decisao.class);
		adicionarFiltro(filtro, criteria);
		criteria.setProjection(Projections.rowCount());
		return (Long) criteria.uniqueResult();
	}

	private void adicionarFiltro(DecisaoFilter filtro, Criteria criteria) {
		criteria.createAlias("entidade", "e");

		if (filtro != null) {
			if (isEntidadePresente(filtro))
				criteria.add((Criterion) Restrictions.eq("entidade", filtro.getEntidade()));

			if (!StringUtils.isEmpty(filtro.getAmbito())) {
				criteria.add(Restrictions.ilike("ambito", filtro.getAmbito(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getDescricaoDecisao())) {
				criteria.add(Restrictions.ilike("descricaoDecisao", filtro.getDescricaoDecisao(), MatchMode.ANYWHERE));
			}

			if (!StringUtils.isEmpty(filtro.getPontoSituacao())) {
				criteria.add(Restrictions.eq("pontoSituacao", filtro.getPontoSituacao()));

				if (!StringUtils.isEmpty(filtro.getDataDecisao())) {
					criteria.add(Restrictions.eq("dataDecisao", filtro.getDataDecisao()));
				}

				if (filtro.getPontosFocais() != null && !filtro.getPontosFocais().isEmpty()) {
					List<Criterion> subqueries = new ArrayList<>();
					for (Long codigoEntidade : filtro.getPontosFocais().stream().mapToLong(Entidade::getCodigo)
							.toArray()) {
						DetachedCriteria dc = DetachedCriteria.forClass(DecisaoEntidade.class);
						dc.add(Restrictions.eq("id.entidade.codigo", codigoEntidade));
						dc.setProjection(Projections.property("id.decisao"));

						subqueries.add(Subqueries.propertyIn("codigo", dc));
					}

					Criterion[] criterions = new Criterion[subqueries.size()];
					criteria.add(Restrictions.and(subqueries.toArray(criterions)));
				}

			}
		}
	}

	private boolean isEntidadePresente(DecisaoFilter filtro) {
		return (filtro.getEntidade() != null && filtro.getEntidade().getCodigo() != null);
	}

	@Transactional(readOnly = true)
	@Override
	public Decisao buscarComResponsaveis(Long codigo) {
		Criteria criteria = manager.unwrap(Session.class).createCriteria(Decisao.class);
		criteria.createAlias("pontosFocais", "d", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eq("codigo", codigo));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
		return (Decisao) criteria.uniqueResult();
	}

	@Override
	public List<Decisao> filtrar(DecisaoFilter filtro) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Decisao> query = builder.createQuery(Decisao.class);
		Root<Decisao> root = query.from(Decisao.class);

		List<Predicate> predicates = new ArrayList<>();

		if (filtro.getTermoGeral() != null && !filtro.getTermoGeral().isEmpty()) {
			String likeTerm = "%" + filtro.getTermoGeral().toLowerCase() + "%";
			predicates.add(builder.or(builder.like(builder.lower(root.get("decisao")), likeTerm),
					builder.like(builder.lower(root.get("ambito")), likeTerm),
					builder.like(builder.lower(root.get("pontoSituacao")), likeTerm),
					builder.like(builder.lower(root.join("entidade").get("nome")), likeTerm)));
		}

		query.where(predicates.toArray(new Predicate[0]));
		return manager.createQuery(query).getResultList();
	}
}
