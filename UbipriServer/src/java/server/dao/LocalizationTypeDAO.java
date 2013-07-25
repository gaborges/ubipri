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
import server.model.LocalizationType;
import server.model.User;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Estudo
 */
public class LocalizationTypeDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public LocalizationTypeDAO() {
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
    public LocalizationTypeDAO(AccessBD db) {
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
    public void insert(LocalizationType o) {
        String sql =
                " INSERT INTO localization_type (loctyp_name,loctyp_precision,loctyp_metric) VALUES (?,?,?); ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setDouble(2, o.getPrecision());
            pstmt.setString(3, o.getMetric());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    /**
     * Remove um registro no banco de dados através do id passado pelo objeto.
     * Ao menos o objeto deve conter o ID do device a ser removido.
     *
     * @param
     * @return void
     *
     */
    public void delete(LocalizationType o) {
        String sql =
                " DELETE FROM localization_type WHERE loctyp_id = ? ; ";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
    }

    /**
     * Atualiza um registro no banco de dados, somente serão atualizados os
     * dados da classe, não atualizando os demais objetos que são agregados ou
     * que compõem ele.
     *
     * @param um device
     * @return void
     */
    public void update(LocalizationType o) {
        String sql =
                " UPDATE localization_type SET loctyp_name = ? , loctyp_precision = ? , loctyp_metric = ? WHERE loctyp_id = ? ;";
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, o.getName());
            pstmt.setDouble(2, o.getPrecision());
            pstmt.setString(3, o.getMetric());
            pstmt.setInt(4, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
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
    public LocalizationType get(int id, boolean allData) {
        LocalizationType temp = null;
        String sql = "";
        
        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if(id == -1) return null;
        
        if (allData) {
            // não implementado ainda
            sql =
                    " SELECT loctyp_name,loctyp_precision,loctyp_metric " +
                    " FROM localization_type " +
                    " WHERE loctyp_id = ? ;";
        } else {

            sql =
                    " SELECT loctyp_name,loctyp_precision,loctyp_metric " +
                    " FROM localization_type " +
                    " WHERE loctyp_id = ? ;";
        }
        this.db.conectar();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new LocalizationType(id, rs.getString("loctyp_name"), rs.getDouble("loctyp_precision"), rs.getString("loctyp_metric"));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return temp;
    }

    /**
     * retorna todos os registros cadastrados no banco de dados referentes ao
     * objeto alvo
     *
     * @param
     * @return
     */
    public ArrayList<LocalizationType> getList() {
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
    public ArrayList<LocalizationType> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até
     * uma quatidade Limit, método serve de suporte aos outros 2 lists
     * 
     * @param @return 
     */
    private ArrayList<LocalizationType> getList(int begin, int limit, boolean all) {
        ArrayList<LocalizationType> list = null;
        String sql = "";

        this.db.conectar();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT loctyp_id,loctyp_name,loctyp_precision,loctyp_metric " +
                    " FROM localization_type  ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT loctyp_id,loctyp_name,loctyp_precision,loctyp_metric " +
                        " FROM localization_type ORDER BY loctyp_id " +
                        " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin-1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<LocalizationType>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                list.add(new LocalizationType(rs.getInt("loctyp_id"), rs.getString("loctyp_name"), rs.getDouble("loctyp_precision"), rs.getString("loctyp_metric")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        this.db.desconectar();
        return list;
    }
}
