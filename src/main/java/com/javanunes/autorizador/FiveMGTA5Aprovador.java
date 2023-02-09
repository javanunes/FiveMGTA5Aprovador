/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.javanunes.autorizador;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import static java.lang.System.exit;


/**
 *
 * @author ricardo
 */
public class FiveMGTA5Aprovador {
    
    // Troque o usuario, senha , ip e nome do banco conforme o seu banco de dados MySQL ou MariaDB
    static String user="root";
    static String password="blablabla";
    static String host="192.168.1.14";
    static String database="gta5";
    
    static String file = "c:\\FXServer\\server-data\\server.cfg";
    static Connection conn;
    static Statement banco; 
    
 
    private static void aprovaWhiteListDB(String x){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://"+host+":3306/"+database,user,password);
            banco = conn.createStatement();
            
            PreparedStatement stmt = conn.prepareStatement("update vrp_users set whitelisted=? where id=?");
            stmt.setString(1, "1");
            stmt.setString(2,x);
            stmt.executeUpdate();
            
            System.out.printf("ID %s aprovado\n", x);
         }
        catch(Exception e){
            System.out.printf("Deu erro meus amores com id %s: "+e, x);
            System.out.println("Saindo!");
        }
    }
    
    public static void escreveNovoTokenKeyCFG(String isto){
        if(!isto.isEmpty()){
            try{
                String conteudo="";
                String linha="";
                String novaKey = "";
                String procurado  = "sv_licenseKey \"";
                File arquivo = new File(file);
                FileReader arquivoPraLer = new FileReader(arquivo);
                BufferedReader arq = new BufferedReader(arquivoPraLer);
                novaKey = procurado + isto  + "\"";

                while(arq.ready()){
                    linha =  arq.readLine() + "\n";
                    if(linha.indexOf(procurado) > -1){
                        linha = novaKey + "\n";
                    }
                    conteudo += linha;
                }

                FileWriter arquivoAtualizado = new FileWriter(arquivo); 
                //BufferedWriter arqA = new BufferedWriter(arquivoAtualizado);
                arquivoAtualizado.write(conteudo);
                arquivoAtualizado.close();


                System.out.println(conteudo);
            }
            catch(Exception e){
               System.out.println("Erro --> "+e);
            }
        }
    }
    
    public static void main(String... args){
        Scanner prompt = new Scanner(System.in);
        Scanner pessoa = new Scanner(System.in);
        
        String resposta = "";
        Integer op=0;
        Integer id=0;
        
        while(op != 3){
            try{
             System.out.println("1 - autorizar da whitelist | 2 - trocar chave | 3 - trocar chave");
             op = prompt.nextInt();
             if(op == 1){
                System.out.println("UID a liberar: "); 
                id = pessoa.nextInt();
                aprovaWhiteListDB(id.toString());
             }
             if(op == 2){
                System.out.println("Nova chave: ");
                resposta = pessoa.nextLine();
                escreveNovoTokenKeyCFG(resposta);
             }
            }
            catch(Exception e){
               System.out.println("Até logo!"); 
               exit(0);
            }
        }
        System.out.println("Até logo!");
    }
    
}
