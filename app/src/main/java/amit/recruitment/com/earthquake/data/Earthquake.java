package amit.recruitment.com.earthquake.data;

import java.util.Date;

/**
 * Created by amit on 3/23/18.
 */

public class Earthquake {

    private Date datetime;
    private float depth;
    private String src;
    private String eqid;
    private float lng;
    private float magnitude;
    private float lat;

    public Earthquake(float lat, float lng, float magnitude, String eqid, String src, float depth, Date datetime) {
        this.lat = lat;
        this.lng = lng;
        this.magnitude = magnitude;
        this.eqid = eqid;
        this.src = src;
        this.depth = depth;
        this.datetime = datetime;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    public float getDepth() {
        return depth;
    }

    public void setDepth(float depth) {
        this.depth = depth;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getEqid() {
        return eqid;
    }

    public void setEqid(String eqid) {
        this.eqid = eqid;
    }

    public float getLng() {
        return lng;
    }

    public void setLng(float lng) {
        this.lng = lng;
    }

    public float getMagnitude() {
        return magnitude;
    }

    public void setMagnitude(float magnitude) {
        this.magnitude = magnitude;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }
}
