package uk.co.donnelly.memorypalace.data.channeltype

import junit.framework.TestCase.assertEquals
import org.junit.Test

class ChannelTypeMapperTest {
    private val sut = ChannelTypeMapperImpl()

    @Test
    fun testToDomain() {
        val domainEntity = sut.toChannelType(channelTypeDataFrench)
        assertEquals(channelTypeDomainFrench, domainEntity)
    }

    @Test
    fun testToData() {
        val dataEntity = sut.toChannelTypeEntity(channelTypeDomainFrench)
        assertEquals(channelTypeDataFrench, dataEntity)
    }

    @Test
    fun testToDataNoId() {
        val dataEntity = sut.toChannelTypeEntity(channelTypeDomainSpanish)
        channelTypeDataSpanish.id = 2
        assertEquals(channelTypeDataSpanish, dataEntity)
    }
}