/* * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * *
 * REALIZADO POR: Dário Félix - 2018275530 | Projeto Final: "Gestor de Publicações de CISUC" - POAO 2020/2021
 * FICHEIRO - PARTE 12/13: Ficheiro - Manipula Ficheiros (Ficheiro Java - Classe)
 * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * * */


package dftf.poao;


import java.io.*;
import java.util.*;


/**
 *      Ficheiro e uma classe que permite manipular ficheiros (.txt e .obj), lendo os
 * dados neles contidos para a classe Cisuc e vice-versa
 *
 * @author      Dario Felix - POAO FCTUC 2020/21
 * @version     2.0
 * @see         dftf.poao.Cisuc
 * @see         dftf.poao.GrupoInvestigacao
 * @see         dftf.poao.Investigador
 * @see         dftf.poao.Publicacao
 */
public class Ficheiro {
    private final boolean ERRO = false;
    private final boolean SUCESSO = true;

    private final String nomeFichObjEstudante = "FichObjEst.obj";
    private final String nomeFichObjMembroEfetivo = "FichObjMemEfe.obj";
    private final String nomeFichObjGrupoInvestigacao = "FichObjGruInv.obj";
    private final String nomeFichObjArtigoConferencia= "FichObjArtConf.obj";
    private final String nomeFichObjArtigoRevista = "FichObjArtRev.obj";
    private final String nomeFichObjCapituloLivro = "FichObjCapLiv.obj";
    private final String nomeFichObjLivro = "FichObjLiv.obj";
    private final String nomeFichObjLivroArtigosConferencia = "FichObjLivArtCon.obj";

    private final Cisuc cisuc;

    /**
     *      Construtor
     * @param cisuc para inicializar
     */
    public Ficheiro(Cisuc cisuc) {
        this.cisuc = cisuc;
    }

    /**
     *      Inicializa o cicuc com base nos ficheiros
     * @return boolean SUCESSO ou ERRO
     */
    public boolean inicializar() {
        if (this.temFicheirosObj()) {
            if (this.lerFicheirosObj() == ERRO)
                return ERRO;
            this.uniformizar();
            return SUCESSO;
        } else {
            return this.lerFicheirosTxt();
        }
    }


    private void uniformizar() {
        for (Investigador inv : this.cisuc.getListaAllInOneInvestigador()) {
            inv.setGrupoInvestigacao(this.cisuc.getGrupoInvestigacaoFromLista(inv.getGrupoInvestigacao().getAcronimo()));
            if (inv.getInvestigadorOrientador() != null)
                ((Estudante) inv).setInvestigadorOrientador(this.cisuc.getInvestigadorFromLista(inv.getInvestigadorOrientador().getNome()));
        }

        for (GrupoInvestigacao gi : this.cisuc.getListaGrupoInvestigacao()) {
            gi.setInvestigadorResponsavel((MembroEfetivo) this.cisuc.getInvestigadorFromLista(gi.getInvestigadorResponsavel().getNome()));
            ArrayList<Investigador> nova = new ArrayList<>();
            for (Investigador inv : gi.getListaMembros()) {
                nova.add(this.cisuc.getInvestigadorFromLista(inv.getNome()));
            }
            gi.setListaMembros(nova);
        }

        for (Publicacao pub : this.cisuc.getListaAllInOnePublicacao()) {
            ArrayList<Investigador> nova = new ArrayList<>();
            for (Investigador inv : pub.getListaAutores()) {
                nova.add(this.cisuc.getInvestigadorFromLista(inv.getNome()));
            }
            pub.setListaAutores(nova);
        }
    }


    private boolean lerFicheirosTxt() {
        File fileGrupoInvestigacao, fileInvestigador, filePublicacao;
        FileReader fileReaderGrupoInvestigacao, fileReaderInvestigador, fileReaderPublicacao;
        BufferedReader brGrupoInvestigacao, brInvestigador, brPublicacao;
        String tempLinha;
        StringBuilder tempMultiLinhas;
        boolean fator_blank;
        ArrayList<String[]> tempDadosMembroEfetivo = new ArrayList<> ();
        ArrayList<String[]> tempDadosEstudante = new ArrayList<> ();

        // Abrir Ficheiros
        try {
            fileInvestigador = new File("investigador.txt");
            if(!fileInvestigador.exists() || !fileInvestigador.isFile()) {
                System.out.print("\n### ERRO ###  Ficheiro \"investigador.txt\" não existe.\n\n");
                return ERRO;
            }

            fileReaderInvestigador = new FileReader(fileInvestigador);
            brInvestigador = new BufferedReader(fileReaderInvestigador);
        } catch (FileNotFoundException e) {
            System.out.print("\n### ERRO ###  Ficheiro \"investigador.txt\" inexistente.\n\n");
            return ERRO;
        }

        try {
            fileGrupoInvestigacao = new File("grupoInvestigacao.txt");
            if(!fileGrupoInvestigacao.exists() || !fileGrupoInvestigacao.isFile()) {
                System.out.print("\n### ERRO ###  Ficheiro \"grupoInvestigacao.txt\" não existe.\n\n");
                return ERRO;
            }

            fileReaderGrupoInvestigacao = new FileReader(fileGrupoInvestigacao);
            brGrupoInvestigacao = new BufferedReader(fileReaderGrupoInvestigacao);
        } catch (FileNotFoundException e) {
            System.out.print("\n### ERRO ###  Ficheiro \"grupoInvestigacao.txt\" inexistente.\n\n");
            return ERRO;
        }

        try {
            filePublicacao = new File("publicacao.txt");
            if(!filePublicacao.exists() || !filePublicacao.isFile()) {
                System.out.print("\n### ERRO ###  Ficheiro \"publicacao.txt\" não existe.\n\n");
                return ERRO;
            }

            fileReaderPublicacao = new FileReader(filePublicacao);
            brPublicacao = new BufferedReader(fileReaderPublicacao);
        } catch (FileNotFoundException e) {
            System.out.print("\n### ERRO ###  Ficheiro \"publicacao.txt\" inexistente.\n\n");
            return ERRO;
        }

        // Parsing de Blocos (Objetos/Entidade)
        try {
            while ((tempLinha = brInvestigador.readLine()) != null) {
                if (this.parsingInvestigador(tempLinha, tempDadosMembroEfetivo, tempDadosEstudante) == ERRO)
                    return ERRO;
            }
            brInvestigador.close();
        } catch (IOException e) {
            System.out.print("\n### ERRO ###  IOException: \"investigador.txt\".\n\n");
            return ERRO;
        }

        try {
            while ((tempLinha = brGrupoInvestigacao.readLine()) != null) {
                if (this.parsingGrupoInvestigacao(tempLinha) == ERRO)
                    return ERRO;
            }
            brGrupoInvestigacao.close();
        } catch (IOException e) {
            System.out.print("\n### ERRO ###  IOException: \"grupoInvestigacao.txt\".\n\n");
            return ERRO;
        }

        if (this.updateDadosInvestigador(tempDadosMembroEfetivo, tempDadosEstudante) == ERRO)
            return ERRO;

        try {
            fator_blank = false;
            tempMultiLinhas = new StringBuilder();

            while ((tempLinha = brPublicacao.readLine()) != null) {
                if (tempLinha.isBlank()) {
                    if (!fator_blank) {
                        if (this.parsingPublicacao(tempMultiLinhas.toString()) == ERRO)
                            return ERRO;
                        tempMultiLinhas = new StringBuilder();
                        fator_blank = true;
                    }
                } else {
                    fator_blank = false;
                    if (!tempMultiLinhas.toString().isBlank())
                        tempMultiLinhas.append("\n");
                    tempMultiLinhas.append(tempLinha);
                }
            }
            if (!tempMultiLinhas.toString().isBlank())
                if (this.parsingPublicacao(tempMultiLinhas.toString()) == ERRO)
                    return ERRO;

            brPublicacao.close();
        } catch (IOException e) {
            System.out.print("\n### ERRO ###  IOException: \"publicacao.txt\".\n\n");
            return ERRO;
        }

        return this.escreverFicheirosObj();
    }


    private boolean updateDadosInvestigador(ArrayList<String[]> tempDadosMembroEfetivo, ArrayList<String[]> tempDadosEstudante) {
        String[] tempDados;
        Investigador tempInvestigador;
        GrupoInvestigacao tempGrupoInvestigacao;

        int i=0;
        for (MembroEfetivo membroEfetivo : this.cisuc.getListaMembroEfetivo()) {
            tempDados = tempDadosMembroEfetivo.get(i);
            tempGrupoInvestigacao = this.cisuc.getGrupoInvestigacaoFromLista(tempDados[0]);
            if (tempGrupoInvestigacao == null) {
                System.out.printf("\n### ERRO ###  \"investigador.txt\": GrupoInvestigacao \"%s\" não existe.\n\n", tempDados[0]);
                return ERRO;
            } else {
                membroEfetivo.setGrupoInvestigacao(tempGrupoInvestigacao);
            }
            i++;
        }

        i=0;
        for (Estudante estudante : this.cisuc.getListaEstudante()) {
            tempDados = tempDadosEstudante.get(i);
            tempGrupoInvestigacao = this.cisuc.getGrupoInvestigacaoFromLista(tempDados[0]);
            if (tempGrupoInvestigacao == null) {
                System.out.printf("\n### ERRO ###  \"investigador.txt\": GrupoInvestigacao \"%s\" não existe.\n\n", tempDados[0]);
                return ERRO;
            } else {
                estudante.setGrupoInvestigacao(tempGrupoInvestigacao);
            }
            i++;
        }

        i=0;
        for (Estudante estudante : this.cisuc.getListaEstudante()) {
            tempDados = tempDadosEstudante.get(i);
            tempInvestigador = this.cisuc.getInvestigadorFromLista(tempDados[1]);
            if (tempInvestigador == null) {
                System.out.printf("\n### ERRO ###  \"investigador.txt\": Investigador \"%s\" não existe.\n\n", tempDados[1]);
                return ERRO;
            } else {
                if (!estudante.getGrupoInvestigacao().equals(tempInvestigador.getGrupoInvestigacao())) {
                    System.out.printf("\n### ERRO ###  \"investigador.txt\": Investigador Orientador \"%s\" não pertence ao Grupo de Investigação \"%s\".\n\n", tempDados[1], estudante.getGrupoInvestigacao().getAcronimo());
                    return ERRO;
                } else {
                    estudante.setInvestigadorOrientador(tempInvestigador);
                }
            }
            i++;
        }

        return SUCESSO;
    }


    private boolean parsingInvestigador(String input, ArrayList<String[]> tempDadosMembroEfetivo, ArrayList<String[]> tempDadosEstudante) {
        final int indiceTipo = 4-1;
        final int nArgsEstudante = 7;
        final int nArgsMembroEfetivo = 6;
        final String strEstudante = "Estudante";
        final String strMembroEfetivo = "MembroEfetivo";

        if (input.isBlank()) {
            System.out.print("\n### ERRO ###  \"investigador.txt\": input em branco.\n\n");
            return ERRO;
        }

        ArrayList<String> listaInputs = new ArrayList<>(Arrays.asList(input.split("\t")));
        listaInputs.removeIf(String::isBlank);   // operacao lambda: str -> (str.isBlank()) >>> substituido por metodo de referencia

        if (listaInputs.size() != nArgsEstudante && listaInputs.size() != nArgsMembroEfetivo) {
            System.out.printf("\n### ERRO ###  \"investigador.txt\": numero de atributos errados, esperava %d ou %d mas tem %d.\n\n", nArgsEstudante, nArgsMembroEfetivo, listaInputs.size());
            return ERRO;
        }

        String nome = listaInputs.get(0);
        String email = listaInputs.get(1);
        String acronimoGrupoInvestigacao = listaInputs.get(2);

        if (this.cisuc.getInvestigadorFromLista(nome) != null) {
            System.out.printf("\n### ERRO ###  \"investigador.txt\": \"%s\" já existe (duplicado).\n\n", nome);
            return ERRO;
        }

        if (listaInputs.get(indiceTipo).equals(strMembroEfetivo) && listaInputs.size() == nArgsMembroEfetivo) {
            int numeroGabinete;
            long numeroTelefoneDei;

            try {
                numeroGabinete = Integer.parseInt(listaInputs.get(4));
                numeroTelefoneDei = Long.parseLong(listaInputs.get(5));
            } catch (NumberFormatException e) {
                System.out.print("\n### ERRO ###  \"investigador.txt\": NumberFormatException: em numeroGabinete (Int) ou numeroTelefoneDei (Long).\n\n");
                return ERRO;
            }

            String[] str = new String[1];
            str[0] = acronimoGrupoInvestigacao;
            tempDadosMembroEfetivo.add(str);

            this.cisuc.getListaMembroEfetivo().add(new MembroEfetivo(nome, email, null, numeroGabinete, numeroTelefoneDei));
        } else {
            if (listaInputs.get(indiceTipo).equals(strEstudante) && listaInputs.size() == nArgsEstudante) {
                String tituloTese;
                GregorianCalendar dataPrevistaConclusao;
                String nomeInvestigadorOrientador;

                try {
                    tituloTese = listaInputs.get(4);
                    Scanner sc = new Scanner(listaInputs.get(5));
                    int dia = sc.nextInt();
                    int mes = sc.nextInt();
                    int ano = sc.nextInt();
                    if (mes > 12 || mes < 1 || dia > 31 || dia < 1 || ano < 1) {
                        System.out.print("\n### ERRO ###  \"investigador.txt\": Data: dia, mes ou ano invalidos.\n\n");
                        return ERRO;
                    }

                    dataPrevistaConclusao = new GregorianCalendar(ano, mes -1, dia);    // listaInputs.get(5);
                    nomeInvestigadorOrientador = listaInputs.get(6);

                } catch (InputMismatchException e) {
                    System.out.print("\n### ERRO ###  \"investigador.txt\": InputMismatchException: Data: dia, mes ou ano (Int).\n\n");
                    return ERRO;
                } catch (NoSuchElementException | IllegalStateException e) {
                    System.out.print("\n### ERRO ###  \"investigador.txt\": Outro, NoSuchElementException ou IllegalStateException.\n\n");
                    return ERRO;
                }

                String[] str = new String[2];
                str[0] = acronimoGrupoInvestigacao;
                str[1] = nomeInvestigadorOrientador;
                tempDadosEstudante.add(str);

                this.cisuc.getListaEstudante().add(new Estudante(nome, email, null, tituloTese, dataPrevistaConclusao, null));
            } else {
                System.out.printf("\n### ERRO ###  \"investigador.txt\": \"%s\" (NomeTipo) e %d (NumeroAtributos) não correspondem a nenhum tipo de Investigador.\n\n", listaInputs.get(indiceTipo), listaInputs.size());
                return ERRO;
            }
        }
        return SUCESSO;
    }


    private boolean parsingGrupoInvestigacao(String input) {
        final int nArgsMin = 3;

        if (input.isBlank()) {
            System.out.print("\n### ERRO ###  \"grupoInvestigacao.txt\": input em branco.\n\n");
            return ERRO;
        }

        ArrayList<String> listaInputs = new ArrayList<>(Arrays.asList(input.split("\t")));
        listaInputs.removeIf(String::isBlank);   // operacao lambda: str -> (str.isBlank()) >>> substituido por metodo de referencia

        if (listaInputs.size() < nArgsMin) {
            System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": numero de atributos errados, esperava no minimo %d mas tem %d.\n\n", nArgsMin, listaInputs.size());
            return ERRO;
        }

        String nomeGrupo = listaInputs.get(0);
        String acronimo = listaInputs.get(1);
        String tempNomeInvestigador = listaInputs.get(2);
        MembroEfetivo investigadorResponsavel;
        Investigador tempInvestigador;

        if (this.cisuc.getGrupoInvestigacaoFromLista(acronimo) != null) {
            System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": \"%s\" já existe (duplicado).\n\n", acronimo);
            return ERRO;
        }

        try {
            investigadorResponsavel = (MembroEfetivo) this.cisuc.getInvestigadorFromLista(tempNomeInvestigador);
        } catch (ClassCastException e) {
            System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": ClassCastException: \"%s\" não é MembroEfetivo.\n\n", tempNomeInvestigador);
            return ERRO;
        }

        if (investigadorResponsavel == null) {
            System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": \"%s\" não existe.\n\n", tempNomeInvestigador);
            return ERRO;
        }

        int i;
        ArrayList<Investigador> listaMembros = new ArrayList<>();

        for (i=3; i<listaInputs.size(); i++) {
            tempInvestigador = this.cisuc.getInvestigadorFromLista(listaInputs.get(i));
            if (tempInvestigador == null) {
                System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": \"%s\" não existe.\n\n", listaInputs.get(i));
                return ERRO;
            } else {
                if (listaMembros.contains(tempInvestigador)) {
                    System.out.printf("\n### ERRO ###  \"grupoInvestigacao.txt\": \"%s\" está repetido.\n\n", listaInputs.get(i));
                    return ERRO;
                } else {
                    listaMembros.add(tempInvestigador);
                }
            }
        }

        this.cisuc.getListaGrupoInvestigacao().add(new GrupoInvestigacao(nomeGrupo,acronimo,investigadorResponsavel,listaMembros));
        return SUCESSO;
    }


    private boolean parsingPublicacao(String input) {
        final int indiceTipo = 7-1;
        final int nArgsMin = 7;
        final int nArgsArtigoConferencia_ArtigoRevista = nArgsMin + 3;
        final int nArgsCapituloLivro = nArgsMin + 5;
        final int nArgsLivro = nArgsMin + 2;
        final int nArgsLivroArtigosConferencia = nArgsMin + 4;
        final String strArtigoConferencia = "ArtigoConferencia";
        final String strArtigoRevista = "ArtigoRevista";
        final String strCapituloLivro = "CapituloLivro";
        final String strLivro = "Livro";
        final String strLivroArtigosConferencia = "LivroArtigosConferencia";

        if (input.isBlank()) {
            System.out.print("\n### ERRO ###  \"publicacao.txt\": input em branco.\n\n");
            return ERRO;
        }

        ArrayList<String> listaInputs = new ArrayList<>(Arrays.asList(input.split("\n")));
        listaInputs.removeIf(String::isBlank);   // operacao lambda: str -> (str.isBlank()) >>> substituido por metodo de referencia
        if (listaInputs.size() < nArgsMin) {
            System.out.printf("\n### ERRO ###  \"publicacao.txt\": numero de atributos errados, esperava no minimo %d mas tem %d.\n\n", nArgsMin, listaInputs.size());
            return ERRO;
        }

        ArrayList<Investigador> listaAutores = new ArrayList<> ();
        Investigador tempInvestigador;
        ArrayList<String> tempListaNomeInvestigador = new ArrayList<>(Arrays.asList(listaInputs.get(0).split("\t")));
        tempListaNomeInvestigador.removeIf(String::isBlank);
        if (tempListaNomeInvestigador.isEmpty()) {
            System.out.print("\n### ERRO ###  \"publicacao.txt\": publicação sem autores.\n\n");
            return ERRO;
        }
        for(String str : tempListaNomeInvestigador) {
            tempInvestigador = this.cisuc.getInvestigadorFromLista(str);
            if (tempInvestigador == null) {
                System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" não existe.\n\n", str);
                return ERRO;
            } else {
                if (listaAutores.contains(tempInvestigador)) {
                    System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" já adicionado (duplicado).\n\n", str);
                    return ERRO;
                } else {
                    listaAutores.add(tempInvestigador);
                }
            }
        }

        String titulo = listaInputs.get(1);

        ArrayList<String> listaPalavrasChave = new ArrayList<>(Arrays.asList(listaInputs.get(2).split("\t")));
        listaPalavrasChave.removeIf(String::isBlank);
        if (tempListaNomeInvestigador.isEmpty()) {
            System.out.print("\n### ERRO ###  \"publicacao.txt\": publicação sem palavras-chave.\n\n");
            return ERRO;
        }

        int anoPublicacao, dimensaoAudiencia;
        try {
            anoPublicacao = Integer.parseInt(listaInputs.get(3));
            dimensaoAudiencia = Integer.parseInt(listaInputs.get(4));
        } catch (NumberFormatException e) {
            System.out.print("\n### ERRO ###  \"publicacao.txt\": NumberFormatException: em anoPublicacao (Int) ou dimensaoAudiencia (Int).\n\n");
            return ERRO;
        }

        String resumo = listaInputs.get(5);

        String tempTipo = listaInputs.get(indiceTipo);
        switch (listaInputs.size()) {
            case nArgsLivro:
                if(!tempTipo.equals(strLivro)) {
                    System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" (NomeTipo) e %d (NumeroAtributos) não correspondem a nenhum tipo de Publicacao.\n\n", tempTipo, nArgsLivro);
                    return ERRO;
                }

                String editoraLivro = listaInputs.get(7);

                long isbnLivro;
                try {
                    isbnLivro = Long.parseLong(listaInputs.get(8));
                } catch (NumberFormatException e) {
                    System.out.print("\n### ERRO ###  \"publicacao.txt\": NumberFormatException: em isbnLivro (Long).\n\n");
                    return ERRO;
                }

                this.cisuc.getListaLivro().add(new Livro(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, editoraLivro, isbnLivro));
                break;

            case nArgsArtigoConferencia_ArtigoRevista:
                if(tempTipo.equals(strArtigoConferencia)) {

                    String nomeConferencia = listaInputs.get(7);

                    GregorianCalendar dataConferencia;
                    try {
                        Scanner sc = new Scanner(listaInputs.get(8));
                        int dia = sc.nextInt();
                        int mes = sc.nextInt();
                        int ano = sc.nextInt();

                        if (mes > 12 || mes < 1 || dia > 31 || dia < 1 || ano < 1) {
                            System.out.print("\n### ERRO ###  \"publicacao.txt\": Data: dia, mes ou ano invalidos.\n\n");
                            return ERRO;
                        }

                        dataConferencia = new GregorianCalendar(ano, mes -1, dia);
                    }catch (InputMismatchException e) {
                        System.out.print("\n### ERRO ###  \"publicacao.txt\": InputMismatchException: Data: dia, mes ou ano (Int).\n\n");
                        return ERRO;
                    } catch (NoSuchElementException | IllegalStateException e) {
                        System.out.print("\n### ERRO ###  \"publicacao.txt\": Outro, NoSuchElementException ou IllegalStateException.\n\n");
                        return ERRO;
                    }

                    String localizacaoConferencia = listaInputs.get(9);

                    this.cisuc.getListaArtigoConferencia().add(new ArtigoConferencia(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, nomeConferencia, dataConferencia, localizacaoConferencia));

                } else {
                    if (tempTipo.equals(strArtigoRevista)) {

                        String nomeRevista = listaInputs.get(7);

                        GregorianCalendar dataRevista;
                        int numeroRevista;
                        try {
                            Scanner sc = new Scanner(listaInputs.get(8));
                            int dia = sc.nextInt();
                            int mes = sc.nextInt();
                            int ano = sc.nextInt();

                            if (mes > 12 || mes < 1 || dia > 31 || dia < 1 || ano < 1) {
                                System.out.print("\n### ERRO ###  \"publicacao.txt\": Data: dia, mes ou ano invalidos. \n\n");
                                return ERRO;
                            }

                            dataRevista = new GregorianCalendar(ano, mes -1, dia);

                            numeroRevista = Integer.parseInt(listaInputs.get(9));
                        } catch (InputMismatchException e) {
                            System.out.print("\n### ERRO ###  \"publicacao.txt\": InputMismatchException: Data: dia, mes ou ano (Int).\n\n");
                            return ERRO;
                        } catch (NumberFormatException e) {
                            System.out.print("\n### ERRO ###  \"publicacao.txt\": NumberFormatException: em numeroRevista (Int).\n\n");
                            return ERRO;
                        } catch (NoSuchElementException | IllegalStateException e) {
                            System.out.print("\n### ERRO ###  \"publicacao.txt\": Outro, NoSuchElementException ou IllegalStateException.\n\n");
                            return ERRO;
                        }

                        this.cisuc.getListaArtigoRevista().add(new ArtigoRevista(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, nomeRevista, dataRevista, numeroRevista));
                    } else {
                        System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" (NomeTipo) e %d (NumeroAtributos) não correspondem a nenhum tipo de Publicacao.\n\n", tempTipo, nArgsArtigoConferencia_ArtigoRevista);
                        return ERRO;
                    }
                }
                break;

            case nArgsLivroArtigosConferencia:
                if(!tempTipo.equals(strLivroArtigosConferencia)) {
                    System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" (NomeTipo) e %d (NumeroAtributos) não correspondem a nenhum tipo de Publicacao.\n\n", tempTipo, nArgsLivroArtigosConferencia);
                    return ERRO;
                }

                String editoraLivro2;
                long isbnLivro2;
                String nomeConferencia;
                int numeroArtigos;
                try {
                    editoraLivro2 = listaInputs.get(7);
                    isbnLivro2 = Long.parseLong(listaInputs.get(8));
                    nomeConferencia = listaInputs.get(9);
                    numeroArtigos = Integer.parseInt(listaInputs.get(10));
                } catch (NumberFormatException e) {
                    System.out.print("\n### ERRO ###  \"publicacao.txt\": NumberFormatException: em isbnLivro (Long) ou numeroArtigos (Int).\n\n");
                    return ERRO;
                }

                this.cisuc.getListaLivroArtigosConferencia().add(new LivroArtigosConferencia(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, editoraLivro2, isbnLivro2, nomeConferencia, numeroArtigos));
                break;

            case nArgsCapituloLivro:
                if(!tempTipo.equals(strCapituloLivro)) {
                    System.out.printf("\n### ERRO ###  \"publicacao.txt\": \"%s\" (NomeTipo) e %d (NumeroAtributos) não correspondem a nenhum tipo de Publicacao.\n\n", tempTipo, nArgsCapituloLivro);
                    return ERRO;
                }

                String editoraLivro3;
                long isbnLivro3;
                String nomeCapitulo;
                int paginaInicioCapitulo;
                int paginaFimCapitulo;
                try {
                    editoraLivro3 = listaInputs.get(7);
                    isbnLivro3 = Long.parseLong(listaInputs.get(8));
                    nomeCapitulo = listaInputs.get(9);
                    paginaInicioCapitulo = Integer.parseInt(listaInputs.get(10));
                    paginaFimCapitulo = Integer.parseInt(listaInputs.get(11));
                } catch (NumberFormatException e) {
                    System.out.print("\n### ERRO ###  \"publicacao.txt\": NumberFormatException: em isbnLivro (Long) ou paginaInicioCapitulo (Int) ou paginaFimCapitulo (Int).\n\n");
                    return ERRO;
                }

                this.cisuc.getListaCapituloLivro().add(new CapituloLivro(listaAutores, titulo, listaPalavrasChave, anoPublicacao, dimensaoAudiencia, resumo, editoraLivro3, isbnLivro3, nomeCapitulo, paginaInicioCapitulo, paginaFimCapitulo));
                break;

            default:
                System.out.printf("\n### ERRO ###  \"publicacao.txt\": numero de atributos errados (%d).\n\n", listaInputs.size());
                return ERRO;
        }
        return SUCESSO;
    }


    private boolean rotinaLeituraObj(String nomeFichObj) {
        try {
            File file = new File(nomeFichObj);
            if(!file.exists() || !file.isFile()) {
                System.out.printf("\n### ERRO ###  \"%s\": ficheiro não existe.\n\n", nomeFichObj);
                return ERRO;
            }
            FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis);

            if (this.nomeFichObjGrupoInvestigacao.equals(nomeFichObj))
                this.cisuc.setListaGrupoInvestigacao((ArrayList<GrupoInvestigacao>) ois.readObject());
            else if (this.nomeFichObjEstudante.equals(nomeFichObj))
                this.cisuc.setListaEstudante((ArrayList<Estudante>) ois.readObject());
            else if (this.nomeFichObjMembroEfetivo.equals(nomeFichObj))
                this.cisuc.setListaMembroEfetivo((ArrayList<MembroEfetivo>) ois.readObject());
            else if (this.nomeFichObjArtigoConferencia.equals(nomeFichObj))
                this.cisuc.setListaArtigoConferencia((ArrayList<ArtigoConferencia>) ois.readObject());
            else if (this.nomeFichObjArtigoRevista.equals(nomeFichObj))
                this.cisuc.setListaArtigoRevista((ArrayList<ArtigoRevista>) ois.readObject());
            else if (this.nomeFichObjCapituloLivro.equals(nomeFichObj))
                this.cisuc.setListaCapituloLivro((ArrayList<CapituloLivro>) ois.readObject());
            else if (this.nomeFichObjLivro.equals(nomeFichObj))
                this.cisuc.setListaLivro((ArrayList<Livro>) ois.readObject());
            else if (this.nomeFichObjLivroArtigosConferencia.equals(nomeFichObj))
                this.cisuc.setListaLivroArtigosConferencia((ArrayList<LivroArtigosConferencia>) ois.readObject());
            else {
                System.out.printf("\n### ERRO ###  \"%s\": ficheiro não reconhecido.\n", nomeFichObj);
                return ERRO;
            }

            ois.close();
        } catch (FileNotFoundException e) {
            System.out.printf("\n### ERRO ###  \"%s\": FileNotFoundException: ficheiro inexistente.\n", nomeFichObj);
            return ERRO;
        } catch (IOException e) {
            System.out.printf("\n### ERRO ###  \"%s\": IOException: ao ler ficheiro.\n", nomeFichObj);
            return ERRO;
        } catch (ClassNotFoundException e) {
            System.out.printf("\n### ERRO ###  \"%s\": ClassNotFoundException: esperava ArrayList<GrupoInvestigacao>.\n\n", nomeFichObj);
            return ERRO;
        }

        return SUCESSO;
    }


    private boolean lerFicheirosObj() {
        return this.rotinaLeituraObj(this.nomeFichObjMembroEfetivo) && this.rotinaLeituraObj(this.nomeFichObjEstudante) && this.rotinaLeituraObj(this.nomeFichObjGrupoInvestigacao) && this.rotinaLeituraObj(this.nomeFichObjArtigoConferencia) && this.rotinaLeituraObj(this.nomeFichObjArtigoRevista) && this.rotinaLeituraObj(this.nomeFichObjCapituloLivro) && this.rotinaLeituraObj(this.nomeFichObjLivro) && this.rotinaLeituraObj(this.nomeFichObjLivroArtigosConferencia);
    }


    private boolean rotinaEscritaObj(String nomeFichObj) {
        try {
            File file = new File(nomeFichObj);
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            if (this.nomeFichObjGrupoInvestigacao.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaGrupoInvestigacao());
            else if (this.nomeFichObjEstudante.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaEstudante());
            else if (this.nomeFichObjMembroEfetivo.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaMembroEfetivo());
            else if (this.nomeFichObjArtigoConferencia.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaArtigoConferencia());
            else if (this.nomeFichObjArtigoRevista.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaArtigoRevista());
            else if (this.nomeFichObjCapituloLivro.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaCapituloLivro());
            else if (this.nomeFichObjLivro.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaLivro());
            else if (this.nomeFichObjLivroArtigosConferencia.equals(nomeFichObj))
                oos.writeObject(this.cisuc.getListaLivroArtigosConferencia());
            else {
                System.out.printf("\n### ERRO ###  \"%s\": ficheiro não reconhecido.\n", nomeFichObj);
                return ERRO;
            }

            oos.close();
        } catch (FileNotFoundException e) {
            System.out.printf("\n### ERRO ###  \"%s\": FileNotFoundException: ficheiro inexistente.\n", nomeFichObj);
            return ERRO;
        } catch (IOException e) {
            System.out.printf("\n### ERRO ###  \"%s\": IOException: ao escrever ficheiro.\n", nomeFichObj);
            return ERRO;
        }

        return SUCESSO;
    }


    private boolean escreverFicheirosObj() {
        return this.rotinaEscritaObj(this.nomeFichObjMembroEfetivo) && this.rotinaEscritaObj(this.nomeFichObjEstudante) && this.rotinaEscritaObj(this.nomeFichObjGrupoInvestigacao) && this.rotinaEscritaObj(this.nomeFichObjArtigoConferencia) && this.rotinaEscritaObj(this.nomeFichObjArtigoRevista) && this.rotinaEscritaObj(this.nomeFichObjCapituloLivro) && this.rotinaEscritaObj(this.nomeFichObjLivro) && this.rotinaEscritaObj(this.nomeFichObjLivroArtigosConferencia);
    }


    private boolean temFicheirosObj() {
        File fileObjGrupoInvestigacao = new File(this.nomeFichObjGrupoInvestigacao);
        File fileObjEstudante = new File(this.nomeFichObjEstudante);
        File fileObjMembroEfetivo = new File(this.nomeFichObjMembroEfetivo);

        return fileObjGrupoInvestigacao.exists() && fileObjGrupoInvestigacao.isFile() && fileObjMembroEfetivo.exists() && fileObjMembroEfetivo.isFile() && fileObjEstudante.exists() && fileObjEstudante.isFile();
    }



}
