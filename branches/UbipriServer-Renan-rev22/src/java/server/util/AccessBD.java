/*
 * Classe para estabelecer conexão com banco de dados Postgres.
 * Certifique-se que o projeto tenha a biblioteca (arquivo .jar) do Postgres 
 *  senão tiver adicione na pasta Bibliotecas.
 */
package server.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author evandro
 */
public final class AccessBD {

    private Connection conn;
    private String connectionString;
    private String serverAddress;
    private String dataBaseName;
    private String userName;
    private String userPassword;

    // Construtor
    /**
     *
     * @param Construtor principal
     * @return carrega o drive do postgres
     */
    public AccessBD() {
        // Carrega o driver de conexão com o banco de dados
        // se não achar o driver lança uma exceção.
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println(cnfe.getMessage());
        }
    }
    // Construtor com parâmetros
    /**
     *
     * @param Contrutor com parâmetros: (endereço do Servidor, nome do bando de
     * dados, nome do usuário, senha do usuário);
     * @return ao instanciar já insere os dados para conexão com o banco de
     * dados
     */
    public AccessBD(String servidorBD, String nomeBD, String usuarioBD, String senhaBD) {
        this();
        this.setStringConexao(servidorBD, nomeBD, usuarioBD, senhaBD);
    }

    // Estabelece conexão com banco de dados.
    /**
     *
     * @param @return Estabelece conexão com banco de dados.
     */
    public void connect() {
        // Verifica se a conexão já não está aberta

        // Tenta conectar na base de dados.
        try {
            if (conn == null) {
                conn = DriverManager.getConnection(getStringDeConexao());
                if (Config.debugClassesDAO) {
                    System.out.println("Conexão com BD estabelecida com sucesso!");
                }
            }
        } catch (SQLException sqlex) {
            System.out.println(sqlex.getMessage());
        }
    }
    // Desconecta do banco de dados.
    /**
     *
     * @param @return Desconecta do banco de dados.
     */
    public void disconnect() {
        if (conn != null) {
            try {
                conn.close();
                if (Config.debugClassesDAO) {
                    System.out.println("Conexão encerrada com sucesso!");
                }
            } catch (Exception e) {
                if (Config.debugClassesDAO) {
                    System.out.println("desconectar: erro ao fechar a conexão.");
                }
            }
        }
    }
    // Retorna um objeto Connection
    /**
     *
     * @param @return retorna um objeto Connection
     */
    public Connection getConnection() {   
        if (conn == null) {
            this.connect();
        }    
        return conn;
    }
    // Retorna string de conexão com o banco    

    public String getStringDeConexao() {
        return connectionString;
    }

    // Configura a string de conexão
    /**
     *
     * @param parâmetros: (endereço do Servidor, nome do bando de dados, nome do
     * usuário, senha do usuário);
     * @return insere os dados para conexão com o banco de dados
     */
    public void setStringConexao(String servidorBD, String nomeBD, String usuarioBD, String senhaBD) {
        this.setEnderecoServidor(servidorBD);
        this.setNomeBancoDeDados(nomeBD);
        this.setNomeUsuario(usuarioBD);
        this.setSenhaUsuario(senhaBD);
        // Monta a string de conexão com banco de dados.
        // Essa string serve apenas para banco Postgres
        // Exemplo de string de conexão:
        // "jdbc:postgresql://localhost/teste?user=postgres&password=ifsul"
        connectionString = "jdbc:postgresql://";
        connectionString += serverAddress + "/";
        connectionString += dataBaseName + "?";
        connectionString += "user=" + userName + "&";
        connectionString += "password=" + userPassword;
    }
    public String getEnderecoServidor() {
        return serverAddress;
    }
    public void setEnderecoServidor(String enderecoServidor) {
        this.serverAddress = enderecoServidor;
    }
    public String getNomeBancoDeDados() {
        return dataBaseName;
    }
    public void setNomeBancoDeDados(String nomeBancoDeDados) {
        this.dataBaseName = nomeBancoDeDados;
    }
    public String getNomeUsuario() {
        return userName;
    }
    public void setNomeUsuario(String nomeUsuario) {
        this.userName = nomeUsuario;
    }
    public String getSenhaUsuario() {
        return userPassword;
    }
    public void setSenhaUsuario(String senhaUsuario) {
        this.userPassword = senhaUsuario;
    }
}
