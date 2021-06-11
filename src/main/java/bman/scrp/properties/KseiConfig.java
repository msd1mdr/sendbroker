package bman.scrp.properties;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import bman.scrp.persist.ParameterRepository;

@Configuration
@ConfigurationProperties(prefix = "scrp")
public class KseiConfig {

	@Autowired 
	private ParameterRepository paramRepo;

	private String smtpHost;
	private String smtpPort;
	private String smtpUsername;
	private String smtpPassword;
	private String mailSubject;
	private String mailBody;
	private String mailFrom;
	
	private String sourcefolder;
	private String sendemail;
	private String sendftp;
	


	@PostConstruct
	public void init() {
		this.smtpHost = paramRepo.findByName("Email Server Port").getValue1();
		this.smtpPort = paramRepo.findByName("Email Server Port").getValue2();
		this.mailSubject = paramRepo.findById("P20").get().getValue1();
		this.mailBody = paramRepo.findById("P20").get().getValue2();
		
	}

	
	public KseiConfig() {
		super();
	}


	public ParameterRepository getParamRepo() {
		return paramRepo;
	}


	public void setParamRepo(ParameterRepository paramRepo) {
		this.paramRepo = paramRepo;
	}


	public String getSmtpHost() {
		return smtpHost;
	}


	public void setSmtpHost(String smtpHost) {
		this.smtpHost = smtpHost;
	}


	public String getSmtpPort() {
		return smtpPort;
	}


	public void setSmtpPort(String smtpPort) {
		this.smtpPort = smtpPort;
	}


	public String getSmtpUsername() {
		return smtpUsername;
	}


	public void setSmtpUsername(String smtpUsername) {
		this.smtpUsername = smtpUsername;
	}


	public String getSmtpPassword() {
		return smtpPassword;
	}


	public void setSmtpPassword(String smtpPassword) {
		this.smtpPassword = smtpPassword;
	}


	public String getMailSubject() {
		return mailSubject;
	}


	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}


	public String getMailBody() {
		return mailBody;
	}


	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}


	public String getMailFrom() {
		return mailFrom;
	}


	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}


	public String getSourcefolder() {
		return sourcefolder;
	}


	public void setSourcefolder(String sourcefolder) {
		this.sourcefolder = sourcefolder;
	}


	public String getSendemail() {
		return sendemail;
	}


	public void setSendemail(String sendemail) {
		this.sendemail = sendemail;
	}


	public String getSendftp() {
		return sendftp;
	}


	public void setSendftp(String sendftp) {
		this.sendftp = sendftp;
	}



}


