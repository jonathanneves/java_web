package controller;

import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.hibernate.Transaction;
import org.hibernate.Criteria;
import org.hibernate.Session;

import model.Disciplina;
import util.HibernateUtil;

@ManagedBean //Classe controladora
@ViewScoped
public class DisciplinaController {

	private static final long serialVersionUID = 1L;
	
	Disciplina disciplina;
	List<Disciplina> disciplinas;
	
	@PostConstruct
	public void inicializa() {
		disciplina = new Disciplina(); 
		listarTodas();
	}
	
	public void salvar () {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(disciplina);		//merge = Salvar (Insert) Ele identifica o ID direto e ja edita
			t.commit();
			disciplina = new Disciplina();
			addMessage("Cadastro", "Disciplina cadastrada com sucesso");
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
			sessao.delete(disciplina);		
			t.commit();
			disciplina = new Disciplina();
			addMessage("Exclusão", "Disciplina excluída com sucesso");
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
			Criteria consulta = sessao.createCriteria(Disciplina.class);	//Criteria = select (consulta)
			disciplinas = consulta.list();
		} catch (Exception e) {
			addMessage("ERRO", "ERRO");
		} finally {
			sessao.close();
		}
	}
	
	
	public void editar(ActionEvent evt) {
		disciplina = (Disciplina)evt.getComponent().getAttributes().get("disciplinaEdita");
	}
	
	public void excluir(ActionEvent evt) {
		disciplina = (Disciplina)evt.getComponent().getAttributes().get("disciplinaExclui");
		exclui();
	}
	
//	public void edita(ActionEvent evt) {
//		disciplina = (Disciplina)evt.getComponent().getAttributes().get("disciplinaEdita");
//		listaDisciplina.remove(disciplina);
//	}
//	
//	public void exclui(ActionEvent evt) {	
//		disciplina = (Disciplina)evt.getComponent().getAttributes().get("alunoExclui");
//		listaDisciplina.remove(disciplina);
//	}
	
	public Disciplina getDisciplina() {
		return disciplina;
	}

	public void setDisciplina(Disciplina disciplina) {
		this.disciplina = disciplina;
	}

	public List<Disciplina> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<Disciplina> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
