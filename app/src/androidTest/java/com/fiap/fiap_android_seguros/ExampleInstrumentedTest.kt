package com.fiap.fiap_android_seguros

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.fiap.fiap_android_seguros.application.usecases.GetUserLoggedUseCase
import com.fiap.fiap_android_seguros.application.usecases.LoginUseCase
import com.fiap.fiap_android_seguros.application.viewmodels.UserLoginRequest
import com.fiap.fiap_android_seguros.data.remote.datasource.UserRemoteFirebaseDataSourceImpl
import com.fiap.fiap_android_seguros.data.repositories.UserRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext

        val userCaseLogin = LoginUseCase(
            UserRepositoryImpl(
                UserRemoteFirebaseDataSourceImpl(
                    FirebaseAuth.getInstance(),
                    FirebaseFirestore.getInstance()
                )
            )
        )
//        runBlocking {
//            val Login = userCaseLogin
//                .doLogin(
//                    UserLoginRequest("ayton.tt@teste.com", "1234567")
//                )
//            print(Login.)
//        }

    }
}