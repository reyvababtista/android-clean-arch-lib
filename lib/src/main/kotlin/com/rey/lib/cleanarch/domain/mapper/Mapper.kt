package com.rey.lib.cleanarch.domain.mapper

interface Mapper<SourceType, ResultType> {
    fun map(dataModel: SourceType): ResultType
}