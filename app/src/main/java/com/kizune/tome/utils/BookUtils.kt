package com.kizune.tome.utils

import com.kizune.tome.R
import com.kizune.tome.network.Book
import java.util.*

enum class FilterType {
    Title,
    Author,
    Genre,
    Publisher,
    Isbn,
}

fun List<Book>.criteria(
    filterType: FilterType,
    filter: String
): List<Book> {
    return when (filterType) {
        FilterType.Title -> {
            this.filter { item ->
                item.volumeInfo.title
                    .contains(filter, true)
            }
        }
        FilterType.Author -> {
            this.filter { item ->
                item.volumeInfo.authors
                    ?.map { author ->
                        author.contains(filter, true)
                    }
                    ?.fold(false) { accumulator, nextItem ->
                        accumulator || nextItem
                    } ?: false
            }
        }
        FilterType.Genre -> {
            this.filter { item ->
                item.volumeInfo.categories
                    ?.map { category ->
                        category.contains(filter, true)
                    }
                    ?.fold(false) { accumulator, nextItem ->
                        accumulator || nextItem
                    } ?: false
                        || item.volumeInfo.previewLink.lowercase().contains(filter.lowercase())
                        || item.volumeInfo.infoLink.lowercase().contains(filter.lowercase())
            }
        }
        FilterType.Publisher -> {
            this.filter { item ->
                item.volumeInfo.publisher
                    .contains(filter, true)
            }
        }
        FilterType.Isbn -> {
            this.filter { item ->
                item.volumeInfo.industryIdentifiers
                    ?.map { single ->
                        single.identifier
                    }
                    ?.map { identifier ->
                        identifier.contains(filter, true)
                    }
                    ?.fold(false) { accumulator, nextItem ->
                        accumulator || nextItem
                    } ?: false
            }
        }
    }
}


/**
 * Permette di creare una lista di Pair a seconda degli attributi del libro
 */
fun Book.createAttributesList(
): List<Pair<Int, String>> {
    val list: ArrayList<Pair<Int, String>> = ArrayList()
    val volumeInfo = this.volumeInfo

    if (!volumeInfo.authors?.joinToString(separator = "\n").isNullOrBlank()) {
        list.add(
            Pair(
                R.string.detail_author,
                volumeInfo.authors?.joinToString(separator = "\n") ?: ""
            ),
        )
    }
    if (volumeInfo.publisher.isNotBlank()) {
        list.add(
            Pair(
                R.string.detail_publisher,
                volumeInfo.publisher
            ),
        )
    }
    if (!volumeInfo.categories?.joinToString(separator = "\n").isNullOrBlank()) {
        list.add(
            Pair(
                R.string.detail_genre,
                volumeInfo.categories?.joinToString(separator = "\n") ?: ""
            ),
        )
    }
    val language = volumeInfo.language.let { language ->
        Locale.forLanguageTag(language).displayLanguage.replaceFirstChar { first ->
            if (first.isLowerCase()) first.titlecase(
                Locale.getDefault()
            ) else first.toString()
        }
    }
    if (language.isNotBlank()) {
        list.add(
            Pair(
                R.string.detail_language,
                language
            )
        )
    }
    if (volumeInfo.publishedDate.substring(0, 4).isNotBlank()) {
        list.add(
            Pair(
                R.string.detail_date,
                volumeInfo.publishedDate.substring(0, 4)
            ),
        )
    }
    if (volumeInfo.pageCount != 0) {
        list.add(
            Pair(
                R.string.detail_pages,
                volumeInfo.pageCount.toString()
            ),
        )
    }

    if (!volumeInfo.industryIdentifiers?.getOrNull(0)?.identifier.isNullOrBlank()) {
        list.add(
            Pair(
                R.string.detail_isbn_10,
                volumeInfo.industryIdentifiers?.getOrNull(0)?.identifier ?: ""
            ),
        )
    }

    if (!volumeInfo.industryIdentifiers?.getOrNull(1)?.identifier.isNullOrBlank()) {
        list.add(
            Pair(
                R.string.detail_isbn_13,
                volumeInfo.industryIdentifiers?.getOrNull(1)?.identifier ?: ""
            ),
        )
    }

    if (volumeInfo.description.isNotBlank()) {
        list.add(
            Pair(
                R.string.detail_description,
                volumeInfo.description
            )
        )
    }
    return list
}