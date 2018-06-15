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
import model.Aluno;
import model.Contato;
import util.HibernateUtil;

@ManagedBean //Classe controladora
@ViewScoped
public class AlunoController {

	private static final long serialVersionUID = 1L;
	
	private Aluno aluno;
	private List<Aluno> alunos;
	private List<Contato> contatos;

	@PostConstruct
	public void inicializa() {
		aluno = new Aluno(); 
		listarContatos();
		listarAlunos();
	}
	
	public void salvar () {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		Transaction t = null;
		try {
			t = sessao.beginTransaction();
			sessao.merge(aluno);		//merge = Salvar (Insert) Ele identifica o ID direto e ja edita
			t.commit();
			aluno = new Aluno();
			addMessage("Cadastro", "Aluno(a) cadastrado(a) com sucesso");
			listarAlunos();
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
			sessao.delete(aluno);			
			t.commit();
			aluno = new Aluno();
			addMessage("Exclusão", "Aluno(a) excluído(a) com sucesso");
			listarAlunos();
		} catch (Exception e) {
			if(t!=null)
				t.rollback();
			throw(e);
		}finally{
			sessao.close();
		}
	}
	
	@SuppressWarnings("unchecked")	//ADD WARNING para tirar o erro de consulta.list()
	public void listarContatos() {
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		try {
			Criteria consulta = sessao.createCriteria(Contato.class);	//Criteria = select (consulta)
			contatos = consulta.list();
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
	
	public void selecionaContato(ActionEvent evt) {
		aluno.setContato((Contato) (evt.getComponent().getAttributes().get("contatoSelecionado")));
		System.out.println("Estado: "+aluno.getContato().getUf());
	}
	
	public void editar(ActionEvent evt) {
		aluno = (Aluno)evt.getComponent().getAttributes().get("alunoEdita");
	}
	
	public void excluir(ActionEvent evt) {
		aluno = (Aluno)evt.getComponent().getAttributes().get("alunoExclui");
		exclui();
	}
	
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
	
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	public List<Aluno> getAlunos() {
		return alunos;
	}
	public void setAlunos(List<Aluno> alunos) {
		this.alunos = alunos;
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
