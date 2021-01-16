/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 8/13: ArtigoRevista (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


/**
 *      As publicacoes artigo de revista sao caracterizadas pelo titulo, palavras chave, resumo, ano da publicacao,
 * dimensao da audiencia, e pelo nome, data e numero da revista.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Publicacao
 */
public class ArtigoRevista extends Publicacao implements Serializable {
    private final String nomeRevista;
    private final GregorianCalendar dataRevista;
    private final int numeroRevista;

    /**
     *      Construtor
     */
    public ArtigoRevista(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo, String nomeRevista, GregorianCalendar dataRevista, int numeroRevista) {
        super(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo);
        this.nomeRevista = nomeRevista;
        this.dataRevista = dataRevista;
        this.numeroRevista = numeroRevista;
    }


    protected char setFatorImpactoByAudiencia(int dimensaoAudiencia) {
        if (dimensaoAudiencia >= 1000) {
            return 'A';
        } else {
            if (dimensaoAudiencia < 500) {
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
                shift + "\t" + "NOME REVISTA: " + this.nomeRevista + "\n" +
                shift + "\t" + "DATA REVISTA: " + String.format("%d/%d/%d", this.dataRevista.get(Calendar.DAY_OF_MONTH), this.dataRevista.get(Calendar.MONTH) + 1, this.dataRevista.get(Calendar.YEAR)) + "\n" +
                shift + "\t" + "NUMERO REVISTA: " + this.numeroRevista + "\n";
    }
}
