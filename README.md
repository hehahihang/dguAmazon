# 외국인 관광객 대상 WiFi 추천 서비스

**[뱃지나 프로젝트에 관한 이미지들이 이 위치에 들어가면 좋습니다]**

* 외국인들에게 지하철 WiFi 품질정보 분석을 통해, 최적의 WiFi 추천 서비스를 제공합니다.
  - 사용자가 설정한 경로 내에서, 가장 좋은 품질을 가진 ‘최적의 WiFi 분석 결과를 리스트업’ 형태로 제공
  - 와이파이 추천 리스트를 토대로 ‘통신사 추천’


### Data / 활용 데이터

* 활용 데이터는 다음과 같이 한정했습니다.
 - LightGBM Model을 이용한 Wifi Speed, Wifi Success-Rate 예측
  + 역은 2호선 순환선으로 한정함
  + 시간은 1시간 단위로 한정함
  + Score는 Speed와 Success-Rate 간 가중치를 고려한 값 (0 <= Score <= 1)
  
#### Example

|Station|Day of the week|Weather|Hour|SSID|Score|
|:---:|:---:|:---:|:---:|:-------:|:-------:|
|강남|금|맑음|5|KT-Wifi|0.4601|
|강남|금|맑음|5|U-Wifi|0.4648|
|강남|금|맑음|5|T-Wifi|0.4515|
|강남|금|맑음|5|KT-Free|0.3758|
|강남|금|맑음|5|Free-U|0.4645|

## Running the tests / 테스트의 실행
### UML
<p align="center">
  <img src = "https://user-images.githubusercontent.com/37091363/131243173-b4215989-0010-40f5-8e43-ed0048a97572.png" width = "700" height="300"/>
</p>

### Flow Chart
<p align="center">
  <img src = "https://user-images.githubusercontent.com/37091363/131243067-b295e2b2-dc36-43f9-a445-d951b3c92c27.png" width = "1000" height="500"/>
</p>

#### Introduce Screens

|Splash Screen|Initial Screen|Choice Screen|
|:-:|:-:|:-:|
|![Screenshot_1608096161](https://user-images.githubusercontent.com/37091363/131243785-d10ac4f5-f783-49a2-b443-6c2f538477f6.png)|![Screenshot_1608091301](https://user-images.githubusercontent.com/37091363/131243800-a563ae78-2f8f-4ee7-b2d2-9c28dcca6b22.png)|![Screenshot_1608091326](https://user-images.githubusercontent.com/37091363/131244829-c72151d3-0a16-4e8b-84f0-c55a8e7d7cbb.png)|

|Result Screen|Expandable List|Setting Screen|
|:-:|:-:|:-:|
|![Screenshot_1608091394](https://user-images.githubusercontent.com/37091363/131244835-18673711-4f62-4cc1-9468-7e3866dad7b2.png)|![Screenshot_1608091423](https://user-images.githubusercontent.com/37091363/131244838-0c6704a5-5fae-4b8e-9766-714f0f33cad2.png)|![Screenshot_1608091428](https://user-images.githubusercontent.com/37091363/131244843-5f1e80fe-120c-4f91-b5d4-da10abb8d2a7.png)|


### Test Scenario/테스트 시나리오

#### 사당 → 강남 (평일, 맑은 날씨)
|오전 출근시간|낮 시간|
|:-:|:-:|
|![그림1](https://user-images.githubusercontent.com/37091363/131245708-9dd902a2-e75c-408f-9182-87284abec2b2.png)|![그림2](https://user-images.githubusercontent.com/37091363/131245730-fb217598-d5cc-4b2d-9e1d-bc6769eff136.png)|


## Deployment / 배포

Add additional notes about how to deploy this on a live system / 라이브 시스템을 배포하는 방법

## Built With / 누구랑 만들었나요?

* [이름](링크) - 무엇 무엇을 했어요
* [Name](Link) - Create README.md

## Contributiong / 기여

Please read [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) for details on our code of conduct, and the process for submitting pull requests to us. / [CONTRIBUTING.md](https://gist.github.com/PurpleBooth/b24679402957c63ec426) 를 읽고 이에 맞추어 pull request 를 해주세요.

## License / 라이센스

This project is licensed under the MIT License - see the [LICENSE.md](https://gist.github.com/PurpleBooth/LICENSE.md) file for details / 이 프로젝트는 MIT 라이센스로 라이센스가 부여되어 있습니다. 자세한 내용은 LICENSE.md 파일을 참고하세요.

## Acknowledgments / 감사의 말

* Hat tip to anyone whose code was used / 코드를 사용한 모든 사용자들에게 팁
* Inspiration / 영감
* etc / 기타
