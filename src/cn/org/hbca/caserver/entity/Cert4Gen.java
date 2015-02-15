package cn.org.hbca.caserver.entity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Cert4Gen {
	private Map extention;
	private String C;
	private String CN;
	private String S;
	private String L;
	private String O;
	private String OU;
	
	public Cert4Gen(){
		extention = new HashMap();
	}
	public String getC() {
		return C;
	}
	public void setC(String c) {
		C = c;
	}
	public String getCN() {
		return CN;
	}
	public void setCN(String cN) {
		CN = cN;
	}
	public String getS() {
		return S;
	}
	public void setS(String s) {
		S = s;
	}
	public String getL() {
		return L;
	}
	public void setL(String l) {
		L = l;
	}
	public String getO() {
		return O;
	}
	public void setO(String o) {
		O = o;
	}
	public String getOU() {
		return OU;
	}
	public void setOU(String oU) {
		OU = oU;
	}
	public String getSN() {
		return SN;
	}
	public void setSN(String sN) {
		SN = sN;
	}
	public Date getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(Date beginDate) {
		this.beginDate = beginDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getIssuerDN() {
		return issuerDN;
	}
	public void setIssuerDN(String issuerDN) {
		this.issuerDN = issuerDN;
	}
	public int getKeyUsage() {
		return keyUsage;
	}
	public void setKeyUsage(int keyUsage) {
		this.keyUsage = keyUsage;
	}
	private String SN;
	private Date beginDate;
	private Date endDate;
	private String issuerDN;
	private int keyUsage;
	
	public String getDN(){
		StringBuffer sb = new StringBuffer();
		sb.append("CN=" + this.CN);
		sb.append(",O=" + this.O);
		sb.append(",L=" + this.L);
		sb.append(",S=" + this.S);
		sb.append(",C=" + this.C);
		return sb.toString();
	}
	
}
