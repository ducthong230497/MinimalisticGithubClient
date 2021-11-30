package com.github.minimalisticclient.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.minimalisticclient.data.database.daos.RepoDao
import com.github.minimalisticclient.data.database.daos.UserDao
import junit.framework.TestCase
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppDatabaseTest : TestCase() {

    private lateinit var context: Context
    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao
    private lateinit var repoDao: RepoDao

    @Before
    public override fun setUp() {
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).allowMainThreadQueries().build()
        userDao = db.userDao()
        repoDao = db.repoDao()
    }

    @After
    public override fun tearDown() {
        db.close()
    }

    @Test
    fun testInsertUser() = runBlocking {
        val user = UserEntity(1, "duc_thong", "avatar", 10, 10, 10, 10)
        userDao.insert(user)
        val getUser = userDao.getUser("duc_thong").firstOrNull()
        Assert.assertEquals(user, getUser)
    }

    @Test
    fun testInsertRepos() = runBlocking {
        val user = UserEntity(1, "duc_thong", "avatar", 10, 10, 10, 10)
        userDao.insert(user)
        val repos = listOf(
            RepoEntity(1, "duc_thong", "Repo1", "Description1", "Eng"),
            RepoEntity(2, "duc_thong", "Repo2", "Description2", "Eng"),
            RepoEntity(3, "duc_thong", "Repo3", "Description3", "Eng"),
            RepoEntity(4, "duc_thong", "Repo4", "Description4", "Eng"),
            RepoEntity(5, "duc_thong", "Repo5", "Description5", "Eng"),
            RepoEntity(6, "duc_thong", "Repo6", "Description6", "Eng"),
            RepoEntity(7, "duc_thong", "Repo7", "Description7", "Eng"),
            RepoEntity(8, "duc_thong", "Repo8", "Description8", "Eng"),
            RepoEntity(9, "duc_thong", "Repo9", "Description9", "Eng"),
        )
        repoDao.insertAll(repos)

        val getRepos = repoDao.getUserRepos("duc_thong").firstOrNull()

        Assert.assertEquals(repos, getRepos)
    }
}
