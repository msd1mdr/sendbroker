package bman.scrp.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.FileQueue;
import bman.scrp.persist.FileQueueRepository;

@Component
public class UpdateDataProcessor implements Processor {{}

	@Autowired
	private FileQueueRepository fileQueueRepo;

	@Override
	public void process(Exchange exchange) throws Exception {

		Long submitId = (Long)exchange.getIn().getHeader("submitid");
		
		Optional<FileQueue> optFileQueue = fileQueueRepo.findById(submitId);
		if (optFileQueue.isPresent()) {
			FileQueue fileQueue = optFileQueue.get();
			fileQueue.setStatus("DONE");
			fileQueue.setErrormsg("");
			fileQueueRepo.save(fileQueue);
		}
		
	}
}