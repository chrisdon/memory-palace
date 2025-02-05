package uk.co.donnelly.memorypalace.data.channeltype

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class ChannelTypeRepositoryTest {
    private lateinit var dataSource: ChannelTypeDataSource
    private lateinit var sut: ChannelTypeRepositoryImpl

    @Before
    fun setup() {
        dataSource = ChannelTypeDataSourceFake()
        sut = ChannelTypeRepositoryImpl(dataSource)
    }

    @Test
    fun verifyGetChannels() = runTest {
        val expectedChannels = listOf(channelTypeDomainFrench, channelTypeDomainGerman)
        dataSource.saveChannelType(channelTypeDomainFrench)
        dataSource.saveChannelType(channelTypeDomainGerman)

        sut.getChannelTypes().collect { fetchedChannels ->
            assertEquals(expectedChannels, fetchedChannels)
        }
    }

    @Test
    fun verifyAddChannel() = runTest {
        val expectedChannel = channelTypeDomainFrench

        sut.addChannelType(expectedChannel)

        dataSource.getChannelType(channelTypeDomainFrench.id).collect { savedChannel ->
            assertEquals(expectedChannel, savedChannel)
        }
    }

    @Test
    fun verifyRemoveChannel() = runTest {
        dataSource.saveChannelType(channelTypeDomainFrench)
        dataSource.saveChannelType(channelTypeDomainGerman)

        sut.removeChannelType(channelTypeDomainGerman)

        dataSource.getChannelTypes().collect { channels ->
            assertEquals(1, channels.size)
            assertEquals(0, channels[0].id)
        }
    }

    @Test
    fun verifyGetChannel() = runTest {
        dataSource.saveChannelType(channelTypeDomainFrench)

        sut.getChannelType(channelTypeDomainFrench.id).collect { fetchedChannel ->
            assertEquals(channelTypeDomainFrench, fetchedChannel)
        }
    }
}