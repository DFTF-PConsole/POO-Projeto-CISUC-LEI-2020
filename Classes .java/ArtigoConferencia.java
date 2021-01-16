/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 9/13: ArtigoConferencia (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *      As publicacoes Artigo Conferencia sao caracterizados pelo titulo, palavras chave, resumo, ano da publicacao,
 * dimensao da audiencia, e pelo nome, data e localizacao da conferencia.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Publicacao
 */
public class ArtigoConferencia extends Publicacao implements Serializable {
    private final String nomeConferencia;
    private final GregorianCalendar dataConferencia;
    private final String localizacaoConferencia;

    /**
     *      Construtor
     */
    public ArtigoConferencia(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo, String nomeConferencia, GregorianCalendar dataConferencia, String localizacaoConferencia) {
        super(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo);
        this.nomeConferencia = nomeConferencia;
        this.dataConferencia = dataConferencia;
        this.localizacaoConferencia = localizacaoConferencia;
    }


    protected char setFatorImpactoByAudiencia(int dimensaoAudiencia) {
        if (dimensaoAudiencia >= 500) {
            return 'A';
        } else {
            if (dimensaoAudiencia < 200) {
                return 'C';
            } else {
                return 'B';
            }
        }
    }

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @param shift - espacamento inicial (\t)
     * @return String
     */
    public String imprimir(String shift) {
        return this.imprimirPrincipal(shift) +
                shift + "\t" + "NOME CONFERENCIA: " + this.nomeConferencia + "\n" +
                shift + "\t" + "DATA CONFERENCIA: " + String.format("%d/%d/%d", this.dataConferencia.get(Calendar.DAY_OF_MONTH), this.dataConferencia.get(Calendar.MONTH) + 1, this.dataConferencia.get(Calendar.YEAR)) + "\n" +
                shift + "\t" + "LOCALIZAÇÃO CONFERENCIA: " + this.localizacaoConferencia + "\n";
    }
}
