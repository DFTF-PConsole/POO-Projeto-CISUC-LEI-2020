/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 11/13: LivroArtigosConferencia (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;


import java.io.Serializable;
import java.util.ArrayList;


/**
 *      O livro de artigos de conferencia e um livro publicado por algumas conferencia com os principais artigos
 * submetidos na conferencia, e e caracterizado pelo titulo, palavras chave, resumo, ano da publicacao,
 * dimensao da audiencia, pela editora e ISBN do livro, pelo nome da conferencia e numero de artigos.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Livro
 * @see dftf.poao.Publicacao
 */
public class LivroArtigosConferencia extends Livro implements Serializable {
    private final String nomeConferencia;
    private final int numeroArtigos;


    /**
     *      Construtor
     */
    public LivroArtigosConferencia(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo, String editoraLivro, long isbnLivro, String nomeConferencia, int numeroArtigos) {
        super(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, editoraLivro, isbnLivro);
        this.nomeConferencia = nomeConferencia;
        this.numeroArtigos = numeroArtigos;
    }


    @Override
    protected char setFatorImpactoByAudiencia(int dimensaoAudiencia) {
        if (dimensaoAudiencia >= 7500) {
            return 'A';
        } else {
            if (dimensaoAudiencia < 2500) {
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
        return super.imprimir(shift) +
                shift + "\t" + "NOME CONFERENCIA: " + this.nomeConferencia + "\n" +
                shift + "\t" + "NUMERO ARTIGOS: " + this.numeroArtigos + "\n";
    }
}
