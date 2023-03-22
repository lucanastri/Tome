package com.kizune.tome.utils

import com.kizune.tome.network.Book

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