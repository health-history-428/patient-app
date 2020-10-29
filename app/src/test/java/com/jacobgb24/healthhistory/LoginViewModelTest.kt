package com.jacobgb24.healthhistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.core.app.ApplicationProvider
import com.jacobgb24.healthhistory.viewmodels.LoginViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class LoginViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: LoginViewModel

    @Before
    fun setUp() {
        viewModel = LoginViewModel(ApplicationProvider.getApplicationContext(), MockApi(), Dispatchers.Default)
        bindObserver(viewModel.password, viewModel.email, viewModel.emailError)
    }

    @Test
    fun `test invalid email causes email error to populate`() {
        viewModel.email.value = "bademail"
        viewModel.emailError.assertValue { it?.isNotEmpty() }

        viewModel.email.value = "a@b.c"
        viewModel.emailError.assertValue { it == null }
    }

    @Test
    fun  `test that allValid is true iff all fields are valid`() {
        viewModel.allValid.assertValue { !it }

        viewModel.email.value = "a@b.c"
        viewModel.password.value = "password"
        viewModel.allValid.assertValue { it }

        viewModel.password.value = ""
        viewModel.allValid.assertValue { !it }
        viewModel.password.value = "something"
        viewModel.allValid.assertValue { it }

        viewModel.email.value = "bademail"
        viewModel.allValid.assertValue { !it }
    }
}