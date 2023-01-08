package com.example.afreecaandroid.ui

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.isRoot
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.afreecaandroid.ui.view.MainActivity
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import com.example.afreecaandroid.R
import com.example.afreecaandroid.ui.adapter.TalkCamPagingAdapter

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    var activityScenarioRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun talk_Cam_Ui_Test() {
        // 1) 네트워크에서 데이터를 가져올 때 까지 일정시간 대기
        onView(isRoot()).perform(waitFor(3000))

        // 2) ApiRecyclerView 에 표시되는 여부 확인
        onView(withId(R.id.rv_talk_cam))
            .check(matches(isDisplayed()))

        // 3) 첫 번째 아이템 클릭
        onView(withId(R.id.rv_talk_cam))
            .perform(actionOnItemAtPosition<TalkCamPagingAdapter.TalkCamViewHolder>(0, click()))
        onView(isRoot()).perform(waitFor(2000))

        // 4) 토크 캠방 프로필 이미지가 정상적으로 뜨는지 확인
        onView(withId(R.id.iv_talk_cam_detail_bj_profile_image))
            .check(matches(isDisplayed()))

        // 5) 토크 캠방 방송 제목이 정상적으로 뜨는지 확인
        onView(withId(R.id.tv_talk_cam_detail_broadcast_title))
            .check(matches(isDisplayed()))

        // 6) 토크 캠방 BJ 닉네임이 정상적으로 뜨는지 확인
        onView(withId(R.id.tv_talk_cam_detail_bj_nickname))
            .check(matches(isDisplayed()))

        // 7) 토크 캠방 썸네일 이미지가 정상적으로 뜨는지 확인
        onView(withId(R.id.iv_talk_cam_detail_thumb_nail))
            .check(matches(isDisplayed()))
    }

    // 데이터를 네트워크로 가져오는데 시간이 걸리기 때문에
    // 바로 리사이클러뷰 표시 확인하면 테스트 실패가 발생하기 때문에 waitFor 메서드를 작성해준다.
    private fun waitFor(delay: Long): ViewAction {
        return object : ViewAction {
            override fun getConstraints(): Matcher<View> = ViewMatchers.isRoot()

            override fun getDescription(): String = "wait for $delay milliseconds"

            override fun perform(uiController: UiController, view: View?) {
                uiController.loopMainThreadForAtLeast(delay)
            }
        }
    }
}
