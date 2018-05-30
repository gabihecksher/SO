/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package escalonador;

import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
/**
 *
 * @author gabriela
 */
public class Escalonador {
    private List<Processo> FTR; //fila de processos prontos do tipo tempo-real
    private List<Processo> FU; //fila de processos prontos do tipo usuario
    private Processo processo_cpu; //processo sendo executado atualmente
    
    /**
     * @param args the command line arguments
     */
    
    public void adicionarEmFTR(Processo novo){
        this.FTR.add(novo);
    }
    
    public void adicionarEmFU(Processo novo){
        this.FU.add(novo);
    }
    
    
    public static void main(String[] args) {
        Escalonador escalonador = new Escalonador();
        List<Processo> ftr = new ArrayList<Processo>();
        Scanner ler = new Scanner(System.in);
 
        System.out.printf("Informe o nome de arquivo texto:\n");
        String nome = ler.nextLine();
 
        System.out.printf("\nContaúdo do arquivo texto:\n");
        try {
            FileReader arq = new FileReader(nome);
            BufferedReader lerArq = new BufferedReader(arq);

            String linha = lerArq.readLine();
            while (linha != null) {
                System.out.printf("%s\n", linha);
                Processo processoNovo = new Processo(); //cria novo processo
                String[] informacoesProcesso = linha.split(", "); //separa as informacoes pelas virgulas
                
                //pega cada informacao, transforma em int e associa ao novo processo
                
                processoNovo.setTempoChegada(Integer.parseInt(informacoesProcesso[0])); 
                processoNovo.setPrioridade(Integer.parseInt(informacoesProcesso[1]));
                processoNovo.setTempoProcessamento(Integer.parseInt(informacoesProcesso[2]));
                processoNovo.setTamanho(Integer.parseInt(informacoesProcesso[3]));
                processoNovo.setQtdImpressoras(Integer.parseInt(informacoesProcesso[4]));
                processoNovo.setQtdScanners(Integer.parseInt(informacoesProcesso[5]));
                processoNovo.setQtdModems(Integer.parseInt(informacoesProcesso[6]));
                processoNovo.setQtdCDs(Integer.parseInt(informacoesProcesso[7]));
                ftr.add(processoNovo);
                
                linha = lerArq.readLine(); //le a proxima linha
            }
 
        arq.close();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n",
            e.getMessage());
        }
 
        System.out.println();
        
        int numero = 0;
        for(Processo processo : ftr){
            numero++;
            System.out.println("-----------------------------------------");
            System.out.println("Processo " + numero);
            System.out.println("Tempo Chegada: " + processo.getTempoChegada());
            System.out.println("Prioridade: " + processo.getPrioridade());
            System.out.println("Tempo Processamento: " + processo.getTempoProcessamento());
            System.out.println("Tamanho: " + processo.getTamanho());
            System.out.println("Qtd Impressoras: " + processo.getQtdImpressoras());
            System.out.println("Qtd Scanners: " + processo.getQtdScanners());
            System.out.println("Qtd Modems: " + processo.getQtdModems());
            System.out.println("Qtd CDs: " + processo.getQtdCDs());
            
        }
        
    }
}
