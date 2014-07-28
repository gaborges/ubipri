/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import server.model.Environment;
import server.model.Point;
import server.util.AccessBD;
import server.util.Config;

/**
 *
 * @author Guazzelli
 */
public class PointDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public PointDAO() {
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
    public PointDAO(AccessBD db) {
        this.db = db;
    }

    /**
     * retorna a conexão que está sendo usada pela classe
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
    public void insert(Point o) {
        String sql
                = " INSERT INTO environment_point (envpoi_latitude,envpoi_longitude,envpoi_altitude,envpoi_order,environment_id)  VALUES (?,?,?,?,?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setDouble(1, o.getLatitude());
            pstmt.setDouble(2, o.getLongitude());
            pstmt.setDouble(3, o.getAltitude());
            pstmt.setDouble(4, o.getOrder());
            pstmt.setInt(5, o.getEnvironment().getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
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
    public void delete(Point o) {
        String sql
                = " DELETE FROM environment_point WHERE envpoi_id = ? ; ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
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
    public void update(Point o) {
        String sql
                = " UPDATE environment_point SET envpoi_latitude = ?,envpoi_longitude = ?,envpoi_altitude = ?,envpoi_order = ?,environment_id = ? WHERE envpoi_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setDouble(1, o.getLatitude());
            pstmt.setDouble(2, o.getLongitude());
            pstmt.setDouble(3, o.getAltitude());
            pstmt.setDouble(4, o.getOrder());
            pstmt.setInt(5, o.getEnvironment().getId());
            pstmt.setInt(6, o.getId());

            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
    }

    /**
     * passa-se por parâmetro dois argumentos: <ul> <li> 1) o id do device que
     * será retornado;</li> <li>2) se true retorna além dos dados do device suas
     * comunicações e funcionalidades (Não implementado ainda) </li>
     * </ul>
     *
     * @param id a ser retornado, argumento se vão ser necessários todos os
     * dados relacionados com a classe
     * @return retorna um device
     */
    public Point get(int id, boolean allData) {
        Point temp = new Point();
        String sql = "";

        // se encontrar -1 quer dizer que chegou ao nodo raiz  (recursivamente), retornando nulo
        if (id == -1) {
            return null;
        }

        if (allData) {
            // não implementado ainda
            sql
                    = " SELECT envpoi_latitude,envpoi_longitude,envpoi_altitude,envpoi_order,environment_id"
                    + " FROM environment_point"
                    + " WHERE envpoi_id = ?;";
        } else {

            sql
                    = " SELECT envpoi_latitude,envpoi_longitude,envpoi_altitude,envpoi_order,environment_id"
                    + " FROM environment_point"
                    + " WHERE envpoi_id = ?;";
        }
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp.setId(id); // Seta o ID
                temp.setLatitude(rs.getDouble("envpoi_latitude"));
                temp.setLongitude(rs.getDouble("envpoi_longitude"));
                temp.setAltitude(rs.getDouble("envpoi_altitude"));
                temp.setOrder(rs.getDouble("envpoi_order"));
                temp.setEnvironment(new Environment(rs.getInt("environment_id")));
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
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
    public ArrayList<Point> getList() {
        return getList(-1, -1, false);
    }

    /**
     * Retorna os registros cadastrados no banco de dados a partir de parâmetro
     * de paginação.
     *
     * @param passa-se o índice inicial e o número de elementos a serem
     * retornados
     * @return retorna todos os devices que começam pelo índice Begin até uma
     * quatidade Limit
     */
    public ArrayList<Point> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até uma
     * quatidade Limit, método serve de suporte aos outros 2 lists
     *
     * @param @return
     */
    private ArrayList<Point> getList(int begin, int limit, boolean all) {
        ArrayList<Point> list = null;
        Point temp = null;
        String sql = "";
        EnvironmentDAO envDAO = new EnvironmentDAO(db);

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql = " SELECT envpoi_id,envpoi_latitude,envpoi_longitude,envpoi_altitude,envpoi_order,environment_id"
                        + " FROM environment_point "
                        + " ORDER BY envpoi_id;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT envpoi_id,envpoi_latitude,envpoi_longitude,envpoi_altitude,envpoi_order,environment_id"
                        + " FROM environment_point"
                        + " LIMIT ? OFFSET ? "
                        + " ORDER BY envpoi_id;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<Point>();
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro
                temp = new Point();
                temp.setId(rs.getInt("envpoi_id")); // Seta o ID
                temp.setLatitude(rs.getDouble("envpoi_latitude"));
                temp.setLongitude(rs.getDouble("envpoi_longitude"));
                temp.setAltitude(rs.getDouble("envpoi_altitude"));
                temp.setOrder(rs.getDouble("envpoi_order"));
                temp.setEnvironment(envDAO.get(rs.getInt("environment_id"), false));
                list.add(temp);
            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString() + ". Exception: " + e);
        }
        //this.db.disconnect();
        return list;
    }
}
