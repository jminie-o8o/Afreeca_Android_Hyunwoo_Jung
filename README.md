
## ๐ ํ๋ก์ ํธ ๊ตฌ์กฐ

<img src="https://user-images.githubusercontent.com/79504043/211185103-43339dab-0254-47ff-a299-47e4b0a99b9b.png">  

</br>
</br>


## โ ๊ธฐ์  ์คํ
- ์ํคํ์ฒ : MVVM Architecture (View - DataBinding - Repository - DataSource) + Single Activity Architecture
- ํ๋๊ทธ๋จผํธ ๊ด๋ฆฌ : Navigation
- ๋น๋๊ธฐ ์ฒ๋ฆฌ : Coroutines + Flow
- REST API ํต์  : Retrofit2 + OkHttp3
- ์ง๋ ฌํ ๋ผ์ด๋ธ๋ฌ๋ฆฌ : Gson
- ํ์ด์ง ๋ผ์ด๋ธ๋ฌ๋ฆฌ : Paging3
- ์ด๋ฏธ์ง ๋ก๋ฉ ๋ผ์ด๋ธ๋ฌ๋ฆฌ : Glide
- ์์กด์ฑ ์ฃผ์ ๋ผ์ด๋ธ๋ฌ๋ฆฌ: Hilt
- ์๋ฌ ํธ๋ค๋ง : Coroutine Exception Handler + LoadStateListener
- ํ์คํธ ์๋ํ : Junit4 + Espresso

</br>

## ๐ต ๊ธฐ์ ์  ๊ณ ๋ฏผ

<details>
<summary>API์์ ๊ฐ์ ธ์ค๋ DTO์ UI์์ ์ฌ์ฉํ  UiData ๋ชจ๋ธ์ ๋ถ๋ฆฌ</summary>
<div markdown="1">

</br>

- API์์ ๊ฐ์ ธ์ค๋ ๋ฐ์ดํฐ๋ ์ฑ์์ ์ค์ง์ ์ผ๋ก ์ฌ์ฉ๋๋ ํ๋กํผํฐ ์ธ์ ๋ ๋ง์ ๋ฐ์ดํฐ๊ฐ ๋ด๊ฒจ์์์ต๋๋ค.
- ์ด๋ DTO์ ๋ชจ๋  ๋ฐ์ดํฐ๋ฅผ ์ฑ์์ ์ฌ์ฉํ๋ ๋ชจ๋ธ์ด ๋ค ๊ฐ์ง๊ณ  ์์ผ๋ฉด ๋ถํ์ํ ์ค๋ฅ๋ฅผ ๋ฐ์์ํฌ ๊ฐ๋ฅ์ฑ๋ ์๋ค๊ณ  ์๊ฐํ์ต๋๋ค.
- ๋ฐ๋ผ์ ๋ชจ๋ธ์ DTO, UiData ๋๋์ด ๊ด๋ฆฌํ์์ผ๋ฉฐ Data Class ์์ ์ ์ ํจ์์์ ๋ฐ์ดํฐ ๋ณํ ๋ก์ง์ ๊ตฌํํ์์ต๋๋ค.

</br>

[ API์์ ๊ฐ์ ธ์ค๋ DTO ]

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

[ ์ค์  ์ฑ์์ ์ฌ์ฉํ๋ UiData ]

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

[ DTO ์์ ๋ฐ์์จ ๋ฐ์ดํฐ๋ฅผ UI ์์ ์ฌ์ฉํ  ๋ชจ๋ธ๋ก ๋ฐ๊พธ์ด ์ฃผ๋ ํจ์ ]

```kotlin
// DTO ์์ ๋ฐ์์จ ๋ฐ์ดํฐ๋ฅผ UI ์์ ์ฌ์ฉํ  ๋ชจ๋ธ๋ก ๋ฐ๊พธ์ด ์ฃผ๋ ํจ์
fun toUiDataFromDTO(board: Broad) = UiData(
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
<summary>join() ํจ์๋ฅผ ์ด์ฉํ ๋๊ธฐ์ฒ๋ฆฌ ๋ฐ ์นดํ๊ณ ๋ฆฌ ์ ๋ณด๋ฅผ ์ฑ๊ธํค ์ธ์ ํด๋์ค์ ์ ์ฅ</summary>
<div markdown="1">

</br>

- ์๊ตฌ์ฌํญ์ ์นดํ๊ณ ๋ฆฌ API๋ฅผ ์ฌ์ฉํ์ฌ ๊ฐ์ ธ์จ ์นดํ๊ณ ๋ฆฌ ์ค 3๊ฐ ์ด์์ ์นดํ๊ณ ๋ฆฌ๋ฅผ ์ ์ ํ์ฌ ํญ์ ๊ตฌ์ฑํด์ผ ํ๋ค๋ ๋ด์ฉ์ด ์์์ต๋๋ค.
- ์ด๋ฅผ ์ํด์๋ ๋จผ์  ์นดํ๊ณ ๋ฆฌ API๋ฅผ ํตํด ์นดํ๊ณ ๋ฆฌ ์ ๋ณด๋ฅผ ๊ฐ์ ธ์จ ์ดํ์ ๋ฐฉ์ก ๋ฆฌ์คํธ๋ฅผ ๊ฐ์ ธ์์ผ ํ์ต๋๋ค.
- ์ด๋ฅผ ์ํด ์ฝ๋ฃจํด Job์ Join ํจ์๋ฅผ ์ฌ์ฉํด ์นดํ๊ณ ๋ฆฌ๋ฅผ ๋ถ๋ฌ์ค๋ ํจ์์ ๋ฐฉ์ก์ ๋ณด๋ฅผ ๋ถ๋ฌ์ค๋ ํจ์๋ฅผ ๋๊ธฐ์ ์ผ๋ก ์ํํ์์ต๋๋ค.
- ๋ํ 3๊ฐ์ง ํญ์ ๋ํด์ ๋งค๋ฒ ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์ฌ ๋ ์นดํ๊ณ ๋ฆฌ๋ฅผ API ํธ์ถ์ ํ๋ ๋ก์ง์์ startDestination์ธ ํ ํฌ / ์บ ๋ฐฉ ํญ์์ ํ๋ฒ๋ง ์นดํ๊ณ ๋ฆฌ API๋ฅผ ํธ์ถํ ๋ค ์ฑ๊ธํค ํด๋์ค์ธ ์ธ์์ ์ ์ฅํด ์ฌํ์ฉํ์ฌ API ํธ์ถ ํ์๋ฅผ ๊ฐ์์์ผ ํผํฌ๋จผ์ค๋ฅผ ๊ฐ์ ํ์์ต๋๋ค.

</br>

[ join()์ ์ฌ์ฉํ์ฌ ํจ์๋ฅผ ๋๊ธฐ์ ์ผ๋ก ์ํํ๋ ๋ถ๋ถ ]
```kotlin
private fun getTalkCamBroadCastList() {
    viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
        val categoryJob = launch(Dispatchers.IO) {
            talkCamRepository.getCategoryNum()
        }
        categoryJob.join() // join ํจ์๋ฅผ ์ฌ์ฉํ์ฌ ์นดํ๊ณ ๋ฆฌ ์ ๋ณด๋ฅผ ๋ถ๋ฌ์ค๋ ์ ๋ณด๋ฅผ ๋จผ์ , ์ฆ ๋๊ธฐ์ ์ผ๋ก ์ํ
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


- CategorySession ํด๋์ค๋ @Singleton ์ด๋ธํ์ด์์ ํตํด ์ฑ ์ ์ญ์์ ํ๋์ ์ธ์คํด์ค๋ก ์ฌ์ฉ๋๊ฒ ํ์์ต๋๋ค.

</br>


[ ์นดํ๊ณ ๋ฆฌ ์ ๋ณด๋ฅผ ์ ์ฅํ๋ CategorySession ํด๋์ค ]
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
<summary>PagingSource์์ ๋ชจ๋  ํ์ด์ง๋ฅผ ๋ก๋ํ์ ๋ ํธ์ถ๋๋ checkEndOfPaginationReached() ํจ์ ๋ก์ง ๊ณ ๋ฏผ</summary>
<div markdown="1">

</br>

- Paging3์ PagingSource์์ ์ด๋จ ๋ nextKey๋ฅผ null๋ก ํ ์ง, ์ฆ ๋ง์ง๋ง ํ์ด์ง๊ฐ ๋ก๋๋๋ค๋ ๊ฒ์ ์๋ฆด checkEndOfPaginationReached() ํจ์์ ๊ตฌํ์ด ํ์ํ์ต๋๋ค.
- ์ ์ฒด ๋ฐ์ดํฐ ๊ฐ์์ธ totalCount ์์ ํ์ด์ง ํ๋๋น ๋ค์ด์ค๋ ๊ฐ์์ธ pageBlock ์ ๋๋ ๋ชซ์ 1์ ๋ํ๋ฉด ์ด ํ์ํ ํ์ด์ง ์๊ฐ ๋ฉ๋๋ค.
- ์๋ฅผ ๋ค์ด ์ ์ฒด ๋ฐ์ดํฐ๊ฐ 101๊ฐ๊ณ  ํ๋ฒ์ 10๊ฐ์ ๋ฐ์ดํฐ๊ฐ ๋ค์ด์จ๋ค๋ฉด ํ์ํ ํ์ด์ง๋ 101 / 10 + 1 = 11ํ์ด์ง๊ฐ ๋ฉ๋๋ค.
- ์ด๋ ์ฃผ์ํ  ์ ์ "์ ์ฒด ๋ฐ์ดํฐ์์ ํ์ด์ง๋น ๋ฐ์ดํฐ๋ฅผ ๋๋์์ ๋ ๋๋จธ์ง๊ฐ 0์ด๋ผ๋ฉด ๋๋ ๋ชซ์ด ํ์ํ ํ์ด์ง"์๋๋ค. ์ฆ 1์ ๋ํ  ํ์ ์๋ค๋ ๋ป์๋๋ค. (์ ์ฒด ๋ฐ์ดํฐ๊ฐ 100๊ฐ๊ณ  ํ๋ฒ์ 10๊ฐ์ ๋ฐ์ดํฐ๊ฐ ๋ค์ด์จ๋ค๋ฉด ํ์ํ ํ์ด์ง๋ 10ํ์ด์ง ์๋๋ค. ์ด๋ 100 % 10 = 0์ผ ๋ ํด๋นํฉ๋๋ค.)
- API ์์ ๋ด๋ ค์ฃผ๋ pageBlock์ด ํญ์ 60๊ฐ๊ฐ ๋์ด์ค๋ฏ๋ก  pageBlock 60 ์ผ๋ก ๊ณ ์ ํ์์ต๋๋ค.

</br>


[ PagingSource์์ ๋ชจ๋  ํ์ด์ง๋ฅผ ๋ก๋ํ์ ๋ ํธ์ถ๋๋ checkEndOfPaginationReached() ํจ์ ]

```kotlin
private fun checkEndOfPaginationReached(
    pageNumber: Int,
    totalCount: Int
): Boolean {
    val checkNum = totalCount / Constants.PAGE_BLOCK
    val formula = totalCount % Constants.PAGE_BLOCK
    if (formula == 0) {
        if (pageNumber >= checkNum) return true // ์ ์ฒด ๋ฐ์ดํฐ์์ ํ์ด์ง๋น ๋ฐ์ดํฐ๋ฅผ ๋๋์์ ๋ ๋๋จธ์ง๊ฐ 0์ด๋ผ๋ฉด ๋๋ ๋ชซ์ด ํ์ํ ํ์ด์ง
    } else {
        if (pageNumber >= checkNum + 1) return true // ์ ์ฒด ๋ฐ์ดํฐ ๊ฐ์์ธ totalCount ์์ ํ์ด์ง ํ๋๋น ๋ค์ด์ค๋ ๊ฐ์์ธ pageBlock ์ ๋๋ ๋ชซ์ 1์ ๋ํ๋ฉด ์ด ํ์ํ ํ์ด์ง ์
    }
    return false
}
```

</br>


</div>
</details>

<details>
<summary>์ฌํ์ฉํ  ์ฝ๋์ ํ์ง ์์ ์ฝ๋</summary>
<div markdown="1">

</br>

- ์ฝ๋์ ์ค๋ณต์ ๋ฐฉ์งํ๊ธฐ ์ํด ์ฌํ์ฉ ํ  ์ฝ๋๊ฐ ๋ฌด์์ด ์๋์ง ๊ณ ๋ฏผํ์ต๋๋ค.
- ์ฐ์  ํ ํฌ / ์บ ๋ฐฉ, ์ฌํ. ๋จน๋ฐฉ / ์ฟก๋ฐฉ์ ์๋ต ๋ฐ์ดํฐ๋ ์นดํ๊ณ ๋ฆฌ ๋๋ฒ์๋ฐ๋ผ ๊ทธ ๋ด์ฉ์ด ๋ฌ๋ผ์ง ๋ฟ ํ๋กํผํฐ๋ ํํ๋ ๋ค๋ฅด์ง ์์ผ๋ฏ๋ก ํ๋์ DTO๋ฅผ ์ฌํ์ฉํ์์ต๋๋ค.

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

- ๋ํ ์ฒ์์๋ PagingAdapter ์ญ์ ๋ชจ๋  ํญ์์ ๋์ผํ๊ฒ ์ฌ์ฉ๋๋ค๊ณ  ์๊ฐํ์ฌ ํ๋์ PagingAdapter๋ฅผ ๋ชจ๋  ํญ์์ ๊ณต์ ํ์ต๋๋ค.
- ํ์ง๋ง ๊ฐ์ PagingAdapter๋ฅผ ์ฌ์ฉํ  ์ ํญ์ ์ด๋ํ  ๋๋ง๋ค ๋ชจ๋  ๋ฐ์ดํฐ๊ฐ ์๋ก ๋ค์ด์ค๋ฏ๋ก (์ธ ๊ฐ์ง ํญ์ ๋ชจ๋ ์นดํ๊ณ ๋ฆฌ ๋๋ฒ์ ๋ค๋ผ ๋ชจ๋ ๋ค๋ฅธ ๋ฐ์ดํฐ๊ฐ ๋ค์ด์ค๋ฏ๋ก) DiffUtil์ด ๋ชจ๋  ์์ดํ๋ทฐ๋ฅผ ์๋ก ๊ทธ๋ฆฌ๋๋ก ํ๋ ์ด์๊ฐ ๋ฐ์ํ์ต๋๋ค.
- ๋ฐ๋ผ์ ๊ฐ ํญ๋ง๋ค PagingAdapter๋ฅผ ํ๋์ฉ ๋ง๋ค์ด์ฃผ๋ ๋ฐฉ์์ผ๋ก ๋ฆฌํฉํ ๋ง ํ์์ต๋๋ค.
- ๋น๋ก ์ฝ๋๊ฐ ์ค๋ณต๋์ง๋ง ํญ์ ์ด๋ํ์ฌ๋ ์๋ก ๋ทฐ๋ฅผ ๊ทธ๋ฆฌ์ง ์๊ณ  ๊ธฐ์กด์ ๋ฐ์ดํฐ๋ฅผ ๊ทธ๋๋ก ๋ณด์ฌ์ฃผ๋ฏ๋ก ํผํฌ๋จผ์ค ์ธก๋ฉด์์๋ ๊ฐ์ ํ  ์ ์์์ต๋๋ค.

</br>


<img width="400" alt="image" src="https://user-images.githubusercontent.com/79504043/211184879-d7efd78f-2f6c-4ad9-aa8a-fd6c0926f5cd.png">

</br>
</br>



</div>
</details>


<details>
<summary>Ui ํ์คํธ(Espresso)์์ ์ผ์ ์๊ฐ ๋์ ๋๊ธฐํ๋ waitFor() ํจ์</summary>
<div markdown="1">

</br>

- Ui ํ์คํธ ๋์ค RecyclerView์ RecyclerView์ ๋ฐ์ดํฐ๊ฐ ์ ๋๋ก ๋ค์ด์๋์ง ํ์ธํ๋ ๋ก์ง์ด ์์์ต๋๋ค.
- ํ์ง๋ง ๋ฐ์ดํฐ๋ฅผ ๋คํธ์ํฌ๋ก ๊ฐ์ ธ์ค๋๋ฐ ์๊ฐ์ด ๊ฑธ๋ฆฌ๊ธฐ ๋๋ฌธ์ ๋ฐ์ดํฐ๋ฅผ RecyclerView์ ๊ทธ๋ฆฌ๊ธฐ ์ ์ ํ์คํธ๊ฐ ์ํ๋์ ํ์คํธ ์คํจ๋ก ์ด์ด์ง๋ ์ํฉ์ด ๋ฐ์ํ์ต๋๋ค.
- ๋ฐ๋ผ์ ๋คํธ์ํฌ์์ ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์ฌ ๋ ๊น์ง ์ผ์ ์๊ฐ ๋์ ๊ธฐ๋ค๋ฆฌ๊ฒ ๋ง๋๋ waitFor() ํจ์๋ฅผ ๋ง๋ค์ด ์ฌ์ฉํ์ต๋๋ค.

</br>


[ ๋คํธ์ํฌ์์ ๋ฐ์ดํฐ๋ฅผ ๊ฐ์ ธ์ฌ ๋ ๊น์ง ์ผ์ ์๊ฐ ๋์ ๊ธฐ๋ค๋ฆฌ๊ฒ ๋ง๋๋ waitFor() ํจ์ ]

```kotlin
// ๋ฐ์ดํฐ๋ฅผ ๋คํธ์ํฌ๋ก ๊ฐ์ ธ์ค๋๋ฐ ์๊ฐ์ด ๊ฑธ๋ฆฌ๊ธฐ ๋๋ฌธ์
// ๋ฐ๋ก ๋ฆฌ์ฌ์ดํด๋ฌ๋ทฐ ํ์ ํ์ธํ๋ฉด ํ์คํธ ์คํจ๊ฐ ๋ฐ์ํ๊ธฐ ๋๋ฌธ์ waitFor ๋ฉ์๋๋ฅผ ์์ฑํด์ค๋ค.
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


## ๐ฑ ๋์ ์์

| ํ ํฌ / ์บ ๋ฐฉ | ์ฌํ | ๋จน๋ฐฉ / ์ฟก๋ฐฉ | ์์ธํ๋ฉด |
|:--------:|:--------:|:--------:|:--------:|
| <img src=https://user-images.githubusercontent.com/79504043/211182838-5f3122be-440e-4cd1-a8e4-d4f763ab86a4.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211182957-99d62fc7-7145-466c-8ccc-2253f56421bb.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211182999-963cfdac-f84d-4dae-8ad3-73b7de66a66e.gif width=200> | <img src=https://user-images.githubusercontent.com/79504043/211183034-e074e7b5-17ab-415d-97b9-0add8571392c.gif width=200> |

</br>


## ๐ ํ์คํธ ํต๊ณผ ์ด๋ฏธ์ง

[ Unit ํ์คํธ ]

<img width="500" alt="แแณแแณแแตแซแแฃแบ 2022-11-30 แแฉแแฎ 7 08 50" src="https://user-images.githubusercontent.com/79504043/211182569-f6d5097f-d1a6-41ce-9a43-feb9b91a347a.png">

</br>
</br>


[ UI ํ์คํธ ]

<img width="572" alt="แแณแแณแแตแซแแฃแบ 2022-12-01 แแฉแแฎ 2 39 57" src="https://user-images.githubusercontent.com/79504043/211182583-722e8b98-d110-431d-9106-39539c0728ef.png">

</br>
