package com.example.afreecaandroid.data.model


import com.google.gson.annotations.SerializedName

data class TalkCamApiDTO(
    @SerializedName("broad")
    val broad: List<Broad>,
    @SerializedName("page_block")
    val pageBlock: Int,
    @SerializedName("page_no")
    val pageNo: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("total_cnt")
    val totalCnt: Int
) {
    data class Broad(
        @SerializedName("broad_bps")
        val broadBps: String,
        @SerializedName("broad_cate_no")
        val broadCateNo: String,
        @SerializedName("broad_grade")
        val broadGrade: String,
        @SerializedName("broad_no")
        val broadNo: String,
        @SerializedName("broad_resolution")
        val broadResolution: String,
        @SerializedName("broad_start")
        val broadStart: String,
        @SerializedName("broad_thumb")
        val broadThumb: String,
        @SerializedName("broad_title")
        val broadTitle: String,
        @SerializedName("is_password")
        val isPassword: String,
        @SerializedName("profile_img")
        val profileImg: String,
        @SerializedName("total_view_cnt")
        val totalViewCnt: String,
        @SerializedName("user_id")
        val userId: String,
        @SerializedName("user_nick")
        val userNick: String,
        @SerializedName("visit_broad_type")
        val visitBroadType: String
    )
}
