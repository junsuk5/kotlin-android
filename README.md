[공지] kotlin-extensions 비추천으로 인해 기본 프로젝트 구성시 자동으로 id 참조가 되지 않는 문제가 있습니다

Module수준의 build.gradle 의 최상단에 다음과 같이 id 'kotlin-android-extensions' 를 추가하시면 책 내용대로 진행하실 수 있습니다.
```
plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'kotlin-android-extensions'
}
```

[공지] 2019. 7. 22. 이후부터 AndroidX, SDK 29에 대응하는 코드로 업데이트 하고 있습니다.

[공지] 현재 안드로이드 스튜디오로 새 프로젝트를 만들면 AndroidX가 강제로 설정되므로 책에 support.로 시작하는 패키지들이 모두 androidx. 로 변경되었습니다. 

# Android 10(API29) 기준 변경점 리뷰 영상
* 챕터 5 - https://youtu.be/bFTlUoQgdcM
* 챕터 7 - https://youtu.be/JlPO9vXVxDY
* 챕터 9 - https://youtu.be/ixeua1QsKqI
* 챕터 13 - https://youtu.be/PS0MmgxVjYc

# 챕터13
현재 버전에서 BasicActivity를 선택하면 자동으로 Fragment를 이용해 구현된 예제가 나오는데 책에 있는 내용과 꽤 달라져 맞춰가면서 수정을 하여 독자분께서 공개한 소스입니다.
참고하시기 바랍니다.
https://github.com/dazeemdas/ojs_ch13_fragment

# 챕터9
챕터9는 Android10의 저장소 관련 정책 변경으로 (Scoped Strorage) targetSdk 29로 실행하기 위해서 상당수 코드 변경이 필요합니다.

https://github.com/junsuk5/kotlin-android/commit/b56e66051cb1af7aaa621c861c19f41b29664858

기본 코드를 그대로 실행하려면 targetSdk를 27로 하시면 됩니다.

# 오준석의 안드로이드 생존코딩: 코틀린 편
<img src="http://image.yes24.com/momo/TopCate1985/MidCate002/198416184.jpg" width="240">

* [Yes24](http://bit.ly/2N6RoUS)

## 예제 코드 실행 방법

안드로이드 스튜디오에서 각 챕터를 Open 합니다.

## 안드로이드 관련 운영 페이지
* [유튜브](https://www.youtube.com/playlist?list=PLxTmPHxRH3VWTd-8KB67Itegihkl4SVKe)
* [페이스북](https://www.facebook.com/untilandroid)
* [브런치](https://brunch.co.kr/@hopeless)

## 오탈자 및 오류, 변경사항 (1쇄 기준, 3쇄에는 대부분 수정되어 있음)

책을 보시다가 오탈자 및 오류를 발견하실 경우 Github에 이슈로 등록 해 주시기 바랍니다.

- 14쪽 하단 '구글 플레이스 스토어' -> '구글 플레이 스토어'
- 53쪽 아래 그림 5, 6번 동그라미 위치 오류
- 57쪽 그림 3, 4번 동그라미 위치 살짝 위로
- 65쪽 아래에서 두번째 줄. 동그라미 2 지보드 -> gboard
- 77쪽 동그라미 9 위치 오류
- 101쪽 중간 'when문은' -> 'when문을'
- 105쪽 둘째줄 '이 코드는 빈 생성자를' -> '이 코드는 name 파라미터를 받는 생성자를'
- 113, 114쪽 toUpperCase -> toUpperCase()
- 155쪽 둘째줄 '에디티텍스트' -> '에디트텍스트'
- 160쪽 6.3절 둘째줄 'FloatingActionButton' -> 'FloatingActionButton,' (콤마)
- 169쪽 하단 스샷 'com.android.support.design:27.1.1' -> 'com.google.android.material:material:1.0.0' 로 변경 됨
- 6장 전체적으로 본문 LAB -> LAP 수정
  - 160쪽 하단 스샷 LAB -> LAP
  - 175쪽 4째줄 labButton -> lapButton
  - 176쪽 스샷수정 LAB -> LAP
  - 180쪽 하단 11번 동그라미에 labLayout -> lapLayout
  - 186쪽 6.5.2절 소스코드 4번 동그라미 줄 LAB -> LAP
  - 186쪽 6.5.2절 하단 문단 4번 동그라미 1 LAB -> 1 LAP
  - 187쪽 소스코드 아래에서 6번째 줄 LAB -> LAP
- 194쪽 7.3.1 테마 수정 삭제 -> Android Studio 3.3 부터 해당 기능이 삭제 됨
  - https://github.com/junsuk5/kotlin-android/tree/master/Chapter07/app/src/main/res/values 참고하여 직접 테마수정 가능함
- 201쪽 제일 아래줄 7번 동그라미 WebView -> webView
- 305쪽 소스에서 setContentView(R.layout.activity_maps) 누락됨
- 316쪽 하당 소스 주석 중 // 세모 모드로... -> // 세로 모드로... 로 수정
- 325쪽 flashOn(), flashOff() 메서드내에 cameraManager.setTorchMode(cameraId!!, true) 로 수정
- 339쪽 "새로 크기를" -> "세로 크기를"
- 396쪽 하단 이미지에서 kotlin-kapt 를 realm-android 보다 위에 작성
- 405쪽 소스 코드 중 .visibility = View.GONE -> hide() 로 수정
- 411쪽 하단 소스 코드 'realmCollection' -> 'realmResult'
- 414쪽 스샷 Root element 값을 android.support.constraint.ConstraintLayout -> androidx.constraintlayout.widget.ConstraintLayout 로 수정


## 관련 서적

* [될 때까지 안드로이드](http://www.yes24.com/24/goods/59298937?scode=032&OzSrank=1)
