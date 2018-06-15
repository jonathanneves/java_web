package model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Aluno implements Serializable {
	
	private static final long serialVersionUID = 1L; 
	
	@Id		
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column	private String nome;
	@Column	private Date nascimento;
	@Column	private String sexo;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private Contato contato;
																//CascadeType.REMOVE so remove
	@OneToMany(mappedBy="aluno", cascade=CascadeType.ALL)		//cascade se alterar no aluno ele altera na tabela matricula, o mesmo pra excluir
	private List<Matricula> matriculas;							//mappedBy indica o lado fraco do relacionamento, fica bidirecional

	@Override
	public String toString() {
		return getNome();
	}
		    
	public List<Matricula> getMatriculas() {
		return matriculas;
	}
	public void setMatriculas(List<Matricula> matriculas) {
		this.matriculas = matriculas;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Date getNascimento() {
		return nascimento;
	}
	public void setNascimento(Date nascimento) {
		this.nascimento = nascimento;
	}
	public String getSexo() {
		return sexo;
	}
	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Contato getContato() {
		return contato;
	}
	
	public void setContato(Contato contato) {
		this.contato = contato;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Aluno other = (Aluno) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
//	public Double getNota1() {
//		return nota1;
//	}
//	public void setNota1(Double nota1) {
//		this.nota1 = nota1;
//	}
//	public Double getNota2() {
//		return nota2;
//	}
//	public void setNota2(Double nota2) {
//		this.nota2 = nota2;
//	}
//	public Double getNota3() {
//		return nota3;
//	}
//	public void setNota3(Double nota3) {
//		this.nota3 = nota3;
//	}
//	
//	public Double getMedia() {
//		return media;
//	}
//	
//	public void setMedia() {
//		double total = (getNota1() + getNota2() + getNota3())/3;
//		BigDecimal bd = new BigDecimal(total).setScale(1, RoundingMode.HALF_EVEN);
//		this.media = bd.doubleValue();
//	}
//
//	public String getAprovacao() {
//		return aprovacao;
//	}
//
//	public void setAprovacao() {
//		if(getMedia()>=7)
//			this.aprovacao = "Aprovado";
//		else
//			this.aprovacao = "Reprovado";
//	}

}
