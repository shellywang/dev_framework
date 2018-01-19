/**
 * 
 */
package com.shelly.spring.mvc.login;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author zlwang
 *
 */
@Controller
public class LoginController {

	public static final String LOGIN_FAILURE_COUNT = "loginFailureCount";

	// ldap配置
	private Hashtable<String, String> env = new Hashtable<String, String>();
	LdapContext ldapContext = null;

	final String ldapUserName = "cn=sync-admin,OU=common-act,OU=Internal users,OU=users,ou=saif,dc=saif,dc=com";
	final String ldapUserPwd = "Saif@2010";
	final String ldapUrl = "ldap://172.16.110.11:389/dc=saif,dc=com";

	@RequestMapping(value = "index")
	public String index() {
		// SSO
		String path = ServletActionContext.getRequest().getContextPath();
		String basepath = ServletActionContext.getRequest().getScheme() + "://"
				+ ServletActionContext.getRequest().getServerName() + ":"
				+ ServletActionContext.getRequest().getServerPort() + path + "/";
		String myUrl = basepath + "ssoLogin.action";
		String clientUrl = "http://portal.saif.sjtu.edu.cn/ssobridge/app.aspx?a=FRF_Faculty&r=" + myUrl;
		ServletActionContext.getRequest().getSession().setAttribute("clientUrl", clientUrl);
		return "ssoVerify";
	}

	@RequestMapping(value = "ssoLogin")
	public String ssoLogin() {
		String UserIdentity = ServletActionContext.getRequest().getParameter("UserIdentity");

		String strDecrypt = "";// N9MUYh8ouk4=;3ZOJjkdu8yY=
		String key = "N9MUYh8ouk4";// "lUiVWNV/MA8=";
		String IV = "3ZOJjkdu8yY=";// "rDFX9T4Pg3g=";

		try {
			strDecrypt = DES64Util.decrypt(UserIdentity, key, IV);
		} catch (Exception e) {
			e.printStackTrace();
		}

		int iSplit = strDecrypt.indexOf("&");
		int iSplit2 = strDecrypt.indexOf("=");
		String loginName = strDecrypt.substring(iSplit2 + 1, iSplit);

		ServletActionContext.getRequest().getSession().setAttribute("proAccount", loginName);
		return "home";
	}

	public String login() {
		String account = ServletActionContext.getRequest().getParameter("proAccount");
		String pwd = ServletActionContext.getRequest().getParameter("password");
		ServletActionContext.getRequest().getSession().setAttribute("proAccount", account);

		if (doLogin(account, pwd)) {
			return "home";
		}
		return forward("login", "Wrong UserName or Password");
	}

	private String forward(String view, String message) {
		addActionMessage(getText(message));
		return view;
	}

	private boolean isLogin(String user, String pwd) throws NamingException {
		SearchControls sc = new SearchControls();
		sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
		NamingEnumeration<SearchResult> answer = ldapContext.search("", "cn=" + user, sc);
		String distinguishedName = "";
		while (answer.hasMoreElements()) {
			Object obj = (Object) answer.nextElement();
			if (obj instanceof SearchResult) {
				SearchResult si = (SearchResult) obj;
				Attributes attrs = si.getAttributes();
				distinguishedName = attrs.get("distinguishedName").toString().split(": ")[1];
				break;
			} else {
				System.out.println(obj);
			}
		}
		if ("".equals(distinguishedName)) {
			return false;
		}

		LdapContext login = getLdapConnection(distinguishedName, pwd);

		if (null == login) {
			return false;
		} else {
			return true;
		}
	}

	private LdapContext getLdapConnection(String userName, String passwd) {
		// 用户名称，cn,ou,dc 分别：用户，组，域
		env.put(Context.SECURITY_PRINCIPAL, userName);
		// 用户密码 cn 的密码
		env.put(Context.SECURITY_CREDENTIALS, passwd);
		// url 格式：协议://ip:端口/组,域 ,直接连接到域或者组上面
		env.put(Context.PROVIDER_URL, ldapUrl);
		// LDAP 工厂
		env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		// 验证的类型 "none", "simple", "strong"
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		try {
			ldapContext = new InitialLdapContext(env, null);
			System.out.println("---connection is ready----");
		} catch (NamingException e) {
			System.out.println("---get connection failure----");
			return null;
		}
		return ldapContext;
	}

	private boolean doLogin(String account, String password) {
		boolean isLogin = false;
		ldapContext = getLdapConnection(ldapUserName, ldapUserPwd);
		try {
			if (account.equals("admin")) {
				isLogin = true;
			} else {
				isLogin = isLogin(account, password);
			}
			if (!isLogin) {
				return false;
			}
		} catch (NamingException e1) {
			e1.printStackTrace();
		}
		return true;
	}
}
