
## 🏛 프로젝트 구조

<img src="https://user-images.githubusercontent.com/79504043/211185103-43339dab-0254-47ff-a299-47e4b0a99b9b.png">  

</br>
</br>


## ⚙ 기술 스택
- 아키텍처 : MVVM Architecture (View - DataBinding - Repository - DataSource) + Single Activity Architecture
- 프래그먼트 관리 : Navigation
- 비동기 처리 : Coroutines + Flow
- REST API 통신 : Retrofit2 + OkHttp3
- 직렬화 라이브러리 : Gson
- 페이징 라이브러리 : Paging3
- 이미지 로딩 라이브러리 : Glide
- 의존성 주입 라이브러리: Hilt
- 에러 핸들링 : Coroutine Exception Handler + LoadStateListener
- 테스트 자동화 : Junit4 + Espresso

</br>

## 🕵 기술적 고민

<details>
<summary>API에서 가져오는 DTO와 UI에서 사용할 UiData 모델의 분리</summary>
<div markdown="1">

</br>

- API에서 가져오는 데이터는 앱에서 실질적으로 사용되는 프로퍼티 외에 더 많은 데이터가 담겨있었습니다.
- 이때 DTO의 모든 데이터를 앱에서 사용하는 모델이 다 가지고 있으면 불필요한 오류를 발생시킬 가능성도 있다고 생각했습니다.
- 따라서 모델을 DTO, UiData 나누어 관리하였으며 Data Class 안의 정적함수에서 데이터 변환 로직을 구현하였습니다.

</br>

[ API에서 가져오는 DTO ]

```kotlin
data class DataSourceDTO(
    @SerializedName("total_cnt")
    val totalCnt: Int,
    @SerializedName("page_block")
    val pageBlock: Int,
    @SerializedName("page_no")
    val pageNo: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("broad")
    val broad: List<Broad>
)

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
```

</br>

[ 실제 앱에서 사용하는 UiData ]

```kotlin
data class UiData(
    val userId: String,
    val broadThumbnail: String,
    val broadTitle: String,
    val profileImage: String,
    val totalViewCount: String
)
```

</br>

[ DTO 에서 받아온 데이터를 UI 에서 사용할 엔티티로 바꾸어 주는 함수 ]

```kotlin
// DTO 에서 받아온 데이터를 UI 에서 사용할 엔티티로 바꾸어 주는 함수
fun toTalkCamDataFromApi(board: Broad) = UiData(
    userId = board.userId,
    broadThumbnail = board.broadThumb,
    broadTitle = board.broadTitle,
    profileImage = board.profileImg,
    totalViewCount = board.totalViewCnt
)
```

</br>


</div>
</details>

<details>
<summary>join() 함수를 이용한 동기처리 및 카테고리 정보를 싱글톤 세션 클래스에 저장</summary>
<div markdown="1">

</br>

- 요구사항에 카테고리 API를 사용하여 가져온 카테고리 중 3개 이상의 카테고리를 선정하여 탭을 구성해야 한다는 내용이 있었습니다.
- 이를 위해서는 먼저 카테고리 API를 통해 카테고리 정보를 가져온 이후에 방송 리스트를 가져와야 했습니다.
- 이를 위해 코루틴 Job의 Join 함수를 사용해 카테고리를 불러오는 함수와 방송정보를 불러오는 함수를 동기적으로 수행하였습니다.
- 또한 3가지 탭에 대해서 매번 데이터를 가져올 때 카테고리를 API 호출을 하는 로직에서 startDestination인 토크 / 캠방 탭에서 한번만 카테고리 API를 호출한 뒤 싱글톤 클래스인 세션에 저장해 재활용하여 API 호출 횟수를 감소시켜 퍼포먼스를 개선하였습니다.

</br>

[ join()을 사용하여 함수를 동기적으로 수행하는 부분 ]
```kotlin
private fun getTalkCamBroadCastList() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val categoryJob = launch(Dispatchers.IO) {
            talkCamRepository.getCategoryNum()
        }
        categoryJob.join() // join 함수를 사용하여 카테고리 정보를 불러오는 정보를 먼저, 즉 동기적으로 수행
        talkCamRepository.getTalkCamBroadCastList()
            .cachedIn(viewModelScope)
            .catch { throwable->
                _talkCamBroadCastList.value = UiState.Error
                _error.emit(CoroutineException.handleThrowableWithCEHModel(throwable))
            }
            .collect {
                _talkCamBroadCastList.value = UiState.Success(it)
            }
    }
}
```

</br>


- CategorySession 클래스는 @Singleton 어노테이션을 통해 앱 전역에서 하나의 인스턴스로 사용되게 하였습니다.

</br>


[ 카테고리 정보를 저장하는 CategorySession 클래스 ]
```kotlin
@Singleton
class CategorySession @Inject constructor() {
    var categoryApiDTO: List<BroadCategory> = emptyList()
}
```

</br>


</div>
</details>

<details>
<summary>PagingSource에서 모든 페이지를 로드했을 때 호출되는 checkEndOfPaginationReached() 함수 로직 고민</summary>
<div markdown="1">

</br>

- Paging3의 PagingSource에서 어떨 때 nextKey를 null로 할지, 즉 마지막 페이지가 로드됐다는 것을 알릴 checkEndOfPaginationReached() 함수의 구현이 필요했습니다.
- 전체 데이터 개수인 totalCount 에서 페이지 하나당 들어오는 개수인 pageBlock 을 나눈 몫에 1을 더하면 총 필요한 페이지 수가 됩니다.
- 예를 들어 전체 데이터가 101개고 한번에 10개의 데이터가 들어온다면 필요한 페이지는 101 / 10 + 1 = 11페이지가 됩니다.
- 이때 주의할 점은 "전체 데이터에서 페이지당 데이터를 나누었을 때 나머지가 0이라면 나눈 몫이 필요한 페이지"입니다. 즉 1을 더할 필요 없다는 뜻입니다. (전체 데이터가 100개고 한번에 10개의 데이터가 들어온다면 필요한 페이지는 10페이지 입니다. 이는 100 % 10 = 0일 때 해당합니다.)
- API 에서 내려주는 pageBlock이 항상 60개가 넘어오므로  pageBlock 60 으로 고정하였습니다.

</br>


[ PagingSource에서 모든 페이지를 로드했을 때 호출되는 checkEndOfPaginationReached() 함수 ]

```kotlin
private fun checkEndOfPaginationReached(
    pageNumber: Int,
    totalCount: Int
): Boolean {
    val checkNum = totalCount / Constants.PAGE_BLOCK
    val formula = totalCount % Constants.PAGE_BLOCK
    if (formula == 0) {
        if (pageNumber >= checkNum) return true // 전체 데이터에서 페이지당 데이터를 나누었을 때 나머지가 0이라면 나눈 몫이 필요한 페이지
    } else {
        if (pageNumber >= checkNum + 1) return true // 전체 데이터 개수인 totalCount 에서 페이지 하나당 들어오는 개수인 pageBlock 을 나눈 몫에 1을 더하면 총 필요한 페이지 수
    }
    return false
}
```

</br>


</div>
</details>

<details>
<summary>재활용할 코드와 하지 않을 코드</summary>
<div markdown="1">

</br>

- 코드의 중복을 방지하기 위해 재활용 할 코드가 무엇이 있는지 고민했습니다.
- 우선 토크 / 캠방, 여행. 먹방 / 쿡방의 응답 데이터는 카테고리 넘버에따라 그 내용이 달라질 뿐 프로퍼티는 형태는 다르지 않으므로 하나의 DTO를 재활용하였습니다.

</br>


[ DataSourceDTO ]

```kotlin
data class DataSourceDTO(
    @SerializedName("total_cnt")
    val totalCnt: Int,
    @SerializedName("page_block")
    val pageBlock: Int,
    @SerializedName("page_no")
    val pageNo: Int,
    @SerializedName("time")
    val time: Int,
    @SerializedName("broad")
    val broad: List<Broad>
)
```

</br>

- 또한 처음에는 PagingAdapter 역시 모든 탭에서 동일하게 사용된다고 생각하여 하나의 PagingAdapter를 모든 탭에서 공유했습니다.
- 하지만 같은 PagingAdapter를 사용할 시 탭을 이동할 때마다 모든 데이터가 새로 들어오므로 (세 가지 탭은 모두 카테고리 넘버에 다라 모두 다른 데이터가 들어오므로) DiffUtil이 모든 아이템뷰를 새로 그리도록 하는 이슈가 발생했습니다.
- 따라서 각 탭마다 PagingAdapter를 하나씩 만들어주는 방식으로 리팩토링 하였습니다.
- 비록 코드가 중복되지만 탭을 이동하여도 새로 뷰를 그리지 않고 기존의 데이터를 그대로 보여주므로 퍼포먼스 측면에서는 개선할 수 있었습니다.

</br>


<img width="400" alt="image" src="https://user-images.githubusercontent.com/79504043/211184879-d7efd78f-2f6c-4ad9-aa8a-fd6c0926f5cd.png">

</br>
</br>



</div>
</details>


<details>
<summary>Ui 테스트(Espresso)에서 일정시간 동안 대기하는 waitFor() 함수</summary>
<div markdown="1">

</br>

- Ui 테스트 도중 RecyclerView의 RecyclerView에 데이터가 제대로 들어왔는지 확인하는 로직이 있었습니다.
- 하지만 데이터를 네트워크로 가져오는데 시간이 걸리기 때문에 데이터를 RecyclerView에 그리기 전에 테스트가 수행되서 테스트 실패로 이어지는 상황이 발생했습니다.
- 따라서 네트워크에서 데이터를 가져올 때 까지 일정시간 동안 기다리게 만드는 waitFor() 함수를 만들어 사용했습니다.

</br>


[ 네트워크에서 데이터를 가져올 때 까지 일정시간 동안 기다리게 만드는 waitFor() 함수 ]

```kotlin
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
```

</br>


</div>
</details>

</br>


## 📱 동작 영상

| 토크 / 캠방 | 여행 | 먹방 / 쿡방 | 상세화면 |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/211182838-5f3122be-440e-4cd1-a8e4-d4f763ab86a4.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211182957-99d62fc7-7145-466c-8ccc-2253f56421bb.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211182999-963cfdac-f84d-4dae-8ad3-73b7de66a66e.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211183034-e074e7b5-17ab-415d-97b9-0add8571392c.gif width=200> |

</br>


## 📌 테스트 통과 이미지

[ Unit 테스트 ]

<img width="500" alt="스크린샷 2022-11-30 오후 7 08 50" src="https://user-images.githubusercontent.com/79504043/211182569-f6d5097f-d1a6-41ce-9a43-feb9b91a347a.png">

</br>
</br>


[ UI 테스트 ]

<img width="572" alt="스크린샷 2022-12-01 오후 2 39 57" src="https://user-images.githubusercontent.com/79504043/211182583-722e8b98-d110-431d-9106-39539c0728ef.png">

</br>
