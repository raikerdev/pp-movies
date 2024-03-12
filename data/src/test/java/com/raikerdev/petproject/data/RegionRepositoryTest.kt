package com.raikerdev.petproject.data

import com.raikerdev.petproject.data.PermissionChecker.Permission.COARSE_LOCATION
import com.raikerdev.petproject.data.datasource.LocationDataSource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock

class RegionRepositoryTest {

    @Test
    fun `Return default region when coarse permission not granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn false }
        )

        val region = regionRepository.findLastRegion()

        Assert.assertEquals(RegionRepository.DEFAULT_REGION, region)
    }

    @Test
    fun `Return region from LocationDataSource when permission granted`(): Unit = runBlocking {
        val regionRepository = buildRegionRepository(
            locationDataSource = mock { onBlocking { findLastLocation() } doReturn "CO" },
            permissionChecker = mock { on { check(COARSE_LOCATION) } doReturn true }
        )

        val region = regionRepository.findLastRegion()

        Assert.assertEquals("CO", region)
    }

}

private fun buildRegionRepository(
    locationDataSource: LocationDataSource = mock(),
    permissionChecker: PermissionChecker = mock()
) = RegionRepository(locationDataSource, permissionChecker)
