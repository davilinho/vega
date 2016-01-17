package com.vega.app.geo.entity;

import android.location.Address;

/**
 * Component created on 22/12/2015.
 *
 * @author dmartin
 */
public class GeoEntity {

    /**
     * Latitud obtenida por el sensor GPS.
     */
    private double latitude;

    /**
     * Longitud obtenida por el sensor GPS.
     */
    private double longitude;

    /**
     * Objeto de tipo Address que contiene la información detallada de la posición geoposicionada.
     */
    private Address address;


    /**
     * Constructor por defecto.
     */
    public GeoEntity() { }

    /**
     * Retorna el valor de la propiedad latitude.
     * @return the latitude.
     */
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Retorna el valor de la propiedad longitude.
     * @return the longitude.
     */
    public double getLongitude() {
        return this.longitude;
    }

    /**
     * Retorna el valor de la propiedad address.
     * @return the address.
     */
    public Address getAddress() {
        return this.address;
    }

    /**
     * Establece el valor de la propiedad latitude.
     * @param latitude the latitude to set.
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * Establece el valor de la propiedad longitude.
     * @param longitude the longitude to set.
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * Establece el valor de la propiedad address.
     * @param address the address to set.
     */
    public void setAddress(Address address) {
        this.address = address;
    }

}
