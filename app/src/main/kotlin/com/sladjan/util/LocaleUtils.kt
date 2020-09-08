package com.sladjan.util

import java.util.*

private object SyncAvoid {
    val defaultLocale: Locale = Locale.getDefault()
    val countryCodes: Set<String> = Locale.getISOCountries().toSet()
    val availableLocales: List<Locale> = Locale.getAvailableLocales().filter { countryCodes.contains(it.country) }
    val countriesLocales: SortedMap<String, Locale> = availableLocales.map { it.country.capitalize() to it }.toMap().toSortedMap()
    val supportedLocales: List<Locale> = setOf(
            Locale.GERMAN,
            Locale.ENGLISH,
            SPANISH,
            Locale.FRENCH,
            Locale.ITALIAN,
            PORTUGUESE,
            RUSSIAN,
            Locale.SIMPLIFIED_CHINESE,
            Locale.TRADITIONAL_CHINESE,
            defaultLocale)
            .toList()
}

val SPANISH: Locale = Locale("es")

val PORTUGUESE: Locale = Locale("pt")

val RUSSIAN: Locale = Locale("ru")

private const val SEPARATOR: String = "_"

fun findByCountryCode(countryCode: String): Locale =
        SyncAvoid.availableLocales
                .find { countryCode.capitalize() == it.country }
                ?: SyncAvoid.defaultLocale

fun allCountries(): List<Locale> = SyncAvoid.countriesLocales.values.toList()

fun findByLanguageTag(languageTag: String): Locale {
    val languageTagPredicate: (Locale) -> Boolean = {
        val locale: Locale = fromLanguageTag(languageTag)
        it.language == locale.language && it.country == locale.country
    }
    return SyncAvoid.supportedLocales.find(languageTagPredicate) ?: SyncAvoid.defaultLocale
}

fun supportedLanguages(): List<Locale> = SyncAvoid.supportedLocales

fun defaultCountryCode(): String = SyncAvoid.defaultLocale.country

fun defaultLanguageTag(): String = toLanguageTag(SyncAvoid.defaultLocale)

fun toLanguageTag(locale: Locale): String = locale.language + SEPARATOR + locale.country

private fun fromLanguageTag(languageTag: String): Locale {
    val codes: Array<String> = languageTag.split(SEPARATOR).toTypedArray()
    return when (codes.size) {
        1 -> Locale(codes[0])
        2 -> Locale(codes[0], codes[1].capitalize())
        else -> SyncAvoid.defaultLocale
    }
}
