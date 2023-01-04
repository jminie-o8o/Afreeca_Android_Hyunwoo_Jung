package com.example.afreecaandroid.data.model


import com.google.gson.annotations.SerializedName

data class CategoryApiDTO(
    @SerializedName("broad_category")
    val broadCategory: List<BroadCategory>
) {
    data class BroadCategory(
        @SerializedName("cate_name")
        val cateName: String,
        @SerializedName("cate_no")
        val cateNo: String,
        @SerializedName("child")
        val child: List<Child>
    ) {
        data class Child(
            @SerializedName("cate_name")
            val cateName: String,
            @SerializedName("cate_no")
            val cateNo: String
        )
    }
}
