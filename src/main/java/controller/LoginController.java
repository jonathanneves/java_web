package controller;

import java.io.Serializable;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ManagedBean 
@ViewScoped
public class LoginController implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String login;
	private String senha;
	
	public String fazLogin() {
		if(login.equalsIgnoreCase("admin") && senha.equalsIgnoreCase("admin"))
			return "exemplo1";
		else
			return "erro";
	}
	
	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	
	

}
