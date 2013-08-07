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
import server.model.AccessLevel;
import server.model.AccessType;
import server.model.EnvironmentType;
import server.util.AccessBD;
import server.util.SingleConnection;

/**
 *
 * @author Estudo
 */
public class AccessLevelDAO {

    private AccessBD db = null;
    private PreparedStatement pstmt;
    private ResultSet rs;

    /**
     *
     * @param @return contrutor conecta-se por padrão com as configurações
     * dentro da classe server.util.Config;
     */
    public AccessLevelDAO() {
        db = SingleConnection.getAccessDB();
    }

    /**
     *
     * @param @return contrutor recebe por parâmetro um acesso BD;
     */
    public AccessLevelDAO(AccessBD db) {
        this.db = db;
    }

    /**
     * retorna a conexão que está sendo usada pela classe
     *
     * @param
     * @return
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
    public void insert(AccessLevel o) {
        String sql =
                " INSERT INTO access_level (acclev_impact_factor,environment_type_id,access_type_id) "
                + " VALUES (?,?,?); ";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setDouble(1, o.getImpactFactor());
            pstmt.setInt(2, o.getEnvironmentType().getId());
            pstmt.setInt(3, o.getAccessType().getId());
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
    public void delete(AccessLevel o) {
        String sql =
                " DELETE FROM access_level WHERE acclev_id = ? ; ";
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
    public void update(AccessLevel o) {
        String sql =
                " UPDATE access_level SET acclev_impact_factor = ?, environment_type_id = ?,access_type_id = ? "
                + " WHERE access_id = ? ;";
        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setDouble(1, o.getImpactFactor());
            pstmt.setInt(2, o.getEnvironmentType().getId());
            pstmt.setInt(3, o.getAccessType().getId());
            pstmt.setInt(4, o.getId());
            pstmt.execute();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
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
    public AccessLevel get(int accessLevelId) {
        AccessLevel temp = null;
        String sql = "";

        sql = " SELECT acclev_impact_factor, environment_type_id, access_type_id, envtyp_name, acctyp_name "
                + " FROM access_level, access_type, environment_type "
                + " WHERE environment_type_id = envtyp_id AND access_type_id = acctyp_id AND acclev_id = ?;";

        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, accessLevelId);
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo registro, no caso o 1º registro
                ActionDAO actDAO = new ActionDAO(db);

                temp = new AccessLevel();
                temp.setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.setId(accessLevelId);
                temp.setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setActionsList(actDAO.getList(temp));

            }
            rs.close();
            pstmt.close();
        } catch (SQLException e) {
            System.out.println("Class: " + this.toString()+". Exception: "+e);
        }
        //this.db.disconnect();
        return temp;
    }
    
    public AccessLevel get(EnvironmentType environmentType,AccessType accessType) {
        AccessLevel temp = null;
        String sql = "";

        sql = " SELECT acclev_id,acclev_impact_factor "
                + " FROM access_level "
                + " WHERE environment_type_id = ?  AND access_type_id = ? ;";

        //this.db.connect();
        try {
            pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, environmentType.getId());
            pstmt.setInt(2, accessType.getId());
            rs = pstmt.executeQuery();

            if (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo registro, no caso o 1º registro
                ActionDAO actDAO = new ActionDAO(db);

                temp = new AccessLevel();
                temp.setAccessType(accessType);
                temp.setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.setId(rs.getInt("acclev_id"));
                temp.setEnvironmentType(environmentType);
                temp.setActionsList(actDAO.getList(temp));

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
    public ArrayList<AccessLevel> getList() {
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
    public ArrayList<AccessLevel> getList(int begin, int limit) {
        return getList(begin, limit, false);
    }

    /**
     * Retorna todos os registros que começam pelo índice Begin até uma
     * quatidade Limit, método serve de suporte aos outros 2 lists
     *
     * @param
     * @return
     */
    private ArrayList<AccessLevel> getList(int begin, int limit, boolean all) {
        ArrayList<AccessLevel> list = null;
        AccessLevel temp = null;
        String sql = "";

        //this.db.connect();
        try {
            if (all || (begin == -1) || (limit == -1)) {
                sql =
                        " SELECT acclev_id, acclev_impact_factor, environment_type_id, access_type_id, envtyp_name, acctyp_name " +
                            " FROM access_level, access_type, environment_type " +
                            " WHERE environment_type_id = envtyp_id AND access_type_id = acctyp_id ;";
                pstmt = getConnection().prepareStatement(sql);
            } else {
                sql = " SELECT acclev_id, acclev_impact_factor, environment_type_id, access_type_id, envtyp_name, acctyp_name " +
                            " FROM access_level, access_type, environment_type " +
                            " WHERE environment_type_id = envtyp_id AND access_type_id = acctyp_id "
                        + " LIMIT ? OFFSET ? ;";
                pstmt = getConnection().prepareStatement(sql);
                pstmt.setInt(1, limit);
                pstmt.setInt(2, (begin - 1));
            }

            rs = pstmt.executeQuery();
            list = new ArrayList<AccessLevel>();
            ActionDAO actDAO = new ActionDAO(db);
            while (rs.next()) { // se retornou um número maior que 0 estão existe alguém que possui esse código
                // Vai para o próximo retisro, no caso o 1º registro

                temp = new AccessLevel();
                temp.setAccessType(new AccessType(rs.getInt("access_type_id"), rs.getString("acctyp_name")));
                temp.setImpactFactor(rs.getDouble("acclev_impact_factor"));
                temp.setId(rs.getInt("access_type_id"));
                temp.setEnvironmentType(new EnvironmentType(rs.getInt("environment_type_id"), rs.getString("envtyp_name")));
                temp.setActionsList(actDAO.getList(temp));

                list.add(temp);
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
