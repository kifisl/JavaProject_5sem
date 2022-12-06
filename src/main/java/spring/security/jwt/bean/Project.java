package spring.security.jwt.bean;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "project_table")
@Data
public class Project
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @Size(min = 4, max = 16, message = "name from 4 to 16")
    @NotNull(message = "Can not be null")
    private String name;

    @Column
    @Size(min = 4, message = "description from 4 to inf")
    @NotNull(message = "Can not be null")
    private String description;

    @Column
    @NotNull
    private float price;

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    @Column
    @NotNull
    private int places;

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Project()
    {
    }

    public Project(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
