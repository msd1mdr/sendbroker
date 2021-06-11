package bman.scrp.processor;

import java.io.FileNotFoundException;

import org.apache.camel.AggregationStrategy;
import org.apache.camel.Exchange;
import org.apache.camel.attachment.AttachmentMessage;
import org.springframework.stereotype.Component;

import bman.scrp.exception.FileMissingException;

@Component
public class KeepHeaderAggr implements AggregationStrategy  {

	@Override
	public Exchange aggregate(Exchange oldExchange, Exchange newExchange)  {
//		System.out.print("sedang di aggregate: ");
//		Long submitId = (Long) oldExchange.getIn().getHeader("fileQ.submitId");
//		String filename = (Long) oldExchange.getIn().getHeader("filename");
//		System.out.println(oldExchange.getMessage().getHeader("submitId"));
		
//		AttachmentMessage attachment = new AttachmentMessage();
		oldExchange.getIn().setBody(newExchange.getIn().getBody());
        return oldExchange; 
	}


}
