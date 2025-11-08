/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.elearning.entities.Statistics;


import com.mycompany.elearning.entities.Utilisateurs.Admin;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
/**
 *
 * @author ousam713
 */


@Entity
@Table(name = "admin_statistics")
public class AdminStatistics extends Statistics {
    
    private Integer totalUsers;
    
    private Integer totalCourses;
    
    private Float platformGrowth;
    
    private Integer activeUsers;
    
    @OneToOne
    @JoinColumn(name = "admin_id", nullable = false)
    private Admin admin;
    
    // Constructeurs
    public AdminStatistics() {
        super();
        this.totalUsers = 0;
        this.totalCourses = 0;
        this.platformGrowth = 0.0f;
        this.activeUsers = 0;
    }
    
    public AdminStatistics(Admin admin) {
        super();
        this.admin = admin;
        this.totalUsers = 0;
        this.totalCourses = 0;
        this.platformGrowth = 0.0f;
        this.activeUsers = 0;
    }
    
    // Getters et Setters
    public Integer getTotalUsers() {
        return totalUsers;
    }
    
    public void setTotalUsers(Integer totalUsers) {
        this.totalUsers = totalUsers;
    }
    
    public Integer getTotalCourses() {
        return totalCourses;
    }
    
    public void setTotalCourses(Integer totalCourses) {
        this.totalCourses = totalCourses;
    }
    
    public Float getPlatformGrowth() {
        return platformGrowth;
    }
    
    public void setPlatformGrowth(Float platformGrowth) {
        this.platformGrowth = platformGrowth;
    }
    
    public Integer getActiveUsers() {
        return activeUsers;
    }
    
    public void setActiveUsers(Integer activeUsers) {
        this.activeUsers = activeUsers;
    }
    
    public Admin getAdmin() {
        return admin;
    }
    
    public void setAdmin(Admin admin) {
        this.admin = admin;
    }
}