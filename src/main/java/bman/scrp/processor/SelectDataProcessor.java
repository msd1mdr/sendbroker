package bman.scrp.processor;

import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import bman.scrp.persist.Broker;
import bman.scrp.persist.BrokerRepository;
import bman.scrp.persist.FileQueue;
import bman.scrp.persist.FileQueueRepository;

@Component
public class SelectDataProcessor implements Processor {{}

	@Autowired
	private FileQueueRepository fileQueueRepo;
	@Autowired
	private BrokerRepository brokerRepository ;
	
	@Override
	public void process(Exchange exchange) throws Exception {

		Optional<FileQueue> optFileQueue = fileQueueRepo.findFirstByStatus("READY");
		if (optFileQueue.isPresent()) {
			FileQueue fileQueue = optFileQueue.get();
			Optional<Broker> optBroker = brokerRepository.findById(fileQueue.getRecipient());
			fileQueue.setRcptFtpHost(optBroker.get().getIpAddress());
			fileQueue.setFtpUsername(optBroker.get().getFtpUser());
			fileQueue.setFtpPassword(optBroker.get().getFtpPasswd());
			fileQueue.setRcptEmailAddress(optBroker.get().getEmailAddress());
			
			exchange.getIn().setBody(fileQueue);
		}  
//		System.out.println("habis");
		
	}

}