package com.jacobgb24.healthhistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import org.junit.Test

import org.junit.Before
import org.junit.Rule
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest= Config.NONE)
class RegistrationViewModelTest {

    @get:Rule val instantTaskExecutorRule = InstantTaskExecutorRule()
    private lateinit var viewModel: RegistrationViewModel

    @Before
    fun setUp() {
        viewModel = RegistrationViewModel()
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
}