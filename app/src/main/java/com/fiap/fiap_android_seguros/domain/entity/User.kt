package com.fiap.fiap_android_seguros.domain.entity

class User(
    val id: String = "",
    val name: String,
    val email: String,
    val phone: String,
    val password: String,
    val address: String,
    val corretor: Boolean,
    val idade: String
) {
    constructor(name: String) : this("", name, "", "", "", "", false, "")

    constructor(email: String, password: String) : this(
        "",
        "", email, "", password, "", false, ""
    )

}