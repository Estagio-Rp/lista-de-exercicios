package objetosCollections.exercicioUm;

import java.util.ArrayList;

public class Turma {

    ArrayList<Aluno> alunos = new ArrayList<>();

    public void adicionarAluno(Aluno aluno){
        alunos.add(aluno);
    }

    public void listarAlunos(){
        System.out.println("--- LISTA DE ALUNOS DA TURMA ---");

        for (Aluno aluno : alunos){
            System.out.println("Nome: " + aluno.nome + " | idade: " + aluno.idade + "anos");
        }
    }
}
