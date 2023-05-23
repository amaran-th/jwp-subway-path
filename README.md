![image](https://user-images.githubusercontent.com/81465068/219571225-27face3f-67dd-4264-85a6-de09b521e7f5.png)

# 우테코 5기 2레벨 5~6주차 - 지하철 미션

![Generic badge](https://img.shields.io/badge/level2-week5~6-green.svg)
![Generic badge](https://img.shields.io/badge/version-1.0.1-brightgreen.svg)

> 우아한테크코스 5기 2레벨 5~6주차 미션, 지하철 미션을 구현한 저장소입니다.


# 목차
- [시작하기](#시작하기)
  - [Git Clone](#git-clone)
  - [DB 활성화하기](#db-활성화하기)
  - [어플리케이션 실행](#어플리케이션-실행)
- [도메인 모델 네이밍 사전](#도메인-모델-네이밍-사전)
- [DB(DAO)](#dbdao)
- [기능 목록](#기능-목록)
  - [1. 노선 관련 기능](#1-노선-관련-기능)
  - [2. 역 관련 기능](#2-역-관련-기능)
  - [3. 경로 관련 기능](#3-경로-관련-기능)


## 시작하기
### Git Clone
해당 레포지토리를 Clone합니다.
```
git clone -b as https://github.com/amaran-th/jwp-subway-path.git
```
### DB 활성화하기
src/main/resources/docker-compose.yml 파일이 있는 위치로 이동합니다.
다음 명령어를 사용해 도커를 실행합니다.
단, 실행 환경에 도커 어플리케이션이 설치되어 있어야 합니다.
```shell
docker-compose -p chess up -d
```
만약 실행 중인 도커 서버를 중단하고 싶다면 다음 명령어를 입력하면 됩니다.
```
docker-compose -p chess down
```
### 어플리케이션 실행
MySQL 환경이 셋팅된 docker 서버가 실행 중인 상태에서 src/main/java/subway에 위치한 SubwayApplication.java 파일을 실행시켜 프로그램을 동작시킬 수 있습니다.

## 도메인 모델 네이밍 사전
| 한글명 | 영문명      | 설명                 | 분류    |
|-----|----------|--------------------|-------|
| 역   | Station  | 지하철 역              | class |
| 노선  | Line     | 구간의 모음             | class |
| 거리  | Distance | 역 간 거리 또는 경로의 총 거리 | class |
| 요금  | Charge   | 특정 경로에 대한 운임 요금    | class |

## DB(DAO)

- 프로덕션 환경은 MySQL, 테스트 환경은 In-Memory DB(H2)를 사용하였다.
- DB 테이블 설계
- 테이블 이름 - STATION

  | id | name | next | distance | line_id |
  | ---- | ---- |------|----------| -------- |
  | 1L | 노포역 | 2L | 10 | 1L |
  | 2L | 화정역 | 3L | 5 | 1L |
  | 3L | 잠실역 | 0L | null | 1L |
  | 4L | 화정역 | 5L | 8 | 2L |


- 테이블 이름 - LINE

  | id | name | color | head_station |
  | --- | --- |-------| ------------ |
  | 1L | 1호선 | 파란색 | 1L |
  | 2L | 2호선 | 초록색 | 4L |


## 기능 목록
### 1. 노선 관련 기능

- 노선의 목록 조회
    
    ```java
    GET /lines
    ```
    
    ```java
    ResponseBody = [
            {
                    id : 1,
                    name : "동해선",
                    color : "파란색",
            },{
                    id : 2,
                    name : "수인분당선",
                    color : "노란색",
                    
            },{
                    id : 3,
                    name : "우이신설선",
                    color : "연두색",
            }
    ]
    ```
    
- 노선 조회
    
    ```java
    GET /lines/{lineId}
    ```
    
    ```java
    ResponseBody = {
        id : 1,
        name : "동해선",
        color : "파란색",
    }
    ```
    
- 노선 추가
    
    ```java
    POST /lines
      RequestBody = {
          name:"1호선",
          color:"주황색",
          upStation:"강남역",
          downStation:"역삼역",
          distance:10,
      }
    ```
    
    ```java
    ResponseBody = {
        id : 1,
      }
    ```
    
- 노선 제거
    
    ```java
    DELETE /lines/{lineId}
    ```
    
    ```java
    noContent
    ```
    

### 2. 역 관련 기능

- 역 목록 조회
    
    ```java
    GET /lines/{line_id}/stations
    ```
    
    ```java
    ResponseBody = [
            {
                    id : 1,
                    name : "노포역",
                    
            },{
                    id : 2,
                    name : "범어사역",
                    
            },{
                    id : 3,
                    name:"남산역",
            }
    ]
    ```
    
- 역 추가
    
    ```java
    POST /lines/{line_id}/stations
      
      RequestBody = {
          upStation : "노포역",
          downStation : "강남역",
          distance : 10,
      }
    ```
    
    ```java
    ResponseBody = {
        upStationId : 1,
        downStationId : 2,
      }
    ```
    
- 역 제거
    
    ```java
    DELETE /lines/{line_id}/stations
    
    RequestBody = {
        name : "역삼역"
    }
    ```
    
    ```java
    ResponseBody = {
        id : 2,
    }
    ```
    

### 3. 경로 관련 기능

- 출발역과 도착역 간 최단 경로 조회
    
    ```java
    GET /path
      
      RequestBody = {
        startStation: "성수역",
        endStation: "건대입구역"
      }
    ```
    
    ```java
    ResponseBody = {
        path: ["성수역", "뚝섬역", "잠실역", "건대입구역"],
        distance: 26,
        charge: 1650,
      }
    ```
