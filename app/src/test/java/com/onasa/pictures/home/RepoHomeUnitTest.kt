package com.onasa.pictures.home

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.onasa.pictures.rules.TestCoroutineRule
import com.onasa.pictures.uiModules.home.RepoHome
import com.onasa.pictures.uiModules.home.ViewModelHome
import org.json.JSONArray
import org.json.JSONObject
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RepoHomeUnitTest {

    @get:Rule
    val testCoroutineRule = TestCoroutineRule()

    @get:Rule
    val testInstantTaskExecutorRule: TestRule = InstantTaskExecutorRule()

    private lateinit var mRepoHome: RepoHome

    @Before
    fun setupRepo(){
        mRepoHome = RepoHome(mock(Context::class.java))
    }

    /**
     *   Example Tests
     */

    @Test
    fun addition_isCorrect() {
        testCoroutineRule.runBlockingTest {
            assertEquals(4, 2 + 2)
        }
    }

    @Test
    fun addition_isCorrectB() {
        testCoroutineRule.runBlockingTest {
            assertEquals(4, 2 + 2)
            assertEquals(3, 1 + 1) // This should fail
        }
    }

    /**
     *   Example Tests Ends
     */


    // This test will pass as our Picture Model has default values null to it will give 1 object in list with null values
    @Test
    fun parse_dataJsonFile_shouldReturnSuccess(){
        testCoroutineRule.runBlockingTest {
            val list = mRepoHome.parsePicturesJson("[{}]")
            println("Test Running : list -> ${list?.size}")
            assertNotNull(list)
            assertEquals(list?.size?:0, 1)
        }
    }

    // This test will fail because we are not passing a Json Array and also no Json Objects
    @Test
    fun parse_dataJsonFile_shouldReturnError(){
        testCoroutineRule.runBlockingTest {
            val list = mRepoHome.parsePicturesJson("")
            println("Test : list -> ${list?.size}")
            assertNotNull(list)
        }
    }


}