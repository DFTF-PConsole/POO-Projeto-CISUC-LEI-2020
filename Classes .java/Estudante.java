/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 4/13: Estudante (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.io.Serializable;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *      Os estudantes sao alunos de Doutoramento do DEI que temporariamente pertencem a um grupo de investigacao,
 * e sao caracterizados pelo seu nome, email, grupo de investigacao a que pertencem, titulo da tese,
 * data prevista de conclusao e pelo investigador orientador (tem de pertencer ao mesmo grupo de investigacao).
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Investigador
 * @see dftf.poao.GrupoInvestigacao
 */
public class Estudante extends Investigador implements Serializable {
    private final String tituloTese;
    private final GregorianCalendar dataPrevistaConclusao;
    private Investigador investigadorOrientador;


    /**
     *      Construtor
     */
    public Estudante(String nome, String email, GrupoInvestigacao grupoInvestigacao, String tituloTese, GregorianCalendar dataPrevistaConclusao, Investigador investigadorOrientador) {
        super(nome, email, grupoInvestigacao);
        this.tituloTese = tituloTese;
        this.dataPrevistaConclusao = dataPrevistaConclusao;
        this.investigadorOrientador = investigadorOrientador;
    }


    /**
     * Permite imprimir nome personalizado para publicacao
     * @return String
     */
    public String getNomePublicacao() {
        String[] partes = super.getNome().split(" ");
        if (partes.length > 1)
            return partes[0].charAt(0) + ". " + partes[partes.length -1];
        else
            return super.getNome();
    }

    /**
     * Permite obter o investigador orientador (nas classes que não possui, retorna null)
     * @return Investigador
     */
    public Investigador getInvestigadorOrientador() {
        return investigadorOrientador;
    }

    public void setInvestigadorOrientador(Investigador investigadorOrientador) {
        this.investigadorOrientador = investigadorOrientador;
    }

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @return String
     */
    public String imprimir() {
        return String.format("%s -> EMAIL: %s, TITULO DA TESE: \"%s\", DATA PREVISTA DE CONCLUSÃO: %d/%d/%d, INVESTIGADOR ORIENTADOR: %s", this.nome, this.email,this.tituloTese, this.dataPrevistaConclusao.get(Calendar.DAY_OF_MONTH), this.dataPrevistaConclusao.get(Calendar.MONTH) +1, this.dataPrevistaConclusao.get(Calendar.YEAR), this.investigadorOrientador.getNome());

    }
}
