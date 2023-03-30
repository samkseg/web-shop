package se.iths.webshop.business;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue(value = "EMPLOYEE")
public class Employee extends Person{
    public Employee (){}

    public Employee (String name, String password){
        super(name, password);
    }
}
