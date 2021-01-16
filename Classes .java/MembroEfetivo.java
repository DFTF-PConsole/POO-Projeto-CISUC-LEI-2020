/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 3/13: MembroEfetivo (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */

package dftf.poao;


import java.io.Serializable;


/**
 *      Os membros efetivos correspondem a investigadores de carreira que sao caracterizados pelo seu nome, email,
 * grupo de investigacao a que pertencem, numero de gabinete e numero de telefone do DEI.
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Investigador
 * @see dftf.poao.GrupoInvestigacao
 */
public class MembroEfetivo extends Investigador implements Serializable {
    private final int numeroGabinete;
    private final long numeroTelefoneDei;


    /**
     *      Construtor
     */
    public MembroEfetivo(String nome, String email, GrupoInvestigacao grupoInvestigacao, int numeroGabinete, long numeroTelefoneDei) {
        super(nome, email, grupoInvestigacao);
        this.numeroGabinete = numeroGabinete;
        this.numeroTelefoneDei = numeroTelefoneDei;
    }


    /**
     * Permite imprimir nome personalizado para publicacao
     * @return String
     */
    public String getNomePublicacao() {
        String[] partes = super.getNome().split(" ");
        if (partes.length > 1)
            return "Professor " + partes[0] + " " + partes[partes.length -1];
        else
            return "Professor " + super.getNome();
    }

    /**
     * Permite obter o investigador orientador (nas classes que não possui, retorna null)
     * @return null
     */
    public Investigador getInvestigadorOrientador() {
        return null;
    }

    /**
     * Permite imprimir estruturadamente os dados do objeto.
     * @return String
     */
    public String imprimir() {
        return String.format("%s -> EMAIL: %s, N. GABINETE: %d, N. TELEFONE: %d", this.nome, this.email, this.numeroGabinete, this.numeroTelefoneDei);

    }
}
