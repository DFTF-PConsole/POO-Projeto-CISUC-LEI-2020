/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 5/13: GrupoInvestigacao (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.io.Serializable;
import java.util.ArrayList;


/**
 *      Cada grupo de investigacao e caracterizado pelo seu nome, acronimo, pelo investigador responsavel
 * (membro efetivo) e por uma lista de membros do grupo (investigadores).
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.MembroEfetivo
 * @see dftf.poao.Investigador
 */
public class GrupoInvestigacao implements Serializable {
    private final String nome;
    private final String acronimo;
    private MembroEfetivo investigadorResponsavel;
    private ArrayList<Investigador> listaMembros;


    /**
     *      Construtor
     */
    public GrupoInvestigacao(String nome, String acronimo, MembroEfetivo investigadorResponsavel, ArrayList<Investigador> listaMembros) {
        this.nome = nome;
        this.acronimo = acronimo;
        this.investigadorResponsavel = investigadorResponsavel;
        this.listaMembros = listaMembros;
    }


    public String getAcronimo() {
        return this.acronimo;
    }

    public String getNome() {
        return this.nome;
    }

    /**
     * Permite retornar uma lista com os membros (incluindo investigador responsavel)
     * @return ArrayList Investigador membros
     */
    public ArrayList<Investigador> getListaTodosMembros() {
        ArrayList<Investigador> membros = new ArrayList<>(this.listaMembros);
        membros.add(this.investigadorResponsavel);
        return membros;
    }

    public ArrayList<Investigador> getListaMembros() {
        return listaMembros;
    }

    public MembroEfetivo getInvestigadorResponsavel() {
        return investigadorResponsavel;
    }

    public void setInvestigadorResponsavel(MembroEfetivo investigadorResponsavel) {
        this.investigadorResponsavel = investigadorResponsavel;
    }

    public void setListaMembros(ArrayList<Investigador> listaMembros) {
        this.listaMembros = listaMembros;
    }
}
