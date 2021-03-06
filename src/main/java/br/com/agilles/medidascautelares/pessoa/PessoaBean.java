package br.com.agilles.medidascautelares.pessoa;

import br.com.agilles.medidascautelares.endereco.Endereco;
import br.com.agilles.medidascautelares.endereco.EnderecoBean;
import br.com.agilles.medidascautelares.endereco.WebServiceEndereco;
import org.primefaces.context.RequestContext;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;

/**
 * Created by jille on 04/11/2016.
 */
@Named
@RequestScoped
public class PessoaBean implements Serializable {

    @Inject
    private Pessoa pessoa;
    @Inject
    private PessoaDao dao;
    @Inject
    private Endereco endereco;
    private List<Pessoa> todasPessoas = new ArrayList<>();
    private Pessoa pessoaSelecionada = new Pessoa();


    /**
     * Getter and Setters
     **/
    public Pessoa getPessoaSelecionada() {
        return pessoaSelecionada;
    }

    public void setPessoaSelecionada(Pessoa pessoaSelecionada) {
        this.pessoaSelecionada = pessoaSelecionada;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Pessoa> getTodasPessoas() {
        return todasPessoas;
    }

    @PostConstruct
    public void listarTodasPessoas() {
        this.todasPessoas = dao.listarTodasPessoas();
    }


    public String atualizarPessoa() {
        pessoaSelecionada.setEndereco(endereco);
        if (dao.atualizarPessoa(pessoaSelecionada)) {
            RequestContext contexto = RequestContext.getCurrentInstance();
            contexto.execute("swal('Sucesso!', 'Dados alterados com sucesso!', 'success')");
        } else {
            RequestContext contexto = RequestContext.getCurrentInstance();
            contexto.execute("swal('Erro!', 'Algo aconteceu errado, tente novamente ou entre em contato com o Administrador do sistema!', 'error')");
        }
        return "consultaPessoas";
    }

    public String goEditarPessoa() {
        return "edicaoPessoas";
    }

    public void gravarPessoa() {
        pessoa.setEndereco(endereco);
        if (dao.gravarPessoa(pessoa)) {

            RequestContext contexto = RequestContext.getCurrentInstance();
            contexto.execute("swal('Sucesso!', 'Nova pessoa inserida no sistema!', 'success')");
            this.pessoa = new Pessoa();
            this.endereco = new Endereco();

        } else {
            RequestContext contexto = RequestContext.getCurrentInstance();
            contexto.execute("swal('Erro!', 'Algo aconteceu errado, tente novamente ou entre em contato com o Administrador do sistema!', 'error')");
        }
        pessoa = new Pessoa();
        endereco = new Endereco();
    }

    public Endereco preencherCep() {
        String cep = endereco.getCep().replace("-", "");
        this.endereco = WebServiceEndereco.getEnderecoPorCep(cep);
        return this.endereco;

    }

    public List<Pessoa> completarPessoas(String query){
        List<Pessoa> pessoas = new ArrayList<>();
        pessoas = dao.listarTodasPessoas();
        List<Pessoa> sugestoes = new ArrayList<>();
        for(Pessoa p : pessoas){
            String nome = p.getNome();
            if (nome.contains(query)) {
                sugestoes.add(p);
            }
        }
        return sugestoes;
    }

}
