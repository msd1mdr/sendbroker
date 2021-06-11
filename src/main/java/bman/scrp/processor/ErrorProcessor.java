package bman.scrp.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.FileQueue;
import bman.scrp.persist.FileQueueRepository;
import bman.scrp.router.FtpRouter;

@Component
public class ErrorProcessor implements Processor {
    final Logger log = LoggerFactory.getLogger(FtpRouter.class);
    
	@Autowired
	private FileQueueRepository fileQueueRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		Exception e=(Exception)exchange.getProperty(Exchange.EXCEPTION_CAUGHT);
		String errmsg = "";
		if (e.getMessage().length()>1000) errmsg = e.getMessage().substring(0,1000);
		else errmsg = e.getMessage();

		Long submitId = (Long)exchange.getIn().getHeader("submitid");
		
		Optional<FileQueue> optFileQueue = fileQueueRepo.findById(submitId);
		if (optFileQueue.isPresent()) {
			FileQueue fileQueue = optFileQueue.get();
			fileQueue.setStatus("ERROR");
			fileQueue.setErrormsg(errmsg);
			fileQueueRepo.save(fileQueue);
		}

		
	}

}
