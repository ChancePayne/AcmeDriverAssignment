package com.cmpayne.acmedriverassignment.domain.models

data class Address(
    val streetNumber: String,
    val streetName: String,
    val unit: String? = null,
    val city: String,
    val state: String,
    val zip: String
) {
    override fun toString(): String =
        "$streetNumber $streetName\n" +
                if (unit != "") {
                    "$unit\n"
                } else {
                    ""
                } + "$city, $state $zip"
}