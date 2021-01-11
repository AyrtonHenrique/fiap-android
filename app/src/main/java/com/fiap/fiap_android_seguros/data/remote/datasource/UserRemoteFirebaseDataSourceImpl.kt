package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.NewUserRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.RequestState
import com.fiap.fiap_android_seguros.data.remote.UserRemoteResponse
import com.fiap.fiap_android_seguros.data.remote.UserLoginRemoteRequest
import com.fiap.fiap_android_seguros.data.remote.exceptions.UserNotLoggedException
import com.fiap.fiap_android_seguros.data.remote.mapper.NewUserFirebasePayloadMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class UserRemoteFirebaseDataSourceImpl (
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<UserRemoteResponse> {
        return try {
            firebaseAuth.currentUser?.reload()
            val firebaseUser = firebaseAuth.currentUser
            val user = UserRemoteResponse(firebaseUser?.displayName ?: "")

            if (firebaseUser == null)
                RequestState.Error(UserNotLoggedException())
            else
                RequestState.Success(user)

        } catch (e: Exception) {
            RequestState.Error(e)
        }

    }

    override suspend fun doLogin(userLogin: UserLoginRemoteRequest): RequestState<UserRemoteResponse> {
        return try {
            firebaseAuth.signInWithEmailAndPassword(
                userLogin.email,
                userLogin.password
            ).await()

            val firebaseUser = firebaseAuth.currentUser
            val user = UserRemoteResponse(firebaseUser?.displayName ?: "")

            if (firebaseUser == null) {
                RequestState.Error(UserNotLoggedException())
            } else {
                RequestState.Success(user)
            }

        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun create(newUser: NewUserRemoteRequest): RequestState<UserRemoteResponse> {
        return try {
            firebaseAuth.createUserWithEmailAndPassword(newUser.email, newUser.password).await()

            val newUserFirebasePayload =
                NewUserFirebasePayloadMapper.mapToNewUserFirebasePayload(newUser)

            val userId = firebaseAuth.currentUser?.uid
            if (userId == null) {
                RequestState.Error(java.lang.Exception("Não foi possível criar a conta"))
            } else {
                firebaseFirestore
                    .collection("users")
                    .document(userId)
                    .set(newUserFirebasePayload)
                    .await()
                RequestState.Success(NewUserFirebasePayloadMapper.mapToUser(newUserFirebasePayload))
            }
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }
}