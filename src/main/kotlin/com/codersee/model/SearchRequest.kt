package com.codersee.model

import io.micronaut.serde.annotation.Serdeable

@Serdeable.Deserializable
data class SearchRequest(val name: String)