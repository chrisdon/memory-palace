package uk.co.donnelly.memorypalace.data.station

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
import uk.co.donnelly.memorypalace.data.channeltype.ChannelTypeDao
import uk.co.donnelly.memorypalace.data.channeltype.channelTypeSpanish
import uk.co.donnelly.memorypalace.data.db.PalaceDatabase
import uk.co.donnelly.memorypalace.data.palace.PalaceDao
import uk.co.donnelly.memorypalace.data.palace.palace1
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class StationDaoTest {
    private lateinit var stationDao: StationDao
    private lateinit var db: PalaceDatabase
    private lateinit var palaceDao: PalaceDao
    private lateinit var channelDao: ChannelTypeDao

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, PalaceDatabase::class.java).build()
        palaceDao = db.palaceDao()
        stationDao = db.stationDao()
        channelDao = db.channelTypeDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWrite() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(station1)
        val station = stationDao.getSavedStation(1L).first()
        assertEquals(station1, station)
    }

    @Test
    @Throws(Exception::class)
    fun testReadAndWriteExisting() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(station1)
        station2.id = 1L
        stationDao.insertOrUpdate(station2)

        val stationAmended = stationDao.getSavedStation(1L).first()
        assertEquals("Hallway", stationAmended?.name)

        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun testMultipleInserts() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(station1)
        stationDao.insertOrUpdate(station2)
        val list = stationDao.getSavedStations(1L)
        assertEquals(2, list.first().size)
        assertEquals("Hallway", list.first()[1].name)
    }

    @Test
    @Throws(Exception::class)
    fun testMultipleInsertsInOneExisting() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(listOf(station1, station2))

        stationDao.insertOrUpdate(updatedStations)
        val listExisting = stationDao.getSavedStations(1L).first()
        assertEquals(2, listExisting.size)
        assertEquals("To leave", listExisting[0].meaning)
    }

    @Test
    @Throws(Exception::class)
    fun testDelete() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(station1)
        stationDao.insertOrUpdate(station2)
        val station = stationDao.getSavedStation(1L).first()
        if (station != null) {
            stationDao.deleteStation(station)
        }
        val list = stationDao.getSavedStations(1L)
        assertEquals(1, list.first().size)
        assertEquals("Hallway", list.first()[0].name)
    }

    @Test
    @Throws(Exception::class)
    fun testInsertsMultiple() = runBlocking {
        setupChannelAndPalace()
        stationDao.insertOrUpdate(listOf(station1, station2))
        val list = stationDao.getSavedStations(1L).first()
        assertEquals(2, list.size)
        assertEquals("Hallway", list[1].name)
    }

    private suspend fun setupChannelAndPalace() {
        channelDao.insertOrUpdate(channelTypeSpanish)
        palaceDao.insertOrUpdate(palace1)
    }
}