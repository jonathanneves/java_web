package controller;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Aluno;
import model.Disciplina;
import model.Matricula;
import util.HibernateUtil;

@ManagedBean //Classe controladora
@ViewScoped
public class MatriculaController {

	private static final long serialVersionUID = 1L;
	
	private Matricula matricula;
	private List<Matricula> matriculas;
	
	private List<SelectItem> disciplinas;
	private List<SelectItem> alunos;
	
	@PostConstruct
	public void inicializa() {
		matricula = new Matricula(); 
		listarMatriculas();
		listarAlunos();
		listarDisciplinas();
	}
	
	public void salvar () {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(matricula);		//merge = Salvar (Insert) Ele identifica o ID direto e ja edita
			t.commit();
			matricula = new Matricula();
			addMessage("Cadastro", "Aluno matriculado com sucesso");
			listarMatriculas();
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
			sessao.delete(matricula);		
			t.commit();
			matricula = new Matricula();
			addMessage("Exclusão", "Matrícula excluída com sucesso");
			listarMatriculas();
		} catch (Exception e) {
			if(t!=null)
				t.rollback();
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")	//ADD WARNING para tirar o erro de consulta.list()
	public void listarMatriculas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Matricula.class);	//Criteria = select (consulta)
			matriculas = consulta.list();
		} catch (Exception e) {
			addMessage("ERRO", "Erro ao listar contatos");
		} finally {
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")	//ADD WARNING para tirar o erro de consulta.list()
	public void listarAlunos() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Aluno.class);	//Criteria = select (consulta)
			alunos = consulta.list();
		} catch (Exception e) {
			addMessage("ERRO", "Erro ao listar alunos");
		} finally {
			sessao.close();
		}
	}
	@SuppressWarnings("unchecked")	//ADD WARNING para tirar o erro de consulta.list()
	public void listarDisciplinas() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Disciplina.class);	//Criteria = select (consulta)
			disciplinas = consulta.list();
		} catch (Exception e) {
			addMessage("ERRO", "Erro ao listar contatos");
		} finally {
			sessao.close();
		}
	}
	
		
	public void editar(ActionEvent evt) {
		matricula = (Matricula)evt.getComponent().getAttributes().get("matriculaEdita");
	}
	
	public void excluir(ActionEvent evt) {
		matricula = (Matricula)evt.getComponent().getAttributes().get("matriculaExclui");
		exclui();
	}

	public List<Matricula> getMatriculas() {
		return matriculas;
	}

	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}

	public Matricula getMatricula() {
		return matricula;
	}

	public void setMatricula(Matricula matricula) {
		this.matricula = matricula;
	}

	public List<SelectItem> getDisciplinas() {
		return disciplinas;
	}

	public void setDisciplinas(List<SelectItem> disciplinas) {
		this.disciplinas = disciplinas;
	}

	public List<SelectItem> getAlunos() {
		return alunos;
	}

	public void setAlunos(List<SelectItem> alunos) {
		this.alunos = alunos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
