package com.fiap.fiap_android_seguros.domain.entity

class User(
    val name: String,
    val email: String,
    val phone: String,
    val password: String
) {
    constructor(name: String) : this(name, "", "", "")
    constructor(email: String, password: String) : this("", email, "", password)

}