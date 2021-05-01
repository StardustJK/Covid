package com.bupt.covid.pojo;

import javax.persistence.*;

@Entity
@Table(name="tb_user")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "phone")
    private String phone;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private int status;

    @Column(name = "password")
    private String password;

    @Column(name = "real_name")
    private String realName;

    @Column(name = "ID_number")
    private String IDNumber;

    @Column(name = "role")
    private int role;

    @Column(name = "auth")
    private boolean auth;

    @Column(name = "wifi_risk_level")
    private int wifiRiskLevel;

    @Column(name = "bluetooth_risk_level")
    private int bluetoothRiskLevel;

    @Column(name = "wifi_infection_rate")
    private float wifiInfectionRate;

    @Column(name = "bluetooth_infection_rate")
    private float bluetoothInfectionRate;

    public boolean getAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getIDNumber() {
        return IDNumber;
    }

    public void setIDNumber(String IDNumber) {
        this.IDNumber = IDNumber;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isAuth() {
        return auth;
    }

    public int getWifiRiskLevel() {
        return wifiRiskLevel;
    }

    public void setWifiRiskLevel(int wifiRiskLevel) {
        this.wifiRiskLevel = wifiRiskLevel;
    }

    public int getBluetoothRiskLevel() {
        return bluetoothRiskLevel;
    }

    public void setBluetoothRiskLevel(int bluetoothRiskLevel) {
        this.bluetoothRiskLevel = bluetoothRiskLevel;
    }

    public float getWifiInfectionRate() {
        return wifiInfectionRate;
    }

    public void setWifiInfectionRate(float wifiInfectionRate) {
        this.wifiInfectionRate = wifiInfectionRate;
    }

    public float getBluetoothInfectionRate() {
        return bluetoothInfectionRate;
    }

    public void setBluetoothInfectionRate(float bluetoothInfectionRate) {
        this.bluetoothInfectionRate = bluetoothInfectionRate;
    }
}
