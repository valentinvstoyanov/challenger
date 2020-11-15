package com.valentinvstoyanov.rekord.records.domain

import com.valentinvstoyanov.rekord.records.domain.model.CreateRecord
import com.valentinvstoyanov.rekord.records.domain.model.Record
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

interface RecordService {

    fun createRecord(record: CreateRecord): Mono<Record>

    fun getRecordById(id: String): Mono<Record>
    fun getAllRecords(): Flux<Record>

}