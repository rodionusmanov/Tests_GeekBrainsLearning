package com.example.tests_geekbrainslearning.automator_lesson05

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SdkSuppress
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.uiautomator.By
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.Until
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import com.example.tests_geekbrainslearning.R

@RunWith(AndroidJUnit4::class)
@SdkSuppress(minSdkVersion = 18)
class BehaviorTest {

    private val uiDevice: UiDevice = UiDevice.getInstance(getInstrumentation())
    private val context = ApplicationProvider.getApplicationContext<Context>()
    private val packageName = context.packageName

    @Before
    fun setup() {
        uiDevice.pressHome()
        val intent = context.packageManager.getLaunchIntentForPackage(packageName)
        intent!!.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
        context.startActivity(intent)
        uiDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), TIMEOUT)
    }

    companion object {
        private const val TIMEOUT = 5000L
    }

    @Test
    fun test_MainActivityIsStarted() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        Assert.assertNotNull(editText)
    }

    @Test
    fun test_Espresso_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.pressImeActionButton())
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "Number of results: 774")
    }

    @Test
    fun test_SearchIsPositive() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedText =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        Assert.assertEquals(changedText.text.toString(), "Number of results: 774")
    }

    @Test
    fun test_OpenDetailsScreen() {

        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        val changedText =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedText.text, "Number of results: 0")
    }

    @Test
    fun test_OpenDetailsScreenAndCheckCorrectCount() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "UiAutomator"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedTextActivityMain =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val toDetails = uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.findObject(By.res(packageName, "totalCountTextView"))
        val changedTextDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedTextDetailsActivity.text, "Number of results: 774")
    }

    @Test
    fun test_EditTextBlankSearch_NullTextView() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = ""
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        Assert.assertNull(
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        )
    }

    @Test
    fun test_EditTextNotBlankSearch_NotNullTextView() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "any search"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        Assert.assertNotNull(
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        )
    }

    @Test
    fun test_IncrementClickPositiveTest() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "algol"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedTextActivityMain =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val toDetails =
            uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.findObject(By.res(packageName, "incrementButton"))
        val incrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "incrementButton")), TIMEOUT)
        incrementButton.click()
        val changedTextDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedTextDetailsActivity.text, "Number of results: 3631")
    }

    @Test
    fun test_IncrementClickNegativeTest() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "algol"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedTextActivityMain =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val toDetails =
            uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.findObject(By.res(packageName, "incrementButton"))
        val incrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "incrementButton")), TIMEOUT)
        incrementButton.click()
        val changedTextDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertNotEquals(changedTextDetailsActivity.text, "Number of results: 3630")
    }

    @Test
    fun test_DecrementClickPositiveTest() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "algol"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedTextActivityMain =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val toDetails =
            uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.findObject(By.res(packageName, "decrementButton"))
        val incrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "decrementButton")), TIMEOUT)
        incrementButton.click()
        val changedTextDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertEquals(changedTextDetailsActivity.text, "Number of results: 3629")
    }

    @Test
    fun test_DecrementClickNegativeTest() {

        val editText = uiDevice.findObject(By.res(packageName, "searchEditText"))
        editText.text = "algol"
        val totalCount = uiDevice.findObject(By.res(packageName, "searchTotalCountButton"))
        totalCount.click()
        val changedTextActivityMain =
            uiDevice.wait(
                Until.findObject(By.res(packageName, "totalCountTextView")),
                TIMEOUT
            )
        val toDetails =
            uiDevice.findObject(By.res(packageName, "toDetailsActivityButton"))
        toDetails.click()
        uiDevice.findObject(By.res(packageName, "decrementButton"))
        val incrementButton =
            uiDevice.wait(Until.findObject(By.res(packageName, "decrementButton")), TIMEOUT)
        incrementButton.click()
        val changedTextDetailsActivity =
            uiDevice.wait(Until.findObject(By.res(packageName, "totalCountTextView")), TIMEOUT)
        Assert.assertNotEquals(changedTextDetailsActivity.text, "Number of results: 3630")
    }
}