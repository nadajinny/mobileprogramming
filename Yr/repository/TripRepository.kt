package com.mb.traveltogether.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mb.traveltogether.model.Trip

class TripRepository {
    private val db = FirebaseFirestore.getInstance() //firestore 객체 초기화

    //여행 생성 함수 createTrip
    fun createTrip(trip: Trip, onSuccess: (String)->Unit, onFailure: (Exception)->Unit){
        val tripData = hashMapOf(
            "title" to trip.title,
            "creatorId" to trip.creatorId,
            "isPublic" to trip.isPublic,
            "members" to trip.members,
            "createdAt" to Timestamp.now()
        )
        db.collection("trips") //trip 컬렉션 접근
            .add(tripData) //데이터 추가. 자동으로 ID 생성
            .addOnSuccessListener { documentReference ->
                val tripId = documentReference.id //생성된 문서 ID 가져오기
                onSuccess(tripId) //성공시 tripID return
            }
            .addOnFailureListener{e->onFailure(e)} //실패시 실패 콜백
    }

    //여행 삭제 함수 deleteTrip
    fun deleteTrip(tripId: String, onSuccess: ()->Unit, onFailure:(Exception)->Unit){
        db.collection("trips").document(tripId) //여행 데이터 접근
            .delete() //여행 데이터 삭제
            .addOnSuccessListener { onSuccess() } //성공 콜백
            .addOnFailureListener{e->onFailure(e)} //실패 콜백
    }

    //여행 조회 함수 getTrip
    fun getTrip(tripId: String, onSuccess: (Trip?) -> Unit, onFailure: (Exception) -> Unit){
        db.collection("trips").document(tripId) //여행 데이터 접근
            .get() //데이터 get
            .addOnSuccessListener { document ->
                if(document.exists()){ //문서 존재하면
                    val trip = document.toObject(Trip::class.java) //trip객체로 변환
                    onSuccess(trip) //성공시 trip객체 return
                }
                else{ //문서 존재X
                    onSuccess(null) //null return
                }
            }
            .addOnFailureListener{e->onFailure(e)} //실패 콜백
    }
}