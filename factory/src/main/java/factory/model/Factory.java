package factory.model;

import javax.persistence.*;

@Entity
@Table(name = "factory")
public class Factory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "factory_id")
    private Long id;

    @Column(name = "factory_name")
    private String name;

    @Column(name = "factory_address")
    private String address;

    @Column(name = "factory_email")
    private String email;

    @Column(name = "contact_number")
    private String contactNumber;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

  }
