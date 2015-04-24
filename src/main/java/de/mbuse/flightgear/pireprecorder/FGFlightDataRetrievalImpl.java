/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package de.mbuse.flightgear.pireprecorder;

import java.util.Calendar;
import java.util.TimeZone;
import org.javalite.http.Http;
import org.javalite.http.Get;
import org.json.JSONObject;

/**
 *
 * @author mbuse
 */
public class FGFlightDataRetrievalImpl implements FlightDataRetrieval, Configuration {
    
    private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
    
    private String host = "localhost";
    private int port = 5500;

    public void setHost(String host) {
        this.host = host;
    }

    public String getHost() {
        return host;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getPort() {
        return port;
    }
    

    private String getPropertyURL(String property) {
        return "http://" + host + ":" + port + "/json/" + property;
    }
    private String getProperty(String property) {
        Get response = Http.get(getPropertyURL(property));
        JSONObject json = new JSONObject(response.text());
        return json.get("value").toString();
    }
    
    @Override
    public String getAirport() {
        return getProperty("sim/airport/closest-airport-id");
    }

    @Override
    public long getFuel() {
        return Math.round(Double.parseDouble(getProperty("consumables/fuel/total-fuel-lbs")));
    }

    @Override
    public double getGroundspeed() {
        return Double.parseDouble(getProperty("velocities/groundspeed-kt"));
    }
    
    @Override
    public Calendar getTimeUTC() {
        Calendar cal = Calendar.getInstance();
        cal.setTimeZone(UTC);
        return cal;
    }
    
    
    
    
}