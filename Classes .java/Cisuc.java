/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 1/13: Cisuc - Main (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.util.*;


/**
 *      Cisuc e uma classe que possui todos os dados de Investigadores, Grupos de Investigacao e Publicacoes.
 *      Contem metodos que permitem manipular esses dados (para uma interface de consola, por exemplo).
 *
 * @author      Dario Felix - POAO FCTUC 2020/21
 * @version     3.0
 * @see         dftf.poao.Investigador
 * @see         dftf.poao.Estudante
 * @see         dftf.poao.MembroEfetivo
 * @see         dftf.poao.GrupoInvestigacao
 * @see         dftf.poao.Publicacao
 * @see         dftf.poao.ArtigoRevista
 * @see         dftf.poao.ArtigoConferencia
 * @see         dftf.poao.LivroArtigosConferencia
 * @see         dftf.poao.CapituloLivro
 * @see         dftf.poao.Livro
 */
public class Cisuc {
    private static final boolean ERRO = false;

    private ArrayList<Estudante> listaEstudante;
    private ArrayList<MembroEfetivo> listaMembroEfetivo;
    private ArrayList<ArtigoConferencia> listaArtigoConferencia;
    private ArrayList<ArtigoRevista> listaArtigoRevista;
    private ArrayList<CapituloLivro> listaCapituloLivro;
    private ArrayList<Livro> listaLivro;
    private ArrayList<LivroArtigosConferencia> listaLivroArtigosConferencia;
    private ArrayList<GrupoInvestigacao> listaGrupoInvestigacao;


    /**
     *      Main - Execucao do programa.
     */
    public static void main(String[] args) {
        System.out.print("\n*********************************************************************\n");
        System.out.print("* POAO - LEI FCTUC 2020/21 | Dario Filipe Torres Felix N.2018275530 *\n");
        System.out.print("* Projeto Final - Gestor de publicações do CISUC                    *\n");
        System.out.print("*********************************************************************\n\n");

        Cisuc cisuc = new Cisuc();
        Ficheiro fich = new Ficheiro(cisuc);
        InterfaceConsola consola = new InterfaceConsola(cisuc);

        if (fich.inicializar() == ERRO)
            return;

        consola.start();
    }

    /**
     *      Construtor
     */
    public Cisuc() {
        this.listaEstudante = new ArrayList<> ();
        this.listaMembroEfetivo = new ArrayList<> ();
        this.listaGrupoInvestigacao = new ArrayList<> ();
        this.listaArtigoConferencia = new ArrayList<> ();
        this.listaArtigoRevista = new ArrayList<> ();
        this.listaCapituloLivro = new ArrayList<> ();
        this.listaLivro = new ArrayList<> ();
        this.listaLivroArtigosConferencia = new ArrayList<> ();
    }


    /**
     *      get Lista com todos os Investigadores
     */
    public ArrayList<Investigador>  getListaAllInOneInvestigador() {
        ArrayList<Investigador> listaInvestigador = new ArrayList<>();
        listaInvestigador.addAll(this.listaEstudante);
        listaInvestigador.addAll(this.listaMembroEfetivo);
        return listaInvestigador;
    }

    /**
     *      get Lista com todas as Publicacoes
     */
    public ArrayList<Publicacao> getListaAllInOnePublicacao() {
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        listaPublicacao.addAll(this.listaArtigoConferencia);
        listaPublicacao.addAll(this.listaArtigoRevista);
        listaPublicacao.addAll(this.listaCapituloLivro);
        listaPublicacao.addAll(this.listaLivro);
        listaPublicacao.addAll(this.listaLivroArtigosConferencia);
        return listaPublicacao;
    }


    /**
     *      Calcula as publicacoes de um grupo de investigacao
     */
    public ArrayList<Publicacao> calcListaPublicacaoDeGrupoInvestigacao (GrupoInvestigacao grupoInvestigacao) {
        //publicações de um grupo de investigação
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        for (Investigador inv : grupoInvestigacao.getListaTodosMembros()) {
            for (Publicacao pub : this.calcListaPublicacaoDeInvestigador(inv)) {
                if (!listaPublicacao.contains(pub))
                    listaPublicacao.add(pub);
            }
        }
        return listaPublicacao;
    }


    /**
     *      calcula lista de publicacoes de um investigador
     */
    public ArrayList<Publicacao> calcListaPublicacaoDeInvestigador (Investigador investigador) {
        //publicações de um grupo de investigação
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>(this.getListaAllInOnePublicacao());
        listaPublicacao.removeIf(pub -> !pub.getListaAutores().contains(investigador));
        return listaPublicacao;
    }


    /**
     *      filtra lista de publicacoes para os ultimos 5 anos
     */
    public ArrayList<Publicacao> filtrarListaPublicacaoUltimos5Anos (ArrayList<Publicacao> publicacoesBruto) {
        //publicações dos últimos 5 anos
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        GregorianCalendar hoje = new GregorianCalendar();
        for (Publicacao pub : publicacoesBruto) {
            if (hoje.get(Calendar.YEAR) - pub.getAnoPublicacao() <= 5)
                listaPublicacao.add(pub);
        }
        return listaPublicacao;
    }

    /**
     *      filtra lista de publicacoes por ano
     */
    public ArrayList<Publicacao> filtrarListaPublicacaoByAno (ArrayList<Publicacao> publicacoesBruto, int ano) {
        //publicações do ano x
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        if (ano < 1)
            return listaPublicacao;
        for (Publicacao pub : publicacoesBruto) {
            if (pub.getAnoPublicacao() == ano)
                listaPublicacao.add(pub);
        }
        return listaPublicacao;
    }


    /**
     *      filtra lista de publicacoes por fator
     */
    public ArrayList<Publicacao> filtrarListaPublicacaoByFator (ArrayList<Publicacao> publicacoesBruto, char fator) {
        //publicações com fator x
        ArrayList<Publicacao> listaPublicacao = new ArrayList<>();
        if (fator > 'C' || fator < 'A')
            return listaPublicacao;
        for (Publicacao pub : publicacoesBruto) {
            if (pub.getFatorImpacto() == fator)
                listaPublicacao.add(pub);
        }
        return listaPublicacao;
    }


    /**
     *      filtra lista de investigadores ppor tipo (MembroEfetivo)
     */
    public ArrayList<MembroEfetivo> filtrarListaInvestigadorByMembroEfetivo (ArrayList<Investigador> investigadores) {
        ArrayList<MembroEfetivo> outListaMembroEfetivo = new ArrayList<>();
        ArrayList<Investigador> castLista = new ArrayList<>(this.listaMembroEfetivo);
        for (Investigador inv : investigadores) {
            if (castLista.contains(inv))
                outListaMembroEfetivo.add((MembroEfetivo) inv);
        }
        return outListaMembroEfetivo;
    }

    /**
     *      filtra lista de investigadores ppor tipo (Estudante)
     */
    public ArrayList<Estudante> filtrarListaInvestigadorByEstudante (ArrayList<Investigador> investigadores) {
        ArrayList<Estudante> outListaEstudante = new ArrayList<>();
        ArrayList<Investigador> castLista = new ArrayList<>(this.listaEstudante);
        for (Investigador inv : investigadores) {
            if (castLista.contains(inv))
                outListaEstudante.add((Estudante) inv);
        }
        return outListaEstudante;
    }

    /**
     *      filtra lista de publicacoes ppor tipo (ArtigoConferencia)
     */
    public ArrayList<ArtigoConferencia> filtrarListaPublicacaoByArtigoConferencia (ArrayList<Publicacao> publicacoes) {
        ArrayList<ArtigoConferencia> outListaArtigoConferencia = new ArrayList<>();
        ArrayList<Publicacao> castLista = new ArrayList<>(this.listaArtigoConferencia);
        for (Publicacao pub : publicacoes) {
            if (castLista.contains(pub))
                outListaArtigoConferencia.add((ArtigoConferencia) pub);
        }
        return outListaArtigoConferencia;
    }


    /**
     *      filtra lista de publicacoes ppor tipo (ArtigoRevista)
     */
    public ArrayList<ArtigoRevista> filtrarListaPublicacaoByArtigoRevista (ArrayList<Publicacao> publicacoes) {
        ArrayList<ArtigoRevista> outListaArtigoRevista = new ArrayList<>();
        ArrayList<Publicacao> castLista = new ArrayList<>(this.listaArtigoRevista);
        for (Publicacao pub : publicacoes) {
            if (castLista.contains(pub))
                outListaArtigoRevista.add((ArtigoRevista) pub);
        }
        return outListaArtigoRevista;
    }


    /**
     *      filtra lista de publicacoes ppor tipo (CapituloLivro)
     */
    public ArrayList<CapituloLivro> filtrarListaPublicacaoByCapituloLivro (ArrayList<Publicacao> publicacoes) {
        ArrayList<CapituloLivro> outListaCapituloLivro = new ArrayList<>();
        ArrayList<Publicacao> castLista = new ArrayList<>(this.listaCapituloLivro);
        for (Publicacao pub : publicacoes) {
            if (castLista.contains(pub))
                outListaCapituloLivro.add((CapituloLivro) pub);
        }
        return outListaCapituloLivro;
    }

    /**
     *      filtra lista de publicacoes ppor tipo (Livro)
     */
    public ArrayList<Livro> filtrarListaPublicacaoByLivro (ArrayList<Publicacao> publicacoes) {
        ArrayList<Livro> outListaLivro = new ArrayList<>();
        ArrayList<Publicacao> castLista = new ArrayList<>(this.listaLivro);
        for (Publicacao pub : publicacoes) {
            if (castLista.contains(pub))
                outListaLivro.add((Livro) pub);
        }
        return outListaLivro;
    }

    /**
     *      filtra lista de publicacoes ppor tipo (LivroArtigosConferencia)
     */
    public ArrayList<LivroArtigosConferencia> filtrarListaPublicacaoByLivroArtigosConferencia (ArrayList<Publicacao> publicacoes) {
        ArrayList<LivroArtigosConferencia> outListaLivroArtigosConferencia = new ArrayList<>();
        ArrayList<Publicacao> castLista = new ArrayList<>(this.listaLivroArtigosConferencia);
        for (Publicacao pub: publicacoes) {
            if (castLista.contains(pub))
                outListaLivroArtigosConferencia.add((LivroArtigosConferencia) pub);
        }
        return outListaLivroArtigosConferencia;
    }

    /**
     *      get Investigador da lista no cisuc por nome
     */
    public Investigador getInvestigadorFromLista(String nome) {
        for(Investigador investigador : this.getListaAllInOneInvestigador()) {
            if(investigador.getNome().equals(nome)) {
                return investigador;
            }
        }
        return null;
    }

    /**
     *      get Grupo de Investigacao da lista no cisuc por acronimo
     */
    public GrupoInvestigacao getGrupoInvestigacaoFromLista(String acronimo) {
        for(GrupoInvestigacao grupoInvestigacao : this.listaGrupoInvestigacao) {
            if(grupoInvestigacao.getAcronimo().equals(acronimo)) {
                return grupoInvestigacao;
            }
        }
        return null;
    }


    public ArrayList<Estudante> getListaEstudante() {
        return this.listaEstudante;
    }

    public ArrayList<MembroEfetivo> getListaMembroEfetivo() {
        return this.listaMembroEfetivo;
    }

    public ArrayList<ArtigoConferencia> getListaArtigoConferencia() {
        return this.listaArtigoConferencia;
    }

    public ArrayList<ArtigoRevista> getListaArtigoRevista() {
        return this.listaArtigoRevista;
    }

    public ArrayList<CapituloLivro> getListaCapituloLivro() {
        return this.listaCapituloLivro;
    }

    public ArrayList<Livro> getListaLivro() {
        return this.listaLivro;
    }

    public ArrayList<LivroArtigosConferencia> getListaLivroArtigosConferencia() {
        return this.listaLivroArtigosConferencia;
    }

    public ArrayList<GrupoInvestigacao> getListaGrupoInvestigacao() {
        return this.listaGrupoInvestigacao;
    }

    public void setListaEstudante(ArrayList<Estudante> listaEstudante) {
        this.listaEstudante = listaEstudante;
    }

    public void setListaMembroEfetivo(ArrayList<MembroEfetivo> listaMembroEfetivo) {
        this.listaMembroEfetivo = listaMembroEfetivo;
    }

    public void setListaArtigoConferencia(ArrayList<ArtigoConferencia> listaArtigoConferencia) {
        this.listaArtigoConferencia = listaArtigoConferencia;
    }

    public void setListaArtigoRevista(ArrayList<ArtigoRevista> listaArtigoRevista) {
        this.listaArtigoRevista = listaArtigoRevista;
    }

    public void setListaCapituloLivro(ArrayList<CapituloLivro> listaCapituloLivro) {
        this.listaCapituloLivro = listaCapituloLivro;
    }

    public void setListaLivro(ArrayList<Livro> listaLivro) {
        this.listaLivro = listaLivro;
    }

    public void setListaLivroArtigosConferencia(ArrayList<LivroArtigosConferencia> listaLivroArtigosConferencia) {
        this.listaLivroArtigosConferencia = listaLivroArtigosConferencia;
    }

    public void setListaGrupoInvestigacao(ArrayList<GrupoInvestigacao> listaGrupoInvestigacao) {
        this.listaGrupoInvestigacao = listaGrupoInvestigacao;
    }
}


