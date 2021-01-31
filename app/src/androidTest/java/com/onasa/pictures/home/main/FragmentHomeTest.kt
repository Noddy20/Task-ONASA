package com.onasa.pictures.home.main

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.onasa.pictures.R
import com.onasa.pictures.uiModules.home.ActivityHome
import com.onasa.pictures.uiModules.home.main.AdapterPicturesList
import com.onasa.pictures.utils.MyViewAction
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class FragmentHomeTest {

    @get:Rule
    var activityRule: ActivityScenarioRule<*> = ActivityScenarioRule(ActivityHome::class.java)


    @Test
    fun click_onPicturesItem(){
        // Our RecyclerView loads items after 2 sec of delay so we have used 3 sec of sleep otherwise viewholder not found will be thrown (alternatively we can use registerIdlingResources)
        Thread.sleep(3000)
        onView(withId(R.id.rvHomePictures))
            .perform(RecyclerViewActions.actionOnItemAtPosition<AdapterPicturesList.MyViewHolder>(1, MyViewAction.clickItemWithId(R.id.itemRootView)))
    }

}