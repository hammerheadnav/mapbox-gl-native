package com.mapbox.mapboxsdk.testapp.maps

import android.graphics.PointF
import android.support.test.espresso.UiController
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory
import com.mapbox.mapboxsdk.geometry.LatLng
import com.mapbox.mapboxsdk.maps.MapView
import com.mapbox.mapboxsdk.maps.MapboxMap
import com.mapbox.mapboxsdk.testapp.action.MapboxMapAction.invoke
import com.mapbox.mapboxsdk.testapp.activity.BaseActivityTest
import com.mapbox.mapboxsdk.testapp.activity.espresso.EspressoTestActivity
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class VisibleRegionTest : BaseActivityTest() {

  private lateinit var mapView: MapView

  override fun getActivityClass(): Class<*> {
    return EspressoTestActivity::class.java
  }

  override fun beforeTest() {
    super.beforeTest()
    mapView = (rule.activity as EspressoTestActivity).mapView
  }

  @Test
  fun visibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )
      val visibleRegion = mapboxMap.projection.visibleRegion
      assertTrue(latLngs.all { visibleRegion.latLngBounds.contains(it) })
    }
  }

  @Test
  fun paddedVisibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(
        (mapView.width / 4f).toInt(),
        (mapView.height / 4f).toInt(),
        (mapView.width / 4f).toInt(),
        (mapView.height / 4f).toInt())

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 1)
      assertTrue(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)))
    }
  }

  @Test
  fun paddedLeftVisibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding((mapView.width / 4f).toInt(), 0, 0, 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f)))
    }
  }

  @Test
  fun paddedTopVisibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, (mapView.height / 4f).toInt(), 0, 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f)))
    }
  }

  @Test
  fun paddedRightVisibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, 0, (mapView.width / 4f).toInt(), 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f)))
    }
  }

  @Test
  fun paddedBottomVisibleRegionTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 0.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, 0, 0, (mapView.height / 4f).toInt())

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat())))
    }
  }

  @Test
  fun visibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )
      val visibleRegion = mapboxMap.projection.visibleRegion
      assertTrue(latLngs.all { visibleRegion.latLngBounds.contains(it) })
    }
  }

  @Test
  fun paddedVisibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(
        (mapView.width / 4f).toInt(),
        (mapView.height / 4f).toInt(),
        (mapView.width / 4f).toInt(),
        (mapView.height / 4f).toInt())

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 1)
      assertTrue(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)))
    }
  }

  @Test
  fun paddedLeftVisibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding((mapView.width / 4f).toInt(), 0, 0, 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f)))
    }
  }

  @Test
  fun paddedTopVisibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, (mapView.height / 4f).toInt(), 0, 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f)))
    }
  }

  @Test
  fun paddedRightVisibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, 0, (mapView.width / 4f).toInt(), 0)

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f)))
    }
  }

  @Test
  fun paddedBottomVisibleRegionOverDatelineTest() {
    validateTestSetup()
    invoke(mapboxMap) { _: UiController, mapboxMap: MapboxMap ->
      mapboxMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(0.0, 180.0), 8.0))
      val latLngs = listOf(
        mapboxMap.getLatLngFromScreenCoords(0f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), 0f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width.toFloat(), mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height.toFloat()),
        mapboxMap.getLatLngFromScreenCoords(0f, mapView.height / 2f),
        mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height / 2f)
      )

      mapboxMap.setPadding(0, 0, 0, (mapView.height / 4f).toInt())

      val visibleRegion = mapboxMap.projection.getVisibleRegion(false)
      val filtered = latLngs.filter { visibleRegion.latLngBounds.contains(it) }
      assertTrue(filtered.size == 6)
      assertFalse(filtered.contains(mapboxMap.getLatLngFromScreenCoords(mapView.width / 2f, mapView.height.toFloat())))
    }
  }

  private fun MapboxMap.getLatLngFromScreenCoords(x: Float, y: Float): LatLng {
    return this.projection.fromScreenLocation(PointF(x, y))
  }
}