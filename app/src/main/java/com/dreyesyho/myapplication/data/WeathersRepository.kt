package com.dreyesyho.myapplication.data

interface WeathersRepository {
    suspend fun getPets(): NetworkResult<List<Weather>>
}