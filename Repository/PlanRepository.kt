package com.mb.traveltogether.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.mb.traveltogether.model.Plan

class PlanRepository {
    private val db = FirebaseFirestore.getInstance()

    fun addPlan(tripId: String, plan: Plan, onSuccess:()->Unit, onFailure:(Exception)->Unit){
        val planData=hashMapOf(
            "time" to plan.time,
            "location" to plan.location,
            "description" to plan.description,
            "lastUpdateBy" to plan.lastUpdateBy,
            "lastUpdateAt" to Timestamp.now()
        )
        db.collection("trips").document(tripId)
            .collection("plans").document(plan.planId)
            .set(planData)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{e->onFailure(e)}
    }

    fun deletePlan(tripId: String, planId:String, onSuccess:()->Unit, onFailure: (Exception) -> Unit){
        db.collection("trips").document(tripId)
            .collection("plans").document(planId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{e->onFailure(e)}
    }

    fun getPlans(tripId: String, callback: (List<Plan>)->Unit, onFailure: (Exception)->Unit){
        db.collection("trips").document(tripId)
            .collection("plans")
            .orderBy("time", Query.Direction.ASCENDING)//시간순 정렬
            .get()
            .addOnSuccessListener {
                result -> val plans = result.documents.mapNotNull { it.toObject(Plan::class.java) }
                callback(plans)
            }
            .addOnFailureListener{e->onFailure(e)}
    }
}
