# 번개장터 클론코딩 🛒

## 프로젝트 설명
> 중고거래 플랫폼 번개장터를 구현했습니다.<br/>
> 템플릿의 저작권은 (주)소프트스퀘어드에 있습니다. 상업적 용도의 사용을 금합니다.

## 프로젝트 적용 기술

<img src="https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=Android&logoColor=white"> <img src="https://img.shields.io/badge/Kotlin-7F52FF?style=for-the-badge&logo=Kotlin&logoColor=white">

<img src="https://img.shields.io/badge/Retrofit2-00B274?style=for-the-badge&logo=&logoColor=white"> <img src="https://img.shields.io/badge/OkHttp3-3E4348?style=for-the-badge&logo=&logoColor=white">
<img src="https://img.shields.io/badge/FirebaseStorage-FFCA28?style=for-the-badge&logo=&logoColor=white">

## 구현 사항

### 1. 로그인, 회원가입
ViewPager와 Thread sleep 기능을 이용하여 로그인 안내 페이지의 이미지가 자동 변경됩니다.<br/>
TextWatcher를 이용하여 회원가입의 다음단계로 넘어갈 수 있습니다.

![bunjang_join](https://user-images.githubusercontent.com/97423205/213185126-42f2ef57-e8db-4b1b-8578-6b24bba97345.gif)
<br/>

### 2. 목록
OkHttp와 Retrofit을 이용하여 서버와 통신합니다.<br/>
Recylerview를 사용한 목록 볼 수 있습니다.

![bunjang_list](https://user-images.githubusercontent.com/97423205/213184676-b0eabd53-1b0e-4476-8e27-d54719deec16.gif)
<br/>

### 3. 결제
BottomSheetDialog를 이용하여 주소를 선택할 수 있습니다.<br/>
TextWatcher를 이용하여 포인트 사용 금액에 따른 총 결제금액이 변합니다.

![bunjang_buy](https://user-images.githubusercontent.com/97423205/213187910-e17da436-379f-4e92-991d-f0229f1f6639.gif)
<br/>

### 4. 판매 등록
갤러리 권한 확인을 확인합니다.<br/>
등록한 이미지는 FirebaseStorage에 저장되어 해당 주소를 서버에 저장합니다.

![bunjang_register](https://user-images.githubusercontent.com/97423205/213190754-15d2fce3-6b8a-46b8-b954-bf4ad6bdb841.gif)

