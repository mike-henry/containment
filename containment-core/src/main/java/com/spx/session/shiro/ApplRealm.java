package com.spx.session.shiro;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.HashSet;
import java.util.Set;
import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import com.spx.session.HashedUserPasswordToken;
import com.spx.session.UserRepository;
import com.spx.session.model.User;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApplRealm extends AuthorizingRealm {


  private BeanManager beanManager;

  public ApplRealm(BeanManager bm) {
    this.beanManager = bm;

  }

  @Override
  protected void onInit() {
    this.setAuthenticationTokenClass(HashedUserPasswordToken.class);
  }

  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection p) {
    Set<String> roles = getRolesFromUserName(p.getPrimaryPrincipal().toString());
    AuthorizationInfo info = new SimpleAuthorizationInfo(roles);
    return info;
  }

  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken rawToken) throws AuthenticationException {
    HashedUserPasswordToken token = (HashedUserPasswordToken) rawToken;
    String password = getPasswordFromUserName(token.getUsername());
    String hashedPassword = getHashedPassword(password, token.getHash());
    log.warn("Shiro thinks : password {}  is the same as password {}", hashedPassword, new String(token.getPassword()));

    if (StringUtils.equals(hashedPassword, new String(token.getPassword())) == false) {
      throw new AuthenticationException("Unknown User name or password");
    }

    SimpleAuthenticationInfo info =
        new SimpleAuthenticationInfo(token.getUsername(), token.getPassword(), token.getHash());

    return info;
  }

  private Set<String> getRolesFromUserName(String username) {
    Set<String> roles = new HashSet<String>();
    roles.add("test");

    return roles;
  }

  private String getPasswordFromUserName(String username) {
    beanManager.createCreationalContext(null);
    try {
      @SuppressWarnings("unchecked")
      Bean<UserRepository> userDAObean =
          (Bean<UserRepository>) beanManager.resolve(beanManager.getBeans(UserRepository.class));
      CreationalContext<UserRepository> creationalContext = beanManager.createCreationalContext(null);
      UserRepository userRepository = userDAObean.create(creationalContext);
      User user = userRepository.findByName(username);
      return user.getPassword();
    } catch (Exception e)

    {
      e.printStackTrace();
      throw new AuthenticationException("Unknown User name or password", e);
    }
  }

  private String getHashedPassword(String password, String challenge) {
    try {
      return doGetHashedPassword(password, challenge);
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
    String result = Base64.getEncoder().encodeToString(digest);
    log.info(" Shiro Hashed");
    return result;
  }

}
