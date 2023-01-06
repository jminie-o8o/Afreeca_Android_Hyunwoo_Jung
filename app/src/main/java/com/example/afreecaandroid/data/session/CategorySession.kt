package com.example.afreecaandroid.data.session

import com.example.afreecaandroid.data.model.BroadCategory
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategorySession @Inject constructor() {
    var categoryApiDTO: List<BroadCategory> = emptyList()
}
