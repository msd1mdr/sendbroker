package bman.scrp.persist;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Transient;

@Entity(name="FILE_SENDING_Q")
public class FileQueue {
	
	@Id
	private Long submitId;

	private String filename;
	private String sendMethod;
	private String recipient;
	private String submodul;
	private String status;
	private String errormsg;
	private LocalDateTime execute_time;
	@Column(name="REC_ADDR")
	private String rcptFtpHost;
	@Column(name="USERNAME")
	private String ftpUsername;
	@Column(name="PASSWD")
	private String ftpPassword;
	
	@Transient
	private String rcptEmailAddress;
	
	public Long getSubmitId() {
		return submitId;
	}
	public void setSubmitId(Long submitId) {
		this.submitId = submitId;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getSendMethod() {
		return sendMethod;
	}
	public void setSendMethod(String sendMethod) {
		this.sendMethod = sendMethod;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getSubmodul() {
		return submodul;
	}
	public void setSubmodul(String submodul) {
		this.submodul = submodul;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrormsg() {
		return errormsg;
	}
	public void setErrormsg(String errormsg) {
		this.errormsg = errormsg;
	}
	public LocalDateTime getExecute_time() {
		return execute_time;
	}
	public void setExecute_time(LocalDateTime execute_time) {
		this.execute_time = execute_time;
	}
	public String getRcptFtpHost() {
		return rcptFtpHost;
	}
	public void setRcptFtpHost(String rcptFtpHost) {
		this.rcptFtpHost = rcptFtpHost;
	}
	public String getFtpUsername() {
		return ftpUsername;
	}
	public void setFtpUsername(String ftpUsername) {
		this.ftpUsername = ftpUsername;
	}
	public String getFtpPassword() {
		return ftpPassword;
	}
	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}
	
	public String getRcptEmailAddress() {
		return rcptEmailAddress;
	}
	public void setRcptEmailAddress(String rcptEmailAddress) {
		this.rcptEmailAddress = rcptEmailAddress;
	}
	
	
}
