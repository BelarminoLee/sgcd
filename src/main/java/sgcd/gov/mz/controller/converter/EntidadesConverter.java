/**
 * 
 */
package sgcd.gov.mz.controller.converter;

import javax.persistence.Convert;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.thymeleaf.util.StringUtils;

import sgcd.gov.mz.model.Entidade;

/**
 * @author Lee dos Santos
 *
 */
@Component
public class EntidadesConverter implements Converter<String, Entidade> {

	@Override
	public Entidade convert(String codigo) {
		if (!StringUtils.isEmpty(codigo)) {
			Entidade entidade = new Entidade();
			entidade.setCodigo(Long.valueOf(codigo));
			return entidade;
		}
		return null;
	}

}