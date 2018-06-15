package controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import model.Contato;
import util.HibernateUtil;

@ManagedBean //Classe controladora
@ViewScoped
public class ContatoController {

	private static final long serialVersionUID = 1L;
	
	private Contato contato;
	private List<Contato> contatos;
    
	@PostConstruct
	public void inicializa() {
		contato = new Contato(); 
		listarTodas();
	}
	
	public void salvar () {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(contato);		//merge = Salvar (Insert) Ele identifica o ID direto e ja edita
			t.commit();
			contato = new Contato();
			addMessage("Cadastro", "Contato cadastrado com sucesso");
			listarTodas();
		} catch (Exception e) {
			if(t!=null)
				t.rollback();
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	public void exclui () {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.delete(contato);			
			t.commit();
			contato = new Contato();
			addMessage("Exclusão", "Contato excluído com sucesso");
			listarTodas();
		} catch (Exception e) {
			if(t!=null)
				t.rollback();
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")	//ADD WARNING para tirar o erro de consulta.list()
	public void listarTodas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Contato.class);	//Criteria = select (consulta)
			contatos = consulta.list();
		} catch (Exception e) {
			addMessage("ERRO", "ERRO");
		} finally {
			sessao.close();
		}
	}
	
	public void editar(ActionEvent evt) {
		contato = (Contato)evt.getComponent().getAttributes().get("contatoEdita");
	}
	
	public void excluir(ActionEvent evt) {
		contato = (Contato)evt.getComponent().getAttributes().get("contatoExclui");
		exclui();
	}
	
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	public Contato getContato() {
		return contato;
	}
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	public List<Contato> getContatos() {
		return contatos;
	}
	public void setContatos(List<Contato> contatos) {
		this.contatos = contatos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}