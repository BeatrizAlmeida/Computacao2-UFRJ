package lab4;

import java.util.HashMap;

public class Turma {
	
	Disciplina disciplina;
	Periodo periodo;
	Professor professor;
	HashMap<Long, Aluno> alunos = new HashMap<>();
	HashMap<Long, Float> notas = new HashMap<>();
	public Turma(Disciplina disciplina, Periodo periodo, Professor professor) {
		this.disciplina = disciplina;
		this.periodo = periodo;
		this.professor = professor;
	}
	
	public void inscreverAluno(Aluno aluno) {
		this.alunos.put(aluno.getDre(), aluno);
	}
	
	public void atribuirMediaFinal(long dre, float nota) {
		Aluno aluno = alunos.get(dre);
		aluno.inserirItemHistorico(disciplina, nota, periodo);
		this.notas.put(dre, nota);
	}
	
	public float obterMediaFinal(long dre) {
		return notas.get(dre);
	}
	
	public String listarAlunos() {
		String resultado = "Listagem de alunos:\n";
		for (Aluno aluno: this.alunos.values()) {
			resultado+= aluno.getNome();
			resultado+= "  DRE:";
			resultado+= aluno.getDre();
			resultado+= "\n";
		}
		return resultado;
	}
	
	//Para testar
	public static void main(String[] args) {
		Disciplina disciplina = new Disciplina("Disciplina", 5, "1111");
		Periodo periodo = new Periodo(2020, 2);
		Professor professor = new Professor("Professor", 2009);
		Turma turma = new Turma(disciplina, periodo, professor);
		Aluno aluno1 = new Aluno(1200, "Aluno de tal");
		Aluno aluno2 = new Aluno(1202, "Pessoa Pereira");
		turma.inscreverAluno(aluno1);
		turma.inscreverAluno(aluno2);
		System.out.println(turma.listarAlunos());
	}
}
