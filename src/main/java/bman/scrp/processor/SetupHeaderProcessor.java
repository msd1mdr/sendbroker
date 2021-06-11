package bman.scrp.processor;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.FileQueue;
import bman.scrp.properties.KseiConfig;

@Component
public class SetupHeaderProcessor implements Processor {{}

	@Autowired
	private KseiConfig kseiConfig;

	@Override
	public void process(Exchange exchange) throws Exception {

		FileQueue fileQ = exchange.getIn().getBody(FileQueue.class);
		
		exchange.getIn().setHeader("filename", fileQ.getFilename());
//		exchange.getIn().setHeader("submitid", fileQ.getSubmitId());

		exchange.getIn().setHeader("ftphost", fileQ.getRcptFtpHost());
		exchange.getIn().setHeader("ftpusername", fileQ.getFtpUsername());
		exchange.getIn().setHeader("ftppwd", fileQ.getFtpPassword());

		exchange.getIn().setHeader("smtphost", kseiConfig.getSmtpHost());
		exchange.getIn().setHeader("smtpport", kseiConfig.getSmtpPort());
		exchange.getIn().setHeader("smtpuser", kseiConfig.getSmtpUsername());
		exchange.getIn().setHeader("smtppwd", kseiConfig.getSmtpPassword());
		exchange.getIn().setHeader("to", fileQ.getRcptEmailAddress());
		exchange.getIn().setHeader("from", kseiConfig.getMailFrom());
		exchange.getIn().setHeader("subject", kseiConfig.getMailSubject());
		exchange.getIn().setHeader("mailBody", kseiConfig.getMailBody());
		
	}

}