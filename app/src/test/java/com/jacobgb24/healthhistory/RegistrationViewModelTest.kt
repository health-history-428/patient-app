package com.jacobgb24.healthhistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import com.jacobgb24.healthhistory.api.Resource
import com.jacobgb24.healthhistory.model.User
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@ExperimentalCoroutinesApi
@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class RegistrationViewModelTest {

    private val dispatcher = TestCoroutineDispatcher()


    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        viewModel = RegistrationViewModel(MockApi(), dispatcher)
        bindObserver(viewModel.passwordConfirmError, viewModel.passwordConfirm,
            viewModel.password, viewModel.email, viewModel.emailError)
    }

    @Test
    fun testEmailError() {
        viewModel.email.value = "bademail"
        viewModel.emailError.assertValue { it?.isNotEmpty() }

        viewModel.email.value = "a@b.c"
        viewModel.emailError.assertValue { it == null }
    }

    @Test
    fun testPasswordConfirmError() {
        viewModel.password.value = "password"
        viewModel.passwordConfirm.value = "password"
        viewModel.passwordConfirmError.assertValue { it == null }

        viewModel.password.value = "different"
        viewModel.passwordConfirmError.assertValue {it?.isNotEmpty() }
    }

    @Test
    fun testAllValid() {

        viewModel.allValid.assertValue { !it }

        viewModel.email.value = "a@b.c"
        viewModel.password.value = "password"
        viewModel.passwordConfirm.value = "password"
        viewModel.allValid.assertValue { it }

        viewModel.passwordConfirm.value = "password2"
        viewModel.passwordConfirmError.assertValue { it?.isNotEmpty() }
        viewModel.allValid.assertValue { !it }
        viewModel.passwordConfirm.value = "password"
        viewModel.allValid.assertValue { it }

        viewModel.email.value = "bademail"
        viewModel.allValid.assertValue { !it }
    }

//    @Test
//    fun testApiBad() = dispatcher.runBlockingTest {
//        viewModel.email.value = "good@a.b"
//        viewModel.password.value = "password"
//        viewModel.passwordConfirm.value = "password"
//        val res: LiveData<Resource<User>>? = viewModel.tryRegister()
//        print("res ${res!!.value}")
////        assert(User::class.isInstance(res?.data))
////        assert((res?.data as User).id == 1)
//
//    }
}