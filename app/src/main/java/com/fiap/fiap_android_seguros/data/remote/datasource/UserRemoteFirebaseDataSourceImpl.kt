package com.fiap.fiap_android_seguros.data.remote.datasource

import com.fiap.fiap_android_seguros.data.remote.*
import com.fiap.fiap_android_seguros.data.remote.exceptions.UserNotLoggedException
import com.fiap.fiap_android_seguros.data.remote.mapper.NewUserFirebasePayloadMapper
import com.fiap.fiap_android_seguros.domain.entity.Conversa
import com.fiap.fiap_android_seguros.domain.entity.Mensagem
import com.fiap.fiap_android_seguros.domain.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import java.util.*

class UserRemoteFirebaseDataSourceImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseFirestore: FirebaseFirestore
) : UserRemoteDataSource {
    override suspend fun getUserLogged(): RequestState<User> {
        return try {
            firebaseAuth.currentUser?.reload()
            val firebaseUser = firebaseAuth.currentUser

            if (firebaseUser == null)
                RequestState.Error(UserNotLoggedException())
            else {
                val userId = firebaseAuth.currentUser?.uid
                var userRemoteInfo = firebaseFirestore
                    .collection("users")
                    .document(userId ?: "")
                    .get()
                    .await()
                    .toObject(NewUserFirebasePayload::class.java)

                if (userRemoteInfo !== null) {
                    val user = NewUserFirebasePayloadMapper.mapToNewUser(userRemoteInfo)
                    RequestState.Success(user)
                } else {
                    RequestState.Error(java.lang.Exception("Não foi possível encontrar os dados do usuario"))
                }

            }

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

    override suspend fun sendMessage(
        message: Mensagem,
        conversation: String?
    ): RequestState<Void?> {
        return try {

            firebaseFirestore
                .collection("conversations")
                .document()
                .set(
                    ConversationRequest(
                        conversation ?: "",
                        arrayListOf(
                            MessageRequest(
                                message.enviadoPeloCorretor,
                                message.textoMensagem,
                                firebaseAuth.currentUser?.uid
                            )
                        ),
                        message.idCorretor,
                        message.IdCliente,
                        message.nomeCliente,
                        message.nomeCorretor
                    )
                )
            RequestState.Success(null)
        } catch (e: java.lang.Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getUsersCorretor(): RequestState<Array<User>> {

        val response = firebaseFirestore
            .collection("users")
            .whereEqualTo("corretor", true)
            .get()
            .await()

        val corretoresDisponiveis = response.map {
            it.toObject(NewUserFirebasePayload::class.java)
        }

        return RequestState.Success(corretoresDisponiveis.map {
            NewUserFirebasePayloadMapper.mapToNewUser(it)
        }.toTypedArray())
    }

    override suspend fun getAllConversation(): RequestState<List<Conversa>> {
        return try {
            val response = firebaseFirestore
                .collection("conversations")
                .get()
                .await()
            val conversas = response.map {
                val obj = it.toObject(ConversationRequest::class.java)
                Conversa(
                    obj.id ?: "",
                    obj.messages?.map {
                        Mensagem(
                            obj.idCliente ?: "",
                            it.textoMensagem ?: "",
                            obj.idCorretor ?: "",
                            it.corretor ?: false,
                            obj.nomeCorretor ?: "",
                            obj.nomeCliente ?: ""
                        )
                    } ?: emptyList(),
                    obj.idCorretor ?: "",
                    obj.idCliente ?: "",
                    obj.nomeCliente ?: "",
                    obj.nomeCorretor ?: ""
                )
            }

            RequestState.Success(conversas)
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }

    override suspend fun getUserById(id: String): RequestState<User> {
        return try {
            var userRemoteInfo = firebaseFirestore
                .collection("users")
                .document(id)
                .get()
                .await()
                .toObject(NewUserFirebasePayload::class.java)

            if (userRemoteInfo !== null) {
                val user = NewUserFirebasePayloadMapper.mapToNewUser(userRemoteInfo)
                RequestState.Success(user)
            } else {
                RequestState.Error(java.lang.Exception("Não foi possível encontrar os dados do usuario"))
            }
        } catch (e: Exception) {
            RequestState.Error(e)
        }
    }
}