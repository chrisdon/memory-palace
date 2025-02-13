package uk.co.donnelly.memorypalace.data.palace

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.kotlin.any
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import uk.co.donnelly.memorypalace.data.common.ImageUtilFake

class PalaceDataSourceTest {
    private val dao: PalaceDao = mock()
    private val mapper = PalaceMapperImpl()
    private val imageUtil = ImageUtilFake()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val sut = PalaceDataSourceImpl(
        palaceDao = dao,
        dispatcher = UnconfinedTestDispatcher(),
        palaceEntityMapper = mapper,
        imageUtil = imageUtil
    )

    @Test
    fun testSavePalace() = runTest {
        sut.savePalace(palaceDomain)
        verify(dao).insertOrUpdate(palaceData1)
    }

    @Test
    fun testUpdatePalace() = runTest {
        sut.updatePalace(palaceDomain)
        verify(dao).updatePalace(palaceData1)
    }

    @Test
    fun testDeletePalace() = runTest {
        sut.deletePalace(palaceDomain)
        verify(dao).deletePalace(palaceData1)
    }

    @Test
    fun testGetPalaces() = runTest {
        whenever(dao.getSavedPalaces()).thenReturn(flowOf(listOf(palaceData1, palaceData2)))
        sut.getPalaces().collect { palaces ->
            assertEquals(listOf(palaceDomainId0, palaceDomain2), palaces)
        }
    }

    @Test
    fun testGetPalace() = runTest {
        whenever(dao.getSavedPalace(any())).thenReturn(flowOf(palaceData1))
        sut.getPalace(0).collect { palace ->
            assertEquals(palaceDomainId0, palace)
        }
    }

    @Test
    fun testGetPalaceNull() = runTest {
        whenever(dao.getSavedPalace(any())).thenReturn(flowOf(null))
        sut.getPalace(0).collect { palace ->
            assertEquals(null, palace)
        }
    }
}