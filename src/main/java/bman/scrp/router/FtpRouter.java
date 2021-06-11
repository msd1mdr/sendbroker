package bman.scrp.router;


import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.processor.EmailHeaderProcessor;
import bman.scrp.processor.ErrorProcessor;
import bman.scrp.processor.KeepHeaderAggr;
import bman.scrp.processor.SelectDataProcessor3;
import bman.scrp.processor.SetupHeaderProcessor;
import bman.scrp.processor.UpdateDataProcessor;
import bman.scrp.properties.KseiConfig;

@Component
public class FtpRouter extends RouteBuilder{
	@Autowired
	private SelectDataProcessor3 selectDataProcessor;
	@Autowired
	private UpdateDataProcessor updateDataProcessor;
	@Autowired
	private ErrorProcessor errorProcessor;
	@Autowired
	private KeepHeaderAggr keepHeaderAggr;
	@Autowired
	private SetupHeaderProcessor setupHeaderProcessor;
	@Autowired
	private EmailHeaderProcessor emailHeaderProcessor;
	@Autowired
	private KseiConfig kseiConfig;
	
	@Override
	public void configure() throws Exception {
	    final Logger log = LoggerFactory.getLogger(FtpRouter.class);

	    log.debug("Start routing");
	    
	    onException(javax.mail.AuthenticationFailedException.class)
	    	.log(LoggingLevel.ERROR, log, "Gagal connect ke server")
	    	.to("direct:dead")
	    	;
    	
	    onException(org.apache.camel.CamelExchangeException.class)
	    	.log(LoggingLevel.ERROR, log, "File tidak ditemukan")
	    	.process(errorProcessor)
	    	.log("${exception.stacktrace}")
	    	;
	    
	    errorHandler(deadLetterChannel("direct:dead"));
	    
		from("timer://timer1?period=60000")   // 60 detik 
			.to("direct:sendmethod")
		;

		from("direct:sendmethod")
			.setHeader("sourcedir", simple(kseiConfig.getSourcefolder()))
			
			.process(selectDataProcessor)

			.loop(simple("${body.size}")).copy()
				.setBody(simple("${body[${header.CamelLoopIndex}]}"))
				.setHeader("submitid", simple("${body.submitId}"))
				
				.choice()
					.when(simple("${body.sendMethod} == 'EMAIL'"))
							.to("direct:email")
					.when(simple("${body.sendMethod} == 'FTP'"))
						.to("direct:ftp")
				.end()

				.process(updateDataProcessor)

			.end()   // end loop
						
		;
		

		from("direct:ftp")
			.log(LoggingLevel.INFO, log, "Akan ftp file ${body.filename}")
			.process(setupHeaderProcessor)
			.pollEnrich()
				.simple("file://${header.sourcedir}?idempotent=false&noop=true&fileName=${header.filename}")
				.aggregationStrategy(keepHeaderAggr)
				.timeout(1000)
//			.toD("ftp://zgb-speedtest-1.tele2.net/upload?fileName=${header.filename}")
				.toD("ftp://${header.ftpusername}@${header.ftphost}/?password=${header.ftppwd}")
			
			.log(LoggingLevel.INFO, log, "Sukses FTP: ${header.filename}") 
		;
	
		from("direct:email")
			.log(LoggingLevel.INFO, log, "Akan email file ${body.filename}")
			.process(emailHeaderProcessor)
			.pollEnrich()
				.simple("file://${header.sourcedir}?idempotent=false&noop=true&fileName=${header.filename}")
				.aggregationStrategy(keepHeaderAggr)
				.timeout(1000)
				
			.setBody(constant("Ini email untuk keperluan testing"))
//			.toD("smtp://frans.mazhar%40mio.co.id@smtp-relay.sendinblue.com:587?"
//					+ "password=hcJ80kxLQOSDZTUz&"
//					+ "to=frans.mazhar%40mii.co.id&"
//					+ "subject=Halo&"
//					+ "from=frans.mazhar%40gmail.com")
			.toD("smtp://${header.smtpuser}@${header.smtphost}:${header.smtpport}?"
					+ "password=${header.smtppwd}&"
					+ "to=${header.to}&"
					+ "subject=${header.subject}&"
					+ "from=${header.from}")

			.log(LoggingLevel.INFO, log, "Sukses EMAIL: ${header.filename}")
		;

		
		from("direct:dead")
			.process(errorProcessor)
			.log(LoggingLevel.ERROR, log, "Gagal kirim ${header.filename}: ${exception.message}")
	    	.log("${exception.stacktrace}")
		;

	}

}
