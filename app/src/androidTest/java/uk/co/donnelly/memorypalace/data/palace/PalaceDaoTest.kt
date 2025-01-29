package uk.co.donnelly.memorypalace.data.palace

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import uk.co.donnelly.memorypalace.data.db.PalaceDatabase
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PalaceDaoTest {
    private lateinit var dao: PalaceDao
    private lateinit var db: PalaceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PalaceDatabase::class.java).build()
        dao = db.palaceDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWrite() = runBlocking {
        dao.insertOrUpdate(palace1)
        val channelType = dao.getSavedPalace(1L).first()
        assertEquals(palace1, channelType)
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWriteExisting() = runBlocking {
        dao.insertOrUpdate(palace1)
        val palaceNew = palace1Amended
        palaceNew.id = 1L
        dao.insertOrUpdate(palaceNew)
        val savedPalace = dao.getSavedPalace(1L).first()
        assertEquals("Addington", savedPalace?.name)
    }

    @Test
    @Throws(Exception::class)
    fun testMultipleInserts() = runBlocking {
        dao.insertOrUpdate(palace1)
        dao.insertOrUpdate(palace1Amended)
        val list = dao.getSavedPalaces()
        assertEquals(2, list.first().size)
        assertEquals("Addiscombe", list.first()[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun testDelete() = runBlocking {
        dao.insertOrUpdate(palace1)
        dao.insertOrUpdate(palace1Amended)
        val savedPalace = dao.getSavedPalace(1L).first()
        if (savedPalace != null) {
            dao.deletePalace(savedPalace)
        }
        val list = dao.getSavedPalaces()
        assertEquals(1, list.first().size)
        assertEquals("Addington", list.first()[0].name)
    }
}