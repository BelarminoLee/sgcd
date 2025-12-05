package sgcd.gov.mz.thymeleaf;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Component;
import org.thymeleaf.dialect.AbstractProcessorDialect;
import org.thymeleaf.processor.IProcessor;
import org.thymeleaf.standard.StandardDialect;

import sgcd.gov.mz.thymeleaf.processor.ClassForErrorAttributeTagProcessor;
import sgcd.gov.mz.thymeleaf.processor.MenuAttributeTagProcessor;
import sgcd.gov.mz.thymeleaf.processor.MessageElementTagProcessor;
import sgcd.gov.mz.thymeleaf.processor.OrderElementTagProcessor;
import sgcd.gov.mz.thymeleaf.processor.PaginationElementTagProcessor;

@Component
public class BrewerDialect extends AbstractProcessorDialect {

	public BrewerDialect() {
		super("AlgaWorks Brewer", "brewer", StandardDialect.PROCESSOR_PRECEDENCE);
	}
	
	@Override
	public Set<IProcessor> getProcessors(String dialectPrefix) {
		final Set<IProcessor> processadores = new HashSet<>();
		processadores.add(new ClassForErrorAttributeTagProcessor(dialectPrefix));
		processadores.add(new MessageElementTagProcessor(dialectPrefix));
		processadores.add(new OrderElementTagProcessor(dialectPrefix));
		processadores.add(new PaginationElementTagProcessor(dialectPrefix));
		processadores.add(new MenuAttributeTagProcessor(dialectPrefix));
		return processadores;
	}

}
