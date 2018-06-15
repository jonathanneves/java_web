package controller;

import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import model.Pessoa;

//@SessionScoped Tempo que o site fica online pro usuario. Padrao 30min
@ManagedBean //Classe controladora
@ViewScoped

public class PessoaController implements Serializable{
	
	private static final long serialVersionUID = 1L;

	public  String caminhoOrigem;
	private Pessoa pessoa = new Pessoa();
	private List<Pessoa> listaPessoas = new ArrayList<Pessoa>();
	
	/*public void inserePessoa() {
		listaPessoas.add(pessoa);
		pessoa = new Pessoa(); //pra zerar o que já estava
	}*/
	
	public void inserePessoa() {
		try {
			listaPessoas.add(pessoa);
			pessoa = new Pessoa(); 
			addMessage("Cadastro", "Cliente cadastro com sucesso");
			
			if(!pessoa.getCodigo().isEmpty()) {
				Path origem = Paths.get(caminhoOrigem);
				Path destino = Paths.get("C:\\uploads\\"+ pessoa.getCodigo() + ".png");
				
				Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
			}else {			
				FacesMessage fm = new FacesMessage("Código não preenchido");
				FacesContext.getCurrentInstance().addMessage("código", fm);
				return;
			}
			
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void edita(ActionEvent evt) {	
		//recebe a informaçao do botao q foi clicado, recebe pessoa
		//Vai enviar os dados do xhtml atraves do "pessoaEdita" informa a classe e envia para instancia pessoa
		//getComponent diz qual botao foi clicado
		//getAttributes pegas as informaçoes da pessoa
		//get("pessoaEdita") é o nome do atributo no xhtml
			pessoa = (Pessoa)evt.getComponent().getAttributes().get("pessoaEdita");
			listaPessoas.remove(pessoa);
	}
	
	public void exclui(ActionEvent evt) {	
		Pessoa p = (Pessoa)evt.getComponent().getAttributes().get("pessoaExclui");
		listaPessoas.remove(p);
		System.out.println("excluir "+ pessoa.getNome());
	}
	
	public void upload(FileUploadEvent event) {
		try {
			//UploadedFile uploadedFile = event.getFile();
			//File file = new File("C:\\uploadsProvisorio", uploadedFile.getFileName());
			
			//OutputStream out = new FileOutputStream(file);
			//out.write(uploadedFile.getContents());
			//out.close();
			
			UploadedFile x = event.getFile();
			Path t = Files.createTempFile(null, null);
			Files.copy(x.getInputstream(), t, StandardCopyOption.REPLACE_EXISTING);
			System.out.println(t.toString());
			caminhoOrigem = t.toString();
			
//			Path origem = Paths.get(t.toString());
//			Path destino = Paths.get("C:\\uploads\\"+ x.getFileName());
//			
//			Files.copy(origem, destino, StandardCopyOption.REPLACE_EXISTING);
//			
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	public String navega() {
		return "atividade";
	}
	
	public Pessoa getPessoa() {
		return pessoa;
	}
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public List<Pessoa> getListaPessoas() {
		return listaPessoas;
	}

	public void setListaPessoas(List<Pessoa> listaPessoas) {
		this.listaPessoas = listaPessoas;
	}

	//Mensagem de erro 
	//<p:messages id="msg" showDetail="true" closable="true"/>
	public void addMessage(String summary, String detail) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}
}
