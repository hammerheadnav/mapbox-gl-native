package com.mapbox.mapboxsdk;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertSame;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MapboxTest {

  private Context context;
  private Context appContext;

  @Before
  public void before() {
    context = mock(Context.class);
    appContext = mock(Context.class);
    when(context.getApplicationContext()).thenReturn(appContext);
  }

  @Test
  public void testGetAccessToken() {
    final String accessToken = "pk.0000000001";
    injectMapboxSingleton(accessToken);
    assertSame(accessToken, Mapbox.getAccessToken());
  }

  @Test
  public void testApplicationContext() {
    injectMapboxSingleton("dummy");
    assertNotNull(Mapbox.getApplicationContext());
    assertNotEquals(context, appContext);
    assertEquals(appContext, appContext);
  }

  @Test
  public void testPkTokenValid() {
    assertTrue(Mapbox.isAccessTokenValid("pk.0000000001"));
  }

  @Test
  public void testSkTokenValid() {
    assertTrue(Mapbox.isAccessTokenValid("sk.0000000001"));
  }

  @Test
  public void testEmptyToken() {
    assertFalse(Mapbox.isAccessTokenValid(""));
  }

  @Test
  public void testNullToken() {
    assertFalse(Mapbox.isAccessTokenValid(null));
  }

  @Test
  public void testBlaBlaToken() {
    assertFalse(Mapbox.isAccessTokenValid("blabla"));
  }

  @Test
  public void testConnected() {
    injectMapboxSingleton("dummy");

    // test Android connectivity
    ConnectivityManager connectivityManager = mock(ConnectivityManager.class);
    NetworkInfo networkInfo = mock(NetworkInfo.class);
    when(appContext.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager);
    when(connectivityManager.getActiveNetworkInfo()).thenReturn(networkInfo);
    when(networkInfo.isConnected()).thenReturn(false);
    assertFalse(Mapbox.isConnected());
    when(networkInfo.isConnected()).thenReturn(true);
    assertTrue(Mapbox.isConnected());

    // test manual connectivity
    Mapbox.setConnected(true);
    assertTrue(Mapbox.isConnected());
    Mapbox.setConnected(false);
    assertFalse(Mapbox.isConnected());

    // reset to Android connectivity
    Mapbox.setConnected(null);
    assertTrue(Mapbox.isConnected());
  }

  private void injectMapboxSingleton(String accessToken) {
    Mapbox mapbox = new Mapbox(appContext, accessToken);
    try {
      Field field = Mapbox.class.getDeclaredField("INSTANCE");
      field.setAccessible(true);
      field.set(mapbox, mapbox);
    } catch (Exception exception) {
      throw new AssertionError();
    }
  }
}
