/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 10/13: CapituloLivro (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;


import java.io.Serializable;
import java.util.ArrayList;


/**
 *      Os capitulos de livros sao caracterizados pelo titulo, palavras chave, resumo, ano da publicacao,
 * dimensao da audiencia, pela editora e ISBN do livro, e pelo nome do capitulo, pagina de inicio e
 * pagina de fim do capitulo.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Livro
 * @see dftf.poao.Publicacao
 */
public class CapituloLivro extends Livro implements Serializable {
    private final String nomeCapitulo;
    private final int paginaInicioCapitulo;
    private final int paginaFimCapitulo;


    /**
     *      Construtor
     */
    public CapituloLivro(ArrayList<Investigador> listaAutores, String titulo, ArrayList<String> listaPalavrasChave, int anoPublicacao, int dimensaoAudiencia, String resumo, String editoraLivro, long isbnLivro, String nomeCapitulo, int paginaInicioCapitulo, int paginaFimCapitulo) {
        super(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, editoraLivro, isbnLivro);
        this.nomeCapitulo = nomeCapitulo;
        this.paginaInicioCapitulo = paginaInicioCapitulo;
        this.paginaFimCapitulo = paginaFimCapitulo;
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
        return super.imprimir(shift) +
                shift + "\t" + "NOME CAPITULO: " + this.nomeCapitulo + "\n" +
                shift + "\t" + "PAGINA INICIO CAPITULO: " + this.paginaInicioCapitulo + "\n" +
                shift + "\t" + "PAGINA FIM CAPITULO: " + this.paginaFimCapitulo + "\n";
    }
}
