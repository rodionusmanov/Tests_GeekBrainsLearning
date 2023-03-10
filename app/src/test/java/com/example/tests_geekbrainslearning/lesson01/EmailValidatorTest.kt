package com.example.tests_geekbrainslearning.lesson01

import com.example.tests_geekbrainslearning.lesson01.EmailValidator
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class EmailValidatorTest {
    @Test
    fun emailValidator_CorrectEmailSimple_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.com"))
    }

    @Test
    fun emailValidator_CorrectEmailSubDomain_ReturnsTrue() {
        assertTrue(EmailValidator.isValidEmail("name@email.co.uk"))
    }

    @Test
    fun emailValidator_InvalidEmailNoTld_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email"))
    }

    @Test
    fun emailValidator_InvalidEmailDoubleDot_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email..com"))
    }

    @Test
    fun emailValidator_InvalidEmailNoUsername_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("@email.com"))
    }

    @Test
    fun emailValidator_EmptyString_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(""))
    }

    @Test
    fun emailValidator_NullEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail(null))
    }

    @Test
    fun emailValidator_NonEnglishSymbolsName_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("имя@email.com"))
    }

    @Test
    fun emailValidator_NonEnglishSymbolsEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@почта.com"))
    }

    @Test
    fun emailValidator_NonEnglishSymbolsDomain_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email.рф"))
    }

    @Test
    fun emailValidator_InvalidSymbolsInName_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("na,me@email.com"))
    }

    @Test
    fun emailValidator_InvalidSymbolsInEmail_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@em+ail.com"))
    }

    @Test
    fun emailValidator_InvalidSymbolsInDomain_ReturnsFalse() {
        assertFalse(EmailValidator.isValidEmail("name@email.co_m"))
    }
}