package bman.scrp.processor;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.springframework.stereotype.Component;

@Component
public class SendEmailAggr implements AggregationStrategy{

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
		System.out.print("sedang di aggregate: ");
		
//		oldExchange.getIn().setBody(newExchange.getIn().getBody());
        return oldExchange; 
	}

}
