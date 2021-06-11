package bman.scrp.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.FileQueue;
import bman.scrp.properties.KseiConfig;

@Component
public class EmailHeaderProcessor implements Processor {

	@Autowired
	private KseiConfig kseiConfig;

	@Override
	public void process(Exchange exchange) throws Exception {

		FileQueue fileQ = exchange.getIn().getBody(FileQueue.class);
		
		exchange.getIn().setHeader("filename", fileQ.getFilename());
		exchange.getIn().setHeader("submitid", fileQ.getSubmitId());

		exchange.getIn().setHeader("smtphost", kseiConfig.getSmtpHost());
		exchange.getIn().setHeader("smtpport", kseiConfig.getSmtpPort());
		exchange.getIn().setHeader("smtpuser", kseiConfig.getSmtpUsername());
		exchange.getIn().setHeader("smtppwd", kseiConfig.getSmtpPassword());
		exchange.getIn().setHeader("To", fileQ.getRcptEmailAddress());
		exchange.getIn().setHeader("From", kseiConfig.getMailFrom());
		exchange.getIn().setHeader("Subject", kseiConfig.getMailSubject());
		
	}

}