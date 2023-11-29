package org.blitzcode.api.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node
public class Property {

    @Id
    @GeneratedValue  private Long id;
    private String address;
    private String county;
    private String district;
    private String titleNumber;

    public Property(String address, String county, String district, String titleNumber) {
        this.id = null;
        this.address = address;
        this.county = county;
        this.district = district;
        this.titleNumber = titleNumber;
    }

    public Property withId(Long id) {
        if (this.id.equals(id)) {
            return this;
        } else {
            Property newObject = new Property(this.address, this.county, this.district, this.titleNumber);
            newObject.id = id;
            return newObject;
        }
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTitleNumber() {
        return titleNumber;
    }

    public void setTitleNumber(String titleNumber) {
        this.titleNumber = titleNumber;
    }
}
