package uk.co.donnelly.memorypalace.data.palace

import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class PalaceRepositoryTest {
    private lateinit var dataSource: PalaceDataSource
    private lateinit var sut: PalaceRepositoryImpl

    @Before
    fun setup() {
        dataSource = PalaceDataSourceFake()
        sut = PalaceRepositoryImpl(dataSource)
    }

    @Test
    fun verifyGetPalaces() = runTest {
        val expectedPalaces = listOf(palaceDomainId0, palaceDomainId1)
        dataSource.savePalace(palaceDomainId0)
        dataSource.savePalace(palaceDomainId1)

        sut.getPalaces().collect { fetchedPalaces ->
            assertEquals(expectedPalaces, fetchedPalaces)
        }
    }

    @Test
    fun verifyAddPalace() = runTest {
        val expectedPalace = palaceDomain

        sut.addPalace(expectedPalace)

        dataSource.getPalace(palaceDomain.id).collect { savedPalace ->
            assertEquals(expectedPalace, savedPalace)
        }
    }

    @Test
    fun verifyUpdate() = runTest {
        dataSource.savePalace(palaceDomainId0)
        dataSource.savePalace(palaceDomainId1)

        sut.updatePalace(palaceDomainId1Amended)

        dataSource.getPalace(palaceDomainId1.id).collect { updatedPalace ->
            assertEquals(palaceDomainId1Amended, updatedPalace)
        }
    }

    @Test
    fun verifyRemovePalace() = runTest {
        dataSource.savePalace(palaceDomainId0)
        dataSource.savePalace(palaceDomainId1)

        sut.removePalace(palaceDomainId1)

        dataSource.getPalaces().collect { palaces ->
            assertEquals(1, palaces.size)
            assertEquals(0, palaces[0].id)
        }
    }

    @Test
    fun verifyGetPalace() = runTest {
        dataSource.savePalace(palaceDomainId0)

        sut.getPalace(palaceDomainId0.id).collect { fetchedPalace ->
            assertEquals(palaceDomainId0, fetchedPalace)
        }
    }
}