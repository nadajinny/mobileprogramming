package com.mb.traveltogether.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mb.traveltogether.model.Plan

class PlanRepository {
    private val db = FirebaseFirestore.getInstance() //firestore 객체 초기화

    //계획 추가 함수 addPlan
    fun addPlan(tripId: String, plan: Plan, onSuccess:()->Unit, onFailure:(Exception)->Unit){
        val planData=hashMapOf( //계획 데이터 생성
            "time" to plan.time,
            "location" to plan.location,
            "description" to plan.description,
            "lastUpdateBy" to plan.lastUpdateBy,
            "lastUpdateAt" to Timestamp.now()
        )
        db.collection("trips").document(tripId) //여행 데이터 접근
            .collection("plans") //계획 데이터 접근
            .add(planData) //계획 추가
            .addOnSuccessListener { onSuccess() } //성공 콜백
            .addOnFailureListener{e->onFailure(e)} //실패 콜백
    }

    //계획 삭제 함수 deletePlan
    fun deletePlan(tripId: String, planId:String, onSuccess:()->Unit, onFailure: (Exception) -> Unit){
        db.collection("trips").document(tripId) //여행 데이터 접근
            .collection("plans").document(planId) //계획 데이터 접근
            .delete() //데이터 삭제
            .addOnSuccessListener { onSuccess() } //성공 콜백
            .addOnFailureListener{e->onFailure(e)} //실패 콜백
    }

    //계획 조회 함수 getPlans
    fun getPlans(tripId: String, callback: (List<Plan>)->Unit, onFailure: (Exception)->Unit){
        db.collection("trips").document(tripId) //여행 데이터 접근
            .collection("plans") //plans collection 접근
            .orderBy("time", Query.Direction.ASCENDING)//시간순 정렬
            .get() //데이터 get
            .addOnSuccessListener {
                result -> val plans = result.documents.mapNotNull { it.toObject(Plan::class.java) }
                //toIbject->Plan객체로 변환, mapNotNull->변환 결과로 리스트 생성, null 포함X
                callback(plans) //성공시 plans 리스트 return
            }
            .addOnFailureListener{e->onFailure(e)} //실패 콜백
    }
}