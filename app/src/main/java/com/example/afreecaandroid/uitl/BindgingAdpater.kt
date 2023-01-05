package com.example.afreecaandroid.uitl

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import coil.load
import coil.transform.CircleCropTransformation
import com.example.afreecaandroid.R
import com.example.afreecaandroid.ui.model.TalkCamData

// Coil 를 통해 url 을 받아와 imageView 에 표시
@BindingAdapter("setThumbnail")
fun setThumbnail(view: ImageView, imageUrl: String) {
    view.load("https:${imageUrl}") {
        placeholder(R.drawable.ic_baseline_image_not_supported_20) // 로딩 도중 이미지 표시
        error(R.drawable.ic_baseline_image_not_supported_20) // 이미지 로딩 실패시 실패 표시
    }
}

@BindingAdapter("setProfileImage")
fun setProfileImage(view: ImageView, imageUrl: String) {
    view.load("https:${imageUrl}") {
        placeholder(R.drawable.ic_baseline_image_not_supported_20) // 로딩 도중 이미지 표시
        error(R.drawable.ic_baseline_image_not_supported_20) // 이미지 로딩 실패시 실패 표시
        transformations(CircleCropTransformation())
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("setViewer")
fun setViewer(view: TextView, viewer: String) {
    view.text = "${viewer}명 시청중"
}

@BindingAdapter("setProfileImageAtDetail")
fun setProfileImageAtDetail(view: ImageView, uiState: UiState<TalkCamData>?) {
    if (uiState is UiState.Success) {
        view.load("https:${uiState.data.profileImage}") {
            placeholder(R.drawable.ic_baseline_image_not_supported_20) // 로딩 도중 이미지 표시
            error(R.drawable.ic_baseline_image_not_supported_20) // 이미지 로딩 실패시 실패 표시
            transformations(CircleCropTransformation())
        }
    }
}

@BindingAdapter("setTitleAtDetail")
fun setTitleAtDetail(view: TextView, uiState: UiState<TalkCamData>?) {
    if (uiState is UiState.Success) {
        view.text = uiState.data.broadTitle
    }
}

@BindingAdapter("setNickNameAtDetail")
fun setNickNameAtDetail(view: TextView, uiState: UiState<TalkCamData>?) {
    if (uiState is UiState.Success) {
        view.text = uiState.data.userId
    }
}

@BindingAdapter("setThumbnailImageAtDetail")
fun setThumbnailImageAtDetail(view: ImageView, uiState: UiState<TalkCamData>?) {
    if (uiState is UiState.Success) {
        view.load("https:${uiState.data.broadThumbnail}") {
            placeholder(R.drawable.ic_baseline_image_not_supported_20) // 로딩 도중 이미지 표시
            error(R.drawable.ic_baseline_image_not_supported_20) // 이미지 로딩 실패시 실패 표시
        }
    }
}
