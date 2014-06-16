/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

/**
 *
 * @author guilherme
 */
public class Point {
    /*
     * envpoi_id serial not null primary key,
	envpoi_latitude double precision not null,
	envpoi_longitude double precision not null,
	envpoi_altitude double precision not null default 0.0,
	envpoi_order integer,
	environment_id integer not null,
     */
    private int id;
    private double latitude;
    private double longitude;
    private double altitude;
    private double order;

    public Point() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public double getOrder() {
        return order;
    }

    public void setOrder(double order) {
        this.order = order;
    }
    
}
