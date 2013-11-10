package nl.tudelft.ec2interface.instancemanager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import nl.tudelft.ec2interface.sysmonitor.SystemUsageInfo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.sun.org.apache.bcel.internal.generic.NEW;

public class MasterActorInfo {
	
	private String publicIP;
	
	public String getPublicIP() {
		return publicIP;
	}

	public void setPublicIP(String publicIP) {
		this.publicIP = publicIP;
	}

	public String ToJson(MasterActorInfo maInfo)
	{
		try {
			
			ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
			String json = ow.writeValueAsString(maInfo);
			return json;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
			
		}
		
	}
	
	public MasterActorInfo FromJson(String jsonString)
	{
		try {
			
			MasterActorInfo maInfo = new ObjectMapper().readValue(jsonString, MasterActorInfo.class);
			return maInfo;
			
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public MasterActorInfo setInfo(String publicIP)
	{
		MasterActorInfo maInfo = new MasterActorInfo();
		maInfo.setPublicIP(publicIP);
		return maInfo;
	}
	
	public MasterActorInfo getInfoFromFile(String filePath)
	{
		try {
			String jsonString = new Scanner(new File(filePath)).useDelimiter("\\Z").next();
			return new MasterActorInfo().FromJson(jsonString);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static void main(String[] args)
	{
		MasterActorInfo maInfo = new MasterActorInfo().setInfo("232.388.1");
		
		String maInfoString = maInfo.ToJson(maInfo).toString();
		
		System.out.println(maInfoString);
		System.out.println(maInfo.FromJson(maInfoString).getPublicIP());
		System.out.println(new MasterActorInfo().getInfoFromFile("masterInfo").getPublicIP());
		
	}

}
