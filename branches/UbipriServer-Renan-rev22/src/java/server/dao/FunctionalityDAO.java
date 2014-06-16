/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.model.Functionality;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class FunctionalityDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public FunctionalityDAO() {
        db = new AccessBD(
                Config.dbServer, // IP do Servidor
                Config.dbName, // Nome do Banco de dados
                Config.dbUser, // Usuário
                Config.dbPassword); // Senha
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public FunctionalityDAO(AccessBD db) {
        this.db = db;
    }

    /**
     *  retorna a conexão que está sendo usada pela classe
     * 
     * @param @return 
     */
    public Connection getConnection() {
        return db.getConnection();
    }

    /**
     * insere um device no banco de dados.
     *
     * @param
     * @return
     */
    public void insert(Functionality o) {
        String sql =
                " INSERT INTO functionality (fun_name) VALUES (?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * Remove um registro no banco de dados através do id passado pelo objeto.
     * Ao menos o objeto deve conter o ID do device a ser removido.
     *
     * @param
     * @return void
     *
     */
    public void delete(Functionality o) {
        String sql =
                " DELETE FROM functionality WHERE fun_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * Atualiza um registro no banco de dados, somente serão atualizados os
     * dados da classe, não atualizando os demais objetos que são agregados ou
     * que compõem ele.
     *
     * @param um device
     * @return void
     */
    public void update(Functionality o) {
        String sql =
                " UPDATE functionality SET fun_name = ? WHERE fun_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setInt(2, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
    }

    /**
     * passa-se por parâmetro dois argumentos: <ul> <li> 1) o id do device que será
     * retornado;</li> <li>2) se true retorna além dos dados do device suas comunicações
     * e funcionalidades (Não implementado ainda) </li>
     * </ul>
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public Functionality get(int id, boolean allData) {
        Functionality temp = null;
        String sql = "";
        
        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if(id == -1) return null;
        
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT fan_name " +
                    " FROM functionality " +
                    " WHERE fan_id = ? ;";
        } else {

            sql =
                    " SELECT fan_name " +
                    " FROM functionality " +
                    " WHERE fan_id = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new Functionality(id, rs.getString("fun_name"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<Functionality> getList() {
        return getList(-1, -1, false);
    }

    /**
     * Retorna os registros cadastrados no banco de dados a partir de parâmetro de paginação.
     * 
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<Functionality> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<Functionality> getList(int begin, int limit, boolean all) {
        ArrayList<Functionality> list = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = "SELECT fun_id, fun_name " +
                        " FROM functionality ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT fun_id, fun_name " +
                        " FROM functionality ORDER BY fun_id " +
                        " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<Functionality>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                list.add(new Functionality(rs.getInt("fun_id"), rs.getString("fun_name")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return list;
    }
}
