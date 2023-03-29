package se.iths.webshop.business;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CUSTOMER")
public class Customer extends Person{
    public Customer (){}

    public Customer (String name, String password){
        super(name, password);
    }

}
