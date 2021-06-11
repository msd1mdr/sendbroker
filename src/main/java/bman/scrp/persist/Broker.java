package bman.scrp.persist;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name="ANGGOTA_BURSA")
public class Broker {

	@Id
	private String kodeAb;
	private String ipAddress;
	private String port;
	private String ftpUser;
	private String ftpPasswd;
	private String emailAddress;
	
	public String getKodeAb() {
		return kodeAb;
	}
	public void setKodeAb(String kodeAb) {
		this.kodeAb = kodeAb;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getFtpUser() {
		return ftpUser;
	}
	public void setFtpUser(String ftpUser) {
		this.ftpUser = ftpUser;
	}
	public String getFtpPasswd() {
		return ftpPasswd;
	}
	public void setFtpPasswd(String ftpPasswd) {
		this.ftpPasswd = ftpPasswd;
	}
	
	public String getEmailAddress() {
		return this.emailAddress.replace(";", ",");
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	
	
}
