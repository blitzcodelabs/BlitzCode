package org.blitzcode.api.domain;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Node
public class Company {
    @Id
    @GeneratedValue
    private Long id;
    private String SIC;
    private String category;
    private String companyNumber;
    private String countryOfOrigin;
    private LocalDate incorporationDate;
    private Integer mortgagesOutstanding;
    private String name;
    private String status;

    // Mapped automatically
    private List<Property> owns = new ArrayList<>();

    @Relationship(type = "HAS_CONTROL", direction = Relationship.Direction.INCOMING)
    private List<Person> controlledBy = new ArrayList<>();

    public Company(String SIC, String category, String companyNumber, String countryOfOrigin, LocalDate incorporationDate, Integer mortgagesOutstanding, String name, String status) {
        this.id = null;
        this.SIC = SIC;
        this.category = category;
        this.companyNumber = companyNumber;
        this.countryOfOrigin = countryOfOrigin;
        this.incorporationDate = incorporationDate;
        this.mortgagesOutstanding = mortgagesOutstanding;
        this.name = name;
        this.status = status;
    }

    public Company withId(Long id) {
        if (this.id.equals(id)) {
            return this;
        } else {
            Company newObject = new Company(this.SIC, this.category, this.companyNumber, this.countryOfOrigin, this.incorporationDate, this.mortgagesOutstanding, this.name, this.status);
            newObject.id = id;
            return newObject;
        }
    }

    public String getSIC() {
        return SIC;
    }

    public void setSIC(String SIC) {
        this.SIC = SIC;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCompanyNumber() {
        return companyNumber;
    }

    public void setCompanyNumber(String companyNumber) {
        this.companyNumber = companyNumber;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }

    public LocalDate getIncorporationDate() {
        return incorporationDate;
    }

    public void setIncorporationDate(LocalDate incorporationDate) {
        this.incorporationDate = incorporationDate;
    }

    public Integer getMortgagesOutstanding() {
        return mortgagesOutstanding;
    }

    public void setMortgagesOutstanding(Integer mortgagesOutstanding) {
        this.mortgagesOutstanding = mortgagesOutstanding;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}