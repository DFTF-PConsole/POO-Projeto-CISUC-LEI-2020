/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 7/13: Livro (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;


import java.io.Serializable;
import java.util.ArrayList;


/**
 *      As publicacoes livro sao caracterizadas pelo titulo, palavras chave, resumo, ano da publicacao,
 * dimensao da audiencia, e pela editora e ISBN do livro. Algumas publicacoes livro podem ser capitulos de livro
 * ou livro de artigos de conferencia.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Publicacao
 * @see dftf.poao.CapituloLivro
 * @see dftf.poao.LivroArtigosConferencia
 */
public class Livro extends Publicacao implements Serializable {
    protected String editoraLivro;
    protected long isbnLivro;


    /**
     *      Construtor
     */
    public Livro(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo, String editoraLivro, long isbnLivro) {
        super(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo);
        this.editoraLivro = editoraLivro;
        this.isbnLivro = isbnLivro;
    }


    @Override
    protected char setFatorImpactoByAudiencia(int dimensaoAudiencia) {
        if (dimensaoAudiencia >= 10000) {
            return 'A';
        } else {
            if (dimensaoAudiencia < 5000) {
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
                shift + "\t" + "EDITORA LIVRO: " + this.editoraLivro + "\n" +
                shift + "\t" + "ISBN LIVRO: " + this.isbnLivro + "\n";
    }
}
