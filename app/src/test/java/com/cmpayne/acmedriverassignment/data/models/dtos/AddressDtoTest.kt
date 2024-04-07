package com.cmpayne.acmedriverassignment.data.models.dtos

import com.cmpayne.acmedriverassignment.data.dtos.AddressDto
import org.junit.Assert.assertEquals

import org.junit.Test

class AddressDtoTest {

    @Test
    fun parseToDomainModel_StandardFormatNoUnit() {
        val address = AddressDto("18 Sequoia Drive, Berlin, TX 12345").toDomainModel()

        assertEquals(address.streetNumber, "18")
        assertEquals(address.streetName, "Sequoia Drive")
        assertEquals(address.unit, "")
        assertEquals(address.city, "Berlin")
        assertEquals(address.state, "TX")
        assertEquals(address.zip, "12345")
    }

    @Test
    fun parseToDomainModel_StandardFormatWithUnit() {
        val address = AddressDto("2957 Diamond Street, #330, San Francisco, CA 28465").toDomainModel()

        assertEquals(address.streetNumber, "2957")
        assertEquals(address.streetName, "Diamond Street")
        assertEquals(address.unit, "#330")
        assertEquals(address.city, "San Francisco")
        assertEquals(address.state, "CA")
        assertEquals(address.zip, "28465")
    }

    @Test
    fun parseToDomainModel_ExtraCommaWithUnit() {
        val address = AddressDto("2957 Diamond Street, #330, San Francisco, CA 28465").toDomainModel()

        assertEquals(address.streetNumber, "2957")
        assertEquals(address.streetName, "Diamond Street")
        assertEquals(address.unit, "#330")
        assertEquals(address.city, "San Francisco")
        assertEquals(address.state, "CA")
        assertEquals(address.zip, "28465")
    }
}