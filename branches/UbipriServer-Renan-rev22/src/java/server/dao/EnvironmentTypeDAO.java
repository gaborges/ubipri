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
import server.model.EnvironmentType;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class EnvironmentTypeDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public EnvironmentTypeDAO() {
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
    public EnvironmentTypeDAO(AccessBD db) {
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
    public void insert(EnvironmentType o) {
        String sql =
                " INSERT INTO environment_type (envtyp_name)  VALUES (?); ";
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
    public void delete(EnvironmentType o) {
        String sql =
                " DELETE FROM environment_type WHERE envtyp_id = ? ; ";
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
    public void update(EnvironmentType o) {
        String sql =
                " UPDATE environment_type SET envtyp_name = ? WHERE envtyp_id = ? ;";
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
    public EnvironmentType get(int id, boolean allData) {
        EnvironmentType temp = null;
        String sql = "";
        
        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if(id == -1) return null;
        
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT envtyp_name " +
                    " FROM environment_type " +
                    " WHERE envtyp_id = ? ;";
        } else {

            sql =
                    " SELECT envtyp_name " +
                    " FROM environment_type " +
                    " WHERE envtyp_id = ? ;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new EnvironmentType(id, rs.getString("envtyp_name"));
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
    public ArrayList<EnvironmentType> getList() {
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
    public ArrayList<EnvironmentType> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<EnvironmentType> getList(int begin, int limit, boolean all) {
        ArrayList<EnvironmentType> list = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT envtyp_id, envtyp_name " +
                        " FROM environment_type ";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT envtyp_id, envtyp_name " +
                        " FROM environment_type ORDER BY envtyp_id " +
                        " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<EnvironmentType>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                list.add(new EnvironmentType(rs.getInt("envtyp_id"), rs.getString("envtyp_name")));
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
