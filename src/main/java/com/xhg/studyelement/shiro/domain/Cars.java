package com.xhg.studyelement.shiro.domain;

import java.io.Serializable;

/**
 * cars
 * @author 
 */
public class Cars implements Serializable {
    /**
     * 汽车ID
     */
    private Integer id;

    /**
     * 车牌
     */
    private String carnumber;

    private String driver;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCarnumber() {
        return carnumber;
    }

    public void setCarnumber(String carnumber) {
        this.carnumber = carnumber;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Cars other = (Cars) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getCarnumber() == null ? other.getCarnumber() == null : this.getCarnumber().equals(other.getCarnumber()))
            && (this.getDriver() == null ? other.getDriver() == null : this.getDriver().equals(other.getDriver()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getCarnumber() == null) ? 0 : getCarnumber().hashCode());
        result = prime * result + ((getDriver() == null) ? 0 : getDriver().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", carnumber=").append(carnumber);
        sb.append(", driver=").append(driver);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}