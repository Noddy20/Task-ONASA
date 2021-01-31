package com.onasa.pictures.home

import androidx.test.core.app.ApplicationProvider
import com.onasa.pictures.rules.TestCoroutineRule
import com.onasa.pictures.uiModules.home.RepoHome
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepoHomeUnitTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    private val mRepoHome = RepoHome(ApplicationProvider.getApplicationContext())

    @Test
    fun addition_isCorrect() {
        assertEquals(4, 2 + 2)
    }

    @Test
    fun addition_isCorrectB() {
        assertEquals(4, 2 + 2)
        assertEquals(3, 1 + 1) // This should fail
    }


    @Test
    fun parse_dataJsonFile_shouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            val list = mRepoHome.parsePicturesJson("")
            assertNotNull(list)
        }
    }


}