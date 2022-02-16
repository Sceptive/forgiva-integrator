package com.sceptive.forgiva.integrator.core.fsconnectors;

import com.fasterxml.jackson.annotation.JsonProperty;


/*
{
	"host": "test.com",
	"account": "administrator",
	"animal": "Spider",
	"character_length": 16,
	"complexity": 1,
	"is_lowercase": true,
	"is_uppercase": true,
	"is_symbols": true,
	"is_number": true,
	"password_hash":
	"EE26B0DD4AF7E749AA1A8EE3C10AE9923F618980772E473F8819A5D4940E0DB27AC185F8A0E1D5F84F88BC887FD67B143732C304CC5FA9AD8E6F57F50028A8FF",
	"renewal_date": "",
	"signature": "",
	"legacy_mode": false
}

 */

public class FSRequest {
@JsonProperty("host")
private String  host;
@JsonProperty("account")
private String  account;
@JsonProperty("animal")
private String  animal;
@JsonProperty("character_length")
private Integer character_length;
@JsonProperty("complexity")
private Integer complexity;
@JsonProperty("is_lowercase")
private boolean is_lowercase;
@JsonProperty("is_uppercase")
private boolean is_uppercase;
@JsonProperty("is_symbols")
private boolean is_symbols;
@JsonProperty("is_number")
private boolean is_number;
@JsonProperty("password_hash")
private String  password_hash;
@JsonProperty("renewal_date")
private String  renewal_date;
@JsonProperty("signature")
private String  signature;
@JsonProperty("legacy_mode")
private boolean legacy_mode;

public FSRequest host(String _host) {
    this.host = _host;
    return this;
}

public FSRequest account(String _account) {
    this.account = _account;
    return this;
}

public FSRequest animal(String _animal) {
    this.animal = _animal;
    return this;
}

public FSRequest character_length(Integer _character_length) {
    this.character_length = _character_length;
    return this;
}

public FSRequest complexity(Integer _complexity) {
    this.complexity = _complexity;
    return this;
}

public FSRequest is_lowercase(boolean _is_lowercase) {
    this.is_lowercase = _is_lowercase;
    return this;
}

public FSRequest is_uppercase(boolean _is_uppercase) {
    this.is_uppercase = _is_uppercase;
    return this;
}

public FSRequest is_symbols(boolean _is_symbols) {
    this.is_symbols = _is_symbols;
    return this;
}

public FSRequest is_number(boolean _is_number) {
    this.is_number = _is_number;
    return this;
}

public FSRequest password_hash(String _password_hash) {
    this.password_hash = _password_hash;
    return this;
}

public FSRequest renewal_date(String _renewal_date) {
    this.renewal_date = _renewal_date;
    return this;
}

public FSRequest signature(String _signature) {
    this.signature = _signature;
    return this;
}

public FSRequest legacy_mode(boolean _legacy_mode) {
    this.legacy_mode = _legacy_mode;
    return this;
}

public String getHost() {
    return host;
}

public String getAccount() {
    return account;
}

public String getAnimal() {
    return animal;
}

public Integer getCharacter_length() {
    return character_length;
}

public Integer getComplexity() {
    return complexity;
}

public boolean getIs_lowercase() {
    return is_lowercase;
}

public boolean getIs_uppercase() {
    return is_uppercase;
}

public boolean getIs_symbols() {
    return is_symbols;
}

public boolean getIs_number() {
    return is_number;
}

public String getPassword_hash() {
    return password_hash;
}

public String getRenewal_date() {
    return renewal_date;
}

public String getSignature() {
    return signature;
}

public boolean getLegacy_mode() {
    return legacy_mode;
}
}
