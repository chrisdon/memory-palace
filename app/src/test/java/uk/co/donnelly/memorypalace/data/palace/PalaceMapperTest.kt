package uk.co.donnelly.memorypalace.data.palace

import junit.framework.TestCase.assertEquals
import org.junit.Test

class PalaceMapperTest {
    private val sut = PalaceMapperImpl()

    @Test
    fun testToDataNoId() {
        val dataEntity = sut.toPalaceEntity(palaceDomain)
        assertEquals(palaceData1, dataEntity)
    }

    @Test
    fun testToData() {
        val dataEntity = sut.toPalaceEntity(palaceDomainId0)
        assertEquals(palaceData1, dataEntity)
    }

    @Test
    fun testToDomain() {
        val domainEntity = sut.toPalace(palaceData1)
        assertEquals(palaceDomainId0, domainEntity)
    }
}