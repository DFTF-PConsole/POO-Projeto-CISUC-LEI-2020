/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 2/13: Investigador (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;


import java.io.Serializable;

/**
 *      Investigador e uma classe abstracta.
 *      Os investigadores sao caracterizados pelo seu nome, email e grupo de investigacao a que pertencem.
 *      Os investigadores podem ser de duas categorias: membro efetivo ou estudante.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Investigador
 * @see dftf.poao.GrupoInvestigacao
 */
public abstract class Investigador implements Serializable {
    protected String nome;
    protected String email;
    protected GrupoInvestigacao grupoInvestigacao;


    /**
     *      Construtor
     */
    public Investigador(String nome, String email, GrupoInvestigacao grupoInvestigacao) {
        this.nome = nome;
        this.email = email;
        this.grupoInvestigacao = grupoInvestigacao;
    }


    public String getNome() {
        return this.nome;
    }


    public GrupoInvestigacao getGrupoInvestigacao() {
        return grupoInvestigacao;
    }


    /**
     * Permite imprimir nome personalizado para publicacao
     * @return String
     */
    public abstract String getNomePublicacao();


    public void setGrupoInvestigacao(GrupoInvestigacao grupoInvestigacao) {
        this.grupoInvestigacao = grupoInvestigacao;
    }

    /**
     * Permite obter o investigador orientador (nas classes que não possui, retorna null)
     * @return Investigador (ou null)
     */
    public abstract Investigador getInvestigadorOrientador();

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @return String
     */
    public abstract String imprimir();
}
