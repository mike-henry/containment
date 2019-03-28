package com.spx.session;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.ApplicationScoped;
import org.apache.commons.lang3.StringUtils;
import lombok.extern.slf4j.Slf4j;

@ApplicationScoped
@Slf4j
public class CredentialAuthenticator {

  Map<String, StartToken> startTokens = new HashMap<String, StartToken>();

  Map<String, SessionToken> sessionTokens = new HashMap<String, SessionToken>();


  public CredentialAuthenticator() {
    log.info("Authenticator Starting up");
  }

  public Boolean isValid(Credentials enteredCredentials) {
    String oneTimePassowrd = getHashedPassword(enteredCredentials.getUser(), enteredCredentials.getToken());
    log.info("user {} has  password entered {} password should be {}", enteredCredentials.getUser(),
        enteredCredentials.getPassword(), oneTimePassowrd);
    return StringUtils.equals(enteredCredentials.getPassword(), oneTimePassowrd);
  }

  private String getHashedPassword(String password, String challenge) {

    String result;
    try {
      result = doGetHashedPassword(password, challenge);
      return result;
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private String doGetHashedPassword(String password, String challenge)
      throws NoSuchAlgorithmException, UnsupportedEncodingException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    String text = password + challenge;
    md.update(text.getBytes("UTF-8")); // Change this to "UTF-16" if needed
    byte[] digest = md.digest();
    String result = new String(java.util.Base64.getEncoder().encode(digest));
    log.info("Hashed");
    return result;
  }

  public StartToken start(String userName) {
    StartToken result = new StartToken();
    this.startTokens.put(userName, result);
    log.debug("user {} initialized session with start token {}", userName, result.getValue());
    return result;
  }

  public void end(SessionToken session) {
    if (session != null)
      log.info("Session " + session.getValue());
    sessionTokens.values().remove(session);
  }

  public SessionToken createSession(String user) {
    SessionToken sessionToken = new SessionToken();
    this.sessionTokens.put(user, sessionToken);
    return sessionToken;
  }

}
