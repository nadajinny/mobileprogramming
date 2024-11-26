package com.mb.traveltogether.repository

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.mb.traveltogether.model.Trip

class TripRepository {
    private val db = FirebaseFirestore.getInstance()

    fun createTrip(tripId: String, trip: Trip, onSuccess: ()->Unit, onFailure: (Exception)->Unit){
        val tripData = hashMapOf(
            "title" to trip.title,
            "creatorId" to trip.creatorId,
            "isPublic" to trip.isPublic,
            "members" to trip.members,
            "createdAt" to Timestamp.now()
        )
        db.collection("trips").document(tripId)
            .set(tripData)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener{e->onFailure(e)}
    }

    fun deleteTrip(tripId: String, onSuccess: ()->Unit, onFilure:(Exception)->Unit){
        db.collection("trips").document(tripId)
            .delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener{e->onFilure(e)}
    }
}
