package com.cmpayne.acmedriverassignment.data.dtos

import com.cmpayne.acmedriverassignment.domain.models.Address

/* DB best practices recommend addresses be stored as individual values rather than long strings,
 if the data source used in production doesn't follow these practices, I'd use an address
 parsing service like www.smarty.com to parse and verify the addresses rather than the brittle
 string parsing method used here. */
data class AddressDto(
    val stringAddress: String
) {
    fun toDomainModel(): Address {
        val splitString = stringAddress.split(", ")

        val street = splitString.first().split(" ")
        val streetNumber = street.first()
        val builder = StringBuilder()
        street.forEachIndexed { index, string ->
            if (index != 0) {
                builder.append(string)
                if (index < street.size - 1) {
                    builder.append(" ")
                }
            }
        }
        val streetName = builder.toString()

        return when (splitString.size) {
            5 -> Address(
                streetNumber = streetNumber,
                streetName = streetName,
                unit = splitString[1],
                city = splitString[2],
                state = splitString[3].replace(" ", ""),
                zip = splitString[4].replace(" ", "")
            )

            4 -> Address(
                streetNumber = streetNumber,
                streetName = streetName,
                unit = splitString[1],
                city = splitString[2],
                state = splitString[3].split(" ").first(),
                zip = splitString[3].split(" ").last()
            )

            else -> Address(
                streetNumber = streetNumber,
                streetName = streetName,
                unit = "",
                city = splitString[1],
                state = splitString[2].split(" ").first(),
                zip = splitString[2].split(" ").last(),
            )
        }
    }
}