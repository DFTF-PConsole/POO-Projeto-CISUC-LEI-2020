/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 6/13: Publicacao (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *      Publicacao e uma classe abstracta.
 *      As publicacoes sao caracterizadas pelos autores, titulo, palavras chave, ano da publicacao,
 * tipo de publicacao (artigo de conferencias, artigo de revista e livro) e pela dimensao da audiencia.
 *      A dimensao da audiencia corresponde ao numero de pessoas que assistiram ou adquiriram a publicacao.
 * Por exemplo, um artigo de conferencia e apresentado para uma plateia de 300 pessoas (audiencia = 300) e
 * sao vendidas 2400 unidades de um livro (audiencia = 2400).
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Livro
 * @see dftf.poao.ArtigoRevista
 * @see dftf.poao.ArtigoConferencia
 * @see dftf.poao.Investigador
 */
public abstract class Publicacao implements Serializable {
    protected ArrayList<Investigador> listaAutores;
    protected String titulo;
    protected ArrayList<String> listaPalavrasChave;
    protected int anoPublicacao;
    protected int dimensaoAudiencia;
    protected String resumo;
    protected char fatorImpacto;

    /**
     *      Construtor de Publicacao
     *
     * @param listaAutores ArrayList Investigador
     * @param titulo String
     * @param listaPalavrasChave ArrayList String
     * @param anoPublicacao int
     * @param dimensaoAudiencia int
     * @param resumo String
     *
     */
    public Publicacao(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo) {
        this.listaAutores = listaAutores;
        this.titulo = titulo;
        this.listaPalavrasChave = listaPalavrasChave;
        this.anoPublicacao = anoPublicacao;
        this.dimensaoAudiencia = dimensaoAudiencia;
        this.resumo = resumo;
        this.fatorImpacto = this.setFatorImpactoByAudiencia(dimensaoAudiencia);
    }


    protected abstract char setFatorImpactoByAudiencia(int dimensaoAudiencia);


    public int getAnoPublicacao() {
        return anoPublicacao;
    }

    public ArrayList<Investigador> getListaAutores() {
        return this.listaAutores;
    }

    public char getFatorImpacto() {
        return fatorImpacto;
    }

    public void setListaAutores(ArrayList<Investigador> listaAutores) {
        this.listaAutores = listaAutores;
    }

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @param shift - espacamento inicial (\t)
     * @return String
     */
    public abstract String imprimir(String shift);

    /**
     * Permite imprimir estruturadamente os dados dos autores.
     * @return String
     */
    public String getListaAutoresStrFormatado(){
        StringBuilder strListaAutores = new StringBuilder();
        for (Investigador inv : this.listaAutores) {
            strListaAutores.append(inv.getNomePublicacao()).append(", ");
        }
        return strListaAutores.substring(0, strListaAutores.length() -2);
    }

    /**
     * Permite imprimir estruturadamente os dados das palavras chave.
     * @return String
     */
    public String getListaPalavraChaveStrFormatado(){
        StringBuilder strPalavraChave = new StringBuilder();
        for (String str : this.listaPalavrasChave) {
            strPalavraChave.append(str).append(", ");
        }
        return strPalavraChave.substring(0, strPalavraChave.length() -2);
    }

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @param shift - espacamento inicial (\t)
     * @return String
     */
    public String imprimirPrincipal(String shift) {
        return shift + "TITULO: " + this.titulo + "\n" +
                shift + "\t" + "AUTORES: " + this.getListaAutoresStrFormatado() + "\n" +
                shift + "\t" + "PALAVRAS CHAVE: " + this.getListaPalavraChaveStrFormatado() + "\n" +
                shift + "\t" + "ANO PUBLICAÇÃO: " + this.anoPublicacao + "\n" +
                shift + "\t" + "DIMENSÃO AUDIÊNCIA: " + this.dimensaoAudiencia + "\n" +
                shift + "\t" + "FATOR IMPACTO: " + this.fatorImpacto + "\n" +
                shift + "\t" + "RESUMO: " + this.resumo + "\n";
    }
}
