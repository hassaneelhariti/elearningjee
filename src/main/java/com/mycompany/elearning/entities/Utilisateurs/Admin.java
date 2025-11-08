/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;

import jakarta.persistence.*;


/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "admin")
public class Admin extends User {
    
    // Constructeurs
    public Admin() {
        super();
    }
    
    public Admin(String username, String email, String password, String firstName, String lastName) {
        super(username, email, password, firstName, lastName);
    }
}