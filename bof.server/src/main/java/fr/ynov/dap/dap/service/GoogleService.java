package fr.ynov.dap.dap.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.GmailScopes;

import fr.ynov.dap.dap.Config;

/**
 * 
 * @author Florent
 * Generic service class
 */
public class GoogleService {
	
	@Autowired 
	protected Config cfg;
	
	private Credential credentials;
	private GoogleAuthorizationCodeFlow flow;
	
	public GoogleService(){
	}
	
	/**
	 * 
	 * @param userID
	 * @return The  google credentials for the userID
	 * @throws IOException
	 * @throws GeneralSecurityException
	 */
	protected Credential getCredentials(String userID) throws IOException, GeneralSecurityException {
		return credentials = new AuthorizationCodeInstalledApp(getFlow(), new LocalServerReceiver()).authorize(userID);
	}
 
	/**
	 * 
	 * @return GoogleAuthorizationCodeFlow instance
	 * @throws GeneralSecurityException
	 * @throws IOException
	 */
	public GoogleAuthorizationCodeFlow getFlow() throws GeneralSecurityException, IOException {
		InputStream in = Config.class.getResourceAsStream(cfg.getCREDENTIALS_FILE_PATH());
		final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(cfg.getJSON_FACTORY(), new InputStreamReader(in));
		return new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, cfg.getJSON_FACTORY(), clientSecrets, cfg.getSCOPES())
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(cfg.getTOKENS_DIRECTORY_PATH())))
                .setAccessType("offline")
                .build();
	}
}