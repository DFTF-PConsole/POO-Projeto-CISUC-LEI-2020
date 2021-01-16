/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 13/13: InterfaceConsola - Interface pra Manipular a Classe Cicuc (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package dftf.poao;

import java.util.*;

/**
 *      InterfaceConsola e uma classe que contem metodos que permitem exibir os dados que estao no Cisuc na consola,
 * chamando metodos do Cisuc que manipulam, calculam ou filtram esses dados.
 *
 *
 * @author Dario Felix - POAO FCTUC 2020/21
 * @version 1.0
 * @see dftf.poao.Cisuc
 * @see dftf.poao.GrupoInvestigacao
 * @see dftf.poao.Investigador
 * @see dftf.poao.Publicacao
 */
public class InterfaceConsola {
    private final boolean ERRO = false;
    private final boolean SUCESSO = true;

    private final Cisuc cisuc;

    /**
     *      Construtor
     * @param cisuc que esteja previamente inicializado.
     */
    public InterfaceConsola(Cisuc cisuc) {
        this.cisuc = cisuc;
    }


    /**
     *      Executa o menu principal para interpretar comandos
     * @return boolean (ERRO ou SUCESSO)
     */
    public boolean start() {
        int[] ptrTempOpcaoInt = new int[1];
        Scanner sc = new Scanner(System.in);
        String tempOpcaoStr;
        Investigador tempInvestigador;
        GrupoInvestigacao tempGrupoInvestigacao;

        do {
            this.printMenuPrincipal();

            if(this.inputOpcaoInt(ptrTempOpcaoInt, sc) == ERRO)
                return ERRO;

            switch (ptrTempOpcaoInt[0]) {
                case 1:
                    opcao1();
                    break;

                case 2:
                    this.printMenuListarGrupoInvestigacao();
                    tempOpcaoStr = sc.nextLine();
                    if ( (tempGrupoInvestigacao = this.cisuc.getGrupoInvestigacaoFromLista(tempOpcaoStr)) == null) {
                        System.out.printf("### ERRO ###  Opção invalida: o acronimo de Grupo de Investigação \"%s\" não existe.\n", tempOpcaoStr);
                        System.out.print("Tente novamente!\n");
                        break;
                    }
                    this.opcao2(tempGrupoInvestigacao);
                    break;

                case 3:
                    this.printMenuListarGrupoInvestigacao();
                    tempOpcaoStr = sc.nextLine();
                    if ( (tempGrupoInvestigacao = this.cisuc.getGrupoInvestigacaoFromLista(tempOpcaoStr)) == null) {
                        System.out.printf("### ERRO ###  Opção invalida: o acronimo de Grupo de Investigação \"%s\" não existe.\n", tempOpcaoStr);
                        System.out.print("Tente novamente!\n");
                        break;
                    }
                    this.opcao3(tempGrupoInvestigacao);
                    break;

                case 4:
                    this.printMenuListarInvestigador();
                    tempOpcaoStr = sc.nextLine();
                    if ( (tempInvestigador = this.cisuc.getInvestigadorFromLista(tempOpcaoStr)) == null) {
                        System.out.printf("### ERRO ###  Opção invalida: o nome de Investigador \"%s\" não existe.\n", tempOpcaoStr);
                        System.out.print("Tente novamente!\n");
                        break;
                    }
                    this.opcao4(tempInvestigador);
                    break;

                case 5:
                    opcao5();
                    break;

                case 6:
                    System.out.print("\nSAIU DO CISUC!\n");
                    break;

                default:
                    System.out.printf("### ERRO ###  Opção invalida: introduza um numero entre 1 e 6 (atual = %d).\n", ptrTempOpcaoInt[0]);
                    System.out.print("Tente novamente!\n");
                    break;
            }

        } while (ptrTempOpcaoInt[0] != 6);

        return SUCESSO;
    }


    private void printMenuPrincipal() {
        System.out.print("\nMENU PRINCIPAL: Escolha uma das seguintes opções (numero associado):\n");
        System.out.print(" 1 - Apresentar os indicadores gerais do CISUC (várias informações gerais)\n");
        System.out.print(" 2 - Listar as publicações de um grupo de investigação, dos últimos 5 anos, organizadas por ano, por tipo de publicação e por fator de impacto\n");
        System.out.print(" 3 - Listar os membros de um grupo de investigação agrupados por categoria\n");
        System.out.print(" 4 - Listar as publicações de um investigador agrupadas por ano, tipo de publicação e fator de impacto\n");
        System.out.print(" 5 - Listar todos os grupos de investigação, e apresentar várias informações para cada grupo\n");
        System.out.print(" 6 - SAIR\n");
        System.out.print("> ");
    }


    private void printMenuListarGrupoInvestigacao() {
        System.out.print("\nMENU: Escolha um dos seguintes Grupos de Investigação para realizar a operação (introduza o acronimo):\n");
        for (GrupoInvestigacao grupoInvestigacao : this.cisuc.getListaGrupoInvestigacao()) {
            System.out.printf(" - %s\t(%s)\n", grupoInvestigacao.getAcronimo(), grupoInvestigacao.getNome());
        }
        System.out.print("> ");
    }


    private void printMenuListarInvestigador() {
        System.out.print("\nMENU: Escolha um dos seguintes Investigadores para realizar a operação (introduza o nome):\n");
        for (Investigador investigador : this.cisuc.getListaAllInOneInvestigador()) {
            System.out.printf(" - %s\n", investigador.getNome());
        }
        System.out.print("> ");
    }


    private void opcao1 () {
        // Apresentar os indicadores gerais do CISUC (várias informações gerais)

        System.out.print("\nINDICADORES GERAIS DO CISUC:\n");
        System.out.printf(" Total de membros: %d\n", this.cisuc.getListaEstudante().size() + this.cisuc.getListaMembroEfetivo().size());
        System.out.printf(" Número de membros de cada categoria:\n \t%d Membros Efetivos\n \t%d Estudantes\n", this.cisuc.getListaMembroEfetivo().size(), this.cisuc.getListaEstudante().size());
        System.out.printf(" Total de publicações dos últimos 5 anos: %d\n", this.cisuc.filtrarListaPublicacaoUltimos5Anos(this.cisuc.getListaAllInOnePublicacao()).size());
        System.out.printf(" Número de publicações de cada tipo:\n \t%d Artigos de Conferencias\n \t%d Livros\n \t%d Capitulos de Livros\n \t%d Livros de Artigos de Conferencias\n \t%d Artigos de Revistas\n", this.cisuc.getListaArtigoConferencia().size(), this.cisuc.getListaLivro().size(), this.cisuc.getListaCapituloLivro().size(), this.cisuc.getListaLivroArtigosConferencia().size(), this.cisuc.getListaArtigoRevista().size());
        System.out.print("\n*** *** *** *** *** *** *** *** *** ***\n\n");
    }


    private void opcao2 (GrupoInvestigacao grupoInvestigacao) {
        // Listar as publicações de um grupo de investigação, dos últimos 5 anos, organizadas por ano, por tipo de publicação e por fator de impacto
        ArrayList<Publicacao> listaPublicacao = this.cisuc.calcListaPublicacaoDeGrupoInvestigacao(grupoInvestigacao);
        listaPublicacao = this.cisuc.filtrarListaPublicacaoUltimos5Anos(listaPublicacao);

        System.out.printf("\nLISTAGEM DAS PUBLICAÇÕES DO GRUPO DE INVESTIGAÇÃO \"%s - %s\" (DOS ÚLTIMOS 5 ANOS):\n", grupoInvestigacao.getAcronimo(), grupoInvestigacao.getNome());

        rotinaListarPublicacoes(listaPublicacao);

        System.out.print("\n*** *** *** *** *** *** *** *** *** ***\n\n");
    }


    private void opcao3 (GrupoInvestigacao grupoInvestigacao) {
        // Listar os membros de um grupo de investigação agrupados por categoria
        ArrayList<Estudante> tempListaEstudante = this.cisuc.filtrarListaInvestigadorByEstudante(grupoInvestigacao.getListaMembros());
        ArrayList<MembroEfetivo> tempListaMembroEfetivo = this.cisuc.filtrarListaInvestigadorByMembroEfetivo(grupoInvestigacao.getListaMembros());
        MembroEfetivo responsavel = grupoInvestigacao.getInvestigadorResponsavel();
        boolean fator;

        System.out.printf("\nLISTAGEM DOS MEMBROS DO GRUPO DE INVESTIGAÇÃO \"%s - %s\":\n", grupoInvestigacao.getAcronimo(), grupoInvestigacao.getNome());
        System.out.print(" Investigador Responsavel (Membro Efetivo):\n");
        System.out.printf(" \t%s\n\n", responsavel.imprimir());

        System.out.print(" Membros Efetivos:\n");
        fator = false;
        for (MembroEfetivo me : tempListaMembroEfetivo) {
            System.out.printf(" \t%s\n", me.imprimir());
            fator = true;
        }
        if (!fator)
            System.out.print(" \t(sem elementos)\n");

        System.out.print("\n Estudantes:\n");
        fator = false;
        for (Estudante est : tempListaEstudante) {
            System.out.printf(" \t%s\n", est.imprimir());
            fator = true;
        }
        if (!fator)
            System.out.print(" \t(sem elementos)\n");

        System.out.print("\n\n*** *** *** *** *** *** *** *** *** ***\n\n");
    }


    private void opcao4 (Investigador investigador) {
        // Listar as publicações de um investigador agrupadas por ano, tipo de publicação e fator de impacto
        ArrayList<Publicacao> listaPublicacao = this.cisuc.calcListaPublicacaoDeInvestigador(investigador);

        System.out.printf("\nLISTAGEM DAS PUBLICAÇÕES DO INVESTIGADOR \"%s\":\n", investigador.getNome());

        rotinaListarPublicacoes(listaPublicacao);

        System.out.print("\n*** *** *** *** *** *** *** *** *** ***\n\n");
    }


    private void opcao5 () {
        // Listar todos os grupos de investigação, e apresentar várias informações para cada grupo

        ArrayList<Publicacao> tempListaPublicacao;
        ArrayList<Investigador> tempListaInvestigador;
        GregorianCalendar hoje = new GregorianCalendar();

        System.out.print("\nLISTAGEM DOS GRUPOS DE INVESTIGAÇÃO:\n");

        for (GrupoInvestigacao grupo : this.cisuc.getListaGrupoInvestigacao()) {
            tempListaPublicacao = this.cisuc.filtrarListaPublicacaoUltimos5Anos(this.cisuc.calcListaPublicacaoDeGrupoInvestigacao(grupo));
            tempListaInvestigador = grupo.getListaTodosMembros();

            System.out.printf(" %s - %s:\n", grupo.getAcronimo(), grupo.getNome());
            System.out.printf(" \tTotal de membros: %d\n", grupo.getListaTodosMembros().size());
            System.out.printf(" \tNúmero de membros de cada categoria:\n \t\t%d Membros Efetivos\n \t\t%d Estudantes\n", this.cisuc.filtrarListaInvestigadorByMembroEfetivo(tempListaInvestigador).size(), this.cisuc.filtrarListaInvestigadorByEstudante(tempListaInvestigador).size());
            System.out.printf(" \tTotal de publicações dos últimos 5 anos: %d\n", tempListaPublicacao.size());
            System.out.print(" \tNúmero de publicações por ano:\n");
            for (int i=0 ; i <5; i++) {
                System.out.printf(" \t\t%d no ano %d\n", this.cisuc.filtrarListaPublicacaoByAno(tempListaPublicacao, hoje.get(Calendar.YEAR) - i).size(), hoje.get(Calendar.YEAR) - i);
            }
            System.out.printf(" \tNúmero de publicações de cada tipo:\n \t\t%d Artigos de Conferencias\n \t\t%d Livros\n \t\t%d Capitulos de Livros\n \t\t%d Livros de Artigos de Conferencias\n \t\t%d Artigos de Revistas\n", this.cisuc.filtrarListaPublicacaoByArtigoConferencia(tempListaPublicacao).size(), this.cisuc.filtrarListaPublicacaoByLivro(tempListaPublicacao).size(), this.cisuc.filtrarListaPublicacaoByCapituloLivro(tempListaPublicacao).size(), this.cisuc.filtrarListaPublicacaoByLivroArtigosConferencia(tempListaPublicacao).size(), this.cisuc.filtrarListaPublicacaoByArtigoRevista(tempListaPublicacao).size());
            System.out.printf(" \tNúmero de publicações por fator:\n \t\t%d com fator 'A'\n \t\t%d com fator 'B'\n \t\t%d com fator 'C'\n\n", this.cisuc.filtrarListaPublicacaoByFator(tempListaPublicacao, 'A').size(), this.cisuc.filtrarListaPublicacaoByFator(tempListaPublicacao, 'B').size(), this.cisuc.filtrarListaPublicacaoByFator(tempListaPublicacao, 'C').size());
        }

        System.out.print("\n*** *** *** *** *** *** *** *** *** ***\n\n");
    }


    private void rotinaListarPublicacoes (ArrayList<Publicacao> listaPublicacao) {
        ArrayList<Integer> listaAnos = this.calcAnosEmListaPublicacao(listaPublicacao);
        ArrayList<Character> listaChar = new ArrayList<>();
        listaChar.add('A');
        listaChar.add('B');
        listaChar.add('C');
        boolean fator;

        System.out.print("\n AGRUPADO POR ANO:\n");
        fator = false;
        for (int ano : listaAnos) {
            System.out.printf(" \tNo ano %d:\n", ano);
            for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByAno(listaPublicacao, ano)) {
                System.out.printf("%s\n", pub.imprimir(" \t\t"));
            }
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print("\n\n AGRUPADO POR TIPO DE PUBLICAÇÃO:\n");
        System.out.print(" \tArtigo Conferencia:\n");
        fator = false;
        for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByArtigoConferencia(listaPublicacao)) {
            System.out.printf("%s\n", pub.imprimir(" \t\t"));
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print(" \tArtigo Revista:\n");
        fator = false;
        for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByArtigoRevista(listaPublicacao)) {
            System.out.printf("%s\n", pub.imprimir(" \t\t"));
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print(" \tLivro:\n");
        fator = false;
        for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByLivro(listaPublicacao)) {
            System.out.printf("%s\n", pub.imprimir(" \t\t"));
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print(" \tCapitulo Livro:\n");
        fator = false;
        for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByCapituloLivro(listaPublicacao)) {
            System.out.printf("%s\n", pub.imprimir(" \t\t"));
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print(" \tLivro Artigos Conferencia:\n");
        fator = false;
        for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByLivroArtigosConferencia(listaPublicacao)) {
            System.out.printf("%s\n", pub.imprimir(" \t\t"));
            fator = true;
        }
        if (!fator)
            System.out.print(" \t\t(sem elementos)\n\n");

        System.out.print("\n\n AGRUPADO POR FATOR DE IMPACTO:\n");
        for (char ch : listaChar) {
            System.out.printf(" \tFator '%c':\n", ch);
            fator = false;
            for (Publicacao pub : this.cisuc.filtrarListaPublicacaoByFator(listaPublicacao, ch)) {
                System.out.printf("%s\n", pub.imprimir(" \t\t"));
                fator = true;
            }
            if (!fator)
                System.out.print(" \t\t(sem elementos)\n\n");
        }
    }


    private boolean inputOpcaoInt (int[] ptrTempOpcaoInt, Scanner sc) {
        try {
            ptrTempOpcaoInt[0] = sc.nextInt();
        } catch (InputMismatchException e) {
            System.out.print("### ERRO ###  InputMismatchException: opção é do tipo inteiro.\n");
            ptrTempOpcaoInt[0] = 0;
        } catch (NoSuchElementException | IllegalStateException e) {
            System.out.print("\n### ERRO ###  start(): Opção: Outro, NoSuchElementException ou IllegalStateException.\n\n");
            return ERRO;
        }
        sc.nextLine();
        return SUCESSO;
    }


    private ArrayList<Integer> calcAnosEmListaPublicacao(ArrayList<Publicacao> listaPublicacao) {
        ArrayList<Integer> lista = new ArrayList<>();

        for (Publicacao pub : listaPublicacao) {
            if (!lista.contains(pub.getAnoPublicacao()))
                lista.add(pub.getAnoPublicacao());
        }

        Collections.sort(lista);

        return lista;
    }

}
