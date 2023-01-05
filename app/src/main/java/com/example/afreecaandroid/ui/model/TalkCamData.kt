package com.example.afreecaandroid.ui.model

import android.os.Parcelable
import com.example.afreecaandroid.data.model.Broad

@kotlinx.parcelize.Parcelize
data class TalkCamData(
    val userId: String,
    val broadThumbnail: String,
    val broadTitle: String,
    val profileImage: String,
    val totalViewCount: String
) : Parcelable {
    companion object {
        // DTO 에서 받아온 데이터를 UI 에서 사용할 엔티티로 바꾸어 주는 함수
        fun toTalkCamDataFromApi(board: Broad) = TalkCamData(
            userId = board.userId,
            broadThumbnail = board.broadThumb,
            broadTitle = board.broadTitle,
            profileImage = board.profileImg,
            totalViewCount = board.totalViewCnt
        )
    }
}
