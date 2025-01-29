package uk.co.donnelly.memorypalace.data.channeltype

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
class ChannelTypeDaoTest {
    private lateinit var dao: ChannelTypeDao
    private lateinit var db: PalaceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PalaceDatabase::class.java).build()
        dao = db.channelTypeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWrite() = runBlocking {
        dao.insertOrUpdate(channelTypeFrench)
        val channelType = dao.getSavedChannelType(1L).first()
        assertEquals(channelTypeFrench, channelType)
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWriteExisting() = runBlocking {
        dao.insertOrUpdate(channelTypeFrench)
        channelTypeSpanish.id = 1L
        dao.insertOrUpdate(channelTypeSpanish)
        val channelType2 = dao.getSavedChannelType(1L).first()
        assertEquals("Spanish", channelType2.name)
    }

    @Test
    @Throws(Exception::class)
    fun testMultipleInserts() = runBlocking {
        dao.insertOrUpdate(channelTypeFrench)
        dao.insertOrUpdate(channelTypeSpanish)
        val list = dao.getSavedChannelTypes()
        assertEquals(2, list.first().size)
        assertEquals("Spanish", list.first()[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun testDelete() = runBlocking {
        dao.insertOrUpdate(channelTypeFrench)
        dao.insertOrUpdate(channelTypeSpanish)
        val channelType = dao.getSavedChannelType(1L).first()
        dao.deleteChannelType(channelType)
        val list = dao.getSavedChannelTypes()
        assertEquals(1, list.first().size)
        assertEquals("Spanish", list.first()[0].name)
    }
}