package com.jacobgb24.healthhistory.api

import com.jacobgb24.healthhistory.model.*
import com.jacobgb24.healthhistory.quickLog
import kotlinx.coroutines.delay
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.HttpException
import retrofit2.Response
import java.util.*

/**
 * A mock API useful for testing. This can be set in the dev menu on the login page
 */
class MockApi : ApiInterface {

    @Volatile private var shares = mutableMapOf<String, Share>()

    /**
     * Helpful function for creating an exception like what the real server produces
     */
    private inline fun <reified T> createError(err: String, code: Int = 400): HttpException {
        throw HttpException(
            Response.error<T>(
                code,
                "{\"error\":[\"${err}\"]}".toResponseBody("application/json".toMediaTypeOrNull())
            )
        )
    }


    override suspend fun loginUser(obj: ApiInterface.LoginReq): User {
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User(1, 1, "login-mock@api.com")
    }

    override suspend fun registerUser(obj: ApiInterface.RegisterReq): User {
        print("register called")
        if (obj.email.contains("bad"))
            throw createError<User>("Invalid Email")
        return User(2, 2, "register-mock@api.com")
    }

    override suspend fun getInsurance(): Insurance {
        return Insurance(
            "Real Insurance Co.", "12345678",
            "Doe", "John",
            Calendar.getInstance().time, Calendar.getInstance().time,
            "876554321", "Joe Smith",
            "Real Company Ltd.", null,
            Address("0 N 0 E", "", "Provo", "UT", "84606")
        )
    }

    override suspend fun updateInsurance(insurance: Insurance): Insurance {
        delay(1000)
        return insurance
    }

    override suspend fun getPatientInfo(): PatientInfo {
        // artificial delay to test loading icon
        delay(1000)
        return PatientInfo(
            Calendar.getInstance().time, "6'3\"", "male",
            mutableListOf("Pollen", "Air"), mutableListOf(),
            mutableListOf("Appendix Removal"), mutableListOf(), mutableListOf()
        )
    }

    override suspend fun updatePatientInfo(info: PatientInfo): PatientInfo {
        delay(1000)
        return info
    }

    override suspend fun getContact(): Contact {
        return Contact("1", null,
            "(000) 123-4567", "John Doe"
        )
    }

    override suspend fun updateContact(contact: Contact): Contact {
        delay(1000)
        return contact
    }

    override suspend fun getAddress(id: String): Address {
        // random zip code to show refresh occurred
        return  Address("0 N 0 E", "", "Provo", "UT", (0..99999).random().toString().padStart(5, '0'))
    }

    override suspend fun getAccount(id: String): Account {
        return Account(owner_id = "1")
    }

    override suspend fun getUser(id: String): User {
        return User(email = "${(0..100).random()}@mail.com")
    }

    override suspend fun getAllShares(): Map<String, Share> {
        if ((0..4).random() == 1 && shares.size < 5) {
            quickLog("mock adding share")
            val id = UUID.randomUUID().toString()
            shares[id] = Share(viewer_id = "1", status = SharedStatus.REQUESTED, id = id)
        }
        quickLog("mock returning ${shares.size}")
        return shares
    }

    override suspend fun approveShare(shareResponse: ApiInterface.ShareResponse): Share {
        val share = shares.values.first { it.id == shareResponse.share }
        share.status = SharedStatus.APPROVED
        quickLog("mock approved $share")
        return share
    }

    override suspend fun denyShare(shareResponse: ApiInterface.ShareResponse): Share {
        val share = shares.values.first { it.id == shareResponse.share }
        share.status = SharedStatus.DENIED
        quickLog("mock denied $share")
        return share
    }


}