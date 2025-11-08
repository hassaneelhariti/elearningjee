/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Utilisateurs;

import com.mycompany.elearning.entities.Statistics.AdminStatistics;
import jakarta.persistence.*;


/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "admin")
public class Admin extends User {
    
    @Column(nullable = false)
    private String adminLevel;
    
    @OneToOne(mappedBy = "admin", cascade = CascadeType.ALL)
    private AdminStatistics statistics;
    
    // Constructeurs
    public Admin() {
        super();
    }
    
    public Admin(String username, String email, String password, String firstName, String lastName, String adminLevel) {
        super(username, email, password, firstName, lastName);
        this.adminLevel = adminLevel;
    }
    
    // Getters et Setters
    public String getAdminLevel() {
        return adminLevel;
    }
    
    public void setAdminLevel(String adminLevel) {
        this.adminLevel = adminLevel;
    }
    
    public AdminStatistics getStatistics() {
        return statistics;
    }
    
    public void setStatistics(AdminStatistics statistics) {
        this.statistics = statistics;
    }
}
