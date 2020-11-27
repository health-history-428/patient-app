package com.jacobgb24.healthhistory

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jacobgb24.healthhistory.api.MockApi
import com.jacobgb24.healthhistory.viewmodels.RegistrationViewModel
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
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
        viewModel = RegistrationViewModel(MockApi(), Dispatchers.Default)
        bindObserver(viewModel.passwordConfirmError, viewModel.passwordConfirm,
            viewModel.password, viewModel.email, viewModel.emailError)
    }

    @Test
    fun `test invalid email causes email error to populate`() {
        viewModel.email.value = "bademail"
        viewModel.emailError.assertValue { it?.isNotEmpty() }

        viewModel.email.value = "a@b.c"
        viewModel.emailError.assertValue { it == null }
    }

    @Test
    fun `test non-matching password confirm causes error to populate`() {
        viewModel.password.value = "password"
        viewModel.passwordConfirm.value = "password"
        viewModel.passwordConfirmError.assertValue { it == null }

        viewModel.password.value = "different"
        viewModel.passwordConfirmError.assertValue {it?.isNotEmpty() }
    }

    @Test
    fun `test that allValid is true iff all fields are valid`() {

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