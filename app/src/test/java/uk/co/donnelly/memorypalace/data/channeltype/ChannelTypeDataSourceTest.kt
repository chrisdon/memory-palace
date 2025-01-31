package uk.co.donnelly.memorypalace.data.channeltype

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

class ChannelTypeDataSourceTest {
    private val dao: ChannelTypeDao = mock()
    private val mapper = ChannelTypeMapperImpl()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val sut = ChannelTypeDataSourceImpl(
        channelTypeDao = dao,
        dispatcher = UnconfinedTestDispatcher(),
        channelTypeEntityMapper = mapper
    )

    @Test
    fun testSaveChannelType() = runTest {
        sut.saveChannelType(channelTypeDomainFrench)
        verify(dao).insertOrUpdate(channelTypeDataFrench)
    }

    @Test
    fun testDeleteChannelType() = runTest {
        sut.deleteChannelType(channelTypeDomainFrench)
        verify(dao).deleteChannelType(channelTypeDataFrench)
    }

    @Test
    fun testGetChannelTypes() = runTest {
        channelTypeDataSpanish.id = 2
        whenever(dao.getSavedChannelTypes()).thenReturn(
            flowOf(
                listOf(channelTypeDataFrench, channelTypeDataSpanish)
            )
        )

        sut.getChannelTypes().collect { palaces ->
            assertEquals(listOf(channelTypeDomainFrench, channelTypeDomainSpanish), palaces)
        }
    }

    @Test
    fun testGetChannelType() = runTest {
        whenever(dao.getSavedChannelType(any())).thenReturn(flowOf(channelTypeDataFrench))
        sut.getChannelType(0).collect { palace ->
            assertEquals(channelTypeDomainFrench, palace)
        }
    }
}